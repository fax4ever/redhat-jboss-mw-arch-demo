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
 * The Class HistoryCacheServiceBean.
 *
 * @author Andrea Battaglia (Red Hat)
 */
@Singleton
@Startup
@LocalBean
@Lock(LockType.READ)
public class HistoryCacheServiceBean {
    /** The log. */
    @Inject
    private Logger LOG;

    @EJB
    private CacheManagerServiceBean cacheManagerService;

    private RemoteCache<PayloadHistory, PayloadHistory> countersCache;
    private QueryFactory<Query> qf;

    /**
     * Inits the.
     */
    @PostConstruct
    private void init() {
        countersCache = cacheManagerService.getCache("Demo_HistoryCache");
        qf = Search.getQueryFactory(countersCache);
    }

    public void store(String payload) {
        PayloadHistory payloadHistory = new PayloadHistory();
        payloadHistory.setPayload(payload);
        payloadHistory.setTimestamp(new Date());
        countersCache.put(payloadHistory, payloadHistory);
    }

    public Map<String, Long> countHistoryByLastMins(final Integer minutes) {
        Map<String, Long> counters = null;
        try {

            // Query query = qf.from(Book.class)
            // .select(Expression.property("author"),
            // Expression.count("title"))
            // .having("title").like("%engine%").groupBy("author")
            // .toBuilder().build();
            // results.get(0)[0] will contain the first matching entry's author
            // // results.get(0)[1] will contain the first matching entry's
            // title
            // List<Object[]> results = query.list();

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.add(Calendar.MINUTE, minutes * -1);
            Query query = null;
            QueryBuilder<Query> qb = qf.from(PayloadHistory.class)//
                    .select(Expression.property("payload"),
                            Expression.count("timestamp"))//
                    .having("timestamp").gte(calendar.getTime())//
                    .toBuilder();
            qb.groupBy("payload");//

            query = qb.build();
            List<Object[]> results = query.list();

            counters = new TreeMap<>();
            for (Object[] result : results) {
                counters.put((String) result[0], (Long) result[1]);
            }

        } catch (Exception e) {
            LOG.error("Error find results by piva", e);
        }
        return counters;
    }

}
