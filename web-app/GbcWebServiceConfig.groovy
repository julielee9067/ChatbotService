dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:postgresql://dmzpgs01u.gbcuat.local:5432/gbcwebsite_service"
            username = "postuat"
            password = "XXXXXXX"
}




dataSource_banner {

    dialect = 'org.hibernate.dialect.OracleDialect'

    driverClassName = "oracle.jdbc.driver.OracleDriver"

    dbCreate = 'validate'

    username = 'intappl'

    password = 'XXXXXXX'



    //TEST

    url = 'jdbc:oracle:thin:@gbcora01u.gbcuat.local:1521:TEST'

}

grails {

    mail {

        host = "smtp.gmail.com"

        username = "gbc.international.centre@gmail.com"

        password = "XXXXX"



        from = "noreply@georgebrown.ca"

        port = "465"

        props = ["mail.smtp.auth"                  : "true",

                 "mail.smtp.socketFactory.port"    : "465",

                 "mail.smtp.socketFactory.class"   : "javax.net.ssl.SSLSocketFactory",

                 "mail.smtp.socketFactory.fallback": "false"]



        overrideAddress = "Emily.wang2@georgebrown.ca"

    }

}


ldap {

    query {

        domain = 'gbc'

        tls = false

        base = 'dc=gbc,dc=local'



        timeLimit = 5000

        attributes =[        'samAccountName',

                             'canonicalName',

                             'cn',

                             'department',

                             'givenName',

                             'instanceType',

                             'mail',

                             'manager',

                             'memberOf',

                             'name',

                             'primaryGroupID',

                             'showInAddressBook',

                             'sn',

                             'telephoneNumber',

                             'title'

        ]

        url = 'ldap://gbcad4.gbc.local:389'



        loginId = 'M16011301'

        password = 'XXXXXX'


    }

    security {

        url = 'ldap://gbcad4.gbc.local:389' // 10.10.10.84

        tls = false // Ensure sever's certificate is installed in jssecacerts

        domain = 'gbc'

        search {

            base = 'dc=gbc,dc=local'

            filter = "samAccountName={0}"

            timeLimit = 5000 //ms

            attributes = ['sn', 'givenName', 'mail', 'telephoneNumber']

        }

        updateProperties = [firstName:'givenName', lastName:'sn', email:'mail']

    }



}

commandLinePdf = true