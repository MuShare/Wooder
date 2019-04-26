package org.mushare.wooder.service.impl;

import org.mushare.wooder.bean.TextBean;
import org.mushare.wooder.bean.TextContentBean;
import org.mushare.wooder.domain.Text;
import org.mushare.wooder.domain.TextContent;
import org.mushare.wooder.service.TextManager;
import org.mushare.wooder.service.common.BaseManager;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TextManagerImpl extends BaseManager implements TextManager {

    @Override
    public Result add(String identifier, String textfolderId, String memberId) {
        return authTextFolder(textfolderId, memberId, textFolder -> {
            long now = System.currentTimeMillis();
            Text text = new Text();
            text.setCreatedAt(now);
            text.setUpdatedAt(now);
            text.setIdentifer(identifier);
            text.setName("");
            text.setAndroid(true);
            text.setIos(true);
            text.setFolder(textFolder);
            textDao.save(text);
            List<TextContent> contents = languageDao.findByProjectOrderByName(text.getFolder().getProject()).stream().map(language -> {
                TextContent content = new TextContent();
                content.setCreatedAt(now);
                content.setUpdatedAt(now);
                content.setString("");
                content.setLanguage(language);
                content.setText(text);
                return content;
            }).collect(Collectors.toList());
            textContentDao.saveAll(contents);
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
            TextBean textBean = new TextBean(text);
            textBean.setContents(textContentDao.findByTextOrderByLanguage(text).stream().map(content -> {
                return new TextContentBean(content);
            }).collect(Collectors.toList()));
            return Result.data(textBean);
        });
    }

    @Override
    public Result edit(TextBean textBean, String memberId) {
        return authText(textBean.getId(), memberId, text -> {
            long now = System.currentTimeMillis();
            text.setIdentifer(textBean.getIdentifier());
            text.setName(textBean.getName());
            text.setAndroid(textBean.getPlatforms().contains("android"));
            text.setIos(textBean.getPlatforms().contains("ios"));
            text.setUpdatedAt(now);
            textDao.save(text);
            Map<String, String> contents = textBean.getContents().stream()
                    .collect(Collectors.toMap(TextContentBean::getId, TextContentBean::getString));
            textContentDao.findByTextOrderByLanguage(text).forEach(content -> {
                if (!contents.keySet().contains(content.getId())) {
                    return;
                }
                String string = contents.get(content.getId());
                // If the new string is null or equals to the old string, skip it.
                if (string == null || content.getString().equals(string)) {
                    return;
                }
                content.setString(string);
                content.setUpdatedAt(now);
                textContentDao.save(content);
            });
            return Result.success();
        });
    }

}
