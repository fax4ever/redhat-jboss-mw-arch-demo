/*
 *
 */
package com.redhat.demo.arch.microservices.auditor.ejb.services.impl;

import com.redhat.demo.arch.microservices.auditor.common.dto.PayloadHistory;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Expression;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryBuilder;
import org.infinispan.query.dsl.QueryFactory;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.*;

/**
 * @author Fabio Massimo Ercoli
 *         fabio.ercoli@redhat.com
 *         on 23/04/16
 */
@Singleton
@Startup
@LocalBean
@Lock(LockType.READ)
public class HistoryServiceBean {

    @Inject
    private Logger LOG;

    @EJB
    private CacheManagerServiceBean cacheManagerService;

    private RemoteCache<PayloadHistory, PayloadHistory> countersCache;

    @PostConstruct
    private void init() {
        countersCache = cacheManagerService.getCache("Demo_HistoryCache");
    }

}
