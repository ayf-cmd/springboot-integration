package ${module}.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import lombok.experimental.SuperBuilder;

/**
 * @Description:  ${entityComment}
 * @Author:       ${author}   
 * @CreateDate:   ${createTime}
 * @Version:      ${version}
 * 当为Integer、Long等类型使用 @NotNull
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel(value="${entityName}Vo",description="${entityComment}")
public class ${entityName}Vo implements Serializable {
	
<#list cis as ci>
	<#if ci.property=="isDelete" || ci.property=="createTime" || ci.property=="updateTime">
	<#elseif ci.property=="hdUserId" || ci.property=="hdOrgSn" || ci.property=="ctUserId" || ci.property=="ctOrgNo">
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
	