package ${module}.service;

import ${module}.dto.${entityName}Dto;
import ${module}.dto.${entityName}LogicDto;
import ${module}.entity.${entityName};
import ${module}.vo.${entityName}QueryVo;
import ${module}.vo.${entityName}Vo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import javax.validation.Valid;

/**   
 * @Description:  ${entityComment}——SERVICE
 * @Author:       ${author}   
 * @CreateDate:   ${createTime}
 * @Version:      ${version}
 */
public interface ${entityName}Service extends IService<${entityName}> {
	
	/**
	 * ${entityComment}新增
	 */
	${entityName}LogicDto add(@Valid ${entityName}Vo vo);
    
	/**
	 * ${entityComment}更新
	 */
    ${entityName}LogicDto modifyById(@Valid ${entityName}Vo vo);
    
	/**
	 * ${entityComment}删除
	 */
    ${entityName}LogicDto delete(Long id);
    
	/**
	 * 通过${entityComment}编号查询${entityComment}详情
	 */
	${entityName}Dto detailByNo(String no);
	
	/**
	 * 通过${entityComment}id查询${entityComment}详情
	 */
    ${entityName}Dto detailById(Long id);
    
	/**
	 * ${entityComment}列表
	 */
    PageInfo<${entityName}Dto> listPage(${entityName}QueryVo vo);
    
	/**
	 * ${entityComment}弹框列表
	 */
    PageInfo<${entityName}Dto> listCmb(${entityName}QueryVo vo);
}