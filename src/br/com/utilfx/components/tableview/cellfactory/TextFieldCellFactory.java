package br.com.utilfx.components.tableview.cellfactory;

import javafx.scene.control.TableColumn;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import util.string.FormatStringInterface;
import br.com.utilfx.components.tableview.cell.TextFieldCell;

/**
 * Cria um objeto para controlar a célula das colunas que possuirem 
 * o objeto TextField.
 * 
 * @author Heverton Cruz
 * @version 1.0
 */
public abstract class TextFieldCellFactory implements Callback<TableColumn, TextFieldCell> {

    protected FormatStringInterface format;
    protected TextAlignment textAlignment;
    
    /**
     * Pega o formatador definido.
     * 
     * @return 
     */
    public FormatStringInterface getFormat() {
        return format;
    }
    
    /**
     * Pega o alinhamento do texto.
     * 
     * @return 
     */
    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    @Override
    public TextFieldCell call(TableColumn p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
