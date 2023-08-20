package com.example.demo.jsql;


import cn.tongdun.tianzhou.common.base.BusException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.catalyst.TableIdentifier;
import org.apache.spark.sql.catalyst.analysis.UnresolvedRelation;
import org.apache.spark.sql.catalyst.expressions.*;
import org.apache.spark.sql.catalyst.plans.logical.*;
import org.apache.spark.sql.execution.SparkSqlParser;
import org.apache.spark.sql.execution.command.*;
import org.apache.spark.sql.execution.datasources.CreateTable;
import org.apache.spark.sql.internal.SQLConf;
import scala.Option;
import scala.collection.JavaConversions;
import scala.collection.Seq;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import static com.example.demo.jsql.SparkSqlParserUtils.TableType.*;

@Slf4j
public class SparkSqlParserUtils {

    enum TableType {
        VIEW("create的视图(包括临时视图和永久视图)"),
        TEMP("临时表，query缓存表"),
        DATABASE("数据库"),
        CREATE_TABLE("create的表"),
        SOURCE_TABLE("输入表或视图"),
        TARGET_TABLE("输出表或视图"),
        CACHE_TABLE("缓存表"),
        ;

        @Getter
        private final String desc;

        TableType(String desc) {
            this.desc = desc;
        }
    }

    public static final String POINT = ".";
    public static final String TEMP_REPLACE = "'temp'";
    public static final String EMPTY = "";
    public static final String SEMICOLON = ";";

    private static final Pattern pattern = Pattern.compile("\\$\\{[^}]*}");

    private static SparkSqlParser sparkSqlParser = new SparkSqlParser(new SQLConf());


    private SparkSqlParserUtils() {
    }

