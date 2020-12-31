package cn.kgc.coolrental.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Property;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("权限")
public class Perm implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("权限名称")
    private String name;
    @ApiModelProperty("权限标识")
    private String perm;
    @ApiModelProperty("权限url")
    private String url;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("父级Id")
    private Integer pId;


}
