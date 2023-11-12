package Models;

import Data.DbContext;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class PublicoGeral { //SuperClasse nomeada de PublicoGeral com os atributos comuns para ademais Classes
    private String nome;
    private int idade;
    private String telefone;
    private String senha;
    private boolean idoso;
    private boolean pcd;
    private boolean funcionario;

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
            boolean status = db.executarUpdateSql("INSERT INTO public.reclamabus(nome) VALUES ('"+this.nome+"')");
            if (status){
                System.out.print("Nome registrado!");
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
                }
                break;
            }catch (Exception InputMismatchException){
                System.out.print("Por favor, insira um valor válido: ");
            }
        }
        try {
            boolean status = db.executarUpdateSql("INSERT INTO public.reclamabus(idade) VALUES ('"+this.idade+"')");
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
        //System.out.println("Nome: "+getNome());
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
        System.out.print("""
        Insira o período do dia da ocorrência:
            
        1) 
        2)  """);
        sc.close();
    }

    public void exibirOcorrencia(){

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
        ResultSet rs = db.executarQuerySql("SELECT nome FROM public.reclamabus");

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
    /*public String getNome(){
        DbContext db = new DbContext();
        ResultSet rs = db.executarQuerySql("SELECT nome FROM public.reclamabus");
        while(rs.next()){
            nome = rs.getString(nome);
        }
        db.desconectarBanco();
        return nome;
    }*/
    public void setNome(String nome){
        this.nome = nome;
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
        if(idoso == true){
            String sim = "Sim";
            return sim;
        }else{
            String nao = "Não";
            return nao;
        }
    }
    public void setIdoso(){
        this.idoso = idoso;
    }

    public String getPCD(){
        if (pcd == true){
            String sim = "Sim";
            return sim;
        }else{
            String nao = "Não";
            return nao;
        }
    }
    public void setPCD(){
        this.pcd = pcd;
    }

    public String getFuncionario(){
        if (pcd == true){
            String sim = "Sim";
            return sim;
        }else{
            String nao = "Não";
            return nao;
        }
    }
    public void setFuncionario(){
        this.funcionario = funcionario;
    }
}
