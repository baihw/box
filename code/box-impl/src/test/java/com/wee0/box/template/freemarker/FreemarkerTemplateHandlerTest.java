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

package com.wee0.box.template.freemarker;

import com.wee0.box.BoxConfig;
import com.wee0.box.template.ITemplateHandler;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/19 15:17
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class FreemarkerTemplateHandlerTest {

    protected static ITemplateHandler impl = FreemarkerTemplateHandler.me();

    private static final Map<String, Object> _DATA1 = new HashMap<>(16);

    static {
        _DATA1.put("name", "tempTemplate");
        _DATA1.put("userName", "baiHuaWei");
    }

    @Test
    public void processText() {
        Assert.assertEquals("hello tempTemplate!", impl.processText("hello ${name}!", _DATA1));
        Assert.assertEquals("hello baiHuaWei!", impl.processText("hello ${userName}!", _DATA1));
    }

    @Test
    public void process() {
        Map<String, Object> _entityModel = new HashMap<>(32);
        _entityModel.put("entityPackage", "com.wee0.test.project1.module1.entity");
        _entityModel.put("entityBase", "com.wee0.box.sql.entity.BaseEntity");
        _entityModel.put("author", "baihw");
        _entityModel.put("createDate", "2019/10/20");
        _entityModel.put("desc", "description");
//        _entityModel.put("desc_extend", "");
        _entityModel.put("tableName", "table_name");
        _entityModel.put("entityName", "entityName");
        List<Map<String, Object>> _columns = new ArrayList<>(8);
        for (int _i = 0; _i < 8; _i++) {
            Map<String, Object> _column = new HashMap<>(8);
            _column.put("columnComment", "comment" + _i);
            _column.put("columnName", "name" + _i);
            _column.put("columnJavaType", "java.lang.String");
            _column.put("fieldName", "fieldName" + _i);
            _column.put("fieldNameB", "FieldName" + _i);
            _columns.add(_column);
        }
        _entityModel.put("columns", _columns);
        String _entityString = impl.process("entity.ftl", _entityModel);
        System.out.println();
        System.out.println(_entityString);
        System.out.println();
    }


    static void _test01() throws IOException, TemplateException {
        final Configuration _configuration = new Configuration(Configuration.VERSION_2_3_29);

        File _templateDirectory = new File("D:/temp");
        _configuration.setDirectoryForTemplateLoading(_templateDirectory);
        _configuration.setDefaultEncoding("UTF-8");
        _configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template _template = _configuration.getTemplate("hello.ftl");
        Writer _out = new OutputStreamWriter(System.out);
        Map<String, Object> _data = new HashMap<>(3);
        _data.put("s1", "string1");
        _data.put("i1", 1);
        _data.put("bool1", true);
        _template.process(_data, _out);
    }

}
