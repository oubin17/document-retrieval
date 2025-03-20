package com.odk.basebootstrap;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.odk"})
//JPA扫描路径
@EnableJpaRepositories(basePackages = "com.odk.basedomain")
//@EnableRedisEnhancedRepositories(basePackages = "com.odk.redis")
@EnableRedisDocumentRepositories(basePackages = "com.odk.redis")
@EntityScan("com.odk.basedomain")
//开启审计功能，自动添加时间
@EnableJpaAuditing
//es jpa扫描路径
//@EnableElasticsearchRepositories(basePackages = "com.odk.basedomain")
public class OdkBootstrapApplication {

    public static void main(String[] args) {

        try {
            SpringApplication.run(OdkBootstrapApplication.class, args);
        } catch (Throwable t) {
            System.err.println("启动异常: " + t.getMessage());
            t.printStackTrace();
        }
    }

}
