/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package padrao;

import utility.ArqTexto;
import java.sql.*;
import java.io.*;

/**
 *
 * @author gabal
 */
public class CriarTabelas {

    public static Statement conecta() {
        String url = "jdbc:postgresql://localhost:5432/projetopadaria2023";
        String user = "postgres";
        String senha = "postgresql";
        try {
            Connection conexao = DriverManager.getConnection(url, user, senha);
            Statement sessao = conexao.createStatement();
            System.out.print("logado na base de dados!!" + conexao.getSchema());
            return sessao;
        } catch (SQLException ex) {
            System.out.println("Erro de sql :" + ex.getMessage());
        }

        return null;
    }

    public static void main(String[] args) {
        ArqTexto arqSQL = new ArqTexto("C:/Users/gmend/OneDrive/Desktop/Padocas_-_Copia/Padocas - Copia/Padocas/src/padrao/script.sql");
        try {
            Statement sessao = CriarTabelas.conecta();
            arqSQL.abreArqLeitura();
            String sql = arqSQL.lerLinha();
            int numTab = 0;
            while (sql != null) {
                sessao.executeUpdate(sql);
                numTab++;
                sql = arqSQL.lerLinha();
            }
            System.out.println(numTab + " tabelas criadas com sucesso!");
            arqSQL.fechaArqGravacao();
            arqSQL.fechaArqLeitura();
        } catch (IOException ex) {
            System.out.println("Erro de arquivo :" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Erro de sql :" + ex);
        }
    }

}
