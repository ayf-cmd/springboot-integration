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
@ApiModel(value="${entityName}UpdateVo",description="${entityComment}更新条件")
public class ${entityName}UpdateVo implements Serializable {

<#list cis as ci>
	<#if ci.property=="isDelete" || ci.property=="createTime" || ci.property=="updateTime" || ci.property=="ctUserId" || ci.property=="ctOrgNo" >
	<#elseif ci.property=="hdUserId" || ci.property=="hdOrgSn">
	@ApiModelProperty(value = "${ci.comment}",hidden = true)
	// @NotEmpty(message = "${ci.comment}不能为空")
	private ${ci.javaType} ${ci.property};
	
	<#else>
	@ApiModelProperty(value = "${ci.comment}",required = false)
	// @NotEmpty(message = "${ci.comment}不能为空")
	private ${ci.javaType} ${ci.property};
	
	</#if>
</#list>
	
}
	