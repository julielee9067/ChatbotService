package ca.georgebrown.chatbot

import ca.georgebrown.security.Appuser
import org.springframework.security.access.annotation.Secured


@Secured(['ROLE_APP_MANAGER','ROLE_DEVELOPER'])
class SettingsController {

    def springSecurityService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        Appuser appuser = springSecurityService.currentUser
        params.max = max
        [appuser:appuser,settingsInstanceList: Setting.list(params), settingsInstanceTotal: Setting.count()]
    }

    def edit(Long id) {
        Appuser appuser = springSecurityService.currentUser
        def settingsInstance = Setting.get(id)
        if (!settingsInstance) {
            flash.message = "Setting ${id} not found"
            redirect(action: "list")
            return
        }
        [appuser:appuser,settingsInstance: settingsInstance]
    }

    def update(Long id, Long version) {
        def settingsInstance = Setting.get(id)

        if (!settingsInstance) {
            flash.message = "Setting ${id} not found"
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (settingsInstance.version > version) {
                settingsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        ['Settings'] as Object[],
                        "Another user has updated this Settings while you were editing")
                render(view: "edit", model: [settingsInstance: settingsInstance])
                return
            }
        }

        if (params.value instanceof Date) {
            params.value = params.value.format(params.type==Setting.TYPE.Date?"yyyy-M-d":"yyyy-M-d H:m:s")
        }

        settingsInstance.properties = params

        if (!settingsInstance.save(flush: true)) {
            render(view: "list", model: [settingsInstance: settingsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: ["Updated", settingsInstance.code])
        redirect(action: "list", id: settingsInstance.id)
    }
}
