package org.mushare.wooder.controller.web;

import com.google.common.collect.ImmutableMap;
import org.mushare.wooder.bean.TextBean;
import org.mushare.wooder.controller.common.BaseController;
import org.mushare.wooder.controller.common.ErrorCode;
import org.mushare.wooder.controller.common.Response;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return Response.ok()
                    .append("text", result.getData())
                    .build();
        });
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity editText(@RequestBody TextBean textBean, HttpServletRequest request) {
        System.out.println(textBean);
        return authMember(request, memberId -> {
            Result result = textManager.edit(textBean, memberId);
            if (result.hasError()) {
                return result.errorMapping(null);
            }
            return Response.success().build();
        });
    }

}
