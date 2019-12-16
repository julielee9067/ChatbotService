import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_settingslist_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/settings/list.gsp" }
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
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(3)
invokeTag('render','g',9,['template':("/layouts/navigation"),'model':([which: 'config'])],-1)
printHtmlPart(5)
if(true && (flash.message)) {
printHtmlPart(6)
expressionOut.print(flash.message)
printHtmlPart(7)
}
printHtmlPart(8)
loop:{
int i = 0
for( settingsInstance in (settingsInstanceList.sort { [it.seq] }) ) {
printHtmlPart(9)
createTagBody(3, {->
expressionOut.print(fieldValue(bean: settingsInstance, field: "code"))
})
invokeTag('link','g',36,['class':("edit"),'action':("edit"),'id':(settingsInstance.id)],3)
printHtmlPart(10)
expressionOut.print(fieldValue(bean: settingsInstance, field: "description"))
printHtmlPart(10)
expressionOut.print(fieldValue(bean: settingsInstance, field: "type"))
printHtmlPart(10)
expressionOut.print(fieldValue(bean: settingsInstance, field: "value"))
printHtmlPart(11)
i++
}
}
printHtmlPart(12)
})
invokeTag('captureBody','sitemesh',51,[:],1)
printHtmlPart(13)
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
