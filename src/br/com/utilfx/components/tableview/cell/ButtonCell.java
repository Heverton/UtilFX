package br.com.utilfx.components.tableview.cell;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import br.com.utilfx.components.tableview.TableViewModel;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.DEFAULT;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.EDITCELL;

/**
 * Cria um TableCell que possue um objeto Button com o ID do objeto da linha.
 *
 * @author Herberts Cruz
 * @version 1.0
 *
 * @param <S>
 * @param <T>
 */
public class ButtonCell<S, T> extends TableCellFX<S, T> {

    /**
     * Armazena o único objeto Button da célula.
     */
    private final Button button;
    private final TableViewModel model;

    public ButtonCell(TableViewModel model) {
        this(model, "", true, false);
    }

    public ButtonCell(TableViewModel model, boolean isFunctioDefault) {
        this(model, "", isFunctioDefault, false);
    }

    public ButtonCell(TableViewModel model, String label) {
        this(model, label, true, false);
    }

    public ButtonCell(TableViewModel model, String label, boolean isFunctioDefault) {
        this(model, label, isFunctioDefault, false);
    }

    public ButtonCell(final TableViewModel model, String label, boolean isFunctioDefault, boolean bs) {
        this.model = model;
        button = new Button(label);
        //Centraliza o conteúdo do objeto Button
        button.setAlignment(Pos.CENTER);
        //Centraliza objeto Button na célula
        setAlignment(Pos.CENTER);
        //Exibe o objeto Button
        setGraphic(button);

        switch (model.getTipo()) {
            case DEFAULT:
                //Define como não editável
                button.setDisable(true);
                break;
            case EDITCELL:
                if (isFunctioDefault) {
                    button.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            requestFocus();
                        }
                    });

                    button.setOnKeyReleased(new EventHandler<KeyEvent>() {
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
        super.startEdit();//Pega a posição atual da célula que possue o foco da seleção

//        switch (model.getTipo()) {
//            case DEFAULT:
//                break;
//            case EDITCELL:
//                //avança para a proxima célula
//                model.selectCelNext();
//                break;
//        }
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            //Oculta o texto da célula se houver
            setText(null);
            //Oculta o objeto Button
            setGraphic(null);
        } else {
            //Exibe o objeto Button
            setGraphic(button);
        }
    }

    /**
     * Retorna o objeto de edição.
     *
     * @return Button
     */
    public Button getButton() {
        return button;
    }

    @Override
    public Node getNode() {
        return button;
    }
}
