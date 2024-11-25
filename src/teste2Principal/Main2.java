/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teste2Principal;

import javax.swing.JOptionPane;

/**
 *
 * @author andre
 */
public class Main2 {
    public static void main(String[] args) {
        TeatroView view = new TeatroView();
        TeatroController controller = new TeatroController(view);

        while (true) {
            String escolha = view.mostrarMenu();
            if (escolha == null || escolha.equals("Sair")) break;

            switch (escolha) {
                case "Cadastro de Usuário":
                    controller.cadastrarUsuario();
                    break;
                case "Comprar Ingresso":
                    controller.comprarIngresso();
                    break;
                case "Imprimir Ingresso":
                    controller.imprimirIngresso();
                    break;
                case "Estatísticas de Vendas":
                    controller.gerarEstatisticas();
                    break;
                case "Pesquisar Usuário":
                    controller.pesquisarUsuario();
                    break;
                    case "Exibir Mapa de Cadeiras":
                    controller.mostrarMapaCadeiras();
                    break;
                default:
                    view.mostrarMensagem("Opção inválida!");
                    break;
            }
        }
    }
}


