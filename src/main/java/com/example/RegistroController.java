package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegistroController {

    @FXML
    private Button botaoCadastro;

    @FXML
    private Label botaoPossuiCadastro;

    @FXML
    private TextField campoCPF;

    @FXML
    private PasswordField campoConfSenha;

    @FXML
    private DatePicker campoNascimento;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private TextField campoUsuario;

    @FXML
    public void initialize() {
    botaoCadastro.setOnAction(event -> salvarDados());
    
    // Adicionando o redirecionamento quando o usuário clica no Label "Possui cadastro?"
    botaoPossuiCadastro.setOnMouseClicked(event -> {
        trocarParaTelaLogin();
    });
}

    private void salvarDados() {
        String usuario = campoUsuario.getText();
        String cpf = campoCPF.getText();
        String senha = campoSenha.getText();
        String confSenha = campoConfSenha.getText();
        LocalDate nascimento = campoNascimento.getValue();
        
        // Adicionando mensagens de depuração
        System.out.println("Usuário: '" + usuario + "'");
        System.out.println("CPF: '" + cpf + "'");
        System.out.println("Senha: '" + senha + "'");
        System.out.println("Confirmação de Senha: '" + confSenha + "'");
        System.out.println("Data de Nascimento: '" + nascimento + "'");
        
        // Verificando se todos os campos foram preenchidos
        if (usuario.trim().isEmpty() || cpf.trim().isEmpty() || senha.trim().isEmpty() || confSenha.trim().isEmpty() || nascimento == null) {
            System.out.println("Todos os campos precisam ser preenchidos.");
            return;
        }
    
        // Verificando o formato do CPF
        if (!isValidCPF(cpf)) {
            System.out.println("CPF deve ter 11 dígitos.");
            return;
        }
    
        LocalDate hoje = LocalDate.now();
        int idade = Period.between(nascimento, hoje).getYears();
        if (idade < 18) {
            System.out.println("É necessário ser maior de idade para se cadastrar.");
            return;
        }
        
        if (!senha.equals(confSenha)) {
            System.out.println("As senhas não coincidem.");
            return;
        }
        
        // Salvar os dados de cadastro no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cadastro.txt", true))) {
            writer.write(usuario + ";" + cpf + ";" + nascimento.toString() + ";" + senha);
            writer.newLine();
            System.out.println("Dados salvos com sucesso.");
            // Chamar o método para trocar a cena
            trocarParaTelaLogin();
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
    
    // Método para validar o CPF
    private boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{11}");
    }

    private void trocarParaTelaLogin() {
        try {
            App.setRoot("login"); // Chamando o método da classe App para trocar a tela
        } catch (IOException e) {
            System.out.println("Erro ao carregar a tela de login: " + e.getMessage());
        }
    }
}