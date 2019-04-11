package com.iat.tpldapapachedirectory;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.*;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LdapQueries {

    /**
     * Performs search using a search of all persons in a LDAP domain (SearchScope.SearchScope.SUBTREE)
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     */
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

    /**
     * Performs search using a search of all persons in a LDAP organizational unit (SearchScope.ONELEVEL)
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     */
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

    /**
     * Add an entry (a person) to the LDAP server
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     * @param cn : a LDAP common name
     * @param sn : a LDAP surname
     */
    public void addPerson(LdapConnection connection, String domain, String cn, String sn) throws LdapException {
        connection.add(
                new DefaultEntry(
                        "cn="+cn+", ou=adm, "+domain+"", // the dn
                        "ObjectClass: inetOrgPerson",
                        "ObjectClass: organizationalPerson",
                        "ObjectClass: person",
                        "ObjectClass: radiusprofile",
                        "ObjectClass: top",
                        "cn: "+cn+"",
                        "sn: "+sn+"" ) );

        assertTrue(connection.exists("cn="+cn+", ou=adm, "+domain+""));
    }

    /**
     * Delete the entry (a person) with the given distinguished name to the LDAP server
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     * @param cn : a LDAP common name
     */
    public void deletePerson(LdapConnection connection, String domain, String cn) throws Exception {

        connection.delete("cn="+cn+", ou=adm, "+domain+"");
    }

    /**
     * Applies all the modifications (add attributes + values = ModificationOperation.ADD_ATTRIBUTE)...
     * ... to the entry (a person) specified by its distinguished name
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     * @param cn : a LDAP common name
     * @param attributeId1 : first attribute to add
     * @param value1 : first value to add
     * @param attributeId2 : second attribute to add
     * @param value2 : second value to add
     *
     */
    public void addAttributesToPerson(LdapConnection connection, String domain, String cn, String attributeId1,
                                      String value1, String attributeId2, String value2) throws LdapException {
        Modification addedGivenName = new DefaultModification(ModificationOperation.ADD_ATTRIBUTE, attributeId1,
                value1);
        Modification addedInitials = new DefaultModification(ModificationOperation.ADD_ATTRIBUTE, attributeId2,
                value2);

        connection.modify("cn="+cn+", ou=adm, "+domain+"", addedGivenName, addedInitials); // the dn
    }

    /**
     * Applies all the modifications (remove attributes = ModificationOperation.REMOVE_ATTRIBUTE)...
     * ... to the entry (a person) specified by its distinguished name
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     * @param cn : a LDAP common name
     * @param attributeId1 : first attribute to remove
     * @param attributeId2 : second attribute to remove
     *
     */
    public void removeAttributesToPerson(LdapConnection connection, String domain, String cn,
                                         String attributeId1, String attributeId2) throws LdapException {
        Modification removedGivenName = new DefaultModification(ModificationOperation.REMOVE_ATTRIBUTE, attributeId1);
        Modification removedInitials = new DefaultModification(ModificationOperation.REMOVE_ATTRIBUTE, attributeId2);

        connection.modify("cn="+cn+", ou=adm, "+domain+"", removedGivenName, removedInitials); // the dn
    }

    /**
     * Applies all the modifications (replace attributes + values = ModificationOperation.REPLACE_ATTRIBUTE)...
     * ... to the entry (a person) specified by its distinguished name
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     * @param cn : a LDAP common name
     * @param attributeId1 : first attribute to replace
     * @param value1 : first value to replace
     * @param attributeId2 : second attribute to replace
     * @param value2 : second value to replace
     *
     */
    public void replaceAttributesToPerson(LdapConnection connection, String domain, String cn, String attributeId1,
                                          String value1, String attributeId2, String value2) throws LdapException {
        Modification replacedGivenName = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE,
                attributeId1, value1);
        Modification replacedInitials = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE,
                attributeId2, value2);

        connection.modify("cn="+cn+", ou=adm, "+domain+"", replacedGivenName, replacedInitials); // the dn
    }

    /**
     * Move and rename the given entryDn. The old relative distinguished name will be deleted.
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param oldDn : The old relative distinguished name will be deleted
     * @param newDn : The new relative distinguished name
     * @param deleteOldDn : Tells if the old relative distinguished name must be removed (true = removed)
     */
    public void moveAndRenamePerson(LdapConnection connection, String oldDn, String newDn,
                                    boolean deleteOldDn) throws LdapException {
        connection.moveAndRename(oldDn, newDn, deleteOldDn);
    }

    /**
     * Performs search using a search of all organizational unit in a LDAP domain (SearchScope.SearchScope.ONELEVEL)
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     */
    public void findAllOu(LdapConnection connection, String domain) throws LdapException, CursorException, IOException {

        EntryCursor cursor = connection.search(domain, "(objectclass=organizationalUnit)", SearchScope.ONELEVEL, "*" );
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

    /**
     * Add an entry (an organizational unit) to the LDAP server
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     * @param ou : an organizational unit
     */
    public void addOu(LdapConnection connection, String domain, String ou) throws LdapException {
        connection.add(
                new DefaultEntry(
                        "ou="+ou+", "+domain+"", // the dn
                        "ObjectClass: organizationalUnit",
                        "ObjectClass: top",
                        "ou: "+ou+"") );

        assertTrue(connection.exists("ou="+ou+", "+domain+""));
    }

    /**
     * Delete the entry (an organizational unit) with the given distinguished name to the LDAP server
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param domain : a LDAP domain
     */
    public void deleteOu(LdapConnection connection, String domain,String ou) throws Exception {

        connection.delete("ou="+ou+", "+domain+"");
    }

    /**
     * Renames the given entryDn with new relative distinguished name and deletes the old relative distinguished name
     * if deleteOldRdn is set to true
     * @param connection : an instance of LdapNetworkConnection (interface LdapConnection)
     * @param entryDn : The old relative distinguished name will be deleted
     * @param newDn : The new relative distinguished name
     * @param deleteOldDn : Tells if the old relative distinguished name must be removed (true = removed)
     */
    public void renameOu(LdapConnection connection, String entryDn, String newDn,
                                    boolean deleteOldDn) throws LdapException {
        connection.moveAndRename(entryDn, newDn, deleteOldDn);
    }



    private void assertTrue(boolean exists) { }
}
