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

    public static void main(String [] args) {

//        String caminhoArquivo = "E:\\FACULDADE\\8 Período\\Teoria da Computação & Compiladores\\A3" +
//                "\\Exemplo01_20230907150340.apt";
        String caminhoArquivo = "E:\\FACULDADE\\8 Período\\Teoria da Computação & Compiladores\\A3\\interpretador-POCPOC" +
                "\\interpretadorPoc\\src\\br\\com\\intepretadorAportugueiseido\\arquivos\\Exemplo01_20230907150340.apt";
        Sintaxes sintaxes = new Sintaxes();
        List<Token> tokens = new ArrayList<>();
        List<Simbolo> simbolos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))){

            String linha;
            int contador = 1;
            int id = 1;
            int id_ts = 1;

            while ((linha = leitor.readLine()) != null) {
                linha = linha.replaceAll("'.*", "");

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
                                            elementoAtual, "Delimitador/Separador", 0));
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

                    System.out.println(linha + "   ----------> " + contador);
                    contador ++;
                }
            }

            for (Token token : tokens) {
                System.out.println(token.toString());
            }

            System.out.println("\n\n");

            for (Simbolo simbolo: simbolos) {
                System.out.println(simbolo.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
