package cn.cellcom.szba.common;


import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.open.cellcom.CellcomEnv;
import cn.open.db.JDBC;
import cn.open.db.JdbcResultSet;
import cn.open.db.ProcedureParameter;
import cn.open.db.ProcedureResult;
import cn.open.db.SqlAndValue;
import cn.open.db.SqlConfHelper;
import cn.open.reflect.JavaBase;
import cn.open.util.ArrayUtil;
import cn.open.util.CommonUtil;
import cn.open.web.QueryCondition;

/**
 * 只适合会议通sybase库的表字段为有大小写的表
 * @author Administrator
 *
 */

public class SqlHelper2
{

 public SqlHelper2()
 {
 }

 public static void main(String args[])
     throws Exception
 {
     JDBC jdbc = new JDBC(CellcomEnv.CP_208_GZMCH);
     List sqls = new ArrayList();
     List tables = Arrays.asList(new String[] {
         "t_event"
     });
     String tableName;
     String sql;
     for(Iterator iterator = tables.iterator(); iterator.hasNext(); sqls.add(CommonUtil.join(tableName, new Object[] {
 ".updateById", "=", sql
})))
     {
         tableName = (String)iterator.next();
         sql = generateInsert(jdbc, tableName);
         sqls.add(CommonUtil.join(tableName, new Object[] {
             ".insert", "=", sql
         }));
         sql = generateFindById(jdbc, tableName);
         sqls.add(CommonUtil.join(tableName, new Object[] {
             ".findById", "=", sql
         }));
         sql = generateDeleteById(jdbc, tableName);
         sqls.add(CommonUtil.join(tableName, new Object[] {
             ".deleteById", "=", sql
         }));
         sql = generateUpdateById(jdbc, tableName);
     }

     ArrayUtil.print(sqls, false);
 }

 public static SqlAndValue generateQueryAllBySql(HttpServletRequest request, List conditionList, String sql)
     throws Exception
 {
     StringBuilder sb = new StringBuilder(CommonUtil.join(sql, new Object[] {
         " where 1=1 "
     }));
     List values = new ArrayList();
     if(ArrayUtil.isNotEmpty(conditionList))
     {
         for(Iterator iterator = conditionList.iterator(); iterator.hasNext();)
         {
             QueryCondition condition = (QueryCondition)iterator.next();
             SqlAndValue sav = SqlConfHelper.getConditionStatement(condition, request);
             if(sav != null)
             {
                 sb.append(CommonUtil.join(" and ", new Object[] {
                     sav.getSql()
                 }));
                 values.addAll(Arrays.asList(sav.getValues()));
             }
         }

     }
     return new SqlAndValue(sb.toString(), values.toArray());
 }

 public static SqlAndValue generateQueryAll(HttpServletRequest request, List conditionList, String tableName)
     throws Exception
 {
     StringBuilder sb = new StringBuilder(CommonUtil.join("select * from ", new Object[] {
         tableName, " where 1=1 "
     }));
     List values = new ArrayList();
     if(ArrayUtil.isNotEmpty(conditionList))
     {
         for(Iterator iterator = conditionList.iterator(); iterator.hasNext();)
         {
             QueryCondition condition = (QueryCondition)iterator.next();
             SqlAndValue sav = SqlConfHelper.getConditionStatement(condition, request);
             if(sav != null)
             {
                 sb.append(CommonUtil.join(" and ", new Object[] {
                     sav.getSql()
                 }));
                 values.addAll(Arrays.asList(sav.getValues()));
             }
         }

     }
     return new SqlAndValue(sb.toString(), values.toArray());
 }

