package cn.kgc.coolrental.entity;
//
//import cn.kgc.coolrental.config.resolver.Jsonpost;
//import cn.kgc.coolrental.config.resolver.PostArgument2JsonConverter;
import cn.kgc.coolrental.util.Json2SetTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("岗位类型")
public class Post implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("岗位类型名称")
    private String typeName;
    @ApiModelProperty("岗位名称")
    @TableField(value = "post_names", jdbcType = JdbcType.VARCHAR, typeHandler = Json2SetTypeHandler.class)
    private Set<String> postNames;
    @ApiModelProperty("启动状态")
    private Integer status;


}
