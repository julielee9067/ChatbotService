package ca.georgebrown.chatbot

import grails.transaction.Transactional
import groovy.sql.Sql
import java.text.DecimalFormat


@Transactional
class OasService {

    def dataSource_oas

    // This function returns the current term.
    def getCurrentTermAndFullAcademicYear() {
        def date = new Date()
        def year = date[Calendar.YEAR]
        def month = date[Calendar.MONTH]
        def currentTerm = [:]
        def nextTerm = [:]
        def nextNextTerm = [:]
        def nextNextNextTerm = [:]

        if (month in [0, 1, 2, 3]) {
            currentTerm.code = (year - 1).toString() + '02'
            currentTerm.name = 'Winter ' + year.toString()
            nextTerm.code = (year - 1).toString() + '03'
            nextTerm.name = 'Spring/Summer ' + year.toString()
            nextNextTerm.code = (year).toString() + '01'
            nextNextTerm.name = 'Fall ' + year.toString()
            nextNextNextTerm.code = (year).toString() + '02'
            nextNextNextTerm.name = 'Winter ' + (year + 1).toString()
        }
        else if (month in [4, 5, 6, 7]) {
            currentTerm.code = (year - 1).toString() + '03'
            currentTerm.name = 'Spring/Summer ' + year.toString()
            nextTerm.code = (year).toString() + '01'
            nextTerm.name = 'Fall ' + year.toString()
            nextNextTerm.code = (year).toString() + '02'
            nextNextTerm.name = 'Winter ' + (year + 1).toString()
            nextNextNextTerm.code = (year).toString() + '03'
            nextNextNextTerm.name = 'Spring/Summer ' + (year + 1).toString()
        }
        else if (month in [8, 9, 10, 11]) {
            currentTerm.code = (year).toString() + '01'
            currentTerm.name = 'Fall ' + year.toString()
            nextTerm.code = (year).toString() + '02'
            nextTerm.name = 'Winter ' + (year + 1).toString()
            nextNextTerm.code = (year).toString() + '03'
            nextNextTerm.name = 'Spring/Summer ' + (year).toString()
            nextNextNextTerm.code = (year + 1).toString() + '01'
            nextNextNextTerm.name = 'Fall ' + (year + 1).toString()
        }
        def termList = []
        termList.add(nextTerm)
        termList.add(nextNextTerm)
        termList.add(nextNextNextTerm)
        return termList
    }

    // This function returns the program name by 4-digit course code.
    def getProgramNameByCode(code) {
        Sql sql = Sql.newInstance(dataSource_oas)
        def name = sql.rows("SELECT name from program where code = :code", [code: code])
        sql.close()
        return name[0]?.name
    }

    // This function returns the specific admissions officer depending on the region.
    def findAdmissionsOfficerForUser(def userId) {
        def admissionsOfficer
        Sql sql = Sql.newInstance(dataSource_oas)
        def nationality = (sql.rows("SELECT nationality from applicant where appuser_id = :appuser_id", [appuser_id: userId])).toInteger()
        sql.close()

        if (nationality < 10000) {
            admissionsOfficer = "hi"
        }
        else if (nationality < 20000) {
            admissionsOfficer = "hihi"
        }
        else if (nationality < 30000) {
            admissionsOfficer = "hihihi"
        }
        else if (nationality < 40000) {
            admissionsOfficer = "hihihihi"
        }
        else if (nationality < 50000) {
            admissionsOfficer = "hihihihihi"
        }
        else {
            admissionsOfficer = "default"
        }

        return admissionsOfficer
    }

    // This function returns the tuition for each term.
    def getTuitionByCode(code) {
        def termList = getCurrentTermAndFullAcademicYear()
        DecimalFormat decimalFormat = new DecimalFormat('#.00')
        Sql sql = Sql.newInstance(dataSource_oas)
        def termString = '('

        termList.each() { term ->
            termString += term.code
            termString += ','
        }

        termString = termString[0..-2]
        termString += ')'
        def result = sql.rows("Select id from program where code = :code", [code: code])
        def costs
        if(result) {
            costs = sql.rows("Select SUM(fee_amount) cost, term from program_fee where program_id = " + result[0].id + " and term in " + termString + " group by term")
        }
        sql.close()

        costs.eachWithIndex { cost, i ->
            costs[i].cost = decimalFormat.format(cost.cost)
            cost.put('termName', termList[i].name)
        }

        return costs
    }

    // You need to add the function to the list if you wish to access any of the backend service functions without authentication.
    def getPublicFunctions() {
        def publicFunctionList = []
        publicFunctionList.add('getTuitionByCode')
        publicFunctionList.add('checkStudentId')
        return publicFunctionList
    }

