package com.fst.service.service;
import com.fst.api.application.MerchantServiceApi;
import com.fst.api.application.entity.MerchantDTO;
import com.fst.common.marchant.domain.Merchant;
import com.fst.service.mapper.MerchantMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author：fst
 * @data：2021/8/17 21:05
 * @info：商户服务，通过Doubble调用，不是RestFul风格，所以没有controller
 */
@Service
public class MerchantServiceImpl implements MerchantServiceApi {

    // @Value("${spring.datasource.type}")
    // String data;

    @Autowired
    MerchantMapper merchantMapper;

    @Override
    public MerchantDTO queryMerchantById(Long merchantId) {
        Merchant merchant = merchantMapper.selectById(merchantId);
        MerchantDTO merchantDTO = new MerchantDTO();
        BeanUtils.copyProperties(merchant,merchantDTO);
        return merchantDTO;
    }

    @Override
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) {
        Merchant merchant = new Merchant();
        //设置审核状态0‐未申请,1‐已申请待审核,2‐审核通过,3‐审核拒绝
        merchant.setAuditStatus("0");
        //设置手机号
        merchant.setMobile(merchantDTO.getMobile());
        //...设置其他信息

        //保存商户
        merchantMapper.insert(merchant);
        //将新增商户id返回
        merchantDTO.setId(merchant.getId());
        return merchantDTO;

    }
}
