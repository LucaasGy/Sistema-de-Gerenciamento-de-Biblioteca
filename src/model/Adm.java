package model;

import dao.DAO;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.objetos.ObjetoInvalido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Subclasse Adm que extende a Superclasse Usuário.
 *
 * Representa um Administrador do sistema, podendo realizar CRUD dos livros e usuários,
 * solicitar relatórios e estatísticas do sistema, bloqueio e desbloqueio de Leitores,
 * gerenciamento de multas e também pesquisa de livros herdado da Superclasse Usuário.
 *
 * @author Lucas Gabriel.
 */

public class Adm extends Usuario {

    /**
     * Construtor de um Administrador do sistema.
     *
     * Recebe como parâmetro a maioria dos atributos da classe para inseri-las diretamente.
     * A inserção é feita chamando o construtor da Superclasse.
     * O tipo de usuário é definido a depender do usuário em questão.
     *
     * @param nome nome do Administrador
     * @param senha senha do Administrador
     */

    public Adm(String nome, String senha){
        super(nome, senha, TipoUsuario.ADM);
    }

    /**
     * Método que cria um novo usuário do tipo Leitor e o adiciona no sistema.
     *
     * @param leitor objeto leitor contendo todas as informações pré definidas
     */

    public void criarLeitor(Leitor leitor){

        DAO.getLeitor().criar(leitor);
    }

    /**
     * Método que remove um Leitor do sistema.
     *
     * Caso o leitor não seja encontrado ou esteja com algum empréstimo
     * ele não poderá ser removido.
     * Caso tudo esteja correto, é removido todas as reservas e prazos do leitor
     * e ele é removido do sistema.
     *
     * @param id identificação do leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorTemEmprestimo caso o leitor esteja com algum emprestimo de livro,
     * retorna uma exceção informando o ocorrido
     */

