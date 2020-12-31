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
@ApiModel("支付管理")
public class Pay implements Serializable {

    @ApiModelProperty("支付单号")
    private Integer id;
    @ApiModelProperty("信息编号")
    private String infoId;
    @ApiModelProperty("支付用户")
    private Integer userId;
    @ApiModelProperty("信息标题")
    private String title;
    @ApiModelProperty("支付金额")
    private Double paymentAmount;
    @ApiModelProperty("支付方式")
    private String paymentWay;
    @ApiModelProperty("支付状态")
    private Integer paymentState;
    @ApiModelProperty("支付日期")
    private Date paymentDate;
    @ApiModelProperty("退款金额")
    private Double refundAmount;
    @ApiModelProperty("退款日期")
    private Date refundDate;
    @ApiModelProperty("操作退款人")
    private Integer operatorId;
    @ApiModelProperty("退款原因")
    private String refundCause;
    @ApiModelProperty("补充说明")
    private String explain;


}
