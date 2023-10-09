package model;

import dao.DAO;

import erros.objetos.ObjetoInvalido;
import erros.objetos.UsuarioSenhaIncorreta;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Sistema que representa operações que o sistema deve fazer,
 * não dependendo de uma classe em específico.
 * Contém métodos para: verificação de login, aplicação automática de multas,
 * entre outros.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class Sistema {

    /**
     * Método que verifica login de um Administrador.
     *
     * Verifica de o ID informado consta no sistema e caso conste,
     * verifica se a senha informada coincide com a armazenada.
     * Caso tudo esteja correto, retorna o objeto Adm encontrado.
     *
     * @param id identificação do administrador
     * @param senha senha do administrador
     * @return retorna objeto adm
     * @throws ObjetoInvalido caso não seja encontrado o administrador com o id informado,
     * retorna uma exceção informando o ocorrido.
     * @throws UsuarioSenhaIncorreta caso a senha informada não coincida com a senha armazenada
     * no sistema, retorna uma exceção informando o ocorrido.
     */

    public static Adm checarLoginADM(int id, String senha) throws ObjetoInvalido, UsuarioSenhaIncorreta {
        Adm adm = DAO.getAdm().encontrarPorId(id);

        if(adm==null)
            throw new ObjetoInvalido("ADMINISTRADOR NÃO ENCONTRADO");

        else if (!adm.getSenha().equals(senha.toLowerCase()))
                throw new UsuarioSenhaIncorreta();

        return adm;
    }

    /**
     * Método que verifica login de um Bibliotecario.
     *
     * Verifica de o ID informado consta no sistema e caso conste,
     * verifica se a senha informada coincide com a armazenada.
     * Caso tudo esteja correto, retorna o objeto Bibliotecario encontrado.
     *
     * @param id identificação do bibliotecario
     * @param senha senha do bibliotecario
     * @return retorna objeto bibliotecario
     * @throws ObjetoInvalido caso não seja encontrado o bibliotecario com o id informado,
     * retorna uma exceção informando o ocorrido.
     * @throws UsuarioSenhaIncorreta caso a senha informada não coincida com a senha armazenada
     * no sistema, retorna uma exceção informando o ocorrido.
     */

    public static Bibliotecario checarLoginBibliotecario(int id, String senha) throws ObjetoInvalido, UsuarioSenhaIncorreta {
        Bibliotecario bibliotecario = DAO.getBibliotecario().encontrarPorId(id);

        if(bibliotecario==null)
            throw new ObjetoInvalido("BIBLIOTECARIO NÃO ENCONTRADO");

        else if(!bibliotecario.getSenha().equals(senha.toLowerCase()))
                throw new UsuarioSenhaIncorreta();

        return bibliotecario;
    }

    /**
     * Método que verifica login de um Leitor.
     *
     * Verifica de o ID informado consta no sistema e caso conste,
     * verifica se a senha informada coincide com a armazenada.
     * Caso tudo esteja correto, retorna o objeto Leitor encontrado.
     *
     * @param id identificação do leitor
     * @param senha senha do leitor
     * @return retorna objeto leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido.
     * @throws UsuarioSenhaIncorreta caso a senha informada não coincida com a senha armazenada
     * no sistema, retorna uma exceção informando o ocorrido.
     */

    public static Leitor checarLoginLeitor(int id, String senha) throws ObjetoInvalido, UsuarioSenhaIncorreta {
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NÃO ENCONTRADO");

        else if(!leitor.getSenha().equals(senha.toLowerCase()))
            throw new UsuarioSenhaIncorreta();

        return leitor;
    }

    /**
     * Método que verifica se o Leitor recebe uma multa.
     *
     * Ao devolver o livro, sistema verifica se houve atraso na devolução,
     * e caso houver, aplica o dobro da quantidade de dias de atraso, remove
     * todas as reservas e prazos do leitor e caso necessário, cria um novo prazo
     * para o novo top1 da fila de reserva do livro que o leitor possuia prazo, ir
     * realizar o empréstimo.
     *
     * @param emprestimo empréstimo do leitor
     */

    public static void aplicarMulta(Emprestimo emprestimo){
        LocalDate dataDevolvendo = LocalDate.now();

        long diasDeAtraso = ChronoUnit.DAYS.between(emprestimo.getdataPrevista(),dataDevolvendo);

        if(diasDeAtraso>0) {
            Leitor altera = DAO.getLeitor().encontrarPorId(emprestimo.getLeitor());
            altera.setDataMulta(dataDevolvendo.plusDays(diasDeAtraso*2));
            DAO.getLeitor().atualizar(altera);

            adicionarPrazoParaTop2reserva(emprestimo.getLeitor());
        }
    }

    /**
     * Método utilizado para verificar se a multa de um leitor já expirou.
     *
     * Este método será utilizado na fase 3, quando o projeto possuir uma "main",
     * onde a cada vez que o programa for aberto, ele irá verificar se caso um leitor
     * possua multa e essa multa já tiver sido expirada, a multa é retirada.
     */

    public static void verificarMultasLeitores(){
        for(Leitor leitor : DAO.getLeitor().encontrarTodos()){
            if(leitor.getDataMulta()!=null && leitor.getDataMulta().isBefore(LocalDate.now())) {
                leitor.setDataMulta(null);
                DAO.getLeitor().atualizar(leitor);
            }
        }
    }

    /**
     * Método utilizado para verificar se os prazos para realizar empréstimo de livros
     * já expiraram.
     *
     * Esta forma de remoção em sequência, evita problemas de deslocamento de índice que podem
     * ocorrer ao remover elementos de um ArrayList enquanto o percorremos.
     *
     * Ao final, envia as listas de prazos para remover e adicionar para o DAO.
     */

    public static void verificarPrazosEReservas(){
        List<Prazos> prazosARemover = new ArrayList<Prazos>();
        List<Prazos> prazosAAdicionar = new ArrayList<Prazos>();

        for(Prazos prazo : DAO.getPrazos().encontrarTodos()){
            if(prazo.getDataLimite().isBefore(LocalDate.now())){
                DAO.getReserva().removeTop1(prazo.getLivro());

                if(DAO.getReserva().livroTemReserva(prazo.getLivro())){
                    Reserva reserva1 = DAO.getReserva().top1Reserva(prazo.getLivro());
                    Prazos prazotop1 = new Prazos(reserva1.getLeitor(),reserva1.getLivro());
                    prazosAAdicionar.add(prazotop1);
                }

                prazosARemover.add(prazo);
            }
        }

        DAO.getPrazos().atualizarPrazos(prazosARemover, prazosAAdicionar);
    }


    /**
     * Método que verifica se um leitor possui prazos.
     *
     * Primeiramente é removido todas as reservas ativas do leitor.
     *
     * Caso o leitor possua prazos, é verificado se o livro do prazo do leitor
     * possui alguma reserva, pois se possuir é necessário criar um prazo
     * para o top1 da fila ir realizar o emprestimo do livro em questão.
     *
     * @param id identificação do leitor
     */

    public static void adicionarPrazoParaTop2reserva(int id){
        DAO.getReserva().remover(id);

        List<Prazos> listaPrazos = DAO.getPrazos().prazosDeUmLeitor(id);

        if(listaPrazos!=null){
            for(Prazos prazo : listaPrazos){
                Reserva reservaTop1 = DAO.getReserva().top1Reserva(prazo.getLivro());
                if(reservaTop1!=null){
                    Prazos novoPrazoTop1 = new Prazos(reservaTop1.getLeitor(), reservaTop1.getLivro());
                    DAO.getPrazos().criar(novoPrazoTop1);
                }
            }

            DAO.getPrazos().remover(id);
        }
    }
}