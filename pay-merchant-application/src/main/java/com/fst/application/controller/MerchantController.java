package com.fst.application.controller;

import com.aliyun.oss.OSS;
import com.fst.api.application.MerchantServiceApi;
import com.fst.application.service.MerchantService;
import com.fst.application.service.SmsService;
import com.fst.application.vo.MerchantDetailVO;
import com.fst.application.vo.MerchantRegisterVo;
import com.fst.common.BusinessException;
import com.fst.common.marchant.domain.MerchantDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author：fst
 * @data：2021/8/17 21:13
 * @info：商户控制层
 * 1、MerchantRegisterVO用于应用层接收前端请求及响应前端数据。
 * 2、MerchantDTO 用于服务层传入及响应数据。
 * 3、entity(实体类) 用于持久层传入及响应数据。
 *      git config --global http.sslVerify "false"
 * MapStruct  数据传输对象转换的繁琐
 */
//merchant
@RestController
public class MerchantController {
    //下面是一个接口
    @Reference
    MerchantServiceApi merchantServiceApi;
    @Autowired
    SmsService smsService;
    @Autowired
    MerchantService merchantService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    OSS oss;

    //远程调用 根据商户id获取商户注册信息
    @GetMapping("/merchants/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id") Long id) {
        return merchantService.queryMerchantById(id);
    }
    //获取短信验证码
    @GetMapping("/SendSms")
    public String getSendSms(@RequestParam("phone") String mobile){
        //拿到验证码的key
        return smsService.getSendSms(mobile);
    }
    //注册商户
    @PostMapping("/merchants/register")
    public MerchantRegisterVo registerMerchant(@RequestBody MerchantRegisterVo merchantRegister) {
        return merchantService.registerMerchant(merchantRegister);
    }

    //上传证件照
    @ApiOperation("上传证件照")
    @ApiParam(value = "证件照",required = true)
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws BusinessException {
        return merchantService.upload(multipartFile);
    }

    @ApiOperation("资质申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantInfo", value = "商户认证资料", required = true, dataType = "MerchantDetailVO", paramType = "body")
    })
    @PostMapping("/my/merchants/save")
    public void saveMerchant(@RequestBody MerchantDetailVO merchantInfo){
        //merchantServiceApi.applyMerchant(merchantInfo);
        merchantService.saveMerchant(merchantInfo);
    }

}
















































