<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lounge Bar - Welcome</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
    body {
        font-family: 'Segoe UI', Arial, sans-serif;
        background: linear-gradient(135deg, #1e293b 0%, #0f172a 100%);
        margin: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        color: white;
    }

    .container {
        text-align: center;
        padding: 20px;
    }

    h1 {
        font-size: 4rem;
        color: #f8fafc;
        margin-bottom: 10px;
        letter-spacing: 3px;
        text-transform: uppercase;
    }

    .subtitle {
        font-size: 1.2rem;
        color: #94a3b8;
        margin-bottom: 50px;
        font-weight: 300;
    }

    .roles-container {
        display: flex;
        justify-content: center;
        gap: 40px;
    }

   .role-card {
        background-color: rgba(255, 255, 255, 0.05);
        border: 1px solid rgba(255, 255, 255, 0.1);
        padding: 40px;
        border-radius: 24px;
        width: 240px;
        box-shadow: 0 20px 50px rgba(0,0,0,0.3);
        transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
        backdrop-filter: blur(10px);
   }

    .role-card:hover {
        transform: translateY(-15px);
        box-shadow: 0 30px 60px rgba(0,0,0,0.5);
        border-color: #38bdf8;
        background-color: rgba(255, 255, 255, 0.08);
    }

    .role-card h2 {
        color: #f8fafc;
        margin-bottom: 30px;
        font-size: 1.8rem;
    }

    button {
        margin: 10px 0;
        padding: 14px;
        cursor: pointer;
        width: 100%;
        border-radius: 12px;
        border: none;
        font-weight: 700;
        font-size: 1rem;
        transition: 0.3s;
        text-transform: uppercase;
    }


     .btn-login {
         background-color: #38bdf8;
         color: #0f172a;
     }

     .btn-login:hover {
          background-color: #7dd3fc;
          transform: scale(1.05);
     }

     .btn-signup {
          background-color: transparent;
          color: #f8fafc;
          border: 2px solid rgba(255, 255, 255, 0.2);
     }

      .btn-signup:hover {
           background-color: rgba(255, 255, 255, 0.1);
           border-color: #f8fafc;
     }

      a {
           text-decoration: none;
     }
    </style>
</head>
<body>
    <div class="container">
        <h1>LoungeBar</h1>
        <p class="subtitle">Please select your role to continue.</p>

        <div class="roles-container" style="display: flex; justify-content: space-around;">
            <div class="role-card">
                <h2>Waiter</h2>
                <a href="login?role=waiter"><button>Login</button></a>
                <br>
                <a href="signup?role=waiter"><button>Sign-up</button></a>
            </div>

            <div class="role-card">
                <h2>Customer</h2>
                <a href="login?role=customer"><button>Login</button></a>
                <br>
                <a href="signup?role=customer"><button>Sign-up</button></a>
            </div>
        </div>
    </div>
</body>
</html>
