package org.mushare.wooder.service.impl;

import org.mushare.wooder.bean.LanguageBean;
import org.mushare.wooder.domain.Language;
import org.mushare.wooder.service.LanguageManager;
import org.mushare.wooder.service.common.BaseManager;
import org.mushare.wooder.service.common.Result;
import org.mushare.wooder.service.common.ResultList;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LanguageManagerImpl extends BaseManager implements LanguageManager {

    @Override
    public Result add(String identifier, String name, String projectId, String memberId) {
        return authProject(projectId, memberId, project -> {
            Language language = new Language();
            language.setCreatedAt(System.currentTimeMillis());
            language.setUpdatedAt(language.getCreatedAt());
            language.setIdentifier(identifier);
            language.setName(name);
            language.setProject(project);
            languageDao.save(language);
            return Result.success();
        });
    }

    @Override
    public ResultList<LanguageBean> getLanguagesByProjectId(String projectId, String memberId) {
        return (ResultList<LanguageBean>) authProject(projectId, memberId, project -> {
            return ResultList.data(languageDao.findByProjectOrderByName(project).stream().map(language -> {
                return new LanguageBean(language);
            }).collect(Collectors.toList()));
        });
    }

}
