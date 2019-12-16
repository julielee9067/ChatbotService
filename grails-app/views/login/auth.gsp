<html>
<head>
    <meta name='layout' content='login'/>
    <title><g:message code="app.login.title"/></title>
<style type='text/css' media='screen'>

#login .inner .login_message {
    padding: 6px 25px 20px 25px;
    color: #c33;
}


div#login {
    max-width: 40%;
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
    function logout() {
        ${remoteFunction(controller:"login", action:"ajaxLogout")};
    }
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
    <div class="panel-heading">GBC Web Site Services</div>
    <div class="panel-body">
        <g:if test='${flash.message}'>
            <div class='login_message'>${flash.message}</div>
        </g:if>
        <form action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
            <input type='submit' id="submitFormBtn" value='thisbutton' style="display:none" />
            <div class="form-group col-sm-12">
                <label for='username' class="col-sm-3 control-label"><g:message code="app.login.username"/>:</label>
                <div class="col-sm-7">
                    <input type='text' class='form-control' name='j_username' id='username'/>
                </div>
            </div>

            <div class="form-group col-sm-12">
                <label for='password' class="col-sm-3 control-label"><g:message code="app.login.password"/>:</label>
                <div class="col-sm-7">
                    <input type='password' class='form-control' name='j_password' id='password'/>
                </div>
            </div>

            <div class="checkbox col-sm-12" id="remember_me_holder">
                <label>
                    <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
                    <g:message code="app.login.rememberme"/>
                </label>
            </div>
            <input type='submit' id="submit" class="btn btn-primary pull-right" value='${message(code: "app.login.button")}' />

            %{--<div class="form-group col-sm-12">
                <input type='submit' class="btn btn-primary" id="submit" value='${message(code: "springSecurity.login.button")}'/>
            </div>--}%
        </form>

    </div>
</div>



<script type='text/javascript'>
    (function() {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
</script>
</body>
</html>
