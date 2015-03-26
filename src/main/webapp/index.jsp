<%-- 
    Document   : index.jsp
    Created on : Mar 24, 2015, 1:45:49 PM
    Author     : mdufek1
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
                        <li><a href="<%= response.encodeURL("home.jsp")%>">Home</a></li>
                    </ul>

                </div>
                <p class="navbar-brand pull-right">${time}</p>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3">
                    <div id="hotelSelectLeft">
                        <form id="hotelSearchForm" name="hotelSearchForm" method="Post" action="<%response.encodeURL("${contextPath}/hdc");%>">
                        <table>
                        <tr><td>Search By: </td><td>
                        <select id="colSelect">
                            <option value="all">All</option>
                            <c:forEach var="field" items="${fields}">
                                <option value="h.${field}">${field}</option>
                            </c:forEach>
                        </select></td>
                        <td><input type="text" id="val"  name="val" /></td>
                        <td><button type="button" id="search" class="btn btn-info">Search</button></td>
                        </tr>
                        </table>
                        </form>
            <ul id="hotelList">
            </ul>

                    </div>
                </div>
                <div class="col-md-9">
                    <form id="hotelForm" name="hotelForm" method="POST" action="<%response.encodeURL("${contextPath}/hdc");%>">
                        <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-5">
                                <table>
                                    <tr><td>ID:</td>    <td><input type="text" id="id" readonly name="id"/></td></tr>
                                    <tr><td>Street Address:</td>    <td><input type="text" id="street" name="street"/></td>  </tr>   
                                    <tr>    <td>State:</td>    <td><input type="text" id="state" name="state"/></td>    </tr>
                                    <tr>    <td>Notes:</td>    <td><TextArea name="notes" id="notes" rows="5"></textarea></td>    </tr>
</table>
                       </div>
                        <div class="col-lg-5">
                            <table>
                            <tr><td>Name:</td>      <td><input type="text" id="name" name="name" value="${selectedHotel.hotelName}"/></td></tr>
                           <tr> <td>City:</td>      <td><input type="text" id="city" name="city" value="${selectedHotel.city}"/></td></tr>
                          <tr>   <td>Zip(Postal) Code:</td>      <td><input type="text" id="zip" name="zip" value="${selectedHotel.postalCode}"/></td>   </tr>
                           </table>
                       </div>
                        <div class="col-lg-2">
                            <button type="button" id="clear" class="btn btn-default">Clear Table</button>
                                                        <button type="button" id="create" class="btn btn-info">New Record</button>
                            <button type="button" id="update" class="btn btn-primary">Update Changes</button>

                            <button type="button" id="delete" class="btn btn-danger">!!Delete Record!!</button>
                       </div>
                   </div>
                          </div>  
                        
                        
                       
                    </table>
                </form>
        </div>

        </div>
    </div>
		<script src="js/jquery-1.10.2.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
                <script src="js/app.js"></script>