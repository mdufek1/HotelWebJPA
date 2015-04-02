<%-- 
    Document   : home.jsp
    Created on : Feb 17, 2015, 1:35:22 PM
    Author     : mdufek1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width">
		<link href="css/bootstrap.min.css" rel="stylesheet" />
                <link href="css/style.css" rel="stylesheet" type="text/css"/>
		<title>Third Bootstrap</title>
	</head>
	<body>

    <nav class="navbar	navbar-default	navbar-static-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="#" class="navbar-brand">Hotel Manager</a>

                <button type="button" class="navbar-toggle" data-toggle="collapse" datatarget="#collapse-menu">
                    <span class="sr-only">Toggle	navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="collapse	navbar-collapse pull-left" id="collapse-menu">
                <ul class="nav	navbar-nav navbar-right">
                    <li><a href="<%= response.encodeURL("hdc")%>">Manage Hotels</a></li>

                </ul>
                

            </div>
        </div>
    </nav>
		<script src="js/jquery-1.10.2.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
               
	</body>
</html>

