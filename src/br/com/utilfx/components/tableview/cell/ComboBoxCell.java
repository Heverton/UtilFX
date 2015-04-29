package br.com.utilfx.components.tableview.cell;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TablePosition;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import br.com.utilfx.components.tableview.TableViewModel;
import br.com.utilfx.components.util.CellReflection;

/**
 * Cria um TableCell que possue um objeto ComboBox que aparece quando se tenta
 * editar.
 *
 * @author Herberts Cruz
 * @version 1.0
 */
public class ComboBoxCell<S, T> extends TableCellFX<S, T> {

    /**
     * Armazena a variável de navegação dos pojo.
     */
    private final String projetion;
    /**
     * Armazena o único ComboBox da célula.
     */
    private final ComboBox comboBox;
    private final TableViewModel model;

    public ComboBoxCell(final TableViewModel model, final String projetion, final ObservableList items) {
        this(model, projetion, items, true, false);
    }

    public ComboBoxCell(final TableViewModel model, final String projetion, final ObservableList items, boolean isFunctioDefault) {
        this(model, projetion, items, isFunctioDefault, false);
    }

    private ComboBoxCell(final TableViewModel model, final String projetion, final ObservableList items, final boolean isFunctioDefault, boolean bs) {
        this.model = model;
        this.projetion = projetion;
        this.comboBox = new ComboBox(items);

        //Define o tamanho do ComboBox
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        switch (model.getTipo()) {
            case DEFAULT:
                break;
            case EDITCELL:
                if (isFunctioDefault) {
                    if (model.getOnKeyEventColumns() != null) {
                        comboBox.addEventFilter(KeyEvent.KEY_PRESSED, model.getOnKeyEventColumns());

                    }
                }
                break;
        }

        //Define o evento KeyReleased
        comboBox.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    //Cancela a edição
                    cancelEdit();
                } else if (t.getCode() == KeyCode.ENTER) {
                    Object string = null;
                    switch (model.getTipo()) {
                        case DEFAULT:
                            //Define como não editável
                            comboBox.setDisable(true);
                            break;
                        case EDITCELL:
                            //Busca o nome para visualização
                            string = CellReflection.getValuePojo(comboBox.getSelectionModel().getSelectedItem(), projetion);
                            //Atualiza o objeto da célula
                            commitEdit((T) comboBox.getSelectionModel().getSelectedItem());
                            //Seta o valor de exibição
                            setText(string.toString());
                            //Oculta o combobox da célula
                            setGraphic(null);

                            if (isFunctioDefault) {
                                //avança para a proxima célula
                                model.selectCelNext();
                            }
                            break;
                    }

                }
            }
        });

        comboBox.setOnHiding(new EventHandler() {
            @Override
            public void handle(Event t) {
                Object string = null;
                switch (model.getTipo()) {
                    case DEFAULT:
                        //Define como não editável
                        comboBox.setDisable(true);
                        break;
                    case EDITCELL:
                        //Busca o nome para visualização
                        string = CellReflection.getValuePojo(comboBox.getSelectionModel().getSelectedItem(), projetion);
                        //Atualiza o objeto da célula
                        commitEdit((T) comboBox.getSelectionModel().getSelectedItem());
                        //Seta o valor de exibição
                        setText(string.toString());
                        //Oculta o combobox da célula
                        setGraphic(null);
                        break;
                }
            }
        });
    }

    @Override
    public void startEdit() {
        super.startEdit();
        //Seleciona o item do ComboBox que equivale ao valor visualizado
        comboBox.getSelectionModel().select(comboBox.getItems().indexOf(getObject()));
        //Oculta o texto exibido
        setText(null);
        //Exibe o combobox na célula
        setGraphic(comboBox);
        //Seta o foco
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                comboBox.requestFocus();
            }
        });
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        //Ajusta e seta o valor do campo
        ajusteValue();
        //Oculta o combobox da célula
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            //Oculta o texto da célula se houver
            setText(null);
            //Oculta o combobox da célula
            setGraphic(null);
        } else {
            //Se estiver em edição
            if (isEditing()) {
                //Seleciona o item do ComboBox que equivale ao valor visualizado
                comboBox.getSelectionModel().select(comboBox.getItems().indexOf(getObject()));
                //Ajusta e seta o valor do campo
                ajusteValue();
                //Exibe o combobox na célula
                setGraphic(comboBox);
                // Define para forma de dados iniciais
            } else {
                //Seta valor dos dados
                setText(getStringObjetc());
                //Oculta o combobox da célula
                setGraphic(null);
            }
        }
    }

    /**
     * Retorna o objeto ComboBox da célula.
     *
     * @return ComboBox
     */
    public ComboBox getComboBox() {
        return comboBox;
    }

    private String getStringObjetc() {
        //Busca o nome para visualização a primeira posição
        return (String) CellReflection.getValuePojo(getItem(), projetion);
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
        return comboBox;
    }

    private void ajusteValue() {

        Object dados = ((getItem() == null) ? null : getItem());

        //Verificar se objeto é nulo
        if (dados != null) {
            //Busca o nome para visualização
            Object string = CellReflection.getValuePojo(dados, projetion);

            //Exibe o texto que estava sendo exibido antes de habilitar a edição
            setText((string != null) ? string.toString() : "");
        }
    }

    /**
     * Pega o objeto que tem o valor da célula.
     *
     * @return Object
     */
    private Object getObject() {
        //Navega entre os itens da ComboBox
        for (Object o : comboBox.getItems()) {

            Object value = CellReflection.getValuePojo(o, projetion);

            //Se o valor da célula for igual ao valor do campo no objeto
            if (!(getString().equals(value.toString()))) {
                return o;
            }
        }

        return null;
    }

}
