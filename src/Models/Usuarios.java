package Models;

import Data.DbContext;

//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                String esc = sc.next();
                if(esc.equals("1")){
                    criarConta();
                    break;
                }else if(esc.equals("2")){
                    login();
                    break;
                }else{
                    System.out.print("Insira uma ação válida: ");
                    esc = sc.next();
                }
                break;
            }catch(Exception e){
                e.printStackTrace();
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
                while(getIdade() < 0 || getIdade() > 120){
                    System.out.print("Por favor, insira uma idade válida: ");
                    setIdade(sc.nextInt());
                }
                if(getIdade() >= 60){
                    setIdoso("Sim");
                }else{
                    setIdoso("Não");
                }
                break;
            }catch (Exception InputMismatchException){
                System.out.print("Por favor, insira um valor válido: ");
            }
        }

        System.out.print("Insira seu telefone (sem espaços): ");
        setTelefone(sc.next()); //Definição do telefone do usuario
        while(telefone.length() != 11){
            System.out.print("Por favor, insira um telefone válido: ");
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
                System.out.print("Por favor, insira uma opção válida: ");
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
                System.out.println("Por favor, insira uma opção válida!");
            }
        }

        System.out.print("Insira sua senha (+ 6 caracteres): ");
        setSenha(sc.next()); //Definição da senha do usuario
        while(senha.length() < 6){
            System.out.print("Insira uma senha com + de 6 caracteres: ");
            setSenha(sc.next());
        }
        
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO public.usuarios(nome, email, idade, telefone, senha, idoso, pcd, funcionario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, getNome());
            pstmt.setString(2, getEmail());
            pstmt.setInt(3, getIdade());
            pstmt.setString(4, getTelefone());
            pstmt.setString(5, getSenha());
            pstmt.setString(6, getIdoso());
            pstmt.setString(7, getPCD());
            pstmt.setString(8, getFuncionario());
            pstmt.executeUpdate();

        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
            e.printStackTrace();
        }


        System.out.print("Conta criada! Insira 0 para retornar ao menu: ");
        int voltar = sc.nextInt();

        if(voltar == 0){
            menuPrincipal();
        }else{
            while(voltar != 0) {
                System.out.print("Insira 0 para retornar ao menu: "); //Loop criado com objetivo da funcionalidade de retorno
                voltar = sc.nextInt();
            }
        sc.close();
        }

        }
    
    public void login() throws SQLException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira seu e-mail: ");
        setEmail(sc.next());
        System.out.print("Insira sua senha: ");
        setSenha(sc.next());
        if(checkLogin(getEmail(), getSenha())){
            definirConta(getEmail());
            System.out.flush();
            System.out.println("Seja bem vindo/a!");
            menuPrincipal();
        }else{
            System.out.println("Credenciais incorretas. Tente novamente...");
            System.out.flush();
            iniciar();
        }
        sc.close();
    }
    
    public boolean checkLogin(String email, String senha) throws SQLException{
        DbContext db = new DbContext();
        boolean passou = false;
        db.conectarBanco();
        ResultSet rs1 = db.executarQuerySql("SELECT id_usuarios FROM public.usuarios WHERE('"+email+"')");
        ResultSet rs2 = db.executarQuerySql("SELECT id_usuarios FROM public.usuarios WHERE('"+senha+"')");
        if(rs1 == rs2){
            passou = true;
        }else{
            passou = false;
        }
        db.desconectarBanco();
        return passou;
    }

    private void definirConta(String email) throws SQLException {
        DbContext db = new DbContext();

        ResultSet rs = db.executarQuerySql("SELECT nome, idade, email, telefone, idoso, pcd, funcionario FROM public.usuarios WHERE('"+email+"')");

        setNome(rs.getString("nome"));
        setIdade(rs.getInt("idade"));
        setEmail(rs.getString("email"));
        setTelefone(rs.getString("telefone"));
        setIdoso(rs.getString("idoso"));
        setPCD(rs.getString("pcd"));
        setFuncionario(rs.getString("funcionario"));
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
                System.out.print("Por favor, insira 0 para retornar ao menu: ");
                voltar = sc.next();
            }
        }
        menuPrincipal();
        sc.close();
    }

    public void excluirConta() throws SQLException{
        Scanner sc = new Scanner(System.in);
        DbContext db = new DbContext();
        db.conectarBanco();
        System.out.println("Tem certeza disso? Esta eh uma acao irreversivel. [S/N]");
        String esc = sc.next();
        esc.toUpperCase();
        while(true){
            if(esc.equals("S")){
                boolean status = db.executarUpdateSql("DELETE FROM public.usuarios WHERE email = ('"+getEmail()+"')");
                if(status){
                    System.out.println("Conta excluida com sucesso! Retornando ao Menu Inicial...");
                    System.out.flush();
                    iniciar();
                    break;
                }else{
                    System.out.println("Houve um erro na exclusao da conta. Retornando ao Menu Principal...");
                    System.out.flush();
                    menuPrincipal();
                    break;
                }
            }else if(esc.equals("N")){
                System.out.println("Exclusao cancelada, retornando ao Menu Principal...");
                System.out.flush();
                menuPrincipal();
                break;
            }else{
                System.out.print("Por favor, insira uma escolha valida: ");
                esc = sc.next();
                esc.toUpperCase();
            }
        }
        db.desconectarBanco();
        sc.close();
    }

    public void atualizarConta() throws SQLException{
        Scanner sc = new Scanner(System.in);
        DbContext db = new DbContext();
        db.conectarBanco();
        int esc = 0;
        System.out.print("""
        Insira o dado a ser atualizado:
        
        1) Nome
        2) E-mail
        3) Idade
        4) Telefone
        5) Senha
        
        Insira a opção desejada:  """);
        while(esc != 1 || esc != 2 || esc != 3 || esc != 4 || esc != 5){
            try{
            esc = sc.nextInt();
            }catch(Exception InputMismatchException){
                System.out.println("Insira uma opção valida!");
            }
                switch(esc){
                    case 1:
                        System.out.print("Insira o novo nome: ");
                        setNome(sc.next());
                        try{
                        boolean status = db.executarUpdateSql("UPDATE public.usuarios SET nome = '"+getNome()+"' WHERE email = '"+getEmail()+"'");
                        if(status){
                            System.out.println("Nome atualizado com sucesso! Retornando ao Menu Principal...");
                            menuPrincipal();
                        }
                        }catch(Exception e){
                            System.out.println("Erro na inserção no banco de dados!");
                            e.printStackTrace();
                        }
                    case 2:
                        System.out.print("Insira o novo E-mail: ");
                        setEmail(sc.next());
                        try{
                        boolean status = db.executarUpdateSql("UPDATE public.usuarios SET email = '"+getEmail()+"' WHERE telefone = '"+getTelefone()+"'");
                        if(status){
                            System.out.println("E-mail atualizado com sucesso! Retornando ao Menu Principal...");
                            menuPrincipal();
                        }
                        }catch(Exception e){
                            System.out.println("Erro na inserção no banco de dados!");
                            e.printStackTrace();
                        }
                    case 3:
                        System.out.print("Insira a nova idade: ");
                        setIdade(sc.nextInt());
                        try{
                        boolean status = db.executarUpdateSql("UPDATE public.usuarios SET idade = '"+getIdade()+"' WHERE email = '"+getEmail()+"'");
                        if(status){
                            System.out.println("Nome atualizado com sucesso! Retornando ao Menu Principal...");
                            menuPrincipal();
                        }
                        }catch(Exception e){
                            System.out.println("Erro na inserção no banco de dados!");
                            e.printStackTrace();
                        }
                    case 4:
                        System.out.print("Insira o novo telefone: ");
                        setTelefone(sc.next());
                        try{
                        boolean status = db.executarUpdateSql("UPDATE public.usuarios SET telefone = '"+getTelefone()+"' WHERE email = '"+getEmail()+"'");
                        if(status){
                            System.out.println("Nome atualizado com sucesso! Retornando ao Menu Principal...");
                            menuPrincipal();
                        }
                        }catch(Exception e){
                            System.out.println("Erro na inserção no banco de dados!");
                            e.printStackTrace();
                        }
                    case 5:
                        System.out.print("Insira a nova senha: ");
                        setSenha(sc.next());
                        try{
                        boolean status = db.executarUpdateSql("UPDATE public.usuarios SET senha = '"+getSenha()+"' WHERE email = '"+getEmail()+"'");
                        if(status){
                            System.out.println("Nome atualizado com sucesso! Retornando ao Menu Principal...");
                            menuPrincipal();
                        }
                        }catch(Exception e){
                            System.out.println("Erro na inserção no banco de dados!");
                            e.printStackTrace();
                        }
            }  
        }
    }

    public void menuPrincipal() throws SQLException { //Método menuPrincipal, onde exibirá na tela do usuário as opções de funcionalidade do ReclamaBus
        Scanner sc = new Scanner(System.in);
        Ocorrencias oc = new Ocorrencias();
        System.out.print("""
        Bem-vindo à sua conta!
                                        
        1) Exibir Conta
        2) Atualizar Conta
        3) Excluir Conta
        4) Criar Ocorrência
        5) Exibir Ocorrência

        Insira o número da ação desejada (insira 0 para sair): """);
        String esc = sc.next();
        switch (esc) {
            case "0" -> System.exit(0);
            case "1" -> exibirConta();
            case "2" -> atualizarConta();
            case "3" -> excluirConta();
            case "4" -> oc.criarOcorrencia();
            case "5" -> oc.exibirOcorrencia();
            default -> {
                System.out.println("Por favor, insira uma ação válida!");
                System.out.flush();
                iniciar();
            }
        }
        sc.close();
    }
    //'getter' e 'setter' dos atributos da SuperClasse PublicoGeral (nome, idade, telefone e senha)
    private String getNome(){
        return nome;
    }
    private void setNome(String nome){
        this.nome = nome;
    }

    private String getEmail(){
        return email;
    }
    private void setEmail(String email){
        this.email = email;
    }

    private int getIdade(){
        return idade;
    }
    private void setIdade(int idade){
        this.idade = idade;
    }

    private String getTelefone(){
        return telefone;
    }
    private void setTelefone(String telefone){
        this.telefone = telefone;
    }

    private String getIdoso() {
        return idoso;
    }
    private void setIdoso(String idoso){
        this.idoso = idoso;
    }

    private String getPCD(){
        return pcd;
    }
    private void setPCD(String pcd){
        this.pcd = pcd;
    }

    private String getFuncionario(){
        return funcionario;
    }
    private void setFuncionario(String funcionario){
        this.funcionario = funcionario;
    }

    private String getSenha(){
        return senha;
    }
    private void setSenha(String senha){
        this.senha = senha;
    }
}