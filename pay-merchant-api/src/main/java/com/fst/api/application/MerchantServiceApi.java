package com.fst.api.application;

import com.fst.common.BusinessException;
import com.fst.common.marchant.domain.MerchantDTO;

/**
 * @author：fst
 * @data：2021/8/17 19:54
 * @info：
 */
public interface MerchantServiceApi {
    /**
     * 根据ID查询商户信息
     * @param merchantId
     * @return
     */
    MerchantDTO queryMerchantById(Long   merchantId);

    MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException;

    /**
     * 资质申请接口
     * @param merchantId 商户id
     * @param merchantDTO 资质申请的信息
     * @throws BusinessException
     */
    void applyMerchant(Long merchantId,MerchantDTO merchantDTO) throws BusinessException;
}
