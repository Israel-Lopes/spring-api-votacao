package com.urna.app.api.utils;

public class FormatarCpf {
    public String replace(String cpf) {
        return cpf
                .replace(" ", "")
                .replace("-", "")
                .replace(".", "");
    }
}
