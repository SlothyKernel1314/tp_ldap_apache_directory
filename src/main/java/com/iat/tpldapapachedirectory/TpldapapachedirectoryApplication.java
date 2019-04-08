package com.iat.tpldapapachedirectory;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class TpldapapachedirectoryApplication {

    // doc : https://directory.apache.org/api/gen-docs/latest2/apidocs/

    public static void main(String[] args) throws Exception {

        SpringApplication.run(TpldapapachedirectoryApplication.class, args);

        // CREATE CONNECTION TO LDAP ===================================================================================

        ConnectionService connectionService = new ConnectionService();

        String domain = connectionService.getDomain();

        LdapConnection connection = connectionService.bindingConnection();

        // CREATE CONNECTION TO LDAP DONE ==============================================================================


        // TEST CASES ==================================================================================================

        LdapQueries ldapQueries = new LdapQueries();
//
        ldapQueries.findAllAdm(connection, domain);
//        ldapQueries.findAllPersons(connection, domain);
//        ldapQueries.addPerson(connection, domain, "kmitroglou", "Mitroglou");
//        ldapQueries.deletePerson(connection, domain, "kmitroglou");
//        ldapQueries.addAttributesToPerson(connection, domain, "kmitroglou",
//                "givenName", "Mitroflop", "initials", "KM");
//        ldapQueries.removeAttributesToPerson(connection, domain, "kmitroglou",
//                "givenName", "initials");
//        ldapQueries.replaceAttributesToPerson(connection, domain, "kmitroglou",
//                "givenName", "Gronaldo", "initials", "MK");
//        ldapQueries.moveAndRenamePerson(connection, "cn=kmitroglou,ou=adm,dc=vinci-melun,dc=org",
//                "cn=kmitroglou,ou=profs,dc=vinci-melun,dc=org", true);

        // END TEST CASES ==============================================================================================


        // CLOSE CONNECTION TO LDAP ====================================================================================

        connectionService.closeConnection(connection);

        // CLOSE CONNECTION TO LDAP DONE ===============================================================================
    }

}
