package ca.georgebrown.chatbot

import grails.transaction.Transactional
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.logging.LogFactory
import javax.naming.Context
import javax.naming.NamingEnumeration
import javax.naming.directory.Attribute
import javax.naming.directory.Attributes
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult
import javax.naming.ldap.InitialLdapContext
import javax.naming.ldap.LdapContext
import javax.naming.ldap.StartTlsRequest
import javax.naming.ldap.StartTlsResponse


@Transactional
class LdapService {

    private static log = LogFactory.getLog(this)
    static def grailsApplication

    static def getAttributes(String target, String filter = null) {
        def resultList = []
        Map<String, String> ldapAttributes = new HashMap<String, String>()

        if (!target) return ldapAttributes

        String url = grailsApplication.config.ldap.query.url

        if (!filter) filter = 'samAccountName'

        def filterArray = filter.split(">") //use ">" as delimiter because it's rarely seen in the input
        def targetArray = target.split(">")
        def smallerArray = Math.min(filterArray.length, targetArray.length) //avoid bug while running in loop
        String searchFilter = "(&"

        for (int i = 0; i < smallerArray; i++) {
            def filterTemplate = "(${filterArray[i]}=${targetArray[i]})"
            searchFilter += filterTemplate
        }

        searchFilter += ")"
        boolean tls = grailsApplication.config.ldap.query.tls
        String domain = grailsApplication.config.ldap.query.domain
        String loginId = grailsApplication.config.ldap.query.loginId
        String domainUsername = domain ? "${domain}\\${loginId}" : loginId
        String searchBase = grailsApplication.config.ldap.query.base
        int searchTimeLimit = grailsApplication.config.ldap.query.timeLimit ?: 0
        String[] searchAttributes = grailsApplication.config.ldap.query.attributes
        String password = grailsApplication.config.ldap.query.password

        Hashtable<String, String> env = new Hashtable<String, String>()
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
        env.put(Context.PROVIDER_URL, url)

        LdapContext ctx = null;

        try {
            ctx = new InitialLdapContext(env)
            if (tls) {
                ((StartTlsResponse) ctx.extendedOperation(new StartTlsRequest())).negotiate()
            }

            // Add security
            ctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, "simple")
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, domainUsername)
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password)

            // Test authentication by executing command that requires security
            ctx.getAttributes("", ["dnsHostName"] as String[])

            // Find user object and update database user object
            try {
                SearchControls constraints = new SearchControls(SearchControls.SUBTREE_SCOPE,
                        10L, searchTimeLimit, searchAttributes, false, false);
                NamingEnumeration<?> searchResults = ctx.search(searchBase, searchFilter, constraints);

                if (searchResults != null && searchResults.hasMore()) {
                    while (true) {
                        SearchResult searchResult = (SearchResult) searchResults.next();
                        if (!searchResult) break
                        Attributes attributes = searchResult.getAttributes();
                        if (attributes != null) {
                            for (NamingEnumeration enums = attributes.getAll(); enums.hasMore();) {
                                Attribute attribute = (Attribute) enums.next();
                                ldapAttributes.put(attribute.getID(), attribute.get());
                            }
                            resultList.add(ldapAttributes)
                            ldapAttributes = new HashMap<String, String>()
                        }
                    }
                }
            } catch (Exception e) {
                // Ignore error and simply do not update database
            }
        } catch (Exception e) {
            log.error e
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                }
            }
        }
        return resultList
    }

    def randomPassword() {
        int randomStringLength = 32
        String charset = (('a'..'z') + ('A'..'Z') + ('0'..'9')).join()
        String randomString = RandomStringUtils.random(randomStringLength, charset.toCharArray())
        return randomString
    }
}
