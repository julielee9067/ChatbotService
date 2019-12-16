<div class="container-fluid">
    <div class="row">
        <nav class="navbar navbar-light bg-white col-12">
            <span id="navbar-brand" >
                <asset:image src="GBCLogo.png" title='GBC Banner' width="100"/>
                <span class="navbar-title"><g:message code="app.title"/></span>
            </span>
            <span class=" float-right">
                <g:if test="${appuser}">
                    <span class="text-right">Welcome ${appuser.displayName}</span>
                    <g:link controller="logout">
                        <button class="btn btn-primary navbar-btn">Logout</button>
                    </g:link>
                </g:if>
            </span>
        </nav>
%{--       <div class="navbar-nav mr-auto">
    <g:if env="production">
    </g:if>
    <g:else>
        <p class="nav-item" style="padding-top: 2em">
            <button class="badge badge-pill badge-primary nav-pills" onclick="$('#debugSection').toggle();">Toggle Debug</button>
        </p>
    </g:else>
</div>--}%

%{--   <div class="collapse navbar-collapse">
    <div class="nav navbar-nav navbar-right">
        <g:if test="${appuser}">
            <p class="navbar-text">Welcome ${appuser.displayName}</p>
            <g:link controller="logout">
                <button class="btn btn-primary navbar-btn">Logout</button>
            </g:link>
        </g:if>
    </div>
</div>--}%
    </div>
</div>
