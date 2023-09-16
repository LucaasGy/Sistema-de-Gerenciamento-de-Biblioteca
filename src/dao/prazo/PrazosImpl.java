package dao.prazo;

import model.Prazos;
import model.Reserva;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar todos os prazos para realizar empréstimo de livros do sistema, e estruturar os métodos
 * necessários para inserir, consultar, alterar ou remover. Implementa a interface PrazosDAO.
 *
 * @author Lucas Gabriel.
 */


public class PrazosImpl implements PrazosDAO {

    private List<Prazos> listaPrazos;

    /**
     * Construtor que inicializa a lista de armazenamento de prazos.
     */

    public PrazosImpl() {
        this.listaPrazos = new ArrayList<Prazos>();
    }

    /**
     * Método para adicionar um objeto do tipo Prazos na lista de armazenamento.
     *
     * @param obj Prazo que deve ser armazenado
     * @return retorna objeto prazo criado
     */

    @Override
    public Prazos criar(Prazos obj) {
        this.listaPrazos.add(obj);
        return obj;
    }

    /**
     * Deleta todos os objetos do tipo Prazos do banco de dados.
     */

    @Override
    public void removerTodos() {
        this.listaPrazos.clear();
    }

    /**
     * Método que encontra todos os prazos ativos.
     *
     * @return retorna lista de prazos ativos encontrados
     */

    @Override
    public List<Prazos> encontrarTodos() {
       return this.listaPrazos;
    }

    /**
     * Método que encontra o prazo ativo de um livro.
     *
     * @param isbn isbn do livro que recebe o prazo
     * @return retorna prazo encontrado
     */

    @Override
    public Prazos encontrarPrazoDeUmLivro(double isbn) {
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLivro().getISBN()==isbn){
                return prazo;
            }
        }

        return null;
    }

    /**
     * Método que encontra todos os prazos ativos de um leitor.
     *
     * @param id identificação do leitor
     * @return retorna lista de prazos ativos de um leitor
     */

    @Override
    public List<Prazos> prazosDeUmLeitor(int id){
        List<Prazos> listaPrazosLeitor = new ArrayList<Prazos>();

        for(Prazos prazos : this.listaPrazos){
            if(prazos.getLeitor().getID()==id)
                listaPrazosLeitor.add(prazos);
        }

        return listaPrazosLeitor;
    }

    /**
     * Método de retorno do Prazos através da busca por ID.
     *
     * @param id identificação do leitor que recebe o prazo a ser encontrado
     * @return retorna objeto prazos encontrado
     */

    @Override
    public Prazos encontrarPorId(int id) {
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLeitor().getID()==id){
                return prazo;
            }
        }

        return null;
    }

    /**
     * Método que remove o prazo ativo de um livro.
     *
     * @param isbn isbn do livro que recebe o prazo a ser deletado
     */

    @Override
    public void removerPrazoDeUmLivro(double isbn) {
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLivro().getISBN()==isbn){
                this.listaPrazos.remove(prazo);
                return;
            }
        }
    }

    /**
     * Método que remove todos os prazos ativos de um leitor.
     *
     * Esta forma de remoção em sequência, evita problemas de deslocamento de índice que podem
     * ocorrer ao remover elementos de um ArrayList enquanto o percorremos.
     *
     * @param id identificação do leitor que recebe o prazo a ser deletado
     */

    @Override
    public void remover(int id) {
        List<Prazos> prazosARemover = new ArrayList<>();

        for(Prazos prazos : this.listaPrazos){
            if (prazos.getLeitor().getID()==id)
                prazosARemover.add(prazos);
        }

        this.listaPrazos.removeAll(prazosARemover);
    }

    /**
     * Método que atualiza a lista de prazos com a exclusão de prazos vencidos.
     *
     * Limpa a lista desatualizada com prazos vencidos e adiciona os prazos
     * atualizados.
     *
     * @param novosPrazos lista de prazos atualizada ( prazos vencidos excluidos )
     */

    public void atualizarPrazos(List<Prazos> novosPrazos){
        this.listaPrazos.clear();
        this.listaPrazos.addAll(novosPrazos);
    }

    @Override
    public Prazos atualizar(Prazos obj) {
        return null;
    }
}
