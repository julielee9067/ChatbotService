import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_intents_manageCategory_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/intents/_manageCategory.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('submitButton','g',58,['class':("editButton p-1 mb-2 pull-right"),'style':("border-color: black; width: auto"),'name':("addCategory"),'action':("updateIntentCategory"),'value':("Add a New Category"),'params':([categoryList: chatbotIntentTypes, categoryAction: 'addCategory'])],-1)
printHtmlPart(1)
invokeTag('textArea','g',58,['name':("newCategory"),'rows':("1"),'cols':("30"),'class':("mr-1 mb-2 pull-right")],-1)
printHtmlPart(2)
for( category in (chatbotIntentTypes) ) {
printHtmlPart(3)
expressionOut.print(category.category)
printHtmlPart(4)
expressionOut.print(category.category)
printHtmlPart(5)
expressionOut.print(category.description)
printHtmlPart(6)
invokeTag('textArea','g',69,['id':("${category.category}-Input"),'style':("padding-left:10px;height:100px;font-size:14px;display:none"),'value':(category.description),'name':("${category.category}.description"),'action':("updateIntentCategory")],-1)
printHtmlPart(7)
expressionOut.print(category.category)
printHtmlPart(8)
expressionOut.print(category.category)
printHtmlPart(9)
expressionOut.print(category.category)
printHtmlPart(10)
expressionOut.print(category.category)
printHtmlPart(11)
expressionOut.print(category.category)
printHtmlPart(12)
expressionOut.print(category.category)
printHtmlPart(8)
expressionOut.print(category.category)
printHtmlPart(9)
expressionOut.print(category.category)
printHtmlPart(10)
expressionOut.print(category.category)
printHtmlPart(11)
expressionOut.print(category.category)
printHtmlPart(13)
if(true && (category.category != "escalation")) {
printHtmlPart(14)
createClosureForHtmlPart(15, 4)
invokeTag('link','g',74,['class':("editButton pull-left"),'name':("deleteCategory"),'style':("color: #fc0505; margin-left: 5px"),'controller':("intents"),'action':("updateIntentCategory"),'value':("Delete"),'params':([category: category.category, categoryAction: 'deleteCategory'])],4)
printHtmlPart(16)
}
printHtmlPart(17)
}
printHtmlPart(18)
invokeTag('submitButton','g',83,['class':("editButton p-1"),'style':("border-width: thin; margin-left: auto; margin-right: auto; border-color: black; margin-bottom: 2px;"),'name':("submit"),'action':("updateIntentCategory"),'value':("UPDATE"),'params':([categoryList: chatbotIntentTypes])],-1)
printHtmlPart(19)
})
invokeTag('form','g',83,['name':("updateIntentCategory"),'action':("updateIntentCategory"),'params':([categoryList: chatbotIntentTypes])],1)
printHtmlPart(20)
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
