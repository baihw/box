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
package com.wee0.box.notify.sms.dy;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/3/28 8:09
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class DySmsHelperTest {
    static {
        // 或者运行时加上 -Dsms.accessKeyId=testId -Dsms.accessSecret=testSecret
        System.setProperty("sms.accessKeyId", "testId");
        System.setProperty("sms.accessSecret", "testSecret");
    }

    @Test
    public void buildGetUrl() {

        final String _demoSignatureNonce = "45e25e9b-0a6f-4070-8c85-2956eda1b466";
        final String _demoTimestamp = "2017-07-12T02:42:19Z";
        final String _demoToQueryString = "AccessKeyId=testId&Action=SendSms&Format=XML&OutId=123&PhoneNumbers=15300000001&RegionId=cn-hangzhou&SignName=阿里云短信测试专用&SignatureMethod=HMAC-SHA1&SignatureNonce=45e25e9b-0a6f-4070-8c85-2956eda1b466&SignatureVersion=1.0&TemplateCode=SMS_71390007&TemplateParam={\"customer\":\"test\"}&Timestamp=2017-07-12T02:42:19Z&Version=2017-05-25";
        final String _demoQueryString = "AccessKeyId=testId&Action=SendSms&Format=XML&OutId=123&PhoneNumbers=15300000001&RegionId=cn-hangzhou&SignName=%E9%98%BF%E9%87%8C%E4%BA%91%E7%9F%AD%E4%BF%A1%E6%B5%8B%E8%AF%95%E4%B8%93%E7%94%A8&SignatureMethod=HMAC-SHA1&SignatureNonce=45e25e9b-0a6f-4070-8c85-2956eda1b466&SignatureVersion=1.0&TemplateCode=SMS_71390007&TemplateParam=%7B%22customer%22%3A%22test%22%7D&Timestamp=2017-07-12T02%3A42%3A19Z&Version=2017-05-25";
        final String _demoToSign = "GET&%2F&AccessKeyId%3DtestId%26Action%3DSendSms%26Format%3DXML%26OutId%3D123%26PhoneNumbers%3D15300000001%26RegionId%3Dcn-hangzhou%26SignName%3D%25E9%2598%25BF%25E9%2587%258C%25E4%25BA%2591%25E7%259F%25AD%25E4%25BF%25A1%25E6%25B5%258B%25E8%25AF%2595%25E4%25B8%2593%25E7%2594%25A8%26SignatureMethod%3DHMAC-SHA1%26SignatureNonce%3D45e25e9b-0a6f-4070-8c85-2956eda1b466%26SignatureVersion%3D1.0%26TemplateCode%3DSMS_71390007%26TemplateParam%3D%257B%2522customer%2522%253A%2522test%2522%257D%26Timestamp%3D2017-07-12T02%253A42%253A19Z%26Version%3D2017-05-25";
        final String _demoSign = "zJDF+Lrzhj/ThnlvIToysFRq6t4=";
        final String _demoSignature = "zJDF%2BLrzhj%2FThnlvIToysFRq6t4%3D";
        final String _demoUrl = "http://dysmsapi.aliyuncs.com/?Signature=zJDF%2BLrzhj%2FThnlvIToysFRq6t4%3D&AccessKeyId=testId&Action=SendSms&Format=XML&OutId=123&PhoneNumbers=15300000001&RegionId=cn-hangzhou&SignName=%E9%98%BF%E9%87%8C%E4%BA%91%E7%9F%AD%E4%BF%A1%E6%B5%8B%E8%AF%95%E4%B8%93%E7%94%A8&SignatureMethod=HMAC-SHA1&SignatureNonce=45e25e9b-0a6f-4070-8c85-2956eda1b466&SignatureVersion=1.0&TemplateCode=SMS_71390007&TemplateParam=%7B%22customer%22%3A%22test%22%7D&Timestamp=2017-07-12T02%3A42%3A19Z&Version=2017-05-25";

        java.util.Map<String, String> _params = new java.util.HashMap<>();
//        _params.put("AccessKeyId", "testId");
//        _params.put("SignatureMethod", "HMAC-SHA1");
//        _params.put("SignatureVersion", "1.0");
        _params.put("Format", "XML");
        _params.put("SignatureNonce", _demoSignatureNonce);
        _params.put("Timestamp", _demoTimestamp);
        _params.put("Action", "SendSms");
        _params.put("TemplateCode", "SMS_71390007");
        _params.put("TemplateParam", "{\"customer\":\"test\"}");
        _params.put("SignName", "阿里云短信测试专用");
        _params.put("PhoneNumbers", "15300000001");
        _params.put("OutId", "123");

        String _queryString = DySmsHelper.me()._buildQueryString(_params);
        System.out.println("_queryString: " + _queryString);
        Assert.assertEquals(_demoQueryString, _queryString);

        String _signature = DySmsHelper.me()._buildSignature(_queryString);
        System.out.println("_signature: " + _signature);
        Assert.assertEquals(_demoSignature, _signature);

        String _getUrl = DySmsHelper.me().buildGetUrl(_params);
        System.out.println("getUrl: " + _getUrl);
        Assert.assertEquals(_demoUrl, _getUrl);
    }

    @Test
    public void querySendSmsDetail() {
    }

}
