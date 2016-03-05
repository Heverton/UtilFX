package br.com.utilfx.components;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.string.FilterString;
import util.string.ValidateString;
import br.com.utilfx.components.tableview.TableViewModel;

/**
 * Auxilia nas tarefas cotidianas de formulários.
 *
 * @author Heverton Cruz
 * @version 1.0
 */
final public class HelpForm {

    /**
     * Gera uma mensagem organizada com campos separados por vírgula e cojunção
     * "e". Também obedece à flexição de número da frase colocando no singular
     * quando houver apenas um campo vazio.
     *
     * @param itensVazios
     * @return
     */
    private static String geraMensagemCamposVazios(List itensVazios) {
        if (itensVazios.size() == 1) {
            return "O campo " + itensVazios.get(0) + " deve ser preenchido.";
        } else if (itensVazios.size() > 1) {
            String mensagem = "";

            for (int i = 0; i < itensVazios.size(); i++) {
                if (i == itensVazios.size() - 2) {
                    mensagem += itensVazios.get(i) + " e ";
                } else {
                    mensagem += itensVazios.get(i) + ", ";
                }
            }
            mensagem = mensagem.substring(0, mensagem.length() - 2);

            return "Os campos " + mensagem + " devem ser preenchidos.";
        } else {
            return "";
        }
    }

    public static void limpaCamposNumericos(TextField... componentes) {
        limpaCamposNumericos2("0", componentes);
    }

    public static void limpaCamposNumericos(String defaultValue, TextField... componentes) {
        limpaCamposNumericos2(defaultValue, componentes);
    }

    private static void limpaCamposNumericos2(String defaultValue, TextField... componentes) {

        //Validar se o valor e nuloou vazio
        if (defaultValue == null) {
            if (defaultValue.isEmpty()) {
                defaultValue = "0";
            }
        } else {
            defaultValue = "0";
        }

        //Atribuit ovalor padrao
        for (Node componente : componentes) {
            if (componente instanceof TextField) {
                ((TextField) componente).setText(defaultValue);
                componente.setStyle(null);
            }
        }
    }

    public static void limpaCampos(Node... componentes) {
        limpaCampos2("", componentes);
    }

    public static void limpaCampos(String defaultValue, Node... componentes) {
        limpaCampos2(defaultValue, componentes);
    }

    private static void limpaCampos2(String defaultValue, Node... componentes) {

        //Validar se o valor e nuloou vazio
        if (defaultValue == null) {
            if (defaultValue.isEmpty()) {
                defaultValue = "";
            }
        } else {
            defaultValue = "";
        }

        for (Node componente : componentes) {
            if (componente instanceof TextField) {
                ((TextField) componente).clear();
                componente.setStyle(null);
            } else if (componente instanceof ComboBox) {
                ((ComboBox) componente).getItems().clear();
                componente.setStyle(null);
            } else if (componente instanceof TextArea) {
                ((TextArea) componente).clear();
                componente.setStyle(null);
            } else if (componente instanceof TableView) {
                ((TableView) componente).getItems().clear();
                componente.setStyle(null);
            } else if (componente instanceof ListView) {
                ((ListView) componente).getItems().clear();
                componente.setStyle(null);
            }
        }
    }

    /**
     * Desabilitar campos de bloco
     *
     * Tipos aceitados: TextField, ComboBox, TextArea, TableView, ListView
     *
     * @param defaultValue
     * @param componentes
     */
    public static void alterDisableCampos(boolean defaultValue, Node... componentes) {
        for (Node componente : componentes) {
            if (componente instanceof TextField) {
                ((TextField) componente).clear();
                componente.setDisable(defaultValue);
            } else if (componente instanceof ComboBox) {
                ((ComboBox) componente).getItems().clear();
                componente.setDisable(defaultValue);
            } else if (componente instanceof TextArea) {
                ((TextArea) componente).clear();
                componente.setDisable(defaultValue);
            } else if (componente instanceof TableView) {
                ((TableView) componente).getItems().clear();
                componente.setDisable(defaultValue);
            } else if (componente instanceof ListView) {
                ((ListView) componente).getItems().clear();
                componente.setDisable(defaultValue);
            }
        }
    }

