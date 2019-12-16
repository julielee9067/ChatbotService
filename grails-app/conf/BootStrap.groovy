import ca.georgebrown.security.*
import ca.georgebrown.chatbot.*
import ca.georgebrown.gbcapptemplate.BootstrapHelper
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject

import java.text.SimpleDateFormat


class BootStrap {
    static final DATA_LOAD_MODE = [CREATE: 'create', UPDATE: 'update']
    static final DATEVAL = "DATEVAL~"
    def grailsApplication
    def init = { servletContext ->
        createRoles()
        createAdminUser()
        loadJsonDomainData()
    }
    def destroy = {
    }

    def createRoles() {
        def roleList = [
                'ROLE_USER_ADMIN':'User Admin',
                'ROLE_APP_MANAGER':'Application Manager',
                'ROLE_DEVELOPER':'Developer',
                'ROLE_WS_ACCT':'Web Service Account'
        ]

        roleList.each() { authority, name ->
            Approle approle = Approle.findByAuthority(authority)
            if(!approle) {
                approle = new Approle(authority: authority, name:name)
                approle.save(flush:true)
            }
        }
    }
    def createAdminUser() {
        Approle approle = Approle.findByAuthority("ROLE_USER_ADMIN")
        Appuser user = Appuser.findByUsername('admin')
        if (!user) {
            user = new Appuser(username: 'admin', password: 'admin', authenticationType: Appuser.AUTHENTICATION_TYPE.DAO, firstName: 'Admin', lastName: 'User')
            user.save(flush: true, failOnError: true)
            AppuserApprole.create user, approle
        }
    }

    def loadJsonDomainData() {
        loadJsonDataToDomain("ca.georgebrown.gbcwebsiteservice",'settings', Setting)
    }



    def	loadJsonDataToDomain(String packageName, String jsonFileName, Class clazz, String key = 'code') {
        def obj = null
        def applicationContext = grailsApplication.mainContext
        String basePath = applicationContext.getResource("/").getFile().toString()
        String jsonText  = new File("${basePath}/bootstrapData/${jsonFileName}.json").text
        JSONObject jsonObj = JSON.parse(jsonText)
        jsonObj.each() {String keyValue,v->
            def k = loadConvert(keyValue)
            def mode = v.mode?:DATA_LOAD_MODE.CREATE
            obj = clazz."findBy${key.capitalize()}"(k)
            if(!obj) {
                obj = clazz.newInstance("${key}":k)
            } else {
                if(mode == DATA_LOAD_MODE.CREATE) return
            }

            def properties = [:]
            v.properties.each() { field , val ->
                val = loadConvert(val)
                properties.put(field,val)
                if(val instanceof org.codehaus.groovy.grails.web.json.JSONArray) {
                    properties[field] = BootstrapHelper.joinJSONArray(val,"")
                }
            }
            obj.properties = properties
            if(v?.domains) {
                v.domains.each() { domainField->
                    def instance =  grailsApplication.getDomainClass("${packageName}.${domainField.class}").clazz."findBy${domainField.key}"(domainField.value)
                    obj[domainField.field] = instance
                }
            }
            obj.save(flush: true, failOnError: true)
            if (v?.hasMany && mode == DATA_LOAD_MODE.UPDATE) {
                v.hasMany.each() { hasMany ->
                    hasMany.each() { manyProperty, val ->
                        if (obj."${manyProperty}") obj."${manyProperty}".clear()
                        val.list.each() { keyVal ->
                            def manyObj = grailsApplication.getDomainClass("${packageName}.${val.domain}").clazz."findBy${val.key.capitalize()}"(keyVal)
                            if(!manyObj) {
                                println "keyVal = ${keyVal}, val.domain=${val.domain}, package=${packageName}, val.key=${val.key}"
                            }
                            obj."addTo${manyProperty.capitalize()}"(manyObj)
                        }
                    }
                }
                obj.save(flush: true, failOnError: true)
            }
        }

    }
    def loadConvert(def val) {
        if(val instanceof String) val = val.trim()
        if(val instanceof String && val.startsWith(DATEVAL)) {
            def dsplit = val.split("~")
            val = new SimpleDateFormat("yyyy-MM-dd").parse(dsplit[1])
        }
        return val

    }

}
