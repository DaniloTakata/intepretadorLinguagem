package br.com.intepretadorAportugueiseido;

import br.com.intepretadorAportugueiseido.classes.Sintaxes;
import br.com.intepretadorAportugueiseido.tabelaSimbolo.Simbolo;
import br.com.intepretadorAportugueiseido.tabelaToken.Token;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalisadorLexico {

    public List<Token> analiseLexica() {

        String caminhoArquivo = "/Users/alexsandermartins/intepretadorLinguagem/src/br/com/intepretadorAportugueiseido/arquivos/Exemplo01_20230907150340.apt";
        Sintaxes sintaxes = new Sintaxes();
        List<Token> tokens = new ArrayList<>();
        List<Simbolo> simbolos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))){

            String linha;
            int contador = 1;
            int id = 1;
            int id_ts = 1;

            while ((linha = leitor.readLine()) != null) {
                linha = (linha.replaceAll("'.*", "")).replaceAll("\t", "");

                if (!linha.trim().startsWith("'") && !linha.equals("")) {

                    int indiceElementoAtual = 0;
                    String elementoAtual = "";

                    for (int i = 0; i < linha.length(); i++) {

                        if (!String.valueOf(linha.charAt(i)).equals(" ")) {
                            elementoAtual += linha.charAt(i);
                        } else {


                            if (sintaxes.fazParteSintaxe(elementoAtual)) {

                                if (sintaxes.ehOperadores(elementoAtual)) {
                                    indiceElementoAtual += 2;
                                    tokens.add(new Token(id, contador, indiceElementoAtual,
                                            elementoAtual, "Operador", 0));
                                    id++;
                                } else if (sintaxes.ehDelimitadoresESeparadoresa(elementoAtual)) {
                                    indiceElementoAtual += 2;
                                    tokens.add(new Token(id, contador, indiceElementoAtual,
                                            elementoAtual, "Delimitador", 0));
                                    id++;
                                } else if (sintaxes.ehPalavraReservada(elementoAtual)) {
                                    indiceElementoAtual += 2;
                                    tokens.add(new Token(id, contador, indiceElementoAtual,
                                            elementoAtual, "Palavra Reservada", 0));
                                    id++;
                                } else if (sintaxes.ehConstantesLiterais(elementoAtual)) {
                                    indiceElementoAtual += 2;
                                    tokens.add(new Token(id, contador, indiceElementoAtual,
                                            elementoAtual, "Constante Literal", 0));
                                    id++;
                                }

                            } else {

                                simbolos.add(new Simbolo(id_ts, elementoAtual));
                                id_ts++;

                                indiceElementoAtual += 2;
                                tokens.add(new Token(id, contador, indiceElementoAtual,
                                        elementoAtual, "Identificador", id_ts));
                                id++;
                            }


                            elementoAtual = "";
                        }
                    }

                    contador ++;
                }
            }




            List<String> tokensStringForm = new ArrayList<>();

            for (Token tokenAtual: tokens) {
                tokensStringForm.add(tokenAtual.getImagem());

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    return tokens;
    }
}
