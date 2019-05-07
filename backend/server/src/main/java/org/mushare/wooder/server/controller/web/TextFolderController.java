package org.mushare.wooder.server.controller.web;

import lombok.AllArgsConstructor;
import org.mushare.wooder.server.service.TextFolderService;
import org.mushare.wooder.server.service.TextService;
import org.mushare.wooder.spec.Endpoints;
import org.mushare.wooder.spec.response.OperationResponse;
import org.mushare.wooder.spec.response.TextFolderListResponse.TextFolderListItem;
import org.mushare.wooder.spec.response.TextListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Endpoints.TEXT_FOLDER)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TextFolderController {

  private TextFolderService textFolderService;
  private TextService textService;

  @RequestMapping(value = "/{textfolderId}", method = RequestMethod.GET)
  public TextFolderListItem getTextFolderInfo(@PathVariable long textfolderId) {
    return textFolderService.getTextFolderInfo(textfolderId);
  }

  @RequestMapping(value = "/{textfolderId}/text/add", method = RequestMethod.POST)
  public OperationResponse createText(@PathVariable long textfolderId,
      @RequestParam String identifier) {
    return textService.createText(identifier, textfolderId);
  }

  @RequestMapping(value = "/{textfolderId}/text/list", method = RequestMethod.GET)
  public TextListResponse getTextByTextFolderId(@PathVariable long textfolderId,
      @RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "10") int pageSize) {
    return textService.getTextByTextfolderId(textfolderId, pageNumber, pageSize);
  }

}
