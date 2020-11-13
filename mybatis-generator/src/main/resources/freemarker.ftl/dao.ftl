package ${module}.dao;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
	