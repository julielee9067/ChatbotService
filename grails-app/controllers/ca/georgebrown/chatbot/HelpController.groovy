package ca.georgebrown.chatbot

import ca.georgebrown.security.Appuser
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest


@Secured(['ROLE_DEVELOPER'])
class HelpController {

    def springSecurityService

    def index() {
        Appuser appuser = springSecurityService.currentUser
        def fileList = UploadedFile.list()
        [appuser: appuser, fileList: fileList]
    }

    def downloadFile(params) {
        def uf = UploadedFile.get(params.id)
        def dir = grailsApplication.config.file.upload.directory?:'/tmp'

        File uFile = new File("${dir}/${uf.originalFilename}")
        response.setHeader("Content-disposition", "filename=\"${uf.originalFilename}\"")
        response.contentType = uf.contentType?.mimeType

        try {
            response.outputStream << new FileInputStream(uFile)
            response.outputStream.flush()
        }
        catch (Exception e) {
            print(e)
            response.outputStream << e.message
            response.outputStream.flush()
        }
    }

    def uploadFile() {
        def msg=''
        def anchor = "fileAnchor"

        if (params.containsKey("send")) {
            UploadedFile uploadedFile = upload(params, request)
            if (!uploadedFile) {
                msg = "File failed to upload"
                [msg: msg]
            }
            else {
                msg = "File successfully uploaded"
            }
        }

        return redirect(controller: 'help', action: 'index', params:[msg: msg, anchor: anchor])
    }

    def upload(def params, def request) {
        switch(request.method) {
            case "GET":
                break
            case "POST":
                if (request instanceof MultipartHttpServletRequest) {
                    for(filename in request.getFileNames()) {
                        MultipartFile file = request.getFile(filename)
                        String originalFileExtension = file.originalFilename.substring(file.originalFilename.lastIndexOf(".")+1)

                        String storageDirectory =  grailsApplication.config.file.upload.directory ?: '/tmp' // FIX THIS DIRECTORY TO SOMETHING ELSE!!!!!!!!

                        if (params?.storageDir) storageDirectory = params?.storageDir

                        FileContentType contentType = FileContentType.findByExt('.' + originalFileExtension)
                        File newFile = new File("$storageDirectory/$file.originalFilename")
                        file.transferTo(newFile)

                        if(UploadedFile.findByOriginalFilename(newFile.name)) {
                            def originalFiles = UploadedFile.findAllByOriginalFilename(newFile.name)
                            originalFiles.each() { UploadedFile originalFile ->
                                originalFile.delete(flush: true)
                            }
                        }
                        UploadedFile ufile = new UploadedFile(originalFilename: newFile.name, contentType: contentType, dir: storageDirectory)
                        ufile.save(flush: true, failOnError: true)
                        return ufile
                    }
                }
                break
            default: println("error")
        }
        return null
    }
}
