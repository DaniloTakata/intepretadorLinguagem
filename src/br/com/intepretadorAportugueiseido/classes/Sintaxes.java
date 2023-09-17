package br.com.intepretadorAportugueiseido.classes;


import java.util.ArrayList;
import java.util.List;

public class Sintaxes {

    private List<String> palavrasReservadas = new ArrayList<>();
    private List<String> identificadores = new ArrayList<>();
    private List<String> delimitadoresESeparadores = new ArrayList<>();
    private List<String> operadores = new ArrayList<>();
    private List<String> constantesLiterais = new ArrayList<>();

    public Sintaxes() {
        palavrasReservadas.add("inteiro");
        palavrasReservadas.add("real");
        palavrasReservadas.add("enquanto");
        palavrasReservadas.add("imprime");
        palavrasReservadas.add("le");
        palavrasReservadas.add("se");

        delimitadoresESeparadores.add("(");
        delimitadoresESeparadores.add(")");

        operadores.add("==");
        operadores.add("=");
        operadores.add(">");
        operadores.add("<");
        operadores.add("+");
        operadores.add("!");

        constantesLiterais.add("0");
        constantesLiterais.add("1");
        constantesLiterais.add("2");
        constantesLiterais.add("3");
        constantesLiterais.add("4");
        constantesLiterais.add("5");
        constantesLiterais.add("6");
        constantesLiterais.add("7");
        constantesLiterais.add("8");
        constantesLiterais.add("9");

    }

    public boolean fazParteSintaxe(String caracter) {

        return palavrasReservadas.contains(caracter) || delimitadoresESeparadores.contains(caracter)
                || operadores.contains(caracter);

    }

    public boolean ehPalavraReservada(String caracter) {
        return palavrasReservadas.contains(caracter);
    }

    public boolean ehDelimitadoresESeparadoresa(String caracter) {
        return delimitadoresESeparadores.contains(caracter);
    }

    public boolean ehOperadores(String caracter) {
        return operadores.contains(caracter);
    }

    public boolean ehConstantesLiterais(String caracter) { return constantesLiterais.contains(caracter); }

}
