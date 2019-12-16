import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_transcript_browseTranscripts_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/transcript/_browseTranscripts.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
for( conversation in (conversations) ) {
printHtmlPart(2)
createTagBody(2, {->
printHtmlPart(3)
expressionOut.print(conversation?.value.get(0).userId)
printHtmlPart(4)
})
invokeTag('link','g',238,['action':("transcript"),'params':([token: conversation?.key, 'search-userId': 'Go', 'userId': conversation?.value.get(0).userId])],2)
printHtmlPart(5)
if(true && (params.'search-userId' == 'Go')) {
printHtmlPart(3)
createTagBody(3, {->
printHtmlPart(6)
expressionOut.print(formatDate(format: 'MMM-dd-yyyy(HH:mm:ss)', date: conversation?.value.get(0).timeSentMessage))
printHtmlPart(3)
})
invokeTag('link','g',245,['action':("transcript"),'params':([token: conversation?.key, 'search-userId': 'Go', 'userId': conversation?.value.get(0).userId])],3)
printHtmlPart(4)
}
else {
printHtmlPart(3)
createTagBody(3, {->
printHtmlPart(6)
expressionOut.print(formatDate(format: 'MMM-dd-yyyy(HH:mm:ss)', date: conversation?.value.get(0).timeSentMessage))
printHtmlPart(3)
})
invokeTag('link','g',250,['action':("transcript"),'params':([token: conversation?.key])],3)
printHtmlPart(4)
}
printHtmlPart(7)
if(true && (conversation?.value?.contains("escalate"))) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
if(true && (conversation?.value?.contains("error"))) {
printHtmlPart(11)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
if(true && (conversation?.value?.contains("mismatch"))) {
printHtmlPart(12)
}
else {
printHtmlPart(9)
}
printHtmlPart(13)
}
printHtmlPart(14)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574861392000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
