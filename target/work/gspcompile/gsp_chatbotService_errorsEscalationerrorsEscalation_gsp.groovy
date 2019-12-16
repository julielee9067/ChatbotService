import ca.georgebrown.chatbot.ChatbotTranscript
import  ca.georgebrown.chatbot.Setting
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_errorsEscalationerrorsEscalation_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/errorsEscalation/errorsEscalation.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
invokeTag('set','g',8,['var':("settingService"),'bean':("settingService")],-1)
printHtmlPart(2)
createTagBody(1, {->
printHtmlPart(3)
invokeTag('captureMeta','sitemesh',12,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(4)
createTagBody(2, {->
createClosureForHtmlPart(5, 3)
invokeTag('captureTitle','sitemesh',16,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',16,[:],2)
printHtmlPart(6)
})
invokeTag('captureHead','sitemesh',168,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('render','g',177,['template':("/layouts/navigation"),'model':([which:'errorsEscalation'])],-1)
printHtmlPart(7)
for( error in (errorsList) ) {
printHtmlPart(8)
expressionOut.print(ChatbotTranscript.findByChatTokenAndOrderBubble(error.chatToken, error.orderBubble-1).chatMessage)
printHtmlPart(9)
createTagBody(3, {->
printHtmlPart(10)
expressionOut.print(formatDate(format: 'MMM dd, yyyy (HH:mm:ss)', date: error?.timeSentMessage))
printHtmlPart(9)
})
invokeTag('link','g',197,['action':("transcript"),'controller':("transcript"),'params':([token: error.chatToken])],3)
printHtmlPart(11)
expressionOut.print(ChatbotTranscript.findByChatTokenAndOrderBubble(error.chatToken, error.orderBubble).id)
printHtmlPart(12)
}
printHtmlPart(13)
createTagBody(2, {->
printHtmlPart(14)
invokeTag('submitButton','g',217,['name':("Resolve"),'action':("resolve"),'class':("btn btn-sm btn-primary")],-1)
printHtmlPart(15)
})
invokeTag('form','g',218,['name':("Resolve"),'action':("resolve")],2)
printHtmlPart(16)
for( error in (unrecognized) ) {
printHtmlPart(17)
createTagBody(3, {->
printHtmlPart(18)
expressionOut.print(error.utterance)
printHtmlPart(19)
})
invokeTag('link','g',246,['action':("transcript"),'controller':("transcript"),'params':([token: error.chatToken])],3)
printHtmlPart(20)
expressionOut.print(error.utterance)
printHtmlPart(12)
}
printHtmlPart(21)
createTagBody(2, {->
printHtmlPart(22)
invokeTag('submitButton','g',266,['name':("Resolve"),'action':("resolveMismatch"),'class':("btn btn-sm btn-primary")],-1)
printHtmlPart(15)
})
invokeTag('form','g',267,['name':("Resolve"),'action':("resolveUnrecognized"),'params':([])],2)
printHtmlPart(23)
for( mismatched in (mismatch) ) {
printHtmlPart(24)
expressionOut.print(mismatched.utterance)
printHtmlPart(25)
expressionOut.print(mismatched.correctIntent)
printHtmlPart(26)
expressionOut.print(mismatched.utterance)
printHtmlPart(27)
expressionOut.print(mismatched.correctIntent)
printHtmlPart(28)
}
printHtmlPart(29)
createTagBody(2, {->
printHtmlPart(30)
invokeTag('submitButton','g',315,['name':("Resolve"),'action':("resolveMismatch"),'class':("btn btn-sm btn-primary")],-1)
printHtmlPart(15)
})
invokeTag('form','g',316,['name':("Resolve"),'action':("resolveMismatch"),'params':([])],2)
printHtmlPart(31)
for( escalation in (escalationStats) ) {
printHtmlPart(32)
expressionOut.print(escalation.reason)
printHtmlPart(33)
expressionOut.print(escalation.freq)
printHtmlPart(34)
}
printHtmlPart(35)
expressionOut.print(escalationStat.percentage)
printHtmlPart(36)
expressionOut.print(escalationStat.numIntent.toInteger())
printHtmlPart(37)
expressionOut.print(escalationStat.numEscalation.toInteger())
printHtmlPart(38)
})
invokeTag('captureBody','sitemesh',358,['class':("container-fluid")],1)
printHtmlPart(39)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1575391786000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
