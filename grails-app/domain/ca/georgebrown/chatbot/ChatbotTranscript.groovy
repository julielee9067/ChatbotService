package ca.georgebrown.chatbot


class ChatbotTranscript {

    String chatToken
    Integer userId
    Integer orderBubble
    String speakerBubble
    String chatMessage
    Boolean resolved
    Date timeSentMessage

    static constraints = {
        chatMessage(maxSize: 4000)
        resolved nullable: true
        timeSentMessage nullable: true
    }

    static mapping = {
        version false
        resolved defaultValue: 'false'
    }
}