 public static SqlAndValue generateQueryAll(HttpServletRequest request, String tableName, Map queryMap)
     throws Exception
 {
     List conditionList = (List)queryMap.get(tableName);
     StringBuilder sb = new StringBuilder(CommonUtil.join("select * from ", new Object[] {
         tableName, " where 1=1 "
     }));
     List values = new ArrayList();
     if(ArrayUtil.isNotEmpty(conditionList))
     {
         for(Iterator iterator = conditionList.iterator(); iterator.hasNext();)
         {
             QueryCondition condition = (QueryCondition)iterator.next();
             SqlAndValue sav = SqlConfHelper.getConditionStatement(condition, request);
             if(sav != null)
             {
                 sb.append(CommonUtil.join(" and ", new Object[] {
                     sav.getSql()
                 }));
                 values.addAll(Arrays.asList(sav.getValues()));
             }
         }

     }
     return new SqlAndValue(sb.toString(), values.toArray());
 }

 public static String generateUpdateById(JDBC jdbc, String tableName)
     throws Exception
 {
     Connection conn = null;
     String sql = CommonUtil.join("update ", new Object[] {
         tableName
     });
     String getPkSql = CommonUtil.join("select index_col('", new Object[] {
         tableName, "' ,indid,1) field_name from sysindexes where status&2048=2048 and id=object_id('", tableName, "')"
     });
     String pkFieldName = (String)jdbc.queryForObject(conn, getPkSql, String.class, new Object[0]);
     List procedureParameterList = new ArrayList();
     procedureParameterList.add(new ProcedureParameter(tableName, JDBC.PROCEDURE_PARAMTER_TYPE.IN));
     ProcedureResult pr = jdbc.execProcedure(conn, "sp_help", procedureParameterList);
     List resultList = pr.getResultList();
     if(ArrayUtil.isNotEmpty(resultList))
     {
         JdbcResultSet jsr = (JdbcResultSet)resultList.get(2);
         List datas = jsr.getDatas();
         StringBuilder setStr = new StringBuilder();
         for(int i = 0; i < datas.size(); i++)
         {
             Boolean isIndentity = (Boolean)jsr.getValueByFieldName("Identity", i);
             if(!isIndentity.booleanValue())
             {
                 String columnName = (String)jsr.getValueByFieldName("Column_name", i);
                 CommonUtil.makeupArrayStr(setStr, CommonUtil.join(columnName, new Object[] {
                     "=?"
                 }), ",");
             }
         }

         String setClause = CommonUtil.join(" set ", new Object[] {
             setStr.toString()
         });
         String whereClause = CommonUtil.join(" where ", new Object[] {
             pkFieldName, "=?"
         });
         return CommonUtil.join(sql, new Object[] {
             setClause, whereClause
         });
     } else
     {
         return null;
     }
 }

 public static SqlAndValue generateDymicUpate(Object pojo, String fullUpdateSql)
     throws Exception
 {
     if(pojo != null && StringUtils.isNotBlank(fullUpdateSql))
     {
         String setClause = getFieldListUpdate(fullUpdateSql);
         String whereClause = getWhere(fullUpdateSql);
         String pkColumn = getWhereColumn(whereClause);
         Object pkValue = null;
         Method method = JavaBase.getGetter(pojo.getClass(), JavaBase.getGetterByFieldName(JavaBase.columnName2FiledName(pkColumn)));
         if(method != null)
             pkValue = method.invoke(pojo, new Object[0]);
         if(StringUtils.isNotBlank(setClause) && pkValue != null)
         {
             List fullFieldList = new ArrayList();
             List realFieldList = new ArrayList();
             List values = new ArrayList();
             String setPairs[] = StringUtils.trim(setClause).split(",");
             String as[];
             int j = (as = setPairs).length;
             for(int i = 0; i < j; i++)
             {
                 String setPair = as[i];
                 setPair = StringUtils.trim(setPair);
                 if(StringUtils.endsWith(setPair, "?"))
                 {
                     String column = StringUtils.trim(CommonUtil.getFirstMatch(setPair, "\\s*\\w+"));
                     if(StringUtils.isNotBlank(column) && !StringUtils.equals(column, pkColumn))
                         fullFieldList.add(column);
                 }
             }

             List getterList = JavaBase.getGetter(pojo.getClass());
             for(Iterator iterator = getterList.iterator(); iterator.hasNext();)
             {
                 Method getter = (Method)iterator.next();
                 Object returnVal = getter.invoke(pojo, new Object[0]);
                 if(returnVal != null)
                 {
                     String fieldName = JavaBase.getFieldNameByGetter(getter.getName());
//                     String column = JavaBase.fieldName2ColumnName(fieldName);
                     String column = fieldName;
                     if(StringUtils.isNotBlank(fieldName) && !StringUtils.equals(pkColumn.toLowerCase(), column.toLowerCase()))
                     {
                         realFieldList.add(column);
                         values.add(returnVal);
                     }
                 }
             }

             List newFieldList = compareA1B1(fullFieldList, realFieldList);
             StringBuilder setStr = new StringBuilder();
             String fieldName;
             for(Iterator iterator1 = newFieldList.iterator(); iterator1.hasNext(); CommonUtil.makeupArrayStr(setStr, CommonUtil.join(fieldName, new Object[] {
 "=?"
}), ","))
                 fieldName = (String)iterator1.next();

             String baseUpdateSql = getBaseUpdate(fullUpdateSql);
             setClause = setStr.toString();
             values.add(pkValue);
             String sql = CommonUtil.join(baseUpdateSql, new Object[] {
                 " set ", setClause, " ", whereClause
             });
             return new SqlAndValue(sql, values.toArray());
         }
     }
     return null;
 }

