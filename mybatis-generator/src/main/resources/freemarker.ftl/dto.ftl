package ${module}.dto;

import java.io.Serializable;
import java.util.Date;
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
@ApiModel(value="${entityName}Dto",description="${entityComment}响应数据")
@SuppressWarnings("serial")
public class ${entityName}Dto implements Serializable {
	
<#list cis as ci>
	<#if ci.property!="isDelete">
	@ApiModelProperty(value = "${ci.comment}")
	private ${ci.javaType} ${ci.property};
	
	</#if>
</#list>
	@ApiModelProperty(value = "操作标识")
	private Boolean sucFlag;
}
	