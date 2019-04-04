package com.iat.tpldapapachedirectory;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpldapapachedirectoryApplication {

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

        // QUERIES =====================================================================================================

        LdapQueries ldapQueries = new LdapQueries();

//        ldapQueries.findAllAdm(connection, DOMAIN);
//        ldapQueries.findAllPersons(connection, DOMAIN);
        ldapQueries.addPerson(connection, DOMAIN, "kmitroglou", "Mitroglou");
//        ldapQueries.deletePerson(connection, DOMAIN, "kmitroglou");
        ldapQueries.addAttributesToPerson(connection, DOMAIN, "kmitroglou",
                "givenName", "Mitroflop", "initials", "KM");
//        ldapQueries.removeAttributesToPerson(connection, DOMAIN, "kmitroglou",
//                "givenName", "initials");

        // END QUERIES =================================================================================================

        // unbinding
        connection.unBind();

        // CLosing the connection
        connection.close();
    }

}
