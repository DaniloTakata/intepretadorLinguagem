package br.com.intepretadorAportugueiseido;


import java.util.*;

public class AnalisadorSintatico {
    private List<String> tokens;
    private int indiceTokenAtual;

    public AnalisadorSintatico(List<String> tokens) {
        this.tokens = tokens;
        this.indiceTokenAtual = 0;
    }

    public void analisarPrograma() {
        analisarDeclaracoes();
        analisarComandos();
        System.out.println("Análise de Comandos finalizada!");
    }

    private void analisarDeclaracoes() {
        while (corresponde("(", "inteiro") || corresponde("(", "real") || corresponde("(", "cadeia")) {
            analisarDeclaracao();
        }
    }

    private void analisarDeclaracao() {
        consumir("(");
        while (correspondeID()) {
            consumirID();
        }
        consumir(")");
    }

    private void analisarComandos() {
        System.out.println("Tamanho: " + tokens.size());
        System.out.println("IndiceAtual: " + indiceTokenAtual);
        while (indiceTokenAtual < tokens.size()) {
            analisarComando();
        }
    }

    private void analisarComando() {
        if (corresponde("(", "enquanto")) {
            analisarEnquanto();
        } else if (corresponde("(", "le")) {
            analisarLeitura();
        } else if (corresponde("(", "se")) {
            analisarSe();
        } else if (corresponde("(", "=")) {
            analisarAtribuicao();
        } else if (corresponde("(", "imprime")) {
            analisarImpressao();
        }
    }

    private void analisarEnquanto() {
        consumir("(", "enquanto");
        analisarExpressao();
        analisarComandos();
        consumir(")");
    }

    private void analisarLeitura() {
        consumir("(", "le");
        consumirID();
        consumir(")");
    }

    private void analisarSe() {
        consumir("(", "se");
        analisarExpressao();
        analisarComandos();
        analisarComandos();
        consumir(")");
    }

    private void analisarAtribuicao() {
        consumir("(");
        consumir("=");
        consumirID();
        analisarExpressao();
        consumir(")");
    }

    private void analisarImpressao() {
        consumir("(", "imprime");
        analisarExpressao();
        consumir(")");
    }

    private void analisarExpressao() {
        consumir("(");
        consumirOperador();
        analisarOperando();
        analisarOperando();
        consumir(")");
    }

    private void analisarOperando() {
        if (correspondeNumero() || correspondeCadeia() || correspondeID()) {
            consumirToken();
        }
    }

    private void consumir(String... tokensEsperados) {
        for (String token : tokensEsperados) {
            if (indiceTokenAtual < tokens.size() && tokens.get(indiceTokenAtual).equals(token)) {
                indiceTokenAtual++;
            }
        }
    }

    private String consumirTipo() {
        if (indiceTokenAtual < tokens.size()) {
            String token = tokens.get(indiceTokenAtual);
            indiceTokenAtual++;
            return token;
        }
        return "";
    }

    private void consumirID() {
        if (indiceTokenAtual < tokens.size() && correspondeID()) {
            consumirToken();
        }
    }

    private void consumirOperador() {
        if (indiceTokenAtual < tokens.size() && (corresponde(">", "==", "="))) {
            consumirToken();
        }
    }

    private void consumirToken() {
        if (indiceTokenAtual < tokens.size()) {
            indiceTokenAtual++;
        }
    }

    private boolean corresponde(String... tokensEsperados) {
        int indiceAtual = indiceTokenAtual;
        for (String token : tokensEsperados) {
            if (indiceAtual < tokens.size() && tokens.get(indiceAtual).equals(token)) {
                indiceAtual++;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean correspondeID() {
        return indiceTokenAtual < tokens.size() && tokens.get(indiceTokenAtual).matches("[a-zA-Z][a-zA-Z0-9]*");
    }

    private boolean correspondeNumero() {
        return indiceTokenAtual < tokens.size() && tokens.get(indiceTokenAtual).matches("[0-9]+");
    }

    private boolean correspondeCadeia() {
        return indiceTokenAtual < tokens.size() && tokens.get(indiceTokenAtual).matches("'.*'");
    }

    public static void main(String[] args) {
        List<String> tokens = Arrays.asList(
                "(", "inteiro", "a1", "b1", ")", "(", "real", "r1", ")", "(", "cadeia", "s1", "s2", ")",
                "(", "enquanto", "(", ">", "a1", "10", ")", "(", "=", "a1", "2", ")", "(", "=", "b1", "(", "+", "b1", "2", ")", ")", "(", "imprime", "b1", ")", ")",
                "(", "le", "a1", ")", "(", "se", "(", "==", "a1", "b1", ")", "(", "(", "=", "a1", "2", ")", "(", "=", "b1", "3", ")", ")", "(", "(", "=", "a1", "5", ")", "(", "=", "b1", "4", ")", ")"
        );

        AnalisadorSintatico analisador = new AnalisadorSintatico(tokens);
        analisador.analisarPrograma();
        System.out.println("Análise sintática concluída com sucesso.");
    }

}

