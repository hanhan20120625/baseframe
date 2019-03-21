/**
 * Copyright &copy; 2017 <a href="msframe/">msframe</a> All rights reserved.
 */
package com.msframe.modules.cms.dao;

import java.util.List;

import com.msframe.common.persistence.CrudDao;
import com.msframe.common.persistence.annotation.MyBatisDao;
import com.msframe.modules.cms.entity.CmsProgramLang;

/**
 * 影片关联语言DAO接口
 *
 * @author lpz
 * @version 2018-11-14
 */
@MyBatisDao
public interface CmsProgramLangDao extends CrudDao<CmsProgramLang> {

    /**
     * @param cmsProgramLang
     * @author leon
     * @date 2018/11/20
     * @description 根据ProgramId更新对应的LangId
     */
    public void updateByProgramId(CmsProgramLang cmsProgramLang);

    /**
     * @param cmsProgramLang
     * @description 根据ProgramId、LangId 删除对应数据
     * @date 2018/11/20
     * @author leon
     */
    void deleteByProgramIdAndLangId(CmsProgramLang cmsProgramLang);
}