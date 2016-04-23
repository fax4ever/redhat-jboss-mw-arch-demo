/*
 *
 */
package com.redhat.demo.arch.microservices.auditor.ejb.services.impl;

import com.redhat.demo.arch.microservices.auditor.ejb.listener.RemoteCacheListener;
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

    public static final String DEMO_COUNTERS_CACHE = "Demo_CountersCache";

    @Inject
    private Logger LOG;

    @Inject
    private RemoteCacheListener listener;

    @EJB
    private CacheManagerServiceBean cacheManagerService;

    private RemoteCache<String, Integer> countersCache;

    private boolean activeListener = true;

    @PostConstruct
    private void init() {
        countersCache = cacheManagerService.getCache(DEMO_COUNTERS_CACHE);
        countersCache.addClientListener(listener);
        listener.setCacheName(DEMO_COUNTERS_CACHE);
    }

    public void on() {

        if (activeListener) {
            return;
        }

        countersCache.addClientListener(listener);
        activeListener = true;

    }

    public void off() {

        countersCache.removeClientListener(listener);
        activeListener = false;

    }

}
