package com.github.prerna.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
//@Import(org.springframework.boot.autoconfigure.h2.H2ConsoleProperties.class)
//@Configuration
public class H2Config {
    private static final Log logger = LogFactory.getLog(H2Config.class);

//    @Bean
    public ServletRegistrationBean<WebServlet> h2Console(H2ConsoleProperties properties,
                                                         ObjectProvider<DataSource> dataSource) {
        String path = properties.getPath();
        String urlMapping = path + (path.endsWith("/") ? "*" : "/*");
        ServletRegistrationBean<WebServlet> registration = new ServletRegistrationBean<>(new WebServlet(), urlMapping);
        H2ConsoleProperties.Settings settings = properties.getSettings();
        if (settings.isTrace()) {
            registration.addInitParameter("trace", "");
        }
        if (settings.isWebAllowOthers()) {
            registration.addInitParameter("webAllowOthers", "");
        }
        dataSource.ifAvailable((available) -> {
            try (Connection connection = available.getConnection()) {
                logger.info("H2 console available at '" + path + "'. Database available at '"
                        + connection.getMetaData().getURL() + "'");
            }
            catch (SQLException ex) {
                // Continue
            }
        });
        return registration;
    }

}
