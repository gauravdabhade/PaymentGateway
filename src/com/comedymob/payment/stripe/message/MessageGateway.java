package com.comedymob.payment.stripe.message;

import com.comedymob.payment.stripe.beans.RequestBean;
import com.comedymob.payment.stripe.beans.ResponseBean;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class MessageGateway
{
    public ResponseBean sendRequest(String module, RequestBean request) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
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
}
