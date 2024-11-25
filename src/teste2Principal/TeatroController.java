/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teste2Principal;

/**
 *
 * @author andre
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//fazer verificador de cpf, verificador de cadeira,verificador de numero(opcional)

public class TeatroController {

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Ingresso> ingressos = new ArrayList<>();
    private TeatroView view;

    public TeatroController(TeatroView view) {
        this.usuarios = new ArrayList<>();
        this.ingressos = new ArrayList<>();
        this.view = view;
    }

    public void cadastrarUsuario() {
        try {
            String nome = view.solicitarEntrada("Nome:");
            String cpf = view.solicitarEntrada("CPF:");
            String telefone = view.solicitarEntrada("Telefone:");
            String endereco = view.solicitarEntrada("Endereço:");
            LocalDate dataNascimento = LocalDate.parse(view.solicitarEntrada("Data de Nascimento (YYYY-MM-DD):"));

            for (Usuario u : usuarios) {
                if (u.getCpf().equals(cpf)) {
                    view.mostrarMensagem("Usuário já cadastrado!");
                    return;
                }
            }

            usuarios.add(new Usuario(nome, cpf, telefone, endereco, dataNascimento));
            view.mostrarMensagem("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public void comprarIngresso() {
        try {
            String cpf = view.solicitarEntrada("Informe seu CPF:");
            Usuario usuario = buscarUsuarioPorCpf(cpf);

            if (usuario == null) {
                view.mostrarMensagem("Usuário não encontrado!");
                return;
            }

            String[] pecas = {"Peça A", "Peça B", "Peça C"};
            String peca = view.escolherOpcao("Escolha a peça:", pecas);

            String[] sessoes = {"Manhã", "Tarde", "Noite"};
            String sessao = view.escolherOpcao("Escolha a sessão:", sessoes);

            String[] areas = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
            String area = view.escolherOpcao("Escolha a área:", areas);

            int poltrona = Integer.parseInt(view.solicitarEntrada("Escolha o número da poltrona:"));

            double valor = switch (area) {
                case "Plateia A" ->
                    40.00;
                case "Plateia B" ->
                    60.00;
                case "Frisa" ->
                    600.00;
                case "Camarote" ->
                    800.0;
                case "Balcão Nobre" ->
                    250.0;
                default ->
                    0.0;
            };

            for (Ingresso i : ingressos) {
                if (i.getPeca().equals(peca) && i.getSessao().equals(sessao) && i.getPoltrona() == poltrona) {
                    view.mostrarMensagem("Poltrona já ocupada!");
                    return;
                }
            }

            ingressos.add(new Ingresso(cpf, peca, sessao, area, poltrona, valor));
            view.mostrarMensagem("Ingresso comprado com sucesso!");
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao comprar ingresso: " + e.getMessage());
        }
    }

    public void imprimirIngresso() {
        try {
            String cpf = view.solicitarEntrada("Informe seu CPF:");

            for (Ingresso i : ingressos) {
                if (i.getCpfCliente().equals(cpf)) {
                    String conteudo = "INGRESSO\n"
                            + "Cliente CPF: " + i.getCpfCliente() + "\n"
                            + "Peça: " + i.getPeca() + "\n"
                            + "Sessão: " + i.getSessao() + "\n"
                            + "Área: " + i.getArea() + "\n"
                            + "Poltrona: " + i.getPoltrona() + "\n"
                            + "Valor: R$ " + i.getValor() + "\n";

                    String nomeArquivo = cpf + "_ingresso.txt";
                    BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo));
                    writer.write(conteudo);
                    writer.close();

                    view.mostrarMensagem("Ingresso salvo em " + nomeArquivo);
                    return;
                }
            }

            view.mostrarMensagem("Nenhum ingresso encontrado para o CPF informado!");
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao imprimir ingresso: " + e.getMessage());
        }
    }

    private Usuario buscarUsuarioPorCpf(String cpf) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf)) {
                return u;
            }
        }
        return null;
    }

    public void gerarEstatisticas() {
        try {
            if (ingressos.isEmpty()) {
                view.mostrarMensagem("Nenhum ingresso foi vendido!");
                return;
            }

            Map<String, Integer> vendasPorPeca = new HashMap<>();
            Map<String, Integer> ocupacaoPorSessao = new HashMap<>();
            Map<String, Double> lucroPorPeca = new HashMap<>();

            for (Ingresso ingresso : ingressos) {
                // Estatísticas de peças
                vendasPorPeca.put(ingresso.getPeca(), vendasPorPeca.getOrDefault(ingresso.getPeca(), 0) + 1);
                lucroPorPeca.put(ingresso.getPeca(), lucroPorPeca.getOrDefault(ingresso.getPeca(), 0.0) + ingresso.getValor());

                // Estatísticas de sessões
                ocupacaoPorSessao.put(ingresso.getSessao(), ocupacaoPorSessao.getOrDefault(ingresso.getSessao(), 0) + 1);
            }

            // Determinar maiores/menores estatísticas
            String pecaMaisVendida = Collections.max(vendasPorPeca.entrySet(), Map.Entry.comparingByValue()).getKey();
            String pecaMenosVendida = Collections.min(vendasPorPeca.entrySet(), Map.Entry.comparingByValue()).getKey();

            String sessaoMaisOcupada = Collections.max(ocupacaoPorSessao.entrySet(), Map.Entry.comparingByValue()).getKey();
            String sessaoMenosOcupada = Collections.min(ocupacaoPorSessao.entrySet(), Map.Entry.comparingByValue()).getKey();

            String pecaMaisLucrativa = Collections.max(lucroPorPeca.entrySet(), Map.Entry.comparingByValue()).getKey();
            String pecaMenosLucrativa = Collections.min(lucroPorPeca.entrySet(), Map.Entry.comparingByValue()).getKey();

            double lucroMedio = lucroPorPeca.values().stream().mapToDouble(Double::doubleValue).sum() / vendasPorPeca.size();

            // Exibir estatísticas
            String estatisticas = """
                Estatísticas de Venda:
                ----------------------
                Peça mais vendida: %s
                Peça menos vendida: %s
                Sessão mais ocupada: %s
                Sessão menos ocupada: %s
                Peça mais lucrativa: %s
                Peça menos lucrativa: %s
                Lucro médio do teatro por peça: R$ %.2f
                """.formatted(
                    pecaMaisVendida, pecaMenosVendida, sessaoMaisOcupada, sessaoMenosOcupada, pecaMaisLucrativa, pecaMenosLucrativa, lucroMedio
            );

            // Salvar estatísticas em arquivo .csv
            BufferedWriter writer = new BufferedWriter(new FileWriter("estatisticas_venda.csv"));
            writer.write(estatisticas);
            writer.close();

            view.mostrarMensagem("Estatísticas geradas e salvas em 'estatisticas_venda.csv'.");
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao gerar estatísticas: " + e.getMessage());
        }
    }

    public void pesquisarUsuario() {
        try {
            String cpf = view.solicitarEntrada("Informe o CPF do usuário:");
            Usuario usuario = buscarUsuarioPorCpf(cpf);

            if (usuario == null) {
                view.mostrarMensagem("Usuário não encontrado!");
                return;
            }

            StringBuilder ingressosUsuario = new StringBuilder("Ingressos comprados pelo usuário:\n");

            for (Ingresso ingresso : ingressos) {
                if (ingresso.getCpfCliente().equals(cpf)) {
                    ingressosUsuario.append(
                            """
                        Peça: %s | Sessão: %s | Área: %s | Poltrona: %d | Valor: R$ %.2f
                        """.formatted(
                                    ingresso.getPeca(), ingresso.getSessao(), ingresso.getArea(), ingresso.getPoltrona(), ingresso.getValor()
                            )
                    );
                }
            }

            if (ingressosUsuario.length() == 0) {
                view.mostrarMensagem("Nenhum ingresso encontrado para o CPF informado.");
            } else {
                view.mostrarMensagem(ingressosUsuario.toString());
            }
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao pesquisar usuário: " + e.getMessage());
        }
    }

    public void mostrarMapaCadeiras() {
        new MapaCadeiras(ingressos);
    }

    // Classe interna para o Mapa de Cadeiras
    private class MapaCadeiras extends JFrame {

        private List<Ingresso> ingressos;
        private JComboBox<String> comboPeca, comboSessao, comboArea;
        private JPanel panelCadeiras;
        private int totalCadeiras = 50; // Ajuste o total conforme a capacidade

        public MapaCadeiras(List<Ingresso> ingressos) {
            this.ingressos = ingressos;

            setTitle("Mapa de Cadeiras");
            setSize(700, 700);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta janela
            setLayout(new BorderLayout());

            // Painel de filtros
            JPanel panelFiltros = new JPanel(new GridLayout(1, 3));
            comboPeca = new JComboBox<>(new String[]{"Peça A", "Peça B", "Peça C"});
            comboSessao = new JComboBox<>(new String[]{"Manhã", "Tarde", "Noite"});
            comboArea = new JComboBox<>(new String[]{"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre", "null"});

            comboPeca.addActionListener(e -> atualizarMapa());
            comboSessao.addActionListener(e -> atualizarMapa());
            comboArea.addActionListener(e -> atualizarMapa());

            panelFiltros.add(new JLabel("Peça:"));
            panelFiltros.add(comboPeca);
            panelFiltros.add(new JLabel("Sessão:"));
            panelFiltros.add(comboSessao);
            panelFiltros.add(new JLabel("Área:"));
            panelFiltros.add(comboArea);

            add(panelFiltros, BorderLayout.NORTH);

            // Painel de cadeiras
            panelCadeiras = new JPanel(new GridLayout(5, 10)); // 5 linhas x 10 colunas
            add(panelCadeiras, BorderLayout.CENTER);

            atualizarMapa();
            setVisible(true);
        }

        private void atualizarMapa() {
            panelCadeiras.removeAll(); // Limpa o painel

            // Obter filtros selecionados
            String pecaSelecionada = (String) comboPeca.getSelectedItem();
            String sessaoSelecionada = (String) comboSessao.getSelectedItem();
            String areaSelecionada = (String) comboArea.getSelectedItem();

            // Determinar o número total de cadeiras com base na área selecionada
            int totalCadeiras = getTotalCadeiras(areaSelecionada);

            // Ajustar o layout do painel dinamicamente
            int colunas = 10; // Definimos 10 colunas fixas
            int linhas = (int) Math.ceil(totalCadeiras / (double) colunas);
            panelCadeiras.setLayout(new GridLayout(linhas, colunas));

            // Preencher o painel com cadeiras
            for (int i = 1; i <= totalCadeiras; i++) {
                JButton cadeira = new JButton(String.valueOf(i));
                cadeira.setBackground(isOcupada(pecaSelecionada, sessaoSelecionada, areaSelecionada, i)
                        ? Color.RED
                        : Color.GREEN);
                cadeira.setEnabled(false); // Apenas visual
                panelCadeiras.add(cadeira);
            }

            panelCadeiras.revalidate();
            panelCadeiras.repaint();
        }

        private int getTotalCadeiras(String area) {
            switch (area) {
                case "Plateia A":
                    return 25; // Número de cadeiras para a área VIP
                case "Plateia B":
                    return 100; // Número de cadeiras para a área Regular
                case "Frisa":
                    return 6; // Número de cadeiras para a área Econômica
                case "Camarote":
                    return 5; // Número de cadeiras para a área Econômica
                case "Balcão Nobre":
                    return 50; // Número de cadeiras para a área Econômica
                default:
                    return 0; // Valor padrão
            }
        }

        private boolean isOcupada(String peca, String sessao, String area, int poltrona) {
            // Verifica se a cadeira está ocupada para os filtros escolhidos
            return ingressos.stream().anyMatch(i
                    -> i.getPeca().equals(peca)
                    && i.getSessao().equals(sessao)
                    && i.getArea().equals(area)
                    && i.getPoltrona() == poltrona
            );
        }
    }
}
