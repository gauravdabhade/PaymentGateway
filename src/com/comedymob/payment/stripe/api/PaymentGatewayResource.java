package com.comedymob.payment.stripe.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.comedymob.payment.stripe.beans.RequestBean;
import com.comedymob.payment.stripe.beans.ResponseBean;
import com.comedymob.payment.stripe.service.PaymentGatewayService;

@Path("/paymentgateway")
public class PaymentGatewayResource extends AbstractResource
{

    @POST
    @Path("/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(String request)
    {
        RequestBean requestBean = createRequestBean(request);
        ResponseBean response = PaymentGatewayService.getPaymentGatewayService().chargeCreate(requestBean);
        return createResponse(response);
    }
}
