package dao.bibliotecario;

import model.Bibliotecario;
import utils.ArmazenamentoArquivo;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar todos os bibliotecarios do sistema em um arquivo binário
 * e estruturar os métodos necessários para inserir, consultar, alterar ou remover.
 * Implementa a interface BibliotecarioDAO.
 *
 * @author Lucas Gabriel.
 */

public class BibliotecarioImplArquivo implements BibliotecarioDAO{

    private List<Bibliotecario> listaBibliotecario;
    private String nomeArquivo;
    private String nomePasta;
    private int nextID;

    /**
     * Construtor que atribui o nome do arquivo a ser resgatado e resgata a lista de armazenamento de bibliotecarios
     * armazenados em um arquivo binário.
     * Inicializa o número de ID. O ID do técnico possui o 2 como número fixo na casa da unidade, modificando apenas
     * os valores nas outras casas.
     * Caso a lista resgatada do arquivo esteja vazia, o ID é inicializado como 1002.
     * Caso não esteja, obtém o último ID armazenado e adiciona 10 a ele.
     */

    public BibliotecarioImplArquivo() {
        this.nomeArquivo = "bibliotecario.dat";
        this.nomePasta = "Bibliotecario";
        this.listaBibliotecario = ArmazenamentoArquivo.resgatar(this.nomeArquivo,this.nomePasta);

        if(this.listaBibliotecario.isEmpty())
            this.nextID = 1002;
        else
            this.nextID = this.listaBibliotecario.get(this.listaBibliotecario.size()-1).getID() + 10;
    }

    /**
     * Método para adicionar um Bibliotecario na lista de armazenamento e guardar no arquivo.
     *
     * O ID é inserido nos dados do bibliotecario antes de adicioná-lo na lista.
     * O valor 10 é somado no atributo id para o próximo bibliotecario.
     *
     * @param obj bibliotecario que deve ser armazenado
     * @return retorna objeto bibliotecario criado
     */

    @Override
    public Bibliotecario criar(Bibliotecario obj) {
        obj.setID(this.nextID);
        this.nextID += 10;
        this.listaBibliotecario.add(obj);

        ArmazenamentoArquivo.guardar(this.listaBibliotecario,this.nomeArquivo,this.nomePasta);
        return obj;
    }

    /**
     * Método para remover determinado Bibliotecario da estrutura de armazenamento (lista e arquivo).
     *
     * @param id identificação do bibliotecario a ser deletado
     */

    @Override
    public void remover(int id) {
        for(Bibliotecario bibliotecario : this.listaBibliotecario){
            if(bibliotecario.getID()==id) {
                this.listaBibliotecario.remove(bibliotecario);
                ArmazenamentoArquivo.guardar(this.listaBibliotecario,this.nomeArquivo,this.nomePasta);
                return;
            }
        }
    }

    /**
     * Deleta todos os objetos do tipo Bibliotecario do banco de dados.
     *
     * A contagem de ID é resetada para o valor inicial.
     */

    @Override
    public void removerTodos() {
        this.listaBibliotecario.clear();
        this.nextID=1002;
        ArmazenamentoArquivo.guardar(this.listaBibliotecario,this.nomeArquivo,this.nomePasta);
    }

    /**
     * Método de retorno do Bibliotecario através da busca por ID.
     *
     * @param id identificação do bibliotecario a ser encontrado
     * @return retorna objeto bibliotecario encontrado
     */

    @Override
    public Bibliotecario encontrarPorId(int id) {
        for(Bibliotecario bibliotecario : this.listaBibliotecario){
            if(bibliotecario.getID() == id)
                return bibliotecario;
        }

        return null;
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
     * Método que atualiza um objeto do tipo Bibliotecario e guarda a atualização no arquivo.
     *
     * @param obj bibliotecario a ser atualizado
     * @return retorna bibliotecario que foi atualizado
     */

    @Override
    public Bibliotecario atualizar(Bibliotecario obj) {
        for(Bibliotecario bibliotecario : this.listaBibliotecario){
            if(bibliotecario.getID()==obj.getID()){
                this.listaBibliotecario.set(listaBibliotecario.indexOf(bibliotecario), obj);
                ArmazenamentoArquivo.guardar(this.listaBibliotecario,this.nomeArquivo,this.nomePasta);
                return obj;
            }
        }

        return null;
    }

    /**
     * Método que encontra Bibliotecarios por meio de seus nomes.
     *
     * Objetos Bibliotecario que possuam o nome informado, são adicionados numa
     * lista e retornados.
     *
     * @param nome o nome sobre os quais os bibliotecarios devem ser encontrados
     * @return retorna lista de bibliotecarios encontrados
     */

    @Override
    public List<Bibliotecario> encontrarPorNome(String nome) {
        List<Bibliotecario> listaNome = new ArrayList<Bibliotecario>();

        for(Bibliotecario bibliotecario : this.listaBibliotecario){
            if(bibliotecario.getNome().equals(nome.toLowerCase()))
                listaNome.add(bibliotecario);
        }

        return listaNome;
    }

    /**
     * Método que altera o caminho do arquivo Bibliotecario para realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaTeste() {
        this.nomePasta = "Bibliotecario Teste";
        this.nomeArquivo = "bibliotecarioTeste.dat";
    }

    /**
     * Método que retorna o caminho do arquivo Bibliotecario após realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaPrincipal() {
        this.nomePasta = "Bibliotecario";
        this.nomeArquivo = "bibliotecario.dat";
    }
}
