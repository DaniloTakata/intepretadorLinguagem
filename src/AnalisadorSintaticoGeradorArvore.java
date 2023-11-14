import br.com.intepretadorAportugueiseido.No;
import br.com.intepretadorAportugueiseido.TipoNo;
import br.com.intepretadorAportugueiseido.tabelaToken.Token;

import java.util.ArrayList;
import java.util.List;

public class AnalisadorSintaticoGeradorArvore {
    private List<Token> tokens = null;
    private List<String> erros = new ArrayList<String>();
    private Token token = null;
    private int pToken = 0;
    private No raiz = null;

    public AnalisadorSintaticoGeradorArvore(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void analisar() {
        leToken();
        raiz = comandos();
        if(!token.getClasse().equals("EOF")) {
            erros.add("Esperado EOF, token: " + token);
        }
    }

    /**
     * <comandos> ::= <comando> <comandos> |
     */
    private No comandos() {
        No no = new No(TipoNo.comandos);
        if(token.getImagem().equals("(")) {
            no.addFilho(comando());
            no.addFilho(comandos());
        }
        return no;
    }

    /**
     * <comando> ::= '(' <comando_interno> ')'
     */
    private No comando() {
        No no = new No(TipoNo.comando);
        if(token.getImagem().equals("(")) {
            no.addFilho(new No(token));
            leToken();
            no.addFilho(comandoInterno());
            if(token.getImagem().equals(")")) {
                no.addFilho(new No(token));
                leToken();
            } else {
                erros.add("Esperado ')', token: " + token);
            }
        } else {
            erros.add("Esperado '(', token: " + token);
        }
        return no;
    }

    /**
     * <comando_interno> ::= <decl> | <escrita> | <leitura> | <atrib> | <laco> | <cond>
     *
     * @return
     */
    private No comandoInterno() {
        if(token.getImagem().equals("inteiro")
                || token.getImagem().equals("real")
                || token.getImagem().equals("cadeia")
                || token.getImagem().equals("logico")) {
            decl();
        } else if(token.getImagem().equals("imprime")) {
            escrita();
        } else if(token.getImagem().equals("le")) {
            leitura();
        } else if(token.getImagem().equals("=")) {
            atrib();
        } else if(token.getImagem().equals("enquanto")) {
            laco();
        } else if(token.getImagem().equals("se")) {
            cond();
        } else {
            erros.add("Esperado 'inteiro' ou 'real' ou 'cadeia' ou 'logico' ou 'imprime' ou 'le' ou '=' ou 'enquanto' ou 'se', token: "+ token);
        }
        return null;
    }

    /**
     * <decl> ::= <tipo> <ids>
     */
    private void decl() {
        tipo();
        ids();
    }

    /**
     * <tipo> ::= 'inteiro' | 'real' | 'cadeia' | 'logico'
     */
    private void tipo() {
        if(token.getImagem().equals("inteiro")
                || token.getImagem().equals("real")
                || token.getImagem().equals("cadeia")
                || token.getImagem().equals("logico")) {
            leToken();
        } else {
            erros.add("Esperado 'inteiro' ou 'real' ou 'cadeia' ou 'logico', token: "+ token);
        }
    }

    /**
     * <ids> ::= id <ids2>
     */
    private void ids() {
        if(token.getClasse().equals("Identifier")) {
            leToken();
            ids2();
        } else {
            erros.add("Esperado um identificador, token: "+ token);
        }
    }

    /**
     * <ids2> ::= id <ids2> |
     */
    private void ids2() {
        if(token.getClasse().equals("Identifier")) {
            leToken();
            ids2();
        }
    }

    /**
     * <escrita> ::= 'imprime' <imprim>
     */
    private void escrita() {
        if(token.getImagem().equals("imprime")) {
            leToken();
            imprim();
        } else {
            erros.add("Esperado 'imprime', token: "+ token);
        }
    }

    /**
     * <imprim> ::= id | stringLiteral
     */
    private void imprim() {
        if(token.getClasse().equals("Identifier")
                || token.getClasse().equals("StringLiteral")) {
            leToken();
        } else {
            erros.add("Esperado um identificador ou string literal, token: "+ token);
        }
    }

    /**
     * <leitura> ::= 'le' id
     */
    private void leitura() {
        if(token.getImagem().equals("le")) {
            leToken();
            if(token.getClasse().equals("Identifier")) {
                leToken();
            } else {
                erros.add("Esperado um identificador, token: "+ token);
            }
        } else {
            erros.add("Esperado 'le', token: "+ token);
        }
    }

    /**
     * <atrib> 	::= '=' id <expr_arit>
     */
    private void atrib() {
        if(token.getImagem().equals("=")) {
            leToken();
            if(token.getClasse().equals("Identifier")) {
                leToken();
            } else {
                erros.add("Esperado um identificador, token: "+ token);
            }
            exprArit();
        } else {
            erros.add("Esperado '=', token: "+ token);
        }
    }

    /**
     * <expr_arit> ::= <operan> | '(' <op_arit> <expr_arit> <expr_arit> ')'
     */
    private void exprArit() {
        if(token.getClasse().equals("Identifier")
                || token.getClasse().equals("StringLiteral")
                || token.getClasse().equals("IntegerLiteral")
                || token.getClasse().equals("FloatLiteral")
                || token.getClasse().equals("LogicLiteral")) {
            operan();
        } else if(token.getImagem().equals("(")) {
            leToken();
            opArit();
            exprArit();
            exprArit();
            if(token.getImagem().equals(")")) {
                leToken();
            } else {
                erros.add("Esperado ')', token: "+ token);
            }
        } else {
            erros.add("Esperado um '(' ou identificador ou uma string literal ou um inteiro literal ou um real literal ou um logico literal, token: "+ token);
        }
    }

    /**
     * <op_arit> ::= '+' | '-' | '*' | '/' | '.'
     */
    private void opArit() {
        if(token.getImagem().equals("+")
                || token.getImagem().equals("-")
                || token.getImagem().equals("*")
                || token.getImagem().equals("/")
                || token.getImagem().equals(".")) {
            leToken();
        } else {
            erros.add("Esperado '+' ou '-' ou '*' ou '/' ou '.', token: "+ token);
        }
    }

    /**
     * <operan> ::= id | stringLiteral | intLiteral | realLiteral | logicoLiteral
     */
    private void operan() {
        if(token.getClasse().equals("Identifier")
                || token.getClasse().equals("StringLiteral")
                || token.getClasse().equals("IntegerLiteral")
                || token.getClasse().equals("FloatLiteral")
                || token.getClasse().equals("LogicLiteral")) {
            leToken();
        } else {
            erros.add("Esperado um identificador ou uma string literal ou um inteiro literal ou um real literal ou um logico literal, token: "+ token);
        }
    }

    /**
     * <laco> ::= 'enquanto' '(' <expr_rel> ')' <comando> <comandos>
     */
    private void laco() {
        if(token.getImagem().equals("enquanto")) {
            leToken();
            if(token.getImagem().equals("(")) {
                leToken();
                exprRel();
                if(token.getImagem().equals(")")) {
                    leToken();
                    comando();
                    comandos();
                } else {
                    erros.add("Esperado ')', token: "+ token);
                }
            } else {
                erros.add("Esperado '(', token: "+ token);
            }
        } else {
            erros.add("Esperado 'enquanto', token: "+ token);
        }
    }

    /**
     * <expr_rel> ::= <operan> | <op_rel> <expr_rel> <expr_rel>
     */
    private void exprRel() {
        if(token.getClasse().equals("Identifier")
                || token.getClasse().equals("StringLiteral")
                || token.getClasse().equals("IntegerLiteral")
                || token.getClasse().equals("FloatLiteral")
                || token.getClasse().equals("LogicLiteral")) {
            operan();
        } else if(token.getImagem().equals(">")
                || token.getImagem().equals(">=")
                || token.getImagem().equals("<")
                || token.getImagem().equals("<=")
                || token.getImagem().equals("==")
                || token.getImagem().equals("!=")) {
            opRel();
            exprRel();
            exprRel();
        } else {
            erros.add("Esperado um identificador ou uma string literal ou um inteiro literal ou um real literal ou um logico literal "
                    + "ou '>' ou '>=' ou '<' ou '<=' ou '==' ou '!=', token: "+ token);
        }
    }

    /**
     * <op_rel> ::= '>' | '>=' | '<' | '<=' | '==' | '!='
     */
    private void opRel() {
        if(token.getImagem().equals(">")
                || token.getImagem().equals(">=")
                || token.getImagem().equals("<")
                || token.getImagem().equals("<=")
                || token.getImagem().equals("==")
                || token.getImagem().equals("!=")) {
            leToken();
        } else {
            erros.add("Esperado '>' ou '>=' ou '<' ou '<=' ou '==' ou '!=', token: "+ token);
        }
    }

    /**
     * <cond> ::= 'se' '(' <expr_rel> ')' <cond_true> <cond_false>
     */
    private void cond() {
        if(token.getImagem().equals("se")) {
            leToken();
            if(token.getImagem().equals("(")) {
                leToken();
                exprRel();
                if(token.getImagem().equals(")")) {
                    leToken();
                    condTrue();
                    condFalse();
                } else {
                    erros.add("Esperado ')', token: "+ token);
                }
            } else {
                erros.add("Esperado '(', token: "+ token);
            }
        } else {
            erros.add("Esperado 'se', token: "+ token);
        }
    }

    /**
     * <cond_true> 	::= '(' <comando> <comandos> ')'
     */
    private void condTrue() {
        if(token.getImagem().equals("(")) {
            leToken();
            comando();
            comandos();
            if(token.getImagem().equals(")")) {
                leToken();
            } else {
                erros.add("Esperado ')', token: "+ token);
            }
        } else {
            erros.add("Esperado '(', token: "+ token);
        }
    }

    /**
     * <cond_false> ::= '(' <comando> <comandos> ')' |
     */
    private void condFalse() {
        if(token.getImagem().equals("(")) {
            leToken();
            comando();
            comandos();
            if(token.getImagem().equals(")")) {
                leToken();
            } else {
                erros.add("Esperado ')', token: "+ token);
            }
        }
    }

    private Token leToken() {
        token = tokens.get(pToken);
        pToken++;
        return token;
    }

    public boolean temErros() {
        return !erros.isEmpty();
    }

    public void printErros() {
        System.out.println("Erros sintáticos:");
        erros.forEach(e -> System.out.println(e));
    }

    public No getRaiz() {
        return raiz;
    }
}
