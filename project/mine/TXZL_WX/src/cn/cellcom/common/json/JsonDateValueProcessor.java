package cn.cellcom.common.json;

import java.util.Date;

import cn.cellcom.common.date.DateTool;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor {

	/**
     * datePattern
     */
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * JsonDateValueProcessor
     */
    public JsonDateValueProcessor() {
        super();
    }

    /**
     * @param format
     */
    public JsonDateValueProcessor(String format) {
        super();
        this.datePattern = format;
    }

    /**
     * @param value
     * @param jsonConfig
     * @return Object
     */
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * @param key
     * @param value
     * @param jsonConfig
     * @return Object
     */
    public Object processObjectValue(String key, Object value,
            JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * process
     * @param value
     * @return
     */
    private Object process(Object value) {
        try {
            if (value instanceof Date) {
               return  DateTool.formateTime2String((Date)value,this.datePattern );
            }
            return value == null ? "" : value.toString();
        } catch (Exception e) {
            return  new RuntimeException("json时间转成字符异常",e);
        }

    }

    /**
     * @return the datePattern
     */
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * @param pDatePattern the datePattern to set
     */
    public void setDatePattern(String pDatePattern) {
        datePattern = pDatePattern;
    }



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
