package ca.georgebrown.chatbot

class Setting {

    static final TYPE = [
            INT:'int',
            LONG:'long',
            BOOLEAN:'boolean',
            STRING:'string',
            DATE:'date',
            DATETIME:'datetime',
            DOUBLE:'double',
            LIST:'list'
    ]

    static final CODE = [
            IP_WHITELIST:'ipWhitelist',
            AUTHENTICATION_REQUIRED: 'authenticationRequired'
    ]
    String code
    String description
    String value
    String type
    int seq = 0
    static constraints = {
        description nullable:true, maxSize: 4000
        value maxSize: 4000, nullable:true
    }
}
