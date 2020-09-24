<%@ page import="jspControls.ButtonType" %><%--
  Created by IntelliJ IDEA.
  User: Qulence
  Date: 29.02.2020
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
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
            <div class="col-lg-4"></div>
            <div class="col-lg-4">
                <c:forEach items="${dynamicInputControls}" var="dynamicInputControl">
                    <br>
                    <label for="${dynamicInputControl.id}">${dynamicInputControl.labelText}</label>
                    <input type="text" name="${dynamicInputControl.name}" class="form-control"
                           id="${dynamicInputControl.id}" placeholder="${dynamicInputControl.placeholderText}"
                           value="${dynamicInputControl.value}">
                </c:forEach>
                <c:forEach items="${dynamicSelectControls}" var="dynamicSelectControl">
                    <br>
                    <label for="${dynamicSelectControl.id}">${dynamicSelectControl.labelText}</label>
                    <select class="form-control" id="${dynamicSelectControl.id}" name="${dynamicSelectControl.name}">
                        <c:forEach items="${dynamicSelectControl.dynamicOptionControls}" var="dynamicOptionControl">
                            <option value="${dynamicOptionControl.id}">
                                    ${dynamicOptionControl.name}
                            </option>
                        </c:forEach>
                    </select>
                </c:forEach>
                <c:forEach items="${dynamicCheckBoxControls}" var="dynamicCheckBoxControl">
                    <br>
                    <label for="${dynamicCheckBoxControl.id}">${dynamicCheckBoxControl.labelText}</label>
                    <input type="checkbox" ${dynamicCheckBoxControl.state}
                           name="${dynamicCheckBoxControl.name}" class="form-control"
                           id="${dynamicCheckBoxControl.id}">
                </c:forEach>
            </div>
            <div class="col-lg-4"></div>
        </div>
        <div class="row">
            <div class="col-lg-4"></div>
            <div class="col-lg-4">
                <br>
                <c:forEach items="${dynamicButtonControls}" var="dynamicButtonControl">
                    <br>
                    <button type="submit" name="${dynamicButtonControl.name}" value="${dynamicButtonControl.value}"
                            class="btn btn-${dynamicButtonControl.buttonType} btn-block">
                            ${dynamicButtonControl.text}
                    </button>
                </c:forEach>
            </div>
            <div class="col-lg-4"></div>
        </div>
    </form>

</div>
</body>
</html>