    public void removerLeitor(int id) throws ObjetoInvalido, LeitorTemEmprestimo {
        if(DAO.getLeitor().encontrarPorId(id)==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        if(DAO.getEmprestimo().encontrarPorId(id)!=null)
            throw new LeitorTemEmprestimo();

        DAO.getReserva().removerReservasDeUmLeitor(id);
        DAO.getPrazos().remover(id);

        DAO.getLeitor().remover(id);
    }

    /**
     * Método que atualiza a Senha de um Leitor.
     *
     * @param senha nova senha do leitor
     * @param id identificação do leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarSenhaLeitor(String senha, int id) throws ObjetoInvalido {
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        leitor.setSenha(senha);
    }

    /**
     * Método que atualiza o Endereço de um Leitor.
     *
     * @param endereco novo endereço
     * @param id identificação do leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarEnderecoLeitor(String endereco, int id) throws ObjetoInvalido {
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        leitor.setEndereco(endereco);
    }

    /**
     * Método que atualiza o Telefone de um Leitor.
     *
     * @param telefone novo telefone
     * @param id identicação do leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarTelefoneLeitor(String telefone, int id) throws ObjetoInvalido {
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        leitor.setTelefone(telefone);
    }

    /**
     * Método que cria um novo usuário do tipo Bibliotecario e o adiciona no sistema.
     *
     * @param bibliotecario objeto bibliotecario contendo todas as informações pré definidas
     */

    public void criarBibliotecario(Bibliotecario bibliotecario){
        DAO.getBibliotecario().criar(bibliotecario);
    }

    /**
     * Método que remove um Bibliotecario do sistema.
     *
     * @param id identificação do bibliotecario
     * @throws ObjetoInvalido caso não seja encontrado o bibliotecario com o id informado,
     * retorna uma exceção informando o ocorrido
     */
    public void removerBibliotecario(int id) throws ObjetoInvalido {
        if(DAO.getBibliotecario().encontrarPorId(id)==null)
            throw new ObjetoInvalido("BIBLIOTECARIO NAO ENCONTRADO");

        DAO.getBibliotecario().remover(id);
    }

    /**
     * Método que atualiza Senha de um Bibliotecario.
     *
     * @param senha nova senha
     * @param id identificação do bibliotecario
     * @throws ObjetoInvalido caso não seja encontrado o bibliotecario com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarSenhaBibliotecario(String senha, int id) throws ObjetoInvalido {
        Bibliotecario bibliotecario = DAO.getBibliotecario().encontrarPorId(id);

        if(bibliotecario==null)
            throw new ObjetoInvalido("BIBLIOTECARIO NAO ENCONTRADO");

        bibliotecario.setSenha(senha);
    }

    /**
     * Método que cria um novo usuário do tipo Administrador e o adiciona no sistema.
     *
     * @param adm objeto adm contendo todas as informações pré definidas
     */

    public void criarAdm(Adm adm){
        DAO.getAdm().criar(adm);
    }

    /**
     * Método que remove um Administrador do sistema.
     *
     * @param id identicação do administrador
     * @throws ObjetoInvalido caso não seja encontrado o administrador com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public void removerAdm(int id) throws ObjetoInvalido {
        if(DAO.getAdm().encontrarPorId(id)==null)
            throw new ObjetoInvalido("ADMINISTRADOR NAO ENCONTRADO");

        DAO.getAdm().remover(id);
    }

    /**
     * Método que atualiza Senha de um Administrador.
     *
     * @param senha nova senha
     * @param id identicação do administrador
     * @throws ObjetoInvalido caso não seja encontrado o administrador com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarSenhaAdm(String senha, int id) throws ObjetoInvalido {
        Adm adm = DAO.getAdm().encontrarPorId(id);

        if(adm==null)
            throw new ObjetoInvalido("ADMINISTRADOR NAO ENCONTRADO");

        adm.setSenha(senha);
    }

    /**
     * Método que bloqueia um Leitor no sistema.
     *
     * Caso tudo esteja correto, é removido todas as reservas e prazos do leitor
     * e ele é bloqueado no sistema.
     *
     * @param id identificação do leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public void bloquearLeitor(int id) throws ObjetoInvalido {
        Leitor bloquear = DAO.getLeitor().encontrarPorId(id);

        if(bloquear==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        DAO.getReserva().removerReservasDeUmLeitor(id);
        DAO.getPrazos().remover(id);

        bloquear.setBloqueado(true);
    }

    /**
     * Método que desbloqueia um Leitor no sistema.
     *
     * @param id identificação do leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public void desbloquearLeitor(int id) throws ObjetoInvalido {
        Leitor bloquear = DAO.getLeitor().encontrarPorId(id);

        if(bloquear==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        bloquear.setBloqueado(false);
    }

    /**
     * Método que retira manualmente a multa de algum Leitor.
     *
     * @param id identicação do leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public void tirarMulta(int id) throws ObjetoInvalido {
        Leitor tira = DAO.getLeitor().encontrarPorId(id);

        if(tira==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        tira.setDataMulta(null);
    }

    /**
     * Método que cria um novo Livro e o adiciona no sistema.
     *
     * @param livro objeto livro contendo todas as informações pré definidas
     */
    public void registrarLivro(Livro livro){
        DAO.getLivro().criar(livro);
    }

    /**
     * Método que remove um Livro do acervo da biblioteca.
     *
     * Caso o livro não seja encontrado ou esteja emprestado
     * ele não poderá ser removido.
     * Caso tudo esteja correto, é removido todas as reservas e prazos do livro
     * e também o histórico de empréstimos já feitos desse livro. Após isso, ele é removido do acervo.
     *
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     * @throws LivroEmprestado caso o livro esteja emprestado por algum leitor,
     * retorna uma exceção informando o ocorrido
     */

    public void removerUmLivro(double isbn) throws ObjetoInvalido, LivroEmprestado {
        if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        if(DAO.getEmprestimo().encontrarPorISBN(isbn)!=null)
            throw new LivroEmprestado();

        DAO.getReserva().removerReservasDeUmLivro(isbn);
        DAO.getPrazos().removerPrazoDeUmLivro(isbn);
        DAO.getEmprestimo().removerTodosEmprestimosDeUmLivro(isbn);

        DAO.getLivro().removerPorISBN(isbn);
    }

    /**
     * Método que atualiza Titulo de um Livro.
     *
     * @param titulo novo titulo
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarTituloLivro(String titulo, double isbn) throws ObjetoInvalido {
        Livro opera = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera.setTitulo(titulo);
    }

    /**
     * Método que atualiza Autor de um Livro.
     *
     * @param autor novo autor
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarAutorLivro(String autor, double isbn) throws ObjetoInvalido {
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera2==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera2.setAutor(autor);
    }

    /**
     * Método que atualiza Editora de um Livro.
     *
     * @param editora nova editora
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarEditoraLivro(String editora, double isbn) throws ObjetoInvalido {
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera2==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera2.setEditora(editora);
    }

    /**
     * Método que atualiza Ano de um Livro.
     *
     * @param ano novo ano
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarAnoLivro(int ano, double isbn) throws ObjetoInvalido {
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera2==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera2.setAno(ano);
    }

    /**
     * Método que atualiza Categoria de um Livro.
     *
     * @param categoria nova categoria
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     */
    public void atualizarCategoriaLivro(String categoria, double isbn) throws ObjetoInvalido {
        Livro opera2 = DAO.getLivro().encontrarPorISBN(isbn);

        if(opera2==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        opera2.setCategoria(categoria);
    }

    /**
     * Método que atualiza Disponibilidade de um Livro.
     *
     * Caso o livro não seja encontrado ou esteja emprestado
     * ele não poderá ter sua disponibilidade alterada.
     * Caso tudo esteja correto, é removido todas as reservas e prazos do livro
     * e sua disponibilidade é atualizada.
     *
     * ps: não alterar disponibilidade do livro caso ele esteja emprestado
     *
     * @param isbn isbn do livro
     * @param FouT booleano true ou false
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     * @throws LivroEmprestado caso o livro esteja emprestado por algum leitor,
     * retorna uma exceção informando o ocorrido
     */

    public void atualizarDisponibilidadeLivro(double isbn, boolean FouT) throws ObjetoInvalido, LivroEmprestado {
        Livro novo = DAO.getLivro().encontrarPorISBN(isbn);

        if(novo==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        if(DAO.getEmprestimo().encontrarPorISBN(isbn)!=null)
            throw new LivroEmprestado();

        DAO.getReserva().removerReservasDeUmLivro(isbn);
        DAO.getPrazos().removerPrazoDeUmLivro(isbn);

        novo.setDisponivel(FouT);
    }

    /**
     * Método que retorna todos os Livros armazenados no sistema.
     *
     * @return retorna uma lista de livros armazenados
     */

    public List<Livro> estoque(){
        return DAO.getLivro().encontrarTodos();
    }

    /**
     * Método que retorna o total de número de livros armazenados no sistema.
     *
     * @return retorna tamanho da lista de livro armazenados
     */

    public int numeroLivrosEstoque(){
        int n;

        return n = estoque().size();
    }

    /**
     * Método que retorna todos os Livros emprestados atualmente.
     *
     * @return retorna uma lista de livros emprestados
     */

    public List<Livro> livrosEmprestados(){
        List<Livro> livros = new ArrayList<Livro>();

        for(Emprestimo emp : DAO.getEmprestimo().encontrarTodosAtuais()){
            livros.add(emp.getLivro());
        }

        return livros;
    }

    /**
     * Método que retorna todos os Livros atrasados atualmente.
     *
     * @return retorna uma lista de livros atrasados
     */

    public List<Livro> livrosAtrasados(){
        List<Livro> livrosAtrasados = new ArrayList<Livro>();

        LocalDate agr = LocalDate.now();

        for(Emprestimo emp : DAO.getEmprestimo().encontrarTodosAtuais()){
            if(emp.getdataPrevista().isBefore(agr)){
                livrosAtrasados.add(emp.getLivro());
            }
        }

        return livrosAtrasados;
    }

    /**
     * Método que retorna todos os Livros reservados atualmente.
     *
     * @return retorna uma lista de livros reservados
     */

    public List<Livro> livrosReservados(){
        List<Livro> livros = new ArrayList<Livro>();

        for(Reserva res : DAO.getReserva().encontrarTodos()){
            livros.add(res.getLivro());
        }

        return livros;
    }

    /**
     * Método que retorna o número de livros emprestados atualmente.
     *
     * @return retorna tamanho da lista de livros emprestados atualmente
     */

    public int numeroLivrosEmprestados(){
        int n;

        return n = livrosEmprestados().size();
    }

    /**
     * Método que retorna o número de livros atrasados atualmente.
     *
     * @return retorna tamanho da lista de livros atrasados atualmente
     */

    public int numeroLivrosAtrasados(){
        int n;

        return n = livrosAtrasados().size();
    }

    /**
     * Método que retorna o número de livros reservados atualmente.
     *
     * @return retorna tamanho da lista de livros reservados atualmente
     */

    public int numeroLivrosReservados(){
        int n;

        return n = livrosReservados().size();
    }

    /**
     * Método que retorna todos os Empréstimos de um determinado Leitor.
     *
     * @param id identificação do leitor
     * @return retorna uma lista de empréstimos de um leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido
     */

    public List<Emprestimo> historicoEmprestimoDeUmLeitor(int id) throws ObjetoInvalido {
        if(DAO.getLeitor().encontrarPorId(id)==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        return DAO.getEmprestimo().encontrarHistoricoDeUmLeitor(id);
    }

    /**
     * Método que retorna os Livros mais populares em ordem usando como parâmetro
     * a quantidade de vezes que cada livro foi emprestado.
     *
     * Caso possua mais de 10 livros cadastrados no sistema, retorna os 10 mais populares.
     * Caso não possua, retorna a quantidade que tiver.
     *
     * @return retorna lista dos 10 livros mais populares
     */

    public List<Livro> livrosMaisPopulares() {
        List<Livro> livrosPopulares = DAO.getLivro().encontrarTodos();

        Comparator<Livro> comparador = Comparator.comparingInt(Livro::getQtdEmprestimo);
        //Collections.sort(copia, comparador);
        livrosPopulares.sort(comparador);

        List<Livro> top10livros = new ArrayList<Livro>();

        if (livrosPopulares.size() >= 10)
            top10livros.addAll(livrosPopulares.subList(0, 10));

        else
            top10livros.addAll(livrosPopulares);

        return top10livros;
    }
}