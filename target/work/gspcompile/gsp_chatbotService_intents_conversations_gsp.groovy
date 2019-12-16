import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_intents_conversations_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/intents/_conversations.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
loop:{
int i = 0
for( utterance in (utterances) ) {
printHtmlPart(1)
expressionOut.print(i)
printHtmlPart(2)
expressionOut.print(i)
printHtmlPart(3)
expressionOut.print(utterance.value[0].utterance)
printHtmlPart(4)
expressionOut.print(i)
printHtmlPart(5)
for( conv in (utterance.value) ) {
printHtmlPart(6)
createTagBody(3, {->
expressionOut.print(formatDate(format: 'MMM dd, yyyy (HH:mm:ss)', date: conv.timeSentMessage))
})
invokeTag('link','g',24,['controller':("transcript"),'action':("transcript"),'params':([token: conv.chatToken])],3)
printHtmlPart(7)
}
printHtmlPart(8)
i++
}
}
printHtmlPart(9)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574789228000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
