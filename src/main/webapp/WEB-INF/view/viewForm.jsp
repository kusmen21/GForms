<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Form with answers</title>

    <!-- Bootstrap Core CSS -->
    <spring:url value="/resources/css/bootstrap.css" var="bootstrap"/>
    <link href="${bootstrap}" rel="stylesheet" />

    <!-- Custom CSS -->
    <spring:url value="/resources/css/modern-business.css" var="startertemplate"/>
    <link href="${startertemplate}" rel="stylesheet" />

    <!-- Custom Fonts -->
    <spring:url value="/resources/font-awesome/css/font-awesome.min.css" var="fontawesome"/>
    <link href="${fontawesome}" rel="stylesheet" />

    <!-- jQuery -->
    <spring:url value="/resources/js/jquery-2.1.4.min.js" var="jqueryjs"/>

    <!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/js/bootstrap.min.js" var="js"/>
</head>

<body>

<c:url value="/" var="create"/>
<c:url value="/forms" var="forms"/>

<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
             <a class="navbar-brand" href="${create}">G forms</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Tutorial<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${create}">Create form</a>
                        </li>
                        <li>
                            <a href="${forms}">Forms</a>
                        </li>                      
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

 <!-- Page Content -->
        <div class="container">
            <!-- Page Heading/Breadcrumbs -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Form
                        <small>preview</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="${create}">Home</a>
                        </li>
                        <li class="active">Form view</li>
                    </ol>
                </div>
            </div>
            <!-- Content Row -->
            <div class="row">
                <!-- Contact Details Column -->
                <div class="col-md-6">                  
                	<h2>Form name: ${form.name}</h2>
                	<c:forEach var="field" items="${form.fields}">  
                		<hr>
                		<h3>${field.question}</h3>
                		<p>Answers:</p>
                		<c:forEach var="answer" items="${field.answers}">  
                			<small>${answer.answer};</small>
                		</c:forEach> 
                	</c:forEach>                           
                </div>
            </div>	
            <!-- /.row -->            
            <hr>   
        </div>
        <!-- /.container -->

<div class="container">
    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p>Dzmitry Kuskou for EPAM</p>
            </div>
        </div>
    </footer>
    
    <!-- SCRIPTS -->
    <script src="${jqueryjs}"></script>
    <script src="${js}"></script>    
</div>
</body>
</html>