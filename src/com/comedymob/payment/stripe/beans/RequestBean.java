package com.comedymob.payment.stripe.beans;

import java.util.Map;

public class RequestBean
{
    private String action;
    private Map<String, String> request;

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getAction()
    {
        return action;
    }

    public Map<String, String> getRequest()
    {
        return request;
    }

    public void setRequest(Map<String, String> request)
    {
        this.request = request;
    }

}
