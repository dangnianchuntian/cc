package com.zhanghan.zhzuul.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "gray.bean")
public class GrayBean {

    private boolean enabled;

    private String suffix;

    private List<String> graylist;

    private String companyNo;

}