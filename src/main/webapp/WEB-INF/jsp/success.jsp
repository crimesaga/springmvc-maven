<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<table cellpadding="2" cellspacing="2">
		<tr>
			<td>Username</td>
			<td>${acc.username }</td>
		</tr>
		<tr>
			<td>Password</td>
			<td>${acc.password }</td>
		</tr>
		<tr>
			<td>Description</td>
			<td>${acc.description }</td>
		</tr>
		<tr>
			<td>Favs</td>
			<td>
				<c:forEach var="f" items="${acc.favs }">
					${f }, 
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td>DOB</td>
			<td>
				<fmt:formatDate value="${acc.dob }" pattern="dd/MM/yyyy" var="dob" />
				${dob }
			</td>
		</tr>
		<tr>
			<td>Role</td>
			<td>
				${acc.role.id } - ${acc.role.name }
			</td>
		</tr>
	</table>
	
</body>
</html>