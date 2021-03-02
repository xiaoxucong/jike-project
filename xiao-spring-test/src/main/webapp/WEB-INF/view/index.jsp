<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String WEB_APP = request.getContextPath();
    request.setAttribute("WEB_APP", WEB_APP);
%>
<html>
<head>
    <title>用户信息</title>
</head>
<body>
<h1>用户信息</h1>
<form id="add_article_form" action="${WEB_APP}/login" autocomplete="off" method="post" >
   <input type="text" id="username" name="username"/>
   <input type="text" id="password" name="password"/>
    <input type="submit"/>
</form>
</body>
</html>