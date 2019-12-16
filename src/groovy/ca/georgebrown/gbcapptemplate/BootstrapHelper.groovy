package ca.georgebrown.gbcapptemplate

import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by tonym on 2017-05-15.
 */
class BootstrapHelper {

    @Autowired
    def userService
  
    static PARSE_CODE = [MODE_LABEL       :'mode:',
                         CODE_LABEL       :'code:',
                         SEQ_LABEL        :'seq:',
                         NAME_LABEL       :'name:',
                         DECRIPTION_LABEL :'description:',
                         TEMPLATE_START   :'**START**',
                         TEMPLATE_END     :'**END**',
                         SUBJECT_LABEL    :'subject:',
                         TEMPLATES_LABEL  :'templates:', CC_LABEL:'ccList:', BCC_LABEL:'bccList:',
                         SAMPLE_JSON_LABEL:'sampleJsonData:']


    static def loadEmailDef(String path) {
        File file = new File(path)
        String line
        def data = [code:null, description:null, mode:'create',subject:null, ccList:null, bccList:null, templates:[]]
        file.withReader { reader ->
            while ((line = reader.readLine())!=null) {
                if (line.startsWith(PARSE_CODE.CODE_LABEL)) data.code = line.substring(PARSE_CODE.CODE_LABEL.length())
                 if (line.startsWith(PARSE_CODE.DECRIPTION_LABEL)) data.description = line.substring(PARSE_CODE.DECRIPTION_LABEL.length())
                if (line.startsWith(PARSE_CODE.MODE_LABEL)) data.mode = line.substring(PARSE_CODE.MODE_LABEL.length())
                if (line.startsWith(PARSE_CODE.SUBJECT_LABEL)) data.subject = line.substring(PARSE_CODE.SUBJECT_LABEL.length())
                if (line.startsWith(PARSE_CODE.CC_LABEL)) data.ccList = line.substring(PARSE_CODE.CC_LABEL.length())
                if (line.startsWith(PARSE_CODE.BCC_LABEL)) data.bccList = line.substring(PARSE_CODE.BCC_LABEL.length())
                if (line.startsWith(PARSE_CODE.TEMPLATES_LABEL)) data.templates = line.substring(PARSE_CODE.TEMPLATES_LABEL.length()).split(",")
                if (line.startsWith(PARSE_CODE.SAMPLE_JSON_LABEL)) data.sampleJsonData = line.substring(PARSE_CODE.SAMPLE_JSON_LABEL.length())
            }
        }
        return data

    }
    static def loadEmailTemplate(String path) {
        File file = new File(path)
        String line
        boolean templateStarted = false
        StringBuilder template = new StringBuilder()
        boolean done = false
        def data = [mode:'create', name:null, seq:null,code:null,description:null,template:null]
        file.withReader { reader ->
            while ((line = reader.readLine())!=null && !done) {
                if(!templateStarted) {
                    if (line.startsWith(PARSE_CODE.NAME_LABEL)) data.name = line.substring(PARSE_CODE.NAME_LABEL.length())
                    if (line.startsWith(PARSE_CODE.SEQ_LABEL)) data.seq = line.substring(PARSE_CODE.SEQ_LABEL.length())
                    if(line.startsWith(PARSE_CODE.CODE_LABEL)) data.code = line.substring(PARSE_CODE.CODE_LABEL.length())
                    if(line.startsWith(PARSE_CODE.DECRIPTION_LABEL)) data.description = line.substring(PARSE_CODE.DECRIPTION_LABEL.length())
                    if(line.startsWith(PARSE_CODE.MODE_LABEL)) data.mode = line.substring(PARSE_CODE.MODE_LABEL.length())
                    if(line.startsWith(PARSE_CODE.TEMPLATE_START)) templateStarted = true
                } else {
                    if(line.startsWith(PARSE_CODE.TEMPLATE_END)) {
                        done = true
                    }  else {
                        template.append(line + "\n")
                    }
                }
            }
        }
        data.template = template.toString()
        return data
    }
    static String joinJSONArray(arr, delim = ',') {
        def result = ''

        arr.eachWithIndex { e, i ->
            result += e

            if (i != arr.size() - 1) {
                result += delim
            }
        }

        result
    }
}
