package com.comedymob.payment.stripe.beans;

import java.io.Serializable;

public class ResponseBean implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -2463548436112261267L;
    private boolean success;
    private int code;
    private String message;
    private Object data;

    public ResponseBean()
    {
        // TODO Auto-generated constructor stub
    }

    public ResponseBean(int code, String message)
    {
        // TODO Auto-generated constructor stub
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
