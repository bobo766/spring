package ru.korshun.eda;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class MyCustomErrorAttributes
        extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

//        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
//
//        String message = (String) errorAttributes.get("message");
//        String path = (String) errorAttributes.get("path");
//
//        errorAttributes.remove("timestamp");
//        errorAttributes.remove("errors");
//        errorAttributes.remove("path");
//
//        errorAttributes.put("message", String.format("Message: %s. Path: %s", message, path));
//        errorAttributes.put("status", errorAttributes.get("status"));
//
//        return errorAttributes;

        return super.getErrorAttributes(webRequest, options);
    }
}