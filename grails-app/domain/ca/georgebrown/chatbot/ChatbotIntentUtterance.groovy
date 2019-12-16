package ca.georgebrown.chatbot

class ChatbotIntentUtterance {

    String chatToken
    String intent
    String utterance
    boolean incorrect
    String correctIntent
    Date timeSentMessage

    static constraints = {
        utterance(maxSize:4000)
        correctIntent nullable:true
        timeSentMessage nullable:true
    }

    static mapping = {

        version false
        incorrect defaultValue: 'false'


    }
}
