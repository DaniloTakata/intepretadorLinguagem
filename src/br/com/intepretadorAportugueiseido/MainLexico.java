package br.com.intepretadorAportugueiseido;

import br.com.intepretadorAportugueiseido.tabelaToken.Token;

import java.util.List;

public class MainLexico {

    public static void main (String [] args) {

        try {

            AnalisadorLexico al = new AnalisadorLexico();

            List<Token> tokensEncontrado = al.analiseLexica();

            for (int i = 0; i < tokensEncontrado.size(); i++) {
                System.out.println("Token atual: " + tokensEncontrado.get(i).getImagem());
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }


}
