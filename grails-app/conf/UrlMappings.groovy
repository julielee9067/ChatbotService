class UrlMappings {

    static mappings = {

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        //"/"(view:"/index")
        "/"(controller: 'loginSuccess', action: 'index' )
        "500"(view:'/error')
    }
}
