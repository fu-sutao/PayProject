package com.fst.api.application;

import com.fst.api.application.entity.MerchantDTO;

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

    MerchantDTO createMerchant(MerchantDTO merchantDTO);
}
