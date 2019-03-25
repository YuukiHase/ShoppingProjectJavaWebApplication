<%-- 
    Document   : admin
    Created on : Mar 18, 2019, 8:46:29 PM
    Author     : tabal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Management Page</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: sans-serif;
            }

            nav {
                width: 100%;
                background: #4bc970
            }
            ul {
                width: 80%;
                margin: 0 auto;
                padding: 0;
            }
            ul li {
                list-style: none;
                display: inline-block;
                padding: 10px 16px;
                cursor: pointer;
            }
            ul li:hover {
                background: #ccc;
            }
            ul li a {
                text-decoration: none;
                color: white;
            }
            ul li:hover a {
                color: #4bc970;
            }
            .toggle {
                width: 100%;
                padding: 10px 16px;
                background: #ccc;
                text-align: right;
                box-sizing: border-box;
                color: #fff;
                font-size: 16px;
                display: none;
            }
            @media (max-width:768px)
            {
                .toggle {
                    display: block;
                }
                ul {
                    width: 100%;
                    display: none;
                }
                ul li {
                    display: block;
                    text-align: center;
                }
                .active {
                    display: block;
                }
            }

            table {
                position: absolute;
                border-spacing: 0;
                width: 70%;
                margin-top: 10px;
                left: 50%;
                transform: translateX(-50%);
            }
            table tr th {
                color: white;
                font-size: 16px;
                background-color: #4bc970;
                padding: 5px 0;
            }
            table tr td {
                font-size: 16px;
                padding: 5px 0 5px 5px;
            }
            table tr:hover td{
                background-color: bisque;
            }
        </style>
    </head>
    <body>

        <nav>
            <div class="toggle">
                <i class="fa fa-bars menu" aria-hidden="true"></i>
            </div>
            <ul>
                <li><a href="#">Home</a></li>
                <li><a href="create-new-admin.jsp">New Admin</a></li>

                <li style="float: right;"><a href="#">Log out</a></li>
                <li style="float: right;"><a href="#">${USERNAME}</a></li>
            </ul>
        </nav>

        <c:set var="listAdmin" value="${LISTADMIN}" />
        <c:if test="${not empty listAdmin}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${listAdmin}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${dto.name}</td>
                            <td>${dto.email}</td>
                            <c:if test="${dto.isDeleted == false}">
                                <td>
                                    <font color="#4bc970">Active</font>
                                </td>
                            </c:if>
                            <c:if test="${dto.isDeleted == true}">
                                <td>
                                    <font color="red">Deactive</font>
                                </td>
                            </c:if>
                            <td>
                                <form action="ProcessServlet" method="POST">
                                    <input style="float: left;" type="submit" value="Deactive Admin" name="btAction"/>
                                    <input style="float: right;" type="submit" value="Active Admin" name="btAction"/>
                                    <input type="hidden" name="pk" value="${dto.email}" />
                                </form>
                            </td>
                        </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>

    <script src="http://code.jquery.com/jquery-3.3.1.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('.menu').click(function () {
                $('ul').toggleClass('active');
            });
        });
    </script>

</html>
