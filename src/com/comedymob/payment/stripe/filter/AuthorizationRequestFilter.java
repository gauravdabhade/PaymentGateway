package com.comedymob.payment.stripe.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.comedymob.payment.stripe.beans.RequestBean;
import com.comedymob.payment.stripe.beans.ResponseBean;
import com.comedymob.payment.stripe.constants.IMessages;
import com.comedymob.payment.stripe.message.MessageGateway;
import com.comedymob.payment.stripe.utils.MessageHelper;
import com.google.gson.JsonObject;

@Provider
public class AuthorizationRequestFilter implements ContainerRequestFilter
{
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        String tokenString = requestContext.getHeaderString("token");
        if (!tokenString.contains("&"))
        {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(MessageHelper.instance().getMessage(IMessages.TOKEN_FORMAT_INVALID_MSG)).build());
        }
        String[] data = tokenString.split("&");
        String token = data[0];
        String vendor = data[1];
        String userId = data[2];

        MessageGateway messageGateway = new MessageGateway();
        boolean isSessionValid = false;
        try
        {
            String RPC_QUEUE_NAME = "user-mgmt";
            RequestBean requestBean = new RequestBean();
            Map<String, String> request = new HashMap<String, String>();
            requestBean.setAction("isSessionValid");
            request.put("token", token);
            request.put("id", userId);
            request.put("vendor", vendor);
            requestBean.setRequest(request);
            ResponseBean response = messageGateway.sendRequest(RPC_QUEUE_NAME, requestBean);
            isSessionValid = ((JsonObject) response.getData()).get("isSessionValid").getAsBoolean();
        }
        catch (Exception e)
        {
            // ignore
        }
        if (!isSessionValid)
        {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(MessageHelper.instance().getMessage(IMessages.SESSION_INVALID_MSG)).build());
        }
    }
}
