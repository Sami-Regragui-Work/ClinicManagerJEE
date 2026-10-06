<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register - Clinic Manager</title>
</head>
<body>
    <h1>Create Account</h1>

    <c:if test="${not empty formError}">
        <div class="alert alert-danger">${formError}</div>
    </c:if>

    <c:if test="${not empty errors}">
        <div class="alert alert-danger">
            <ul>
                <c:forEach items="${errors}" var="error">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/auth/register">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required/>

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required/>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required/>

        <label for="phone">Phone:</label>
        <input type="tel" id="phone" name="phone" required/>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required/>

        <button type="submit">Register</button>
    </form>

    <p>Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login</a></p>
</body>
</html>