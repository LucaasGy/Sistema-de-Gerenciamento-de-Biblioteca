package dao.bibliotecario;

import model.Bibliotecario;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar todos os bibliotecarios do sistema, e estruturar os métodos
 * necessários para inserir, consultar, alterar ou remover. Implementa a interface BibliotecarioDAO.
 *
 * @author Lucas Gabriel.
 */

public class BibliotecarioImpl implements BibliotecarioDAO {
    private List<Bibliotecario> listaBibliotecario;
    private int nextID;

    /**
     * Construtor que inicializa a lista de armazenamento de bibliotecarios e o número de ID. O ID do
     * técnico possui o 2 como número fixo na casa da unidade, modificando apenas os valores nas
     * outras casas.
     */

    public BibliotecarioImpl() {
        this.listaBibliotecario = new ArrayList<Bibliotecario>();
        this.nextID = 1002;
    }

    /**
     * Método para adicionar um Bibliotecario na lista de armazenamento. O ID é inserido nos dados do bibliotecario
     * antes de adicioná-lo na lista. O valor 10 é somado no atributo id para o próximo bibliotecario.
     *
     * @param obj Bibliotecario que deve ser armazenado
     * @return retorna objeto bibliotecario criado
     */

    @Override
    public Bibliotecario criar(Bibliotecario obj) {
        obj.setID(this.nextID);
        this.nextID += 10;
        this.listaBibliotecario.add(obj);

        return obj;
    }

    /**
     * Método de retorno do Bibliotecario através da busca por ID.
     *
     * @param id identificação do bibliotecario a ser encontrado
     * @return retorna objeto bibliotecario encontrado
     */

    @Override
    public Bibliotecario encontrarPorId(int id) {
        for(Bibliotecario biblio : this.listaBibliotecario){
            if(biblio.getID() == id)
                return biblio;
        }

        return null;
    }

    /**
     * Método que encontra Bibliotecarios por meio de seus nomes.
     * Objetos Bibliotecarios que possuam o nome informado, são adicionados numa
     * lista e retornados.
     *
     * @param nome o nome sobre os quais os Bibliotecarios devem ser encontrados
     * @return retorna lista de bibliotecarios encontrados
     */

    @Override
    public List<Bibliotecario> encontrarPorNome(String nome) {
        List<Bibliotecario> listaNome = new ArrayList<Bibliotecario>();

        for(Bibliotecario biblio : this.listaBibliotecario){
            if(biblio.getNome().equals(nome.toLowerCase()))
                listaNome.add(biblio);
        }

        return listaNome;
    }

    /**
     * Método que atualiza um objeto do tipo Bibliotecario.
     *
     * @param obj bibliotecario a ser atualizado
     * @return retorna bibliotecario que foi atualizado
     */

    @Override
    public Bibliotecario atualizar(Bibliotecario obj) {
        for(Bibliotecario bibliotecario : this.listaBibliotecario){
            if(bibliotecario.getID()==obj.getID()){
                this.listaBibliotecario.set(listaBibliotecario.indexOf(bibliotecario), obj);
                return obj;
            }
        }

        return null;
    }

    /**
     * Método para remover determinado Bibliotecario da estrutura de armazenamento.
     *
     * @param id identificação do bibliotecario a ser deletado
     */

    @Override
    public void remover(int id) {
        for(Bibliotecario biblio : this.listaBibliotecario){
            if(biblio.getID()==id) {
                this.listaBibliotecario.remove(biblio);
                return;
            }
        }
    }

    /**
     * Método de retorno de todos os objetos do tipo Bibliotecario do banco de dados.
     *
     * @return retorna todos os objetos do tipo bibliotecario
     */

    @Override
    public List<Bibliotecario> encontrarTodos() {
        return this.listaBibliotecario;
    }

    /**
     * Deleta todos os objetos do tipo Bibliotecario do banco de dados.
     * A contagem de ID é resetada para o valor inicial.
     */

    @Override
    public void removerTodos() {
        this.listaBibliotecario.clear();
        this.nextID = 1002;
    }
}
