package org.mushare.wooder.controller;

import org.mushare.wooder.controller.common.BaseController;
import org.mushare.wooder.controller.common.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/web/text")
public class TextController extends BaseController {

    @RequestMapping(value = "/{textId}", method = RequestMethod.GET)
    public ResponseEntity getText(@PathVariable String textId, HttpServletRequest request) {
        return authMember(request, memberId -> {
            return Response.ok().build();
        });
    }

    @RequestMapping(value = "/{textId}/edit", method = RequestMethod.POST)
    public ResponseEntity editText(@PathVariable String edit, HttpServletRequest request) {

        return authMember(request, memberId -> {
            return Response.ok().build();
        });
    }

}
