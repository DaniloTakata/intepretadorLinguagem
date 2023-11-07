import br.com.intepretadorAportugueiseido.tabelaToken.Token;

import java.util.List;

public class AnalisadorSintaticoV2 {
    private List<Token> tokens;
    private int index;

    public AnalisadorSintaticoV2(List<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }

    public void parse() {
        comandos();
        if (index < tokens.size()) {
            throw new RuntimeException("Erro de sintaxe: tokens não consumidos completamente");
        }
    }

    private Token getNextToken() {
        if (index < tokens.size()) {
            return tokens.get(index++);
        }
        return null;
    }

    private Token currentToken() {
        if (index < tokens.size()) {
            return tokens.get(index);
        }
        return null;
    }

    private void comandos() {
        comandosAux();
    }

    private void comandosAux() {
        Token token = currentToken();
        if (token != null) {
            comando();
            comandosAux();
        }
    }

    private void comando() {
        Token token = getNextToken();
        if (token != null && token.getImagem().equals("(")) {
            comandoInterno();
            token = getNextToken();
            if (token == null || !token.getImagem().equals(")")) {
                throw new RuntimeException("Erro de sintaxe: Esperava ')'");
            }
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava '('");
        }
    }

    private void comandoInterno() {
        Token token = currentToken();
        if (token != null && imagemETipoDeDado(token)) {
            decl();
        } else if (token != null && token.getImagem().equals("imprime")) {
            escrita();
        } else if (token != null && token.getImagem().equals("le")) {
            leitura();
        } else if (token != null && token.getImagem().equals("=")) {
            atrib();
        } else if (token != null && token.getImagem().equals("enquanto")) {
            laco();
        } else if (token != null && token.getImagem().equals("se")) {
            cond();
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava 'inteiro', 'real', 'cadeia' ou 'logico'");
        }
    }

    private void decl() {
        tipo();
        ids();
    }

    private void tipo() {
        Token token = getNextToken();
        if (token != null && imagemETipoDeDado(token)) {
            // Tipo válido
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava 'inteiro', 'real', 'cadeia' ou 'logico'");
        }
    }

    private void ids() {
        Token token = getNextToken();
        if (token != null && token.getClasse().equals("Identificador")) {
            ids2();
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava um identificador");
        }
    }

    private void ids2() {
        Token token = getNextToken();
        if (token != null && token.getClasse().equals("Identificador")) {
            ids2();
        }
    }

    private void escrita() {
        Token token = getNextToken();
        if (token != null && token.getImagem().equals("imprime")) {
            imprim();
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava 'imprime'");
        }
    }

    private void imprim() {
        Token token = getNextToken();
        if (token != null && (token.getClasse().equals("Identificador") || token.getClasse().equals("StringLiteral"))) {
            // Identificador ou StringLiteral válido
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava um identificador ou stringLiteral");
        }
    }

    private void leitura() {
        Token token = getNextToken();
        if (token != null && token.getImagem().equals("le")) {
            token = getNextToken();
            if (token != null && token.getClasse().equals("Identificador")) {
                // Identificador válido
            } else {
                throw new RuntimeException("Erro de sintaxe: Esperava um identificador após 'le'");
            }
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava 'le'");
        }
    }

    private void atrib() {
        Token token = getNextToken();
        if (token != null && token.getImagem().equals("=")) {
            token = getNextToken();
            if (token != null && token.getClasse().equals("Identificador")) {
                exprArit();
            } else {
                throw new RuntimeException("Erro de sintaxe: Esperava um identificador após '='");
            }
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava '='");
        }
    }