    public static void visitedLogicalPlan(Map<TableType, Set<String>> tableMaps, LogicalPlan curLogicalPlan, LogicalPlan parentLogicalPlan) {

        if (curLogicalPlan instanceof SetDatabaseCommand) {
            saveDataBase(tableMaps, ((SetDatabaseCommand) curLogicalPlan).databaseName());
        }

        if (curLogicalPlan instanceof DescribeTableCommand) {
            saveTableName(tableMaps, SOURCE_TABLE, ((DescribeTableCommand) curLogicalPlan).table(), true);
        }

        if (curLogicalPlan instanceof DropTableCommand) {
            saveTableName(tableMaps, SOURCE_TABLE, ((DropTableCommand) curLogicalPlan).tableName(), true);
        }

        if (curLogicalPlan instanceof TruncateTableCommand) {
            saveTableName(tableMaps, SOURCE_TABLE, ((TruncateTableCommand) curLogicalPlan).tableName(), true);
        }

        if (curLogicalPlan instanceof ShowCreateTableCommand) {
            saveTableName(tableMaps, SOURCE_TABLE, ((ShowCreateTableCommand) curLogicalPlan).table(), true);
        }

        if (curLogicalPlan instanceof InsertIntoTable) {
            LogicalPlan table = ((InsertIntoTable) curLogicalPlan).table();
            visitedLogicalPlan(tableMaps, table, curLogicalPlan);
            LogicalPlan query = ((InsertIntoTable) curLogicalPlan).query();
            visitedLogicalPlan(tableMaps, query, curLogicalPlan);
        }

        if (curLogicalPlan instanceof CreateTable) {
            saveTableName(tableMaps, CREATE_TABLE, ((CreateTable) curLogicalPlan).tableDesc().identifier(), true);
            Option<LogicalPlan> query = ((CreateTable) curLogicalPlan).query();
            if (query.isDefined()) {
                visitedLogicalPlan(tableMaps, query.get(), curLogicalPlan);
            }
        }

        if (curLogicalPlan instanceof CreateTableLikeCommand) {
            saveTableName(tableMaps, CREATE_TABLE, ((CreateTableLikeCommand) curLogicalPlan).targetTable(), true);
            saveTableName(tableMaps, SOURCE_TABLE, ((CreateTableLikeCommand) curLogicalPlan).sourceTable(), true);
        }

        if (curLogicalPlan instanceof CreateViewCommand) {
            ViewType viewType = ((CreateViewCommand) curLogicalPlan).viewType();
            boolean needDatabase = viewType instanceof PersistedView$;
            saveTableName(tableMaps, VIEW, ((CreateViewCommand) curLogicalPlan).name(), needDatabase);
            visitedLogicalPlan(tableMaps, ((CreateViewCommand) curLogicalPlan).child(), curLogicalPlan);
        }

        // 寻找保存表的信息的类
        if (curLogicalPlan instanceof UnresolvedRelation) {
            saveTableName(tableMaps, curLogicalPlan, parentLogicalPlan);
        }
        // 联合关键字
        if (curLogicalPlan instanceof Union) {
            Seq<LogicalPlan> localChildren = curLogicalPlan.children();
            List<LogicalPlan> localPlans = JavaConversions.seqAsJavaList(localChildren);
            localPlans.forEach(localLogicalPlan -> {
                visitedLogicalPlan(tableMaps, localLogicalPlan, curLogicalPlan);
            });
        }
        // 视图
        if (curLogicalPlan instanceof View) {
            LogicalPlan child = ((View) curLogicalPlan).child();
            visitedLogicalPlan(tableMaps, child, curLogicalPlan);
        }
        // 二元节点
        if (curLogicalPlan instanceof BinaryNode) {
            LogicalPlan left = ((BinaryNode) curLogicalPlan).left();
            if (left != null) {
                visitedLogicalPlan(tableMaps, left, curLogicalPlan);
            }
            LogicalPlan right = ((BinaryNode) curLogicalPlan).right();
            if (right != null) {
                visitedLogicalPlan(tableMaps, right, curLogicalPlan);
            }
            if (curLogicalPlan instanceof Join) {
                Option<Expression> condition = ((Join) (curLogicalPlan)).condition();
                Expression expression = condition.get();
                if (expression != null) {
                    visitedExpression(tableMaps, expression);
                }
            }
        }
        // 一元节点
        if (curLogicalPlan instanceof UnaryNode) {
            LogicalPlan child = ((UnaryNode) curLogicalPlan).child();
            visitedLogicalPlan(tableMaps, child, curLogicalPlan);

            if (curLogicalPlan instanceof Filter) {
                Expression condition = ((Filter) curLogicalPlan).condition();
                visitedExpression(tableMaps, condition);
            }
        }
        // 处理缓存表
        if (curLogicalPlan instanceof CacheTableCommand) {
            Option<LogicalPlan> plan = ((CacheTableCommand) curLogicalPlan).plan();
            TableType tableType = plan.isDefined() ? TEMP : CACHE_TABLE;
            saveTableName(tableMaps, tableType, ((CacheTableCommand) parentLogicalPlan).tableIdent(), plan.isEmpty());
            if (plan.isDefined()) {
                LogicalPlan child = plan.get();
                visitedLogicalPlan(tableMaps, child, curLogicalPlan);
            }
        }
    }

    private static void saveTableName(Map<TableType, Set<String>> tableMaps, LogicalPlan logicalPlan, LogicalPlan parentLogicalPlan) {
        TableType tableType = null;
        TableIdentifier tableIdentifier = null;
        if (parentLogicalPlan instanceof CacheTableCommand) {
            tableType = CACHE_TABLE;
            tableIdentifier = ((CacheTableCommand) parentLogicalPlan).tableIdent();
        }
        if (parentLogicalPlan instanceof InsertIntoTable) {
            tableType = TARGET_TABLE;
            tableIdentifier = ((UnresolvedRelation) logicalPlan).tableIdentifier();
        } else if (!(logicalPlan instanceof CacheTableCommand)) {
            tableType = SOURCE_TABLE;
            tableIdentifier = ((UnresolvedRelation) logicalPlan).tableIdentifier();
        }
        saveTableName(tableMaps, tableType, tableIdentifier, true);
    }

    private static void saveTableName(Map<TableType, Set<String>> tableMaps, TableType tableType, TableIdentifier tableIdentifier, boolean needDatabase) {
        Set<String> tableNames = tableMaps.get(tableType);
        if (tableIdentifier != null) {
            tableNames.add(combineFullTableName(tableMaps, tableIdentifier, needDatabase));
        }
    }

