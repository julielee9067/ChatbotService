package ca.georgebrown.chatbot

class UploadedFile {

    Date dateCreated
    String originalFilename
    FileContentType contentType

    static constraints = {
        dateCreated nullable: true
        originalFilename nullable: true
        contentType nullable: true
    }
}