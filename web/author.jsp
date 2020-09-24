<%--
  Created by IntelliJ IDEA.
  User: Qulence
  Date: 28.02.2020
  Time: 6:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Автор</title>
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
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
    <script src="/js/script.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
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
                        <li class="nav-item">
                            <form action="/">
                                <a class="nav-link" href="/">Главная</a>
                            </form>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </div>

    <form method="post">
        <div class="row">
            <div class="col-lg-2">
                <div class="form-group">
                    <div class="row-lg-12">
                        <br>
                        <button type="submit" name="buttonClicked" value="save"
                                class="btn btn-success btn-block">
                            Добавить
                        </button>
                    </div>
                    <div class="row-lg-12">
                        <br>
                        <button type="submit" name="buttonClicked" value="update"
                                class="btn btn-primary btn-block">
                            Изменить
                        </button>
                    </div>
                    <div class="row-lg-12">
                        <br>
                        <button type="submit" name="buttonClicked" value="delete"
                                class="btn btn-danger btn-block">
                            Удалить
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-lg-10">
                <br>
                <table id="sortedTable" class="table table-striped">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">ID</th>
                        <th scope="col">ФИО автора</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${authors}" var="author">
                        <tr>
                            <td>
                                <input type="radio" class="select-item checkbox" name="authorId"
                                       value="${author.id}"/>
                            </td>
                            <th scope="row">${author.id}</th>
                            <td>${author.fullName}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </form>

</div>
</body>
</html>