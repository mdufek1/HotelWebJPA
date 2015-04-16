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
                    <li><a href="login.jsp">Login</a></li>

                </ul>
                

            </div>
        </div>
    </nav>
            <!--method='POST' action="/HotelJPA/register"-->
            <form id="signUpForm" role="form" method='POST' action="/HotelJPA/register" >
            <div class="col-sm-6">
                <h3 style="font-weight: 200;">Sign Up </h3>
                <div class="form-group">
                    <input tabindex="1" class="form-control" id="user" name="user" placeholder="Email address" type="text" autofocus />
                    <input tabindex="2" class="form-control" id="password" name="password" type="password" placeholder="password" />
                </div>
                <div class="form-group">
                    <input class="btn btn-warning" name="submit" type="submit" id="register" value="Register"/>
                </div>
            </div>
        </form>

		<script src="js/jquery-1.10.2.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
               <!-- <script src="js/app.js"></script>-->
	</body>
</html>

