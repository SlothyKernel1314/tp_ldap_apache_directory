package com.iat.tpldapapachedirectory;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpldapapachedirectoryApplication {

    // doc : https://directory.apache.org/api/gen-docs/latest2/apidocs/

    public static void main(String[] args) throws Exception {

        final String DOMAIN = "dc=world-company,dc=org";
        final String PASSWORD = "secret";

        SpringApplication.run(TpldapapachedirectoryApplication.class, args);

        // Opening a connection
        LdapConnection connection = new LdapNetworkConnection( "localhost", 389 );

        try {
            // Secure binding
            connection.bind("cn=admin,"+DOMAIN, PASSWORD);
        } catch (LdapException e) {
            e.printStackTrace();
        }

        // TEST CASES =====================================================================================================

        LdapQueries ldapQueries = new LdapQueries();

//        ldapQueries.findAllAdm(connection, DOMAIN);
//        ldapQueries.findAllPersons(connection, DOMAIN);
//        ldapQueries.addPerson(connection, DOMAIN, "kmitroglou", "Mitroglou");
//        ldapQueries.deletePerson(connection, DOMAIN, "kmitroglou");
//        ldapQueries.addAttributesToPerson(connection, DOMAIN, "kmitroglou",
//                "givenName", "Mitroflop", "initials", "KM");
//        ldapQueries.removeAttributesToPerson(connection, DOMAIN, "kmitroglou",
//                "givenName", "initials");
//        ldapQueries.replaceAttributesToPerson(connection, DOMAIN, "kmitroglou",
//                "givenName", "Gronaldo", "initials", "MK");
//        ldapQueries.moveAndRenamePerson(connection, "cn=kmitroglou,ou=adm,dc=vinci-melun,dc=org",
//                "cn=kmitroglou,ou=profs,dc=vinci-melun,dc=org", true);

        // END TEST CASES =================================================================================================

        // unbinding
        connection.unBind();

        // CLosing the connection
        connection.close();
    }

}
