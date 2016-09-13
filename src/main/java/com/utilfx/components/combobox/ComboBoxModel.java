package com.utilfx.components.combobox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import com.utilfx.components.combobox.cellfactory.CellFactory;
import com.utilfx.components.combobox.stringconverter.CellStringConverter;
import com.utilfx.components.textinputcontrol.FormatTextInputControlInterface;
import com.utilfx.components.combobox.listener.AutoCompListenerConverter;

/**
 * Abstrai a implantação das configurações padrões de um ComboBox para receber
 * dados de objetos VO gerados pelo NetBeans.
 *
 * @author Heverton Cruz
 * @version 1.1
 */
public class ComboBoxModel {

    private ComboBoxModel() {
    }

    /**
     * Faz as configurações padrões para um ComboBox.
     *
     * @param projetion
     * @param comboBox
     * @param items
     * @return
     */
    private static ComboBox apply(String projetion, final ComboBox comboBox, ObservableList items) {
        comboBox.getItems().clear();
        comboBox.setItems(items);
        comboBox.setConverter(new CellStringConverter(projetion));
        comboBox.setCellFactory(new CellFactory(projetion));
        return comboBox;
    }

    /**
     * Faz as configurações padrões para um ComboBox.
     *
     * @param projetion
     * @param comboBox
     * @param items
     * @return
     */
    private static ComboBox applyEdit(String projetion, final ComboBox comboBox, ObservableList items) {
        comboBox.getItems().clear();
        comboBox.setItems(items);
        comboBox.setConverter(new CellStringConverter(projetion));
        comboBox.setCellFactory(new CellFactory(projetion));
        // Metodos para auto completar
        comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                comboBox.hide();
            }
        });
        comboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                comboBox.setEditable(true);
            }
        });
        comboBox.setOnKeyReleased(new AutoCompListenerConverter<>(comboBox, items, projetion));

        return comboBox;
    }

    /**
     * Faz as configurações padrões para um ComboBox editável.
     *
     * @param projetion
     * @param comboBox
     * @param items
     * @return
     */
    private static ComboBox applyEditable(String projetion, ComboBox comboBox, ObservableList items) {
        comboBox = applyEdit(projetion, comboBox, items);
        return comboBox;
    }

    /**
     * Faz as configurações padrões para um ComboBox simples.
     *
     * @param projetion
     * @param comboBox
     */
    public static void simple(String projetion, ComboBox comboBox) {
        simple(projetion, comboBox, FXCollections.observableArrayList());
    }

    /**
     * Faz as configurações padrões para um ComboBox simples.
     *
     * @param projetion
     * @param comboBox
     * @param items
     */
    public static void simple(String projetion, ComboBox comboBox, ObservableList items) {
        apply(projetion, comboBox, items);
    }

    /**
     * Faz as configurações padrões para um ComboBox editável.
     *
     * @param projetion
     * @param comboBox
     */
    public static void editable(String projetion, ComboBox comboBox) {
        editable(projetion, comboBox, FXCollections.observableArrayList());
    }

    /**
     * Faz as configurações padrões para um ComboBox e permite adicionar dados
     * ao ComboBox.
     *
     * @param projetion
     * @param comboBox
     * @param items
     */
    public static void editable(String projetion, ComboBox comboBox, ObservableList items) {
        applyEditable(projetion, comboBox, items);
    }

    /**
     * Faz as configurações padrões para um ComboBox. Permite inserir um
     * tratamento para os dados.
     *
     * @param projetion
     * @param comboBox
     * @param textInputControlInterface
     */
    public static void editable(String projetion, ComboBox comboBox, FormatTextInputControlInterface textInputControlInterface) {
        editable(projetion, comboBox, FXCollections.observableArrayList(), textInputControlInterface);
    }

    /**
     * Faz as configurações padrões para um ComboBox e permite adicionar dados
     * ao ComboBox. Permite inserir um filtro de dados.
     *
     * @param projetion
     * @param comboBox
     * @param items
     * @param textInputControlInterface
     */
    public static void editable(String projetion, ComboBox comboBox, ObservableList items, FormatTextInputControlInterface textInputControlInterface) {
        //Aplica o filtro definido para o ComboBox
        textInputControlInterface.assign(applyEditable(projetion, comboBox, items).getEditor());
    }
}
