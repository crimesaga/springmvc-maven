<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="${pageContext.servletContext.contextPath }/css/style.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<table>
		<th>ID</th>
		<th>Name</th>
		<c:forEach var="usertype" items="${usertypes}">
			<tr>
				<td>${usertype.id}</td>
				<td>${usertype.name}</td>
				<td><a href="<c:url value="/usertype/detail.html">
						<c:param name="id" value="${usertype.id }" />
					</c:url>">Detail</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>