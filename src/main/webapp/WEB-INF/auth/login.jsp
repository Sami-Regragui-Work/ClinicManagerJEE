<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Clinic Manager</title>
</head>
<body>
    <h1>Login</h1>

    <c:if test="${param.registered == 'true'}">
        <div class="alert alert-success">Registration successful! Please login.</div>
    </c:if>

    <c:if test="${not empty formError}">
        <div class="alert alert-danger">${formError}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/auth/login">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required/>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required/>
        </div>

        <button type="submit">Login</button>
    </form>

    <p>No account? <a href="${pageContext.request.contextPath}/auth/register">Register</a></p>
</body>
</html>