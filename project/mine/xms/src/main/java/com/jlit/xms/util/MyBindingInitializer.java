package com.jlit.xms.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
/**
 * SpringMVC中数据动态绑定，时间格式转换
 *
 */
public class MyBindingInitializer implements WebBindingInitializer {  
	  
    public void initBinder(WebDataBinder binder, WebRequest request) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));  
    }  
  
  
}  