/*
 *
 */
package com.redhat.demo.arch.microservices.sender.web.rest.resources;

import java.io.IOException;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.slf4j.Logger;

import com.redhat.demo.arch.microservices.sender.ejb.services.impl.NotifierServiceBean;
import com.redhat.demo.arch.microservices.sender.ejb.services.impl.SenderServiceBean;

/**
 * The Class SenderResource.
 */
@Path("send")
@RequestScoped
public class SenderResource {

    private static Random random = new Random();

    /** The log. */
    @Inject
    private Logger LOG;

    @Inject
    private SenderServiceBean senderService;
    @Inject
    private NotifierServiceBean notifierService;

    /**
     * Simple test method.
     */
    @GET
    @Path("")
    public String sendMessage() {
        String payload = null;
        switch (random.nextInt(3)) {
        case 0:
            payload = "RedHat";
            break;
        case 1:
            payload = "BP Oil";
            break;
        case 2:
            payload = "Canary Warf";
            break;
        }

        try {
            senderService.send(payload);
            notifierService.notifySync(payload);
            return "Sent payload: \"" + payload + "\"";
        } catch (IOException e) {
            return "KO";
        }
    }
}
