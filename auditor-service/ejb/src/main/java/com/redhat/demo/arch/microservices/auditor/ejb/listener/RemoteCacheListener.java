package com.redhat.demo.arch.microservices.auditor.ejb.listener;

import org.infinispan.client.hotrod.annotation.*;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryExpiredEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryRemovedEvent;
import org.slf4j.Logger;

import javax.inject.Inject;

/**
 * @author Fabio Massimo Ercoli
 *         fabio.ercoli@redhat.com
 *         on 23/04/16
 */
@ClientListener
public class RemoteCacheListener {

    @Inject
    private Logger LOG;

    private String cacheName;

    @ClientCacheEntryCreated
    public void handleCreatedEvent(ClientCacheEntryCreatedEvent e) {
        LOG.info("New entry created! Key: {}. Version: {}. Cache: {}", e.getKey(), e.getVersion(), cacheName);
    }

    @ClientCacheEntryModified
    public void handleModifiedEvent(ClientCacheEntryModifiedEvent e) {
        LOG.info("Entry updated! Key: {}. Version: {}. Cache: {}", e.getKey(), e.getVersion(), cacheName);
    }

    @ClientCacheEntryExpired
    public void handleExpiredEvent(ClientCacheEntryExpiredEvent e) {
        LOG.info("Entry expired! Key: {}. Cache: {}", e.getKey(), cacheName);
    }

    @ClientCacheEntryRemoved
    public void handleRemovedEvent(ClientCacheEntryRemovedEvent e) {
        LOG.info("Entry evicted! Key: {}. Cache: {}", e.getKey(), cacheName);
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

}
