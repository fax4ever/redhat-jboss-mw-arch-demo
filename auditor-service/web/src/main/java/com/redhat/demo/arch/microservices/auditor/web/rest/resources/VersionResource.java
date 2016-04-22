/*
 *
 */
package com.redhat.demo.arch.microservices.auditor.web.rest.resources;

import com.redhat.demo.arch.microservices.auditor.ejb.services.impl.AppInfoServiceBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * The Class SenderResource.
 */
@Path("version")
@RequestScoped
public class VersionResource {

    @Inject
    private AppInfoServiceBean appInfoService;

    /**
     * Simple test method.
     */
    @GET
    @Path("")
    public String getVersion() {
        return appInfoService.getAppName() + " - "
                + appInfoService.getAppVersion();
    }
}
