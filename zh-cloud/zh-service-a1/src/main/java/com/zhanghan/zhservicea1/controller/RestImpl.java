package com.zhanghan.zhservicea1.controller;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @version 1.0
 */

import com.zhanghan.zhservicea1.controller.request.LombokRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestImpl {

    @Value("${sid}")
    private String sid;

    @RequestMapping(path = "/sms/send", method = RequestMethod.POST)
    public Object rest(@RequestBody LombokRequest lombokRequest) {

        Map<String, String> result = new HashMap<>();
        result.put("service", "this is:" + sid);
        result.put("companyNo", lombokRequest.getCompanyNo());
        return result;
    }


}