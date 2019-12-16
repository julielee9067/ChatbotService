import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_intents_escalation_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/intents/_escalation.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('submitButton','g',133,['class':("editButton p-1 mb-2 pull-right"),'style':("border-color: black; width: auto"),'name':("addEscalation"),'action':("updateEscalation"),'value':("Add a New Escalation")],-1)
printHtmlPart(1)
invokeTag('textArea','g',134,['name':("newEscalation"),'rows':("1"),'cols':("30"),'class':("mr-1 mb-2 pull-right")],-1)
printHtmlPart(2)
for( escalation in (chatbotEscalations) ) {
printHtmlPart(3)
expressionOut.print(escalation.reason)
printHtmlPart(4)
expressionOut.print(escalation.reason)
printHtmlPart(5)
expressionOut.print(escalation.description)
printHtmlPart(6)
invokeTag('textArea','g',147,['id':("${escalation.reason}-Input"),'style':("padding-left:10px;height:100px;font-size:14px;display:none"),'value':(escalation.description),'name':("${escalation.reason}.description"),'action':("updateEscalation")],-1)
printHtmlPart(7)
expressionOut.print(escalation?.freq)
printHtmlPart(8)
expressionOut.print(escalation.reason)
printHtmlPart(9)
expressionOut.print(escalation.reason)
printHtmlPart(10)
expressionOut.print(escalation.reason)
printHtmlPart(11)
expressionOut.print(escalation.reason)
printHtmlPart(12)
expressionOut.print(escalation.reason)
printHtmlPart(13)
expressionOut.print(escalation.reason)
printHtmlPart(9)
expressionOut.print(escalation.reason)
printHtmlPart(10)
expressionOut.print(escalation.reason)
printHtmlPart(11)
expressionOut.print(escalation.reason)
printHtmlPart(12)
expressionOut.print(escalation.reason)
printHtmlPart(14)
createClosureForHtmlPart(15, 3)
invokeTag('link','g',152,['class':("editButton pull-left"),'name':("deleteEscalation"),'style':("color: #fc0505; margin-left: 5px"),'controller':("intents"),'action':("updateEscalation"),'value':("Delete"),'params':([escalation: escalation.reason, escalationAction: 'deleteEscalation'])],3)
printHtmlPart(16)
}
printHtmlPart(17)
invokeTag('submitButton','g',159,['class':("editButton p-1"),'style':("border-width: thin; margin-left: auto; margin-right: auto; border-color: black; margin-bottom: 2px;"),'name':("submit"),'action':("updateEscalation"),'value':("UPDATE"),'params':([escalationStats: chatbotEscalations])],-1)
printHtmlPart(18)
})
invokeTag('form','g',161,['name':("updateEscalation"),'action':("updateEscalation"),'params':([chatbotEscalations: chatbotEscalations])],1)
printHtmlPart(19)
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
