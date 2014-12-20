package com.comedymob.payment.stripe.service;

import java.util.HashMap;
import java.util.Map;

import com.comedymob.payment.stripe.beans.RequestBean;
import com.comedymob.payment.stripe.beans.ResponseBean;
import com.comedymob.payment.stripe.constants.ICardBO;
import com.comedymob.payment.stripe.constants.IHttpConstants;
import com.comedymob.payment.stripe.constants.IMessages;
import com.comedymob.payment.stripe.constants.IPaymentGatewayConstants;
import com.comedymob.payment.stripe.utils.MessageHelper;
import com.stripe.model.Charge;

public class PaymentGatewayService extends AbstractService
{
    public ResponseBean chargeCreate(RequestBean requestBean)
    {
        ResponseBean response = null;
        try
        {
            Map<String, Object> defaultChargeParams = new HashMap<String, Object>();
            Map<String, Object> defaultCardParams = new HashMap<String, Object>();

            defaultCardParams.put(IPaymentGatewayConstants.TOKEN, requestBean.getRequest().get(ICardBO.TOKEN));
            defaultChargeParams.put(IPaymentGatewayConstants.AMOUNT, requestBean.getRequest().get(ICardBO.AMOUNT));
            defaultChargeParams.put(IPaymentGatewayConstants.CURRENCY, requestBean.getRequest().get(ICardBO.CURRENCY));
            defaultChargeParams.put(IPaymentGatewayConstants.CARD, defaultCardParams);
            Charge createdCharge = Charge.create(defaultChargeParams);
            response = createSuccessResponseBean(IHttpConstants.SUCCESS,
                    MessageHelper.instance().getMessage(IMessages.PAYMENT_SUCCESS));
        }
        catch (Exception e)
        {
            response = createErrorResponseBean(IHttpConstants.INTERNAL_SERVER_ERROR,
                    MessageHelper.instance().getMessage(IMessages.PAYMENT_FAIL) + " " + e.getLocalizedMessage());
        }
        return response;
    }
}
