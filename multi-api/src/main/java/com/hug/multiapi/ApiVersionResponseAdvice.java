package com.hug.multiapi;

import com.hug.common.ReflectionHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * For multi api response
 */
@Slf4j
@ControllerAdvice
public class ApiVersionResponseAdvice implements ResponseBodyAdvice {

    private static String VER_METHOD = "ver_trace";

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType
            , Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String requestApiVersion = serverHttpRequest.getHeaders().getFirst("api-version");
        ApiVersion apiVersion = methodParameter.getMethod().getAnnotation(ApiVersion.class);

        try {
            ReflectionHelper.setField(o, VER_METHOD, requestApiVersion + " -> v" + (null != apiVersion ? apiVersion.value() : ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("method:{}, requestApiVersion:{}, response:{}", methodParameter.getMethod(), requestApiVersion, o.toString());
        return o;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        ApiVersion apiVersion = methodParameter.getMethod().getAnnotation(ApiVersion.class);
        log.info("methodParameter:{}, aClass:{}, apiVersion:{}", methodParameter, aClass, apiVersion);
        return null != methodParameter.getMethod().getAnnotation(ApiVersion.class);
    }

}
