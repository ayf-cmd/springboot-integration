package ${module}.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Description:  ${entityComment}
 * @Author:       ${author}   
 * @CreateDate:   ${createTime}
 * @Version:      ${version}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "${table}")
public class ${entityName} implements Serializable {
	
<#list cis as ci>
	/**
	 * ${ci.comment}
	 */
	<#if ci.property=="isDelete">
	@TableLogic
	</#if>
	<#if ci.property=="id">
	@TableId(type= IdType.AUTO)
	<#else>
	@TableField(value = "${ci.column}")
	</#if>
	private ${ci.javaType} ${ci.property};
	
</#list>
}
	