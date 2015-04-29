package br.com.utilfx.components.tableview.cellfactory;

import javafx.scene.control.TableColumn;
import javafx.scene.text.TextAlignment;
import br.com.util.string.DateString;
import br.com.utilfx.components.tableview.TableViewModel;
import br.com.utilfx.components.tableview.cell.TextFieldCell;
import br.com.utilfx.components.textinputcontrol.DateTextInputControl;

/**
 * Cria um objeto para controlar a célula das colunas que possuirem o objeto
 * TextField com filtro de caracteres.
 *
 * @author Herberts Cruz
 * @version 1.1
 */
public class DateTextFieldCellFactory extends TextFieldCellFactory {

    private final TableViewModel tableViewModel;
    private final boolean isFunctioDefault;
    
    public DateTextFieldCellFactory(TableViewModel tableViewModel, DateString dtString, boolean isFunctioDefault) {
        this.format = dtString;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }
    
    public DateTextFieldCellFactory(TableViewModel tableViewModel, DateString dtString) {
        this.format = dtString;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }
    
    public DateTextFieldCellFactory(TableViewModel tableViewModel, DateString dtString, TextAlignment textAlignment, boolean isFunctioDefault) {
        this.format = dtString;
        this.textAlignment = textAlignment;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }
    
    public DateTextFieldCellFactory(TableViewModel tableViewModel, DateString dtString, TextAlignment textAlignment) {
        this.format = dtString;
        this.textAlignment = textAlignment;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }

    @Override
    public TextFieldCell call(TableColumn tc) {
        TextFieldCell cell = new TextFieldCell(tableViewModel, format, textAlignment, isFunctioDefault);
        //Aplica no TextField o formatador do campo
        DateTextInputControl.apply(cell.getTextField(), (DateString) format);

        return cell;
    }
}
