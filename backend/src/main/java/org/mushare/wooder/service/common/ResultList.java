package org.mushare.wooder.service.common;

import java.util.List;

public class ResultList<T> extends Result<List<T>> {

    private ResultList(ResultCode code, List<T> data) {
        super(code, data);
    }

    public static ResultList success() {
        return new ResultList(ResultCode.Success, null);
    }

    public static ResultList error(ResultCode code) {
        return new ResultList(code, null);
    }

    public static <T> ResultList data(List<T> data) {
        return new ResultList<T>(ResultCode.Success, data);
    }

}
