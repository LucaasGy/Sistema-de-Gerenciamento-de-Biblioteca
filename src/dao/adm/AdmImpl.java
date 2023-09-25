package dao.adm;

import model.Adm;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar todos os administradores do sistemam em uma lista, e estruturar os métodos
 * necessários para inserir, consultar, alterar ou remover. Implementa a interface AdmDAO.
 *
 * @author Lucas Gabriel.
 */

public class AdmImpl implements AdmDAO {
    private List<Adm> listaAdm;
    private int nextID;

    /**
     * Construtor que inicializa a lista de armazenamento de administradores e o número de ID. O ID do
     * técnico possui o 1 como número fixo na casa da unidade, modificando apenas os valores nas
     * outras casas.
     */

    public AdmImpl() {
        this.listaAdm = new ArrayList<Adm>();
        this.nextID = 1001;
    }

    /**
     * Método para adicionar um Administrador na lista de armazenamento.
     *
     * O ID é inserido nos dados do administrador antes de adicioná-lo na lista.
     * O valor 10 é somado no atributo id para o próximo administrador.
     *
     * @param obj administrador que deve ser armazenado
     * @return retorna objeto administrador criado
     */

    @Override
    public Adm criar(Adm obj) {
        obj.setID(this.nextID);
        this.nextID += 10;
        this.listaAdm.add(obj);

        return obj;
    }

    /**
     * Método de retorno do Administrador através da busca por ID.
     *
     * @param id identificação do administrador a ser encontrado
     * @return retorna objeto administrador encontrado
     */

    @Override
    public Adm encontrarPorId(int id) {
        for(Adm adm : this.listaAdm){
            if(adm.getID() == id)
                return adm;
        }

        return null;
    }

    /**
     * Método que encontra Administradores por meio de seus nomes.
     *
     * Objetos Administradores que possuam o nome informado, são adicionados numa
     * lista e retornados.
     *
     * @param nome o nome sobre os quais os administradores devem ser encontrados
     * @return retorna lista de administradores encontrados
     */

    @Override
    public List<Adm> encontrarPorNome(String nome) {
        List<Adm> listaNome = new ArrayList<Adm>();

        for(Adm adm : this.listaAdm){
            if(adm.getNome().equals(nome.toLowerCase()))
                listaNome.add(adm);
        }

        return listaNome;
    }

    /**
     * Método para remover determinado Administrador da estrutura de armazenamento.
     *
     * @param id identificação do administrador a ser deletado
     */

    @Override
    public void remover(int id) {
        for(Adm adm : this.listaAdm){
            if(adm.getID()==id) {
                this.listaAdm.remove(adm);
                return;
            }
        }
    }

    /**
     * Método de retorno de todos os objetos do tipo Administrador do banco de dados.
     *
     * @return retorna todos os objetos do tipo administrador
     */

    @Override
    public List<Adm> encontrarTodos() {

        return this.listaAdm;
    }

    /**
     * Método que atualiza um objeto do tipo Adm.
     *
     * @param obj administrador a ser atualizado
     * @return retorna administrador que foi atualizado
     */

    @Override
    public Adm atualizar(Adm obj) {
        for(Adm adm : this.listaAdm){
            if(adm.getID()==obj.getID()){
                this.listaAdm.set(listaAdm.indexOf(adm), obj);
                return obj;
            }
        }

        return null;
    }

    /**
     * Deleta todos os objetos do tipo Administrador do banco de dados.
     *
     * A contagem de ID é resetada para o valor inicial.
     */

    @Override
    public void removerTodos() {
        this.listaAdm.clear();
        this.nextID = 1001;
    }
}