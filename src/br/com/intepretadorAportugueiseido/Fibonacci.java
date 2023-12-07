package br.com.intepretadorAportugueiseido;

import br.com.intepretadorAportugueiseido.analisadores.AnalisadorLexico;
import br.com.intepretadorAportugueiseido.analisadores.AnalisadorSemantico;
import br.com.intepretadorAportugueiseido.analisadores.AnalisadorSintaticoGeradorArvore;
import br.com.intepretadorAportugueiseido.interpretador.Interpretador;
import br.com.intepretadorAportugueiseido.tabela.TabelaSimbolos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Fibonacci {

    public static void main(String[] args) throws IOException {

        String pathname = "src/br/com/intepretadorAportugueiseido/arquivo/fibonacci.apt";
        BufferedReader reader = new BufferedReader(new FileReader(new File(pathname)));

        AnalisadorLexico analisadorLexico = new AnalisadorLexico(reader);

        analisadorLexico.analisar();

        if(analisadorLexico.temErros()) {
            analisadorLexico.printErros();
            return;
        }

        analisadorLexico.mostraTokens();

        AnalisadorSintaticoGeradorArvore analisadorSintatico =
                new AnalisadorSintaticoGeradorArvore(analisadorLexico.getTokens());
        analisadorSintatico.analisar();
        if(analisadorSintatico.temErros()) {
            analisadorSintatico.printErros();
            return;
        }

        System.out.println("\nMUITO BOM, ANÁLISE LÉXICA E SINTÁTICA OK!");

        analisadorSintatico.mostraArvore(analisadorSintatico.getRaiz(), "", true);

        System.out.println("\n\n");

        AnalisadorSemantico analisadorSemantico = new AnalisadorSemantico(analisadorSintatico.getRaiz());

        analisadorSemantico.analisar();

        if (analisadorSemantico.temErros()) {
            analisadorSemantico.printErros();
        }

        TabelaSimbolos.listaTabela();

        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= EXECUÇÃO DO PROGRAMA =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        Interpretador interpretador = new Interpretador();

        interpretador.interpretar(analisadorSintatico.getRaiz());

    }

}
