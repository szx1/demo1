package com.example.demo.jsql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Commit;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.util.List;

/**
 * @author zengxi.song
 * @date 2023/8/4
 */
public class JsqlParser {

    public static void main(String[] args) throws ParseException, JSQLParserException {
        String sql =
                "insert into  turing.xmg_table_2 partition(ds='')\n" +
                        "select \n" +
                        "t.cardNo\n" +
                        ",max(IntB_amt_cnt_30d)/sum(IntB_amt_cnt_30d) as acc_rate_cnt_tot_max_isIntB_30d\n" +
                        ",max(IntB_amt_cnt_1d)/sum(IntB_amt_cnt_1d) as acc_rate_cnt_tot_max_isIntB_1d\n" +
                        "from(\n" +
                        " select \n" +
                        " t1.cardNo \n" +
                        " ,t1.tranDate\n" +
                        " ,round(t2.amountAmt/100) as IntBamt\n" +
                        " ,count(1) as IntB_amt_cnt_30d\n" +
                        " ,sum(if(datediff(t1.tranDate,t2.tranDate)=0,1,0)) as IntB_amt_cnt_1d\n" +
                        " from (select cardNo,tranDate from turing.xmg_table_1 group by cardNo,tranDate) t1 \n" +
                        " left join  turing.xmg_table_1  t2 \n" +
                        " on t1.cardNo = t2.cardNo\n" +
                        " and datediff(t1.tranDate,t2.tranDate)<30\n" +
                        " and t1.tranDate>=t2.tranDate\n" +
                        " where t1.tranDate=${bizdate}\n" +
                        " group by\n" +
                        " t1.cardNo \n" +
                        " ,t1.tranDate\n" +
                        " ,round(t2.amountAmt/100)  \n" +
                        ")t \n" +
                        "group by \n" +
                        "t.cardNo\n" +
                        ";";
//        System.out.println(singleSqlVisit(sql));

        Statements statements = CCJSqlParserUtil.parseStatements(sql);
//        statements.accept(new StatementVisitorAdapter() {
//            @Override
//            public void visit(Insert insert) {
//                Table table = insert.getTable();
//                System.out.println(table.getDatabase() + "." + table.getName());
//            }
//
//
//
//
//        });

        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        for (Statement statement : statements.getStatements()) {
            if (statement instanceof CreateTable) {
                continue;
            }
            List<String> tableList = tablesNamesFinder.getTableList(statement);
            System.out.println(tableList);
        }
        System.out.println(statements.toString());

        String sql2 = "insert overwrite table default.test select * from default.test2;";
        Statement parse = CCJSqlParserUtil.parse(sql2);
        System.out.println(tablesNamesFinder.getTableList(parse));
    }
}
