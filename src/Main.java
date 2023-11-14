import br.com.intepretadorAportugueiseido.AnalisadorLexico;
import br.com.intepretadorAportugueiseido.tabelaToken.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {

            AnalisadorLexico analisadorLexico = new AnalisadorLexico();
            List<Token> tokens = analisadorLexico.analiseLexica();
            for (Token token : tokens) {
                System.out.println("--> " + token);
            }
            AnalisadorSintaticoV2 prototipoAnalisadorSintatico = new AnalisadorSintaticoV2(tokens);

            prototipoAnalisadorSintatico.parse();

        } catch (Exception e) {
            System.out.println("** " + e.getMessage());
        }
    }
}