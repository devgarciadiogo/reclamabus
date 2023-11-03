package br.com.reclamabus.principal;

import java.sql.ResultSet;
import br.com.reclamabus.Data.DbContext;
import br.com.reclamabus.modelos.*;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PublicoGeral pg = new PublicoGeral();
        pg.exibeMenu();
    }
}
