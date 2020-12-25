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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/5 7:52
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class RabbitMQTest {

    private static final String QUEUE1 = "Queue1";
    private static final String QUEUE2 = "Queue2";

    private static ConnectionFactory _factory;

    @BeforeClass
    public static void _before() {
        _factory = new ConnectionFactory();
        _factory.setHost("192.168.1.215");
        _factory.setPort(5672);
        _factory.setVirtualHost("/test1");
        _factory.setUsername("test1");
        _factory.setPassword("123456");
    }

    @AfterClass
    public static void _after() {
    }

    @Test
    public void testHello() {
        try (Connection _conn = _factory.newConnection(); Channel _channel = _conn.createChannel();) {
//            final String _MSG = "Hello World!";
            _channel.queueDeclare(QUEUE1, false, false, false, null);
            for (int _i = 0; _i < 10; _i++) {
                String _msg = "Hello " + _i;
                _channel.basicPublish("", QUEUE1, null, _msg.getBytes());
                System.out.println(" [x] sent: " + _msg);
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

}
