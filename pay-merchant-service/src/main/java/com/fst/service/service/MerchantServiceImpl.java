package com.fst.service.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fst.api.application.MerchantServiceApi;
import com.fst.api.application.entity.MerchantDTO;
import com.fst.common.BusinessException;
import com.fst.common.domain.CommonErrorCode;
import com.fst.common.marchant.domain.Merchant;
import com.fst.common.util.PhoneUtil;
import com.fst.service.mapper.MerchantMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

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
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException {
        if (merchantDTO == null) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        //手机号非空校验
        if (StringUtils.isEmpty(merchantDTO.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        //手机号合法性校验
        if (!PhoneUtil.isMatches(merchantDTO.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_100109);
        }
        //联系人非空校验
        if (StringUtils.isBlank(merchantDTO.getUsername())) {
            throw new BusinessException(CommonErrorCode.E_100110);
        }
        //密码非空校验
        if (StringUtils.isBlank(merchantDTO.getPassword())) {
            throw new BusinessException(CommonErrorCode.E_100111);
        }
        //检验用户是否已经存在
        LambdaQueryWrapper<Merchant> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Merchant::getMobile,merchantDTO.getMobile());
        Integer count  = merchantMapper.selectCount(lqw);
        if(count>0){
            throw  new BusinessException(CommonErrorCode.E_100113);
        }


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
    /**
     * 资质申请接口
     *
     * @param merchantId  商户id
     * @param merchantDTO 资质申请的信息
     * @throws BusinessException
     */
    @Override
    @Transactional
    public void applyMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException {
        if(merchantId == null || merchantDTO == null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //校验merchantId合法性，查询商户表，如果查询不到记录，认为非法
        Merchant merchant = merchantMapper.selectById(merchantId);
        if(merchant == null){
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        //将dto转成entity
        Merchant entity = new Merchant();
        BeanUtils.copyProperties(merchantDTO,entity);
        //Merchant entity = MerchantConvert.INSTANCE.dto2entity(merchantDTO);



        //将必要的参数设置到entity
        entity.setId(merchant.getId());
        entity.setMobile(merchant.getMobile());//因为资质申请的时候手机号不让改，还使用数据库中原来的手机号
        entity.setAuditStatus("1");//审核状态1-已申请待审核
        entity.setTenantId(merchant.getTenantId());
        //调用mapper更新商户表
        merchantMapper.updateById(entity);
    }
}
