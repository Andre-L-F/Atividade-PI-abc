/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teste2Principal;

/**
 *
 * @author andre
 */
import javax.swing.*;

public class TeatroView {

    public String mostrarMenu() {
        String[] options = {
            "Cadastro de Usuário",
            "Comprar Ingresso",
            "Imprimir Ingresso",
            "Estatísticas de Vendas",
            "Pesquisar Usuário",
            "Exibir Mapa de Cadeiras",
            "Sair"
        };

        return (String) JOptionPane.showInputDialog(
                null,
                "Escolha uma opção:",
                "Menu Principal",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    public String solicitarEntrada(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public String escolherOpcao(String mensagem, String[] opcoes) {
        return (String) JOptionPane.showInputDialog(
                null,
                mensagem,
                "Selecione uma opção",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
    }
}
