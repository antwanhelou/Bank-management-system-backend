package com.banksystem.BankSystem.beans.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "privileged-admins")
public class AdminConfig {

    private List<String> privilegedAdmins;

    public List<String> getPrivilegedAdmins() {
        return privilegedAdmins;
    }
}
