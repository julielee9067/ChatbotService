<html>
<head>
    <meta name='layout' content='login'/>
    <title>Login</title>
<style type='text/css' media='screen'>

#login .inner .login_message {
    padding: 6px 25px 20px 25px;
    color: #c33;
}

.login_message {
    color: red;
    font-weight: bold;
    padding-bottom: 10px;
}

div#login {
    max-width: 90%;
    margin: 40px auto;
}

.btn.btn-primary.pull-right{
    margin-right: 30px;
}
.col-sm-3.control-label{
    text-align: right;
}
</style>
</head>

<script type="text/javascript">

    $(document).ready(function () {
        $('#username').focus();
    });

    $(document).keypress(function (e) {
        if (e.which === 13) {
            e.preventDefault();
            $("#submit").click();
        }
    });

    $.jGrowl.defaults.position = 'center';




</script>

<br>
<br>

<div class="panel panel-default" id='login'>
    <div class="panel-body">
        <g:if test='${message}'>
            <div class='login_message'>${message}</div>
        </g:if>
        <g:form action='authChatbotProcess' controller ='login' params="[lexChatToken: lexChatToken]" id='loginForm' autocomplete='off'>
            <input type='submit' id="submitFormBtn" value='thisbutton' style="display:none" />
            <div class="form-group col-sm-12">
                <label for='username' class="col-sm-3 control-label"><g:message code="app.login.username"/>:</label>
                <div class="col-sm-7">
                    <input type='text' class='form-control' name='username' id='username'/>
                </div>
            </div>

            <div class="form-group col-sm-12">
                <label for='password' class="col-sm-3 control-label"><g:message code="app.login.password"/>:</label>
                <div class="col-sm-7">
                    <input type='password' class='form-control' name='password' id='password'/>
                </div>
            </div>

            <input type='submit' id="submit" class="btn btn-primary pull-right" value='${message(code: "app.login.button")}' />

            %{--<div class="form-group col-sm-12">
                <input type='submit' class="btn btn-primary" id="submit" value='${message(code: "springSecurity.login.button")}'/>
            </div>--}%
        </g:form>

    </div>
</div>



<script type='text/javascript'>
    (function() {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
</script>
</body>
</html>
