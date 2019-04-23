package org.mushare.wooder.service.common;

import org.mushare.wooder.controller.common.ErrorCode;
import org.mushare.wooder.controller.common.Response;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class Result<T> {

    private ResultCode code;
    private T data = null;

    public T getData() {
        return data;
    }

    public ResultCode getCode() {
        return code;
    }

    protected Result(ResultCode code, T data) {
        this.code = code;
        this.data = data;
    }

    public boolean errorEquals(ResultCode code) {
        return this.code.equals(code);
    }

    public boolean hasError() {
        return !code.equals(ResultCode.Success);
    }

    public ResponseEntity errorMapping(Map<ResultCode, ErrorCode>... errorMaps) {
        for (Map<ResultCode, ErrorCode> errorMap : errorMaps) {
            if (errorMap.containsKey(code)) {
                return Response.badRequest(errorMap.get(code)).build();
            }
        }
        return Response.badRequest(ErrorCode.ErrorUnknown).build();
    }

    public static Result success() {
        return new Result(ResultCode.Success, null);
    }

    public static Result error(ResultCode code) {
        return new Result(code, null);
    }

    public static <T> Result data(T data) {
        return new Result<T>(ResultCode.Success, data);
    }

}