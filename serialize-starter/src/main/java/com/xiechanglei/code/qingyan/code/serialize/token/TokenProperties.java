package com.xiechanglei.code.qingyan.code.serialize.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "qingyan.code.token")
@Getter
@Setter
public class TokenProperties {
    private String secretKey = "";
}
