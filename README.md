# ChatbotService
Chatbot service project for Intellisoft Development Inc.

# Instruction
Please read the word file in documents folder for detailed explanation about the website.
Also, you can take a look at controllers and services to learn more about our backend services.

# Contribution
My contributions to this project include, but not limited to, grails-app/controllers, services, and views.

# Java script code
This code is used to deploy the Chatbot to the website.
Note that this only works if the website is authenticated.
```
<script src="https://lex-web-ui-codebuilddeploy-1v905k1aq-webappbucket-k4xrkj0nobqh.s3.amazonaws.com/lex-web-ui-loader.min.js"></script>

<script type="text/javascript">

var chatbotName = "lex-web-ui-iframe";
if(pName) {var introMessage = 'Hi, ' + pName + '. I  am George, the GBC International Admissions Chatbot and I am here to answer your questions. \n If you do not have a specific question to ask, you can type "help" to see the list of the questions. ';}
else {var introMessage = 'Hi, I\'m George, the GBC International Admissions Chatbot and I am here to answer your questions. To serve you better, can you please provide me with your GBC student ID number? It should be 9 digits long and start with the letter M and number 1. If you do not have a GBC student ID number yet, please enter “no ID”'}

var loaderOpts = {baseUrl: 'https://lex-web-ui-codebuilddeploy-1v905k1aq-webappbucket-k4xrkj0nobqh.s3.amazonaws.com/'};

var loader = new ChatBotUiLoader.IframeLoader(loaderOpts);

var loaderCfg = {lex: { "initialText": introMessage, "sessionAttributes": {"GBCToken":  pToken, "GBCuserId": pUserid, "GBCappId": pAppid } }, ui: {"parentOrigin": "https://dmzgrg01u.georgebrown.ca"}}
loader.load(loaderCfg).catch(function (error) { console.error(error); });
</script>
```
