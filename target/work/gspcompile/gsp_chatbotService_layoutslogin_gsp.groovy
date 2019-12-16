import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_layoutslogin_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/layouts/login.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',12,['gsp_sm_xmlClosingForEmptyTag':("/"),'http-equiv':("Content-Type"),'content':("text/html; charset=utf-8")],-1)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',13,['gsp_sm_xmlClosingForEmptyTag':("/"),'http-equiv':("X-UA-Compatible"),'content':("IE=edge,chrome=1")],-1)
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',14,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("viewport"),'content':("width=device-width, initial-scale=1.0")],-1)
printHtmlPart(3)
expressionOut.print(resource(dir: 'images', file: 'favicon.ico'))
printHtmlPart(4)
expressionOut.print(resource(dir: 'css', file: 'mobile.css'))
printHtmlPart(5)
createTagBody(2, {->
createTagBody(3, {->
invokeTag('layoutTitle','g',17,['default':("Applicant Registration")],-1)
})
invokeTag('captureTitle','sitemesh',17,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',17,[:],2)
printHtmlPart(1)
invokeTag('javascript','g',19,['library':("jquery-ui")],-1)
printHtmlPart(1)
invokeTag('stylesheet','asset',21,['src':("application.css")],-1)
printHtmlPart(2)
invokeTag('stylesheet','asset',22,['src':("spring-security-ui.css")],-1)
printHtmlPart(2)
invokeTag('stylesheet','asset',23,['src':("security-ui.css")],-1)
printHtmlPart(1)
invokeTag('layoutHead','g',25,[:],-1)
printHtmlPart(6)
})
invokeTag('captureHead','sitemesh',27,[:],1)
printHtmlPart(6)
createTagBody(1, {->
printHtmlPart(7)
if(true && (!authChatbot)) {
printHtmlPart(8)
invokeTag('render','g',33,['template':("/layouts/banner")],-1)
printHtmlPart(2)
}
printHtmlPart(9)
invokeTag('layoutBody','g',37,[:],-1)
printHtmlPart(10)
if(true && (!authChatbot)) {
printHtmlPart(2)
invokeTag('render','g',40,['template':("/layouts/footer")],-1)
printHtmlPart(11)
}
printHtmlPart(6)
})
invokeTag('captureBody','sitemesh',43,[:],1)
printHtmlPart(12)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1573222110000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
