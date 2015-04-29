package br.com.utilfx.components.tableview.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Callback;
import br.com.util.string.ManipuleString;

/**
 * Cria uma interface comum para objetos Modelos de TableView.
 * 
 * @author Herberts Cruz
 * @version 1.0
 */
public abstract class TableViewModel {
    
    /**
     * Guarda a instância do TableView afetado.
     */
    protected TableView tabela;
    
    public TableViewModel(TableView tabela, ObservableList pojos) {
        //Apaga as colunas padrão
        tabela.getColumns().clear();
        //Adiciona os pojos no TableView
        tabela.setItems(pojos);
        //Define a TableView como de campos editáveis
        tabela.setEditable(true);
        //Armazena a instância em uma variável
        this.tabela = tabela;
    }
    
    /**
     * Classe de criação de um objeto representativo de uma célula da tabela.
     */
    protected class TextFieldCell extends TableCell {
        
        private TextField textField;
        
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
                //Seta o foco
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        textField.requestFocus();
                    }
                });
            }
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(null);
        }
        
        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty); 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getStringItem());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getStringItem());
                    setGraphic(null);
                }
            }
        }
        
        /**
         * Pega a String do objeto Item atual.
         * 
         * @return String
         */
        private String getStringItem() {
            return getItem() == null ? "" : getItem().toString();
        }
        
        /**
         * Cria um objeto TextField.
         */
        private void createTextField() {
            textField = new TextField(getStringItem());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            //Dá uma oportunidade de adicionar uma propriedade fora do contexto deste método
            addPropriedadeTextField(textField);
        }
        
        /**
         * Método que permite adicionar ao objeto TextField corrente uma propriedade.
         * 
         * @param textField 
         */
        public void addPropriedadeTextField(TextField textField) {}
    }
    
    /**
     * Cria um Callback para a célula afetada.
     */
    protected class CallBackForCellValueFactory implements Callback<TableColumn.CellDataFeatures, ObservableValue> {
        
        private String[] nomes;
        
        public CallBackForCellValueFactory(String[] nomes){
            this.nomes = nomes;
        }
        
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures p) {
            //Pega o pojo Object
            Object result = p.getValue();
            //Navega entre os nomes de métodos recursivamente
            for(String nome : nomes) {
                //Executa o método e armazena se resultado na variável
                result = executaMetodo(result, "get"+ManipuleString.toCamelCase(nome, true));
            }

            return new SimpleObjectProperty(result);
        }
    }
    
    /**
     * Executa métodos utilizando a classe Reflection.
     * 
     * @param classe
     * @param nomeMetodo
     * @return Object
     */
    protected Object executaMetodo(Object classe, String nomeMetodo) {
        return executaMetodo(classe, nomeMetodo, new Class[]{}, new Object[]{});
    }
    
    /**
     * Executa métodos utilizando a classe Reflection. Permitindo inserir parametros a serem enviados
     * para o método. 
     * Observação: Todos os parametros do método a ser chamado deve ser tipados por tipos wraps.
     * 
     * @param classe
     * @param nomeMetodo
     * @param tiposParametros
     * @param parametros
     * @return Object
     */
    protected Object executaMetodo(Object classe, String nomeMetodo, Class<?>[] tiposParametros, Object[] parametros) {
        try {
            if(tiposParametros == null) {
                tiposParametros = new Class[]{};
            }
            if(parametros == null) {
                parametros = new Object[]{};
            }
            //Pega o objeto Method do método da classe
            Method metodo = classe.getClass().getMethod(nomeMetodo, tiposParametros);
            //Invoca o método com seus parametros
            return metodo.invoke(classe, parametros);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    private TableColumn criaTableColumn(String titulo, String atributoPojo, boolean editavel, int largura) {
        //Quebra a sequência de nomes de atributos divididos por ponto
        final String[] nomes = atributoPojo.split("\\.");
        
        TableColumn coluna = new TableColumn(titulo);
        //Dimensiona a largura da coluna
        coluna.setMinWidth(largura);
        //Define como buscar os valores das células
        coluna.setCellValueFactory(new CallBackForCellValueFactory(nomes));
        //Define se as células da coluna serão editáveis
        coluna.setEditable(editavel);
        
        return coluna;
    }
    
    /**
     * Adiciona uma coluna no TableView que carregará os dados referente ao nome 
     * do atributo do pojo da TableView.
     * 
     * @param titulo
     * @param atributoPojo
     */
    public TableColumn addColuna(String titulo, String atributoPojo) {
        return addColuna(titulo, atributoPojo, true);
    }
    
    /**
     * Adiciona uma coluna no TableView que carregará os dados referente ao nome 
     * do atributo do pojo da TableView. Também permite definir se a coluna será
     * editável ou não.
     * 
     * @param titulo
     * @param atributoPojo
     * @param editavel
     */
    public TableColumn addColuna(String titulo, String atributoPojo, boolean editavel) {
        return addColuna(titulo, atributoPojo, editavel, 100);
    }
    
    /**
     * Adiciona uma coluna no TableView que carregará os dados referente ao nome 
     * do atributo do pojo da TableView. Também permite definir se a coluna será
     * editável ou não e dimensionar a largura da coluna.
     * 
     * @param titulo
     * @param atributoPojo
     * @param editavel 
     * @param largura
     */
    public TableColumn addColuna(String titulo, String atributoPojo, boolean editavel, int largura) {
        //Cria um TableColumn com as configurações iniciais
        TableColumn coluna = criaTableColumn(titulo, atributoPojo, editavel, largura);
        //Define de que tipo será a célula e como ela será exibida
        coluna.setCellFactory(new Callback<TableColumn, TableCell>(){
            @Override
            public TableCell call(TableColumn p) {
                TextFieldCell cell = new TextFieldCell();
                return cell;
            }
        });
        //Adiciona a coluna à tabela
        tabela.getColumns().add(coluna);
        
        return coluna;
    }
    
    /**
     * Adiciona uma coluna no TableView que carregará os dados referente ao nome 
     * do atributo do pojo da TableView e quando houver uma tentativa de editá-lo, 
     * será apresentado um combobox.
     * 
     * @param titulo
     * @param atributoPojo
     * @param pojosComboBox 
     * @param ComboBoxTableCell
     */
    public void addColunaEditavelComComboBox(String titulo, String atributoPojo, ObservableList pojosComboBox) {
        addColunaEditavelComComboBox(titulo, atributoPojo, pojosComboBox, 100);
    }

    /**
     * Adiciona uma coluna no TableView que carregará os dados referente ao nome 
     * do atributo do pojo da TableView e quando houver uma tentativa de editá-lo, 
     * será apresentado um combobox. Também permite dimensionar a largura da 
     * coluna.
     * 
     * @param titulo
     * @param atributoPojo
     * @param pojosComboBox
     * @param largura 
     */
    public void addColunaEditavelComComboBox(String titulo, final String atributoPojo, 
            final ObservableList pojosComboBox, int largura) {
        //Quebra a sequência de nomes de atributos divididos por ponto
        final String[] nomes = atributoPojo.split("\\.");
        
        TableColumn tc = new TableColumn(titulo);
        //Dimensiona a largura da coluna
        tc.setMinWidth(largura);
        //Define como buscar os valores das células
        tc.setCellValueFactory(new CallBackForCellValueFactory(nomes));
        //Define de que tipo será a célula e como ela será exibida
        tc.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                //Cria um Map para armazenar as instâncias dos pojos do ComboBox
                final Map<String, Serializable> pojosCB = new HashMap();
                Iterator<Serializable> pojosi = pojosComboBox.iterator();
                //Navega entre os pojos
                while(pojosi.hasNext()) {
                    Serializable pojo = pojosi.next();
                    //Executa o método que retorna a descrição do atributo que se 
                    //deseja utilizar como índice, este é excencialmente o valor
                    //que será mostrado na lista do ComboBox
                    Object obj = executaMetodo(pojo, 
                            "get"+ManipuleString.toCamelCase(nomes[nomes.length-1], true));
                    if(obj != null) {
                        //Armazena no MAP
                        pojosCB.put(obj.toString(), pojo);
                    } else {
                        throw new NullPointerException("Não existe um atributo \""+atributoPojo+"\" no pojo.");
                    }
                }
                //Cria o ComboBox que aparecerá na céluna quando em edição
                ComboBoxTableCell cell = new ComboBoxTableCell(pojosCB){
                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            //Executa o método set referente ao atributo do pojo 
                            //Object da linha onde está a célula em edição, 
                            //atribuindo a ele o valor escolhido no ComboBox
                            executaMetodo(tabela.getItems().get(getIndex()), 
                                    "set"+ManipuleString.toCamelCase(pojosCB.get(item.toString()).getClass().getSimpleName(), true), 
                                    new Class[]{pojosCB.get(item.toString()).getClass()}, 
                                    new Object[]{pojosCB.get(item.toString())});
                        }
                    }
                };
                //Ajusta o alinhamento do component para o centro da célula
                cell.setAlignment(Pos.CENTER);
                
                return cell;
            }
        });
        //Adiciona a coluna à tabela        
        tabela.getColumns().add(tc);
    }
    
    
    
    public void addColunaEditavelComCheckBox(String titulo, String atributoPojo) {
        addColunaEditavelComCheckBox(titulo, atributoPojo, 100);
    }
    
    public void addColunaEditavelComCheckBox(String titulo, final String atributoPojo, int largura) {
        //Quebra a sequência de nomes de atributos divididos por ponto
        final String[] nomes = atributoPojo.split("\\.");
        
        TableColumn tc = new TableColumn(titulo);
        //Dimensiona a largura da coluna
        tc.setMinWidth(largura);
        //Define como buscar os valores das células
        tc.setCellValueFactory(new CallBackForCellValueFactory(nomes));
        //Define de que tipo será a célula e como ela será exibida
        tc.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(final TableColumn p) {
                TableCell cell = new TableCell(){
                    @Override
                    public void updateItem(final Object item, boolean empty) {
                        super.updateItem(item, empty);
                        //Se a célula estiver vazia
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            final CheckBox check = new CheckBox();
                            //Seleciona ou não de acordo com o valor do atributo
                            check.setSelected(Boolean.parseBoolean(item.toString()));
                            //Atribui uma ação ao cell
                            check.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    //Executa o método set referente ao atributo do pojo 
                                    //Object da linha onde está a célula em edição, 
                                    //atribuindo a ele o valor escolhido no CheckBox
                                    executaMetodo(tabela.getItems().get(getIndex()),
                                            "set"+ManipuleString.toCamelCase(atributoPojo, true),
                                            new Class[]{Boolean.class},
                                            new Object[]{check.isSelected()});
                                }
                            });
                            //Adiciona o component ao TableCell
                            setGraphic(check);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }
                };
                //Ajusta o alinhamento do component para o centro da célula
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        //Adiciona a coluna à tabela        
        tabela.getColumns().add(tc);
    }
    
    public void addColunaComButtonExcluir(String titulo, String idPojo) {
        addColunaComButtonExcluir(titulo, idPojo, 100);
    }
    
    public void addColunaComButtonExcluir(String titulo, String atributoPojo, int largura) {
        //Quebra a sequência de nomes de atributos divididos por ponto
        final String[] nomes = atributoPojo.split("\\.");
        
        TableColumn tc = new TableColumn(titulo);
        //Dimensiona a largura da coluna
        tc.setMinWidth(largura);
        //Define como buscar os valores das células
        tc.setCellValueFactory(new CallBackForCellValueFactory(nomes));
        //Define de que tipo será a célula e como ela será exibida
        tc.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(final TableColumn p) {
                TableCell cell = new TableCell(){
                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        //Se a célula estiver vazia
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            //Cria um botão com a imagem enviada
                            final Button btn = new Button("");
                            //Adiciona a imagem do botão
                            btn.getStyleClass().add("btExcluir");
                            //Adiciona um Tooptip de esplicação para o botão que terá apenas a imagem
                            btn.setTooltip(new Tooltip("Exclui "));
                            //Atribui uma ação ao component
                            btn.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    //Seleciona a linha do TableView onde está o botão
                                    p.getTableView().getSelectionModel().select(getIndex());
                                    //Pega o pojo Object da linha selecionada
                                    Object item = tabela.getSelectionModel().getSelectedItem();
                                    //Se este objeto não for nulo
                                    if (item != null) {
                                        //Remove-o da tabela
                                        tabela.getItems().remove(item);
                                    }
                                }
                            });
                            //Adiciona o component ao TableCell
                            setGraphic(btn);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }
                };
                //Ajusta o alinhamento do component para o centro da célula
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        //Adiciona a coluna à tabela        
        tabela.getColumns().add(tc);
    }
}
