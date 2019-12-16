<%@ page contentType="text/html;charset=UTF-8" %>

<style>
    table {
        font-size: 15px;
        background-color: white;
        text-align: justify;
        text-justify: inter-word;
        width: 100%;
    }

    tr:hover {
        background-color: #9fc4e4;

    }

    #header:hover{
        background-color: transparent;
    }

    .paginate {
        text-align: center;
        padding-top: 15px;

    }
    .step {
        padding: 10px;
        color: black;
        text-decoration: none;
        transition: background-color .3s;
        border: 1px solid #ddd;
        background-color: white;

    }

    .nextLink {
        padding: 10px;
        color: black;
        text-decoration: none;
        transition: background-color .3s;
        border: 1px solid #ddd;
        background-color: white;
    }

    .prevLink {
        padding: 10px;
        color: black;
        text-decoration: none;
        transition: background-color .3s;
        border: 1px solid #ddd;
        background-color: white;
    }

    .currentStep {
        padding: 10px;
        background-color: #193a58;
        color: white;
        border: 1px solid #337ab7;
    }

    .step:hover:not(.active) {
        background-color: #9fc4e4;
    }

    .nextLink:hover:not(.active) {
        background-color:#9fc4e4;
    }

    .prevLink:hover:not(.active) {
        background-color: #9fc4e4;
    }

    .rTable {
        display: table;
        width: 100%;
        background-color: white;
    }

    .rTableRow {
        display: table-row;
    }
    .rTableHeading {
        display: table-header-group;
        background-color: #ddd;
    }

    .rTableCellUserId {
        display:table-cell;
        padding: 3px 10px;
        width: auto;
        color: black;
    }

    .rTableCellTimeSent {
        display:table-cell;
        padding: 3px 10px;
        width: auto;
        color: black;
    }
    .rTableCellError, .rTableCellMismatch, .rTableCellEscalate {
        display:table-cell;
        padding: 3px 10px;
        width: auto;
        color: black;
    }

    /* Tooltip container */
    .tooltip {
        position: relative;
        display: inline-block;
        border-bottom: 1px dotted black; /* If you want dots under the hoverable text */
    }

    /* Tooltip text */
    .tooltip .tooltiptext {
        visibility: hidden;
        width: 120px;
        background-color: black;
        color: #fff;
        text-align: center;
        padding: 5px 0;
        border-radius: 6px;

        /* Position the tooltip text - see examples below! */
        position: absolute;
        z-index: 1;
    }

    /* Show the tooltip text when you mouse over the tooltip container */
    .tooltip:hover .tooltiptext {
        visibility: visible;
    }

    @media screen and (max-width: 1100px) {
        .rTableRow {
            text-align:center;
        }
    }

    .rTableHeading {
        display: table-header-group;
        background-color: #ddd;
        font-weight: bold; }

     .rTableBody {
         display: table-row-group;
     }
    .header {

        border-bottom: solid;
        border-width:thick;
        font-weight:bold;
        text-align:center;

    }

    /* Add this attribute to the element that needs a tooltip */
    div [data-tooltip] {
        position: relative;
        z-index: 2;
        cursor: pointer;
    }

    /* Hide the tooltip content by default */
    div [data-tooltip]:before,
    div [data-tooltip]:after {
        visibility: hidden;
        -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
        filter: progid: DXImageTransform.Microsoft.Alpha(Opacity=0);
        opacity: 0;
        pointer-events: none;
    }

    /* Position tooltip above the element */
    div [data-tooltip]:before {
        position: absolute;
        bottom: 150%;
        left: 50%;
        margin-bottom: 5px;
        margin-left: -80px;
        padding: 7px;
        width: 160px;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
        background-color: #000;
        background-color: hsla(0, 0%, 20%, 0.9);
        color: #fff;
        content: attr(data-tooltip);
        text-align: center;
        font-size: 14px;
        line-height: 1.2;
    }

    /* Triangle hack to make tooltip look like a speech bubble */
    div [data-tooltip]:after {
        position: absolute;
        bottom: 150%;
        left: 50%;
        margin-left: -5px;
        width: 0;
        border-top: 5px solid #000;
        border-top: 5px solid hsla(0, 0%, 20%, 0.9);
        border-right: 5px solid transparent;
        border-left: 5px solid transparent;
        content: " ";
        font-size: 0;
        line-height: 0;
    }

    /* Show tooltip content on hover */
    div [data-tooltip]:hover:before,
    div [data-tooltip]:hover:after {
        visibility: visible;
        -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=100)";
        opacity: 1;
    }
</style>
<div style="overflow: auto; height: 360px;">
    <table class = "rTable" style="background-color: white; min-width: 100px">
        <tr class="rTableRow" style="alignment: top; background-color: white; height: 10px">
            <th class = "cell header" style="background-color: white">User id</th>
            <th class = "cell header" style="background-color: white">Time</th>
            <th class = "cell header" style="background-color: white" data-tooltip="Escalate">*</th>
            <th class = "cell header" style="background-color: white" data-tooltip="Processing Error">!</th>
            <th class = "cell header" style="background-color: white" data-tooltip="Mismatch Error">X</th>
        </tr>
        <g:each in="${conversations}" var="conversation">
            <tr class="rTableRow" style="background-color: white">
                <td class = "cell" style="background-color: white">
                    <g:link action="transcript" params="[token: conversation?.key, 'search-userId': 'Go', 'userId': conversation?.value.get(0).userId]">
                        ${conversation?.value.get(0).userId}
                    </g:link>
                </td>
                <td class = "cell" style="background-color: white">
                    <g:if test="${params.'search-userId' == 'Go'}">
                        <g:link action="transcript" params="[token: conversation?.key, 'search-userId': 'Go', 'userId': conversation?.value.get(0).userId]">
                            ${formatDate(format: 'MMM-dd-yyyy(HH:mm:ss)', date: conversation?.value.get(0).timeSentMessage)}
                        </g:link>
                    </g:if>
                    <g:else>
                        <g:link action="transcript" params="[token: conversation?.key]">
                            ${formatDate(format: 'MMM-dd-yyyy(HH:mm:ss)', date: conversation?.value.get(0).timeSentMessage)}
                        </g:link>
                    </g:else>
                </td>
                <g:if test="${conversation?.value?.contains("escalate")}">
                    <td class = "cell" style="background-color: white; color: red; text-align: center">*</td>
                </g:if>
                <g:else>
                    <td class = "cell"></td>
                </g:else>
                <g:if test="${conversation?.value?.contains("error")}">
                    <td class = "cell" style="background-color: white; color: red; text-align: center">!</td>
                </g:if>
                <g:else>
                    <td class = "cell"></td>
                </g:else>
                <g:if test="${conversation?.value?.contains("mismatch")}">
                    <td class = "cell" style="background-color: white; color: red; text-align: center">X</td>
                </g:if>
                <g:else>
                    <td class = "cell"></td>
                </g:else>
            </tr>
        </g:each>
    </table>
</div>
%{--<div class="paginate">--}%
%{--    <g:paginate total="${total ?: 0}" controller="transcript" action="browseTranscripts" params="${params}"--}%
%{--                max="${max}" offset="${offset}" maxsteps="5" next="&#8680" prev="&#8678" update="${div_id}"/>--}%
%{--</div>--}%