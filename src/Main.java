import br.com.intepretadorAportugueiseido.AnalisadorLexico;
import br.com.intepretadorAportugueiseido.tabelaToken.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        AnalisadorLexico analisadorLexico = new AnalisadorLexico();
        List<Token> tokens = analisadorLexico.analiseLexica();
        AnalisadorSintaticoV2 prototipoAnalisadorSintatico = new AnalisadorSintaticoV2(tokens);

        prototipoAnalisadorSintatico.parse();
    }
}