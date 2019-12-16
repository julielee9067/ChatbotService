package ca.georgebrown.chatbot

import com.sun.tools.internal.xjc.model.CDefaultValue

class ChatbotIntents {

    String intent
    String description
    String category
    boolean incorrect
    String correctIntent
    int freq

    static constraints = {
        intent(maxSize:4000)
        description nullable:true
        category nullable: true
        freq defaultValue: 0
    }

    static mapping = {
        version false
        incorrect defaultValue: 'false'
    }
}
