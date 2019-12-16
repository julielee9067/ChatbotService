<%@ page import="ca.georgebrown.chatbot.ChatbotTranscript; ca.georgebrown.chatbot.Setting" contentType="text/html;charset=UTF-8" %>
<g:set var="settingService" bean="settingService"/>

<html>
<head>
    <meta name="layout" content="main">
    <title>Gbc Banner Web Service - Chatbot Help Documents</title>
    <script type="application/javascript">

        function validate() {
            var uploadedFile = document.getElementById("my-file-selector");
            if (uploadedFile.value == "") {
                $("#clientMsg").html("<strong>No file uploaded!</strong>");
                return false
            }
            else {
                var isValid = true;
                if (isValid) {
                    $("#clientMsg").html("<strong>You have selected a file</strong>");
                    return true;
                }
                else {
                    bootbox.alert("<p style='white-space: nowrap; margin-top: 20px'>You have not completed the required fields.</p>");
                    return false;
                }
            }
        }

        function setUploadName(el) {
            $('#upload-file-info').html(el.files[0].name);
        }
    </script>
    <style>
        .fileName {
            min-width: 400px;
        }

        .fileDownload {
            width: 70px;
        }
    </style>
</head>

<body class="container-fluid">
<g:render template="/layouts/navigation" model="[which:'help']"/>
<div class="w-100">
    <table style="width: 90%; max-width: 1000px;" class="mx-auto my-3">
        <tr></tr>
        <tr style="border-bottom-style: groove; border-bottom-width: thick">
            <td class="fileName" style="text-align: center; font-weight: bold"> Name </td>
            <td class="fileDownload" style="text-align: center; font-weight: bold"> Download </td>
        </tr>
        <g:each in="${fileList}" var="uploadedFile">
            <tr class ="rowTable" style="border-bottom-style: dotted; border-bottom-width: thin">
                <td class="fileName"> ${uploadedFile.originalFilename} </td>
                <td class="fileDownload">
                    <g:link controller="help" action="downloadFile" id="${uploadedFile.id}" target="_blank"
                            class="btn btn-sm btn-primary" style="border-color: white; border-width: thin"> Download </g:link>
                </td>
            </tr>
        </g:each>
        <tr><td class="fileName"><br></td><td class="fileDownload"><br></td></tr>
        <tr class="rowTable">
            <g:form method="POST" controller="help" action="uploadFile" enctype="multipart/form-data">
                <td class="fileName">
                    <span class="fileName" id="upload-file-info"></span>
                    <label class="btn btn-sm btn-secondary" for="my-file-selector" id="lbFileSelector">
                        <input id="my-file-selector" name="myFile" type="file" style="display:none;" onchange="setUploadName(this)">Choose File
                    </label>
                    <input type="hidden" name="storageDir" value="${grailsApplication.config.file.upload.directory}"/>
                    <input type="submit" name="send" class="btn btn-success btn-sm" value="Upload Selected File" style="background-color: #005AA5;" onclick="return validate();" id="btnSubmitFile"/>
                </td>
            </g:form>
        </tr>
    </table>
</div>
</body>
</html>