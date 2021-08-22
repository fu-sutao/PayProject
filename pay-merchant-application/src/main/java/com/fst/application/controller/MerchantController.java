package com.fst.application.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.fst.api.application.MerchantServiceApi;
import com.fst.api.application.entity.MerchantDTO;
import com.fst.application.service.SmsService;
import com.fst.application.vo.MerchantDetailVO;
import com.fst.application.vo.MerchantRegisterVo;
import com.fst.common.BusinessException;
import com.fst.common.domain.CommonErrorCode;
import com.fst.common.util.PhoneUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

@RestController
public class MerchantController {
    //下面是一个接口
    @Reference
    MerchantServiceApi merchantServiceApi;
    @Autowired
    SmsService smsService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    OSS oss;
    @Value("${oss.aliyun.bucket}")
    private String bucket;
    @Value("${oss.aliyun.domain}")
    private String domain;

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
        // 1.校验
        if (merchantRegister == null) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }

        //手机号非空校验
        if (StringUtils.isBlank(merchantRegister.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_100112);
        }

        //校验手机号的合法性
        if (!PhoneUtil.isMatches(merchantRegister.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_100109);
        }

        //联系人非空校验
        if (StringUtils.isBlank(merchantRegister.getUsername())) {
            throw new BusinessException(CommonErrorCode.E_100110);
        }
        //密码非空校验
        if (StringUtils.isBlank(merchantRegister.getPassword())) {
            throw new BusinessException(CommonErrorCode.E_100111);
        }
        //验证码非空校验
        if (StringUtils.isBlank(merchantRegister.getVerifiyCode()) ||
                StringUtils.isBlank(merchantRegister.getVerifiykey())) {
            throw new BusinessException(CommonErrorCode.E_100103);
        }

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

    //
    //上传证件照
    @ApiOperation("上传证件照")
    @PostMapping("/upload")
    public String upload(@ApiParam(value = "证件照",required = true) @RequestParam("file") MultipartFile multipartFile) throws BusinessException {

        String fileName = System.currentTimeMillis() + ""; //文件名，当前系统时间戳
        String originalFilename = multipartFile.getOriginalFilename(); // 上传文件原文件名
        String ext = originalFilename.substring(originalFilename.indexOf(".")); // 截取获取扩展名

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName + ext, multipartFile.getInputStream());
            oss.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_100106);
        }
        return domain + "/" + fileName + ext;
    }
    @ApiOperation("资质申请")
    @PostMapping("/my/merchants/save")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantInfo", value = "商户认证资料", required = true, dataType = "MerchantDetailVO", paramType = "body")
    })
    public void saveMerchant(@RequestBody MerchantDetailVO merchantInfo){

    }

}
