package cn.kgc.coolrental.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@ApiModel("用户对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @ApiModelProperty("用户Id")
    private Integer id;
    @ApiModelProperty("用户姓名")
    private String name;
    @ApiModelProperty("用户昵称")
    private String nick;
    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户电话号码")
    private String phone;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("账户注册日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registerDate;
    @ApiModelProperty("用户账户状态")
    private Integer status;

}
