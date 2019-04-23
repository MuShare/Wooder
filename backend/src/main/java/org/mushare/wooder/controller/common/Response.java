package org.mushare.wooder.controller.common;

import lombok.Data;
import org.mushare.common.util.Debug;
import org.mushare.wooder.service.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response {

    private Map<String, Object> result;
    private HttpStatus status;
    private int error = 0;
    private String message = "";

    private Response(HttpStatus status) {
        this.status = status;
    }

    public static Response badRequest(ErrorCode errorCode) {
        Response response = new Response(HttpStatus.BAD_REQUEST);
        response.error = errorCode.code;
        response.message = errorCode.message;
        return response;
    }

    public static Response ok() {
        Response response = new Response(HttpStatus.OK);
        response.result = new HashMap<>();
        return response;
    }

    public static Response ok(Map<String, Object> result) {
        Response response = new Response(HttpStatus.OK);
        response.result = result;
        return response;
    }

    public static Response success() {
        return Response.ok().append("success", true);
    }

    public Response append(String key, Object value) {
        if (status != HttpStatus.OK) {
            Debug.error("The result data can only be appended to a response object with OK HttpStatus.");
            return this;
        }
        result.put(key, value);
        return this;
    }

    public Response append(String key, Result result) {
        return append(key, result.getData());
    }

    public ResponseEntity build() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", status.value());
        data.put("timestamp", System.currentTimeMillis());
        switch (status) {
            case OK:
                data.put("result", result);
                return ResponseEntity.status(status).body(data);
            case BAD_REQUEST:
                data.put("error", error);
                data.put("message", message);
                return ResponseEntity.status(status).body(data);
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
