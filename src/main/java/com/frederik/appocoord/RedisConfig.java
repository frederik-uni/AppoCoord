package com.frederik.appocoord;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SslOptions;
import io.lettuce.core.SslVerifyMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;

@Configuration
public class RedisConfig {
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        String host = (isRunningInDocker() || isRunningInKubernetes()) ? "redis" : "localhost";
        var pw = System.getenv("REDIS_PASSWORD");
        var conf = new RedisStandaloneConfiguration(host, 6379);
        if (pw != null) {
            conf.setPassword(RedisPassword.of(pw));
        }
        String sslEnv = System.getenv("SSL");
        boolean isSslEnabled = "true".equalsIgnoreCase(sslEnv);
        if (isSslEnabled) {
            String certPath = System.getenv("CERT_PATH");
            if (!certPath.endsWith("/")) {
                certPath += "/";
            }
            SslOptions sslOptions = SslOptions.builder()
                    .jdkSslProvider().trustManager(new File(certPath+"ca.crt"))
                    .build();
            ClientOptions clientOptions = ClientOptions.builder().sslOptions(sslOptions).build();
            LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder().clientOptions(clientOptions)
                    .useSsl().verifyPeer(SslVerifyMode.CA)
                    .build();
            return new LettuceConnectionFactory(conf, clientConfig);
        } else {
            return new LettuceConnectionFactory(conf);
        }

    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
    public static boolean isRunningInKubernetes() {
        String kubernetesEnv = System.getenv("KUBERNETES_SERVICE_HOST");
        if (kubernetesEnv != null && !kubernetesEnv.isEmpty()) {
            return true;
        }

        File saToken = new File("/var/run/secrets/kubernetes.io/serviceaccount/token");
        return saToken.exists();
    }


    private boolean isRunningInDocker() {
        return new File("/.dockerenv").exists() || "true".equalsIgnoreCase(System.getenv("RUNNING_IN_DOCKER"));
    }
}