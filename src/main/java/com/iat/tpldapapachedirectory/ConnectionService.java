package com.iat.tpldapapachedirectory;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(ConfigurationProperties.class)
public class ConnectionService {

    ConfigurationProperties configurationProperties = new ConfigurationProperties();

        final String domain = "dc=world-company,dc=org";
        final String password = "secret";
//    String domain = configurationProperties.getDomain();
//    String password = configurationProperties.getPassword();

    public String getDomain() {
        return domain;
    }

    public String getPassword() {
        return password;
    }

    public LdapConnection bindingConnection() {
        // Opening a connection
        LdapConnection connection = new LdapNetworkConnection("localhost", 389);
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
