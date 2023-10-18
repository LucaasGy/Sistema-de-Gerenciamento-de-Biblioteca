package dao.prazo;

import model.Prazos;

import utils.ArmazenamentoArquivo;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar em um arquivo binário, todos os prazos para realizar empréstimo de livros do sistema
 * e estruturar os métodos necessários para inserir, consultar, alterar ou remover.
 * Implementa a interface PrazosDAO.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class PrazosImplArquivo implements PrazosDAO{

    private String nomeArquivo;
    private String nomePasta;
    private List<Prazos> listaPrazos;

    /**
     * Construtor que atribui o nome do arquivo a ser resgatado e resgata a lista de armazenamento de prazos
     * armazenados em um arquivo binário.
     */

    public PrazosImplArquivo() {
        this.nomeArquivo = "prazos.dat";
        this.nomePasta = "Prazos";

        this.listaPrazos = ArmazenamentoArquivo.resgatar(this.nomeArquivo,this.nomePasta);
    }

    /**
     * Método para adicionar um objeto do tipo Prazos na lista de armazenamento e guardar no arquivo.
     *
     * @param obj prazo que deve ser armazenado
     * @return retorna objeto prazo criado
     */

    @Override
    public Prazos criar(Prazos obj) {
        this.listaPrazos.add(obj);
        ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
        return obj;
    }

    /**
     * Método que remove todos os prazos ativos de um leitor da estrutura de armazenamento (lista e arquivo).
     *
     * Esta forma de remoção em sequência, evita problemas de deslocamento de índice que podem
     * ocorrer ao remover elementos de um ArrayList enquanto o percorremos.
     *
     * @param id identificação do leitor que recebe o prazo a ser deletado
     */

    @Override
    public void remover(int id) {
        List<Prazos> prazosARemover = new ArrayList<Prazos>();

        for(Prazos prazos : this.listaPrazos){
            if (prazos.getLeitor() == id)
                prazosARemover.add(prazos);
        }

        this.listaPrazos.removeAll(prazosARemover);
        ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
    }

    /**
     * Deleta todos os objetos do tipo Prazos do banco de dados.
     */

    @Override
    public void removerTodos() {
        this.listaPrazos.clear();
        ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
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
            if(prazo.getLeitor() == id){
                return prazo;
            }
        }

        return null;
    }

    /**
     * Método que encontra todos os prazos ativos do banco de dados.
     *
     * @return retorna lista de prazos ativos encontrados
     */

    @Override
    public List<Prazos> encontrarTodos() {
        return this.listaPrazos;
    }

    /**
     * Método que atualiza um objeto do tipo Prazos e guarda a atualização no arquivo.
     *
     * @param obj prazo a ser atualizado
     * @return retorna prazo que foi atualizado
     */

    @Override
    public Prazos atualizar(Prazos obj) {
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLivro()==obj.getLivro()){
                this.listaPrazos.set(this.listaPrazos.indexOf(prazo), obj);
                ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
                return obj;
            }
        }

        return null;
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
            if(prazo.getLivro() == isbn){
                return prazo;
            }
        }

        return null;
    }

    /**
     * Método que remove o prazo ativo de um livro da estrutura de armazenamento (lista e arquivo).
     *
     * @param isbn isbn do livro que recebe o prazo a ser deletado
     */

    @Override
    public void removerPrazoDeUmLivro(double isbn) {
        for(Prazos prazo : this.listaPrazos){
            if(prazo.getLivro() == isbn){
                this.listaPrazos.remove(prazo);
                ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
                return;
            }
        }
    }

    /**
     * Método que encontra todos os prazos ativos de um leitor.
     *
     * @param id identificação do leitor
     * @return retorna lista de prazos ativos de um leitor
     */

    @Override
    public List<Prazos> prazosDeUmLeitor(int id) {
        List<Prazos> listaPrazosLeitor = new ArrayList<Prazos>();

        for(Prazos prazos : this.listaPrazos){
            if(prazos.getLeitor() == id)
                listaPrazosLeitor.add(prazos);
        }

        return listaPrazosLeitor;
    }

    /**
     * Método que atualiza a lista de prazos com a exclusão de prazos vencidos e adição de novos prazos
     * e guarda a atualização no arquivo.
     *
     * Remove prazos vencidos da lista e adiciona novos prazos ativos.
     *
     * @param removePrazos lista de prazos a remover ( prazos vencidos excluidos )
     * @param adinionaPrazos lista de prazos a adicionar ( novos prazos )
     */

    @Override
    public void atualizarPrazos(List<Prazos> removePrazos, List<Prazos> adinionaPrazos) {
        this.listaPrazos.removeAll(removePrazos);
        this.listaPrazos.addAll(adinionaPrazos);
        ArmazenamentoArquivo.guardar(this.listaPrazos,this.nomeArquivo,this.nomePasta);
    }

    /**
     * Método que altera o caminho do arquivo Prazos para realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaTeste() {
        this.nomePasta = "Prazos Teste";
        this.nomeArquivo = "prazosTeste.dat";
        this.listaPrazos = ArmazenamentoArquivo.resgatar(this.nomeArquivo,this.nomePasta);
    }

    /**
     * Método que retorna o caminho do arquivo Prazos após realizar testes unitários e de integração.
     */
    @Override
    public void alteraParaPastaPrincipal() {
        this.nomePasta = "Prazos";
        this.nomeArquivo = "prazos.dat";
        this.listaPrazos = ArmazenamentoArquivo.resgatar(this.nomeArquivo,this.nomePasta);
    }
}
