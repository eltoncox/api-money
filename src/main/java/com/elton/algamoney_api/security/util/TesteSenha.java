package com.elton.algamoney_api.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteSenha {
    public static void main(String[] args) {
        // Senha fornecida pelo usuário
        String rawPassword = "elton7";

        // Senha codificada (copiada do banco de dados)
        String encodedPassword = "$2a$10$Fa4fRSs231Xk3lmlRvjm4uTJ7ucDASszf/hLqjzq5MrUgQo/VF83.";

        // Cria uma instância do BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Verifica se a senha original corresponde à senha codificada
        boolean matches = encoder.matches(rawPassword, encodedPassword);

        // Exibe o resultado
        System.out.println("Senha válida? " + matches);
    }
}