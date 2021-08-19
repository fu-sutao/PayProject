package com.fst.application.controller;

import com.fst.api.application.MerchantServiceApi;
import com.fst.api.application.entity.MerchantDTO;
import com.fst.application.service.SmsService;
import com.fst.application.vo.MerchantRegisterVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author：fst
 * @data：2021/8/17 21:13
 * @info：商户控制层
 * 1、MerchantRegisterVO用于应用层接收前端请求及响应前端数据。
 * 2、MerchantDTO 用于服务层传入及响应数据。
 * 3、entity(实体类) 用于持久层传入及响应数据。
 *
 * MapStruct  数据传输对象转换的繁琐
 */

@RestController
public class MerchantController {
    //下面是一个接口
    @Reference
    MerchantServiceApi merchantServiceApi;
    @Autowired
    SmsService smsService;

    //远程调用 获取商户注册信息
    @GetMapping("/merchants/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id") Long id) {
        MerchantDTO merchantDTO = merchantServiceApi.queryMerchantById(id);
        return merchantDTO;
    }
    //获取短信验证码
    @GetMapping("/SendSms")
    public String getSendSms(@RequestParam("phone") String mobile){
        //拿到验证码的key
        return smsService.getSendSms(mobile);
    }
    //验证验证码信息
    @PostMapping("/merchants/register")
    public MerchantRegisterVo registerMerchant(@RequestBody MerchantRegisterVo merchantRegister) {
        //校验验证码
        smsService.checkVerifiyCode(merchantRegister.getVerifiykey(),merchantRegister.getVerifiyCode());
        //注册商户
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setUsername(merchantRegister.getUsername());
        merchantDTO.setMobile(merchantRegister.getMobile());
        merchantDTO.setPassword(merchantRegister.getPassword());
        merchantServiceApi.createMerchant(merchantDTO);

        return merchantRegister;
    }


    //验证成功账号注册(登录)成功，开始注册商户信息

}
