package com.iat.tpldapapachedirectory.service;

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
    private GlobalProperties globalProperties;


        String domain = "dc=world-company,dc=org";
        String password = "secret";
//    String domain = configurationProperties.getDomain();
//    String password = configurationProperties.getPassword();


    public GlobalProperties getGlobalProperties() {
        return globalProperties;
    }

    public void setGlobalProperties(GlobalProperties globalProperties) {
        this.globalProperties = globalProperties;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LdapNetworkConnection bindingConnection() {
        // Opening a connection

        LdapNetworkConnection connection = new LdapNetworkConnection("localhost", 389);
        try {
            // Secure binding
            connection.bind("cn=admin,"+ domain, password);
        } catch (
                LdapException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void closeConnection(LdapConnection connection) throws LdapException, IOException {
        // unbinding
        connection.unBind();

        // CLosing the connection
        connection.close();
    }
}
