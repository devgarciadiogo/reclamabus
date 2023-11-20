package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbContext {

    private static final String url = "jdbc:postgresql://localhost:5432/reclamabus";
    private static final String usuario = "postgres";
    private static final String senha = "artesanato2015";

    public Connection conn = null;

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(url, usuario, senha);
    }

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
            this.conn = DriverManager.getConnection(this.url, this.usuario, this.senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MÉTODO RESPONSAVEL POR DESCONECTAR DO BANCO DE DADOS
    public void desconectarBanco() {
        try {
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MÉTODO RESPONSAVEL POR REALIZAR UMA QUERY QUE NECESSITE DE RETORNO (SELECT)
    public ResultSet executarQuerySql(String query) {
        try {
            Statement stmt = this.conn.createStatement();

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
            Statement stmt = this.conn.createStatement();

            stmt.executeUpdate(query);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}