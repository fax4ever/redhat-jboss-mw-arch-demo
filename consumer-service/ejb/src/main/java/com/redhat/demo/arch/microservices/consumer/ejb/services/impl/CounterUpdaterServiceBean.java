/*
 *
 */
package com.redhat.demo.arch.microservices.consumer.ejb.services.impl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.infinispan.client.hotrod.RemoteCache;

/**
 * The Class CounterUpdaterServiceBean.
 *
 * @author Andrea Battaglia (Red Hat)
 */
@Singleton
@Startup
@LocalBean
@Lock(LockType.READ)
public class CounterUpdaterServiceBean {
    // /** The log. */
    // @Inject
    // private Logger LOG;

    @EJB
    private CacheManagerServiceBean cacheManagerService;
    private RemoteCache<String, Integer> countersCache;

    /**
     * Inits the.
     */
    @PostConstruct
    private void init() {

        countersCache = cacheManagerService.getCache("BPDemo_CountersCache");
    }

    public void updateCounter(String key) {
        Integer value = countersCache.get(key);
        countersCache.put(key, (value == null ? 0 : value) + 1);
    }
}