     /**
     * Edita campos de bloco
     *
     * Tipos aceitados: TextField, TextArea 
     *
     * @param defaultValue
     * @param componentes
     */
    public static void editCampos(boolean editCampos, Node... componentes) {
        for (Node componente : componentes) {
            if (componente instanceof TextField) {
                ((TextField) componente).setEditable(editCampos);
            } else if (componente instanceof TextArea) {
                ((TextArea) componente).setEditable(editCampos);
            }
        }
    }

    public static String verificaCamposVazios(Map<Label, Node> combinacaoComponentes) {
        return HelpForm.geraMensagemCamposVazios(verificaCamposVaziosList(combinacaoComponentes));
    }

    /**
     * Movimento a barra de rolagem para a primeira linha
     *
     * @param tableViewModel
     * @param tableView
     */
    public static void scrollToZera(TableViewModel tableViewModel, TableView tableView) {        
        //Movimenta barra de rolagem        
        tableViewModel.scrollTo(tableView.getPrefWidth(), 0, TableViewModel.DirecionScrollBar.VOLTAR, TableViewModel.ScrollBarMove.HORIZONTAL);
        //Movimenta barra de rolagem        
        tableViewModel.scrollTo(tableView.getItems().size(), 0, TableViewModel.DirecionScrollBar.VOLTAR, TableViewModel.ScrollBarMove.VERTICAL);
    }

