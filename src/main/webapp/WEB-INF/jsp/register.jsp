<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="${pageContext.servletContext.contextPath }/css/style.css"
	rel="stylesheet" type="text/css">
</head>
<body>

	<s:form method="post" commandName="acc" action="register.html">
		<table cellpadding="2" cellspacing="2">
			<tr>
				<td>Username</td>
				<td><s:input path="username" /></td>
				<td><s:errors path="username" cssClass="error"></s:errors></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><s:password path="password" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Description</td>
				<td><s:textarea path="description" cols="20" rows="5" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Active</td>
				<td><s:checkbox path="active" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Sex</td>
				<td><s:radiobutton path="sex" value="male" /> Male <br> <s:radiobutton
						path="sex" value="female" /> Female</td>
				<td></td>
			</tr>
			<tr>
				<td>Favs</td>
				<td><s:checkboxes items="${lf }" path="favs" itemLabel="name"
						itemValue="id" delimiter="<br>" /></td>
				<td></td>
			</tr>
			<tr>
				<td>DOB</td>
				<td><s:input path="dob" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Role</td>
				<td><s:select path="role" items="${lr }" itemLabel="name"
						itemValue="id"></s:select></td>
				<td></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="2"><input type="submit" value="Save"></td>
			</tr>
		</table>
	</s:form>

</body>
</html>