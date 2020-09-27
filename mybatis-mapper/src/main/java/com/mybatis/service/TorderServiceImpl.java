package com.mybatis.service;

import com.mybatis.entity.Torder;
import com.mybatis.mapper.TorderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TorderServiceImpl implements TorderService {
    @Autowired
    private TorderMapper torderMapper;

    @Override
    public Torder getOneMy(Long id) {
        return torderMapper.getOneMy(id);
    }

}
