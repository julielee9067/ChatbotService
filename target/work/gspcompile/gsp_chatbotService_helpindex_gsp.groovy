import ca.georgebrown.chatbot.ChatbotTranscript
import  ca.georgebrown.chatbot.Setting
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_helpindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/help/index.gsp" }
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
invokeTag('captureMeta','sitemesh',13,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(3)
createTagBody(2, {->
createClosureForHtmlPart(4, 3)
invokeTag('captureTitle','sitemesh',14,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',14,[:],2)
printHtmlPart(5)
})
invokeTag('captureHead','sitemesh',44,[:],1)
printHtmlPart(6)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('render','g',52,['template':("/layouts/navigation"),'model':([which:'help'])],-1)
printHtmlPart(7)
for( uploadedFile in (fileList) ) {
printHtmlPart(8)
expressionOut.print(uploadedFile.originalFilename)
printHtmlPart(9)
createClosureForHtmlPart(10, 3)
invokeTag('link','g',66,['controller':("help"),'action':("downloadFile"),'id':(uploadedFile.id),'target':("_blank"),'class':("btn btn-sm btn-primary"),'style':("border-color: white; border-width: thin")],3)
printHtmlPart(11)
}
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(13)
expressionOut.print(grailsApplication.config.file.upload.directory)
printHtmlPart(14)
})
invokeTag('form','g',79,['method':("POST"),'controller':("help"),'action':("uploadFile"),'enctype':("multipart/form-data")],2)
printHtmlPart(15)
})
invokeTag('captureBody','sitemesh',80,['class':("container-fluid")],1)
printHtmlPart(16)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1575996895000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
