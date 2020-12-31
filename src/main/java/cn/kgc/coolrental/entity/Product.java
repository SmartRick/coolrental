package cn.kgc.coolrental.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("产品表")
public class Product implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("品牌")
    private Brand brand;
    @ApiModelProperty("型号")
    private BrandModel model;
    @ApiModelProperty("产品名称")
    private String name;
    @ApiModelProperty("计量单位")
    private String metering;
    @ApiModelProperty("关键词")
    private String keyword;
    @ApiModelProperty("规格")
    private Set<String> specification;
    @ApiModelProperty("产品图片")
    private Set<String> image;
    @ApiModelProperty("规格说明")
    private String description;
    @ApiModelProperty("启动状态")
    private Integer status;


}
