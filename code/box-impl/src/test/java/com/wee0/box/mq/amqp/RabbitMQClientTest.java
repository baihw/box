/*
 * Copyright (c) 2019-present, wee0.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wee0.box.mq.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/5 7:56
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
final class RabbitMQClientTest {

    private static final String QUEUE1 = "Queue1";
    private static final String QUEUE2 = "Queue2";

    private static final ConnectionFactory _factory = new ConnectionFactory();

    static {
        _factory.setHost("192.168.1.215");
        _factory.setPort(5672);
        _factory.setVirtualHost("/test1");
        _factory.setUsername("test1");
        _factory.setPassword("123456");
    }

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection _conn = _factory.newConnection();
        Channel _channel = _conn.createChannel();
        _channel.queueDeclare(QUEUE1, false, false, false, null);

        DeliverCallback _deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received: " + message);
        };
        _channel.basicConsume(QUEUE1, true, _deliverCallback, consumerTag -> {
        });

    }

}
