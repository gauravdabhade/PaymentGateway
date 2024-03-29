package com.comedymob.payment.stripe.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class PaymentGatewayApplication extends Application
{
    @Override
    public Set<Class<?>> getClasses()
    {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        //register all resources
        classes.add(PaymentGatewayResource.class);
        return classes;
    }
}
