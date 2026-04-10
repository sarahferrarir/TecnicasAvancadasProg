package cleancode.model;

public class Cliente {

    private int id;
    private String nome;
    private String email;
    private TipoCliente tipo;

    public Cliente(int id, String nome, TipoCliente tipo) {

        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.email = nome.replace(" ", "").toLowerCase() + "@email.com";

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

}