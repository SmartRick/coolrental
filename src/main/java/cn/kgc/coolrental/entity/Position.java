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
@ApiModel("岗位信息")
public class Position implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("岗位类型")
    private String positionType;
    @ApiModelProperty("招募人数")
    private Integer recruiting;
    @ApiModelProperty("所属工程类型")
    private String project;
    @ApiModelProperty("工程地点")
    private String site;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("岗位薪资")
    private Double compensation;
    @ApiModelProperty("经验要求")
    private String experience;
    @ApiModelProperty("年龄要求")
    private Integer ageLimit;
    @ApiModelProperty("福利待遇")
    private String weal;
    @ApiModelProperty("公司名称")
    private String company;
    @ApiModelProperty("其他要求")
    private String other;


}
