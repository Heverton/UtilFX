package br.com.utilfx.components.tableview.cellfactory;

import javafx.scene.control.TableColumn;
import javafx.scene.text.TextAlignment;
import br.com.util.string.MaskString;
import br.com.utilfx.components.tableview.TableViewModel;
import br.com.utilfx.components.tableview.cell.TextFieldCell;
import br.com.utilfx.components.textinputcontrol.MaskTextInputControl;

/**
 * Cria um objeto para controlar a célula das colunas que possuirem 
 * o objeto TextField com filtro de caracteres.
 * 
 * @author Heverton Cruz
 * @version 1.1
 */
public class MaskTextFieldCellFactory extends TextFieldCellFactory {
    
    private final TableViewModel tableViewModel;
    private final boolean isFunctioDefault;
    
    public MaskTextFieldCellFactory(TableViewModel tableViewModel, MaskString mask, boolean isFunctioDefault) {
        this.format = mask;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }
    
    public MaskTextFieldCellFactory(TableViewModel tableViewModel, MaskString mask) {
        this.format = mask;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }
        
    public MaskTextFieldCellFactory(TableViewModel tableViewModel, MaskString mask, TextAlignment textAlignment, boolean isFunctioDefault) {
        this.format = mask;
        this.textAlignment = textAlignment;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }
    
    public MaskTextFieldCellFactory(TableViewModel tableViewModel, MaskString mask, TextAlignment textAlignment) {
        this.format = mask;
        this.textAlignment = textAlignment;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }
    
    @Override
    public TextFieldCell call(TableColumn tc) {
        TextFieldCell cell = new TextFieldCell(tableViewModel, format, textAlignment, isFunctioDefault);
        //Aplica no TextField o formatador do campo
        MaskTextInputControl.apply(cell.getTextField(), (MaskString) format);
        
        return cell;
    }
}
