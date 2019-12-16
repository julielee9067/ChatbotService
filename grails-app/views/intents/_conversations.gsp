<div class = "conversations">
    <h2>Conversations</h2>

    <div>
        <g:each in="${utterances}" var="utterance" status="i">
            <div class="row">
                <div class="col-sm-10 convoTab">
                    <div id="accordion" class = "convoTablist" role="tablist" >
                        <!-- registrationSystemStatus  -->
                        <div class="card border-info mb-1 hideThis" style="width: auto; min-width: 100px">
                            <div class="card-header" style="min-width: 100px; width: auto" role="tab" id="headinggetStudents">
                                <h6 class="m-0 p-0" style="width: auto; min-width: 100px; margin: 0">
                                    <a data-toggle="collapse" href="#collapsegetStudents${i}" aria-expanded="false"
                                       aria-controls="collapsegetStudents${i}">
                                        ${utterance.value[0].utterance}
                                    </a>
                                </h6>
                            </div>
                            <div id="collapsegetStudents${i}" class="collapse" role="tabpanel"
                                 aria-labelledby="headinggetStudents"
                                 data-parent="#accordion">
                                <div class="card-body  text-secondary ">
                                    <g:each in="${utterance.value}" var="conv">
                                        <p><g:link controller ="transcript" action = "transcript" params="[token: conv.chatToken]">${formatDate(format: 'MMM dd, yyyy (HH:mm:ss)', date: conv.timeSentMessage)}</g:link></p>
                                    </g:each>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </g:each>
    </div>
</div>