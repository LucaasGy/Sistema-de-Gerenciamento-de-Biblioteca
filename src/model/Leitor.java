package model;

import dao.DAO;
import erros.leitor.*;
import erros.livro.LivroLimiteDeReservas;
import erros.livro.LivroReservado;
import erros.objetos.ObjetoInvalido;

import java.time.LocalDate;

public class Leitor extends Usuario {
    private String endereco;
    private String telefone;
    private LocalDate dataMulta;
    private int limiteRenova;
    private boolean bloqueado;

    public Leitor(String nome, String endereco, String telefone, String senha) {
        super(nome,senha, TipoUsuario.Leitor);

        this.endereco = endereco.toLowerCase();
        this.telefone = telefone.toLowerCase();

        this.bloqueado = false;
        this.dataMulta = null;

        this.limiteRenova=0;
    }

    public LocalDate getDataMulta() {
        return dataMulta;
    }

    public void setDataMulta(LocalDate dataMulta) {
        this.dataMulta = dataMulta;
    }

    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getLimiteRenova() {
        return limiteRenova;
    }

    public void setLimiteRenova(int limiteRenova) {
        this.limiteRenova = limiteRenova;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void renovarEmprestimo() throws LeitorNaoPossuiEmprestimo, LeitorBloqueado, LeitorMultado, LivroReservado, LeitorLimiteDeRenovacao {
        Emprestimo novo = DAO.getEmprestimo().encontrarPorId(this.getID());

        if(novo==null)
            throw new LeitorNaoPossuiEmprestimo();

        if(novo.getLeitor().getBloqueado())
            throw new LeitorBloqueado();

        if(novo.getLeitor().getDataMulta()!=null)
            throw new LeitorMultado("LEITOR MULTADO\n A MULTA VENCERA EM: "+novo.getLeitor().getDataMulta());

        if(DAO.getReserva().livroTemReserva(novo.getLivro().getISBN()))
            throw new LivroReservado();

        if(novo.getLeitor().getLimiteRenova()==1)
            throw new LeitorLimiteDeRenovacao();

        DAO.getEmprestimo().remover(novo.getLeitor().getID());

        novo.setdataPegou(LocalDate.now());
        novo.setdataPrevista(novo.getdataPegou().plusDays(7));

        this.setLimiteRenova(1);

        DAO.getEmprestimo().criar(novo);
    }

    public void reservarLivro(double isbn) throws ObjetoInvalido, LeitorReservarLivroEmMaos, LivroLimiteDeReservas, LeitorLimiteDeReservas, LeitorBloqueado, LeitorMultado {
        if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        if(getBloqueado())
            throw new LeitorBloqueado();

        if(getDataMulta()!=null)
            throw new LeitorMultado("LEITOR MULTADO\n A MULTA VENCERA EM: "+getDataMulta());

        if(DAO.getReserva().encontrarLeitorReservouLivro(isbn).size()==4)
            throw new LivroLimiteDeReservas();

        if(DAO.getReserva().encontrarLivroReservadoPorLeitor(this.getID()).size()==3)
            throw new LeitorLimiteDeReservas();

        if(DAO.getEmprestimo().encontrarPorISBN(isbn).getLeitor().getID()==this.getID())
            throw new LeitorReservarLivroEmMaos();

        Reserva reserva = new Reserva(DAO.getLivro().encontrarPorISBN(isbn) , DAO.getLeitor().encontrarPorId(this.getID()));

        DAO.getReserva().criar(reserva);
    }

    public void retirarReserva(double isbn) throws ObjetoInvalido {
        if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        DAO.getReserva().removerUmaReserva(this.getID(),isbn);
        DAO.getPrazos().removerUmPrazo(this.getID(),isbn);
    }
}
