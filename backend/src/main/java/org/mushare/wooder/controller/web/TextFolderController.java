package org.mushare.wooder.controller.web;

import com.google.common.collect.ImmutableMap;
import org.mushare.wooder.bean.TextBean;
import org.mushare.wooder.bean.TextFolderBean;
import org.mushare.wooder.controller.common.BaseController;
import org.mushare.wooder.controller.common.ErrorCode;
import org.mushare.wooder.controller.common.Response;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultCode;
import org.mushare.wooder.service.common.ResultList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/web/textfolder")
public class TextFolderController extends BaseController {

    private final static Map TextFolderAccessErrorMap = ImmutableMap.of(
            ResultCode.TextFolderIdError, ErrorCode.TextFolderIdNotExist,
            ResultCode.MemberIdError, ErrorCode.MemberIdNotExist,
            ResultCode.NoPrivilege, ErrorCode.ErrorNoPrivilge
    );

    @RequestMapping(value = "/{textfolderId}", method = RequestMethod.GET)
    public ResponseEntity getTextFolderInfo(@PathVariable String textfolderId, HttpServletRequest request) {
        return authMember(request, memberId -> {
            Result<TextFolderBean> result = textFolderManager.textfolderInfo(textfolderId, memberId);
            if (result.hasError()) {
                return result.errorMapping(TextFolderAccessErrorMap);
            }
            return Response.ok()
                    .append("textfolder", result.getData())
                    .build();
        });
    }

    @RequestMapping(value = "/{textfolderId}/text/add", method = RequestMethod.POST)
    public ResponseEntity addText(@PathVariable String textfolderId, @RequestParam String identifier, HttpServletRequest request) {
        return authMember(request, memberId -> {
            Result result = textManager.add(identifier, textfolderId, memberId);
            if (result.hasError()) {
                return result.errorMapping(TextFolderAccessErrorMap);
            }
            return Response.success().build();
        });
    }

    @RequestMapping(value = "/{textfolderId}/text/list", method = RequestMethod.GET)
    public ResponseEntity addText(@PathVariable String textfolderId, HttpServletRequest request) {
        return authMember(request, memberId -> {
            ResultList<TextBean> result = textManager.getTextsByTextfolderId(textfolderId, memberId);
            if (result.hasError()) {
                return result.errorMapping(TextFolderAccessErrorMap);
            }
            return Response.success()
                    .append("texts", result.getData())
                    .build();
        });
    }

}
