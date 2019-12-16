import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_userManagementlist_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/userManagement/list.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',25,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(4)
createClosureForHtmlPart(5, 2)
invokeTag('javascript','g',38,[:],2)
printHtmlPart(4)
invokeTag('render','g',39,['template':("/layouts/navigation"),'model':([which:'userManagement'])],-1)
printHtmlPart(6)
if(true && (flash.message)) {
printHtmlPart(7)
expressionOut.print(flash.message)
printHtmlPart(8)
}
printHtmlPart(9)
createClosureForHtmlPart(10, 2)
invokeTag('form','g',51,['method':("post"),'action':("list"),'id':("form")],2)
printHtmlPart(11)
createTagBody(2, {->
printHtmlPart(12)
createTagBody(3, {->
printHtmlPart(13)
invokeTag('message','g',56,['code':(it.label)],-1)
printHtmlPart(14)
expressionOut.print(it.radio)
printHtmlPart(15)
})
invokeTag('radioGroup','g',57,['labels':(['GBC User','Local Account']),'values':(['LDAP','DOA']),'value':("LDAP"),'onclick':("setAuthType(this)"),'id':("authType"),'name':("authType")],3)
printHtmlPart(16)
})
invokeTag('form','g',59,['method':("post"),'action':("newUser")],2)
printHtmlPart(17)
loop:{
int i = 0
for( user in (ca.georgebrown.security.Appuser.list(sort:'firstName')) ) {
printHtmlPart(18)
createClosureForHtmlPart(19, 3)
invokeTag('link','g',79,['class':("userManagement"),'action':("edit"),'id':(user.id)],3)
printHtmlPart(20)
expressionOut.print(user.authenticationType)
printHtmlPart(20)
expressionOut.print(user.username)
printHtmlPart(21)
expressionOut.print(user.displayName)
printHtmlPart(22)
expressionOut.print(user.email)
printHtmlPart(23)
expressionOut.print(user.email)
printHtmlPart(24)
expressionOut.print(user.enabled?'YES':'NO')
printHtmlPart(20)
expressionOut.print(user.authorities*.name.join(", "))
printHtmlPart(25)
i++
}
}
printHtmlPart(26)
})
invokeTag('captureBody','sitemesh',94,[:],1)
printHtmlPart(27)
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
