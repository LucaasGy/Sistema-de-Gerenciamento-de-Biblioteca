package dao.emprestimo;

import model.Emprestimo;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar todos os empréstimos do sistema, e estruturar os métodos
 * necessários para inserir, consultar, alterar ou remover. Implementa a interface EmprestimoDAO.
 *
 * @author Lucas Gabriel.
 */


public class EmprestimoImpl implements EmprestimoDAO {
    private List<Emprestimo> listaEmprestimoTotal;
    private List<Emprestimo> listaEmprestimoAtual;

    /**
     * Construtor que inicializa as listas de armazenamento de empréstimos.
     * É criados duas lista distintas para armazenar empréstimos ativo e a
     * de todos empréstimos já feitos.
     */

    public EmprestimoImpl() {
        this.listaEmprestimoTotal = new ArrayList<Emprestimo>();
        this.listaEmprestimoAtual = new ArrayList<Emprestimo>();
    }

    /**
     * Método para adicionar um objeto do tipo Emprestimo na lista de armazenamento de empréstimos ativos
     * e a de todos os empréstimo já feitos.
     *
     * @param obj Empréstimo que deve ser armazenado
     * @return retorna objeto empréstimo criado
     */

    @Override
    public Emprestimo criar(Emprestimo obj) {
        this.listaEmprestimoTotal.add(obj);
        this.listaEmprestimoAtual.add(obj);

        return obj;
    }

    /**
     * Método para remover determinado Empréstimo da estrutura de armazenamento.
     *
     * @param id identificação do Leitor que realizou o Emprestimo a ser deletado
     */

    @Override
    public void remover(int id) {
        for(Emprestimo emp : this.listaEmprestimoAtual){
            if(emp.getLeitor().getID() == id){
                this.listaEmprestimoAtual.remove(emp);
                return;
            }
        }
    }

    /**
     * Método que remove todos os empréstimos já feitos de um livro deletado do sistema.
     * É utilizado um for padrão invés de um for each, pois como
     * se trata de sequências de remoções em um loop, o for each
     * acaba dando erro pois o tamanho da lista vai alterando durante
     * as remoções. Utilizando um for padrão, este erro não ocorre.
     *
     * @param isbn isbn do livro deletado
     */

    public void removerTodosEmprestimosDeUmLivro(double isbn){
        for(int i=0; i<this.listaEmprestimoTotal.size(); i++){
            if(this.listaEmprestimoTotal.get(i).getLivro().getISBN()==isbn)
                this.listaEmprestimoTotal.remove(i);
        }
    }

    /**
     * Método de retorno do Emprestimo através da busca por ID.
     *
     * @param id identificação do Leitor que realizou o Emprestimo a ser encontrado
     * @return retorna objeto emprestimo encontrado
     */

    @Override
    public Emprestimo encontrarPorId(int id) {
        for(Emprestimo emp : this.listaEmprestimoAtual){
            if(emp.getLeitor().getID()==id)
                return emp;
        }

        return null;
    }

    /**
     * Método que encontra um empréstimo pelo isbn do livro emprestado.
     *
     * @param isbn isbn do livro que foi emprestado
     * @return retorna o empréstimo do livro encontrado
     */

    @Override
    public Emprestimo encontrarPorISBN(double isbn){
        for(Emprestimo emp : this.listaEmprestimoAtual){
            if(emp.getLivro().getISBN()==isbn)
                return emp;
        }

        return null;
    }

    /**
     * Método que encontra todos os empréstimos já feitos.
     *
     * @return retorna lista de todos os empréstimos já feitos encontrados
     */

    @Override
    public List<Emprestimo> encontrarTodos() {
        return this.listaEmprestimoTotal;
    }

    /**
     * Método que encontra todos os empréstimos ativos.
     *
     * @return retorna lista de empréstimos ativos encontrados
     */

    @Override
    public List<Emprestimo> encontrarTodosAtuais() {
        return this.listaEmprestimoAtual;
    }

    /**
     * Método que encontra todos os empréstimos já feitos por um determinado leitor.
     * Objetos Emprestimo que possuam o ID informado, são adicionados numa
     * lista e retornados.
     *
     * @param id identificação do leitor que realizou o emprestimo
     * @return retorna lista de empréstimos de um leitor
     */

    @Override
    public List<Emprestimo> encontrarHistoricoDeUmLeitor(int id) {
        List<Emprestimo> historicoDoID = new ArrayList<Emprestimo>();

        for(Emprestimo emp : this.listaEmprestimoTotal){
            if(emp.getLeitor().getID()==id)
                historicoDoID.add(emp);
        }

        return historicoDoID;
    }

    /**
     * Deleta todos os objetos do tipo Emprestimo do banco de dados.
     * Antes de deletar todos os empréstimos, a quantidade de empréstimos
     * feitos de cada livro é resetado para 0.
     * É deletado os empréstimos ativos e todos os empréstimos já feitos.
     */

    @Override
    public void removerTodos(){
        for(Emprestimo emp : this.listaEmprestimoTotal){
            emp.getLivro().setQtdEmprestimo(0);
        }
        this.listaEmprestimoAtual.clear();
        this.listaEmprestimoTotal.clear();
    }
}
