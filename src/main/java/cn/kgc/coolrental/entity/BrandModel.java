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
@ApiModel("品牌型号")
public class BrandModel implements Serializable {
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("品牌型号名称")
    private String name;

}
