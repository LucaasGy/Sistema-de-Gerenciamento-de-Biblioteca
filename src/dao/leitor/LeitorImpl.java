package dao.leitor;

import model.Leitor;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar todos os leitores do sistema em uma lista, e estruturar os métodos
 * necessários para inserir, consultar, alterar ou remover. Implementa a interface LeitorDAO.
 *
 * @author Lucas Gabriel.
 */


public class LeitorImpl implements LeitorDAO {
    private List<Leitor> listaLeitor;
    private int nextID;

    /**
     * Construtor que inicializa a lista de armazenamento de leitores e o número de ID. O ID do
     * técnico possui o 3 como número fixo na casa da unidade, modificando apenas os valores nas
     * outras casas.
     */

    public LeitorImpl() {
        this.listaLeitor = new ArrayList<Leitor>();
        this.nextID = 1003;
    }

    /**
     * Método para adicionar um Leitor na lista de armazenamento. O ID é inserido nos dados do leitor
     * antes de adicioná-lo na lista. O valor 10 é somado no atributo id para o próximo leitor.
     *
     * @param obj leitor que deve ser armazenado
     * @return retorna objeto leitor criado
     */

    @Override
    public Leitor criar(Leitor obj) {
        obj.setID(this.nextID);
        this.nextID+=10;
        this.listaLeitor.add(obj);

        return obj;
    }

    /**
     * Método que encontra Leitores por meio de seus nomes.
     *
     * Objetos Leitor que possuam o nome informado, são adicionados numa
     * lista e retornados.
     *
     * @param nome o nome sobre os quais os leitores devem ser encontrados
     * @return retorna lista de leitores encontrados
     */

    @Override
    public List<Leitor> encontrarPorNome(String nome) {
        List<Leitor> listaNome = new ArrayList<Leitor>();

        for(Leitor leitor : this.listaLeitor){
            if(leitor.getNome().equals(nome.toLowerCase()))
                listaNome.add(leitor);
        }

        return listaNome;
    }

    /**
     * Método que encontra Leitores por meio de seus telefones.
     * Objetos Leitor que possuam o nome informado, são adicionados numa
     * lista e retornados.
     *
     * @param telefone o telefone sobre os quais os leitores devem ser encontrados
     * @return retorna lista de leitores encontrados
     */

    @Override
    public List<Leitor> encontrarPorTelefone(String telefone) {
        List<Leitor> listaTelefone = new ArrayList<Leitor>();

        for(Leitor leitor : this.listaLeitor){
            if(leitor.getTelefone().equals(telefone))
                listaTelefone.add(leitor);
        }

        return listaTelefone;
    }

    /**
     * Método de retorno do Leitor através da busca por ID.
     *
     * @param id identificação do leitor a ser encontrado
     * @return retorna objeto leitor encontrado
     */

    @Override
    public Leitor encontrarPorId(int id) {
        for(Leitor leitor : this.listaLeitor){
            if(leitor.getID() == id)
                return leitor;
        }

        return null;
    }

    /**
     * Método que atualiza um objeto do tipo Leitor.
     *
     * @param obj leitor a ser atualizado
     * @return retorna leitor que foi atualizado
     */

    @Override
    public Leitor atualizar(Leitor obj) {
        for(Leitor leitor : this.listaLeitor){
            if(leitor.getID()==obj.getID()){
                this.listaLeitor.set(listaLeitor.indexOf(leitor), obj);
                return obj;
            }
        }

        return null;
    }

    /**
     * Método para remover determinado Leitor da estrutura de armazenamento.
     *
     * @param id identificação do leitor a ser deletado
     */

    @Override
    public void remover(int id) {
        for(Leitor leitor : this.listaLeitor){
            if(leitor.getID()==id){
                this.listaLeitor.remove(leitor);
                return;
            }
        }
    }

    /**
     * Método de retorno de todos os objetos do tipo Leitor do banco de dados.
     *
     * @return retorna todos os objetos do tipo leitor
     */

    @Override
    public List<Leitor> encontrarTodos() {
        return this.listaLeitor;
    }

    /**
     * Deleta todos os objetos do tipo Leitor do banco de dados.
     *
     * A contagem de ID é resetada para o valor inicial.
     */

    @Override
    public void removerTodos() {
        this.listaLeitor.clear();
        this.nextID=1003;
    }

    @Override
    public void alteraParaPastaTeste() {
    }

    @Override
    public void alteraParaPastaPrincipal() {
    }
}
