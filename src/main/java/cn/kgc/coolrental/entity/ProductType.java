package cn.kgc.coolrental.entity;

import cn.kgc.coolrental.util.Json2ListTypeHandler;
import cn.kgc.coolrental.util.Json2SetTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("产品类型")
public class ProductType implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("产品类型名称")
    private String productName;
    @ApiModelProperty("关联父级对象")
    private Integer pId;
    @ApiModelProperty("产品数量")
    private Integer num;
    @ApiModelProperty("规格")
    @TableField(value = "unit", jdbcType = JdbcType.VARCHAR, typeHandler = Json2ListTypeHandler.class)
    private List<String> unit;
    @ApiModelProperty("配件")
    @TableField(value = "accessories", jdbcType = JdbcType.VARCHAR, typeHandler = Json2ListTypeHandler.class)
    private List<String> accessories;
    @ApiModelProperty("二手属性")
    @TableField(value = "used", jdbcType = JdbcType.VARCHAR, typeHandler = Json2ListTypeHandler.class)
    private List<String> used;
    @ApiModelProperty("展示图片")
    private String showImg;
    @ApiModelProperty("状态")
    private Integer status;
}
