package br.com.intepretadorAportugueiseido.tabelaToken;

public class Token {

    private int id;
    private int linha;
    private int coluna;
    private String nome;
    private String classe;

    private int id_ts;

    public Token() {}

    public Token(int id, int linha, int coluna, String nome, String classe, int id_ts) {
        this.id = id;
        this.linha = linha;
        this.coluna = coluna;
        this.nome = nome;
        this.classe = classe;
        this.id_ts = id_ts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public int getId_ts() {
        return id_ts;
    }

    public void setId_ts(int id_ts) {
        this.id_ts = id_ts;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", linha=" + linha +
                ", coluna=" + coluna +
                ", nome='" + nome + '\'' +
                ", classe='" + classe + '\'' +
                ", id_ts=" + id_ts +
                '}';
    }
}
