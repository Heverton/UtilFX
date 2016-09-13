package com.utilfx.components.tableview.cellvaluefactory;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import com.utilfx.components.util.CellReflection;
import util.string.FormatStringInterface;

/**
 * Cria um objeto para controlar a atribuição de valor em colunas.
 * 
 * @author Heverton Cruz
 * @version 1.0
 */
public class CellValueFactory implements Callback<TableColumn.CellDataFeatures, ObservableValue> {

    protected String projetion;
    protected FormatStringInterface format = null;
    
    public CellValueFactory(String projetion) {
        this.projetion = projetion;
    }
    
    public CellValueFactory(String projetion, FormatStringInterface format) {
        this(projetion);
        this.format = format;
    }
    
    @Override
    public ObservableValue call(TableColumn.CellDataFeatures cell) {
        //Se não tiver um formatador
        if(format == null) {
            return new SimpleObjectProperty(CellReflection.getValuePojo(cell.getValue(), projetion));
        } else {
            return new SimpleObjectProperty(format.apply(CellReflection.getValuePojo(cell.getValue(), projetion).toString()));
        
        }
    }

    /**
     * Pega o formatador definido.
     * 
     * @return 
     */
    public FormatStringInterface getFormat() {
        return format;
    }
}
