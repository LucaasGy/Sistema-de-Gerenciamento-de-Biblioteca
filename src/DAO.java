public class DAO {
    private static AdmDAO admDAO;
    private static BibliotecarioDAO bibliotecarioDAO;
    private static LeitorDAO leitorDAO;
    private static LivroDAO livroDAO;
    private static EmprestimoDAO emprestimoDAO;
    private static ReservaDAO reservaDAO;
    private static PrazosDAO prazosDAO;

    public static AdmDAO getAdm() {

        if (admDAO == null) {
            admDAO = new AdmImpl();
        }

        return admDAO;
    }

    public static BibliotecarioDAO getBibliotecario() {

        if (bibliotecarioDAO == null) {
            bibliotecarioDAO = new BibliotecarioImpl();
        }

        return bibliotecarioDAO;
    }

    public static LeitorDAO getLeitor() {

        if (leitorDAO == null) {
            leitorDAO = new LeitorImpl();
        }

        return leitorDAO;
    }

    public static LivroDAO getLivro() {

        if (livroDAO == null) {
            livroDAO = new LivroImpl();
        }

        return livroDAO;
    }

    public static EmprestimoDAO getEmprestimo() {

        if (emprestimoDAO == null) {
            emprestimoDAO = new EmprestimoImpl();
        }

        return emprestimoDAO;
    }

    public static ReservaDAO getReserva() {

        if (reservaDAO == null) {
            reservaDAO = new ReservaImpl();
        }

        return reservaDAO;
    }

    public static PrazosDAO getPrazos() {

        if (prazosDAO == null) {
            prazosDAO = new PrazosImpl();
        }

        return prazosDAO;
    }
}
