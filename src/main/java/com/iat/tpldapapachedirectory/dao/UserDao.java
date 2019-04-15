package com.iat.tpldapapachedirectory.dao;

import com.iat.tpldapapachedirectory.configuration.GlobalProperties;
import com.iat.tpldapapachedirectory.model.User;
import com.iat.tpldapapachedirectory.service.ConnectionService;
import com.iat.tpldapapachedirectory.service.LdapQueries;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class UserDao {

    // documentation : https://directory.apache.org/api/user-guide/6.12-entry.html

    @Autowired
    LdapQueries ldapQueries;

    @Autowired
    ConnectionService connectionService;

    @Autowired
    GlobalProperties globalProperties;

    public ArrayList<User> getAllAdmByIdAndName() throws IOException, LdapException, CursorException {
        LdapNetworkConnection connection = connectionService.openConnection();
        ArrayList<Entry> entries = ldapQueries.findAllAdm(connection, globalProperties.getDomain());
        ArrayList<User> admins = new ArrayList<>();
        for (Entry entry: entries) {
            // TODO : vérifier si le design pattern utilisé ici (LdapQueries + UserDao + UserController) est efficient
            User user = new User();
            user.setCategory(entry.get("radiusTunnelPrivateGroupId").get().toString());
            user.setName(entry.get("sn").get().toString());
            admins.add(user);
        }
        return admins;
    }
}
