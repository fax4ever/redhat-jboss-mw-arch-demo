package com.redhat.demo.arch.microservices.auditor.web.rest.resources;

import com.redhat.demo.arch.microservices.auditor.ejb.services.impl.HistoryServiceBean;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Fabio Massimo Ercoli
 *         fabio.ercoli@redhat.com
 *         on 23/04/16
 */
@Path("history/{value}")
public class HistoryResource {

    @Inject
    private HistoryServiceBean service;

    @POST
    public void add(@PathParam("value") String value) {

        service.add(value);

    }

    @DELETE
    public void remove(@PathParam("value") String value) {

        service.remove(value);

    }

}
