public abstract class Usuario {
    private int ID;
    private String nome;
    private String senha;
    private TipoUsuario tipoUsuario;

    public Usuario(String nome, String senha,TipoUsuario tipoUsuario) {
        this.nome = nome.toLowerCase();
        this.senha = senha.toLowerCase();
        this.tipoUsuario = tipoUsuario;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

}
