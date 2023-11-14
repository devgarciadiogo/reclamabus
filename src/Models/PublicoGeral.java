package Models;

import Data.DbContext;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class PublicoGeral { //SuperClasse nomeada de PublicoGeral com os atributos comuns para ademais Classes
    private String nome;
    private String email;
    private int idade;
    private String telefone;
    private String senha;
    private String idoso;
    private String pcd;
    private String funcionario;

    //Método CriarConta, onde será exibido ao usuário o passo a passo de como criar a sua conta
    public void criarConta(){
        Scanner sc = new Scanner(System.in);
        DbContext db = new DbContext();
        try{
            db.conectarBanco();
        }catch(Exception e){
            System.out.println("Erro ao conectar ao banco!");
        }

        System.out.print("Insira seu nome: ");
        setNome(sc.next()); //Definição do nome do usuario
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.usuarios(nome) VALUES ('"+this.nome+"')");
            if (status){
                System.out.print("Nome registrado!");
            }
        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }

        System.out.print("Insira o seu e-mail: ");
        setEmail(sc.next());
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.usuarios(email) VALUES ('"+this.email+"')");
            if (status){
                System.out.print("E-mail registrado!");
            }
        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }

        System.out.print("Insira sua idade: ");
        while(true){
            try {
                setIdade(sc.nextInt()); //Definição da idade do usuario
                while(idade < 0 || idade > 120){
                    System.out.print("Por favor, insira uma idade válida: ");
                    setIdade(sc.nextInt());
                    if(idade >= 60){
                        this.idoso = "Sim";
                    }else{
                        this.idoso = "Não";
                    }
                }
                break;
            }catch (Exception InputMismatchException){
                System.out.print("Por favor, insira um valor válido: ");
            }
        }
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.usuarios(idade) VALUES ('"+this.idade+"')");
            if (status) {
                System.out.print("Idade registrada!");
            }
        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }

        System.out.print("Insira seu telefone (sem espaços): ");
        setTelefone(sc.next()); //Definição do telefone do usuario
        while(telefone.length() != 11){
            System.out.print("Insira uma telefone válido: ");
            setTelefone(sc.next());
        }
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.usuarios(telefone) VALUES ('"+this.telefone+"')");
            if (status) {
                System.out.print("Telefone registrada!");
            }
        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }

        while(true){
            System.out.print("Você é PCD [S/N]? ");
            String esc = sc.next();
            esc.toUpperCase();
            if(esc == "S"){
                this.pcd = "Sim";
                break;
            }else if(esc == "N"){
                this.pcd = "Não";
                break;
            }else{
                System.out.println("Insira uma opção válida!");
            }
        }
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.usuarios(pcd) VALUES ('"+this.pcd+"')");
            if (status) {
                System.out.print("Registrado!");
            }
        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }

        while(true){
            System.out.print("Você é funcionário de transporte público [S/N]? ");
            String esc = sc.next();
            esc.toUpperCase();
            if(esc == "S"){
                this.funcionario = "Sim";
                break;
            }else if(esc == "N"){
                this.funcionario = "Não";
                break;
            }else{
                System.out.println("Insira uma opção válida!");
            }
        }
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.usuarios(funcionario) VALUES ('"+this.funcionario+"')");
            if (status) {
                System.out.print("Registrado!");
            }
        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }

        System.out.print("Insira sua senha (+ 6 caracteres): ");
        setSenha(sc.next()); //Definição da senha do usuario
        while(senha.length() < 6){
            System.out.print("Insira uma senha com + de 6 caracteres: ");
        }
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.usuarios(senha) VALUES ('"+this.senha+"')");
            if (status) {
                System.out.print("Senha registrada!");
            }
        }catch (Exception e){
            System.out.println("Erro na inserção no banco de dados!");
        }

        System.out.print("Conta criada! Insira 0 para retornar ao menu: ");
        int voltar = sc.nextInt();

        if(voltar == 0){
            exibeMenu();
        }else{
            while(voltar != 0) {
                System.out.print("Insira 0 para retornar ao menu: "); //Loop criado com objetivo da funcionalidade de retorno
                voltar = sc.nextInt();
            }
        sc.close();
        }
    }

    public void exibeConta(){ //Método ExibeConta, onde exibirá na tela do usuário a sua conta cadastrada
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
        exibeMenu();
        sc.close();
    }

    public void criarOcorrencia(){
        Scanner sc = new Scanner(System.in);
        DbContext db = new DbContext();
        db.conectarBanco();
        System.out.print("Insira a linha correspondente do veículo da ocorrência: ");
        String linha = sc.next();
        linha.toUpperCase();
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.ocorrencias(linha) VALUES ('"+linha+"')");
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
                case "1" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('atraso')");
                case "2" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('higiene')");
                case "3" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('lotacao')");
                case "4" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('acessibilidade1')");
                case "5" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('acessibilidade2')");
                case "6" -> db.executarUpdateSql("INSERT INTO public.ocorrencias(categoria) VALUES ('direcaoPerigosa')");
            }
            System.out.println("Categoria registrado!");
        }catch(Exception e){
            System.out.println("Erro na inserção no banco de dados!");              
        }

        System.out.println("Caso queira, dê mais detalhes sobre a situação em si (limite de 300 caracteres):");
        String detalhes = sc.next();
        try{
        db.executarQuerySql("INSERT INTO public.ocorrencias(detalhes) VALUES ('"+detalhes+"')");
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
            menuPrincipal();
            break;
        }
        }
        sc.close();
        db.desconectarBanco();
    }

    public void exibirOcorrencia(){

    }

    public byte[] criptografarSenha(String senha){
        byte[] senhaBytes = senha.getBytes(StandardCharsets.UTF_8);
        byte[] senhaCripto;
        try{
            senhaCripto = MessageDigest.getInstance("SHA-256").dist(senhaBytes);
        } catch(NoSuchAlgorithmException e) {
            System.out.println("Erro na criptografia!");
        }        
        return senhaCripto;
    }

    public void exibeMenu(){
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
            }else if(esc == 2){
                System.out.print("Insira seu nome: ");
                nome = sc.next();
                System.out.println("Insira sua senha: ");
                senha = sc.next();
                checkLogin(nome, senha);
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

    public void checkLogin(String name, String pass){
        DbContext db = new DbContext();
        db.conectarBanco();
        System.out.print("Insira seu nome");
        
        ResultSet rs = db.executarQuerySql("SELECT nome FROM public.reclamabus(nome)");

        db.desconectarBanco();
    }

    public void menuPrincipal() { //Método exibeMenu, onde exibirá na tela do usuário as opções de funcionalidade do ReclamaBus
        Scanner sc = new Scanner(System.in);
        System.out.print("""
        Bem-vindo à sua conta!
                                        
        1) Criar Conta
        2) Exibir Conta
        3) Criar Ocorrência
        4) Exibir Ocorrência

        Insira o número da ação desejada (insira 0 para sair): """);
        String esc = sc.next();
        switch (esc) {
            case "0" -> System.exit(0);
            case "1" -> criarConta();
            case "2" -> exibeConta();
            case "3" -> criarOcorrencia();
            case "4" -> exibirOcorrencia();
            default -> {
                System.out.println("Insira uma ação válida!");
                exibeMenu();
            }
        }
        sc.close();
    }
    //'getter' e 'setter' dos atributos da SuperClasse PublicoGeral (nome, idade, telefone e senha)
    public String getNome(){
        DbContext db = new DbContext();
        ResultSet rs = db.executarQuerySql("SELECT nome FROM public.reclamabus(nome)");
        while(rs.next()){
            nome = rs.getString(nome);
        }
        db.desconectarBanco();
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

    public String getSenha(){
        return senha;
    }
    private void setSenha(String senha){
        this.senha = senha;
    }

    public String getIdoso(){
        return idoso;
    }
    public void setIdoso(String idoso){
        this.idoso = idoso;
    }

    public String getPCD(){
        return pcd;
    }
    public void setPCD(){
        this.pcd = pcd;
    }

    public String getFuncionario(){
        return funcionario;
    }
    public void setFuncionario(){
        this.funcionario = funcionario;
    }
}