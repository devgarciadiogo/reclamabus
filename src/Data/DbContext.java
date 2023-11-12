package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbContext {

    private String url = "jdbc:postgresql://localhost:5432/reclamabus";
    private String usuario = "postgres";
    private String senha = "artesanato2015";

    public Connection connection = null;

    public DbContext() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // MÉTODO RESPONSAVEL POR REALIZAR CONEXÃO COM O BANCO DE DADOS
    public void conectarBanco() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.usuario, this.senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MÉTODO RESPONSAVEL POR DESCONECTAR DO BANCO DE DADOS
    public void desconectarBanco() {
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MÉTODO RESPONSAVEL POR REALIZAR UMA QUERY QUE NECESSITE DE RETORNO (SELECT)
    public ResultSet executarQuerySql(String query) {
        try {
            Statement stmt = this.connection.createStatement();

            ResultSet resultSet = stmt.executeQuery(query);
            return resultSet;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // MÉTODO RESPONSAVEL POR REALIZAR UMA QUERY QUE NÃO NECESSITA DE RETORNO (INSERT/UPDATE/DELETE)
    public boolean executarUpdateSql(String query) {
        try {
            Statement stmt = this.connection.createStatement();

            stmt.executeUpdate(query);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}