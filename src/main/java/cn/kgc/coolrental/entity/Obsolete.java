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
@ApiModel("二手信息")
public class Obsolete implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("标题")
    private String titke;
    @ApiModelProperty("品牌")
    private Integer brandId;
    @ApiModelProperty("型号")
    private Integer modelId;
    @ApiModelProperty("意向价格")
    private Double indicativePrice;
    @ApiModelProperty("出厂年限")
    private Date factoryDate;
    @ApiModelProperty("表现小时数")
    private Integer workingTime;
    @ApiModelProperty("放置地点")
    private String site;
    @ApiModelProperty("产品图片")
    private String image;
    @ApiModelProperty("设备优势")
    private String advantage;


}
