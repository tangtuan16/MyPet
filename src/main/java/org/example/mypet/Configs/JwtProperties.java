package org.example.mypet.Configs;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
public class JwtProperties {
    private String secret;
    private long accessExpirationMs;
    private long refreshExpirationMs;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setAccessExpirationMs(long v) {
        this.accessExpirationMs = v;
    }

    public void setRefreshExpirationMs(long v) {
        this.refreshExpirationMs = v;
    }
}
