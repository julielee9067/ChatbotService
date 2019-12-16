<%@ page import="ca.georgebrown.chatbot.Setting" contentType="text/html;charset=UTF-8" %>
<g:set var="settingService" bean="settingService"/>

<html>
<head>
    <meta name="layout" content="main">


    <title>Gbc Banner Web Service - Documentation</title>
</head>

<body class="container-fluid">

<g:render template="/layouts/navigation" model="[which:'documentation']" />

<div class="container-fluid mx-5 py-3 flex-f">

    <h1 class="">Web Services Documentation</h1>

    <div class="row">
        <div class="col-sm-10">
            <div id="accordion" role="tablist">

                <!-- get status  -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinggetStatus">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetStatus" aria-expanded="false"
                               aria-controls="collapsegetStatus">
                                /getStatus
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetStatus" class="collapse" role="tabpanel"
                         aria-labelledby="headinggetStatus"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getStatus?appId=12345"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getStatus?appId=12345</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns applicant status.
                            <p>
                        </div>
                    </div>
                </div>

                <!-- check if payment has been made  -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headincheckIfTuitionIsSent">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsecheckIfTuitionIsSent" aria-expanded="false"
                               aria-controls="collapsecheckIfTuitionIsSent">
                                /checkIfTuitionIsSent
                            </a>
                        </h6>
                    </div>

                    <div id="collapsecheckIfTuitionIsSent" class="collapse" role="tabpanel"
                         aria-labelledby="headingcheckIfTuitionIsSent"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/checkIfTuitionIsSent?userId=12345"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/checkIfTuitionIsSent?userId=12345</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns the payment amount received from the user
                            <p>
                        </div>
                    </div>
                </div>

                <!-- get tuition by user  -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinggetTuitionByUser">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetTuitionByUser" aria-expanded="false"
                               aria-controls="collapsegetTuitionByUser">
                                /getTuitionByUser
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetTuitionByUser" class="collapse" role="tabpanel"
                         aria-labelledby="headingetTuitionByUser"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getTuitionByUser?appId=12345"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getTuitionByUser?appId=12345</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns tuition by user's program.
                            <p>
                        </div>
                    </div>
                </div>

                <!-- check student id -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headingcheckStudentId">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsecheckStudentId" aria-expanded="false"
                               aria-controls="collapsecheckStudentId">
                                /checkStudentId
                            </a>
                        </h6>
                    </div>

                    <div id="collapsecheckStudentId" class="collapse" role="tabpanel"
                         aria-labelledby="headingcheckStudentId"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/checkStudentId?studentId=502328"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/checkStudentId?studentId=502328</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns whether student id exist on our database or not.
                            <p>
                        </div>
                    </div>
                </div>

                <!-- get tuition by code  -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headingetTuitionByCode">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetTuitionByCode" aria-expanded="false"
                               aria-controls="collapsegetStatus">
                                /getTuitionByCode
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetTuitionByCode" class="collapse" role="tabpanel"
                         aria-labelledby="headingetTuitionByCode"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getTuitionByCode?code=B108"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getTuitionByCode?code=B108</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns tuition by the program code
                            <p>
                        </div>
                    </div>
                </div>

                <!-- get program duration -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinggetProgramDuration">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetProgramDuration" aria-expanded="false"
                               aria-controls="collapsegetProgramDuration">
                                /getProgramDuration
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetProgramDuration" class="collapse" role="tabpanel"
                         aria-labelledby="headinggetProgramDuration"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getProgramDuration?code=B108"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getProgramDuration?code=B108</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns length of a program.
                            <p>
                        </div>
                    </div>
                </div>

                <!-- get auth app -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinggetAuthApp">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetAuthApp" aria-expanded="false"
                               aria-controls="collapsegetAuthApp">
                                /getAuthApp
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetAuthApp" class="collapse" role="tabpanel"
                         aria-labelledby="headinggetAuthApp"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getAuthApp?userId=12345&appId=33333&token=c5034a05-4163-4202-84ab-5289e64719ac"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getAuthApp?userId=12345&appId=33333&token=c5034a05-4163-4202-84ab-5289e64719ac</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns whether or not that user is authorized to view application
                            <p>
                        </div>
                    </div>
                </div>


                <!-- get auth user -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinggetAuthUser">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetAuthUser" aria-expanded="false"
                               aria-controls="collapsegetAuthUser">
                                /getAuthUser
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetAuthUser" class="collapse" role="tabpanel"
                         aria-labelledby="headinggetAuthUser"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getAuthUser?userId=12345&token=c5034a05-4163-4202-84ab-5289e64719ac"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getAuthUser?userId=12345&token=c5034a05-4163-4202-84ab-5289e64719ac</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns whether or not that user is authenticated
                            <p>
                        </div>
                    </div>
                </div>

                <!-- get auth role -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinggetAuthRole">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetAuthRole" aria-expanded="false"
                               aria-controls="collapsegetAuthRole">
                                /getAuthRole
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetAuthRole" class="collapse" role="tabpanel"
                         aria-labelledby="headinggetAuthRole"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getAuthRole?userId=12345&token=c5034a05-4163-4202-84ab-5289e64719ac"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getAuthRole?userId=12345&token=c5034a05-4163-4202-84ab-5289e64719ac</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns role of the user
                            <p>
                        </div>
                    </div>
                </div>

                <!-- get auth role -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinggetName">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetName" aria-expanded="false"
                               aria-controls="collapsegetAuthRole">
                                /getName
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetName" class="collapse" role="tabpanel"
                         aria-labelledby="headinggetName"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getName?userId=14513"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getName?userId=14513</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns user's name
                            <p>
                        </div>
                    </div>
                </div>


                <!-- get agent -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinggetAgent">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetAgent" aria-expanded="false"
                               aria-controls="collapsegetAgent">
                                /getAgent
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetAgent" class="collapse" role="tabpanel"
                         aria-labelledby="headinggetAgent"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getAgent?bannerId=100922140"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getAgent?bannerId=100922140</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns name of applicant's agent
                            <p>
                        </div>
                    </div>
                </div>


                <!-- getProgramCodeOptionsFromNameCost -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinggetProgramCodeOptionsFromNameCost">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsegetProgramCodeOptionsFromNameCost" aria-expanded="false"
                               aria-controls="collapsegetProgramCodeOptionsFromNameCost">
                                /getProgramCodeOptionsFromNameCost
                            </a>
                        </h6>
                    </div>

                    <div id="collapsegetProgramCodeOptionsFromNameCost" class="collapse" role="tabpanel"
                         aria-labelledby="headinggetProgramCodeOptionsFromNameCost"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/getProgramCodeOptionsFromNameCost?name=business%20-%20finance"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getProgramCodeOptionsFromNameCost?name=business%20-%20finance</code></a>
                            <a href="/ChatbotService/api/getProgramCodeOptionsFromNameCost?name=gemmology"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/getProgramCodeOptionsFromNameCost?name=gemmology</code></a>
                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns list of possible programs given a name or if there is only one matching program, calculates estimate cost for current term and next three terms (if available)
                            <p>
                        </div>
                    </div>
                </div>

                <!-- findAppidByUsernameAndAuthorize -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headingfindAppIdByUsernameAndAuthorizeAppId">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapsefindAppIdByUsernameAndAuthorizeAppId" aria-expanded="false"
                               aria-controls="collapsefindAppidByUsernameAndAuthorize">
                                /findAppIdByUsernameAndAuthorizeAppId
                            </a>
                        </h6>
                    </div>

                    <div id="collapsefindAppIdByUsernameAndAuthorizeAppId" class="collapse" role="tabpanel"
                         aria-labelledby="headingfindAppIdByUsernameAndAuthorizeAppId"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/findAppIdByUsernameAndAuthorizeAppId?usernameApp=a.37915@test.com&userId=40441&role=5"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/findAppIdByUsernameAndAuthorizeAppId?usernameApp=a.37915@test.com&userId=40441&role=5</code></a>

                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Given the email of an applicant and an agent, returns whether or not the agent is authorized to access information on behalf of the applicant
                            <p>
                        </div>
                    </div>
                </div>

                <!-- listAgentApps -->
                <div class="card border-info mb-1 hideThis">
                    <div class="card-header" role="tab" id="headinglistAgentApps">
                        <h6 class="mb-0">
                            <a data-toggle="collapse" href="#collapselistAgentApps" aria-expanded="false"
                               aria-controls="collapselistAgentApps">
                                /listAgentApps
                            </a>
                        </h6>
                    </div>

                    <div id="collapselistAgentApps" class="collapse" role="tabpanel"
                         aria-labelledby="headinglistAgentApps"
                         data-parent="#accordion">
                        <div class="card-body  text-secondary ">
                            <h5 class="card-title">
                            </h5>
                            <h6>URL</h6>
                            <a href="/ChatbotService/api/listAgentApps?role=5&appuserId=40441&max=4"
                               class="text-danger"
                               target="_blank">
                                <code>/ChatbotService/api/listAgentApps?role=5@test.com&appuserId=40441&max=4</code></a>

                        </div>

                        <div class="card-footer bg-light text-info">
                            <p class="card-text">Returns applications that agent has access to
                            <p>
                        </div>
                    </div>
                </div>



            </div>
        </div>
    </div>
</div>

</body>
</html>