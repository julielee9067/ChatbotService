<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019-10-30
  Time: 09:27
--%>

<script type="text/javascript">

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
.manageBox {
    background-color: white;
    width: 90%;
    height: 90%;
    display:block;
    margin: 2.4%;
    float:left;
    padding-top: 3%;
    text-align:center;
    border-style:solid;
}
</style>
%{--display:table-cell;--}%
%{--padding: 3px 10px;--}%
%{--width: 80px;--}%
%{--color:black;--}%
%{--font-size: 14px;--}%

<div class="intentData">
    <h3> MANAGE INTENT CATEGORY </h3>
    <g:form name="updateIntentCategory" action="updateIntentCategory" params = "[categoryList: chatbotIntentTypes]">
        <g:submitButton class="editButton p-1 mb-2 pull-right" style="border-color: black; width: auto" name="addCategory" action="updateIntentCategory" value="Add a New Category" params = "[categoryList: chatbotIntentTypes, categoryAction: 'addCategory']"/>
        <g:textArea name="newCategory" rows="1" cols="30" class="mr-1 mb-2 pull-right"/>
        <table style="width: 100%;">
            <tr class="rTableRow">
                <td style="display: table-cell; padding: 3px 10px; text-align: center; min-width: 100px" class="cell">Category</td>
                <td style="display: table-cell; padding: 3px 10px; text-align: center; margin-left: 30%; min-width: 350px" class="cell">Description</td>
                <td style="width: 80px; text-align: center" class="cell">Edit</td>
            </tr>
            <g:each in="${chatbotIntentTypes}" var="category">
                <tr class="rTableRow">
                    <td style="display: table-cell; padding: 3px 10px; text-align: center; min-width: 100px" class="cell">${category.category}</td>
                    <td style="display: table-cell; text-align: left; padding-left: auto; padding-right: auto; margin-left: 30%; min-width: 350px" class="cell"><span id="${category.category}-Description">${category.description}</span><g:textArea id = "${category.category}-Input" style="padding-left:10px;height:100px;font-size:14px;display:none" value="${category.description}" name="${category.category}.description" action="updateIntentCategory"/> </td>
                    <td style="width: 150px; text-align: center;" class="cell">
                        <div class="editButton pull-left" onclick="edit_visibility('${category.category}-Description', '${category.category}-Edit', '${category.category}-Input', '${category.category}-Undo')" id="${category.category}-Edit"> EDIT </div>
                        <div style="display:none; text-align: center;" class="editButton pull-left" onclick="undo_visibility('${category.category}-Description', '${category.category}-Edit', '${category.category}-Input', '${category.category}-Undo')" id="${category.category}-Undo"> UNDO </div>
                        <g:if test="${category.category != "escalation"}">
                            <g:link class="editButton pull-left" name="deleteCategory" style="color: #fc0505; margin-left: 5px" controller="intents" action= "updateIntentCategory" value="Delete" params="[category: category.category, categoryAction: 'deleteCategory']">DELETE</g:link>
                        </g:if>
                    </td>
                </tr>
            </g:each>
        </table>
        <br>

        <div style="text-align: center; width: 100%">
            <g:submitButton class="editButton p-1" style="border-width: thin; margin-left: auto; margin-right: auto; border-color: black; margin-bottom: 2px;" name="submit" action="updateIntentCategory" value="UPDATE" params = "[categoryList: chatbotIntentTypes]"/>
        </div>
    </g:form>

</div>