    private static void saveDataBase(Map<TableType, Set<String>> tableMaps, String databaseName) {
        Set<String> dataBaseNames = tableMaps.get(DATABASE);
        if (StringUtils.isNotBlank(databaseName)) {
            dataBaseNames.add(databaseName);
        }
    }

    private static String combineFullTableName(Map<TableType, Set<String>> tableMaps, TableIdentifier tableIdentifier, boolean needDatabase) {
        Optional<String> javaOptional = tableIdentifier.database().isDefined()
                ? Optional.of(tableIdentifier.database().get())
                : Optional.empty();
        StringBuilder databaseName = new StringBuilder(javaOptional.orElseGet(() -> {
            Set<String> databaseNameSet = tableMaps.get(DATABASE);
            if (CollectionUtils.isNotEmpty(databaseNameSet)) {
                String[] array = databaseNameSet.toArray(new String[0]);
                return array[array.length - 1];
            }
            return EMPTY;
        }));
        boolean temp = tableMaps.get(VIEW).contains(tableIdentifier.table()) || tableMaps.get(TEMP).contains(tableIdentifier.table());
        if (needDatabase && databaseName.length() == 0 && !temp) {
            throw new RuntimeException(String.format("cannot match databaseName,table [%s] invalid，please check sql", tableIdentifier.table()));
        }
        if (databaseName.length() > 0) {
            databaseName.append(POINT);
        }
        return databaseName.append(tableIdentifier.table()).toString();
    }


    private static void visitedExpression(Map<TableType, Set<String>> tableMaps, Expression curExpression) {
        //解析In连接词
        if (curExpression instanceof In) {
            Seq<Expression> list = ((In) curExpression).list();
            if (CollectionUtils.isNotEmpty(Collections.singleton(list))) {
                handleExpressions(tableMaps, list);
            }
        } else if (curExpression instanceof SubqueryExpression) {
            LogicalPlan plan = getLogicalPlanOnExpression(curExpression);
            if (plan != null) {
                visitedLogicalPlan(tableMaps, plan, plan);
            }
            Seq<Expression> children = curExpression.children();
            if (CollectionUtils.isNotEmpty(Collections.singleton(children))) {
                handleExpressions(tableMaps, children);
            }
        } else {
            Expression expressionRight = handleBinaryOperatorExpressionOnRight(curExpression);
            if (expressionRight != null) {
                visitedExpression(tableMaps, expressionRight);
            }
            Expression expressionLeft = handleBinaryOperatorExpressionOnLeft(curExpression);
            if (expressionLeft != null) {
                visitedExpression(tableMaps, expressionLeft);
            }
        }
    }

    /**
     * 获取右端的值的表达式子
     *
     * @param curExpression
     * @return
     */
    private static Expression handleBinaryOperatorExpressionOnRight(Expression curExpression) {
        Expression right = null;
        //解析操作符连接词
        if (curExpression instanceof BinaryOperator) {
            if (curExpression instanceof Or) {
                right = ((Or) curExpression).right();
            } else if (curExpression instanceof And) {
                right = ((And) curExpression).right();
            } else if (curExpression instanceof BinaryComparison) {
                if (curExpression instanceof EqualNullSafe) {
                    right = ((EqualNullSafe) curExpression).right();
                } else if (curExpression instanceof GreaterThanOrEqual) {
                    right = ((GreaterThanOrEqual) curExpression).right();
                } else if (curExpression instanceof LessThanOrEqual) {
                    right = ((LessThanOrEqual) curExpression).right();
                } else if (curExpression instanceof LessThan) {
                    right = ((LessThan) curExpression).right();
                } else if (curExpression instanceof GreaterThan) {
                    right = ((GreaterThan) curExpression).right();
                } else if (curExpression instanceof EqualTo) {
                    right = ((EqualTo) curExpression).right();
                }
            }
        }
        return right;
    }

