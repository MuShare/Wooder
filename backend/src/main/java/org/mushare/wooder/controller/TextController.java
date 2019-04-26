package org.mushare.wooder.controller;

import com.google.common.collect.ImmutableMap;
import org.mushare.wooder.bean.TextBean;
import org.mushare.wooder.controller.common.BaseController;
import org.mushare.wooder.controller.common.ErrorCode;
import org.mushare.wooder.controller.common.Response;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/web/text")
public class TextController extends BaseController {

    private final static Map TextAccessErrorMap = ImmutableMap.of(
            ResultCode.TextIdError, ErrorCode.TextIdNotExist,
            ResultCode.MemberIdError, ErrorCode.MemberIdNotExist,
            ResultCode.NoPrivilege, ErrorCode.ErrorNoPrivilge
    );

    @RequestMapping(value = "/{textId}", method = RequestMethod.GET)
    public ResponseEntity getText(@PathVariable String textId, HttpServletRequest request) {
        return authMember(request, memberId -> {
            Result<TextBean> result = textManager.getByTextId(textId, memberId);
            if (result.hasError()) {
                return result.errorMapping(TextAccessErrorMap);
            }
            System.out.println(result.getData());
            return Response.ok()
                    .append("text", result.getData())
                    .build();
        });
    }

    @RequestMapping(value = "/{textId}/edit", method = RequestMethod.POST)
    public ResponseEntity editText(@PathVariable String textId, HttpServletRequest request) {
        return authMember(request, memberId -> {

            return Response.ok().build();
        });
    }

}
