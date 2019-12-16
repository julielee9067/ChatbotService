package ca.georgebrown.chatbot

import grails.util.Environment


class DeleteDuplicateTranscriptJob {

    def transcriptService

    static triggers = {
        switch (Environment.current) {
            case Environment.PRODUCTION:
                cron name: 'deleteDuplicateTranscriptTrigger', cronExpression: '0 0/15 * * * ?' // Every 15 minutes
                break;
            case Environment.DEVELOPMENT:
                cron name: 'deleteDuplicateTranscriptTrigger', cronExpression: '0 0/15 * * * ?' // Every 15 minutes
                break;
            case Environment.TEST:
                cron name: 'deleteDuplicateTranscriptTrigger', cronExpression: '0 0/15 * * * ?' // Every 15 minutes
                break;
        }
    }

    def execute() {
        transcriptService.deleteDuplicateTranscript()
    }
}