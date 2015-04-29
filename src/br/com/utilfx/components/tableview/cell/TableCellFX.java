/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.utilfx.components.tableview.cell;

import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Heverton Cruz
 * 
 * @param <S>
 * @param <T>
 */
public abstract class TableCellFX<S, T> extends TableCell<S, T> {
    
    public KeyCode keyConfirm = KeyCode.ENTER;
    public KeyCode keyCancel = KeyCode.ESCAPE;
    
    public abstract Node getNode();
}
