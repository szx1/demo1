package com.example.demo.jsql;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import org.apache.spark.sql.catalyst.parser.CatalystSqlParser;
import org.apache.spark.sql.catalyst.parser.ParseException;
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SparkSqlParser {
    public static void main(String[] args) {
//        String sql = "insert overwrite table turing.xmg_table_2 partition(ds='') select * from(select * from default.table2) t";
//        String sql = "insert overwrite table turing.xmg_table_2 partition(ds='') values(1,2)";
//        String sql = "insert overwrite table xmg_table_2 partition(ds='') values(1,2)";
//        String sql = "insert overwrite table turing.xmg_table_2 partition(ds='11') select t1.name,t2.age from default.table1 t1 " +
//                "left join default.table2 t2 on t1.id=t2.id";
//        String sql = "use default";
//        String sql = "create table default.tab_test_dt (\n" +
//                "  id  int  comment  'id',\n" +
//                "  name string comment  '姓名')\n" +
//                "comment   '全部数据类型表'";
//        String sql = "create table default.createTable like default.likeTable";
//        String sql = "CREATE TEMPORARY VIEW my_temp_table AS SELECT * FROM default.my_source_table ";
//        String sql = "CREATE  VIEW default.my_temp_table AS SELECT * FROM default.my_source_table ";
        String sql = "CREATE  GLOBAL  TEMPORARY VIEW my_temp_table AS SELECT * FROM default.my_source_table ";
//        String sql = "CREATE  GLOBAL  TEMPORARY VIEW my_temp_table";
//        String sql = "create table default.tab_test_dt select * from default.originTable";
//        String sql = "SELECT current_date(), t1.applyid, count(*)\n" +
//                "FROM (\n" +
//                "    SELECT applyid\n" +
//                "    FROM test.gaf_dksqcxb_dt\n" +
//                "    WHERE querytype = \"车贷查询\" AND datediff(querydt, applydt) <= 30\n" +
//                ") t1\n" +
//                "GROUP BY t1.applyid";
//        String sql="cache table test.table";
//        String sql = "insert overwrite table turing.xmg_table_2 partition(ds='')\n" +
//                "select \n" +
//                "t.cardNo\n" +
//                ",max(IntB_amt_cnt_30d)/sum(IntB_amt_cnt_30d) as acc_rate_cnt_tot_max_isIntB_30d\n" +
//                ",max(IntB_amt_cnt_1d)/sum(IntB_amt_cnt_1d) as acc_rate_cnt_tot_max_isIntB_1d\n" +
//                "from(\n" +
//                " select \n" +
//                " t1.cardNo \n" +
//                " ,t1.tranDate\n" +
//                " ,round(t2.amountAmt/100) as IntBamt\n" +
//                " ,count(1) as IntB_amt_cnt_30d\n" +
//                " ,sum(if(datediff(t1.tranDate,t2.tranDate)=0,1,0)) as IntB_amt_cnt_1d\n" +
//                " from (select cardNo,tranDate from turing.xmg_table_1 group by cardNo,tranDate) t1 \n" +
//                " left join  turing.xmg_table_1  t2 \n" +
//                " on t1.cardNo = t2.cardNo\n" +
//                " and datediff(t1.tranDate,t2.tranDate)<30\n" +
//                " and t1.tranDate>=t2.tranDate\n" +
//                " where t1.tranDate=''\n" +
//                " group by\n" +
//                " t1.cardNo \n" +
//                " ,t1.tranDate\n" +
//                " ,round(t2.amountAmt/100)  \n" +
//                ")t \n" +
//                "group by \n" +
//                "t.cardNo";

//        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, DbType.hive.name());
//        SQLStatement statement = parser.parseStatement();
//        statement.getChildren();
        Map<SparkSqlParserUtils.TableType, Set<String>> tableMaps = SparkSqlParserUtils.getTableMaps();
        org.apache.spark.sql.execution.SparkSqlParser parser = SparkSqlParserUtils.getSparkSqlParserInstance();
        SparkSqlParserUtils.visitedLogicalPlan(tableMaps, parser.parsePlan(sql), parser.parsePlan(sql));
        System.out.println(tableMaps);

//        LogicalPlan logicalPlan1 = parser.parsePlan(sql1);
//        LogicalPlan logicalPlan2 = parser.parsePlan(sql2);
//        LogicalPlan logicalPlan3 = parser.parsePlan(sql3);
//        LogicalPlan logicalPlan4 = parser.parsePlan(sql4);
//        System.out.println(true);

//        try {
//            CatalystSqlParser parser = new CatalystSqlParser();
//            LogicalPlan logicalPlan = parser.parsePlan(sql);
//
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }


}
