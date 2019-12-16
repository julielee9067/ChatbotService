<style>
    .browseRow {
        border-style:solid;
    }

    .selectCI {
       width:150px;
    }
</style>
<div class="intent">
    <h3> Browse By ${params.selection.capitalize()} </h3>
    <g:form name="updateIntent" action="updateIntents" params = "[utteranceData: utterances, browse: params.browse]">
        <table class = "browseTable table-bordered">
                <tr class ="" >
                    <td class = "cell rTableCellCount">Count</td>
                    <td class = "cell rTableCellTimeSent">Last Sent</td>
                    <td class = "cell rTableCellUtterance">Utterance</td>
                    <td class = "cell rTableCellIntent"> Intent</td>
                    <td class = "cell rTableCellIncorrect"> Incorrect? </td>
                    <td class = "cell rTableCellCorrectIntent">Correct Intent</td>
                </tr>
                <g:each in="${utterances}" var="utterance" status ="i">
                    <tr class="" >
                        <td class = "cell rTableCellCount">${utterance.value.size()}</td>
                        <td class = "cell rTableCellTimeSent">${formatDate(format: 'MMM-dd-yyyy (HH:mm:ss)', date: utterance.value[0]?.timeSentMessage)}</td>
                        <td class = "cell rTableCellUtterance">${utterance.value[0]?.utterance}</td>
                        <td class = "cell rTableCellIntent"> ${utterance.value[0]?.intent}</td>
                        <td class = "cell rTableCellIncorrect"> <g:checkBox  onclick="toggle_visibility('${utterance?.value[0]?.utterance}-Select', '${utterance?.value[0]?.utterance}-Select-2')" name="${utterance?.value[0]?.utterance}.incorrect" value="${utterance?.value[0]?.incorrect}"/> </td>
                        <td class = "cell rTableCellCorrectIntent" id='${utterance.value[0]?.utterance}-NewIntent'>
                            <g:select id = '${utterance.value[0]?.utterance}-Select'  class="selectCI ${utterance.value[0]?.incorrect}-Select" name="${utterance.value[0]?.utterance}.correct_intent" from="${intents}" value="${utterance.value[0]?.correctIntent}" optionKey="intent" optionValue="intent"/>
                            <p id="${utterance.value[0]?.utterance}-Select-2" class="${utterance.value[0]?.incorrect}-default">--</p>
                        </td>
                    </tr>
                </g:each>
        </table>
        <g:submitButton class="SubmitButton" onclick = "get_form_information('${utterances}')" name="submit" action= "updateIntents" value="Update" params = "[utteranceData: utterances, selection: params.selection]"/>
    </g:form>
</div>