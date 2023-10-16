package controller;

import model.Adm;
import model.Bibliotecario;
import model.Convidado;
import model.Leitor;

public class TelaInicialController {

    private Adm adm;
    private Bibliotecario bibliotecario;
    private Leitor leitor;
    private Convidado convidado;

    public void setAdm(Adm adm) {
        this.adm = adm;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }

    public void setConvidado(Convidado convidado) {
        this.convidado = convidado;
    }
}
