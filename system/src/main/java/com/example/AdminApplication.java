package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@SpringBootApplication
@EnableConfigurationProperties
public class AdminApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext application = SpringApplication.run(AdminApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        path = path == null ? "" : path;
        
        System.out.println("\n----------------------------------------------------------\n" +
                "  应用启动成功！\n" +
                "  本地访问: http://localhost:" + port + path + "\n" +
                "  外部访问: http://" + ip + ":" + port + path + "\n" +
                "  Swagger文档: http://" + ip + ":" + port + path + "swagger-ui/index.html\n" +
                "----------------------------------------------------------\n");
    }
} 