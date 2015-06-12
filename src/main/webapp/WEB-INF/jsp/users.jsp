<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>


<table class="table table-bordered table-hover table-striped">
	<thead>
		<tr>
			<th>User name</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><a
					href='<spring:url value="user/${user.id }.html"></spring:url>'>
						${user.name } </a></td>				
				<td>
					<a href="/user/remove/${user.id}.html" class="btn btn-danger">Remove</a>
				</td>
			</tr>	
		</c:forEach>
	</tbody>
</table>