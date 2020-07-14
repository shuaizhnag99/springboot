package com.ule.uhj.Dcoffee.tools.sqlUtil.analysis;

import com.ule.uhj.Dcoffee.tools.sqlUtil.SqlUtilConstant;

import java.util.*;

/**
 * Created by zhengxin on 2018/3/26.
 */
public class SqlExicalAnalysisHelper{
    private List<String> tableSimpleNames = new ArrayList<String>();
    private Map<String,String> tableNames = new HashMap<String, String>();
    private Map<String,Set<String>> columnsMap = new HashMap<String, Set<String>>();
    private String orginSql;
    private Section<Word> analysisStack = new Section<Word>("");

    public SqlExicalAnalysisHelper(String analysisSqlString){
        orginSql = new String(analysisSqlString);
    }

    public void Analysis(){
        analysisStack.start(new Word(SqlUtilConstant.SELECT));
        sectionAnalysis(Word.createWords(orginSql, SqlUtilConstant.TRIM), analysisStack.startInner(""));
        analysisStack.end(new Word(SqlUtilConstant.SQL_END));
        extract(analysisStack);
    }

    private void extract(Section<Word> section){
        if(section.size()<=0){
            return;
        }
        List<Section> sectionList = section.list();
        for(Section currentSection : sectionList){
            extract(currentSection);
        }
        List<Word> wordList = section.getSectionValue();
        for(Word currentWord : wordList){
            if(currentWord.getValue().contains(SqlUtilConstant.ASTERISK)){
                continue;
            }
            if(WordHelper.isTable(currentWord)){
                String[] tableString = currentWord.getValue().split(SqlUtilConstant.TRIM);
                addTable(tableString[1], tableString[0]);
            }else
            if(WordHelper.isColumn(currentWord)){
                String[] columnString = currentWord.getValue().split("\\.");
                addColumn(columnString[0],columnString[1]);
            }
        }
    }

    private void sectionAnalysis(Word currentWord,Section<Word> currentSection){
        if(currentWord==null){
            return;
        }
        do{
            if(!currentWord.isAccess()){
                if(WordHelper.isSS(currentWord)){
                    currentWord.access();
                    if(currentSection.isStart()){
                        {
                            Section<Word> innerSection = currentSection.startInner(SqlUtilConstant.SELECT);
                            innerSection.start(currentWord);
                            sectionAnalysis(currentWord.getNext(), innerSection);
                            return;
                        }
                    }else{
                        currentSection.setTag(SqlUtilConstant.SELECT);
                        currentSection.start(currentWord);
                        currentWord = currentWord.getNext();
                        continue;
                    }
                }
                if(WordHelper.isSE(currentWord) && currentSection.getTag().equals(SqlUtilConstant.SELECT)){
                    currentWord.access();
                    currentSection.end(currentWord);
                }
                if(WordHelper.isFS(currentWord)){
                    currentWord.access();
                    Section<Word> newSection = currentSection.newSection(SqlUtilConstant.FROM);
                    newSection.start(currentWord);
                    sectionAnalysis(currentWord.getNext(), newSection);
                    return;
                }
                if(WordHelper.isFE(currentWord)  && currentSection.getTag().equals(SqlUtilConstant.FROM) ){
                    currentWord.access();
                    currentSection.end(currentWord);
                }
                if(WordHelper.isWS(currentWord)){
                    currentWord.access();
                    Section<Word> newSection = currentSection.newSection(SqlUtilConstant.WHERE);
                    if(null==newSection && currentSection.getTag().equals(SqlUtilConstant.WHERE)){
                        currentSection.end(currentWord);
                        newSection = currentSection.newSection(SqlUtilConstant.WHERE);
                    }
                    if(null!=newSection) {
                    	newSection.start(currentWord);
                    	sectionAnalysis(currentWord.getNext(), newSection);
                    }
                    return;
                }
                if(WordHelper.isWE(currentWord)){
                    currentWord.access();
                    currentSection.end(currentWord);
                    return;
                }

                if(!WordHelper.isEM(currentWord)){
                    if(WordHelper.isNM(currentWord)){
                        resolution(currentSection,currentWord);
                    }
                }
            }
            currentWord = currentWord.getNext();
        }while(currentWord!=null);
    }

    private void resolution(Section<Word> section,Word word){
        while(word!=null && (WordHelper.isNM(word) || WordHelper.isEM(word))){
            if(WordHelper.isME(word)){
                word.access();
                word.mergeNext();
                word=word.getNext();
                continue;
            }

            if(WordHelper.isEM(word)){
                word.access();
                word = word.getNext();
                continue;
            }
            word.access();
            if(WordHelper.isTable(word)){
                word.mergeNext();
                section.add(word);
            }else{
                Word newWord = Word.createWords(word.getValue(),SqlUtilConstant.COMMA);
                while(newWord!=null){
                    newWord.access();
                    section.add(newWord);
                    newWord = newWord.getNext();
                }
            }

            word = word.getNext();
            continue;
        }
    }

    private void addTable(String simpleTableName,String tableName){
        addSimpleTable(simpleTableName);
        tableNames.put(simpleTableName, tableName);
    }

