package com.urna.app.api.utils;

import org.springframework.stereotype.Component;

@Component
public class FormatarCpf {
    public String replace(String cpf) {
        return cpf
                .replace(" ", "")
                .replace("-", "")
                .replace(".", "");
    }
}