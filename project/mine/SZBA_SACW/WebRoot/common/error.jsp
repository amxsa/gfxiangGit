<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.cellcom.szba.common.Env"%>
<%@ page import="cn.open.util.DateUtil"%> 
<%--通用的错误"页面"--%>
{"state": "F","code": "${dataMsg.code}","msg":"${dataMsg.msg}","timestamp":"<%=DateUtil.format(DateUtil.getNow(), Env.DATETIME_FORMAT)%>"}