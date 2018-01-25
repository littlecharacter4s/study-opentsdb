package com.lc.tsdb;

import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

public class OpenTSDBStudy {
    public Content crudByPost(String url, String body) throws Exception {
        return Request.Post(url)
                .useExpectContinue()
                .version(HttpVersion.HTTP_1_1)
                .bodyString(body, ContentType.APPLICATION_JSON)
                .execute().returnContent();
    }
}
