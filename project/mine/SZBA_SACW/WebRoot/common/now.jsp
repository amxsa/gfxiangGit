<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.cellcom.szba.common.Env"%>
<%@ page import="cn.open.util.DateUtil"%>

{"state": "Y","time": "<%=DateUtil.format(DateUtil.getNow(), Env.DATETIME_FORMAT) %>"}