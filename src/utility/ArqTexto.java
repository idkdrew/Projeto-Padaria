/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabal
 */
public class ArqTexto implements Serializable {

    private String nomeArq;
    private BufferedReader arqLeitura;
    private BufferedWriter arqGravacao;

    public ArqTexto(String nomeArq) {
        this.nomeArq = nomeArq;
    }

    public void abreArqLeitura() throws IOException {
        try {
            arqLeitura = new BufferedReader(new FileReader(nomeArq));
        } catch (IOException e) {
            throw new IOException("Arquivo " + this.nomeArq + " não encontrado\n" + e.getMessage());
        }
    }

    public void abreArqGravacao() throws IOException {
        try {
            arqGravacao = new BufferedWriter(new FileWriter(nomeArq));
        } catch (IOException e) {
            throw new IOException("Erro na gravação do arquivo " + this.nomeArq + "\n" + e.getMessage());
        }
    }

    public void fechaArqLeitura() throws IOException {
        try {
            if (arqLeitura != null) {
                arqLeitura.close();
            }
        } catch (IOException e) {
            throw new IOException("Erro no fechamento " + this.nomeArq + "\n" + e.getMessage());
        }
    }

    public void fechaArqGravacao() throws IOException {
        try {
            if (arqGravacao != null) {
                arqGravacao.close();
            }
        } catch (IOException e) {
            throw new IOException("Erro no fechamento " + this.nomeArq + "\n" + e.getMessage());
        }
    }

    public String lerLinha() throws FileNotFoundException, IOException {
        try {
            return arqLeitura.readLine();
        } catch (IOException e) {
            throw new IOException("Erro na leitura " + this.nomeArq + "\n" + e.getMessage());
        }
    }

    public void gravarLinha(String linha) throws FileNotFoundException, IOException {
        try {
            while (linha.length() > 0) {
                if (linha.length() > 10) {
                    arqGravacao.write(linha.substring(0, 9) + "\n");
                    linha = linha.substring(9);
                } else {
                    arqGravacao.write(linha);
                    linha = "";
                }
            }

        } catch (IOException e) {
            throw new IOException("Erro na gravação " + this.nomeArq + "\n" + e.getMessage());
        }
    }

    /**
     * @return the nomeArq
     */
    public String getNomeArq() {
        return nomeArq;
    }

    /**
     * @param nomeArq the nomeArq to set
     */
    public void setNomeArq(String nomeArq) {
        this.nomeArq = nomeArq;
    }

    /**
     * @return the arqLeitura
     */
    public BufferedReader getArqLeitura() {
        return arqLeitura;
    }

    /**
     * @param arqLeitura the arqLeitura to set
     */
    public void setArqLeitura(BufferedReader arqLeitura) {
        this.arqLeitura = arqLeitura;
    }

    /**
     * @return the arqSaida
     */
    public BufferedWriter getArqGravacao() {
        return arqGravacao;
    }

    /**
     * @param arqSaida the arqSaida to set
     */
    public void setArqGravacao(BufferedWriter arqGravacao) {
        this.arqGravacao = arqGravacao;
    }
}
