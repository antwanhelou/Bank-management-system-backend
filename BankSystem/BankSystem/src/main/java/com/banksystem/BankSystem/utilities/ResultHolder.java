package com.banksystem.BankSystem.utilities;

import java.util.Map;

public interface ResultHolder{




    static Map<String, String> success() {
        return Map.of("result", "true");
    }

    static Map<String, String> success(String message) {
        return Map.of("result", "true", "message", message);
    }

    static Map<String, String> fail(String message) {
        return Map.of("result", "false", "message", message);
    }



}
