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

package com.wee0.box.task.impl;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/12 12:22
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleTaskManagerTest {

    @Test
    public void asyncRunRunnable() {
        CompletableFuture.runAsync(() -> {
            System.out.println("begin...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end.");
        });
    }

    @Test
    public void asyncRunSupply() {
        CompletableFuture<String> _future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("begin...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end.");
            return "hello";
        });
        System.out.println("over...");
        _future1.thenAccept((val) -> {
            System.out.println("val: " + val);
        });
    }

    public static void main(String[] args) {
        ExecutorService _service = Executors.newFixedThreadPool(2);
        CompletableFuture<String> _future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("begin...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end.");
            return "hello";
        }, _service);
        System.out.println("over...");
        _future1.thenAccept((val) -> {
            System.out.println("val: " + val);
        });

        _service.shutdown();
    }

}
