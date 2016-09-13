package com.utilfx.components.tableview;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import com.utilfx.components.HelpForm;
import com.utilfx.components.tableview.cellfactory.ButtonCellFactory;
import com.utilfx.components.tableview.cellfactory.ButtonWithTextfieldCellFactory;
import com.utilfx.components.tableview.cellfactory.CheckBoxCellFactory;
import com.utilfx.components.tableview.cellfactory.ComboBoxCellFactory;
import com.utilfx.components.tableview.cellfactory.ImageViewCellFactory;
import com.utilfx.components.tableview.cellfactory.TextFieldCellFactory;
import com.utilfx.components.tableview.cellvaluefactory.CellValueFactory;
import com.utilfx.components.tableview.editcommit.CellEditCommit;

/**
 * Abstrai a implantação das configurações padrões de um TableView para receber
 * dados de objetos VO gerados pelo NetBeans.
 *
 * @author Heverton Cruz
 * @version 1.1
 */
public class TableViewModel extends TableView {

    /**
     *
     */
    public static enum TipoTableView {

        /**
         *
         */
        DEFAULT,
        /**
         *
         */
        EDITCELL;
    }

    /**
     *
     */
    public static enum DirecionScrollBar {

        /**
         *
         */
        AVANCAR,
        /**
         *
         */
        VOLTAR;
    }

    public static enum ScrollBarMove {

        /**
         *
         */
        HORIZONTAL,
        /**
         *
         */
        VERTICAL;
    }
    
    private double valueIdAnteriorScroll = 0.0;
    private AnimationTimer timerScroll;

    /**
     *
     */
    private int indexColumns;
    /**
     * Index da Coluna
     */
    private int indexCol = 0;

    /**
     *
     */
    private TableView tableView;

    /**
     *
     */
    private TipoTableView tipo;

    /**
     *
     */
    private TablePosition position;

    /**
     *
     */
    private TablePosition positionNext;
    private ScrollBar scrollbarHorizontal;
    private ScrollBar scrollbarVertical;

    private KeyEventColumns onKeyEventColumns;

    public TipoTableView getTipo() {
        return tipo;
    }

    public void setTipo(TipoTableView tipo) {
        this.tipo = tipo;
    }

    public TablePosition getPosition() {
        return position;
    }

    public void setPosition(TablePosition position) {
        this.position = position;
    }

    /**
     *
     * @return
     */
    public TableView getTableView() {
        return tableView;
    }

    /**
     *
     * @param tableView
     */
    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

    /**
     * Cria um TableView com uma lista de resultados vazia e dá a opção de
     * configurar a edição das colunas. O padrão é "true", ou seja, é editável.
     *
     * @param tableView
     * @param tipo
     */
    public TableViewModel(TableView tableView, TipoTableView tipo) {
        this.TableViewModel(tableView, tipo, FXCollections.observableArrayList());
    }

    /**
     * Cria um TableView com a lista de resultados enviada e colunas editáveis.
     *
     * @param tableView
     * @param tipo
     * @param items
     */
    public TableViewModel(TableView tableView, TipoTableView tipo, ObservableList items) {
        this.TableViewModel(tableView, tipo, items);
    }

    /**
     * Cria um TableView com a lista de resultados enviada e dá a opção de
     * configurar a edição das colunas. O padrão é "true", ou seja, é editável.
     *
     * @param tableView
     * @param tipo
     * @param items
     */
    private void TableViewModel(TableView tableView, TipoTableView tipo, ObservableList items) {
        this.tableView = tableView;
        this.tableView.getColumns().clear();
        this.tableView.getItems().clear();
        this.tableView.setItems(items);
        this.tipo = tipo;

        // Tipo de manipulação da cell
        switch (this.tipo) {
            case DEFAULT:
                tableView.setEditable(false);
                tableView.getSelectionModel().setCellSelectionEnabled(false);
                break;
            case EDITCELL:
                tableView.setEditable(true);
                tableView.getSelectionModel().setCellSelectionEnabled(true);
                break;
        }
    }

    /**
     * Aplica a ação há todos os campo da tabela; Deve ser utilizada
     *
     * @param event
     */
    public void setOnKeyEventColumns(final KeyEventColumns event) {
        onKeyEventColumns = event;
    }

