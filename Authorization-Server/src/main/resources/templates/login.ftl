<!DOCTYPE html>
<html lang="zh-hans">
<head>
<meta charset="utf-8">
<title>登录</title>
<link rel="icon" href="data:image/ico;base64,aWNv">
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<meta name="_csrf" content="4bfd1575-3ad1-4d21-96c7-4ef2d9f86721"/>
<meta name="_csrf_header" content="X-CSRF-TOKEN"/>
<style type="text/css">
html, body {
    height: 100%;
}

body {
    display: flex;
    align-items: center;
    padding-top: 40px;
    padding-bottom: 40px;
    background-color: #f5f5f5;
}

.form-signin {
    width: 100%;
    max-width: 330px;
    padding: 15px;
    margin: auto;
}

.form-signin .checkbox {
    font-weight: 400;
}

.form-signin .form-floating:focus-within {
    z-index: 2;
}

.form-signin input[type="text"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
}

</style>
</head>
<body class="text-center">
    <main class="form-signin">
        <form action="/login" method="post">
            <@csrf.csrf />
            <h1 class="h3 mb-3 fw-normal">欢迎登录</h1>
            <#if RequestParameters["error"]??>
                <div class="text-danger">用户名或密码错误</div>
            </#if>
            <#if RequestParameters["logout"]??>
                <div class="text-danger">您已成功退出</div>
            </#if>
            <div class="form-floating">
                <input type="text" name="username" class="form-control" placeholder="用户名"> 
                <label for="floatingInput">用户名</label>
            </div>
            <div class="form-floating">
                <input type="password" name="password" class="form-control" placeholder="密码">
                <label for="floatingPassword">密码</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary" type="submit">登录</button>
            <p class="mt-5 mb-3 text-muted">&copy; 2020–2021</p>
        </form>
    </main>
</body>
</html>