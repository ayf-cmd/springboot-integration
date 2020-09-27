package com.mybatis.mapper;

import com.mybatis.basedao.IBaseDao;
import com.mybatis.entity.Torder;
import org.apache.ibatis.annotations.Mapper;

/**
 * xml方式
 */
@Mapper
public interface TorderMapper extends IBaseDao<Torder> {

    Torder getOneMy(Long id);

}
