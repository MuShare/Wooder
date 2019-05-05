package org.mushare.wooder.server.controller.web;

import lombok.AllArgsConstructor;
import org.mushare.wooder.server.service.TextService;
import org.mushare.wooder.spec.Endpoints;
import org.mushare.wooder.spec.request.TextRequest;
import org.mushare.wooder.spec.response.OperationResponse;
import org.mushare.wooder.spec.response.TextResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(Endpoints.TEXT)
public class TextController {

  private TextService textService;

  @RequestMapping(value = "/{textId}", method = RequestMethod.GET)
  public TextResponse getText(@PathVariable long textId) {
    return textService.getTextByTextId(textId);
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public OperationResponse editText(@RequestBody TextRequest textReqeust) {
    return textService.editText(textReqeust);
  }

}