    /**
     * Requisitar o foco na celula desejada. Este método utiliza uma thread com
     * runntable chamada Platform.sunlater(...)
     *
     * @param tableViewModel
     * @param linha
     * @param coluna
     */
    public static void focusCell(final TableViewModel tableViewModel, final int linha, final int coluna) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tableViewModel.selectCel(linha, coluna);
            }
        });
    }

    public static List<String> verificaCamposVaziosList(Map<Label, Node> combinacaoComponentes) {
        List<String> itensVazios = new ArrayList();
        Iterator<Label> it = combinacaoComponentes.keySet().iterator();

        while (it.hasNext()) {
            Label rotulo = it.next();
            Node componente = combinacaoComponentes.get(rotulo);

            if (componente instanceof TextField) {
                final TextField comp = (TextField) componente;
                String text = comp.getText();
                //Definições do filtro
                FilterString filter = new FilterString();
                filter.setTypesFilter(new String[]{"number"});
                filter.setWithSpaces(false);
                filter.setEscapes(".,");
                //Retira os caracteres que não forem numericos ou ponto
                String textNumerico = (ValidateString.isNullOrEmpty(text))
                        ? "0" : filter.apply(text);
                //Verifica se está vazio ou o valor é "0.(...)" para campos numéricos
                if (ValidateString.isNullOrEmpty(text) || textNumerico.matches("0|0[.,][0]+")) {
                    itensVazios.add(rotulo.getText());

                    comp.setStyle("-fx-background-color: #FFB9B9");

                    comp.focusedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            comp.setStyle(null);
                        }
                    });
                }
            } else if (componente instanceof ComboBox) {
                final ComboBox comp = (ComboBox) componente;

                if (comp.getSelectionModel().getSelectedItem() == null) {
                    itensVazios.add(rotulo.getText());

                    comp.setStyle("-fx-background-color: #FFB9B9");

                    comp.focusedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            comp.setStyle(null);
                        }
                    });
                }
            } else if (componente instanceof TextArea) {
                final TextArea comp = (TextArea) componente;
                String text = comp.getText();

                if (text.isEmpty()) {
                    itensVazios.add(rotulo.getText());

                    comp.setStyle("-fx-background-color: #FFB9B9");

                    comp.focusedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            comp.setStyle(null);
                        }
                    });
                }
            } else if (componente instanceof TableView) {
                final TableView comp = (TableView) componente;

                if (comp.getItems().isEmpty()) {
                    itensVazios.add(rotulo.getText());

                    comp.setStyle("-fx-background-color: #FFB9B9");

                    comp.focusedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            comp.setStyle(null);
                        }
                    });
                }
            }
        }

        return itensVazios;
    }

    /**
     * Método de Terceiro que reseta o ComboBox, deixando visivelmente sem valor
     * mas ainda abastecido com dados.
     *
     * @param <T>
     * @param combos
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> void resetComboBox(ComboBox<T>... combos) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        for (ComboBox combo : combos) {
            Skin<?> skin = combo.getSkin();
            if (skin == null) {
                return;
            }
            combo.setValue(null);
            Field buttonCellField = skin.getClass().getDeclaredField("buttonCell");
            buttonCellField.setAccessible(true);

            ListCell buttonCell = (ListCell) buttonCellField.get(skin);
            if (buttonCell != null) {
                StringProperty text = buttonCell.textProperty();
                text.set("");
                buttonCell.setItem(null);
            }
        }
    }

    /**
     * Atualiza o cache da tabela.
     * Deve ser utilizado fora de um laço de repeteção.
     *
     * @param tv
     */
    public static void refreshTable(final TableView tv) {
        //Oculta e exibe coluna por coluna
        for (TableColumn column : (ObservableList<TableColumn>) tv.getColumns()) {
            column.setVisible(!column.isVisible());
            column.setVisible(!column.isVisible());
        }
    }

    /**
     * Realizar o refresh na linha selecionada da tableview
     *
     * @param tv
     */
    public static void refreshTableRow(final TableView tv) {
        //Oculta e exibe coluna por coluna
        for (TablePosition column : (ObservableList<TablePosition>) tv.getSelectionModel().getSelectedCells()) {
            column.getTableColumn().setVisible(!column.getTableColumn().isVisible());
            column.getTableColumn().setVisible(!column.getTableColumn().isVisible());
        }
    }

    /**
     * Atualiza cache da coluna e foca na mesma atualizada
     *
     * @param tv
     * @param indexColumns
     * @param isFoco
     */
    public static void refreshTableColumns(final TableView tv, final int indexColumns, final boolean isFoco) {
        Integer[] index = {indexColumns};
        refreshTableColumns(tv, index, -1, isFoco);
    }

    /**
     * Atualiza cache da coluna e foca na indexFoco
     *
     * @param tv
     * @param indexColumns
     * @param indexFoco
     */
    public static void refreshTableColumns(final TableView tv, final int indexColumns, final int indexFoco) {
        Integer[] index = {indexColumns};
        refreshTableColumns(tv, index, indexFoco, true);
    }

    /**
     * Atualiza n colunas e foca na indeFoco
     *
     * @param tv
     * @param indexColumns
     * @param indexFoco
     */
    public static void refreshTableColumns(final TableView tv, final Integer[] indexColumns, final int indexFoco) {
        refreshTableColumns(tv, indexColumns, indexFoco, true);
    }

    /**
     * Atualiza n colunas sem foca na indeFoco
     *
     * @param tv
     * @param indexColumns
     */
    public static void refreshTableColumns(final TableView tv, final Integer[] indexColumns) {
        refreshTableColumns(tv, indexColumns, -1, false);
    }

    /**
     * Atualiza cache da coluna
     *
     * @param tv
     */
    private static void refreshTableColumns(final TableView tv, final Integer[] indexColumns, final int indexFoco, boolean isFoco) {
        //Enquanro estiver executando o for locar a tabela
        if (indexColumns != null) {
            //Atualizar colunas
            for (int index : indexColumns) {
                //Oculta e exibe coluna por coluna
                TableColumn col = (TableColumn) tv.getColumns().get(index);
                //Realizar o atualização
                if (col != null) {
                    // Atualizar a coluna
                    col.setVisible(!col.isVisible());
                    col.setVisible(!col.isVisible());
                }
            }
        } else {
            // Atualizar todas as colunas
            refreshTable(tv);
        }

        //Se true requisitar foco
        if (isFoco) {
            //Requisitar a posição
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    TablePosition position = (TablePosition) tv.getSelectionModel().getSelectedCells().iterator().next();

                    if (indexFoco == -1) {

                        //seta a voluna e a linha selecionada 
                        int coluna = indexColumns[0];
                        int linha = position.getRow();

                        TableColumn column = (TableColumn) tv.getColumns().get(coluna);
                        //seleciona a primeira celula da próxima coluna
                        tv.getSelectionModel().select(linha, column);
                        //Foca no proximo desejado                 
                        tv.edit(linha, (TableColumn) tv.getColumns().get(coluna));

                    } else {
                        //seta a voluna e a linha selecionada 
                        int coluna = indexFoco;
                        int linha = position.getRow();

                        TableColumn column = (TableColumn) tv.getColumns().get(coluna);
                        //seleciona a primeira celula da próxima coluna
                        tv.getSelectionModel().select(linha, column);
                        //Foca no proximo desejado                 
                        tv.edit(linha, (TableColumn) tv.getColumns().get(coluna));
                    }
                }
            });
        }
    }
}
