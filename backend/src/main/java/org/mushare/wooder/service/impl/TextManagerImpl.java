package org.mushare.wooder.service.impl;

import org.mushare.wooder.bean.TextBean;
import org.mushare.wooder.domain.Text;
import org.mushare.wooder.service.TextManager;
import org.mushare.wooder.service.common.BaseManager;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultList;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TextManagerImpl extends BaseManager implements TextManager {

    @Override
    public Result add(String identifier, String textfolderId, String memberId) {
        return authTextFolder(textfolderId, memberId, textFolder -> {
            Text text = new Text();
            text.setCreatedAt(System.currentTimeMillis());
            text.setUpdatedAt(text.getCreatedAt());
            text.setIdentifer(identifier);
            text.setName("");
            text.setAndroid(true);
            text.setIos(true);
            text.setFolder(textFolder);
            textDao.save(text);
            return Result.success();
        });
    }

    @Override
    public ResultList<TextBean> getTextsByTextfolderId(String textfolderId, String memberId) {
        return (ResultList<TextBean>) authTextFolder(textfolderId, memberId, textFolder -> {
            return ResultList.data(textDao.findByFolderOrderByIdentifer(textFolder).stream().map(text -> {
                return new TextBean(text);
            }).collect(Collectors.toList()));
        });
    }

    @Override
    public Result<TextBean> getByTextId(String textId, String memberId) {
        return authText(textId, memberId, text -> {
            return Result.data(new TextBean(text));
        });
    }

}
