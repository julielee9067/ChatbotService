import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_intents_mainStats_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/intents/_mainStats.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
for( category in (catRankings) ) {
printHtmlPart(1)
expressionOut.print(category.category)
printHtmlPart(2)
expressionOut.print(category.freq)
printHtmlPart(3)
}
printHtmlPart(4)
for( intent in (popular) ) {
printHtmlPart(1)
expressionOut.print(intent.intent)
printHtmlPart(2)
expressionOut.print(intent.freq)
printHtmlPart(3)
}
printHtmlPart(5)
expressionOut.print(yesterday)
printHtmlPart(6)
expressionOut.print(totalConversations)
printHtmlPart(7)
for( escalation in (escalationRankings) ) {
printHtmlPart(1)
expressionOut.print(escalation.reason)
printHtmlPart(2)
expressionOut.print(escalation.freq)
printHtmlPart(3)
}
printHtmlPart(8)
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
