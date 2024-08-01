package com.ait.vis.mongo.embeded.api.testutils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
public class HttpHelper {
	public static <T> HttpEntity<T> getHttpEntity(T dto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json;charset=UTF-8");
        //httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(dto, httpHeaders);
    }
}
