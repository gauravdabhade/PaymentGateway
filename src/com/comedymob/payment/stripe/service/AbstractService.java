package com.comedymob.payment.stripe.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.comedymob.payment.stripe.beans.ResponseBean;

public abstract class AbstractService
{
    protected static final Logger logger = LogManager.getLogger(AbstractService.class);

    private static PaymentGatewayService paymentGatewayService = new PaymentGatewayService();

    protected ResponseBean createSuccessResponseBean(int code, Object data)
    {
        ResponseBean response = new ResponseBean();
        response.setCode(code);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    protected ResponseBean createErrorResponseBean(int code, String errorMessage)
    {
        ResponseBean response = new ResponseBean();
        response.setCode(code);
        response.setSuccess(false);
        response.setMessage(errorMessage);
        return response;
    }

    public static PaymentGatewayService getPaymentGatewayService()
    {
        return paymentGatewayService;
    }
}
