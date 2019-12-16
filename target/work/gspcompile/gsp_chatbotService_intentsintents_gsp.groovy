import ca.georgebrown.chatbot.Setting
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_intentsintents_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/intents/intents.gsp" }
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
invokeTag('captureHead','sitemesh',280,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('render','g',286,['template':("/layouts/navigation"),'model':([which:'intents'])],-1)
printHtmlPart(6)
createTagBody(2, {->
printHtmlPart(7)
if(true && (params.selection == 'main')) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
invokeTag('submitButton','g',339,['class':("BrowseButton"),'id':("main"),'name':("browse-date"),'action':("intents"),'value':("Main Stats")],-1)
printHtmlPart(11)
})
invokeTag('form','g',339,['name':("MainMenu"),'url':(" intents?selection=main")],2)
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(7)
if(true && (params.selection == 'date')) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
invokeTag('submitButton','g',345,['class':("BrowseButton"),'id':("date"),'name':("browse-date"),'action':("intents"),'value':("Browse By Date")],-1)
printHtmlPart(11)
})
invokeTag('form','g',345,['name':("BrowseByDate"),'url':(" intents?selection=date")],2)
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(7)
if(true && (params.selection == 'popular')) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
invokeTag('submitButton','g',351,['class':("BrowseButton"),'name':("browse-popular"),'action':("intents"),'value':("Browse By Popular")],-1)
printHtmlPart(11)
})
invokeTag('form','g',351,['name':("BrowseByPopular"),'url':(" intents?selection=popular")],2)
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(7)
if(true && (params.selection == 'data')) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
invokeTag('submitButton','g',357,['class':("BrowseButton"),'id':("data"),'name':("intent-data"),'action':("intents"),'value':("See Intent Data")],-1)
printHtmlPart(11)
})
invokeTag('form','g',357,['name':("SeeIntentData"),'url':(" intents?selection=data")],2)
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(7)
if(true && (params.selection == 'escalation')) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
invokeTag('submitButton','g',363,['class':("BrowseButton"),'id':("escalation"),'name':("intent-data"),'action':("intents"),'value':("See Escalation Data")],-1)
printHtmlPart(11)
})
invokeTag('form','g',363,['name':("SeeEscalationData"),'url':(" intents?selection=escalation")],2)
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(7)
if(true && (params.selection == 'manageCategory')) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
invokeTag('submitButton','g',369,['class':("BrowseButton"),'id':("manage-category"),'name':("intents"),'action':("intents"),'value':("Manage Intent Category")],-1)
printHtmlPart(11)
})
invokeTag('form','g',369,['name':("ManageIntentCategory"),'url':(" intents?selection=manageCategory")],2)
printHtmlPart(12)
createTagBody(2, {->
printHtmlPart(7)
if(true && (params.selection == 'search')) {
printHtmlPart(8)
}
else {
printHtmlPart(9)
}
printHtmlPart(10)
invokeTag('submitButton','g',375,['class':("BrowseButton"),'id':("search"),'name':("intent-search"),'action':("intents"),'value':("Search")],-1)
printHtmlPart(11)
})
invokeTag('form','g',375,['name':("SeeSearch"),'url':(" intents?selection=search")],2)
printHtmlPart(13)
if(true && (params.selection == 'main')) {
printHtmlPart(14)
invokeTag('include','g',384,['controller':("intents"),'action':("viewMainStats"),'params':([Intent: params.Intent, selection: params.selection])],-1)
printHtmlPart(15)
}
printHtmlPart(15)
if(true && (params.selection == 'search')) {
printHtmlPart(14)
invokeTag('include','g',387,['controller':("intents"),'action':("viewIntentInformation"),'params':([Intent: params.Intent, selection: params.selection])],-1)
printHtmlPart(14)
invokeTag('include','g',388,['controller':("intents"),'action':("viewConversations"),'params':([Intent:params.Intent, selection: params.selection])],-1)
printHtmlPart(15)
}
printHtmlPart(15)
if(true && (params.selection == 'date' || params.selection == 'popular')) {
printHtmlPart(14)
invokeTag('include','g',391,['controller':("intents"),'action':("browse"),'params':([selection: params.selection])],-1)
printHtmlPart(15)
}
printHtmlPart(15)
if(true && (params.selection == 'data')) {
printHtmlPart(14)
invokeTag('include','g',394,['controller':("intents"),'action':("intentData"),'params':([selection: params.selection])],-1)
printHtmlPart(15)
}
printHtmlPart(15)
if(true && (params.selection == 'escalation')) {
printHtmlPart(14)
invokeTag('include','g',396,['controller':("intents"),'action':("escalationData")],-1)
printHtmlPart(15)
}
printHtmlPart(15)
if(true && (params.selection == 'manageCategory')) {
printHtmlPart(14)
invokeTag('include','g',399,['controller':("intents"),'action':("manageCategory")],-1)
printHtmlPart(15)
}
printHtmlPart(16)
})
invokeTag('captureBody','sitemesh',400,['class':("container-fluid"),'onload':("shade_active(${params.selection})")],1)
printHtmlPart(17)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1574866256000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
