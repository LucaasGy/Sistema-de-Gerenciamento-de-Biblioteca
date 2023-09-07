package model;

import dao.DAO;
import erros.objetos.ObjetoInvalido;
import erros.objetos.UsuarioSenhaIncorreta;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Classe Sistema que representa operações que o sistema deve fazer,
 * não dependendo de uma classe em específico.
 * Contém métodos para: verificação de login, aplicação automática de multas,
 * entre outros.
 *
 * @author Lucas Gabriel.
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

        else if (!adm.getSenha().equals(senha))
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

        else if(!bibliotecario.getSenha().equals(senha))
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

        else if(!leitor.getSenha().equals(senha))
            throw new UsuarioSenhaIncorreta();

        return leitor;
    }

    /**
     * Método que verifica se o Leitor recebe alguma multa.
     *
     * Ao devolver o livro, sistema verifica se houve atraso na devolução,
     * e caso houver, aplica o dobro da quantidade de dias de atraso e remove
     * todas as reservas e prazos do leitor.
     *
     * @param emprestimo empréstimo do leitor
     */

    public static void checarMulta(Emprestimo emprestimo){
        LocalDate devolve = LocalDate.now();

        long dias = ChronoUnit.DAYS.between(emprestimo.getdataPrevista(),devolve);

        if(dias>0) {
            emprestimo.getLeitor().setDataMulta(devolve.plusDays(dias*2));

            DAO.getReserva().remover(emprestimo.getLeitor().getID());
            DAO.getPrazos().remover(emprestimo.getLeitor().getID());
        }
    }

    /**
     * Método utilizado para verificar se a multa de um leitor já expirou.
     *
     * Este método será utilizado na fase 3, quando o projeto possuir uma "main",
     * onde a cada vez que o programa for aberto, ele irá verificar se caso um leitor
     * possuir multa e essa multa já tiver sido expirada, a multa é retirada.
     */

    public static void verificarMultasLeitores(){
        for(Leitor leitor : DAO.getLeitor().encontrarTodos()){
            if(leitor.getDataMulta()!=null) {
                if (leitor.getDataMulta().isBefore(LocalDate.now()))
                    leitor.setDataMulta(null);
            }
        }
    }

    /**
     * Método utilizado para verificar se os prazos para realizar empréstimo de livros
     * já expirou.
     *
     * Este método será utilizado na fase 3, quando o projeto possuir uma "main",
     * onde a cada vez que o programa for aberto, ele irá verificar quais de todos os prazos
     * existentes já foram expirados. Caso tenham sido, remove o top1 da fila de reserva do livro
     * em questão e remove também seu prazo. Caso após a remoção, o livro ainda possua reservas,
     * é criado um prazo para o antigo top2 da fila ir realizar seu empréstimo.
     */

    public static void verificarPrazosEReservas(){
        List<Prazos> todosPrazos = DAO.getPrazos().encontrarTodos();

        for(int i=0; i<todosPrazos.size(); i++){
            if(todosPrazos.get(i).getDataLimite().isBefore(LocalDate.now())){
                DAO.getReserva().removeTop1(todosPrazos.get(i).getLivro().getISBN());

                if(DAO.getReserva().livroTemReserva(todosPrazos.get(i).getLivro().getISBN())){
                    Reserva reserva1 = DAO.getReserva().top1Reserva(todosPrazos.get(i).getLivro().getISBN());
                    Prazos prazotop1 = new Prazos(reserva1.getLeitor(),reserva1.getLivro(),LocalDate.now().plusDays(2));
                    DAO.getPrazos().criar(prazotop1);
                }

                DAO.getPrazos().removerPrazoDeUmLivro(todosPrazos.get(i).getLivro().getISBN());
            }
        }
    }
}