    private void addColumn(String simpleTableName,String columnName){
        addSimpleTable(simpleTableName);
        Set<String> columnSet = columnsMap.get(simpleTableName)==null ? new HashSet<String>() : columnsMap.get(simpleTableName);
        columnSet.add(columnName);
        columnsMap.put(simpleTableName,columnSet);
    }

    private void addSimpleTable(String simpleTableName){
        if(simpleTableName==null || tableSimpleNames.contains(simpleTableName)){
            return;
        }
        tableSimpleNames.add(simpleTableName);
    }

    public List<String> getTableSimpleNames() {
        return tableSimpleNames;
    }
    public Map<String, String> getTableNames() {
        return tableNames;
    }
    public Map<String,Set<String>> getColumnsMap() {
        return columnsMap;
    }

    public static void main(String args[]){
        String analysisString = "SELECT t1.city_name,\n" +
                "         t1.county_name,\n" +
                "         COUNT(1) AS c1,\n" +
                "         SUM(t2.credit_limit) AS c3\n" +
                "    FROM uleapp_financecr.t_j_zgd_white    t1,\n" +
                "         uleapp_financecr.t_j_account_info t2\n" +
                "   WHERE t1.user_only_id = t2.user_only_id\n" +
                "     AND t2.apply_status = '1'\n" +
                "     AND t2.open_date BETWEEN '2018-03-01' AND '2018-03-25'\n" +
                "     AND t1.province_name LIKE '%浙江%'\n" +
                "     AND t2.limit_type <> '200'\n" +
                "   GROUP BY t1.city_name, t1.county_name;";
        String analysisString2 = "select tdetail.user_name, tdetail.mobile_no, tdetail.store_address\n" +
                "  from uleapp_financecr.t_j_apply_detail tdetail\n" +
                " where tdetail.user_only_id in\n" +
                "       (SELECT t6.user_only_id AS c6\n" +
                "          FROM (SELECT t1.acc_no\n" +
                "                  FROM (SELECT tdue.acc_no AS acc_no,\n" +
                "                               MIN(tdue.loan_time) AS first_loan_time\n" +
                "                          FROM uleapp_financecr.t_j_due tdue\n" +
                "                         GROUP BY tdue.acc_no) t1,\n" +
                "                       uleapp_financecr.t_j_account_info t2,\n" +
                "                       uleapp_financecr.t_j_zgd_white t3\n" +
                "                 WHERE t1.acc_no = t2.acc_no\n" +
                "                   AND t2.user_only_id = t3.user_only_id\n" +
                "                   AND t3.province_name LIKE '%浙江%'\n" +
                "                   AND t2.limit_type <> '200'\n" +
                "                   AND t1.first_loan_time BETWEEN '2018-03-01' AND\n" +
                "                       '2018-03-26'\n" +
                "                   AND EXISTS\n" +
                "                 (SELECT 1\n" +
                "                          FROM uleapp_financecr.t_j_order t8\n" +
                "                         WHERE t8.user_id = t2.user_id\n" +
                "                           AND t8.actual_settle_date BETWEEN '2018-03-01' AND\n" +
                "                               '2018-03-25')) t4,\n" +
                "               uleapp_financecr.t_j_account_info t6,\n" +
                "               uleapp_financecr.t_j_zgd_white t7\n" +
                "         WHERE t4.acc_no = t6.acc_no\n" +
                "           AND t6.user_only_id = t7.user_only_id\n" +
                "           AND t6.limit_type <> '200'\n" +
                "           AND t7.town_name IS NOT NULL\n" +
                "           AND trim(TRANSLATE(t7.town_name, '0123456789', ' ')) IS NOT NULL);";
        String analysisString3 = "(select city_name, county_name, count(1) as c9\n" +
                "    from (select t1.city_name,\n" +
                "                 t1.county_name,\n" +
                "                 t1.town_name,\n" +
                "                 min(t3.loan_time) as first_loan_time\n" +
                "            from uleapp_financecr.t_j_zgd_white    t1,\n" +
                "                 uleapp_financecr.t_j_account_info t2,\n" +
                "                 uleapp_financecr.t_j_due          t3\n" +
                "           where t1.user_only_id = t2.user_only_id\n" +
                "             and t2.acc_no = t3.acc_no\n" +
                "             and t2.apply_status = '1'\n" +
                "             and t1.province_name like '%浙江%'\n" +
                "             AND t2.limit_type <> '200'\n" +
                "             and t1.town_name is not null\n" +
                "             and trim(translate(t1.town_name, '0123456789', ' ')) is not NULL\n" +
                "           group by t1.city_name, t1.county_name, t1.town_name)\n" +
                "   where first_loan_time between '2018-03-01' AND '2018-03-25'\n" +
                "   group by city_name, county_name)";
        SqlExicalAnalysisHelper helper = new SqlExicalAnalysisHelper(analysisString3);
        helper.Analysis();
        System.out.println("访问了以下表：");
        int i =1;
        Map<String,String> tableNameMap = helper.getTableNames();
        for(String ts : helper.getTableSimpleNames()){
            String tableName = tableNameMap.get(ts)!=null ? tableNameMap.get(ts) : "(虚表)";
            System.out.println(i+"."+tableName+" "+ts+"\n"+ts+"的字段有：");
            for(String column : helper.getColumnsMap().get(ts)){
                System.out.print(column + " ");
            }
            System.out.println();
            i++;
        }
    }
}