<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lounge Bar - Welcome</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h1>LoungeBar</h1>

        <div class="roles-container" style="display: flex; justify-content: space-around;">
            <div class="role-card">
                <h2>Waiter</h2>
                <a href="login?role=waiter"><button>Login</button></a>
                <br>
                <a href="signup?role=waiter"><button>Sign-up</button></a>
            </div>

            <div class="role-card">
                <h2>Client</h2>
                <a href="login?role=client"><button>Login</button></a>
                <br>
                <a href="signup?role=client"><button>Sign-up</button></a>
            </div>
        </div>
    </div>
</body>
</html>
