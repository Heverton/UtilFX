package br.com.utilfx.teste;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author usuario
 */
public class Molde {

    private String texto = "";
    private BigDecimal numero = BigDecimal.ZERO;
    private Date data;
    private String mascara = "";
    private Image imagem = null;
    private boolean status;
    private Uf uf;
    private String label;

    public Molde(boolean status, Uf uf) {
        this.status = status;
        this.uf = uf;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Molde() {
//        try {
//            setImagem(new Image(TesteUtilFX.class.getResource("/utilfx/teste/cancelar15x15.png").openStream()));
//        } catch (IOException ex) {
//            Logger.getLogger(TesteUtilFX.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public BigDecimal getNumero() {
        return numero;
    }

    public void setNumero(BigDecimal numero) {
        this.numero = numero;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public Image getImagem() {
        return imagem;
    }

    public void setImagem(Image imagem) {
        this.imagem = imagem;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Molde{" + "texto=" + texto + ", numero=" + numero + ", data=" + data + ", mascara=" + mascara + ", imagem=" + imagem + ", status=" + status + ", uf=" + uf.toString()+ ", label=" + label + '}';
    }
    
    
}
