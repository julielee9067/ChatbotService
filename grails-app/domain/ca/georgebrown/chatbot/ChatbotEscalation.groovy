package ca.georgebrown.chatbot

class ChatbotEscalation {

    Date lastTimeSent
    String reason
    String description
    int freq

    static constraints = {
        lastTimeSent nullable: true
        description nullable: true
        reason nullable: false
        freq defaultValue: 0
    }

    static mapping = {
        version false
    }
}
