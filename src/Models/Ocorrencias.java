package Models;

import Data.DbContext;

import java.sql.Connection;
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
        Scanner sc = new Scanner(System.in);
        DbContext db = new DbContext();
        Usuarios user = new Usuarios();
        db.conectarBanco();
        
        System.out.print("Insira a linha correspondente do veículo da ocorrência: ");
        setLinha(sc.next());
        linha.toUpperCase();
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.ocorrencias(linha) VALUES ('"+getLinha()+"')");
            if (status) {
                System.out.print("Linha registrada!");
            }
        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }
        System.out.print("""
        Insira o período da ocorrência:
        
        1) Entre 04:00 e 06:59
        2) Entre 07:00 e 10:29
        3) Entre 10:30 e 11:59
        4) Entre 12:00 e 16:59
        5) Entre 17:00 e 20:59
        6) Entre 21:00 e 03:59

        Opção escolhida: """);
        String esc = sc.next();
        try { 
            switch(esc){
                case "1" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(horario) VALUES ('04:00-06:59')");
                case "2" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(horario) VALUES ('07:00-10:29')");
                case "3" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(horario) VALUES ('10:30-11:59')");
                case "4" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(horario) VALUES ('12:00-16:59')");
                case "5" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(horario) VALUES ('17:00-20:59')");
                case "6" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(horario) VALUES ('21:00-03:59')");
            }
            System.out.println("Horário registrado!");
        }catch(Exception e){
            System.out.println("Erro na inserção no banco de dados!");              
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
        esc = sc.next();
        try { 
            switch(esc){
                case "1" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('Atraso')");
                case "2" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('Higiene')");
                case "3" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('Lotaco')");
                case "4" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('Acessibilidade (Manutenção de equipamento)')");
                case "5" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('Acessibilidade (Falta de preparo do funcionário)')");
                case "6" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES (Direcao perigosa')");
            }
            System.out.println("Categoria registrada!");
        }catch(Exception e){
            System.out.println("Erro na inserção no banco de dados!");              
        }

        System.out.println("Caso queira, dê mais detalhes sobre a situação em si (limite de 300 caracteres):");
        setDetalhes(sc.next());
        try{
        db.executarQuerySql("INSERT INTO public.ocorrencias(detalhes) VALUES ('"+getDetalhes()+"')");
        }catch(Exception e){
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
    

    public String getLinha(){
        return linha;
    }
    public void setLinha(String linha){
        this.linha = linha;
    }

    public String getHorario(){
        return horario;
    }
    public void setHorario(String horario){
        this.horario = horario;
    }

    public String getCategoria(){
        return categoria;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getDetalhes(){
        return detalhes;
    }
    public void setDetalhes(String detalhes){
        this.detalhes = detalhes;
    }
}
