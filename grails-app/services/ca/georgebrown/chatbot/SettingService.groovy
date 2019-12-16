package ca.georgebrown.chatbot

import grails.transaction.Transactional

@Transactional
class SettingService {
    // This function gets the value for the specific setting by its code.
    def get(String code) {
        Setting setting = Setting.findByCode(code)
        if (!setting) return null
        setting.refresh()
        switch (setting.type) {
            case Setting.TYPE.INT:
                return setting.value.toInteger()
            case Setting.TYPE.LONG:
                return setting.value.toLong()
            case Setting.TYPE.BOOLEAN:
                return setting.value == 'true'
            case Setting.TYPE.DOUBLE:
                return setting.value.toDouble()
            case Setting.TYPE.DATE:
                def dateStr = setting.value
                if (!dateStr) return null
                Date d = Date.parse("yyyy-M-d", dateStr)
                return d
            case Setting.TYPE.DATETIME:
                def dateStr = setting.value
                if (!dateStr) return null
                Date d = Date.parse("yyyy-M-d H:m", dateStr)
                return d
            case Setting.TYPE.LIST:
                if (!setting.value) return null
                return setting.value.split(",")*.trim()
            case Setting.TYPE.STRING:
                return setting.value
        }
        return setting.value
    }

    // This function sets the setting value to the value given.
    def set(String code, String val) {
        Setting setting = Setting.findByCode(code)
        if (!setting) return false
        setting.value = val
        setting.save(flush: true)
        return true
    }
}
