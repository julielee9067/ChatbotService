package ca.georgebrown.chatbot

class ChatbotIntentTypes {

    String category
    String description
    int freq

    static constraints = {
        category nullable: false
        description nullable: true
        freq defaultValue: 0
    }

    static mapping = {
        version false
    }
}
