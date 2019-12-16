<g:javascript>
   /* jQuery.noConflict();*/

    $(document).ready(function () {
        var url = window.location;
        // Will also work for relative and absolute hrefs
        $('ul.nav a').filter(function () {
            return this.href == url;
        }).parent().addClass('active');

        $('.nav-tabs').on('shown.bs.tab', 'a', function (e) {
            if (e.relatedTarget) {
                $(e.relatedTarget).removeClass('active');
            }
        });
        $('.dropdown-toggle').dropdown();
    });
</g:javascript>

<nav class="navbar navbar-expand-sm navbar-light bg-white mb-2 px-0 py-1" >
    <div class="collapse navbar-collapse" id="navbarNav">
        <g:if test="${appuser}">
            <ul class="nav justify-content-center nav-pills">
                <sec:ifAnyGranted roles="ROLE_APP_MANAGER,ROLE_DEVELOPER">
                    <li class="dropdown <g:if test="${which == 'config'}">active</g:if>">
                        <a class="dropdown-toggle" id="drop3" role="button" data-toggle="dropdown" href="#">Configuration</a>
                        <ul id="menu3" class="dropdown-menu" role="menu" aria-labelledby="drop3">
                            <li role="presentation"><g:link controller="settings" action="list">Settings</g:link> </li>
                        </ul>
                    </li>
                </sec:ifAnyGranted>

                <sec:ifAnyGranted roles="ROLE_USER_ADMIN">
                    <li class="<g:if test="${which == 'userManagement'}">active</g:if>">
                        <g:link controller="userManagement" action="list">Manage Users</g:link>
                    </li>
                </sec:ifAnyGranted>

                <sec:ifAnyGranted roles="ROLE_APP_MANAGER,ROLE_DEVELOPER">
                    <li class="<g:if test="${which == 'documentation'}">active</g:if>">
                        <g:link controller="api" action="index">Documentation</g:link>
                    </li>
                </sec:ifAnyGranted>

                <sec:ifAnyGranted roles="ROLE_APP_MANAGER,ROLE_DEVELOPER,ROLE_USER_ADMIN">
                    <li class="<g:if test="${which == 'transcripts'}">active</g:if>">
                        <g:link controller="transcript" action="transcript">Transcripts</g:link>
                    </li>
                </sec:ifAnyGranted>

                <sec:ifAnyGranted roles="ROLE_APP_MANAGER,ROLE_DEVELOPER,ROLE_USER_ADMIN">
                    <li class="<g:if test="${which == 'intents'}">active</g:if>">
                        <g:link controller="intents" action="intents">Intent Monitoring</g:link>
                    </li>
                </sec:ifAnyGranted>

                <sec:ifAnyGranted roles="ROLE_APP_MANAGER,ROLE_DEVELOPER,ROLE_USER_ADMIN">
                    <li class="<g:if test="${which == 'errorsEscalation'}">active</g:if>">
                        <g:link controller="errorsEscalation" action="errorsEscalation">Errors and Escalation</g:link>
                    </li>
                </sec:ifAnyGranted>

                <sec:ifAnyGranted roles="ROLE_APP_MANAGER,ROLE_DEVELOPER,ROLE_USER_ADMIN">
                    <li class="<g:if test="${which == 'help'}">active</g:if>">
                        <g:link controller="help" action="index">Help</g:link>
                    </li>
                </sec:ifAnyGranted>
            </ul>
        </g:if>
    </div>
</nav>