 public static String generateDeleteById(JDBC jdbc, String tableName)
     throws Exception
 {
     Connection conn = null;
     String sql = CommonUtil.join("delete from ", new Object[] {
         tableName, " where "
     });
     String getPkSql = CommonUtil.join("select index_col('", new Object[] {
         tableName, "' ,indid,1) field_name from sysindexes where status&2048=2048 and id=object_id('", tableName, "')"
     });
     String pkFieldName = (String)jdbc.queryForObject(conn, getPkSql, String.class, new Object[0]);
     if(StringUtils.isNotBlank(pkFieldName))
         return CommonUtil.join(sql, new Object[] {
             pkFieldName, "=?"
         });
     else
         return null;
 }

 public static String generateFindById(JDBC jdbc, String tableName)
     throws Exception
 {
     Connection conn = null;
     String sql = CommonUtil.join("select * from ", new Object[] {
         tableName, " where "
     });
     String getPkSql = CommonUtil.join("select index_col('", new Object[] {
         tableName, "' ,indid,1) field_name from sysindexes where status&2048=2048 and id=object_id('", tableName, "')"
     });
     String pkFieldName = (String)jdbc.queryForObject(conn, getPkSql, String.class, new Object[0]);
     if(StringUtils.isNotBlank(pkFieldName))
         return CommonUtil.join(sql, new Object[] {
             pkFieldName, "=?"
         });
     else
         return null;
 }

 public static String generateInsert(JDBC jdbc, String tableName)
     throws Exception
 {
     Connection conn = null;
     String sql = CommonUtil.join("insert into ", new Object[] {
         tableName
     });
     List procedureParameterList = new ArrayList();
     procedureParameterList.add(new ProcedureParameter(tableName, JDBC.PROCEDURE_PARAMTER_TYPE.IN));
     ProcedureResult pr = jdbc.execProcedure(conn, "sp_help", procedureParameterList);
     List resultList = pr.getResultList();
     if(ArrayUtil.isNotEmpty(resultList))
     {
         JdbcResultSet jsr = (JdbcResultSet)resultList.get(2);
         List datas = jsr.getDatas();
         StringBuilder columnStr = new StringBuilder();
         StringBuilder valueStr = new StringBuilder();
         for(int i = 0; i < datas.size(); i++)
         {
             Boolean isIndentity = (Boolean)jsr.getValueByFieldName("Identity", i);
             if(!isIndentity.booleanValue())
             {
                 Object columnName = jsr.getValueByFieldName("Column_name", i);
                 CommonUtil.makeupArrayStr(columnStr, columnName, ",");
                 CommonUtil.makeupArrayStr(valueStr, "?", ",");
             }
         }

         String columnClause = CommonUtil.join(" (", new Object[] {
             columnStr.toString(), ")"
         });
         String valueClause = CommonUtil.join(" values (", new Object[] {
             valueStr.toString(), ")"
         });
         return CommonUtil.join(sql, new Object[] {
             columnClause, valueClause
         });
     } else
     {
         return null;
     }
 }

