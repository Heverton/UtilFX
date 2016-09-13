package com.utilfx.components.tableview.cellfactory;

import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import com.utilfx.components.tableview.cell.ButtonWithTextfieldCell;

/**
 * Cria um objeto para controlar a célula das colunas que possuirem 
 * o objeto CheckBox.
 * 
 * @author Heverton Cruz
 * @version 1.0
 */
public abstract class ButtonWithTextfieldCellFactory implements Callback<TableColumn, ButtonWithTextfieldCell> {
}