    /**
     *
     * @return
     */
    public KeyEventColumns getOnKeyEventColumns() {
        return onKeyEventColumns;
    }

    /**
     * Aplica as definições padrões do TableColumn.
     *
     * @param projetion
     * @param label
     * @param width
     * @param cellFactory
     * @return
     */
    private TableColumn apply(String projetion, String label, int width, Callback cellFactory, int indexTableColumn) {

        TableColumn column;

        // Altera uma colun
        if (indexTableColumn != -1) {
            column = (TableColumn) tableView.getColumns().get(indexTableColumn);
            // Instancia uma nova coluna
        } else {
            column = new TableColumn();
        }

        //Acrescenta mais uma posição
        column.setId(indexCol + "");
        indexCol++;
        column.setText(label);
        column.setMinWidth(width);

        CellValueFactory cellValueFactory = new CellValueFactory(projetion);

        //Se tiver um CellFactory
        if (cellFactory != null) {
            //Se for do tipo TextFieldCellFactory
            if (cellFactory instanceof TextFieldCellFactory) {
                cellValueFactory = new CellValueFactory(projetion, ((TextFieldCellFactory) cellFactory).getFormat());
            }

            column.setCellFactory(cellFactory);
        }

        column.setCellValueFactory(cellValueFactory);

        // Adicionar quando o index foi indefinido
        if (indexTableColumn != -1) {
            //Atualiza a visualização da tabela
            HelpForm.refreshTableColumns(tableView, 0, false);
        } else {
            tableView.getColumns().add(column);
        }

        return column;
    }

    /**
     * Cria uma coluna com objetos Button.
     *
     * @param projetion
     * @param label
     * @param width
     * @param buttonCellFactory
     * @return
     */
    public TableColumn setColumnWithButton(String projetion, String label, int width, ButtonCellFactory buttonCellFactory) {
        TableColumn column = apply(projetion, label, width, buttonCellFactory, -1);
        column.setSortable(false);
        return column;
    }

