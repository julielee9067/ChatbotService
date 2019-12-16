import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_intents_browse_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/intents/_browse.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(params.selection.capitalize())
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
loop:{
int i = 0
for( utterance in (utterances) ) {
printHtmlPart(3)
expressionOut.print(utterance.value.size())
printHtmlPart(4)
expressionOut.print(formatDate(format: 'MMM-dd-yyyy (HH:mm:ss)', date: utterance.value[0]?.timeSentMessage))
printHtmlPart(5)
expressionOut.print(utterance.value[0]?.utterance)
printHtmlPart(6)
expressionOut.print(utterance.value[0]?.intent)
printHtmlPart(7)
invokeTag('checkBox','g',28,['onclick':("toggle_visibility('${utterance?.value[0]?.utterance}-Select', '${utterance?.value[0]?.utterance}-Select-2')"),'name':("${utterance?.value[0]?.utterance}.incorrect"),'value':(utterance?.value[0]?.incorrect)],-1)
printHtmlPart(8)
expressionOut.print(utterance.value[0]?.utterance)
printHtmlPart(9)
invokeTag('select','g',30,['id':("${utterance.value[0]?.utterance}-Select"),'class':("selectCI ${utterance.value[0]?.incorrect}-Select"),'name':("${utterance.value[0]?.utterance}.correct_intent"),'from':(intents),'value':(utterance.value[0]?.correctIntent),'optionKey':("intent"),'optionValue':("intent")],-1)
printHtmlPart(10)
expressionOut.print(utterance.value[0]?.utterance)
printHtmlPart(11)
expressionOut.print(utterance.value[0]?.incorrect)
printHtmlPart(12)
i++
}
}
printHtmlPart(13)
invokeTag('submitButton','g',36,['class':("SubmitButton"),'onclick':("get_form_information('${utterances}')"),'name':("submit"),'action':("updateIntents"),'value':("Update"),'params':([utteranceData: utterances, selection: params.selection])],-1)
printHtmlPart(14)
})
invokeTag('form','g',37,['name':("updateIntent"),'action':("updateIntents"),'params':([utteranceData: utterances, browse: params.browse])],1)
printHtmlPart(15)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1575649216000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
