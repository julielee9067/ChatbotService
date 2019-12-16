<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <!-- <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css" /> -->
    <title><g:layoutTitle default='Applicant Registration'/></title>

    <g:javascript library="jquery-ui"/>

    <asset:stylesheet src="application.css"/>
    <asset:stylesheet src="spring-security-ui.css"/>
    <asset:stylesheet src="security-ui.css"/>

    <g:layoutHead/>

</head>

<body>

<div id="content">
    <g:if test="${!authChatbot}">
        <g:render template='/layouts/banner'/>
    </g:if>


    <g:layoutBody/>
</div>
<g:if test="${!authChatbot}">
    <g:render template='/layouts/footer'/>
</g:if>

</body>
</html>
