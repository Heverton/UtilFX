package br.com.utilfx.components.tableview.cellfactory;

import javafx.scene.control.TableColumn;
import javafx.scene.text.TextAlignment;
import br.com.util.string.FilterString;
import br.com.utilfx.components.tableview.TableViewModel;
import br.com.utilfx.components.tableview.cell.TextFieldCell;
import br.com.utilfx.components.textinputcontrol.FilterTextInputControl;

/**
 * Cria um objeto para controlar a célula das colunas que possuirem o objeto
 * TextField com filtro de caracteres.
 *
 * @author Heverton Cruz
 * @version 1.1
 */
public class LabelTextFieldCellFactory extends TextFieldCellFactory {

    private final TableViewModel tableViewModel;
    private final boolean isFunctioDefault;
    
    public LabelTextFieldCellFactory(TableViewModel tableViewModel, FilterString filter, boolean isFunctioDefault) {
        this.format = filter;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }

    public LabelTextFieldCellFactory(TableViewModel tableViewModel, FilterString filter) {
        this.format = filter;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }
    
    public LabelTextFieldCellFactory(TableViewModel tableViewModel, FilterString filter, TextAlignment textAlignment, boolean isFunctioDefault) {
        this.format = filter;
        this.textAlignment = textAlignment;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }
        
    public LabelTextFieldCellFactory(TableViewModel tableViewModel, FilterString filter, TextAlignment textAlignment) {
        this.format = filter;
        this.textAlignment = textAlignment;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }

    
    @Override
    public TextFieldCell call(TableColumn tc) {        
        TextFieldCell cell = new TextFieldCell(tableViewModel, format, textAlignment, isFunctioDefault);
        //Aplica no TextField o formatador do campo
        FilterTextInputControl.apply(cell.getTextField(), (FilterString) format);
        return cell;
    }

    public TextFieldCell addCall(TableColumn tc) {
        return null;
    }

}
