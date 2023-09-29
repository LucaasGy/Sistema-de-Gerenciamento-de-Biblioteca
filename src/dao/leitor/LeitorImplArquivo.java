package dao.leitor;

import model.Leitor;
import utils.ArmazenamentoArquivo;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar todos os leitores do sistema em um arquivo binário
 * e estruturar os métodos necessários para inserir, consultar, alterar ou remover.
 * Implementa a interface LeitorDAO.
 *
 * @author Lucas Gabriel.
 */

public class LeitorImplArquivo implements LeitorDAO{

    private List<Leitor> listaLeitor;
    private String nomeArquivo;
    private String nomePasta;
    private int nextID;

    /**
     * Construtor que atribui o nome do arquivo a ser resgatado e resgata a lista de armazenamento de leitores
     * armazenados em um arquivo binário.
     * Inicializa o número de ID. O ID do técnico possui o 3 como número fixo na casa da unidade, modificando apenas
     * os valores nas outras casas.
     * Caso a lista resgatada do arquivo esteja vazia, o ID é inicializado como 1003.
     * Caso não esteja, obtém o último ID armazenado e adiciona 10 a ele.
     */

    public LeitorImplArquivo() {
        this.nomeArquivo = "leitor.dat";
        this.nomePasta = "Leitor";
        this.listaLeitor = ArmazenamentoArquivo.resgatar(this.nomeArquivo,this.nomePasta);

        if(this.listaLeitor.isEmpty())
            this.nextID = 1003;
        else
            this.nextID = this.listaLeitor.get(this.listaLeitor.size()-1).getID() + 10;
    }

    /**
     * Método para adicionar um Leitor na lista de armazenamento e guardar no arquivo.
     *
     * O ID é inserido nos dados do leitor antes de adicioná-lo na lista.
     * O valor 10 é somado no atributo id para o próximo leitor.
     *
     * @param obj leitor que deve ser armazenado
     * @return retorna objeto leitor criado
     */

    @Override
    public Leitor criar(Leitor obj) {
        obj.setID(this.nextID);
        this.nextID += 10;
        this.listaLeitor.add(obj);

        ArmazenamentoArquivo.guardar(this.listaLeitor,this.nomeArquivo,this.nomePasta);
        return obj;
    }

    /**
     * Método para remover determinado Leitor da estrutura de armazenamento (lista e arquivo).
     *
     * @param id identificação do leitor a ser deletado
     */

    @Override
    public void remover(int id) {
        for(Leitor leitor : this.listaLeitor){
            if(leitor.getID()==id) {
                this.listaLeitor.remove(leitor);
                ArmazenamentoArquivo.guardar(this.listaLeitor,this.nomeArquivo,this.nomePasta);
                return;
            }
        }
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
        ArmazenamentoArquivo.guardar(this.listaLeitor,this.nomeArquivo,this.nomePasta);
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
     * Método de retorno de todos os objetos do tipo Leitor do banco de dados.
     *
     * @return retorna todos os objetos do tipo leitor
     */

    @Override
    public List<Leitor> encontrarTodos() {
        return this.listaLeitor;
    }

    /**
     * Método que atualiza um objeto do tipo Leitor e guarda a atualização no arquivo.
     *
     * @param obj leitor a ser atualizado
     * @return retorna leitor que foi atualizado
     */

    @Override
    public Leitor atualizar(Leitor obj) {
        for(Leitor leitor : this.listaLeitor){
            if(leitor.getID()==obj.getID()){
                this.listaLeitor.set(listaLeitor.indexOf(leitor), obj);
                ArmazenamentoArquivo.guardar(this.listaLeitor,this.nomeArquivo,this.nomePasta);
                return obj;
            }
        }

        return null;
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
     *
     * Objetos Leitor que possuam o telefone informado, são adicionados numa
     * lista e retornados.
     *
     * @param telefone o telefone sobre os quais os leitores devem ser encontrados
     * @return retorna lista de leitores encontrados
     */

    @Override
    public List<Leitor> encontrarPorTelefone(String telefone) {
        List<Leitor> listaTelefone = new ArrayList<Leitor>();

        for(Leitor leitor : this.listaLeitor){
            if(leitor.getTelefone().equals(telefone.toLowerCase()))
                listaTelefone.add(leitor);
        }

        return listaTelefone;
    }

    /**
     * Método que altera o caminho do arquivo Leitor para realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaTeste() {
        this.nomePasta = "Leitor Teste";
        this.nomeArquivo = "leitorTeste.dat";
    }

    /**
     * Método que retorna o caminho do arquivo Leitor após realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaPrincipal() {
        this.nomePasta = "Leitor";
        this.nomeArquivo = "leitor.dat";
    }
}
