package com.fst.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author：fst
 * @data：2021/8/19 19:32
 * @info：从前端表单接收的数据，后缀为VO
 */
@Data
public class MerchantRegisterVo implements Serializable {
    @ApiModelProperty("商户手机号")
    private String mobile;
    @ApiModelProperty("商户用户名")
    private String username;
    @ApiModelProperty("商户密码")
    private String password;
    @ApiModelProperty("验证码的key")
    private String verifiykey;
    @ApiModelProperty("验证码")
    private String verifiyCode;
}
