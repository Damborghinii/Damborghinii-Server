package org.dongguk.dambo.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractAddrProbe {
    private final Environment env;

    @PostConstruct
    void probe() {
        String key = "contract.musicnft.address";
        System.out.println("=== PROBE: final " + key + " = [" + env.getProperty(key) + "]");
        for (var ps : ((AbstractEnvironment) env).getPropertySources()) {
            Object v = ps.getProperty(key);
            if (v != null) {
                System.out.println(" -> " + ps.getName() + " => " + v);
            }
        }
    }
}