    /**
     * Realizar a alteração da coluna. Realiza o refresh
     *
     * @param projetion
     * @param label
     * @param width
     * @param buttonCellFactory
     * @param indexTableColumn
     * @return
     */
    public TableColumn alterEditColumnsButton(String projetion, String label, int width, ButtonCellFactory buttonCellFactory, int indexTableColumn) {
        TableColumn column = apply(projetion, label, width, buttonCellFactory, indexTableColumn);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Cria uma coluna com objetos Button.
     *
     * @param projetion
     * @param label
     * @param width
     * @param buttonWithTextfieldCellFactory
     * @return
     */
    public TableColumn setColumnWithButtonWithTextfield(String projetion, String label, int width, ButtonWithTextfieldCellFactory buttonWithTextfieldCellFactory) {
        TableColumn column = apply(projetion, label, width, buttonWithTextfieldCellFactory, -1);
        column.setSortable(false);
        return column;
    }

    /**
     * Realizar a alteração da coluna. Realiza o refresh
     *
     * @param projetion
     * @param label
     * @param width
     * @param buttonWithTextfieldCellFactory
     * @param indexTableColumn
     * @return
     */
    public TableColumn alterEditColumnsButtonWithTextfield(String projetion, String label, int width, ButtonWithTextfieldCellFactory buttonWithTextfieldCellFactory, int indexTableColumn) {
        TableColumn column = apply(projetion, label, width, buttonWithTextfieldCellFactory, indexTableColumn);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Cria uma coluna com objetos CheckBox.
     *
     * @param projetion
     * @param tableViewModel
     * @param label
     * @param width
     * @return
     */
    public TableColumn setColumnWithCheckBox(String projetion, TableViewModel tableViewModel, String label, int width) {
        return setColumnWithCheckBox(projetion, tableViewModel, label, width, true);
    }

    public TableColumn setColumnWithCheckBox(String projetion, TableViewModel tableViewModel, String label, int width, boolean isFunctioDefault) {
        TableColumn column = apply(projetion, label, width, new CheckBoxCellFactory(tableViewModel, projetion, isFunctioDefault), -1);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Realizar a alteração da coluna. Realiza o refresh
     *
     * @param projetion
     * @param tableViewModel
     * @param label
     * @param width
     * @param indexTableColumn
     * @param isFunctioDefault
     * @return
     */
    public TableColumn alterEditColumnsCheckBox(String projetion, TableViewModel tableViewModel, String label, int width, int indexTableColumn, boolean isFunctioDefault) {
        TableColumn column = apply(projetion, label, width, new CheckBoxCellFactory(tableViewModel, projetion, isFunctioDefault), indexTableColumn);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Cria uma coluna com objetos ComboBox que aparecem quando se tenta editar
     * a célula da coluna.
     *
     * @param projetion
     * @param label
     * @param width
     * @param cellFactory
     * @return
     */
    public TableColumn setColumnWithComboBox(String projetion, String label, int width, ComboBoxCellFactory cellFactory) {
        TableColumn column = apply(projetion, label, width, cellFactory, -1);
        column.setOnEditCommit(new CellEditCommit(projetion, true));
        return column;
    }

    /**
     * Realizar a alteração da coluna. Realiza o refresh.
     *
     * @param projetion
     * @param label
     * @param width
     * @param cellFactory
     * @param indexTableColumn
     * @return
     */
    public TableColumn alterEditColumnsWithComboBox(String projetion, String label, int width, ComboBoxCellFactory cellFactory, int indexTableColumn) {
        TableColumn column = apply(projetion, label, width, cellFactory, indexTableColumn);
        column.setOnEditCommit(new CellEditCommit(projetion, true));
        return column;
    }

    /**
     * Cria uma coluna com objetos TextField que aparecem quando se tenta editar
     * a célula da coluna, dando a opção de aplicar utilizar um
     * TextFieldCellFactory personalizado.
     *
     * @param projetion
     * @param label
     * @param width
     * @param cellFactory
     * @return
     */
    public TableColumn setColumnWithTextField(String projetion, String label, int width, TextFieldCellFactory cellFactory) {
        TableColumn column = apply(projetion, label, width, cellFactory, -1);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Realizar a alteração da coluna. Realiza o refresh.
     *
     * @param projetion
     * @param label
     * @param width
     * @param cellFactory
     * @param indexTableColumn
     * @return
     */
    public TableColumn alterEditColumnsWithTextField(String projetion, String label, int width, TextFieldCellFactory cellFactory, int indexTableColumn) {
        TableColumn column = apply(projetion, label, width, cellFactory, indexTableColumn);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Cria uma coluna com objetos Label que aparecem quando se tenta editar a
     * célula da coluna, dando a opção de aplicar utilizar um
     * TextFieldCellFactory personalizado.
     *
     * @param projetion
     * @param label
     * @param width
     * @param cellFactory
     * @return
     */
    public TableColumn setColumnWithLabel(String projetion, String label, int width, TextFieldCellFactory cellFactory) {
        TableColumn column = apply(projetion, label, width, cellFactory, -1);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Realizar a alteração da coluna. Realiza o refresh.
     *
     * @param projetion
     * @param label
     * @param width
     * @param cellFactory
     * @param indexTableColumn
     * @return
     */
    public TableColumn alterEditColumnsWithLabel(String projetion, String label, int width, TextFieldCellFactory cellFactory, int indexTableColumn) {
        TableColumn column = apply(projetion, label, width, cellFactory, indexTableColumn);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Cria uma coluna com objetos ImageView.
     *
     * @param projetion
     * @param tableViewModel
     * @param label
     * @param width
     * @return
     */
    public TableColumn setColumnWithImageView(String projetion, TableViewModel tableViewModel, String label, int width) {
        return setColumnWithImageView(projetion, tableViewModel, label, width, true);
    }

    /**
     * Cria uma coluna com objetos ImageView.
     *
     * @param projetion
     * @param tableViewModel
     * @param label
     * @param width
     * @param isFunctioDefault
     * @return
     */
    public TableColumn setColumnWithImageView(String projetion, TableViewModel tableViewModel, String label, int width, boolean isFunctioDefault) {
        TableColumn column = apply(projetion, label, width, new ImageViewCellFactory(tableViewModel, projetion, isFunctioDefault), -1);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Cria uma coluna com objetos ImageView.
     *
     * @param projetionImage
     * @param projetionLabel
     * @param tableViewModel
     * @param label
     * @param width
     * @param isFunctioDefault
     * @return
     */
    public TableColumn setColumnWithImageView(String projetionImage, String projetionLabel, TableViewModel tableViewModel, String label, int width, boolean isFunctioDefault) {
        TableColumn column = apply(projetionImage, label, width, new ImageViewCellFactory(tableViewModel, projetionImage), -1);
        column.setOnEditCommit(new CellEditCommit(projetionImage));
        return column;
    }

    /**
     * Realizar a alteração da coluna. Realiza o refresh.
     *
     * @param projetion
     * @param tableViewModel
     * @param label
     * @param width
     * @param cellFactory
     * @param indexTableColumn
     * @param isFunctioDefault
     * @return
     */
    public TableColumn alterEditColumnsWithImageView(String projetion, TableViewModel tableViewModel, String label, int width, TextFieldCellFactory cellFactory, int indexTableColumn, boolean isFunctioDefault) {
        TableColumn column = apply(projetion, label, width, new ImageViewCellFactory(tableViewModel, projetion, isFunctioDefault), indexTableColumn);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Cria uma coluna com objetos Label.
     *
     * @param projetion
     * @param label
     * @param width
     * @return
     */
    public TableColumn setColumnWithString(String projetion, String label, int width) {
        TableColumn column = apply(projetion, label, width, null, -1);
        column.setEditable(false);
        return column;
    }

    /**
     * Realizar a alteração da coluna. Realiza o refresh.
     *
     * @param projetion
     * @param label
     * @param width
     * @param indexTableColumn
     * @return
     */
    public TableColumn alterEditColumnsWithString(String projetion, String label, int width, int indexTableColumn) {
        TableColumn column = apply(projetion, label, width, null, indexTableColumn);
        column.setOnEditCommit(new CellEditCommit(projetion));
        return column;
    }

    /**
     * Avança com a barra de rolagem.
     *
     * @param value
     * @param indexCol
     * @param scrollDirect
     * @param scrollBarMove
     */
    public void scrollTo(Number value, int indexCol, DirecionScrollBar scrollDirect, ScrollBarMove scrollBarMove) {
        switch (this.tipo) {
            case DEFAULT:
                //Não realiza estando como default
                break;
            case EDITCELL:
                //Caso a tableview não tenha carregado 
                if (scrollbarHorizontal != null || scrollbarVertical != null) {
                    //Verificar qual barra deve ser movimentada
                    if (scrollBarMove == ScrollBarMove.HORIZONTAL) {
                        //Verificar se pose avançar ou não
                        if (indexColumns != indexCol || indexCol == 0) {
                            //Armazena a posição anterior
                            indexColumns = indexCol;
                            //Inserir o valor para decremento ou acrescimo           
                            scrollbarHorizontal.unitIncrementProperty().setValue(value);
                            //Veririfcar função
                            if (scrollDirect == DirecionScrollBar.AVANCAR) {
                                scrollbarHorizontal.increment();
                            } else if (scrollDirect == DirecionScrollBar.VOLTAR) {
                                scrollbarHorizontal.decrement();
                            }
                        }
                    } else if (scrollBarMove == ScrollBarMove.VERTICAL) {
                        //Controle para o movimentar automatico
                        if (valueIdAnteriorScroll < value.doubleValue()) {
                            valueIdAnteriorScroll = value.doubleValue();
                            timerScroll.start();
                        } else if (valueIdAnteriorScroll == value.doubleValue()) {
                            valueIdAnteriorScroll++;
                        }
                    }
                } else {
                    //Configurar barra de rolagem
                    //Busca a barra de rolagem que esta na tableview
                    for (Node n : tableView.lookupAll(".scroll-bar")) {
                        //Barras
                        if (n instanceof ScrollBar) {
                            if (scrollbarVertical == null) {
                                //Tamanho do mover do clique da barra de rolagem
                                scrollbarVertical = (ScrollBar) n;
                                scrollbarVertical.setOrientation(Orientation.VERTICAL);
                                scrollbarVertical.setMin(0);
                                //Animação para a movimentação do mouse
                                timerScroll = new AnimationTimer() {
                                    long lng = 0;
                                    boolean statos = true;

                                    @Override
                                    public void handle(long l) {
                                        if (statos == true) {
                                            if (lng == 0) {
                                                lng = l;
                                            }
                                            if (l > lng + 100000000) {
                                                scrollbarVertical.setValue(scrollbarVertical.getValue() + 0.05);
                                            }
                                        }
                                    }

                                    @Override
                                    public void stop() {
                                        super.stop();
                                        this.statos = false;
                                    }

                                    @Override
                                    public void start() {
                                        super.start();
                                        this.statos = true;
                                    }

                                };
                                //Cria o evento para pausar
                                EventHandler ar = new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent t) {
                                        timerScroll.stop();
                                    }
                                };
                                //Adcionar eventos
                                scrollbarVertical.setOnMouseClicked(ar);
                                scrollbarVertical.setOnMouseReleased(ar);
                                scrollbarVertical.setOnMouseEntered(ar);
                                scrollbarVertical.setOnMouseDragged(ar);

                            } else if (scrollbarHorizontal == null) {
                                //Tamanho do mover do clique da barra de rolagem
                                scrollbarHorizontal = (ScrollBar) n;
                                scrollbarHorizontal.setOrientation(Orientation.HORIZONTAL);
                                scrollbarHorizontal.setBlockIncrement(tableView.getPrefWidth());
                                scrollbarHorizontal.setMax(tableView.getPrefWidth());
                                scrollbarHorizontal.setMin(0);

                            }
                        }
                    }

                }
                break;
        }
    }

    /**
     * Torna a coluna vísivel, invísivel e quando invísivel, visivel.
     *
     * @param textColumn nome que esta na coluna
     * @return
     */
    public boolean ocultarColuna(String textColumn) {
        //Descarregar as colunas 
        for (Object obj : tableView.getColumns()) {
            //Verificar se é realmente coluns
            if (obj instanceof TableColumn) {
                //Converte 
                TableColumn col = (TableColumn) obj;
                //Comparar contéudo e verificar qual coluna deseja inserir
                if (col.getText().equals(textColumn)) {
                    //Se estiver visivel trona invisivel
                    if (col.isVisible()) {
                        col.setVisible(false);
                        //Contrario do IF
                    } else {
                        col.setVisible(true);
                    }
                    return true;
                }
            }
        }
        //Não foi possível alterar
        return false;
    }

    /**
     * Cria uma nova linha abaixo. Obs.: Com refresh incluido.
     *
     * @param itemNovo
     */
    public void newRow(Object itemNovo) throws NullPointerException {
        newRow(itemNovo, true, false);
    }

    public void newRow(Object itemNovo, boolean isRefrash) throws NullPointerException {
        newRow(itemNovo, isRefrash, false);
    }

    private void newRow(Object itemNovo, boolean isRefrash, boolean is) throws NullPointerException {
        if (itemNovo != null) {
            //Adiciona um novo objeto ao array
            tableView.getItems().add(itemNovo);
            //Verificar se existe itens
            if (tableView.getItems().size() > 0) {
                //Verificar permisão para atualizar
                if (isRefrash) {
                    //Atualiza a visualização da tabela
                    HelpForm.refreshTableColumns(tableView, 0, false);
                }
                //Seleciona a primeira célula da linha inserida
                selectCel(tableView.getItems().size() - 1, 0);
                //Iniciar barra de rolagem
                scrollTo(0, 0, DirecionScrollBar.AVANCAR, ScrollBarMove.HORIZONTAL);
            }
        }
    }

    /**
     * Muda o foco para a célula para o endereço passado
     *
     * @param linha
     * @param coluna
     *
     */
    public void selectCel(int linha, int coluna) throws NullPointerException {
        TableColumn column = (TableColumn) tableView.getColumns().get(coluna);
        //seleciona a primeira celula da próxima coluna
        tableView.getSelectionModel().select(linha, column);
        tableView.edit(linha, (TableColumn) tableView.getColumns().get(coluna));

        //Pega a posição atual da célula que possue o foco da seleção
        TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().iterator().next();
        //seta a voluna e a linha selecionada 
        int col = pos.getColumn();

        //Se a coluna for zero não movimentar a barra
        if (col == 0) {
            //Verifica sem linhas exceto a ultima        
        } else if (tableView.getColumns().size() != col + 1) {
            //Movimenta barra de rolagem
            scrollTo(column.getPrefWidth(), (col + 1), DirecionScrollBar.AVANCAR, ScrollBarMove.HORIZONTAL);
            //Ultima linha
        } else {
            //Movimenta barra de rolagem
            scrollTo(tableView.getPrefWidth(), 0, DirecionScrollBar.VOLTAR, ScrollBarMove.HORIZONTAL);
        }
    }

    /**
     * Muda o foco para a proxíma célula .
     *
     */
    public void selectCelNext() {
        //Pega a posição atual da célula que possue o foco da seleção
        position = (TablePosition) tableView.getSelectionModel().getSelectedCells().iterator().next();

        //seta a voluna e a linha selecionada 
        int coluna = position.getColumn();
        int linha = position.getRow();

        //Verifica se o não esta na ultima coluna        
        if (tableView.getColumns().size() != coluna + 1) {
            //Avança para a proxima celula
            tableView.getSelectionModel().selectNext();
            //Pega os dados da coluna selecionada
            TableColumn col = (TableColumn) tableView.getColumns().get(coluna + 1);
            //Habilita a edição da proxima celula
            tableView.edit(linha, col);
            //Movimenta barra de rolagem
            scrollTo(col.getPrefWidth(), (coluna + 1), DirecionScrollBar.AVANCAR, ScrollBarMove.HORIZONTAL);
            //Quando o ponteiro estiver na ultima linha verifica se já esta inserida uma linha abaixo
        } else if (tableView.getItems().size() > (linha + 1)) {
            //seta os valores para posicionar o ponteiro na próxima linha e na primeira celula
            coluna = 0;
            linha = linha + 1;
            //Pega os dados da coluna selecionada
            TableColumn col = (TableColumn) tableView.getColumns().get(coluna);
            //seleciona a primeira celula da próxima coluna
            tableView.getSelectionModel().select(linha, col);
            //edita a primeira celula da próxima coluna
            tableView.edit(linha, (TableColumn) tableView.getColumns().get(coluna));
            //Movimenta barra de rolagem
            scrollTo(tableView.getPrefWidth(), 0, DirecionScrollBar.VOLTAR, ScrollBarMove.HORIZONTAL);
            //Caso o ponteiro esteja na ultima coluna e não tenha nenhuma coluna inserida abaixo
        } else {
            //Mantem a seleção da ultima coluna na ultima linha
            tableView.getSelectionModel().selectPrevious();
            positionNext = (TablePosition) tableView.getSelectionModel().getSelectedCells().iterator().next();
            tableView.edit(positionNext.getRow(), positionNext.getTableColumn());
        }
    }

    /**
     * Muda o foco para a proxíma célula .
     */
    public void cellFocusNext() {
        //Pega a posição atual da célula que possue o foco da seleção
        position = (TablePosition) tableView.getSelectionModel().getSelectedCells().iterator().next();
        //Vai para a próxima célula
        tableView.getSelectionModel().selectNext();
        //Pega a posição posterior da célula que acabou de ganhar o foco da seleção
        positionNext = (TablePosition) tableView.getSelectionModel().getSelectedCells().iterator().next();
        //Chama a célula que ganhou foco para edição
        tableView.edit(positionNext.getRow(), positionNext.getTableColumn());
    }

    /**
     * Deleta a ultima linha da tabela.
     */
    public void delLastRow() {
        if (tableView.getItems().size() > 0) {
            //Remove do array a ultima posição
            tableView.getItems().remove(tableView.getItems().size() - 1);
        }
    }

    /**
     * Deleta a linha selecionada da tabela.
     */
    public void delRowSelected() {
        if (tableView.getItems().size() > 0) {
            int index = tableView.getSelectionModel().getSelectedIndex();
            //Remove do array a ultima posição
            tableView.getItems().remove(index);
        }
    }

    /**
     * Altera o objeto da linha seletionada.
     *
     * @param obj
     */
    public void alterEditRow(Object obj) {
        //Substitui o objeto da linha em seleção para o objeto passado como parametro
        tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex(), obj);
    }
}
