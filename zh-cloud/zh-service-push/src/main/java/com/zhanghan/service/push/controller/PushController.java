package com.zhanghan.service.push.controller;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @version 1.0
 */

import com.zhanghan.service.push.controller.request.PushRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PushController {

    @Value("${sid:push}")
    private String sid;

    @RequestMapping(path = "/live/send", method = RequestMethod.POST)
    public Object rest(@RequestBody PushRequest lombokRequest) {

        Map<String, String> result = new HashMap<>();
        result.put("service", "this is:" + sid);
        result.put("companyNo", lombokRequest.getCompanyNo());
        return result;
    }


}