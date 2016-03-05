package br.com.utilfx.components.tableview.cellfactory;

import javafx.scene.control.TableColumn;
import javafx.scene.text.TextAlignment;
import util.string.FloatString;
import br.com.utilfx.components.tableview.TableViewModel;
import br.com.utilfx.components.tableview.cell.TextFieldCell;
import br.com.utilfx.components.textinputcontrol.FloatTextInputControl;

/**
 * Cria um objeto para controlar a célula das colunas que possuirem 
 * o objeto TextField com filtro de caracteres.
 * 
 * @author Heverton Cruz
 * @version 1.1
 */
public class FloatTextFieldCellFactory extends TextFieldCellFactory {
    
    private final TableViewModel tableViewModel;
    private final boolean isFunctioDefault;
    
    /**
     * Define um formatador para arquivos de ponto flutuante.
     * 
     * @param tableViewModel
     * @param fl 
     */
    public FloatTextFieldCellFactory(TableViewModel tableViewModel, FloatString fl, boolean isFunctioDefault) {
        this.format = fl;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }
    
    public FloatTextFieldCellFactory(TableViewModel tableViewModel, FloatString fl) {
        this.format = fl;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }
    
    public FloatTextFieldCellFactory(TableViewModel tableViewModel, FloatString fl, TextAlignment textAlignment, boolean isFunctioDefault) {
        this.format = fl;
        this.textAlignment = textAlignment;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }
    
    public FloatTextFieldCellFactory(TableViewModel tableViewModel, FloatString fl, TextAlignment textAlignment) {
        this.format = fl;
        this.textAlignment = textAlignment;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }
    
    @Override
    public TextFieldCell call(TableColumn tc) {
        TextFieldCell cell = new TextFieldCell(tableViewModel, format, textAlignment, isFunctioDefault);
        //Aplica no TextField o formatador do campo
        FloatTextInputControl.apply(cell.getTextField(), (FloatString) format);
        
        return cell;
    }
}
