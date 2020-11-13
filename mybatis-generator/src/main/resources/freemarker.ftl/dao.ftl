package ${module}.dao;

import ${module}.dto.${entityName}Dto;
import ${module}.entity.${entityName}Busi;
import ${module}.vo.${entityName}QueryVo;
import ${module}.vo.${entityName}Vo;
import ${module}.entity.${entityName};
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**   
 * @Description:  ${entityComment}——Mapper
 * @Author:       ${author}   
 * @CreateDate:   ${createTime}
 * @Version:      ${version}
 */
public interface ${entityName}Mapper extends BaseMapper<${entityName}> {
	
    ${entityName}Dto detail(${entityName}Vo vo);

    List<${entityName}Dto> listPage(${entityName}QueryVo vo);

    List<${entityName}Dto> listCmb(${entityName}QueryVo vo);

}
	