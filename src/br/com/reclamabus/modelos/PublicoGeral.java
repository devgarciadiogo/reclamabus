package br.com.reclamabus.modelos;

public class PublicoGeral {
    private String nome;
    private int idade;
    private String telefone;
    private String senha;


    public void exibeConta(){
        System.out.println("Nome: "+nome);
        System.out.println("Idade: "+idade);
        System.out.println("Telefone: "+telefone);
    }
    public void criarOcorrencia(){

    }

    public String getNome(){
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
    public void setSenha(String senha){
        this.senha = senha;
    }
}
