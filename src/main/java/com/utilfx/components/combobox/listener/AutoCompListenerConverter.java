/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilfx.components.combobox.listener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.utilfx.components.util.CellReflection;

/**
 * Auto completar da combobox
 *
 * @author Heverton Cruz
 * @version 1.0
 * @param <T>
 */
public class AutoCompListenerConverter<T> implements EventHandler<KeyEvent> {

    private final ComboBox comboBox;
    private ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;
    private final String projetion;

    public AutoCompListenerConverter(ComboBox comboBox, ObservableList<T> list, String projetion) {
        this.projetion = projetion;
        this.comboBox = comboBox;
        this.data = list;
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            // Verificar se a prancha está visível
            if (!comboBox.isShowing()) {
                // Mostrar a prancha que é exibina na compo com a lista de itens 
                comboBox.show();
            }
            //Seta última posição para exibir todas
            caretPos = -1;
            //Mover caracteres para a última posição
            moveCareteres(comboBox.getEditor().getText().length());
            //Retorno
            return;
        } else if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
            // Define que é para continuar e aceitar apagar
            moveCaretToPos = true;
            //Seta a posição do proximo caractere
            caretPos = comboBox.getEditor().getCaretPosition();
        }
        
        //Escapa teclas que não serão utilizadas
        if (event.isControlDown() || event.isShiftDown()
                || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END
                || event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.INSERT) {
            return;
        }

        //Cria uma instância
        ObservableList list = FXCollections.observableArrayList();
        //Realizar o filtro
        for (int i = 0; i < data.size(); i++) {
            //Pega o valor "projetion" para uma futura verificação 
            String strAux = CellReflection.getValuePojo(data.get(i), projetion).toString();
            //Realizar a comparação
            if (strAux.toLowerCase().startsWith(AutoCompListenerConverter.this.comboBox.getEditor().getText().toLowerCase())) {
                //Nova lista filtrada
                list.add(data.get(i));
            }
        }

        //Pega o texto
        String t = comboBox.getEditor().getText();
        //Seta os dados da nova lista
        comboBox.setItems(list);
        // Verificar se é para movimentar para a última ou continuar
        if (!moveCaretToPos) {
            caretPos = -1;
        }
        //Move para a última posição
        moveCareteres(t.length());
        //Caso não esteja vazio mostrar a prancha
        if (!list.isEmpty()) {
            comboBox.show();
        }
    }

    /**
     * Realiza a movimentação
     *
     * @param textLength
     */
    private void moveCareteres(int textLength) {
        if (caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }

}
