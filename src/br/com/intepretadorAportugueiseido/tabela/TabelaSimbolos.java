package br.com.intepretadorAportugueiseido.tabela;

import br.com.intepretadorAportugueiseido.models.Simbolo;
import br.com.intepretadorAportugueiseido.models.Token;

import java.util.ArrayList;
import java.util.List;

public class TabelaSimbolos {

	private static List<Simbolo> tabela = new ArrayList<Simbolo>();
	
	public static int addSimbolo(Simbolo simbolo) {
		tabela.add(simbolo);
		return tabela.size()-1;
	}
	
	public static void listaTabela() {
		System.out.println("\n");
		for (Simbolo simbolo : tabela) {
			System.out.println(simbolo);
		}
	}
	
	public static int contains(Token token) {
		for (int i = 0; i< tabela.size(); i++) {
			Simbolo simbolo = tabela.get(i);
			if (simbolo.getToken().getImagem().equals(token.getImagem())) {
				return i;
			}		
		}
		return -1;
	}

	public static boolean addTipoSimbolo(Token token, String tipo) {
		for (int i = 0; i< tabela.size(); i++) {
			Simbolo simbolo = tabela.get(i);
			if (simbolo.getToken().getImagem().equals(token.getImagem())) {
				simbolo.setTipo(tipo);
				return true;
			}		
		}
		return false;
	}

	public static String getTipo(Token token) {
		Simbolo simbolo = tabela.get(token.getIndiceTabSimb());
		return simbolo.getTipo();
	}

	public static void setTipo(Token token, String tipo) {
		Simbolo simbolo = tabela.get(token.getIndiceTabSimb());
		simbolo.setTipo(tipo);
	}
}
