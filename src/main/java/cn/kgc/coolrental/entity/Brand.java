package cn.kgc.coolrental.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("品牌")
public class Brand implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("品牌名称")
    private String name;
    @ApiModelProperty("启动状态")
    private Integer status;
    @ApiModelProperty("品牌的型号")
    @TableField(exist = false)
    private List<BrandModel> modelList;


}