    // This function returns first name, last name, email address, and student ID by the user ID to send out the email.
    def getUserInfoForEmail(def userId) {
        Sql sql = Sql.newInstance(dataSource_oas)
        def result = sql.rows("Select firstname, lastname, emerg_email, actual_bannerid from applicant where appuser_id = :userId", [userId: userId])
        sql.close()

        if (result.size()) return result[0]
        return null
    }

    // This function returns email address of the user.
    def getEmail(def userId) {
        Sql sql = Sql.newInstance(dataSource_oas)
        def result = sql.rows("Select emerg_email from applicant where id = :userId", [appId: userId])
        sql.close()

        if (result.size()) return result[0]

        return null
    }

    // This function returns EAP program duration for the user.
    def findEapDuration(appId)
    {
        String today = new Date().format('yyyy-MM-dd HH:mm:ss.FFF')
        Sql sql = Sql.newInstance(dataSource_oas)
//        def eapDuration = sql.rows("select eap_duration from applicant_program where applicant_id = :appId and eap_start_date >= :today", [appId: appId, today: today])
        def eapDuration = sql.rows("SELECT eap_duration from applicant_program where applicant_id = :appId", [appId: appId])
        if (eapDuration.size()) return eapDuration[0].eap_duration
        return null
    }

    // This function returns the program code by the application from the user.
    def findProgramCodeFromApplicant(appId) {
        Sql sql = Sql.newInstance(dataSource_oas)
        def programId = sql.rows("Select program_id from International.dbo.applicant_program where applicant_id = :appId", [appId: appId])

        if (programId.size() == 1) {
            def programCode = sql.rows("SELECT code from program where id = :programId", [programId: programId[0].program_id])
            sql.close()
            return [programCode1: programCode[0].code, programCode2: null]
        }
        else if (programId.size() == 2) {
            def programCode1 = sql.rows("SELECT code from program where id = :programId", [programId: programId[0].program_id])
            def programCode2 = sql.rows("SELECT code from program where id = :programId", [programId: programId[1].program_id])
            sql.close()
            return [programCode1: programCode1[0].code, programCode2: programCode2[0].code]
        }

        sql.close()
        return null
    }

    // This function returns the course code by the course name.
    def findProgramCode(search_text) {
        Sql sql = Sql.newInstance(dataSource_oas)
        search_text = search_text.split(" ")
        def query = "SELECT code, name from program where code is not null "
        if (search_text instanceof String[]) {
            search_text.each() { search_word ->
                if (search_word in ['--', '*/', '/*']) {}
                else query += " and name Like '%" + search_word + "%'"
            }
        } else {
            def search_word = search_text

            if (search_word in ['--', '*/', '/*']) {}
            else query += " and name Like '%" + search_word + "%'"
        }
        def result = sql.rows(query)
        return result
    }

    // This function returns the app ID by the username of the student.
    def findAppidByUsername(username) {
        Sql sql = Sql.newInstance(dataSource_oas)
        def app = sql.firstRow("Select a.id from appuser join applicant a on appuser.id = a.appuser_id where appuser.username = :username", [username: username])
        sql.close()
        return app?.id
    }

    // This function authenticates the application for the agents.
    def authorizeAppidForAgents(userId, role, appId) {
        Sql sql = Sql.newInstance(dataSource_oas)
        def target
        def aList
        switch (role?.toInteger()) {
            case 5:
                target = sql.firstRow("select id from agent where appuser_id = :userId", [userId: userId])
                aList = sql.rows("""select id,firstname, lastname from applicant
                                            where agent_id = :targetId and id = :appId""", [targetId: target.id, appId: appId])
                break
            case 4:
                target = sql.firstRow("select id from agent_counselor where appuser_id = :userId", [userId: userId])
                aList = sql.rows("""select applicant.id, applicant.firstname, applicant.lastname from applicant
                                            where applicant.agent_counselor_id = :targetId and applicant.id =:appId""", [targetId: target.id, appId: appId])
                break
            default:
                return false
        }
        sql.close()
        if (aList) return true
        return false
    }

