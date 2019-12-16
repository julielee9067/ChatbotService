package ca.georgebrown.chatbot

import ca.georgebrown.security.Appuser
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured


@Secured('permitAll')
class ApiController {

    def springSecurityService
    def apiService
    def transcriptService

    def index() {
        Appuser appuser = springSecurityService.currentUser
        return [appuser: appuser]
    }

    def getProgramCodeOptionsFromNameCost() {
        render(apiService.getProgramCodeOptionsFromNameForTuition(params) as JSON)
    }

    def checkIfTuitionIsSent() {
        render(apiService.checkIfTuitionIsSent(params) as JSON)
    }

    def checkStudentId() {
        render(apiService.checkStudentId(params) as JSON)
    }

    def getTuitionByCode() {
        render(apiService.getTuitionByCode(params) as JSON)
    }

    def getTuitionByUser() {
        render(apiService.getTuitionByUser(params) as JSON)
    }

    def findAppIdByUsernameAndAuthorizeAppId() {
        render(apiService.findAppIdByUsernameAndAuthorizeAppId(params) as JSON)
    }

    def listAgentApps() {
        render(apiService.listAgentApps(params) as JSON)
    }

    def getApplicantsAssignedToAgents() {
        render(apiService.getApplicantsAssignedToAgents(params) as JSON)
    }

    def updateTranscript() {
        render(transcriptService.updateTranscript(params) as JSON)
    }

    def updateIntentTable() {
        transcriptService.updateIntentTable()
        render(null as JSON)
    }

    def updateIntentUtterances() {
        render(transcriptService.updateIntentUtterances(params) as JSON)
    }

    def getProgramDuration() {
        render(apiService.getProgramDuration(params) as JSON)
    }

    def getAuthApp() {
        render(apiService.getAuthApp(params) as JSON)
    }

    def getEmail() {
        render(apiService.getEmail(params) as JSON)
    }

    def getUserInfoForEmail() {
        render(apiService.getUserInfoForEmail(params) as JSON)
    }

    def getRole() {
        render(apiService.getRole(params) as JSON)
    }

    def getAuthUser() {
        render(apiService.getAuthUser(params) as JSON)
    }

    def getAuthRole() {
        render(apiService.getAuthRole(params) as JSON)
    }

    def getName() {
        render(apiService.getName(params) as JSON)
    }

    def getStatus() {
        render(apiService.getStatus(params) as JSON)
    }

    def getAgent() {
        render(apiService.getAgentName(params) as JSON)
    }
}
