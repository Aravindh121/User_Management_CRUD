<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>User Management Application</title>
</head>
<body>
	<h1>User Management App</h1><br>
	<a href = "<%=request.getContextPath() %>/list">Users</a>
	<br>
	
	<c:if test="${user == null}">
		<c:set var="action" value="insert"/>
	</c:if>
	<c:if test="${user != null}">
		<c:set var="action" value="update"/>
	</c:if>

	<form action="${action}" method="post">
	
    	<c:if test="${user != null}">
					<input type="hidden" name="id" value="<c:out value='${user.id}' />" />
		</c:if>
		
		<label>User Name</label>
		<input type = "text" name = "name" value = "<c:out value='${user.name}'/>" /><br><br>
		
		<label>Email</label>
		<input type = "text" name = "email" value = " <c:out value= '${user.email}' /> " /><br><br>
		
		<label>Country</label>
		<input type = "text" name = "country" value = " <c:out value= '${user.country}' /> " /><br><br>
		
		<input type = "submit" value="submit"/>
		
	</form>
	
	
</body>
</html>