    private void exprArit() {
        Token token = currentToken();
        if (token != null && classeETipoDeDado(token)) {
            operan();
        } else if (token != null && token.getImagem().equals("(")) {
            getNextToken();
            opArit();
            exprArit();
            exprArit();
            token = getNextToken();
            if (token != null && token.getImagem().equals(")")) {
                // Fecha parênteses
            } else {
                throw new RuntimeException("Erro de sintaxe: Esperava ')'");
            }
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava um identificador, string, inteiro, real ou logico");
        }
    }
    private boolean classeETipoDeDado(Token token) {
        return true;
        /*
        if (token != null) {
            String imagem = token.getImagem();
            return imagem.equals("inteiro") || imagem.equals("real") || imagem.equals("cadeia") || imagem.equals("logico") ||
                    token.getClasse().equals("IntLiteral") || token.getClasse().equals("RealLiteral") ||
                    token.getClasse().equals("StringLiteral") || token.getClasse().equals("LogicoLiteral");
        }
        return false;*/
    }

    private void opArit() {
        Token token = getNextToken();
        if (token != null && (token.getImagem().equals("+") || token.getImagem().equals("-") || token.getImagem().equals("*") || token.getImagem().equals("/") || token.getImagem().equals("."))) {
            // Operador aritmético válido
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava '+', '-', '*', '/', ou '.'");
        }
    }

    private boolean imagemETipoDeDado(Token token) {
        if (token != null) {
            String imagem = token.getImagem();
            return imagem.equals("inteiro") || imagem.equals("real") || imagem.equals("cadeia") || imagem.equals("logico");
        }
        return false;
    }

    private void operan() {
        Token token = getNextToken();
        if (token != null && (token.getClasse().equals("Identificador") || token.getClasse().equals("StringLiteral") || token.getClasse().equals("IntLiteral") || token.getClasse().equals("RealLiteral") || token.getClasse().equals("LogicoLiteral"))) {
            // Token válido
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava um identificador, string, inteiro, real ou logico");
        }
    }

    private void laco() {
        Token token = getNextToken();
        if (token != null && token.getImagem().equals("enquanto")) {
            token = getNextToken();
            if (token != null && token.getImagem().equals("(")) {
                exprRel();
                token = getNextToken();
                if (token != null && token.getImagem().equals(")")) {
                    comando();
                    comandos();
                } else {
                    throw new RuntimeException("Erro de sintaxe: Esperava ')'");
                }
            } else {
                throw new RuntimeException("Erro de sintaxe: Esperava '('");
            }
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava 'enquanto'");
        }
    }

    private void exprRel() {
        Token token = currentToken();
        if (token != null && classeETipoDeDado(token)) {
            operan();
        } else if (token != null && opRel(token)) {
            getNextToken();
            exprRel();
            exprRel();
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava um operando ou operador relacional");
        }
    }

    private boolean opRel(Token token) {
        if (token != null) {
            String imagem = token.getImagem();
            return imagem.equals(">") || imagem.equals(">=") || imagem.equals("<") || imagem.equals("<=") || imagem.equals("==") || imagem.equals("!=");
        }
        return false;
    }

    private void cond() {
        Token token = getNextToken();
        if (token != null && token.getImagem().equals("se")) {
            token = getNextToken();
            if (token != null && token.getImagem().equals("(")) {
                exprRel();
                token = getNextToken();
                if (token != null && token.getImagem().equals(")")) {
                    condTrue();
                    condFalse();
                } else {
                    throw new RuntimeException("Erro de sintaxe: Esperava ')'");
                }
            } else {
                throw new RuntimeException("Erro de sintaxe: Esperava '('");
            }
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava 'se'");
        }
    }

    private void condTrue() {
        Token token = getNextToken();
        if (token != null && token.getImagem().equals("(")) {
            comando();
            comandos();
            token = getNextToken();
            if (token != null && token.getImagem().equals(")")) {
                // Fecha parênteses
            } else {
                throw new RuntimeException("Erro de sintaxe: Esperava ')'");
            }
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava '('");
        }
    }

    private void condFalse() {
        Token token = getNextToken();
        if (token != null && token.getImagem().equals("(")) {
            comando();
            comandos();
            token = getNextToken();
            if (token != null && token.getImagem().equals(")")) {
                // Fecha parênteses
            } else {
                throw new RuntimeException("Erro de sintaxe: Esperava ')'");
            }
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperava '('");
        }
    }
}
