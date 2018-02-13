package com.testcompany.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "network.port")
public class NetworkProperties {
    private Integer master;
    private Integer slave1;
    private Integer slave2;

    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }

    public Integer getSlave1() {
        return slave1;
    }

    public void setSlave1(Integer slave1) {
        this.slave1 = slave1;
    }

    public Integer getSlave2() {
        return slave2;
    }

    public void setSlave2(Integer slave2) {
        this.slave2 = slave2;
    }
}
