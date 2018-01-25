package com.lc.tsdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.fluent.Content;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class OpenTSDBStudyTest {
    OpenTSDBStudy openTSDBStudy = new OpenTSDBStudy();

    @Test
    public void testPutByPost() throws Exception {
        String url = "http://hd1:4242/api/put?summary";
        String body;
        Content content;

        LocalDateTime time = LocalDateTime.now();
        long timestamp = time.toEpochSecond(ZoneOffset.of("+8"));
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        sb.append("\t{\n");
        sb.append("\t\t\"metric\":\"sys.cpu.nice\",\n");
        sb.append("\t\t\"timestamp\":").append(timestamp).append(",\n");
        sb.append("\t\t\"value\":13,\n");
        sb.append("\t\t\"tags\": {\n");
        sb.append("\t\t\t\"host\":\"hd1\",\n");
        sb.append("\t\t\t\"dc\":\"lga\"\n");
        sb.append("\t\t}\n");
        sb.append("\t},\n");
        sb.append("\t{\n");
        sb.append("\t\t\"metric\":\"sys.cpu.nice\",\n");
        sb.append("\t\t\"timestamp\":").append(timestamp).append(",\n");
        sb.append("\t\t\"value\":14,\n");
        sb.append("\t\t\"tags\": {\n");
        sb.append("\t\t\t\"host\":\"hd2\",\n");
        sb.append("\t\t\t\"dc\":\"lga\"\n");
        sb.append("\t\t}\n");
        sb.append("\t}\n");
        sb.append("]");
        body = sb.toString();
        System.out.println("request:" + body);
        content = openTSDBStudy.crudByPost(url, body);
        System.out.println("response:" + content.toString());
    }

    @Test
    public void testQueryByPost() throws Exception {
        String url = "http://hd1:4242/api/query";
        String body;
        Content content;

        List<JSONObject> objectList = new ArrayList<>();
        JSONObject object = new JSONObject();
        object.put("metric", "sys.cpu.nice");
        object.put("aggregator", "zimsum");
        object.put("downsample", "1s-zimsum");
        JSONObject o = new JSONObject();
        o.put("host", "hd1");
        object.put("tags", o);
        objectList.add(object);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("start",1516788272L);
        jsonObject.put("end",1516788890L);
        jsonObject.put("queries",objectList);
        jsonObject.put("msResolution",false);
        jsonObject.put("globalAnnotations",true);
        body = JSON.toJSONString(jsonObject);
        System.out.println("request:" + body);
        content = openTSDBStudy.crudByPost(url, body);
        System.out.println("response:" + content.toString());

        System.out.println("------------------------------");

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("\"start\":1516788272000,\n");
        sb.append("\"end\":1516788890000,\n");
        sb.append("\"queries\":[\n");
        sb.append("{\n");
        sb.append("\"metric\":\"sys.cpu.nice\",\n");
        sb.append("\"aggregator\":\"zimsum\",\n");
        sb.append("\"downsample\":\"1s-zimsum\",\n");
        sb.append("\"tags\": {\n");
        sb.append("\"host\":\"hd1\"\n");
        sb.append("}\n");
        sb.append("}],\n");
        sb.append("\"msResolution\":false,\n");
        sb.append("\"globalAnnotations\":true\n");
        sb.append("}");
        body = sb.toString();
        System.out.println("request:" + body);
        content = openTSDBStudy.crudByPost(url, body);
        System.out.println("response:" + content.toString());
    }
}