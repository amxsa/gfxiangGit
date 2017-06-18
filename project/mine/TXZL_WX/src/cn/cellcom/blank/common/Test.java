package cn.cellcom.blank.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Test {
	public static void main(String[] args) {
    	String SQL = "select * from t_leaveword order by recordtime desc ";
    	StringBuffer SQLCount = new StringBuffer();
		SQLCount.append("SELECT COUNT(1) AS CNT ");
    	int a = indexOfFirst(SQL.toString(), "\\s+(?i)from");
		SQLCount.append(StringUtils.substring(SQL, a));
		int b = indexOfFirst(SQLCount.toString(), "\\s+(?i)order\\s+(?i)by");
		String tempSql = SQLCount.toString();
		if(b>0)
			tempSql= StringUtils.substring(SQLCount.toString(),0, b);
         System.out.println(tempSql);

    }
    
    public static int indexOfFirst(String param, String regEx) {
		if (StringUtils.trimToNull(param) == null)
			return 0;
		Matcher matcher = Pattern.compile(regEx).matcher(param);
		if(matcher.find()){
			return matcher.start();
		}
		return 0;
	}
}
