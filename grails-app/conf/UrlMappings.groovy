class UrlMappings {

	static mappings = {
        // custom uri for publisherApiController
        // only goes to index        
        "/api/v1/publishers(.$format)?"(controller:'publisherApi')

        // custom uri for publisherApiController
        "/api/v1/publishers/$id(.$format)?"(controller:'publisherApi'){
          action = [GET:"show", PUT:"update", POST:"save", DELETE:"delete"]
        }

        // this is standard grails stuff
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
