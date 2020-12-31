package cn.kgc.coolrental.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色")
public class Role implements Serializable {

    @ApiModelProperty("主键")
    @TableField("`id`")
    private Integer id;
    @ApiModelProperty("角色名称")
    @TableField("`name`")
    private String name;
    @ApiModelProperty("角色说明")
    @TableField("`desc`")
    private String desc;
    @ApiModelProperty("启用状态")
    @TableField("`status`")
    private Integer status;


}
