/*
 *
 */
package com.redhat.demo.arch.microservices.history.web.rest.resources;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.redhat.demo.arch.microservices.history.web.constants.AppInfo;

/**
 * The Class SenderResource.
 */
@Path("version")
@RequestScoped
public class VersionResource {

    /**
     * Simple test method.
     */
    @GET
    @Path("")
    public String getVersion() {
        return AppInfo.NAME.getValue() + " - Version "
                + AppInfo.VERSION.getValue();
    }
}
