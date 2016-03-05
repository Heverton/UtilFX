package br.com.utilfx.components.tableview.cellfactory;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import br.com.utilfx.components.combobox.cellfactory.CellFactory;
import br.com.utilfx.components.combobox.stringconverter.CellStringConverter;
import br.com.utilfx.components.tableview.TableViewModel;
import br.com.utilfx.components.tableview.cell.ComboBoxCell;

/**
 * Cria um objeto para controlar a célula das colunas que possuirem 
 * o objeto ComboBox.
 * 
 * @author Heverton Cruz
 * @version 1.0
 */
public class ComboBoxCellFactory implements Callback<TableColumn, TableCell> {
    
    private final String projetion;
    private final ObservableList data;
    private final TableViewModel tableViewModel;
    private final boolean isFunctioDefault;

    public ComboBoxCellFactory(TableViewModel tableViewModel, String projetion, ObservableList data, boolean isFunctioDefault) {
        this.projetion = projetion;
        this.data = data;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;        
    }
    
    public ComboBoxCellFactory(TableViewModel tableViewModel, String projetion, ObservableList data) {
        this.projetion = projetion;
        this.data = data;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;        
    }
    
    @Override
    public ComboBoxCell call(TableColumn tc) {
        ComboBoxCell cell = new ComboBoxCell(tableViewModel, projetion, data, isFunctioDefault);
        //Seta o objeto StringConverter
        cell.getComboBox().setConverter(new CellStringConverter(projetion));
        //Seta o factory especial para ComboBoxes
        cell.getComboBox().setCellFactory(new CellFactory(projetion));
        
        return cell;
    }
}
