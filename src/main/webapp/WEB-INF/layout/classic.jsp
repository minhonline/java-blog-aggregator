<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
	
<script type="text/javascript"
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js">
</script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title"></tiles:getAsString></title>
</head>
<body>
	<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
	<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
	<tilesx:useAttribute name="current"/>
	<div class="container">
	 <!-- Static navbar -->
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href='<spring:url value="/"></spring:url>'>JBA</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li class="${current == 'index' ? 'active' : '' }"><a href="<spring:url value="/"></spring:url>">Home</a></li>
              <security:authorize access="hasRole('ROLE_ADMIN')">    
              	<li class="${current == 'users' ? 'active' : '' }"><a href='<spring:url value="/users.html"></spring:url>'>Users</a></li>
              </security:authorize>
              <li class="${current == 'register' ? 'active' : '' }"><a href='<spring:url value="/register.html"></spring:url>'>Registration</a></li>
              <security:authorize access="! isAuthenticated()">              		              		
              		<li class="${current == 'login' ? 'active' : '' }"><a href='<spring:url value="/login.html"></spring:url>'>Login</a></li>
              </security:authorize>
               <security:authorize access="isAuthenticated()"> 
               		<li class="${current == 'account' ? 'active' : '' }"><a href='<spring:url value="/account.html"></spring:url>'>My account</a></li>
              		<li ><a href='<spring:url value="/logout.html"></spring:url>'>Logout</a></li>
              </security:authorize>              
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>
		<tiles:insertAttribute name="body"></tiles:insertAttribute>
		<br> <br>
		<center>
			<tiles:insertAttribute name="footer"></tiles:insertAttribute>
		</center>
	</div>
</body>
</html>