 public static SqlAndValue generateDymicInsert(Object pojo, String fullInsertSql)
     throws Exception
 {
     if(pojo != null && StringUtils.isNotBlank(fullInsertSql))
     {
         String fieldClause = getFieldListInsert(fullInsertSql);
         fieldClause = StringUtils.removeStart(fieldClause, "(");
         fieldClause = StringUtils.removeEnd(fieldClause, ")");
         List fullFieldList = Arrays.asList(fieldClause.split(","));
//         List fullFieldList = new ArrayList();
//         
//         for(int i = 0; i < fullFieldList2.size(); i++){
//        	 String field = (String)fullFieldList2.get(i);
//        	 fullFieldList.add(SqlSelectTool.toLowerCaseFirstOne(field));
//         }
         
         List realFieldList = new ArrayList();
         List values = new ArrayList();
         List getterList = JavaBase.getGetter(pojo.getClass());
         for(Iterator iterator = getterList.iterator(); iterator.hasNext();)
         {
             Method getter = (Method)iterator.next();
             Object returnVal = getter.invoke(pojo, new Object[0]);
             if(returnVal != null)
             {
                 String fieldName = JavaBase.getFieldNameByGetter(getter.getName());
                 if(StringUtils.isNotBlank(fieldName))
                 {
                     realFieldList.add(fieldName);
                     values.add(returnVal);
                 }
             }
         }

         List newFieldList = compareA1B1(fullFieldList, realFieldList);
         StringBuilder columnStr = new StringBuilder();
         StringBuilder valueStr = new StringBuilder();
         for(Iterator iterator1 = newFieldList.iterator(); iterator1.hasNext(); CommonUtil.makeupArrayStr(valueStr, "?", ","))
         {
             String fieldName = (String)iterator1.next();
             CommonUtil.makeupArrayStr(columnStr, fieldName, ",");
         }

         String baseInsertSql = getBaseInsert(fullInsertSql);
         String columnClause = CommonUtil.join(" (", new Object[] {
             columnStr, ")"
         });
         String valueClause = CommonUtil.join(" values (", new Object[] {
             valueStr, ")"
         });
         String sql = CommonUtil.join(baseInsertSql, new Object[] {
             " ", columnClause, valueClause
         });
         return new SqlAndValue(sql, values.toArray());
     } else
     {
         return null;
     }
 }
 
 public static List compareA1B1(List a, List b)
 {
     List result = new ArrayList();
     if(a.isEmpty() || b.isEmpty())
         return result;
    
     int aLen = a.size();
     int bLen = b.size();
     if(aLen >= bLen)
     {
         for(Iterator iterator = b.iterator(); iterator.hasNext();)
         {
             Object obj = (Object)iterator.next();
             
             for(Iterator iterator1 = a.iterator(); iterator1.hasNext();)
             {
                 Object obj1 = (Object)iterator1.next();
                 if(obj.toString().toLowerCase().equals(obj1.toString().toLowerCase())){
                     result.add(obj1);
                     break;
                 }
             }
         }

     } else
     {
         for(Iterator iterator1 = a.iterator(); iterator1.hasNext();)
         {
             Object obj = (Object)iterator1.next();
             for(Iterator iterator = b.iterator(); iterator.hasNext();)
             {
                 Object obj1 = (Object)iterator.next();
                 if(obj.toString().toLowerCase().equals(obj1.toString().toLowerCase())){
                     result.add(obj);
                     break;
                 }
             }
         }

     }
     return result;
 }

