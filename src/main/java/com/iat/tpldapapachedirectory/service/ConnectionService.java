package com.iat.tpldapapachedirectory.service;

import com.iat.tpldapapachedirectory.LdapQueries;
import com.iat.tpldapapachedirectory.configuration.GlobalProperties;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConnectionService {

    @Autowired
    GlobalProperties globalProperties;

    @Autowired
    LdapQueries ldapQueries;

    public LdapNetworkConnection openConnection() {

        // opening a connection
        LdapNetworkConnection connection = new LdapNetworkConnection(globalProperties.getServer(),
                globalProperties.getHost());
        try {
            // secure binding
            connection.bind("cn=admin,"+ globalProperties.getDomain(), globalProperties.getPassword());
        } catch (
                LdapException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void closeConnection(LdapConnection connection) throws LdapException, IOException {

        // unbinding
        connection.unBind();

        // closing the connection
        connection.close();
    }

    public void ldapQueries() throws Exception {

        LdapNetworkConnection connection = openConnection();

        // TEST CASES ==================================================================================================

        ldapQueries.findAllAdm(connection, globalProperties.getDomain());
//        ldapQueries.findAllPersons(connection, globalProperties.getDomain());
//        ldapQueries.addPerson(connection, globalProperties.getDomain(), "kmitroglou", "Mitroglou");
//        ldapQueries.deletePerson(connection, globalProperties.getDomain(), "kmitroglou");
//        ldapQueries.addAttributesToPerson(connection, globalProperties.getDomain(), "kmitroglou",
//                "givenName", "Mitroflop", "initials", "KM");
//        ldapQueries.removeAttributesToPerson(connection, globalProperties.getDomain(), "kmitroglou",
//                "givenName", "initials");
//        ldapQueries.replaceAttributesToPerson(connection, globalProperties.getDomain(), "kmitroglou",
//                "givenName", "Gronaldo", "initials", "MK");
//        ldapQueries.moveAndRenamePerson(connection, "cn=kmitroglou,ou=adm,dc=vinci-melun,dc=org",
//                "cn=kmitroglou,ou=profs,dc=vinci-melun,dc=org", true);
//        ldapQueries.findAllOu(connection, globalProperties.getDomain());
//        ldapQueries.addOu(connection, globalProperties.getDomain(), "pions");
//        ldapQueries.deleteOu(connection, globalProperties.getDomain(), "pions");
//        ldapQueries.renameOu(connection, "ou=pions,dc=vinci-melun,dc=org",
//                "ou=aed,dc=vinci-melun,dc=org", true);

        // END TEST CASES ==============================================================================================

        closeConnection(connection);
    }
}
