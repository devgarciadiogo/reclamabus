package Models;

import Data.DbContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Ocorrencias {
    private String linha;
    private String horario;
    private String categoria;
    private String detalhes;

    public void criarOcorrencia() throws SQLException{
        Connection conn = DbContext.connect();
        Scanner sc = new Scanner(System.in);
        DbContext db = new DbContext();
        Usuarios user = new Usuarios();
        db.conectarBanco();
        
        System.out.print("Insira a linha correspondente do veículo da ocorrência: ");
        setLinha(sc.next());
        linha.toUpperCase();
        
        System.out.print("""
        Insira o período da ocorrência:
        
        1) Entre 04:00 e 06:59
        2) Entre 07:00 e 10:29
        3) Entre 10:30 e 11:59
        4) Entre 12:00 e 16:59
        5) Entre 17:00 e 20:59
        6) Entre 21:00 e 03:59

        Opção escolhida: """);
        setHorario(sc.next());
        switch(getHorario()){
                case "1" -> setHorario("04:00-06:59");
                case "2" -> setHorario("07:00-10:29");
                case "3" -> setHorario("10:30-11:59");
                case "4" -> setHorario("12:00-16:59");
                case "5" -> setHorario("17:00-20:59");
                case "6" -> setHorario("21:00-03:59");
            }

        System.out.print("""
        Insira a categoria da ocorrência:

        1) Atraso
        2) Higiene
        3) Lotação
        4) Acessibilidade (Manutenção de equipamento)
        5) Acessibilidade (Falta de preparo do funcionário)
        6) Direção perigosa
        
        Opção escolhida: """);
        setCategoria(sc.next());
        switch(getCategoria()){
                case "1" -> setCategoria("Atraso");
                case "2" -> setCategoria("Higiene");
                case "3" -> setCategoria("Lotacao");
                case "4" -> setCategoria("Acessibilidade (Manutenção de equipamento)");
                case "5" -> setCategoria("Acessibilidade (Falta de preparo do funcionário)");
                case "6" -> setCategoria("Direcao perigosa");
            }
        
        System.out.println("Caso queira, dê mais detalhes sobre a situação em si (limite de 300 caracteres):");
        setDetalhes(sc.next());
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO public.usuarios(linha, horario, categoria, detalhes) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, getLinha());
            pstmt.setString(2, getHorario());
            pstmt.setString(3, getCategoria());
            pstmt.setString(4, getDetalhes());
        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }
        System.out.println("Ocorrência registrada! Insira 0 para retornar ao Menu Principal: ");
        int voltar = sc.nextInt();
        while(true){
        if(voltar != 0){
            System.out.println("Insira 0 para retornar ao Menu Principal: ");
            voltar = sc.nextInt();
        }else{
            System.out.flush();
            user.menuPrincipal();
            break;
        }
        }
        sc.close();
        db.desconectarBanco();
    }

    public void exibirOcorrencia() throws SQLException{
        Connection conn = DbContext.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT horario, linha, categoria, detalhes FROM ocorrencias");
        while (rs.next()) {
            System.out.println(rs.getString("horario") + "\t" + rs.getString("linha") + "\t" + rs.getString("categoria") + "\t" + rs.getString("detalhes"));
            }
        }
    

    private String getLinha(){
        return linha;
    }
    private void setLinha(String linha){
        this.linha = linha;
    }

    private String getHorario(){
        return horario;
    }
    private void setHorario(String horario){
        this.horario = horario;
    }

    private String getCategoria(){
        return categoria;
    }
    private void setCategoria(String categoria){
        this.categoria = categoria;
    }

    private String getDetalhes(){
        return detalhes;
    }
    private void setDetalhes(String detalhes){
        this.detalhes = detalhes;
    }
}
