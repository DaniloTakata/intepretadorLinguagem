package br.com.intepretadorAportugueiseido.analisadores;

import br.com.intepretadorAportugueiseido.models.No;
import br.com.intepretadorAportugueiseido.models.TipoNo;
import br.com.intepretadorAportugueiseido.models.Token;
import br.com.intepretadorAportugueiseido.tabela.TabelaSimbolos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.com.intepretadorAportugueiseido.models.TipoNo.token;

@SuppressWarnings("unchecked")
public class AnalisadorSemantico {

    private No raiz = null;
    private List<String> erros = new ArrayList<>();

    public AnalisadorSemantico(No raiz) {
        this.raiz = raiz;
    }

    public void analisar() {
        analisar(raiz);
    }

    private Object analisar(No no) {
        return switch (no.getTipo()) {
            case comandos -> comandos(no);
            case comando -> comando(no);
            case comando_interno -> comandoIntern(no);
            case decl -> decl(no);
			/*
			case escrita: {
				return escrita(no);
			}
			case leitura: {
				return leitura(no);
			}*/
//			case atrib: {
//				return atrib(no);
//			}
			/*case laco: {
				return laco(no);
			}
			case cond: {
				return cond(no);
			}*/
            case tipo -> tipo(no);
            case ids -> ids(no);
            case ids2 -> ids2(no);
            default -> null;
        };

    }

    /**
     * <comandos> ::= <comando> <comandos> |
     */
    private Object comandos(No no) {
        if(no.getFilhos().size() == 2) {
            analisar(no.getFilho(0));
            analisar(no.getFilho(1));
        }
        return null;
    }

    /**
     * <comando> ::= '(' <comando_interno> ')'
     */
    private Object comando(No no) {
        analisar(no.getFilho(1));
        return null;
    }

    /**
     * <comando_interno> ::= <decl> | <escrita> | <leitura> | <atrib> | <laco> | <cond>
     */
    public Object comandoIntern(No no) {
        return analisar(no.getFilho(0));
    }

    /**
     * <decl> ::= <tipo> <ids>
     */
    public Object decl(No no) {
        String tipo = (String) analisar(no.getFilho(0));

        if ((no.getFilhos()).size() > 2) {

            List<Token> ids = (List<Token>) analisar(no.getFilho(1));
            Collections.reverse(ids);
            for(Token id: ids) {
                String tipoId = TabelaSimbolos.getTipo(id);
                if(tipoId != null) {
                    erros.add("Token redeclarado: " + id);
                } else {
                    TabelaSimbolos.setTipo(id, tipo);
                    System.out.println("--> " + tipo);
                }
            }

        }

        return null;
    }

    private No atrib() {
        No no = new No(TipoNo.atrib);
        Token tokenOperand = (no.getFilho(0)).getToken();
        Token tokenIdentificador = no.getFilho(1).getToken();

        if (tokenOperand.getImagem().equals("=")) {

            if (tokenOperand.getClasse().equals("Identifier")) {
                String nomeIdentificador = tokenIdentificador.getImagem();
                int posicaoTokenListaSimbolos = TabelaSimbolos.contains(tokenIdentificador);

                if (TabelaSimbolos.contains(tokenIdentificador) != -1) {


                } else {
                    erros.add(tokenIdentificador + " não foi declarada no código!");
                }

                // Verifica se a variável foi declarada
                //verificaVariavelDeclarada(nomeVariavel);

                // Verifica se os tipos são compatíveis
                //verificaTipoAtribuicao(nomeVariavel, no.getFilhos().get(2).getTipo());
            } else {
                erros.add("Esperado um identificador, token: " + token);
            }
        } else {
            erros.add("Esperado '=', token: " + token);
        }
        return no;
    }

    /**
     * <tipo> ::= 'inteiro' | 'real' | 'cadeia' | 'logico'
     */
    public Object tipo(No no) {
        return no.getFilho(0).getToken().getImagem();
    }

    /**
     * <ids> ::= id <ids2>
     */
    public Object ids(No no) {
        if ((no.getFilhos()).isEmpty()) {
            return "";
        }

        List<Token> ids2 = (List<Token>) analisar(no.getFilho(1));
        ids2.add(no.getFilho(0).getToken());
        return ids2;
    }

    /**
     * <ids2> ::= id <ids2> |
     */
    public Object ids2(No no) {
        if(no.getFilhos().isEmpty()) {
            return new ArrayList<Token>();
        } else {
            List<Token> ids2 = (List<Token>) analisar(no.getFilho(1));
            ids2.add(no.getFilho(0).getToken());
            return ids2;
        }
    }

    public boolean temErros() {
        return !erros.isEmpty();
    }

    public void printErros() {
        erros.forEach(erro -> System.out.println(erro));
    }

}
