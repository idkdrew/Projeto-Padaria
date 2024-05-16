/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package padrao;

import java.sql.*;

/**
 *
 * @author gabal
 */
public class CriarBanco {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String senha = "postgresql";
        String database = "projetopadaria2023";
        try {
            Connection conexao = DriverManager.getConnection(url, user, senha);
            Statement sessao = conexao.createStatement();
            String sql = "CREATE DATABASE " + database;
            sessao.executeUpdate(sql);
            System.out.println("Base de dados criada com sucesso!!");
        } catch (SQLException ex) {
            System.out.println("Erro de sql :" + ex.getMessage());
        }
    }

}
