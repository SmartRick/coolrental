package cn.kgc.coolrental.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 求租
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("求租信息")
public class AskRent implements Serializable {
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("详细说明")
    private String description;
    @ApiModelProperty("设备类型")
    private Integer brandId;
    @ApiModelProperty("品牌型号")
    private Integer modelId;
    @ApiModelProperty("放置地点")
    private String site;
    @ApiModelProperty("工程类型")
    private String projectType;
    @ApiModelProperty("出厂年限")
    private Date factoryDate;
    @ApiModelProperty("表显小时数")
    private Integer workingTime;
    @ApiModelProperty("意向价格")
    private Double indicativePrice;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("机手")
    private String staff;
    @ApiModelProperty("保险")
    private String insurance;

}
