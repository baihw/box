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

package com.wee0.box.testObjects;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:32
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class Level1Obj {
    private Level2Obj level2Obj;

    private Level3Obj level3Obj;

    private String string1;

    private Map<String, Object> map1;

    private List<String> list1;

    private List<Map<String, Object>> listMap1;

    public Level2Obj getLevel2Obj() {
        return level2Obj;
    }

    public void setLevel2Obj(Level2Obj level2Obj) {
        this.level2Obj = level2Obj;
    }

    public Level3Obj getLevel3Obj() {
        return level3Obj;
    }

    public void setLevel3Obj(Level3Obj level3Obj) {
        this.level3Obj = level3Obj;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public Map<String, Object> getMap1() {
        return map1;
    }

    public void setMap1(Map<String, Object> map1) {
        this.map1 = map1;
    }

    public List<String> getList1() {
        return list1;
    }

    public void setList1(List<String> list1) {
        this.list1 = list1;
    }

    public List<Map<String, Object>> getListMap1() {
        return listMap1;
    }

    public void setListMap1(List<Map<String, Object>> listMap1) {
        this.listMap1 = listMap1;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Level1Obj{");
        sb.append("level2Obj=").append(level2Obj);
        sb.append(", level3Obj=").append(level3Obj);
        sb.append(", string1='").append(string1).append('\'');
        sb.append(", map1=").append(map1);
        sb.append(", list1=").append(list1);
        sb.append(", listMap1=").append(listMap1);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level1Obj level1Obj = (Level1Obj) o;
        return Objects.equals(level2Obj, level1Obj.level2Obj) &&
                Objects.equals(level3Obj, level1Obj.level3Obj) &&
                Objects.equals(string1, level1Obj.string1) &&
                Objects.equals(map1, level1Obj.map1) &&
                Objects.equals(list1, level1Obj.list1) &&
                Objects.equals(listMap1, level1Obj.listMap1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level2Obj, level3Obj, string1, map1, list1, listMap1);
    }
}
