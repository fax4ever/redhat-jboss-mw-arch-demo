/*
 * 
 */
package com.redhat.demo.arch.microservices.sender.ejb.cdi.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SLF4JProducer {
    @Produces
    public Logger produce(InjectionPoint ip){
            return LoggerFactory.getLogger(ip.getMember().getDeclaringClass().getName());
    }
}
