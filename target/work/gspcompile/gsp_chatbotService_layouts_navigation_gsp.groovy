import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_layouts_navigation_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/_navigation.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
createClosureForHtmlPart(0, 1)
invokeTag('javascript','g',18,[:],1)
printHtmlPart(1)
if(true && (appuser)) {
printHtmlPart(2)
createTagBody(2, {->
printHtmlPart(3)
if(true && (which == 'config')) {
printHtmlPart(4)
}
printHtmlPart(5)
createClosureForHtmlPart(6, 3)
invokeTag('link','g',28,['controller':("settings"),'action':("list")],3)
printHtmlPart(7)
})
invokeTag('ifAnyGranted','sec',31,['roles':("ROLE_APP_MANAGER,ROLE_DEVELOPER")],2)
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(9)
if(true && (which == 'userManagement')) {
printHtmlPart(4)
}
printHtmlPart(10)
createClosureForHtmlPart(11, 3)
invokeTag('link','g',35,['controller':("userManagement"),'action':("list")],3)
printHtmlPart(12)
})
invokeTag('ifAnyGranted','sec',37,['roles':("ROLE_USER_ADMIN")],2)
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(9)
if(true && (which == 'documentation')) {
printHtmlPart(4)
}
printHtmlPart(10)
createClosureForHtmlPart(13, 3)
invokeTag('link','g',41,['controller':("api"),'action':("index")],3)
printHtmlPart(12)
})
invokeTag('ifAnyGranted','sec',43,['roles':("ROLE_APP_MANAGER,ROLE_DEVELOPER")],2)
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(9)
if(true && (which == 'transcripts')) {
printHtmlPart(4)
}
printHtmlPart(10)
createClosureForHtmlPart(14, 3)
invokeTag('link','g',47,['controller':("transcript"),'action':("transcript")],3)
printHtmlPart(12)
})
invokeTag('ifAnyGranted','sec',49,['roles':("ROLE_APP_MANAGER,ROLE_DEVELOPER,ROLE_USER_ADMIN")],2)
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(9)
if(true && (which == 'intents')) {
printHtmlPart(4)
}
printHtmlPart(10)
createClosureForHtmlPart(15, 3)
invokeTag('link','g',53,['controller':("intents"),'action':("intents")],3)
printHtmlPart(12)
})
invokeTag('ifAnyGranted','sec',55,['roles':("ROLE_APP_MANAGER,ROLE_DEVELOPER,ROLE_USER_ADMIN")],2)
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(9)
if(true && (which == 'errorsEscalation')) {
printHtmlPart(4)
}
printHtmlPart(10)
createClosureForHtmlPart(16, 3)
invokeTag('link','g',59,['controller':("errorsEscalation"),'action':("errorsEscalation")],3)
printHtmlPart(12)
})
invokeTag('ifAnyGranted','sec',61,['roles':("ROLE_APP_MANAGER,ROLE_DEVELOPER,ROLE_USER_ADMIN")],2)
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(9)
if(true && (which == 'help')) {
printHtmlPart(4)
}
printHtmlPart(10)
createClosureForHtmlPart(17, 3)
invokeTag('link','g',65,['controller':("help"),'action':("index")],3)
printHtmlPart(12)
})
invokeTag('ifAnyGranted','sec',67,['roles':("ROLE_APP_MANAGER,ROLE_DEVELOPER,ROLE_USER_ADMIN")],2)
printHtmlPart(18)
}
printHtmlPart(19)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1573223794000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
