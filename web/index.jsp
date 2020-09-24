<%--
  Created by IntelliJ IDEA.
  User: Qulence
  Date: 21.02.2020
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Главная страница</title>
    <style>
        <%@include file="/css/style.css" %>
    </style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container-fluid">

    <div class="row">
        <div class="col-lg-12">
            <nav class="navbar navbar-expand-lg navbar-dark bg-primary">

                <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="/">Главная<span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <form method="post" action="/author" id="author">
                                <a class="nav-link" href="javascript:{}"
                                   onclick="document.getElementById('author').submit(); return false;">Автор</a>
                            </form>
                        </li>
                        <li class="nav-item">
                            <form method="post" action="/exhibit" id="exhibit">
                                <a class="nav-link" href="javascript:{}"
                                   onclick="document.getElementById('exhibit').submit(); return false;">Экспонат</a>
                            </form>
                        </li>
                        <li class="nav-item">
                            <form method="post" action="/exhibitType" id="exhibitType">
                                <a class="nav-link" href="javascript:{}"
                                   onclick="document.getElementById('exhibitType').submit(); return false;">Тип
                                    экспоната</a>
                            </form>
                        </li>
                        <li class="nav-item">
                            <form method="post" action="/museum" id="museum">
                                <a class="nav-link" href="javascript:{}"
                                   onclick="document.getElementById('museum').submit(); return false;">Музей</a>
                            </form>
                        </li>
                        <li class="nav-item">
                            <form method="post" action="/museumType" id="museumType">
                                <a class="nav-link" href="javascript:{}"
                                   onclick="document.getElementById('museumType').submit(); return false;">Тип музея</a>
                            </form>
                        </li>
                        <li class="nav-item">
                            <form method="post" action="/exhibitsOfAuthors" id="exhibitsOfAuthors">
                                <a class="nav-link" href="javascript:{}"
                                   onclick="document.getElementById('exhibitsOfAuthors').submit(); return false;">Экспонаты
                                авторов</a>
                            </form>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12 bg-light">
            <br>
            <h4 align="center">Добро пожаловать в автоматизированную систему управления музеями города!</h4>
        </div>
    </div>

</div>
</body>
</html>