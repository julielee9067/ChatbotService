package ca.georgebrown.chatbot

import grails.util.Environment

class ListIntentsJob {

    def transcriptService
    static triggers = {
        switch (Environment.current) {
            case Environment.PRODUCTION:
                cron name: 'listIntentsJobTigger', cronExpression: '0 0 2 * * ?' // daily @ 2AM
                break;
            case Environment.DEVELOPMENT:
                cron name: 'listIntentsJobTrigger', cronExpression: '0 0 2 * * ?' // daily @ 2AM
                break;
            case Environment.TEST:
                cron name: 'listIntentsJobTrigger', cronExpression: '0 0 2 * * ?' // daily @ 2AM
                break;
        }
    }

    def execute() {
        transcriptService.updateIntentTable()
    }
}


