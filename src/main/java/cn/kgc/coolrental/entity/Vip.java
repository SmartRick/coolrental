package cn.kgc.coolrental.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("会员")
public class Vip implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("用户")
    private Integer userId;
    @ApiModelProperty("会员id")
    private String vipId;
    @ApiModelProperty("启动状态")
    private Integer status;

}
