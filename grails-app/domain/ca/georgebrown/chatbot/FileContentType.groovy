package ca.georgebrown.chatbot

class FileContentType {
    String name
    String mimeType
    String ext

    static constraints = {
        ext unique: true
    }
    static mapping = {
        version false
    }
}
