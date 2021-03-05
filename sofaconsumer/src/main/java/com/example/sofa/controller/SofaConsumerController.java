package com.example.sofa.controller;

import com.alipay.sofa.runtime.api.annotation.SofaMethod;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.example.sofa.service.ISofaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: sofaboot
 * @description: 测试接口API
 * @author: JH
 * @create: 2021-03-05 13:56
 */
@RestController
@RequestMapping(value = "/consumer")
public class SofaConsumerController {

    @SofaReference(
            binding=@SofaReferenceBinding(
                    bindingType="bolt",
                    methodInfos={
                            @SofaMethod(name="getSofa",timeout=30),
                            @SofaMethod(name="getTest",timeout=50)}))
    private ISofaService iSofaService;

    @ResponseBody
    @GetMapping(value = "/sofa")
    public String getSofa(){
       return iSofaService.getSofa();
    }

    @ResponseBody
    @GetMapping(value = "/test")
    public String getTest(){
        return iSofaService.getTest();
    }
}