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
<style>
    .escalation {
        background-color: white;
        border-style:solid;
        border-width: medium;
        height: 100%;
        width: 100%;
        float: right;
        padding: 2%;
        overflow:auto;
    }

    .reason {
        width: 20%;
        padding: 1% 5%
    }

    .description {
        width: 50%;
        padding: 1% 5%
    }

    .count {
        width:15%;
        text-align:right;
        padding:2% 10%;
    }

    .escalationTable {
        width:100%;
    }

    .header {
        border-bottom: solid;
        border-width:thick;
    }

    .rowTable{
        border-bottom-style: dotted;
        border-bottom-width:thin;
    }

    .rTableRow {
        display: table-row;
    }

    .rTableCellCause {
        display:table-cell;
        padding: 3px 10px;
        width: 200px;
        color:black;
    }

   .rTableCellMax {
        display:table-cell;
        padding: 3px 10px;
        width: 50px;
        color:black;
   }

   .rTableCellTimeSent {
        display:table-cell;
        padding: 3px 10px;
        width: 200px;
        color:black;
   }

   .rTableCellUtterance {
        display:table-cell;
        padding: 3px 10px;
        width: 90%;
        color:black;
   }

   .rTableCellIntent {
        display:table-cell;
        padding: 3px 10px;
        width: 50%;
        color:black;
   }

   .resolve {
        display:table-cell;
        padding: 3px 10px;
        width: 40%;
        color:black;
   }

   .header {
        border-bottom: solid;
        border-width:thick;
   }

   .errorsContent {
        overflow:auto;
   }

</style>

<div class="escalation">
    <h2> Escalation </h2>
    <g:form name="updateEscalation" action="updateEscalation" params = "[chatbotEscalations: chatbotEscalations]">
        <g:submitButton class="editButton p-1 mb-2 pull-right" style="border-color: black; width: auto" name="addEscalation" action="updateEscalation" value="Add a New Escalation"/>
        <g:textArea name="newEscalation" rows="1" cols="30" class="mr-1 mb-2 pull-right"/>

        <table class="escalationTable">
            <tr class="header">
                <td class="reason"> Type </td>
                <td class="description" style="text-align: center;"> Description </td>
                <td class="count"> Freq </td>
                <td style="width: 70px; text-align: center" class="cell">Edit</td>
            </tr>

            <g:each in="${chatbotEscalations}" var="escalation">
                <tr class="rowTable">
                    <td class="reason"> ${escalation.reason} </td>
                    <td class="description" style="text-align: center"><span id="${escalation.reason}-Description">${escalation.description}</span><g:textArea id = "${escalation.reason}-Input" style="padding-left:10px;height:100px;font-size:14px;display:none" value="${escalation.description}" name="${escalation.reason}.description" action="updateEscalation"/> </td>
                    <td class="count"> ${escalation?.freq} </td>
                    <td style="width: 130px; text-align: center;" class="cell">
                        <div class="editButton pull-left" onclick="edit_visibility('${escalation.reason}-Description', '${escalation.reason}-Edit', '${escalation.reason}-Input', '${escalation.reason}-Undo')" id="${escalation.reason}-Edit"> EDIT </div>
                        <div style="display:none; text-align: center;" class="editButton pull-left" onclick="undo_visibility('${escalation.reason}-Description', '${escalation.reason}-Edit', '${escalation.reason}-Input', '${escalation.reason}-Undo')" id="${escalation.reason}-Undo"> UNDO </div>
                        <g:link class="editButton pull-left" name="deleteEscalation" style="color: #fc0505; margin-left: 5px" controller="intents" action= "updateEscalation" value="Delete" params="[escalation: escalation.reason, escalationAction: 'deleteEscalation']">DELETE</g:link>
                    </td>
                </tr>
            </g:each>
        </table>
        <br>
        <div style="text-align: center; width: 100%">
            <g:submitButton class="editButton p-1" style="border-width: thin; margin-left: auto; margin-right: auto; border-color: black; margin-bottom: 2px;" name="submit" action="updateEscalation" value="UPDATE" params = "[escalationStats: chatbotEscalations]"/>
        </div>
    </g:form>
    <br>
    <span class="pull-right"> ** If you add a new escalation, the intent with the same name will be considered as an escalation. </span>
    <br>
</div>