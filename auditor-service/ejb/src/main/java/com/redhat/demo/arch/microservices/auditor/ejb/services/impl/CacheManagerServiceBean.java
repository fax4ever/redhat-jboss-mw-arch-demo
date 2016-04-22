/*
 *
 */
package com.redhat.demo.arch.microservices.auditor.ejb.services.impl;

import com.redhat.demo.arch.microservices.auditor.common.dto.PayloadHistory;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoSchemaBuilderException;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
@Startup
@LocalBean
@Lock(LockType.READ)
public class CacheManagerServiceBean {

    @Inject
    private Logger LOG;

    private RemoteCacheManager remoteCacheManager;

    @PostConstruct
    private void init() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("init() - start");
        }

        Properties properties = new Properties();
        try (InputStream is = getClass()
                .getResourceAsStream("/hotrod-client.properties")) {
            properties.load(is);
        } catch (IOException e) {
            LOG.error("", e);
        }
        properties.put("infinispan.client.hotrod.server_list",
                System.getProperty("jboss.datagrid.topology"));

        remoteCacheManager = new RemoteCacheManager(properties);
        SerializationContext serCtx = ProtoStreamMarshaller
                .getSerializationContext(remoteCacheManager);
        configureProtobufMarshaller(serCtx);
        remoteCacheManager.start();

        if (LOG.isDebugEnabled()) {
            LOG.debug("init() - end");
        }
    }

    private void configureProtobufMarshaller(SerializationContext serCtx) {
        // generate and register a Protobuf schema and marshallers based
        // on Note class and the referenced classes (User class)
        ProtoSchemaBuilder protoSchemaBuilder = new ProtoSchemaBuilder();
        String generatedSchema;
        try {
            // generate the 'history.proto' schema file based on the
            // annotations on
            // Memo class and register it with the SerializationContext of the
            // client
            generatedSchema = protoSchemaBuilder
                    .fileName("history.proto")//
                    .packageName(PayloadHistory.class.getPackage().getName())//
                    .addClass(PayloadHistory.class)//
                    .build(serCtx);

            // register the schemas with the server too
            RemoteCache<String, String> metadataCache = remoteCacheManager
                    .getCache(
                            ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
            metadataCache.put("BD3.proto", generatedSchema);

            String errors = metadataCache
                    .get(ProtobufMetadataManagerConstants.ERRORS_KEY_SUFFIX);
            if (errors != null) {
                throw new IllegalStateException(
                        "Some Protobuf schema files contain errors:\n"
                                + errors);
            }

        } catch (ProtoSchemaBuilderException | IOException e) {
            throw new RuntimeException(
                    "An error occurred initializing HotRod protobuf marshaller:",
                    e);
        }
    }

    @PreDestroy
    private void destroy() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("destroy() - start");
        }

        remoteCacheManager.stop();

        if (LOG.isDebugEnabled()) {
            LOG.debug("destroy() - end");
        }
    }

    public <K, V> RemoteCache<K, V> getCache(final String cacheName) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("getCache(String cacheName={}) - start", cacheName);
        }

        RemoteCache<K, V> returnCache = remoteCacheManager.getCache(cacheName,
                true);

        LOG.info("RemoteCache {} initializiation completed.", cacheName);

        if (LOG.isDebugEnabled()) {
            LOG.debug("getCache(String cacheName={}) - end - return value={}",
                    cacheName, returnCache);
        }
        return returnCache;
    }

}
