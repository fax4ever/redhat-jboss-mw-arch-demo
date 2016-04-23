/*
 *
 */
package com.redhat.demo.arch.microservices.auditor.ejb.services.impl;

import com.redhat.demo.arch.microservices.auditor.common.dto.PayloadHistory;
import org.infinispan.client.hotrod.RemoteCache;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;

/**
 * @author Fabio Massimo Ercoli
 *         fabio.ercoli@redhat.com
 *         on 23/04/16
 */
@Singleton
@Startup
@LocalBean
@Lock(LockType.READ)
public class AuditCounterServiceBean {

    @Inject
    private Logger LOG;

    // true then audit is active
    private boolean active = true;

    @EJB
    private CacheManagerServiceBean cacheManagerService;

    private RemoteCache<String, Integer> countersCache;

    @PostConstruct
    private void init() {
        countersCache = cacheManagerService.getCache("Demo_CountersCache");
    }

    public void on() {
        active = true;
    }

    public void off() {
        active = false;
    }

}
