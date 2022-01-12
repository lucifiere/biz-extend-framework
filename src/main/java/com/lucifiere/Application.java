package com.lucifiere;

import com.taobao.pandora.boot.PandoraBootstrap;
import org.springframework.context.annotation.ImportResource;

/**
 * SpringBoot Application
 *
 * @author tiankuo
 */
@SpringBootApplication(scanBasePackages = "com.alibaba.dme.bcp.ump")
@ImportResource("classpath:/spring.xml")
public class Application {
    public static void main(String[] args) {
        PandoraBootstrap.run(args);
        SpringApplication.run(Application.class, args);
        PandoraBootstrap.markStartupAndWait();
    }
}
