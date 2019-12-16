<style>
    .box {

        background-color: white;
        width: 45%;
        height: 45%;
        display:block;
        margin: 2.4%;
        float:left;
        padding-top: 3%;
        text-align:center;
        border-style:solid;
    }

    p {
        text-align: left;
        margin-left: 10%
    }
    .conversationsTotal {
        text-align:center;

    }
    .header {

        border-bottom: solid;
        border-width:thick;

    }
    .reason{
        width: 40%;
        padding: 1% 4%
    }
    .count{
        width:30%;
        float:right;
        text-align:right;
        padding:2% 10%;
    }

    .escalationTable {
        width:80%;
        margin: 5% 10%;
        text-align:left;
    }

    .escalation {
        text-align:center;
    }

    .rowTable{
        border-bottom-style: dotted;
        border-bottom-width:thin;
    }

</style>

<div class="box">
    <h4>CATEGORIES</h4>
    <table class="escalationTable">
        <tr class="header">
            <td class="reason">Categories</td>
            <td class="count">Freq</td>
        </tr>
        <g:each in="${catRankings}" var="category">
            <tr class="rowTable">
                <td class="reason">${category.category}</td>
                <td class="count">${category.freq}</td>
            </tr>
        </g:each>
    </table>
</div>

<div class ="box">
    <h4>POPULAR INTENTS</h4>
    <table class="escalationTable">
        <tr class="header">
            <td class="reason">Intent</td>
            <td class="count">Freq</td>
        </tr>
        <g:each in="${popular}" var="intent">
            <tr class="rowTable">
                <td class="reason">${intent.intent}</td>
                <td class="count">${intent.freq}</td>
            </tr>
        </g:each>
    </table>
</div>

<div class ="box conversationsTotal">
    <h4>TOTAL CONVERSATIONS</h4>
    <br><br>
    <h6>As of ${yesterday}, there have been</h6>
    <h1>${totalConversations}</h1>
    <h6>conversations with AskGeorge </h6>
    <br>
    <h6>**The transcripts are updated every day at 11:59 PM.</h6>
</div>

<div class="box escalation">
    <h4>ESCALATION</h4>
    <table class="escalationTable">
        <tr class="header">
            <td class="reason">Type</td>
            <td class="count">Freq</td>
        </tr>
        <g:each in="${escalationRankings}" var="escalation">
            <tr class="rowTable">
                <td class="reason">${escalation.reason}</td>
                <td class="count">${escalation.freq}</td>
            </tr>
        </g:each>
    </table>
</div>