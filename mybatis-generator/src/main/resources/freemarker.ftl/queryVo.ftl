package ${module}.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @Description:  ${entityComment}
 * @Author:       ${author}   
 * @CreateDate:   ${createTime}
 * @Version:      ${version}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel(value="${entityName}QueryVo",description="${entityComment}列表查询条件")
public class ${entityName}QueryVo implements Serializable {
	
	@ApiModelProperty(value = "id")
	private String id;
	
}
	