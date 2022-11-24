package com.xiechanglei.code.qingyan.code.vo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TokenProperties.class)
@RequiredArgsConstructor
public class TokenAutoConfiguration {

    private final TokenProperties tokenProperties;

    @Bean
    @ConditionalOnMissingBean //默认值是方法的返回值
    public TokenHandler tokenHandler() {
        return new TokenHandler(tokenProperties.getSecretKey());
    }
}
