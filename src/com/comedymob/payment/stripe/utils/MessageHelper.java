package com.comedymob.payment.stripe.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MessageHelper
{
    private static final Logger logger = LogManager.getLogger(MessageHelper.class);
    private Properties prop = new Properties();
    private static MessageHelper messageHelper = new MessageHelper();

    private MessageHelper()
    {
        init();
    }

    private void init()
    {
        InputStream in = getClass().getResourceAsStream("/com/comedymob/user/files/messages_en.properties");
        try
        {
            prop.load(in);
        }
        catch (IOException e)
        {
            logger.error("Failed to load messages_en.properties file.", e);
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }

    public static MessageHelper instance()
    {
        return messageHelper;
    }

    public String getMessage(String key)
    {
        return prop.getProperty(key);
    }
}
