package com.iat.tpldapapachedirectory.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tp.ldap")
public class GlobalProperties {

    private String domain;
    private String password;
    private String server;
    private int host;

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

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }
}
