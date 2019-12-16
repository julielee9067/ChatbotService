<html>
<head>
    <meta name='layout' content='login'/>
    <title>Login</title>
<style type='text/css' media='screen'>

#login .inner .login_message {
    padding: 6px 25px 20px 25px;
    color: #c33;
}


div#login {
    max-width: 90%;
    margin: 40px auto;
}

p {
    color: green;
    text-align: center;
    font-weight: bold;
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

<p>Successful Login</p>

</div>
</div>



</body>
</html>
