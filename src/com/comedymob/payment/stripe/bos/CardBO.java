package com.comedymob.payment.stripe.bos;

import java.io.Serializable;

public class CardBO implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String token;
    private String amount;
    private String currency;

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

}
