package br.com.intepretadorAportugueiseido;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LeitorArquivo {

    public BufferedReader leitorArquivo(/*String caminhoArquivo*/) throws FileNotFoundException {
        String caminhoArquivo = "E:\\FACULDADE\\8 Período\\Teoria da Computação & Compiladores\\A3\\interpretador-POCPOC" +
                "\\interpretadorPoc\\src\\br\\com\\intepretadorAportugueiseido\\arquivos\\Exemplo01_20230907150340.apt";
        return new BufferedReader(new FileReader(caminhoArquivo));
    }

}
