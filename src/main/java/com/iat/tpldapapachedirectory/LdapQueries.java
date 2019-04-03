package com.iat.tpldapapachedirectory;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;

import java.io.IOException;

public class LdapQueries {

    public void findAllPersons(LdapConnection connection, String domain) throws LdapException, CursorException, IOException {

        // Root : Dn ; parse : all trees ans subtrees
        EntryCursor cursor = connection.search(domain, "(objectclass=*)", SearchScope.SUBTREE, "*" );
        int nbrEntries = 0;

        while (cursor.next())

        {
            Entry entry = cursor.get();
            System.out.println(entry);
            nbrEntries +=1;

        }

        System.out.println("NOMBRE TOTAL D'ENTREES : " + nbrEntries);
        cursor.close();
    }

    public void findAllAdm(LdapConnection connection, String domain) throws LdapException, CursorException, IOException {

        // Root : ou=adm ; parse : all adm, only adm (one level)
        EntryCursor cursor = connection.search( "ou=adm, "+domain+"", "(objectclass=*)", SearchScope.ONELEVEL, "*" );
        int nbrEntries = 0;

        while (cursor.next())
        {
            Entry entry = cursor.get();
            System.out.println(entry);
            nbrEntries +=1;

        }

        System.out.println("NOMBRE TOTAL D'ENTREES : " + nbrEntries);
        cursor.close();
    }

    public void addPerson(LdapConnection connection, String domain, String cn, String sn) throws LdapException {
        connection.add(
                new DefaultEntry(
                        "cn="+cn+", ou=adm, "+domain+"", // The Dn
                        "ObjectClass: inetOrgPerson",
                        "ObjectClass: organizationalPerson",
                        "ObjectClass: person",
                        "ObjectClass: radiusprofile",
                        "ObjectClass: top",
                        "cn: "+cn+"",
                        "sn: "+sn+"" ) );

        assertTrue(connection.exists("cn="+cn+", ou=adm, "+domain+""));
    }

    private void assertTrue(boolean exists) {
    }
}
