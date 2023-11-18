package Models;

import Data.DbContext;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Usuarios {
    private String nome;
    private String email;
    private int idade;
    private String telefone;
    private String senha;
    private String idoso;
    private String pcd;
    private String funcionario;

    public void iniciar(){
        Scanner sc = new Scanner(System.in);
        DbContext db = new DbContext();
        
        db.conectarBanco();

        System.out.print("""
        Bem vindo ao ReclamaBus!

        1) Criar Conta
        2) Fazer Login

        Insira a ação desejada: """);
        while(true){
            try{
            int esc = sc.nextInt();
            if(esc == 1){
                criarConta();
                break;
            }else if(esc == 2){
                login();
                break;
            }else{
                System.out.print("Insira uma ação válida: ");
                esc = sc.nextInt();
            }
            break;
            }catch(Exception InputMismatchException){
                System.out.println("Insira uma ação válida: ");
            }
        }
        sc.close();
        db.desconectarBanco();
    }
    
    //Método CriarConta, onde será exibido ao usuário o passo a passo de como criar a sua conta
    public void criarConta() throws SQLException{
        Connection conn = DbContext.connect();
        Scanner sc = new Scanner(System.in);
        DbContext db = new DbContext();
        try{
            db.conectarBanco();
        }catch(Exception e){
            System.out.println("Erro ao conectar ao banco!");
        }

        System.out.print("Insira seu nome: ");
        setNome(sc.next()); //Definição do nome do usuario

        System.out.print("Insira o seu e-mail: ");
        setEmail(sc.next());

        System.out.print("Insira sua idade: ");
        while(true){
            try {
                setIdade(sc.nextInt()); //Definição da idade do usuario
                while(idade < 0 || idade > 120){
                    System.out.print("Por favor, insira uma idade válida: ");
                    setIdade(sc.nextInt());
                    if(idade >= 60){
                        setIdoso("Sim");
                    }else{
                        setIdoso("Não");
                    }
                }
                break;
            }catch (Exception InputMismatchException){
                System.out.print("Por favor, insira um valor válido: ");
            }
        }

        System.out.print("Insira seu telefone (sem espaços): ");
        setTelefone(sc.next()); //Definição do telefone do usuario
        while(telefone.length() != 11){
            System.out.print("Insira um telefone válido: ");
            setTelefone(sc.next());
        }

        while(true){
            System.out.print("Você é PCD [S/N]? ");
            String esc = sc.next();
            esc.toUpperCase();
            if(esc.equals( "S")){
                setPCD("Sim");
                break;
            }else if(esc.equals ("N")){
                setPCD("Não");
                break;
            }else{
                System.out.println("Insira uma opção válida!");
            }
        }

        while(true){
            System.out.print("Você é funcionário de transporte público [S/N]? ");
            String esc = sc.next();
            esc.toUpperCase();
            if(esc.equals ("S")){
                setFuncionario("Sim");
                break;
            }else if(esc.equals ("N")){
                setFuncionario("Não");
                break;
            }else{
                System.out.println("Insira uma opção válida!");
            }
        }

        System.out.print("Insira sua senha (+ 6 caracteres): ");
        setSenha(sc.next()); //Definição da senha do usuario
        while(senha.length() < 6){
            System.out.print("Insira uma senha com + de 6 caracteres: ");
            setSenha(sc.next());
        }
        
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO public.usuarios(nome, email, idade, telefone, pcd, funcionario, senha) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, getNome());
            pstmt.setString(2, getEmail());
            pstmt.setInt(3, getIdade());
            pstmt.setString(4, getTelefone());
            pstmt.setString(5, getPCD());
            pstmt.setString(6, getFuncionario());
            pstmt.setString(7, getSenha());
            pstmt.executeUpdate();

        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }

        System.out.print("Conta criada! Insira 0 para retornar ao menu: ");
        int voltar = sc.nextInt();

        if(voltar == 0){
            iniciar();
        }else{
            while(voltar != 0) {
                System.out.print("Insira 0 para retornar ao menu: "); //Loop criado com objetivo da funcionalidade de retorno
                voltar = sc.nextInt();
            }
        sc.close();
        }

        }
    /* 
        public byte[] criptografarSenha(String senha){
        byte[] senhaBytes = senha.getBytes(StandardCharsets.UTF_8);
        byte[] senhaCripto = null;
        try{
            senhaCripto = MessageDigest.getInstance("SHA-256").digest(senhaBytes);
        } catch(Exception e) {
            System.out.println("Erro na criptografia!");
        }        
        return senhaCripto;
    }
    */
    public void login() throws SQLException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira seu e-mail: ");
        setEmail(sc.next());
        System.out.print("Insira sua senha: ");
        setSenha(sc.next());
        if(checkLogin(this.email, this.senha) == true){
            definirConta(this.email);
            System.out.println("Seja bem vindo/a!");
            menuPrincipal();
        }else{
            System.out.println("Credenciais incorretas. Tente novamente...");
            System.out.flush();
            login();
        }
        sc.close();
    }
    
    public boolean checkLogin(String email, String senha) throws SQLException{
        DbContext db = new DbContext();
        boolean passou = false;
        db.conectarBanco();
        
        Connection conn = DbContext.connect();
        Statement stmt1 = conn.createStatement();
        Statement stmt2 = conn.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT id FROM public.usuarios WHERE('"+email+"')");
        ResultSet rs2 = stmt2.executeQuery("SELECT id FROM public.usuarios WHERE('"+senha+"')");
        if(rs1 == rs2){
            passou = true;
        }else{
            passou = false;
        }
        db.desconectarBanco();
        return passou;
    }

    private void definirConta(String email) throws SQLException {
        Connection conn = DbContext.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nome, idade, email, telefone, funcionario, pcd FROM public.usuarios WHERE('"+senha+"')");
        this.nome = rs.getString("nome");
        this.idade = rs.getInt("idade");
        this.email = rs.getString("email");
        this.telefone = rs.getString("telefone");
        this.funcionario = rs.getString("funcionario");
        this.pcd = rs.getString("pcd");
    }

    private void exibirConta() throws SQLException{
        Scanner sc = new Scanner(System.in); 
        System.out.println("Nome: "+getNome());
        System.out.println("Idade: "+getIdade());
        System.out.println("Telefone: "+getTelefone());
        System.out.println("Idoso? "+getIdoso());
        System.out.println("PCD? "+getPCD());
        System.out.println("Funcionário? "+getFuncionario());

        System.out.print("Insira 0 para retornar ao menu: ");
        String voltar = sc.next();
        if(voltar.equals("0")){
            System.out.flush();
        }else{
            while(!voltar.equals("0")) {
                System.out.print("Insira 0 para retornar ao menu: ");
                voltar = sc.next();
            }
        }
        iniciar();
        sc.close();
    }

    public void excluirConta() throws SQLException{
        Scanner sc = new Scanner(System.in);
        DbContext db = new DbContext();
        db.conectarBanco();
        System.out.println("Tem certeza disso? Esta eh uma acao irreversivel. [S/N]");
        String esc = sc.next();
        esc.toUpperCase();
        if(esc == "S"){
            boolean status = db.executarUpdateSql("DELETE FROM public.usuarios WHERE email = ('"+this.email+"')");
            if(status){
                System.out.println("Conta excluida com sucesso! Retornando ao Menu Inicial...");
                System.out.flush();
                iniciar();
            }else{
                System.out.println("Houve um erro na exclusao da conta. Retornando ao Menu Principal");
                System.out.flush();
                menuPrincipal();
            }
        }else if(esc == "N"){
            System.out.println("Exclusao cancelada, retornando a tela inicial...");
            System.out.flush();
            menuPrincipal();
        }else{
            System.out.println("");
        }
        db.desconectarBanco();
        sc.close();
    }

    public void menuPrincipal() throws SQLException { //Método menuPrincipal, onde exibirá na tela do usuário as opções de funcionalidade do ReclamaBus
        Scanner sc = new Scanner(System.in);
        Ocorrencias oc = new Ocorrencias();
        System.out.print("""
        Bem-vindo à sua conta!
                                        
        1) Exibir Conta
        2) Excluir Conta
        3) Criar Ocorrência
        4) Exibir Ocorrência

        Insira o número da ação desejada (insira 0 para sair): """);
        String esc = sc.next();
        switch (esc) {
            case "0" -> System.exit(0);
            case "1" -> exibirConta();
            case "2" -> excluirConta();
            case "3" -> oc.criarOcorrencia();
            case "4" -> oc.exibirOcorrencia();
            default -> {
                System.out.println("Insira uma ação válida!");
                System.out.flush();
                iniciar();
            }
        }
        sc.close();
    }
    //'getter' e 'setter' dos atributos da SuperClasse PublicoGeral (nome, idade, telefone e senha)
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public int getIdade(){
        return idade;
    }
    public void setIdade(int idade){
        this.idade = idade;
    }

    public String getTelefone(){
        return telefone;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getIdoso() {
        return idoso;
    }
    public void setIdoso(String idoso){
        this.idoso = idoso;
    }

    public String getPCD(){
        return pcd;
    }
    public void setPCD(String pcd){
        this.pcd = pcd;
    }

    public String getFuncionario(){
        return funcionario;
    }
    public void setFuncionario(String funcionario){
        this.funcionario = funcionario;
    }

    public String getSenha(){
        return senha;
    }
    private void setSenha(String senha){
        this.senha = senha;
    }
}