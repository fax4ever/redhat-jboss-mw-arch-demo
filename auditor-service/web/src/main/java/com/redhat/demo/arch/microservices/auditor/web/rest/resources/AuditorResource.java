package com.redhat.demo.arch.microservices.auditor.web.rest.resources;

import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Fabio Massimo Ercoli
 *         fabio.ercoli@redhat.com
 *         on 22/04/16
 */
@Path("auditor")
@RequestScoped
public class AuditorResource {

    @Inject
    private Logger LOG;

    @POST
    public void on() {

        LOG.info("ON");

    }

    @DELETE
    public void off() {

        LOG.info("OFF");

    }

}
