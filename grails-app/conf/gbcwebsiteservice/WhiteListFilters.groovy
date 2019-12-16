package gbcwebsiteservice

import ca.georgebrown.chatbot.Setting

class WhiteListFilters {
    def settingService
    def filters = {
        all(controller:'api', action:'*') {
            before = {
             String ipAddress =  request.getRemoteAddr()
             def whiteList = settingService.get(Setting.CODE.IP_WHITELIST)
             boolean allowed = false
             whiteList.each() { prefix->
                 if(allowed) return
                 if(ipAddress.startsWith(prefix)) allowed = true
             }
             if(!allowed) {
                 redirect(controller:'deny', params: [message:"${ipAddress} is not allowed to access this API"])
                 return false
             }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
