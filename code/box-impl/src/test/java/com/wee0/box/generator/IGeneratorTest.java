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

package com.wee0.box.generator;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/19 15:17
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class IGeneratorTest {

    protected static IGenerator impl = null;


    @Test
    public void test1() {
        Map _mock = Mockito.mock(Map.class);
        System.out.println("_mock:" + _mock);
    }

//    @Test
//    public void test2() {
//        ScriptEngineManager _scriptEngineManager = new ScriptEngineManager();
//        List<ScriptEngineFactory> _factories = _scriptEngineManager.getEngineFactories();
//        for (ScriptEngineFactory _factory : _factories) {
//            System.out.println("Name:" + _factory.getEngineName());
//            System.out.println("Version:" + _factory.getEngineVersion());
//            System.out.println("Language name:" + _factory.getLanguageName());
//            System.out.println("Language version:" + _factory.getLanguageVersion());
//            System.out.println("Extensions:" + _factory.getExtensions());
//            System.out.println("Mime types:" + _factory.getMimeTypes());
//            System.out.println("Names:" + _factory.getNames());
//            System.out.println("getScriptEngine:" + _factory.getScriptEngine());
//        }
//        ScriptEngine _scriptEngine = _scriptEngineManager.getEngineByName("js");
//        System.out.println("_scriptEngine:" + _scriptEngine);
//
//        try (InputStream _stream = ClassLoader.getSystemResourceAsStream("3rd/mock-min.js")) {
//            String _mockJs = IoUtils.impl().readString(_stream);
//            System.out.println("mockJs:" + _mockJs);
//            Object _mockJsResult = _scriptEngine.eval(_mockJs);
//            System.out.println("_mockJsResult:" + _mockJsResult);
//
////            String _js = "Mock.mock({\"number|+1\": 202})";
////            String _js = "Mock.mock(\"@string(5)\")";
////            String _js = "Mock.mock('@date(\"yyyyMMdd\")')";
////            String _js = "Mock.mock('@datetime(\"yyyy-MM-dd A HH:mm:ss\")')";
////            String _js = "Mock.mock('@now()')";
////            String _js = "Mock.mock('@paragraph(2)')";
//            String _js = "Mock.mock('@sentence(3, 5)')";
//            Object _jsResult = _scriptEngine.eval(_js);
//            System.out.println("_jsResult:" + _jsResult + ", class:" + _jsResult.getClass());
//            if (_jsResult instanceof ScriptObjectMirror) {
//                ScriptObjectMirror _objectMirror = (ScriptObjectMirror) _jsResult;
//                System.out.println("_objectMirror:" + _objectMirror);
//                _objectMirror.entrySet().forEach((_entry) -> {
//                    System.out.println("_entry:" + _entry);
//                    System.out.println("key:" + _entry.getKey() + ", val:" + _entry.getValue() + ", type:" + _entry.getValue().getClass());
//                });
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
//
//        String _pattern = "userId|";
//        Assert.assertEquals("", "");
//    }

}
