/*
 *
 */
package com.redhat.demo.arch.microservices.sender.ejb.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;

@Singleton
@LocalBean
public class SenderServiceBean {
    @Inject
    private Logger LOG;

    private HttpClient httpClient;

    @PostConstruct
    private void init() {
        httpClient = new DefaultHttpClient();
    }

    @PreDestroy
    private void destroy() {
        httpClient.getConnectionManager().shutdown();

    }

    public void send(String payload) throws IOException {
        String loadBalancerHost = System.getProperty("jboss.loadbalancer.host");
        int loadBalancerPort = 80;
        try {
            loadBalancerPort = Integer
                    .parseInt(System.getProperty("jboss.loadbalancer.port"));
        } catch (NumberFormatException e) {
        }
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("http").setHost(loadBalancerHost)
                    .setPort(loadBalancerPort)
                    .setPath("/demo-history/rest/history/" + payload);
            HttpPut request = new HttpPut(builder.build());

            HttpResponse response = httpClient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            if ((response.getStatusLine().getStatusCode() == 200)
                    || (response.getStatusLine().getStatusCode() == 204)) {
            } else {
                LOG.warn(
                        "Cannot save payload history: History service unavailable: {}",
                        responseCode);

                // throw new RuntimeException("Failed : HTTP error code : "
                // + response.getStatusLine().getStatusCode());

            }
            request.releaseConnection();
        } catch (Exception ex) {
            LOG.error("ex Code sendPut: " + ex);
            // LOG.error("url:" + url);
            // LOG.error("payload:" + payload);
        }
    }

}
