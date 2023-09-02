package model;

import dao.DAO;
import erros.leitor.LeitorBloqueado;
import erros.leitor.LeitorMultado;
import erros.leitor.LeitorNaoPossuiEmprestimo;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.livro.LivroReservado;
import erros.livro.PrazoVencido;
import erros.objetos.ObjetoInvalido;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Bibliotecario extends Usuario {

    public Bibliotecario(String nome, String senha){
        super(nome, senha, TipoUsuario.Bibliotecario);
    }

    public void registrarLivro(Livro livro){
        DAO.getLivro().criar(livro);
    }

    public void fazerEmprestimo(int id, double isbn) throws ObjetoInvalido, LeitorBloqueado, LeitorMultado, LeitorTemEmprestimo, LivroEmprestado, LivroReservado, PrazoVencido {
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);
        Livro livro = DAO.getLivro().encontrarPorISBN(isbn);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        if(livro==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        if(leitor.getBloqueado())
            throw new LeitorBloqueado();

        if(leitor.getDataMulta()!=null)
            throw new LeitorMultado("LEITOR MULTADO\n A MULTA VENCERA EM: "+leitor.getDataMulta());

        if(DAO.getEmprestimo().encontrarPorId(id)!=null)
            throw new LeitorTemEmprestimo();

        if(!livro.getDisponivel())
            throw new LivroEmprestado();

        if(DAO.getReserva().livroTemReserva(isbn)){
            if(DAO.getReserva().top1Reserva(isbn).getLeitor().getID()!=id)
                throw new LivroReservado();

            if (DAO.getPrazos().encontrarUmPrazo(id, isbn).getDataLimite().isBefore(LocalDate.now())){
                DAO.getPrazos().removerUmPrazo(id, isbn);
                DAO.getReserva().removeTop1(isbn);
                throw new PrazoVencido();
            }

            DAO.getPrazos().removerUmPrazo(id, isbn);
            DAO.getReserva().removeTop1(isbn);
        }

        Emprestimo novo = new Emprestimo(livro,leitor);

        DAO.getEmprestimo().criar(novo);
    }

    public void devolverLivro(int id) throws ObjetoInvalido, LeitorNaoPossuiEmprestimo {
        if(DAO.getLeitor().encontrarPorId(id)==null){
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");
        }

        Emprestimo opera = DAO.getEmprestimo().encontrarPorId(id);

        if(opera==null)
            throw new LeitorNaoPossuiEmprestimo();

        LocalDate devolve = LocalDate.now();

        long dias = ChronoUnit.DAYS.between(opera.getdataPrevista(),devolve);

        if(dias>0) {

            opera.getLeitor().setDataMulta(devolve.plusDays(dias*2));

            DAO.getReserva().remover(id);
            DAO.getPrazos().removerPrazosDeUmLeitor(id);
        }

        //caso o dao.livro tenha dao.reserva, ao ser devolvido, eu pego o top 1 da fila e crio um dao.prazo para ele
        //fazer o dao.emprestimo do dao.livro e adiciono na lista de prazos cotendo de quem é o dao.prazo, o dao.livro do dao.prazo
        //e a data limite que ele tem pra fazer o dao.emprestimo
        //Ao tentar fazer o dao.emprestimo, verifico se ta dentro do dao.prazo
        //Se tiver, faco o dao.emprestimo, removo o dao.prazo e removo a dao.reserva dele
        //Se nao tiver, nao faco o dao.emprestimo, removo o dao.prazo e removo a dao.reserva dele
        if(DAO.getReserva().livroTemReserva(opera.getLivro().getISBN())){
            Reserva opera2 = DAO.getReserva().top1Reserva(opera.getLivro().getISBN());
            Prazos novo = new Prazos(opera2.getLeitor(),opera2.getLivro(),devolve.plusDays(2));

            DAO.getPrazos().criar(novo);
        }

        opera.getLivro().setDisponivel(true);
        opera.getLeitor().setLimiteRenova(0);

        DAO.getEmprestimo().remover(id);
    }
}