 private static String getFieldListInsert(String fullInsertSql)
 {
     if(StringUtils.isNotBlank(fullInsertSql))
     {
         fullInsertSql = StringUtils.trim(fullInsertSql);
         String sqlArr[] = fullInsertSql.split("\\s");
         String as[];
         int j = (as = sqlArr).length;
         for(int i = 0; i < j; i++)
         {
             String clause = as[i];
             if(StringUtils.startsWith(clause, "(") && StringUtils.endsWith(clause, ")"))
                 return clause;
         }

     }
     return null;
 }

 private static String getFieldListUpdate(String fullUpdateSql)
 {
     if(StringUtils.isNotBlank(fullUpdateSql))
     {
         fullUpdateSql = StringUtils.trim(fullUpdateSql);
         String s = CommonUtil.getFirstMatch(fullUpdateSql, "^update\\s+\\w+\\s+set\\s+");
         String e = CommonUtil.getFirstMatch(fullUpdateSql, "\\s+where\\s+");
         int start = s.length();
         int end = fullUpdateSql.indexOf(e);
         return fullUpdateSql.substring(start, end);
     } else
     {
         return null;
     }
 }

 private static String getBaseUpdate(String fullUpdateSql)
 {
     if(StringUtils.isNotBlank(fullUpdateSql))
     {
         fullUpdateSql = StringUtils.trim(fullUpdateSql);
         String s = CommonUtil.getFirstMatch(fullUpdateSql, "^update\\s+\\w+\\s+");
         if(StringUtils.isNotBlank(s))
             return StringUtils.trim(s);
     }
     return null;
 }

 private static String getWhere(String fullUpdateSql)
 {
     if(StringUtils.isNotBlank(fullUpdateSql))
     {
         fullUpdateSql = StringUtils.trim(fullUpdateSql);
         String s = CommonUtil.getFirstMatch(fullUpdateSql, "\\s+where\\s+\\w+\\s*=\\s*\\?");
         if(StringUtils.isNotBlank(s))
             return StringUtils.trim(s);
     }
     return null;
 }

 private static String getWhereColumn(String where)
 {
     if(StringUtils.isNotBlank(where))
     {
         where = StringUtils.trim(where);
         String s = CommonUtil.getFirstMatch(where, "\\s+\\w+\\s*=");
         if(StringUtils.isNotBlank(s))
         {
             String arr[] = StringUtils.trim(s).split("=");
             if(arr != null && arr.length > 0 && arr.length <= 2)
                 return arr[0];
         }
     }
     return null;
 }

 private static String getBaseInsert(String fullInsertSql)
 {
     if(StringUtils.isNotBlank(fullInsertSql))
     {
         fullInsertSql = StringUtils.trim(fullInsertSql);
         String s = CommonUtil.getFirstMatch(fullInsertSql, "^insert\\s+into\\s+\\w+\\s+");
         if(StringUtils.isNotBlank(s))
             return StringUtils.trim(s);
     }
     return null;
 }

 public static String getSelectCount(String select)
 {
     if(StringUtils.isNotBlank(select))
     {
         select = StringUtils.trim(select);
         String aSelect = CommonUtil.getFirstMatch(select, "select\\s+", false);
         String aFrom = CommonUtil.getFirstMatch(select, "\\s+from\\s+", false);
         int end = StringUtils.indexOf(select, aFrom);
         StringBuilder sb = (new StringBuilder(select)).delete(aSelect.length(), end);
         sb.insert(aSelect.length(), "count(*)");
         String s = sb.toString();
         int orderByIndex = StringUtils.indexOfIgnoreCase(s, "order by");
         if(orderByIndex >= 0)
             s = s.substring(0, orderByIndex);
         return s;
     } else
     {
         return null;
     }
 }
}


/*
	DECOMPILATION REPORT

	Decompiled from: E:\workspaceHYT_VIP\hyt_vip\WebRoot\WEB-INF\lib\open-x64.jar
	Total time: 296 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/