    /**
     * 获取左端的值的表达式子
     *
     * @param curExpression
     * @return
     */
    private static Expression handleBinaryOperatorExpressionOnLeft(Expression curExpression) {
        Expression left = null;
        //解析操作符连接词
        if (curExpression instanceof BinaryOperator) {
            if (curExpression instanceof Or) {
                left = ((Or) curExpression).left();
            } else if (curExpression instanceof And) {
                left = ((And) curExpression).left();
            } else if (curExpression instanceof BinaryComparison) {
                if (curExpression instanceof EqualNullSafe) {
                    left = ((EqualNullSafe) curExpression).left();
                } else if (curExpression instanceof GreaterThanOrEqual) {
                    left = ((GreaterThanOrEqual) curExpression).left();
                } else if (curExpression instanceof LessThanOrEqual) {
                    left = ((LessThanOrEqual) curExpression).left();
                } else if (curExpression instanceof LessThan) {
                    left = ((LessThan) curExpression).left();
                } else if (curExpression instanceof GreaterThan) {
                    left = ((GreaterThan) curExpression).left();
                } else if (curExpression instanceof EqualTo) {
                    left = ((EqualTo) curExpression).left();
                }
            }
        }
        return left;
    }

    private static LogicalPlan getLogicalPlanOnExpression(Expression curExpression) {
        LogicalPlan plan = null;
        if (curExpression instanceof ListQuery) {
            plan = ((ListQuery) curExpression).plan();
        } else if (curExpression instanceof ScalarSubquery) {
            plan = ((ScalarSubquery) curExpression).plan();
        } else if (curExpression instanceof Exists) {
            plan = ((Exists) curExpression).plan();
        }
        return plan;
    }

    private static void handleExpressions(Map<TableType, Set<String>> tableMaps, Seq<Expression> seqExpression) {
        Collection<Expression> expressions = JavaConversions.asJavaCollection(seqExpression);
        expressions.forEach(expression -> {
            visitedExpression(tableMaps, expression);
        });
    }

    public static SparkSqlParser getSparkSqlParserInstance() {
        return sparkSqlParser;
    }

    public static Map<TableType, Set<String>> getTableMaps() {
        ConcurrentHashMap<TableType, Set<String>> tableMap = new ConcurrentHashMap<>();
        tableMap.put(VIEW, new HashSet<>());
        tableMap.put(TEMP, new HashSet<>());
        tableMap.put(DATABASE, new LinkedHashSet<>());
        tableMap.put(CREATE_TABLE, new HashSet<>());
        tableMap.put(TARGET_TABLE, new HashSet<>());
        tableMap.put(SOURCE_TABLE, new HashSet<>());
        tableMap.put(CACHE_TABLE, new HashSet<>());
        return tableMap;
    }

    private static String replacePlaceHolder(String originSql) {
        return pattern.matcher(originSql).replaceAll(TEMP_REPLACE);
    }

    /**
     * 解析表名 排除掉创建的表、创建的视图、临时缓存表
     *
     * @param cleanSql 清空注释的sql
     * @return databaseName.tableName
     */
    public static Set<String> parseTableNames(String cleanSql) {
        String realSql = replacePlaceHolder(cleanSql);
        Map<TableType, Set<String>> tableMaps = getTableMaps();
        for (String singleSql : realSql.split(SEMICOLON)) {
            try {
                LogicalPlan logicalPlan = sparkSqlParser.parsePlan(singleSql.trim());
                visitedLogicalPlan(tableMaps, logicalPlan, logicalPlan);
            } catch (Exception e) {
                log.error("sql 解析异常", e);
                throw new BusException("sql parse error" + e.getMessage());
            }
        }
        Set<String> result = new HashSet<>();
        result.addAll(tableMaps.get(SOURCE_TABLE));
        result.addAll(tableMaps.get(TARGET_TABLE));
        result.addAll(tableMaps.get(CACHE_TABLE));
        result.removeAll(tableMaps.get(CREATE_TABLE));
        result.removeAll(tableMaps.get(VIEW));
        result.removeAll(tableMaps.get(TEMP));
        return result;
    }


    public static void main(String[] args) {
        String sql = "use default;" +
                "create table default.createTable like likeTable;" +
                "CREATE TEMPORARY VIEW my_temp_table AS SELECT * FROM default.my_source_table;" +
                "insert overwrite table test.overwriteTable partition(ds='') select * from(select * from default.table2) t;" +
                "insert overwrite table test.overwriteTable partition(ds='11') select t1.name,t2.age from default.unionTables1 t1 union select * from test.unionTables2;" +
                "cache table cacheTable;" +
                "use test;" +
                "select * from selectTest";
        System.out.println(parseTableNames(sql));
    }

}


