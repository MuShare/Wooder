package org.mushare.wooder.controller;

import com.google.common.collect.ImmutableMap;
import org.mushare.wooder.bean.TextFolderBean;
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
@RequestMapping(value = "/web/textfolder")
public class TextFolderController extends BaseController {

    private final static Map TextFoldertAccessErrorMap = ImmutableMap.of(
            ResultCode.TextFolderIdError, ErrorCode.TextFolderIdNotExist,
            ResultCode.MemberIdError, ErrorCode.MemberIdNotExist,
            ResultCode.NoPrivilege, ErrorCode.ErrorNoPrivilge
    );

    @RequestMapping(value = "/{textfolderId}", method = RequestMethod.GET)
    public ResponseEntity getTextFolderInfo(@PathVariable String textfolderId, HttpServletRequest request) {
        return authMember(request, memberId -> {
            Result<TextFolderBean> result = textFolderManager.textfolderInfo(textfolderId, memberId);
            if (result.hasError()) {
                return result.errorMapping(TextFoldertAccessErrorMap);
            }
            return Response.ok()
                    .append("textfolder", result.getData())
                    .build();
        });
    }

}
