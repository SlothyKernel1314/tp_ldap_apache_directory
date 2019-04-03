package com.iat.tpldapapachedirectory;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootApplication
public class TpldapapachedirectoryApplication {

    public static void main(String[] args) throws CursorException, LdapException, IOException {

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
//        ldapQueries.addPerson(connection, DOMAIN, "kmitroglou", "Mitroglou");

        // END QUERIES =================================================================================================

        // unbinding
        connection.unBind();

        // CLosing the connection
        connection.close();
    }

}
