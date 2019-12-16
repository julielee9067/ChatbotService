package ca.georgebrown.chatbot

import ca.georgebrown.security.Approle
import ca.georgebrown.security.Appuser
import ca.georgebrown.security.AppuserApprole
import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_USER_ADMIN'])
class UserManagementController {

    def springSecurityService
    def ldapService

    def index() {
       redirect action: "list"
    }

    def list() {
        Appuser appuser = springSecurityService.currentUser
        return [appuser: appuser]
    }

    def edit(Long id) {
        Appuser appuser = springSecurityService.currentUser
        def user = Appuser.get(id)
        if (!user) {
            flash.message = "User ${id} not found"
            redirect(action: "list")
            return
        }
        return [appuser: appuser, user: user]
    }

    def update(Long id, Long version) {
        def user = Appuser.get(id)

        if (!user) {
            flash.message = "Setting ${id} not found"
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (user.version > version) {
                user.errors.rejectValue("version", "default.optimistic.locking.failure",
                        ['Appuser'] as Object[],
                        "Another user has updated this Appuser while you were editing")
                render(view: "edit", model: [user: user])
                return
            }
        }

        user.properties = params

        if (params.resetPassword && params.resetPassword != Appuser.NO_CHG_PASSWORD) {
            user.password = params.resetPassword
        }

        def setRolesList = []

        params.role.each() {arId, on->
            if (on=='on') {
                setRolesList.add(Approle.get(arId.toLong()))
            }
        }

        user.authorities.each() { Approle role->
            if (!(role in setRolesList)) AppuserApprole.remove user, role
        }

        setRolesList.each() {Approle role ->
            if (!(role in user.authorities)) {
                AppuserApprole.create user, role
            }
        }

        if (!user.save(flush: true)) {
            render(view: "edit", model: [user: user])
            return
        }

        flash.message = message(code: 'default.updated.message', args: ['', user.displayName])
        redirect(action: "edit", id: user.id)
    }

    def newUser() {
        def username = params.username
        if (username) {
            def user = Appuser.findByUsername(username)
            if (user) {
                redirect(action:"edit", id:user.id)
                return
            }
            else {
                if (params.authType =='LDAP') {
                    def attribs = ldapService.getAttributes(username)
                    if (!attribs.size()) {
                        flash.message = "Person with id ${username} not found in corporate directory."
                        redirect(action: "list")
                        return
                    }
                    user = new Appuser(username: username, firstName: attribs.givenName, lastName: attribs.sn, email: attribs.mail, password: ldapService.randomPassword())
                } else {
                    user = new Appuser(username: username,  password: ldapService.randomPassword(), authenticationType:Appuser.AUTHENTICATION_TYPE.DAO)
                }
                user.save(flush:true)
                redirect(action:'edit', id:user.id)
                return
            }
        }
        redirect action:'list'
    }

    @Secured(['ROLE_WS_ACCT'])
    def wsAcct() {
        Appuser user = springSecurityService.currentUser
        if (!user) {
            flash.message = "User not authenticated"
            redirect(controller: "login")
            return
        }
        return [appuser:user, user: user]
    }

    @Secured(['ROLE_WS_ACCT'])
    def wsAcctUpdate() {
        Appuser user = springSecurityService.currentUser
        if (!user) {
            flash.message = "User not authenticated"
            redirect(controller: "login")
            return
        }

        user.properties = params
        if (params.resetPassword && params.resetPassword != Appuser.NO_CHG_PASSWORD) {
            user.password = params.resetPassword
        }

        if (!user.save(flush: true)) {
            render(view: "wsAcct", model: [user: user])
            return
        }

        flash.message = message(code: 'default.updated.message', args: ['', user.displayName])
        redirect(action: "wsAcct", id: user.id)
    }
}