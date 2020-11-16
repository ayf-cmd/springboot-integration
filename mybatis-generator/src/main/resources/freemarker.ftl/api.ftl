package ${module}.api;

import ${module}.dto.${entityName}Dto;
import ${module}.dto.${entityName}LogicDto;
import ${module}.vo.${entityName}Vo;
import ${module}.vo.${entityName}QueryVo;
import com.github.pagehelper.PageInfo;

/**   
 * @Description:  ${entityComment}——Api
 * @Author:       ${author}   
 * @CreateDate:   ${createTime}
 * @Version:      ${version}
 */
public interface ${entityName}Api {
	
	/**
	 * ${entityComment}新增
	 */
    ${entityName}LogicDto add(${entityName}Vo vo);
    
	/**
	 * ${entityComment}更新
	 */
    ${entityName}LogicDto modifyById(${entityName}Vo vo);
    
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

}
