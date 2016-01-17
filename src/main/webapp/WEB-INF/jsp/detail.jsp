<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<s:form method="post" commandName="usertype" action="save.html">
		<table cellpadding="2" cellspacing="2">
			<tr>
				<td>Id</td>
				<td><s:input path="id" />
				</td>
			</tr>
			<tr>
				<td>User type</td>
				<td><s:input path="name" />
				</td>
				<td><s:errors path="name" cssClass="error"></s:errors>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="2"><input type="submit" value="Save">
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>