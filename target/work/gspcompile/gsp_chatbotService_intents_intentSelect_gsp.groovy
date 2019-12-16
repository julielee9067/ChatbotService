import ca.georgebrown.chatbot.ChatbotIntentUtterance
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_intents_intentSelect_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/intents/_intentSelect.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('textField','g',149,['id':("intentInput"),'class':("intentInput mr-1"),'name':("Intent"),'action':("intents")],-1)
printHtmlPart(2)
invokeTag('submitButton','g',150,['class':("button search-intent"),'name':("search-intent"),'action':("intents"),'value':("Go")],-1)
printHtmlPart(3)
})
invokeTag('form','g',153,['name':("SearchByIntent"),'url':(" intents?selection=search")],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
invokeTag('select','g',158,['id':("IntentSelect"),'name':("Intent"),'from':(intents),'noSelection':(['':'Select Intent']),'optionKey':("intent"),'optionValue':("intent"),'class':("mr-1")],-1)
printHtmlPart(2)
invokeTag('submitButton','g',159,['class':("button select-intent mr-1"),'name':("select-intent"),'action':("intents"),'value':("Go")],-1)
printHtmlPart(3)
})
invokeTag('form','g',162,['name':("SelectIntent"),'url':(" intents?selection=search")],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(6)
invokeTag('textField','g',168,['id':("intentInput"),'class':("search-utterance mr-1"),'name':("Utterance"),'action':("intents")],-1)
printHtmlPart(2)
invokeTag('submitButton','g',169,['class':("button mr-1"),'name':("search-utterance"),'action':("intents"),'value':("Go")],-1)
printHtmlPart(3)
})
invokeTag('form','g',172,['name':("SearchByUtterance"),'url':(" intents?selection=search")],1)
printHtmlPart(7)
if(true && (params.Intent)) {
printHtmlPart(8)
expressionOut.print(params.Intent)
printHtmlPart(9)
createTagBody(2, {->
printHtmlPart(10)
expressionOut.print(params.Intent)
printHtmlPart(11)
expressionOut.print(description)
printHtmlPart(12)
invokeTag('textArea','g',183,['id':("${params?.Intent}-Input"),'class':("descriptionIntent"),'style':("display:none"),'value':(description),'name':("${params.Intent}.description"),'action':("updateIntentDescription")],-1)
printHtmlPart(13)
expressionOut.print(params.Intent)
printHtmlPart(14)
expressionOut.print(params.Intent)
printHtmlPart(15)
expressionOut.print(params.Intent)
printHtmlPart(16)
expressionOut.print(params.Intent)
printHtmlPart(17)
expressionOut.print(params.Intent)
printHtmlPart(18)
expressionOut.print(params.Intent)
printHtmlPart(19)
expressionOut.print(params.Intent)
printHtmlPart(14)
expressionOut.print(params.Intent)
printHtmlPart(15)
expressionOut.print(params.Intent)
printHtmlPart(16)
expressionOut.print(params.Intent)
printHtmlPart(17)
expressionOut.print(params.Intent)
printHtmlPart(20)
expressionOut.print(params.Intent)
printHtmlPart(21)
invokeTag('submitButton','g',188,['id':("${params.Intent}-Save"),'style':("display:none;"),'class':("saveButton"),'name':("submit"),'action':("updateIntentDescription"),'value':("SAVE"),'params':([Intent: params.Intent])],-1)
printHtmlPart(22)
})
invokeTag('form','g',190,['name':("updateDescription"),'action':("updateIntentDescription"),'params':([Intent: params.Intent, selection: params.selection])],2)
printHtmlPart(23)
createTagBody(2, {->
printHtmlPart(24)
loop:{
int i = 0
for( utterance in (utterances) ) {
printHtmlPart(25)
expressionOut.print(ChatbotIntentUtterance.findAllByUtterance(utterance.key as String).size() ?: 0)
printHtmlPart(26)
expressionOut.print(utterance.key)
printHtmlPart(27)
invokeTag('checkBox','g',203,['onclick':("toggle_visibility('${utterance.key}-Select', '${utterance.key}-Select-2')"),'name':("${utterance.key}.incorrect"),'value':(utterance.value[0].incorrect)],-1)
printHtmlPart(28)
expressionOut.print(utterance.key)
printHtmlPart(29)
invokeTag('select','g',204,['id':("${utterance.key}-Select"),'class':("${utterance.value[0].incorrect}-Select"),'name':("${utterance.key}.correctIntent"),'from':(intents),'value':(utterance.value[0].correctIntent),'optionKey':("intent"),'optionValue':("intent")],-1)
printHtmlPart(30)
expressionOut.print(utterance.key)
printHtmlPart(31)
expressionOut.print(utterance.value[0].incorrect)
printHtmlPart(32)
i++
}
}
printHtmlPart(33)
createClosureForHtmlPart(34, 3)
invokeTag('submitButton','g',210,['class':("SubmitButton"),'name':("submit"),'id':("submitBtn"),'action':("updateIntents"),'params':([utteranceData: utterances, Intent: params.Intent])],3)
printHtmlPart(23)
})
invokeTag('form','g',211,['name':("updateIntent"),'action':("updateIntents"),'params':([utteranceData: utterances, Intent: params.Intent])],2)
printHtmlPart(35)
}
else {
printHtmlPart(36)
}
printHtmlPart(37)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1573222109000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
