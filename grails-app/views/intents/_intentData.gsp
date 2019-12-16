<script type="text/javascript">
    <!--
    function edit_visibility(id, id2, id3, id4) {

        var e = document.getElementById(id);
        var f = document.getElementById(id2);
        var g = document.getElementById(id3);
        var h = document.getElementById(id4);

        e.style.display ='none'
        f.style.display ='none'
        g.style.display = 'table-cell'
        h.style.display = 'table-cell'

    }
    //-->

    function undo_visibility(id, id2, id3, id4) {
        var e = document.getElementById(id);
        var f = document.getElementById(id2);
        var g = document.getElementById(id3);
        var h = document.getElementById(id4);

        e.style.display ='table-cell';
        g.value =null;
        f.style.display ='table-cell';
        g.style.display = 'none';
        h.style.display = 'none';
    }
</script>

<div class="intentData">
    <h3> Intent Data </h3>
    <g:form name="updateIntentData" action="updateIntentData" params="[intents: chatbotIntents, selection: params.selection]">
        <table class="browseTable">
            <div class ="rTable">
                <tr class ="rTableRow" class="browseTable">
                    <td class = "cell rTableCellCount">Count</td>
                    <td class = "cell rTableCellIntent">Intent</td>
                    <td class = "cell rTableCellCategory">Category</td>
                    <td class = "cell rTableCellDescription">Description</td>
                    <td class = "cell rTableCellEdit"></td>
                </tr>

                <g:each in="${chatbotIntents}" var="intent" status ="i">
                    <tr class="rTableRow" >
                        <td class = "cell rTableCellCount">${intent.freq}</td>
                        <td class = "cell rTableCellIntent"> ${intent.intent}</td>
                        <td class = "cell rTableCellCategory" id='${intent.intent}-NewIntent'><g:select id='SelectCategory' class="SelectCategory" name="${intent.intent}.category" from="${chatbotIntentTypes}" noSelection="${['null':'Select One...']}" value="${intent.category}" optionKey="category" optionValue="category"/></td>
                        <td class = "cell rTableCellDescription"><p id="${intent.intent}-Description">${intent.description}</p> <g:textArea id="${intent.intent}-Input" style="padding-left:10px;height:100px; width:100%;font-size:14px;display:none" value="${intent.description}" name="${intent.intent}.description" action="updateIntentData"/> </td>
                        <td class = "cell rTableCellEdit">
                            <div class="editButton" onclick="edit_visibility('${intent.intent}-Description', '${intent.intent}-Edit', '${intent.intent}-Input', '${intent.intent}-Undo')" id="${intent.intent}-Edit">EDIT</div>
                            <div style="display:none;" class="editButton" onclick="undo_visibility('${intent.intent}-Description', '${intent.intent}-Edit', '${intent.intent}-Input', '${intent.intent}-Undo')" id="${intent.intent}-Undo">UNDO</div>
                        </td>
                    </tr>
                </g:each>
            </div>
        </table>
        <g:submitButton class="SubmitButton" name="submit" action= "updateIntentData" value="Update" params = "[intents: chatbotIntents, selection: params.selection]"/>
    </g:form>
    <br>
    <span class="pull-right">** Adding/deleting an intent is not supported. It is automatically added when the user calls an intent from Amazon Lex service.</span>
</div>
