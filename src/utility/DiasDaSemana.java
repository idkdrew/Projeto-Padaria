/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package utility;

/**
 *
 * @author gabal
 */
public enum DiasDaSemana {
    SEG("Segunda-Feira"),
    TER("Terça-Feira"),
    QUA("Quarta-Feira"),
    QUI("Quinta-Feira"),
    SEX("Sexta-Feira"),
    SAB("Sábado"),
    DOM("Domingo");

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    private DiasDaSemana(String descricao) {
        this.descricao = descricao;
    }
}