    // This function returns the list of applications by the agents.
    def listAgentApps(userId, role, max) {
        def appuserId = userId?.toLong()
        def aList = []
        Sql sql = Sql.newInstance(dataSource_oas)

        if (role instanceof String && role.isInteger()) role = role.toInteger()

        if (appuserId) {
            def aProp = "agent_id"
            def target = null
            switch (role) {
                case 5:
                    target = sql.firstRow("select id from agent where appuser_id = :appuserId", [appuserId: appuserId])
                    break
                case 4:
                    aProp = 'agent_counselor_id'
                    target = sql.firstRow("select id from agent where appuser_id = :appuserId", [appuserId: appuserId])
                    break
            }
            if (target) {
                switch (role) {
                    case 5:
                        aList = sql.rows("""select applicant.id, applicant.firstname, applicant.lastname, au.username from applicant
                                            join appuser au on au.id = applicant.appuser_id
                                            where applicant.agent_id = :targetId""", [targetId: target.id])
                        break
                    case 4:
                        aList = sql.rows("""select applicant.id, applicant.firstname, applicant.lastname, au.username from applicant
                                            join appuser au on au.id = applicant.appuser_id
                                            where applicant.agent_counselor_id = :targetId""", [targetId: target.id])
                        break
                    default:
                        aList = []
                }
                if (max instanceof String) {
                    if (max?.isInteger()) aList = aList.take(max.toInteger())
                    else aList = aList.take(10)
                } else {
                    aList = aList.take(max)
                }
            }
        }
        sql.close()
        return aList
    }

    // This function returns the list of applicants assigned to specific agent.
    def getApplicantsByAgent(userId) {
        Sql sql
        sql = Sql.newInstance(dataSource_oas)

        def agent_id = sql.rows('Select id from International.dbo.agent where appuser_id = : userId', [userId: userId])
        def applicants = sql.rows('SELECT id, firstname, lastname from International.dbo.applicant where agent_id = :userId', [userId: userId])

        if (agent_id.size()) agent_id = agent_id[0]

        sql.close()
        return applicants
    }

    // This function returns the list of applicants assigned to specific agent counselor.
    def getApplicantsByAgentCounselor(def userId) {
        Sql sql
        sql = Sql.newInstance(dataSource_oas)

        def agent_id = sql.rows('Select id from International.dbo.agent_counselor where appuser_id = : userId', [userId: userId])
        if (agent_id.size()) agent_id = agent_id[0]
        def applicants = sql.rows('SELECT id, firstname, lastname from International.dbo.applicant where agent_counselor_id = :userId', [userId: agent_id])
        sql.close()
        return applicants
    }

    // Helper funtion to get formatted date.
    def getFormattedDate(def TimeSent) {
        def year = TimeSent.substring(0, 4)
        def monthCode = TimeSent.substring(5, 7)
        def month
        switch (monthCode) {
            case '01':
                month = 'Jan'
                break
            case '02':
                month = 'Feb'
                break
            case '03':
                month = 'Mar'
                break
            case '04':
                month = 'Apr'
                break
            case '05':
                month = 'May'
                break
            case '06':
                month = 'Jun'
                break
            case '07':
                month = 'Jul'
                break
            case '08':
                month = 'Aug'
                break
            case '09':
                month = 'Sep'
                break
            case '10':
                month = 'Oct'
                break
            case '11':
                month = 'Nov'
                break
            case '12':
                month = 'Dec'
                break
            default:
                month = '???'
        }
        def day = TimeSent.substring(8, 10)
        def hour = TimeSent.substring(11, 13)
        def minute = TimeSent.substring(14, 16)
        def date = month + " " + day + ", " + year + " - (" + hour + ":" + minute + ")"
        return date
    }

    // This function returns the user name by user ID.
    def getUserName(userId) {
        def name = ""
        def roleId = getRole(userId)
        def result
        Sql sql
        sql = Sql.newInstance(dataSource_oas)
        switch (roleId) {
            case 7:
                result = sql.rows("select firstname NAME from applicant where appuser_id = :userId", [userId: userId])
                break
            case 5:
                result = sql.rows("select firstname NAME from agent WHERE appuser_id = :userId", [userId: userId])
                break
            case 4:
                result = sql.rows("select firstname NAME from agent_counselor WHERE appuser_id = :userId", [userId: userId])
                break
            default:
                name = ""
        }
        sql.close()
        if (result.size()) {
            name = result[0].NAME
        }
        return name
    }

    // This function returns the role of the user. (Applicant/agent)
    def getRole(userId) {
        def role = 0
        Sql sql = Sql.newInstance(dataSource_oas)
        def result = sql.rows("SELECT approle_id ROLE FROM appuser_approle WHERE appuser_id = :userId", [userId: userId])
        sql.close()
        if (result.size()) {
            role = result[0].ROLE
        }
        return role
    }

    // This function returns the duration of the program by the course code input.
    def getProgramDuration(def code) {
        def duration = ""
        Sql sql
        sql = Sql.newInstance(dataSource_oas)
        def result = sql.rows("SELECT duration DURATION FROM program WHERE code = :code", [code: code])
        sql.close()
        if (result.size()) {
            duration = result[0].DURATION
        }
        return duration
    }

