package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    @FXML
    private Button botaoLogin;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private TextField campoUsuario;

    @FXML
    void initialize(MouseEvent event) {
        trocarParaTelaRegistro();
    }

    @FXML
    public void initialize() {
        botaoLogin.setOnAction(event -> verificarLogin());
    }

    private void verificarLogin() {
        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        if (usuario.isEmpty() || senha.isEmpty()) {
            System.out.println("Usuário e senha são obrigatórios.");
            return;
        }

        boolean loginBemSucedido = verificarCredenciais(usuario, senha);

        if (loginBemSucedido) {
            System.out.println("Login bem-sucedido!");
            trocarParaTelaMenu();
        } else {
            System.out.println("Usuário ou senha incorretos.");
        }
    }

    private boolean verificarCredenciais(String usuario, String senha) {
        try (BufferedReader reader = new BufferedReader(new FileReader("cadastro.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    String usuarioCadastrado = dados[0];
                    String senhaCadastrada = dados[3];

                    if (usuarioCadastrado.equals(usuario) && senhaCadastrada.equals(senha)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return false;
    }
    
    private void trocarParaTelaMenu() {
        try {
            App.setRoot("menu"); // Chamando o método da classe App para trocar a tela
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de login: " + e.getMessage());
        }
    }

private void trocarParaTelaRegistro() {
        try {
            App.setRoot("registro"); // Chamando o método da classe App para trocar a tela
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de login: " + e.getMessage());
        }
    }
}



