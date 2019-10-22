package br.com.bossini.usjt_ccp3anbua_livros_rest_json_basic_activity;

public class Livro {
    private Long id; // classe empacotadora (wrapper)
    private String titulo;
    private String autor;
    private String edicao;
    private int numeroPaginas;

    public Livro(String titulo, String autor, String edicao, int numeroPaginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.edicao = edicao;
        this.numeroPaginas = numeroPaginas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
}
