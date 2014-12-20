package com.comedymob.payment.stripe.api;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.comedymob.payment.stripe.beans.RequestBean;
import com.comedymob.payment.stripe.beans.ResponseBean;
import com.google.gson.Gson;

public class AbstractResource
{
    protected static final Logger logger = LogManager.getLogger(AbstractResource.class);

    protected Response createResponse(ResponseBean response)
    {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(response);
        return Response.status(response.getCode()).entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
    }

    protected RequestBean createRequestBean(String request)
    {
        Gson gson = new Gson();
        RequestBean requestBean = gson.fromJson(request, RequestBean.class);
        return requestBean;
    }
}
