package br.com.utilfx.components.tableview.cellfactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import br.com.utilfx.components.tableview.TableViewModel;
import br.com.utilfx.components.tableview.cell.CheckBoxCell;
import br.com.utilfx.components.util.CellReflection;

/**
 * Cria um objeto para controlar a célula das colunas que possuirem 
 * o objeto CheckBox.
 * 
 * @author Heverton Cruz
 * @version 1.0
 */
public class CheckBoxCellFactory implements Callback<TableColumn, TableCell> {

    private String projetion;
    private final TableViewModel tableViewModel;
    private final boolean isFunctioDefault;
    
    public CheckBoxCellFactory(TableViewModel tableViewModel, String projetion, boolean isFunctioDefault) {
        this.projetion = projetion;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }
    
    public CheckBoxCellFactory(TableViewModel tableViewModel, String projetion) {
        this.projetion = projetion;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }
    
    @Override
    public CheckBoxCell call(TableColumn tc) {
        final CheckBoxCell cell = new CheckBoxCell(tableViewModel, isFunctioDefault);
        
        //Aplica a atualização do pojo ao selecionar o objeto
        cell.getCheckBox().selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean oldValue, Boolean newValue) {
                //Seta a alteração no pojo
                CellReflection.setValuePojo(
                        cell.getTableView().getItems().get(cell.getIndex()), 
                        projetion, new Object[]{newValue});
            }
        });

        return cell;
    }
}