    // This function gets the agent assist by the student's bannder ID.
    def getAgentAssisted(def bannerId) {
        def assisted = ""
        Sql sql
        sql = Sql.newInstance(dataSource_oas)
        def result = sql.rows("SELECT agent_assisted ASSIST FROM applicant WHERE actual_bannerid = :bannerId", [bannerId: bannerId])
        sql.close()
        if (result.size()) {
            assisted = result[0].ASSIST
        }
        return assisted
    }

    // This function gets the agent's name by provided banner ID of the student.
    def getAgentName(def bannerId) {
        def agent = ""
        Sql sql
        sql = Sql.newInstance(dataSource_oas)
        def result = sql.rows("SELECT agent_name AGENTNAME FROM applicant WHERE actual_bannerid = :bannerId", [bannerId: bannerId])
        sql.close()
        if (result.size()) {
            agent = result[0].AGENTNAME
        }
        return agent
    }

    // This function authenticate the user by the user ID and Lex Chat token.
    def authUser(String userId, String token) {
        boolean auth = false
        Sql sql = Sql.newInstance(dataSource_oas)
        Date nowMinus24 = new Date() - 1
        def appuserRec = sql.firstRow("select * from appuser where id = :userId and token = :token and last_login > :ttlDate"
                , [userId: userId, token: token, ttlDate: nowMinus24.format("yyyy-M-d HH:mm")])
        if (appuserRec) auth = true
        sql.close()
        return auth
    }

    // This function returns the user's name by app ID.
    def authAppDetails(appId) {
        Sql sql = Sql.newInstance(dataSource_oas)
        def result = sql.firstRow("""select applicant.id, applicant.firstname, applicant.lastname, a.username from applicant
                                            join appuser a on applicant.appuser_id = a.id
                                            where applicant.id = :appId""", [appId: appId])
        sql.close()
        def details = result?.firstName + ' ' + result?.lastName + ' ( ' + result?.username + ' ) '
        return details
    }

    // This function checks if the received student ID exists in our system, and returns the name if it does.
    def checkStudentId(String studentId) {
        Sql sql = Sql.newInstance(dataSource_oas)
        boolean studentIdExist = false
        def studentIdNum = studentId.replaceAll("[^0-9]", "")
        def result = sql.rows("Select id, firstname, appuser_id from applicant where actual_bannerid = :studentIdNum", [studentIdNum: studentIdNum])
        sql.close()
        if(result) studentIdExist = true
        return [firstName: result[0]?.firstname ?: "", userId: result[0]?.appuser_id?.toString() ?: "", appId: result[0]?.id?.toString() ?: "", studentIdExist: studentIdExist]
    }

    // This function authenticates the application using user ID and app ID.
    def authApp(String userId, String appId) {
        Sql sql = Sql.newInstance(dataSource_oas)
        boolean auth = false
        def roleId
        def appuserRec
        def result = sql.rows("SELECT approle_id ROLE FROM appuser_approle WHERE appuser_id = :userId", [userId: userId])

        if (result.size()) roleId = result[0].ROLE

        switch (roleId) {
            case 7:
                appuserRec = sql.rows("select * from applicant where appuser_id = :userId and id = :appId", [userId: userId, appId: appId])
                break
            case 5:
                def tempUserId = sql.rows("select id ID from agent where appuser_id = :userId", [userId: userId])
                if (tempUserId.size()) {
                    def tempAgentId = tempUserId[0].ID
                    appuserRec = sql.rows("SELECT * from applicant where id = :appId AND agent_id = :agentid", [agentid: tempAgentId, appId: appId])
                } else {
                    return false
                }
                break
            case 4:
                def tempUserId = sql.rows("select id ID FROM agent_counselor where appuser_id = :userId", [userId: userId])
                if (tempUserId.size()) {
                    def tempAgentId = tempUserId[0].ID
                    appuserRec = sql.rows("select * from applicant where agent_counselor_id = :agentcounid and id = :appId", [agentcounid: tempAgentId, appId: appId])
                } else {
                    return false
                }
                break
            default:
                return false
        }

        if (appuserRec.size()) auth = true
        sql.close()
        return auth
    }

    // This function returns the received tuition by the specific user.
    def checkIfTuitionIsSent(def userId) {
        Sql sql = Sql.newInstance(dataSource_oas)
        def result = sql.rows("select payment_amount from applicant where id=:userId", [userId: userId])
        return result[0].payment_amount.replace(" ", "")
    }
}
