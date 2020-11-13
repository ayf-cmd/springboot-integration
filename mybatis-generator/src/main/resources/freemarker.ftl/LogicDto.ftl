package ${module}.dto;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 逻辑处理响应数据 : 增删改
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel(value="${entityName}LogicDto",description="逻辑响应数据")
@SuppressWarnings("serial")
public class ${entityName}LogicDto implements Serializable{

	@ApiModelProperty(value = "操作标识")
	private Boolean sucFlag;
	
	@Builder.Default
	@ApiModelProperty(value = "返回信息")
	private String msg = "SUCCESS";
	
}