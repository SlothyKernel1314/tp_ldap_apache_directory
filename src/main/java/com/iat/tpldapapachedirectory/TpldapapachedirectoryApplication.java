package com.iat.tpldapapachedirectory;

import com.iat.tpldapapachedirectory.service.ConnectionService;
import com.iat.tpldapapachedirectory.configuration.GlobalProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties(GlobalProperties.class)
public class TpldapapachedirectoryApplication {

    // doc : https://directory.apache.org/api/gen-docs/latest2/apidocs/

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(TpldapapachedirectoryApplication.class, args);

        ConnectionService connectionService = context.getBean(ConnectionService.class);

        connectionService.ldapQueries();
    }

}
