package controller;

import dao.DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Adm;
import utils.StageController;

public class TelaProcurarUsuarioController {

    @FXML
    private Button botaoConfirmar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TextField digitaEscolha;

    @FXML
    private ToggleGroup grupo;

    @FXML
    private Label mensagemErro;

    @FXML
    private Label mensagemTitulo;

    private Adm adm;

    private String qualOperacao;

    @FXML
    void confirmarEscolha(ActionEvent event) {
        String dado = digitaEscolha.getText();

        if(dado.isEmpty()) {
            if(this.qualOperacao.equals("Administrador"))
                this.mensagemErro.setText("INSIRA DADO DO ADMINISTRADOR");

            else if(this.qualOperacao.equals("Bibliotecario"))
                this.mensagemErro.setText("INSIRA DADO DO BIBLIOTECARIO");

            else if(this.qualOperacao.equals("Leitor"))
                this.mensagemErro.setText("INSIRA DADO DO LEITOR");
        }

        else{
            RadioButton radio = (RadioButton) grupo.getSelectedToggle();
            String escolha = radio.getText();

            if (escolha.equals("ID") && !StageController.tryParseInt(dado))
                this.mensagemErro.setText("ID É COMPOSTO APENAS POR NÚMEROS");

            else{
                if(this.qualOperacao.equals("Administrador")){
                    if(escolha.equals("ID")){
                        if(DAO.getAdm().encontrarPorId(Integer.parseInt(dado))==null)
                            this.mensagemErro.setText("ADMINISTRADOR NÃO ENCONTRADO");

                        else{
                            //carrego a tela
                        }
                    }

                    else if(escolha.equals("Nome")){
                        if(DAO.getAdm().encontrarPorNome(dado).isEmpty())
                            this.mensagemErro.setText("ADMINISTRADOR NÃO ENCONTRADO");

                        else{
                            //carrego a tela
                        }
                    }
                }

                else if(this.qualOperacao.equals("Bibliotecario")){
                    if(escolha.equals("ID")){
                        if(DAO.getBibliotecario().encontrarPorId(Integer.parseInt(dado))==null)
                            this.mensagemErro.setText("BIBLIOTECARIO NÃO ENCONTRADO");

                        else{
                            //carrego a tela
                        }
                    }

                    else if(escolha.equals("Nome")){
                        if(DAO.getBibliotecario().encontrarPorNome(dado).isEmpty())
                            this.mensagemErro.setText("BIBLIOTECARIO NÃO ENCONTRADO");

                        else{
                            //carrego a tela
                        }
                    }
                }

                else if(this.qualOperacao.equals("Leitor")){
                    if(escolha.equals("ID")){
                        if(DAO.getLeitor().encontrarPorId(Integer.parseInt(dado))==null)
                            this.mensagemErro.setText("LEITOR NÃO ENCONTRADO");

                        else{
                            //carrego a tela
                        }
                    }

                    else if(escolha.equals("Nome")){
                        if(DAO.getBibliotecario().encontrarPorNome(dado).isEmpty())
                            this.mensagemErro.setText("LEITOR NÃO ENCONTRADO");

                        else{
                            //carrego a tela
                        }
                    }
                }
            }
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    public void setAdm(Adm adm) {
        this.adm = adm;
    }

    public void setQualOperacao(String qualOperacao) {
        this.qualOperacao = qualOperacao;
    }

    public void setTitulo(String titulo){
        this.mensagemTitulo.setText(titulo);
    }
}

