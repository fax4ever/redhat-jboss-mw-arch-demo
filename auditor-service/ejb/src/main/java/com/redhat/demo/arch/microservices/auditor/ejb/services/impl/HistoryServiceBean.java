package com.redhat.demo.arch.microservices.auditor.ejb.services.impl;

import com.redhat.demo.arch.microservices.auditor.common.dto.PayloadHistory;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Expression;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author Fabio Massimo Ercoli
 *         fabio.ercoli@redhat.com
 *         on 23/04/16
 */
@Stateless
public class HistoryServiceBean {

    @Inject
    private Logger LOG;

    @EJB
    private CacheManagerServiceBean cacheManagerService;

    private RemoteCache<PayloadHistory, PayloadHistory> countersCache;
    private QueryFactory<Query> qf;

    @PostConstruct
    private void init() {

        countersCache = cacheManagerService.getCache("Demo_HistoryCache");
        qf = Search.getQueryFactory(countersCache);

    }

    public void add(String value) {

        PayloadHistory payloadHistory = new PayloadHistory();
        payloadHistory.setPayload(value);
        payloadHistory.setTimestamp(new Date());

        countersCache.put(payloadHistory, payloadHistory);

        LOG.info("add to history cache {} time {}", payloadHistory.getPayload(), payloadHistory.getTimestamp());

    }

    public void remove(String value) {

        Query query = qf.from(PayloadHistory.class)
                .select(Expression.property("timestamp"))
                .having("payload")
                .equal(value)
                .toBuilder()
                .build();

        List<Object[]> list = query.list();
        for (Object[] date : list) {

            PayloadHistory payloadHistory = new PayloadHistory();
            payloadHistory.setTimestamp(new Date((Long) date[0]));
            payloadHistory.setPayload(value);

            countersCache.remove(payloadHistory);

            LOG.info("remove from history cache {} time {}", payloadHistory.getPayload(), payloadHistory.getTimestamp());
        }

    }

}
