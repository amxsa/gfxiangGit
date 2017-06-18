package cn.cellcom.szba.common;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateEditor extends PropertyEditorSupport {

	private Log log = LogFactory.getLog(DateEditor.class);
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		if(StringUtils.isNotBlank(text)){
			try {
				date = fmt.parse(text);
			} catch (ParseException e) {
				log.error("格式转换出错，换一种YYYYMMDD格式转换");
				fmt = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date = fmt.parse(text);
				} catch (ParseException e1) {
					log.error("已无法转换，请检查时间格式", e);
				}
			}
		}
		setValue(date);
	}

}
