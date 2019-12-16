package ca.georgebrown.chatbot

import grails.transaction.Transactional


@Transactional
class ApiService {

    def oasService

    // This function returns the default message when the application status is asked.
    def getStatus(def params) {
        def result = [success: true, status: null, error: null]

        if (!params?.appId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        result.status = ""
        return result
    }

    // This function gets the tuition by the user. 
    // This function should be in OasService (FIX!)
    def getTuitionByUser(params) {
        def result = [error: null, success: true, programs: null, code: null, costs: null]
        try {
            if (!params?.appId) {
                result.success = false
                result.error = "missing parameter"
                return result
            }

            String thisYear = new Date().format('yyyy')
            int thisMonth = new Date().format('MM').toInteger()
            def termCode
            def eapTuition = 0
            def eapDuration = oasService.findEapDuration(params?.appId)

            if (eapDuration != null) {
                eapTuition = eapDuration.toDouble()/8*2825
            }

            if (thisMonth < 5) {
                termCode = thisYear + '03'
            }
            else if (thisMonth < 9) {
                thisYear = (thisYear.toInteger() + 1).toString()
                termCode = thisYear + '01'
            }
            else if (thisMonth < 13) {
                termCode = thisYear + '02'
            }

            def programCode = oasService.findProgramCodeFromApplicant(params?.appId)
            result?.code = programCode

            if (programCode?.programCode1 && (programCode?.programCode2 == null)) {
                result.programs = programCode.programCode1
                def programName = oasService.getProgramNameByCode(programCode.programCode1)
                def cost = oasService.getTuitionByCode(programCode.programCode1)
                def term = cost?.find{it.term?.value?.toString() == termCode}?.termName
                cost = cost?.find{it.term?.value?.toString() == termCode}?.cost
                result?.costs = "The estimated cost for $programName in $term is \$$cost. "

                if (eapTuition != 0) {
                    def totalTuition = cost?.toDouble() + eapTuition
                    result?.costs = result?.costs + "Because you are planning to take EAP for $eapDuration weeks, the total tuition is \$${totalTuition}. "
                }
            }
            else if (programCode?.programCode1 && programCode?.programCode2) {
                result?.programs = programCode
                def programName1 = oasService.getProgramNameByCode(programCode.programCode1)
                def programName2 = oasService.getProgramNameByCode(programCode.programCode2)
                def cost1 = oasService.getTuitionByCode(programCode.programCode1)
                def cost2 = oasService.getTuitionByCode(programCode.programCode2)
                def term = cost1.find{it.term?.value?.toString() == termCode}?.termName
                cost1 = cost1?.find{it.term?.value?.toString() == termCode}?.cost
                cost2 = cost2?.find{it.term?.value?.toString() == termCode}?.cost
                result?.costs = "In $term, the estimated costs for $programName1 and $programName2 are \$$cost1 and \$$cost2, respectively. "

                if (eapTuition != 0) {
                    def totalTuition1 = cost1?.toDouble() + eapTuition
                    def totalTuition2 = cost2?.toDouble() + eapTuition
                    result?.costs = result?.costs + "Because you are planning to take EAP for $eapDuration weeks, the estimated tuitions are \$$totalTuition1 and \$$totalTuition2. "
                }
            }
            else {
                result?.costs = "We do not have your program information at this moment. "
                if (eapTuition != 0) result?.costs = result?.costs + "Your estimated EAP tuition is \$$eapTuition. "
            }
        }
        catch (Exception e) {
            result?.success = false
            result?.error = e
            result?.costs = "Sorry, your request did not go through. Please try again later. "
        }
        finally {
            return result
        }
    }

    // This function returns teh tuition by the program code.
    def getProgramCodeOptionsFromNameForTuition(params) {
        def result = [error: null, success: true, programs: null, size: 0, code: null]

        if (!params.name) {
            result.error = 'missing parameters'
            result.success = false
            return result
        }

        result.programs = oasService.findProgramCode(params.name)

        if (!result.programs) {
            result.size = 0
        }

        if (result.programs.size()) {
            result.size = result.programs.size()
        }

        if (result.size == 1) {
            def code = result.programs[0].code
            result = [success: true, costs: null, error: null]
            result.costs = oasService.getTuitionByCode(code)
        }

        return result
    }

    def getTuitionByCode(params) {
        def result = [success: true, costs: null, error: null]
        if (!params?.code) {
            result.error = 'missing parameters'
            result.success = false
        }
        result.costs = oasService.getTuitionByCode(params?.code)
        return result
    }

    // This function returns the list of applicants for the agent.
    def getApplicantsAssignedToAgents(params) {
        def result = [success: false, applicants: null]
        def role = params.role.toInteger()
        if (!role?.isInteger()) return result

        if (role == '5') {
            result.applicants = oasService.getApplicantsByAgent(params.userId)
            result.success = true
        }

        if (role == '7') {
            result.applicants = oasService.getApplicantsByAgentCounselor(params.userId)
            result.succes = true
        }
    }

    // This function gets the app id by the user name.
    def findAppIdByUsernameAndAuthorizeAppId(params) {
        def result = [success: true, error: null, authApp: false, authAppId: null]

        if (!params.userId || !params.usernameApp || !params.role) {
            result.success = false
            result.error = 'Missing Parameter'
            return result
        }

        result.authAppId = oasService.findAppidByUsername(params.usernameApp)
        if (result.authAppId) result.authApp = oasService.authorizeAppidForAgents(params.userId, params.role, result.authAppId)
        return result
    }

    // This function lists agent application.
    def listAgentApps(def params) {
        def result = [success: true, error: null, authorizedApps: null]

        if (params.appuserId && params.role) result.authorizedApps = oasService.listAgentApps(params.appuserId, params.role, params?.max ?: 10)
        else {
            result.error = 'missing parameters'
            result.success = false
        }

        return result
    }

    def getName(def params) {
        def result = [success: true, name: null, error: null]

        if (!params?.userId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        result.name = oasService.getUserName(params.userId)
        return result
    }

    def getProgramDuration(def params) {
        def result = [success: true, duration: null, error: null]

        if (!params?.code) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        result.duration = oasService.getProgramDuration(params?.code)
        return result
    }

    def getAgentName(def params) {
        def result = [success: true, agent: null, error: null]
        def assisted = oasService.getAgentAssisted(params.bannerId)
        result.agent = oasService.getAgentName(params.bannerId)

        if (!params?.bannerId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        if (assisted == 'No') {
            result.agent = 'no agent'
            return result
        }

        return result
    }

    def getAuthApp(def params) {
        def result = [success: true, authUser: null, authApp: null, authRole: null, error: null, authorizedApps: null, authAppDetails: null]
        def authUser = oasService.authUser(params.userId, params.token)

        if (!params?.token || !params?.userId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        if (authUser) {
            def authApp
            def authRole = oasService.getRole(params.userId)
            result.authUser = "true"
            result.authRole = authRole
            result.authorizedApps = oasService.listAgentApps(params.userId, authRole, params.max ?: 10)

            if (params?.appId && params?.appId?.isInteger()) {
                authApp = oasService.authApp(params.userId, params.appId)
                result.authAppDetails = oasService.authAppDetails(params.appId)
            }

            if (authApp) result.authApp = "true"
            else result.authApp = "false"
        }
        else {
            result.authUser = "false"
            result.authApp = "false"
        }
        return result
    }

    def getAuthUser(def params) {
        def result = [success: true, authUser: null, error: null]
        def authUser = oasService.authUser(params.userId, params.token)

        if (!params?.token || !params?.userId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        if (authUser) result.authUser = "true"
        else result.authUser = "false"

        return result
    }

    def getRole(def params) {
        def result = [success: true, authRole: null, error: null]
        def authRole = oasService.getRole(params.userId)
        result.authRole = authRole

        if (!params?.userId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        return result
    }

    def checkStudentId(def params) {
        def result = [success: true, error: "", firstName: "", userId: "", appId: ""]
        def object = oasService.checkStudentId(params.studentId)
        result.success = object?.studentIdExist ?: false
        result.firstName = object?.firstName ?: ""
        result.userId = object?.userId ?: ""
        result.appId = object?.appId ?: ""
        if (!params?.studentId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }
        return result
    }

    def getUserInfoForEmail(def params) {
        def result = [success: true, firstName: "", lastName: "", bannerId: "", email: "", error: ""]
        if (!params?.userId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        def object = oasService.getUserInfoForEmail(params.userId)
        result.email = object?.emerg_email ?: ""
        result.firstName = object?.firstname ?: ""
        result.lastName = object?.lastname ?: ""
        result.bannerId = object?.actual_bannerid ?: ""

        return result
    }

    def getEmail(def params) {
        def result = [success: true, email: "", error: ""]
        def email = oasService.getEmail(params.userId)
        result.email = email

        if (!params?.userId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        return result
    }

    def getAuthRole(def params) {
        def result = [success: true, authUser: null, authRole: null, error: null]

        if (!params?.token || !params?.userId) {
            result.success = false
            result.error = "missing parameter"
            return result
        }

        def authUser = oasService.authUser(params.userId, params.token)

        if (authUser) {
            result.authUser = "true"
            def authRole = oasService.getRole(params.userId)
            result.authRole = authRole
        }
        else result.authUser = "false"

        return result
    }

    // This function gets the tuition value from OASService, and renders it with the message.
    def checkIfTuitionIsSent(def params) {
        def result = [success: true, error: false, tuitionAccepted: 0, message: ""]
        try {
            if (!params?.userId) {
                result.success = false
                result.error = "missing parameter"
                return result
            }
            result.tuitionAccepted = oasService.checkIfTuitionIsSent(params.userId) ?: 0
            result.message = "The total payment that has been received is \$${result.tuitionAccepted} CAD. If this does not match with what you expected, " +
                    "please type 'email' to talk to your admissions officer."
        }
        catch (Exception e) {
            print(e)
            result.success = false
            result.error = e
        }
        finally {
            return result
        }
    }
}
