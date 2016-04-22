/**
 *
 */
package com.redhat.demo.arch.microservices.auditor.ejb.services.impl;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
@Singleton
@Startup
@LocalBean
@Lock(LockType.READ)
public class AppInfoServiceBean {
    @Inject
    private Logger LOG;

    private String appName;
    private String appVersion;

    @PostConstruct
    private void init() {
        try (InputStream is = getClass()
                .getResourceAsStream("/appinfo.properties")) {
            Properties appInfo = new Properties();
            appInfo.load(is);
            appName = appInfo.getProperty("app.name");
            appVersion = "Version " + appInfo.getProperty("app.version");
        } catch (Exception e) {
            LOG.warn("Error reading application info text file: {}",
                    e.getMessage());
        }
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }
}
