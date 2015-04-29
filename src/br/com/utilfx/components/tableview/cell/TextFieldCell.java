package br.com.utilfx.components.tableview.cell;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import br.com.util.string.FloatString;
import br.com.util.string.FormatStringInterface;
import br.com.util.string.ManipuleString;
import br.com.utilfx.components.tableview.TableViewModel;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.DEFAULT;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.EDITCELL;

/**
 * Cria um TableCell que possue um objeto TextField que aparece quando se tenta
 * editar e oferece a oportunidade de filtrar os caracteres inseridos.
 *
 * @author Herberts Cruz
 * @version 1.0
 *
 * @param <S>
 * @param <T>
 */
public class TextFieldCell<S, T> extends TableCellFX<S, T> {

    private TextField textField = null;
    //Pega a posição posterior da célula que acabou de ganhar o foco da seleção
    private final TableViewModel model;

    public TextFieldCell(final TableViewModel model) {
        this(model, null, null, false, true);
    }

    public TextFieldCell(final TableViewModel model, boolean isFunctioDefault) {
        this(model, null, null, false, false, isFunctioDefault);
    }

    public TextFieldCell(final TableViewModel model, final FormatStringInterface format) {
        this(model, format, null, false, true);
    }

    public TextFieldCell(final TableViewModel model, final FormatStringInterface format, boolean isFunctioDefault) {
        this(model, format, null, false, isFunctioDefault);
    }

    public TextFieldCell(final TableViewModel model, final FormatStringInterface format, final TextAlignment textAlignment) {
        this(model, format, textAlignment, false, true);
    }

    public TextFieldCell(final TableViewModel model, final FormatStringInterface format, final TextAlignment textAlignment, boolean isLabel) {
        this(model, format, textAlignment, isLabel, false, true);
    }

    public TextFieldCell(final TableViewModel model, final FormatStringInterface format, final TextAlignment textAlignment, boolean isLabel, boolean isFunctioDefault) {
        this(model, format, textAlignment, isLabel, false, isFunctioDefault);
    }

    private TextFieldCell(final TableViewModel model, final FormatStringInterface format, final TextAlignment textAlignment, boolean isLabel, boolean bs, boolean isFunctioDefault) {

        this.model = model;
        this.textField = new TextField();
        
        // Defini que o campo será um textfield inátivo 
        if (isLabel) {
            // Utilizado apenas para campos de label
            this.textField.setEditable(false);
            this.textField.setDisable(true);
        }

        //Seta o valor inicial do campo
        this.textField.setText(getString());

        //Realiza o alinhamento do campo de texto
        textAling(textAlignment);

        this.textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        switch (model.getTipo()) {
            case DEFAULT:
                break;
            case EDITCELL:
                //Define que não utilizara a função default do componente
                if (isFunctioDefault) {
                    if (model.getOnKeyEventColumns() != null) {
                        this.textField.addEventFilter(KeyEvent.KEY_PRESSED, model.getOnKeyEventColumns());
                    }
                }
                break;
        }

        //Define que não utilizara a função default do componente
        if (isFunctioDefault) {
            this.textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == keyConfirm) {
                        commitValue(format, textField);

                        switch (model.getTipo()) {
                            case DEFAULT:
                                //Define como não editável
                                textField.setDisable(true);
                                break;
                            case EDITCELL:
                                //avança para a proxima célula
                                model.selectCelNext();
                                break;
                        }
                    } else if (t.getCode() == keyCancel) {
                        cancelEdit();
                    }
                }
            });

            this.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    switch (model.getTipo()) {
                        case DEFAULT:
                            break;
                        case EDITCELL:
                            //Pega a posição atual da célula que possui o foco da seleção
                            TablePosition position = (TablePosition) model.getTableView().getSelectionModel().getSelectedCells().iterator().next();
                            model.getTableView().edit(position.getRow(), position.getTableColumn());
                            break;
                    }
                }
            });
        }
    }

    /**
     * Realiza o alinhamento do campo de texto
     *
     * @param textAlignment
     */
    private void textAling(final TextAlignment textAlignment) {
        // Seta o alinhamento na celular e no textfield
        if (textAlignment != null) {

            switch (textAlignment) {
                case CENTER:
                    this.setTextAlignment(TextAlignment.CENTER);
                    this.setAlignment(Pos.BASELINE_CENTER);
                    break;
                case JUSTIFY:
                    this.setTextAlignment(TextAlignment.JUSTIFY);
                    this.setAlignment(Pos.BASELINE_CENTER);
                    break;
                case LEFT:
                    this.setTextAlignment(TextAlignment.LEFT);
                    this.setAlignment(Pos.BASELINE_LEFT);
                    break;
                case RIGHT:
                    this.setTextAlignment(TextAlignment.RIGHT);
                    this.setAlignment(Pos.BASELINE_RIGHT);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        //Insere o conteúdo no TextField
        textField.setText(getString());
        //Oculta o texto exibido
        setText(null);
        //Exibe o TextField na célula
        setGraphic(textField);
        //Seta o foco
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                textField.requestFocus();
            }
        });
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        //Apaga o conteúdo do TextField
        textField.clear();
        //Exibe o texto que estava sendo exibido antes de habilitar a edição
        setText(getString());
        //Oculta o TextField da célula
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            //Oculta o texto exibido
            setText(null);
            //Oculta o TextField da célula
            setGraphic(null);
        } else {
            //Se estiver em edição
            if (isEditing()) {
                //Adiciona o novo texto inserido no TextField
                textField.setText(getString());
                //Oculta o texto exibido
                setText(null);
                //Exibe o TextField na célula
                setGraphic(textField);
            } else {
                //Exibe o novo texto inserido
                setText(getString());
                //Oculta o TextField da célula
                setGraphic(null);
            }
        }
    }

    /**
     * Transforma os valores de item nulos em vazio.
     *
     * @return String
     */
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    @Override
    public Node getNode() {
        return textField;
    }

    /**
     * Retorna o objeto de edição.
     *
     * @return TextField
     */
    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    /**
     * Seta uma nova tecla para confirmação da edição, a tecla padrão é o ENTER.
     *
     * @param keyConfirm
     */
    public void setKeyConfirm(KeyCode keyConfirm) {
        this.keyConfirm = keyConfirm;
    }

    /**
     * Seta uma nova tecla para cancelamento da edição, a tecla padrão é o
     * ESCAPE.
     *
     * @param keyCancel
     */
    public void setKeyCancel(KeyCode keyCancel) {
        this.keyCancel = keyCancel;
    }

    public void commitValue(FormatStringInterface format, TextField textField) {
        //A valicação foi feita internamente pois 
        //a formatação não estava sendo executada com o clique do enter
        if (format instanceof FloatString) {
            //Define as variáveis
            String newTextSuffix = "";
            String newTextPrefix = "";

            //Pega a posição do ponto
            int positionVirgula = textField.getText().indexOf(",");

            if (positionVirgula < 0) {
                newTextPrefix = textField.getText();
                newTextSuffix = "";

            } else {
                newTextPrefix = textField.getText().substring(0, positionVirgula) + "";
                newTextSuffix = textField.getText().substring(positionVirgula + 1, textField.getText().length()) + "";
            }

            //Se o tamanho do prefixo for maior que a escala desejada
            newTextSuffix = ManipuleString.rpad(newTextSuffix, "0", ((FloatString) format).getScale());

            textField.setText(newTextPrefix + "," + newTextSuffix);

        }
        commitEdit((T) ((format == null) ? textField.getText() : format.apply(textField.getText())));
    }
}
