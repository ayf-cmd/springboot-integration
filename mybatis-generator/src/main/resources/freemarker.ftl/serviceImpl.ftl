package ${module}.service;

import ${module}.dao.${entityName}Mapper;
import ${module}.dto.${entityName}Dto;
import ${module}.dto.${entityName}LogicDto;
import ${module}.entity.${entityName};
import ${module}.vo.${entityName}QueryVo;
import ${module}.vo.${entityName}Vo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @Description:  ${entityComment}——SERVICEIMPL
 * @Author:       ${author}   
 * @CreateDate:   ${createTime}
 * @Version:      ${version}
 */
@Service
public class ${entityName}ServiceImpl extends ServiceImpl<${entityName}Mapper, ${entityName}> implements ${entityName}Service {
    @Autowired
    private ${entityName}Mapper mapper;
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(${entityName}ServiceImpl.class);
	
    @Override
    public ${entityName}LogicDto add(@Valid ${entityName}Vo vo) {
        ${entityName} entity = BeanCpUtils.transferObjectCase(vo, ${entityName}.class);
        // TODO
        if(mapper.detail(vo)!=null) {
    		throw new BusinessException(ResultConsts.ERROR_STATUS, "记录已存在");
    	}
        return ${entityName}LogicDto.builder().sucFlag(save(entity)).build();
    }

    @Override
    public ${entityName}LogicDto modifyById(@Valid ${entityName}Vo vo) {
    	if(vo.getId()==null) {
    		throw new BusinessException(ResultConsts.ERROR_STATUS, "唯一标识id不能为空");
    	}
        ${entityName} entity = BeanCpUtils.transferObjectCase(vo, ${entityName}.class);
        return ${entityName}LogicDto.builder().sucFlag(updateById(entity)).build();
    }

    @Override
    public ${entityName}LogicDto delete(Long id) {
    	if(id==null) {
    		throw new BusinessException(ResultConsts.ERROR_STATUS, "唯一标识id不能为空");
    	}
        return ${entityName}LogicDto.builder().sucFlag(removeById(id)).build();
    }

    @Override
    public ${entityName}Dto detailByNo(String no) {
    	if(no==null || no.length()==0) {
    		throw new BusinessException(ResultConsts.ERROR_STATUS, "编号不能为空");
    	}
    	// TODO
    	${entityName}Vo vo = ${entityName}Vo.builder().no(no).build();
        ${entityName}Dto result = mapper.detail(vo);
        return result;
    }

    @Override
    public ${entityName}Dto detailById(Long id) {
    	if(id==null) {
    		throw new BusinessException(ResultConsts.ERROR_STATUS, "唯一标识id不能为空");
    	}
    	${entityName}Vo vo = ${entityName}Vo.builder().id(id).build();
        ${entityName}Dto result = mapper.detail(vo);
        return result;
    }

    @Override
    public PageInfo<${entityName}Dto> listPage(${entityName}QueryVo vo) {
        PageInfo<${entityName}Dto> result = new PageInfo<>(mapper.listPage(vo));
        return result;
    }

    @Override
    public PageInfo<${entityName}Dto> listCmb(${entityName}QueryVo vo) {
        PageInfo<${entityName}Dto> result = new PageInfo<>(mapper.listCmb(vo));
        return result;
    }
    
}