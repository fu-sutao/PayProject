package com.fst.common.marchant.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName merchant
 */
@TableName(value ="merchant")
@Data
public class Merchant implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 企业编号
     */
    private String merchantNo;

    /**
     * 企业地址
     */
    private String merchantAddress;

    /**
     * 商户类型
     */
    private String merchantType;

    /**
     * 营业执照（企业证明）
     */
    private String businessLicensesImg;

    /**
     * 法人身份证正面照片
     */
    private String idCardFrontImg;

    /**
     * 法人身份证反面照片
     */
    private String idCardAfterImg;

    /**
     * 联系人姓名
     */
    private String username;

    /**
     * 联系人手机号(关联统一账号)
     */
    private String mobile;

    /**
     * 联系人地址
     */
    private String contactsAddress;

    /**
     * 审核状态 0-未申请,1-已申请待审核,2-审核通过,3-审核拒绝
     */
    private String auditStatus;

    /**
     * 租户ID,关联统一用户
     */
    private Long tenantId;


}