package com.redhat.demo.arch.microservices.auditor.ejb.services.impl;

import org.infinispan.client.hotrod.RemoteCache;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Fabio Massimo Ercoli
 *         fabio.ercoli@redhat.com
 *         on 23/04/16
 */
@Stateless
public class CounterServiceBean {

    @Inject
    private Logger LOG;

    @EJB
    private CacheManagerServiceBean cacheManagerService;

    private RemoteCache<String, Integer> countersCache;

    @PostConstruct
    private void init() {
        countersCache = cacheManagerService.getCache("Demo_CountersCache");
    }

    public void put(String key, Integer value) {

        countersCache.put(key, value);

        LOG.info("insert into cache key {} value {}", key, value);

    }

    public void remove(String key) {

        countersCache.remove(key);

        LOG.info("remove from cache value {}", key);

    }

}
