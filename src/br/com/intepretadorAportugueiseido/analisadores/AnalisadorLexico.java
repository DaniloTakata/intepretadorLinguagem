package br.com.intepretadorAportugueiseido.analisadores;

import br.com.intepretadorAportugueiseido.models.Simbolo;
import br.com.intepretadorAportugueiseido.tabela.TabelaSimbolos;
import br.com.intepretadorAportugueiseido.tabela.TabelasEstaticas;
import br.com.intepretadorAportugueiseido.models.Token;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class AnalisadorLexico {
	
	private List<String> erros = new ArrayList<String>();
	private List<Token> tokens = new ArrayList<Token>();
	private BufferedReader reader = null;
	
	private Pattern cte_int  = Pattern.compile("\\d+");
	private Pattern cte_real  = Pattern.compile("\\d+\\.\\d+");
	private Pattern id  = Pattern.compile("\\p{Alpha}\\p{Alnum}*");

	public AnalisadorLexico(BufferedReader reader) {
		this.reader = reader;
	}
	
	public void analisar() {
		try {
			String linha = reader.readLine();
			int nLinha = 1;
			while( linha != null) {
				
				int inicioComent = linha.indexOf("'");
				if(inicioComent > -1) {
					linha = linha.substring(0, inicioComent);
				}
				StringTokenizer tokenizador = new StringTokenizer(linha);
				
				int nColuna = 1;
			
				while(tokenizador.hasMoreTokens()) {
					String lexema = tokenizador.nextToken();
					
					if (lexema.charAt(0)=='"') {
						int inicio = linha.indexOf('"');
						int fim = linha.indexOf('"', inicio+1);
						if (fim >0) {
							tokens.add(new Token(linha.substring(inicio+1, fim), "StringLiteral", nLinha, nColuna, -1));
							tokenizador = new StringTokenizer(linha.substring(fim, linha.length()));
							lexema = tokenizador.nextToken();
						} else {
							erros.add("simbolo desconhecido: "+linha.substring(inicio, linha.length()-1));
							break;
						}
					} else if (lexema.length() > 1 && lexema.charAt(0) == '/' && lexema.charAt(1) == '/'  ) {
						break;
					} else if (TabelasEstaticas.ehPalavraReservada(lexema)) {
						tokens.add(new Token(lexema, "ReservedWord", nLinha, nColuna, -1));
					} else if (TabelasEstaticas.ehDelimitador(lexema)) {
						tokens.add(new Token(lexema, "Separator", nLinha, nColuna, -1));
					} else if (TabelasEstaticas.ehOperador(lexema)) {
						tokens.add(new Token(lexema, "Operator", nLinha, nColuna, -1));
					} else if (cte_int.matcher(lexema).matches()) {
						tokens.add(new Token(lexema, "IntegerLiteral", nLinha, nColuna, -1));
					} else if (cte_real.matcher(lexema).matches()) {
						tokens.add(new Token(lexema, "FloatLiteral", nLinha, nColuna, -1));
					} else if (id.matcher(lexema).matches()) {
						Token tk = new Token(lexema, "Identifier", nLinha, nColuna, 0);
						int indice = TabelaSimbolos.contains(tk);
						if (indice < 0) {
							indice = TabelaSimbolos.addSimbolo(new Simbolo(tk));
						}
						tk.setIndiceTabSimb(indice);
						tokens.add(tk);
						
					} else {
						erros.add("Símbolo '"+lexema+"' desconhecido: linha "+nLinha+ ", coluna "+nColuna);
					}
					nColuna = nColuna + lexema.length() + 1;
				}
				linha = reader.readLine();
				nLinha++;
			}
			tokens.add(new Token("$", "EOF", -1, -1, 0));
			
		} catch (FileNotFoundException e) {
			System.err.println("ERRO 1: "+e);
		} catch(IOException io) {
			System.err.println("ERRO 2: "+io);
		}
		
	}

	public boolean temErros() {
		return !erros.isEmpty();
	}

	public void printErros() {
		System.out.println("Erros léxicos:");
		erros.forEach(e -> System.out.println(e));
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void mostraTokens() {
		System.out.println("TOKENS:");
		tokens.forEach(e -> System.out.println(e));
		System.out.println("Total de tokens: " + (tokens.size() - 1));
	}

}
