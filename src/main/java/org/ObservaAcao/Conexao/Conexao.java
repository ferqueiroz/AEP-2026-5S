package org.ObservaAcao.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static String URL = "jdbc:mysql://localhost:3306/observacao";
    private static String USUARIO = "root";
    private static String SENHA = "masterkey";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

}
