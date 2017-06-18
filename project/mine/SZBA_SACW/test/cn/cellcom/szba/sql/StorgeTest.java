package cn.cellcom.szba.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;


public class StorgeTest {
	  public static void main(String[] args){ 
          try{ 
             Class.forName("oracle.jdbc.driver.OracleDriver"); 
             String url="jdbc:oracle:thin:@192.168.7.220:1521:orcl"; 
             Connection con = DriverManager.getConnection(url, "SZBA","cellcom"); 
             CallableStatement  pstmt = null; 
             String sql = "{call sp_get_department(?,?)}"; 
             
             pstmt = con.prepareCall(sql); 
             
             oracle.sql.ArrayDescriptor desc = oracle.sql.ArrayDescriptor.createDescriptor("ARRAY_DEPARTMENT_CONDITIONS",con);
             StructDescriptor structDesc = StructDescriptor.createDescriptor("TYPE_DEPARTMENT_CONDITION", con);
             ArrayList<STRUCT> pstruct = new ArrayList<STRUCT>();
             
             Object[] object1=null; 
             Long max = 2032l;//由于表有索引
             TestCondition tc = null;
            for ( int i=0;i<2;i++){ 
            
            	object1 = new Object[2];
            	object1[0] = max++;
            	object1[1] = 1;
            	STRUCT item = new STRUCT(structDesc, con, object1);
 			    pstruct.add(item);
            }  
             
             
             //oracle.sql.ARRAY array = new oracle.sql.ARRAY(desc,con,object1);
             ARRAY array = new ARRAY(desc,con,pstruct.toArray());
            
             pstmt.setArray(1, array); 
             pstmt.registerOutParameter(2, OracleTypes.CURSOR);
            // pstmt.registerOutParameter(2, Types.VARCHAR);
             
             pstmt.executeUpdate();
             
            // System.out.println(pstmt.getString(2));
             ResultSet rs = (ResultSet)pstmt.getObject(2);
             while(rs.next()){
            	 System.out.print(rs.getString(1));  
                 System.out.print("-----");  
                 System.out.println(rs.getString(2)); 
             }
          } catch (Exception e) { 
             e.printStackTrace(); 
          } 
      } 
	  
	  /*public static int insParentChils(ArrayList<Parent> plst,
			   ArrayList<ArrayList<Child>> clstMap, Connection con)
			   throws Exception {
			  CallableStatement cstmt = null;
			  int retVal = -1;
			  try {
			   ArrayDescriptor parentLstDesc = ArrayDescriptor.createDescriptor(
			     T_PARENT_LST, con);
			   StructDescriptor parentDesc = StructDescriptor.createDescriptor(
			     T_PARENT, con);
			   ArrayDescriptor childLstMapDesc = ArrayDescriptor.createDescriptor(
			     T_CHILD_LST_MAP, con);
			   ArrayDescriptor childLstDesc = ArrayDescriptor.createDescriptor(
			     T_CHILD_LST, con);
			   StructDescriptor childDesc = StructDescriptor.createDescriptor(
			     T_CHILD, con);
			   ArrayList<STRUCT> pstruct = new ArrayList<STRUCT>();
			   // 转换plst为Oracle 对象数组
			   for (int i = 0; i < plst.size(); i++) {
			    Parent p = plst.get(i);
			    Object[] record = new Object[2];
			    record[0] = p.getName();
			    record[1] = p.getTitle();
			    STRUCT item = new STRUCT(parentDesc, con, record);
			    pstruct.add(item);
			   }
			   ARRAY dataps = new ARRAY(parentLstDesc, con, pstruct.toArray());
			   ArrayList<ARRAY> cMap = new ArrayList<ARRAY>();
			   // 转换clst为Oracle 对象数组
			   for (int i = 0; i < clstMap.size(); i++) {
			    ArrayList<Child> childLst = clstMap.get(i);
			    ArrayList<STRUCT> cstruct = new ArrayList<STRUCT>();
			    for (int j = 0; j < childLst.size(); j++) {
			     Child c = childLst.get(j);
			     Object[] record = new Object[3];
			     record[0] = c.getChildName();
			     record[1] = c.getChildTitle();
			     record[2] = c.getChildContent();
			     STRUCT item = new STRUCT(childDesc, con, record);
			     cstruct.add(item);
			    }
			    ARRAY datacs = new ARRAY(childLstDesc, con, cstruct.toArray());
			    cMap.add(datacs);
			   }
			   ARRAY datacsMap = new ARRAY(childLstMapDesc, con, cMap.toArray());
			   cstmt = con.prepareCall(PROC_INS_PARENT_CHILD);
			   cstmt.setArray(1, dataps);
			   cstmt.setArray(2, datacsMap);
			   cstmt.registerOutParameter(3, OracleTypes.INTEGER);
			   cstmt.execute();
			   retVal = cstmt.getInt(3);
			  } catch (Exception ex) {
			   ex.printStackTrace();
			  } finally {
			   try {
			    if (cstmt != null) {
			     cstmt.close();
			    }
			   } catch (SQLException sqle) {
			    sqle.printStackTrace();
			   }
			  }
			  return retVal;
			 }
			}*/
}
