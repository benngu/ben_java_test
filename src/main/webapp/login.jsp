<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
        function checkNull(form) {
            let id = form.userId.value, password = form.password.value;

            if (id === "" || password === "") {
                let missingItem = id === "" && password === "" ? "user id and password" : id === "" ? "user id" : "user password";
                alert("Please fill the " + missingItem);
                return false;
            }
        }
    </script>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-md-6 text-center mb-5">
        <h2 class="heading-section">Login</h2>
    </div>
</div>

    <div class="row justify-content-center">
        <div class="col-md-6 text-center mb-5">
            <form id="loginForm" action="login" method="post" onsubmit="return checkNull(this);">
                <label class="col-sm-2 text-right control-label">User ID:</label><input type="text" name="userId"/><br>
                <label class="col-sm-2 text-right control-label">Password:</label><input type="password" name="password"/><br>
                <button class="rounded btn btn-primary w-auto" type="submit">Login</button>
            </form>
            <div class="text-danger"><%= request.getAttribute("result") == null ? "" : request.getAttribute("result") %></div>
        </div>
    </div>
</body>
</html>
