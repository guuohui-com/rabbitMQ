package com.css.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @功能职责: direct 模式 生产者
 * @描述：先创建交换机，在创建队列，然后绑定，之后发消息。但是本次学习可以在监控面板创建并绑定好。
 * @作者: 郭辉
 * @创建时间: 2020-12-02 13:53
 * @copyright Copyright (c) 2020 中国软件与技术服务股份有限公司
 * @company 中国软件与技术服务股份有限公司
 */
public class Producer {
    public static void main(String[] args) {
        //创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/rabbitmq");
        Connection connection = null;
        Channel channel = null;
        try {
            //创建连接
            connection = connectionFactory.newConnection("生产者");
            //通过连接获取通道
            channel = connection.createChannel();
            //通过交换机，生命队列，绑定关系，路由key，发送消息，接收消息
//            String queueName = "queue1";
            /**
             * @Param1 队列名称
             * @Param1 是否要持久化 ，默认durable = false，持久化队列的消息会存盘切不会丢失，但是非持久化队列也会存盘，但是随着副武器重启会丢失。
             * @Param1 是否独占队列
             * @Param1 是否自动删除队列（如在队列是空）
             * @Param1 携带附属参数
             *
             * */
//            channel.queueDeclare(queueName,true,false,false,null);

            //交换机名称
            String exchange = "direct-exchange";
            //交换机类型
            String type = "direct";
            //路由key
            String routingKey = "css";
            //准备消息内容
            String message = "hello world,I am direct-exchange";
            //发送给队列
            channel.basicPublish(exchange, routingKey, null, message.getBytes());
            System.out.println("消息发送成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
