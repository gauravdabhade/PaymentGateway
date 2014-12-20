package com.comedymob.payment.stripe;

import java.util.HashMap;
import java.util.Map;

import com.comedymob.payment.stripe.beans.RequestBean;
import com.comedymob.payment.stripe.beans.ResponseBean;
import com.comedymob.payment.stripe.constants.IPaymentGatewayConstants;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.stripe.model.Charge;

public class PaymentGateway
{

    public static ResponseBean sendRequest(String module, RequestBean request) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IPaymentGatewayConstants.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String requestQueueName = module;
        String replyQueueName = channel.queueDeclare().getQueue();
        QueueingConsumer consumer = new QueueingConsumer(channel);

        channel.basicConsume(replyQueueName, true, consumer);

        String corrId = java.util.UUID.randomUUID().toString();
        BasicProperties props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();

        Gson gson = new Gson();
        String message = gson.toJson(request, RequestBean.class);
        channel.basicPublish("", requestQueueName, props, message.getBytes());
        ResponseBean response = null;
        while (true)
        {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if (delivery.getProperties().getCorrelationId().equals(corrId))
            {
                String responseStr = new String(delivery.getBody());
                response = gson.fromJson(responseStr, ResponseBean.class);
                break;
            }
        }
        return response;
    }

    public static void main(String[] args)
    {
        RequestBean requestBean = new RequestBean();
        Map<String, String> request = new HashMap<String, String>();
        requestBean.setAction("isSessionValid");
        request.put("token", "9ts74ahro413k7mbr2qv4aqlf9");
        request.put("id", "548c111c44ae3889378cb194");
        request.put("vendor", "user");
        requestBean.setRequest(request);
        try
        {
            ResponseBean responseBean = sendRequest("user-mgmt", requestBean);
            if (responseBean.getCode() == 200)
            {
                Map<String, Object> defaultCardParams = new HashMap<String, Object>();
                Map<String, Object> defaultChargeParams = new HashMap<String, Object>();
                defaultCardParams.put("number", "4242424242424242");
                defaultCardParams.put("exp_month", 12);
                defaultCardParams.put("exp_year", 2015);
                defaultCardParams.put("cvc", "123");
                defaultCardParams.put("name", "Sachin");
                defaultCardParams.put("address_line1", "Pune");
                defaultCardParams.put("address_line2", "Pune");
                defaultCardParams.put("address_city", "Pune");
                defaultCardParams.put("address_zip", "412105");
                defaultCardParams.put("address_state", "MH");
                defaultCardParams.put("address_country", "INDIA");

                defaultChargeParams.put("amount", 100000);
                defaultChargeParams.put("currency", "inr");
                defaultChargeParams.put("card", defaultCardParams);
                Charge createdCharge = Charge.create(defaultChargeParams);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
