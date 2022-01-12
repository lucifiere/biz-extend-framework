package com.lucifiere.bef;

import com.lucifiere.bef.util.SpringApplicationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author 忠魂
 */
@Component
@DependsOn("befSpringContextHolder")
public class BefConfig {
    @Autowired
    private SpringApplicationContextHolder springApplicationContextHolder;

    private String[] registerDomain = {"com.lucifiere.dme.bcp.ump.biz.functions"};

    private String[] registerProduct = {"com.lucifiere.dme.bcp.ump.biz.product"};

    @EventListener(ContextRefreshedEvent.class)
    public void register() {
        if (springApplicationContextHolder == null) {
            return;
        }
        BefRegister befRegister = new BefRegister();
        befRegister.setRegisterDomain(registerDomain);
        befRegister.setRegisterProduct(registerProduct);
        befRegister.register();
    }
}
