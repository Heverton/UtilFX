package br.com.utilfx.components.tableview.cell;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import br.com.utilfx.components.tableview.TableViewModel;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.DEFAULT;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.EDITCELL;

/**
 * Cria um TableCell que possue um objeto CheckBox com o valor boolean do
 * atributo do objeto da linha.
 *
 * @author Herberts Cruz
 * @version 1.0
 * @param <S>
 * @param <T>
 */
public class CheckBoxCell<S, T> extends TableCellFX<S, T> {

    /**
     * Armazena o único objeto CheckBox da célula.
     */
    private final CheckBox checkBox;
    private final TableViewModel model;

    public CheckBoxCell(final TableViewModel model) {
        this(model, true, false);
    }

    public CheckBoxCell(final TableViewModel model, boolean isFunctioDefault) {
        this(model, isFunctioDefault, false);
    }

    private CheckBoxCell(final TableViewModel model, boolean isFunctioDefault, boolean bs) {
        this.model = model;
        this.checkBox = new CheckBox();
        //Centraliza o conteúdo do objeto CheckBox
        checkBox.setAlignment(Pos.CENTER);
        //Centraliza objeto CheckBox na célula
        setAlignment(Pos.CENTER);
        //Exibe o objeto CheckBox
        setGraphic(checkBox);

        switch (model.getTipo()) {
            case DEFAULT:
                //Define chekbox como não editável
                checkBox.setDisable(true);
                break;
            case EDITCELL:
                if (isFunctioDefault) {
                    checkBox.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            requestFocus();
                        }
                    });
                    if (model.getOnKeyEventColumns() != null) {
                        checkBox.addEventFilter(KeyEvent.KEY_PRESSED, model.getOnKeyEventColumns());
                    }
                    checkBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent t) {
                            if (t.getCode() == KeyCode.ENTER) {
                                //avança para a proxima célula
                                model.selectCelNext();
                            } else if (t.getCode() == KeyCode.ESCAPE) {
                                cancelEdit();
                            }
                        }
                    });
                }
                break;
        }

    }

    @Override
    public void startEdit() {
        super.startEdit();

    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            //Oculta o texto da célula se houver
            setText(null);
            //Oculta o objeto CheckBox
            setGraphic(null);
        } else {
            //Marca ou desmarca a Checkbox
            checkBox.setSelected(Boolean.parseBoolean(item.toString()));
            //Oculta o combobox da célula
            setGraphic(checkBox);
        }
    }

    /**
     * Retorna o objeto de edição.
     *
     * @return CheckBox
     */
    public CheckBox getCheckBox() {
        return checkBox;
    }

    @Override
    public Node getNode() {
        return checkBox;
    }
}
