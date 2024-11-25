/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package teste2Principal;

/**
 *
 * @author andre
 */
public class Ingresso {
    private String cpfCliente, peca, sessao, area;
    private int poltrona;
    private double valor;

    public Ingresso(String cpfCliente, String peca, String sessao, String area, int poltrona, double valor) {
        this.cpfCliente = cpfCliente;
        this.peca = peca;
        this.sessao = sessao;
        this.area = area;
        this.poltrona = poltrona;
        this.valor = valor;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getPeca() {
        return peca;
    }

    public String getSessao() {
        return sessao;
    }

    public String getArea() {
        return area;
    }

    public int getPoltrona() {
        return poltrona;
    }

    public double getValor() {
        return valor;
    }
}

