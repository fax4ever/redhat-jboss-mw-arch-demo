/*
 *
 */
package com.redhat.demo.arch.microservices.consumer.web.rest.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.redhat.demo.arch.microservices.consumer.ejb.services.impl.AppInfoServiceBean;

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
