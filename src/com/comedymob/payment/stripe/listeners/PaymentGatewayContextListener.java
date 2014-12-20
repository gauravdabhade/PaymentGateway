package com.comedymob.payment.stripe.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PaymentGatewayContextListener implements ServletContextListener
{
    protected static final Logger logger = LogManager.getLogger(PaymentGatewayContextListener.class);

    private Thread queueConsumer = null;

    public void contextInitialized(ServletContextEvent sce)
    {
        if ((queueConsumer == null) || (!queueConsumer.isAlive()))
        {
//            queueConsumer = new Thread(new QueueConsumer());
            queueConsumer.start();
        }
    }

    public void contextDestroyed(ServletContextEvent sce)
    {
        try
        {
            queueConsumer.interrupt();
        }
        catch (Exception ex)
        {
        }
    }
}
