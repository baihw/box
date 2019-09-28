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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:33
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class Level3Obj {

    private String privateString1;

    String string1;

    int int1;

    float float1;

    boolean bool1;

    Date date1;

    Map<String, Object> map1;

    List<String> list1;

    List<Map<String, Object>> listMap1;

    public String getPrivateString1() {
        return privateString1;
    }

    public void setPrivateString1(String privateString1) {
        this.privateString1 = privateString1;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public float getFloat1() {
        return float1;
    }

    public void setFloat1(float float1) {
        this.float1 = float1;
    }

    public boolean isBool1() {
        return bool1;
    }

    public void setBool1(boolean bool1) {
        this.bool1 = bool1;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
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
        final StringBuilder sb = new StringBuilder("Level3Obj{");
        sb.append("privateString1='").append(privateString1).append('\'');
        sb.append(", string1='").append(string1).append('\'');
        sb.append(", int1=").append(int1);
        sb.append(", float1=").append(float1);
        sb.append(", bool1=").append(bool1);
        sb.append(", date1=").append(date1);
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
        Level3Obj level3Obj = (Level3Obj) o;
        return int1 == level3Obj.int1 &&
                Float.compare(level3Obj.float1, float1) == 0 &&
                bool1 == level3Obj.bool1 &&
                Objects.equals(privateString1, level3Obj.privateString1) &&
                Objects.equals(string1, level3Obj.string1) &&
                Objects.equals(date1, level3Obj.date1) &&
                Objects.equals(map1, level3Obj.map1) &&
                Objects.equals(list1, level3Obj.list1) &&
                Objects.equals(listMap1, level3Obj.listMap1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privateString1, string1, int1, float1, bool1, date1, map1, list1, listMap1);
    }

}
