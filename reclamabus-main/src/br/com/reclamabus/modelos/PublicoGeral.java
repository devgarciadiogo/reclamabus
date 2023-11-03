package br.com.reclamabus.modelos;

import java.util.Scanner;
public class PublicoGeral { //SuperClasse nomeada de PublicoGeral com os atributos comuns para ademais Classes
    private String nome;
    private int idade;
    private String telefone;
    private String senha;

    public void criarConta(){ //Método CriarConta, onde será exibido ao usuário o passo a passo de como criar a sua conta
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira seu nome: ");
        setNome(sc.next()); //Definição do nome do usuario
        System.out.print("Insira sua idade: ");
        setIdade(sc.nextInt()); //Definição da idade do usuario
        System.out.print("Insira seu telefone (sem espaços): ");
        setTelefone(sc.next()); //Definição do telefone do usuario
        System.out.print("Insira sua senha (+ 6 caracteres): ");
        setSenha(sc.next()); //Definição da senha do usuario
        System.out.print("Conta criada! Insira 0 para retornar ao menu: ");
        int voltar = sc.nextInt();
        if(voltar == 0){
            exibeMenu();
        }else{
            while(voltar != 0) {
                System.out.print("Insira 0 para retornar ao menu:"); //Loop criado com objetivo da funcionalidade de retorno
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
        System.out.print("Insira 0 para retornar ao menu: ");
        int voltar = sc.nextInt();
        if(voltar == 0){
            exibeMenu();
        }else{
            while(voltar != 0) {
                System.out.print("Insira 0 para retornar ao menu: ");
                voltar = sc.nextInt();
            }
        }
    }

    public void criarOcorrencia(){

    }

    public void exibirOcorrencia(){

    }

    public void exibeMenu() { //Método exibeMenu, onde exibirá na tela do usuário as opções de funcionalidade do ReclamaBus
        Scanner sc = new Scanner(System.in);
        System.out.println(
                """
                        Bem-vindo ao Reclama Bus!
                                        
                        1) Criar Conta
                        2) Exibir Conta
                        3) Criar Ocorrência
                        4) Exibir Ocorrência
                        """);
        System.out.print("Insira o número da ação desejada (insira 0 para sair): ");
        int esc = sc.nextInt();
        switch (esc) {
            case 0:
                System.exit(0);
            case 1:
                criarConta();
                break;
            case 2:
                exibeConta();
                break;
            case 3:
                criarOcorrencia();
                break;
            case 4:
                exibirOcorrencia();
                break;
        }
        sc.close();
    }
    public String getNome(){ //getter e setter dos atributos da SuperClasse PublicoGeral (nome, idade, telefone e senha)
        return nome;
    }
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
}
