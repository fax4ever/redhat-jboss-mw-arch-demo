package com.redhat.demo.arch.microservices.auditor.web.rest.resources;

import com.redhat.demo.arch.microservices.auditor.ejb.services.impl.AuditCounterServiceBean;
import com.redhat.demo.arch.microservices.auditor.ejb.services.impl.AuditHistoryServiceBean;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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

    @Inject
    private AuditCounterServiceBean counterService;

    @Inject
    private AuditHistoryServiceBean historyService;

    @POST
    public void on() {

        LOG.debug("activating audit service");

        counterService.on();
        historyService.on();

        LOG.info("audit service is active");

    }

    @DELETE
    public void off() {

        LOG.debug("turing off audit service");

        counterService.off();
        historyService.off();

        LOG.info("audit service is off");

    }

}
