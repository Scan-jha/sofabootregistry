package com.example.sofa.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.example.sofa.service.ISofaService;
import org.springframework.stereotype.Service;

/**
 * @program: sofaboot
 * @description: 是啊先接口类
 * @author: JH
 * @create: 2021-03-05 13:42
 */
@Service
@SofaService(interfaceType = ISofaService.class, bindings = { @SofaServiceBinding(bindingType = "bolt") } )
public class SofaSreviceImpl implements ISofaService {
    @Override
    public String getSofa() {
        String sofa="sofa";
        return sofa;
    }

    @Override
    public String getTest() {
        String test="test";
        return test;
    }
}