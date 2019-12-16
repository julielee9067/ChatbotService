package gbcwebsiteservice

class SecurityFilters {
    def securityService
    def filters = {
        all(controller:'api', action:'*', actionExclude:'index') {
            before = {
               if(!securityService.authenticated(params)) {
                redirect controller: "authenticationFail"
               }
            }
            after = { Map model ->
            }
            afterView = { Exception e ->
            }
        }
    }
}
