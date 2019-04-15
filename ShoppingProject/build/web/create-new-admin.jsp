<%-- 
    Document   : create-new-admin
    Created on : Mar 25, 2019, 10:09:39 PM
    Author     : tabal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up Page</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
        <style>

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

            body {
                margin: 0;
                padding: 0;
                font-family: sans-serif;
                letter-spacing: 1px;
                background: url(img/bg.jpg);
                background-size: cover;
            }
            .regis {
                border: 1px solid #4bc970;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                width: 380px;
                height: 550px;
                padding: 80px 40px;
                box-sizing: border-box;
                background: rgba(0, 0, 0, .8);
            }
            .regis:before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 50%;
                height: 100%;
                background: rgba(255, 255, 255, .1);
                pointer-events: none;
            }
            .avatar {
                width: 100px;
                height: 100px;
                position: absolute;
                top: calc(-100px/2);
                left: calc(50% - 50px) ;
            }
            h2 {
                margin: 0;
                padding: 0 0 20px;
                color: #4bc970;
                text-align: center;
            }
            .regis p {
                margin: 0;
                padding: 0;
                font-weight: bold;
                color: #fff;
            }
            .regis input {
                width: 100%;
                margin-bottom: 20px;
            }
            .regis input[type="email"],
            .regis input[type="text"],
            .regis input[type="password"] {
                border: none;
                border-bottom: 1px solid #fff;
                background: transparent;
                outline: none;
                color: #fff;
                font-size: 16px;
                height: 40px;
            }
            .regis input[type="submit"] {
                border: none;
                outline: none;
                color: #262626;
                background: #4bc970;
                cursor: pointer;
                border-radius: 20px;
                height: 40px;
                font-size: 16px;
            }
            .regis input[type="submit"]:hover {
                background: #81d99a;
                color: #262626;
            }
            .regis a {
                color: #fff;
                font-size: 14px;
                font-weight: bold;
                text-decoration: none;
            }
            .regis a:hover {
                color: #4bc970;
            }
        </style>
    </head>
    <body>
        <nav>
            <div class="toggle">
                <i class="fa fa-bars menu" aria-hidden="true"></i>
            </div>
            <ul>
                <li><a href="admin.jsp">Home</a></li>
                <li><a href="create-new-admin.html">New Admin</a></li>

                <li style="float: right;"><a href="#">Log out</a></li>
                <li style="float: right;"><a href="#">${USERNAME}</a></li>
            </ul>
        </nav>

        <c:set var="errors" value="${requestScope.SIGNUPERROR}"/>
        <form action="ProcessServlet" method="POST">
            <div class="regis">
                <h2>Sign Up Admin</h2>
                <p>Full Name</p>
                <input name="txtUserName" type="text" required="required" placeholder="Full Name" value="${param.txtUserName}">
                <c:if test="${not empty errors.nameLengthErr}">
                    <font color="red">
                    ${errors.nameLengthErr}
                    </font> <br/>
                </c:if>
                <p>Email address</p>
                <input name="txtEmail" type="email" required="required" placeholder="Enter Email" value="${param.txtEmail}">
                <c:if test="${not empty errors.emailFormatErr}">
                    <font color="red">
                    ${errors.emailFormatErr}
                    </font> <br/>
                </c:if>
                <c:if test="${not empty errors.emailIsExist}">
                    <font color="red">
                    ${errors.emailIsExist}
                    </font> <br/>
                </c:if>
                <p>Password</p>
                <input name="txtPassword" type="password" required="required" placeholder="Password" autocomplete="off">
                <c:if test="${not empty errors.passwordLengthErr}">
                    <font color="red">
                    ${errors.passwordLengthErr}
                    </font> <br/>
                </c:if>
                <p>Retype Password</p>
                <input name="txtRetypePassword" type="password" required="required" placeholder="Retype Password" autocomplete="off">
                <c:if test="${not empty errors.confirmNotMatch}">
                    <font color="red">
                    ${errors.confirmNotMatch}
                    </font> <br/>
                </c:if>
                <input type="submit" name="btAction" value="Sign Up Admin">
            </div>
        </form>
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
