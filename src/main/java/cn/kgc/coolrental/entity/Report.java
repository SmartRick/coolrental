package cn.kgc.coolrental.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("举报管理")
public class Report implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("被举报信息")
    private Integer userId;
    @ApiModelProperty("举报人")
    private Integer informerId;
    @ApiModelProperty("举报时间")
    private Date reportDate;
    @ApiModelProperty("举报原因")
    private String indicativePrice;


}
