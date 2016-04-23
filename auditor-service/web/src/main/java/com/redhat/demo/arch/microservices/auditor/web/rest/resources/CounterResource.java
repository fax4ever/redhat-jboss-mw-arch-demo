package com.redhat.demo.arch.microservices.auditor.web.rest.resources;

import com.redhat.demo.arch.microservices.auditor.ejb.services.impl.CounterServiceBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * @author Fabio Massimo Ercoli
 *         fabio.ercoli@redhat.com
 *         on 23/04/16
 */
@Path("counter")
@RequestScoped
public class CounterResource {

    @Inject
    private CounterServiceBean service;

    @POST
    @Path("{key}/{value}")
    public void put(@PathParam("key") String key, @PathParam("value") Integer value) {
        service.put(key, value);
    }

    @DELETE
    @Path("{key}")
    public void remove(@PathParam("key") String key) {
        service.remove(key);
    }

    @GET
    public String ciao() {

        return "ciao";

    }

}
