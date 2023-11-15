package br.com.intepretadorAportugueiseido.tabela;

import java.util.Arrays;
import java.util.List;

public class TabelasEstaticas {

	private static List<String> palavrasReservadas = 
			Arrays.asList("inteiro", "real", "cadeia", "se", "enquanto", "le", "imprime");
	private static List<String> operadores = 
			Arrays.asList(".","+", "-", "*", "/", "=", "==", "&&", "||", ">", "<", ">=", "<=", "!=");
	private static List<String> delimitadores = Arrays.asList("(", ")");
	
	
	public static boolean ehPalavraReservada(String lexema) {
		return palavrasReservadas.contains(lexema);
	}


	public static boolean ehDelimitador(String lexema) {
		return delimitadores.contains(lexema);
	}


	public static boolean ehOperador(String lexema) {
		return operadores.contains(lexema);
	}
	
}
