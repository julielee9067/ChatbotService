import ca.georgebrown.security.Appuser
import  ca.georgebrown.security.Approle
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_userManagement_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/userManagement/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
if(true && (user.authenticationType == ca.georgebrown.security.Appuser.AUTHENTICATION_TYPE.DAO)) {
printHtmlPart(1)
expressionOut.print(user?.username)
printHtmlPart(2)
}
else {
printHtmlPart(3)
expressionOut.print(user?.username)
printHtmlPart(4)
}
printHtmlPart(5)
if(true && (user.authenticationType == ca.georgebrown.security.Appuser.AUTHENTICATION_TYPE.DAO)) {
printHtmlPart(6)
expressionOut.print(user?.firstName)
printHtmlPart(7)
expressionOut.print(user?.lastName)
printHtmlPart(8)
}
else {
printHtmlPart(9)
expressionOut.print(user?.displayName)
printHtmlPart(10)
}
printHtmlPart(11)
if(true && (user.authenticationType == ca.georgebrown.security.Appuser.AUTHENTICATION_TYPE.DAO)) {
printHtmlPart(12)
expressionOut.print(user?.email)
printHtmlPart(13)
}
else {
printHtmlPart(14)
expressionOut.print(user?.email)
printHtmlPart(15)
expressionOut.print(user?.email)
printHtmlPart(16)
}
printHtmlPart(17)
createClosureForHtmlPart(18, 1)
invokeTag('checkBox','g',47,['name':("enabled"),'value':(user?.enabled)],1)
printHtmlPart(19)
if(true && (user.authenticationType == ca.georgebrown.security.Appuser.AUTHENTICATION_TYPE.DAO)) {
printHtmlPart(20)
expressionOut.print(Appuser.NO_CHG_PASSWORD)
printHtmlPart(21)
}
printHtmlPart(22)
for( role in (Approle.list()) ) {
printHtmlPart(3)
invokeTag('checkBox','g',60,['name':("role.${role.id}"),'value':(role in user.authorities)],-1)
printHtmlPart(23)
expressionOut.print(role.name)
printHtmlPart(24)
}
printHtmlPart(25)
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
