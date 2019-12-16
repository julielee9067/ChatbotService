import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_userManagementwsAcct_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/userManagement/wsAcct.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',5,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',5,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',6,[:],1)
printHtmlPart(3)
createTagBody(1, {->
printHtmlPart(3)
invokeTag('render','g',8,['template':("/layouts/navigation"),'model':([which:'userManagement'])],-1)
printHtmlPart(4)
expressionOut.print(user.displayName)
printHtmlPart(5)
if(true && (flash.message)) {
printHtmlPart(6)
expressionOut.print(flash.message)
printHtmlPart(7)
}
printHtmlPart(1)
createTagBody(2, {->
printHtmlPart(8)
createTagBody(3, {->
printHtmlPart(9)
if(true && (error in org.springframework.validation.FieldError)) {
printHtmlPart(10)
expressionOut.print(error.field)
printHtmlPart(11)
}
printHtmlPart(12)
invokeTag('message','g',21,['error':(error)],-1)
printHtmlPart(13)
})
invokeTag('eachError','g',22,['bean':(user),'var':("error")],3)
printHtmlPart(14)
})
invokeTag('hasErrors','g',24,['bean':(user)],2)
printHtmlPart(15)
createTagBody(2, {->
printHtmlPart(16)
invokeTag('hiddenField','g',27,['name':("id"),'value':(user?.id)],-1)
printHtmlPart(16)
invokeTag('hiddenField','g',28,['name':("version"),'value':(user?.version)],-1)
printHtmlPart(17)
invokeTag('render','g',30,['template':("wsAcctForm")],-1)
printHtmlPart(18)
invokeTag('actionSubmit','g',34,['class':("save btn btn-primary"),'action':("wsAcctUpdate"),'value':("Update")],-1)
printHtmlPart(19)
})
invokeTag('form','g',36,['method':("post")],2)
printHtmlPart(20)
})
invokeTag('captureBody','sitemesh',39,[:],1)
printHtmlPart(21)
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
