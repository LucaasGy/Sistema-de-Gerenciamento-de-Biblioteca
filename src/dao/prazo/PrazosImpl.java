package dao.prazo;

import model.Prazos;

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
     * É utilizado um for padrão invés de um for each, pois como
     * se trata de sequências de remoções em um loop, o for each
     * acaba dando erro pois o tamanho da lista vai alterando durante
     * as remoções. Utilizando um for padrão, este erro não ocorre.
     *
     * @param id identificação do leitor que recebe o prazo a ser deletado
     */

    @Override
    public void remover(int id) {
        for(int i = 0; i < this.listaPrazos.size(); i++){
            if (this.listaPrazos.get(i).getLeitor().getID()==id)
                this.listaPrazos.remove(i);
        }
    }

    @Override
    public Prazos atualizar(Prazos obj) {
        return null;
    }
}
