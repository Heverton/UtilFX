package br.com.utilfx.components.tableview.cell;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import br.com.utilfx.components.tableview.TableViewModel;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.DEFAULT;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.EDITCELL;

/**
 * Cria um TableCell que possue um objeto Button com o ID do objeto da linha.
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class ButtonWithTextfieldCell<S, T> extends TableCellFX<S, T> {

    /**
     * Armazena o único objeto Button da célula.
     */
    private final Button button;
    private final TextField textField;
    private KeyCode keyConfirm = KeyCode.ENTER;
    private KeyCode keyCancel = KeyCode.ESCAPE;
    private final TableViewModel model;

    public ButtonWithTextfieldCell(TableViewModel model) {
        this(model, "", true, false);
    }
    
    public ButtonWithTextfieldCell(TableViewModel model, boolean isFunctioDefault) {
        this(model, "", isFunctioDefault, false);
    }
    
    //Pega a posição posterior da célula que acabou de ganhar o foco da seleção
    public ButtonWithTextfieldCell(TableViewModel model, String label) {
        this(model, label, true, false);
    }
    
    //Pega a posição posterior da célula que acabou de ganhar o foco da seleção
    public ButtonWithTextfieldCell(TableViewModel model, String label, boolean isFunctioDefault) {
        this(model, label, isFunctioDefault, false);
    }

    private ButtonWithTextfieldCell(final TableViewModel model, String label, boolean isFunctioDefault, boolean bs) {
        this.model = model;
    
        textField = new TextField();
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        button = new Button(label);
        //Centraliza o conteúdo do objeto Button
        button.setAlignment(Pos.CENTER);
        //Centraliza objeto Button na célula
        this.setAlignment(Pos.CENTER);
        //Exibe o objeto Button
        button.setGraphic(textField);

        this.setGraphic(button);

        switch (model.getTipo()) {
            case DEFAULT:
                //Define como não editável
                button.setDisable(true);
                textField.setDisable(true);
                break;
            case EDITCELL:
                //Define que não utilizara a função default do componente
                if (isFunctioDefault) {
                    button.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            requestFocus();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();//Pega a posição atual da célula que possue o foco da seleção

        switch (model.getTipo()) {
            case DEFAULT:
                break;
            case EDITCELL:
                //Habiltar o foco na area de texto
                textField.setFocusTraversable(true);
                //Requisitar o foco
                textField.requestFocus();

                //------Codigo removido-------------
                //avança para a proxima célula
                //model.selectCelNext();
                //----------------------------------
                break;
        }
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

    public TextField getTextField() {
        return textField;
    }

    @Override
    public Node getNode() {
        return button;
    }
}
