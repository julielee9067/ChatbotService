import ca.georgebrown.chatbot.ChatbotTranscript
import  ca.georgebrown.chatbot.Setting
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_chatbotService_transcripttranscript_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/transcript/transcript.gsp" }
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
invokeTag('captureMeta','sitemesh',12,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(3)
createTagBody(2, {->
createClosureForHtmlPart(4, 3)
invokeTag('captureTitle','sitemesh',14,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',14,[:],2)
printHtmlPart(5)
})
invokeTag('captureHead','sitemesh',248,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('render','g',255,['template':("/layouts/navigation"),'model':([which:'transcripts'])],-1)
printHtmlPart(6)
if(true && (!params.userId && !params.token)) {
printHtmlPart(7)
}
else if(true && (checkUserId == false && params.userId)) {
printHtmlPart(8)
expressionOut.print(params.userId)
printHtmlPart(9)
}
else if(true && (checkChatToken == false && params.token)) {
printHtmlPart(10)
expressionOut.print(params.token)
printHtmlPart(11)
}
else {
printHtmlPart(12)
createTagBody(3, {->
printHtmlPart(13)
expressionOut.print(params.userId)
printHtmlPart(14)
expressionOut.print(formatDate(format: 'MMM dd, yyyy (HH:mm:ss)', date: dateTranscript))
printHtmlPart(15)
})
invokeTag('form','g',277,['name':("transcriptForm"),'url':(" transcript?userId=${params.userId}")],3)
printHtmlPart(16)
}
printHtmlPart(17)
if(true && (params.tokens && params.userId && params.token && checkChatToken == true && checkUserId == true)) {
printHtmlPart(16)
}
else {
printHtmlPart(18)
}
printHtmlPart(16)
loop:{
int i = 0
for( transcript in (transcriptList) ) {
printHtmlPart(12)
if(true && ((transcript.chatMessage in mismatch.collect{it.utterance}.unique()))) {
printHtmlPart(19)
if(true && (transcript.speakerBubble == 'user')) {
printHtmlPart(20)
expressionOut.print(formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage))
printHtmlPart(21)
expressionOut.print(transcript.speakerBubble)
printHtmlPart(22)
expressionOut.print(transcript.chatMessage)
printHtmlPart(23)
}
else {
printHtmlPart(24)
expressionOut.print(transcript.speakerBubble)
printHtmlPart(25)
expressionOut.print(transcript.chatMessage)
printHtmlPart(26)
expressionOut.print(formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage))
printHtmlPart(27)
}
printHtmlPart(28)
expressionOut.print(mismatch.find{it.utterance ==~ transcript.chatMessage}?.correctIntent)
printHtmlPart(29)
}
else if(true && (unrecognized.contains(transcript.chatMessage))) {
printHtmlPart(19)
if(true && (transcript.speakerBubble == 'user')) {
printHtmlPart(20)
expressionOut.print(formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage))
printHtmlPart(21)
expressionOut.print(transcript.speakerBubble)
printHtmlPart(25)
expressionOut.print(transcript.chatMessage)
printHtmlPart(23)
}
printHtmlPart(30)
createClosureForHtmlPart(31, 4)
invokeTag('link','g',314,['type':("button"),'action':("resolveEscalationFromTranscript"),'controller':("transcript"),'name':("resolved"),'id':("resolveUnrecognized"),'params':([utterance: transcript.chatMessage, token: transcript.chatToken])],4)
printHtmlPart(32)
}
else if(true && (insultList.contains(transcript.chatMessage))) {
printHtmlPart(19)
if(true && (transcript.speakerBubble == 'user')) {
printHtmlPart(20)
expressionOut.print(formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage))
printHtmlPart(21)
expressionOut.print(transcript.speakerBubble)
printHtmlPart(33)
expressionOut.print(transcript.chatMessage)
printHtmlPart(23)
}
printHtmlPart(30)
createClosureForHtmlPart(31, 4)
invokeTag('link','g',326,['type':("button"),'class':("correction"),'action':("resolveEscalationFromTranscript"),'controller':("transcript"),'name':("resolved"),'id':("resolveUnrecognized"),'params':([utterance: transcript.chatMessage, token: transcript.chatToken])],4)
printHtmlPart(34)
}
else if(true && (escalated.contains(transcript.chatMessage))) {
printHtmlPart(19)
if(true && (transcript.speakerBubble == 'user')) {
printHtmlPart(35)
expressionOut.print(formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage))
printHtmlPart(21)
expressionOut.print(transcript.speakerBubble)
printHtmlPart(36)
expressionOut.print(transcript.chatMessage)
printHtmlPart(23)
}
printHtmlPart(30)
createClosureForHtmlPart(31, 4)
invokeTag('link','g',338,['type':("button"),'class':("correction"),'action':("resolveEscalationFromTranscript"),'controller':("transcript"),'name':("resolved"),'id':("resolveUnrecognized"),'params':([utterance: transcript.chatMessage, token: transcript.chatToken])],4)
printHtmlPart(37)
}
else {
printHtmlPart(19)
if(true && (transcript.speakerBubble == 'user')) {
printHtmlPart(38)
expressionOut.print(formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage))
printHtmlPart(39)
expressionOut.print(transcript.speakerBubble)
printHtmlPart(40)
expressionOut.print(transcript.chatMessage)
printHtmlPart(23)
}
else if(true && (transcript.speakerBubble == 'Error' && !transcript.resolved)) {
printHtmlPart(41)
expressionOut.print(transcript.chatMessage)
printHtmlPart(42)
expressionOut.print(formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage))
printHtmlPart(43)
createClosureForHtmlPart(31, 5)
invokeTag('link','g',353,['type':("button"),'class':("timeDisplay"),'style':("margin-top: auto; margin-left: 10px"),'action':("resolveEscalationFromTranscript"),'controller':("transcript"),'name':("resolved"),'id':("resolveUnrecognized"),'params':([utterance: transcript.chatMessage, token: transcript.chatToken])],5)
printHtmlPart(44)
}
else if(true && (transcript.speakerBubble)) {
printHtmlPart(45)
expressionOut.print(transcript.chatMessage)
printHtmlPart(42)
expressionOut.print(formatDate(format: 'HH:mm:ss', date: transcript.timeSentMessage))
printHtmlPart(23)
}
printHtmlPart(12)
}
printHtmlPart(16)
i++
}
}
printHtmlPart(46)
createTagBody(2, {->
printHtmlPart(47)
invokeTag('textField','g',371,['id':("userIdInput"),'name':("userId"),'style':("height: 30px"),'value':(params.userId),'action':("transcript")],-1)
printHtmlPart(12)
invokeTag('submitButton','g',372,['class':("button"),'name':("search-userId"),'action':("transcript"),'value':("Go")],-1)
printHtmlPart(48)
})
invokeTag('form','g',372,['name':("SearchByUserId"),'url':(" transcript")],2)
printHtmlPart(49)
createTagBody(2, {->
printHtmlPart(50)
invokeTag('textField','g',379,['id':("tokenInput"),'name':("token"),'style':("height: 30px"),'value':(params.token),'action':("transcript")],-1)
printHtmlPart(12)
invokeTag('submitButton','g',380,['class':("button"),'name':("search-token"),'action':("transcript"),'value':("Go")],-1)
printHtmlPart(48)
})
invokeTag('form','g',380,['name':("SearchByToken"),'url':(" transcript")],2)
printHtmlPart(51)
invokeTag('include','g',385,['controller':("transcript"),'action':("browseTranscripts")],-1)
printHtmlPart(52)
})
invokeTag('captureBody','sitemesh',386,['class':("container-fluid")],1)
printHtmlPart(53)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1575645688000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
