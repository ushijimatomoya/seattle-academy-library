<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>一括登録｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/thumbnail.js"></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
</body>
<div class="renttable">
    <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col">書籍名</th>
                <th scope="col">貸出日</th>
                <th scope="col">返却日</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="bookRentInfo" items="${bookHistoryList}">
                <tr>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/details">
                            <a href="javascript:void(0)" onclick="this.parentNode.submit();">${bookRentInfo.title}</a><input type="hidden" name="bookId" value="${bookRentInfo.bookId}">
                        </form>
                    </td>
                    <td>${bookRentInfo.rentDate}</td>
                    <td>${bookRentInfo.returnDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</html>
