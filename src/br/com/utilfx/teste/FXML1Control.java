/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utilfx.teste;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextAlignment;
import br.com.util.string.DateString;
import br.com.util.string.FilterString;
import br.com.util.string.FloatString;
import br.com.util.string.MaskString;
import br.com.utilfx.components.HelpForm;
import br.com.utilfx.components.combobox.ComboBoxModel;
import br.com.utilfx.components.tableview.KeyEventColumns;
import br.com.utilfx.components.tableview.TableViewModel;
import br.com.utilfx.components.tableview.cell.ButtonCell;
import br.com.utilfx.components.tableview.cell.ButtonWithTextfieldCell;
import br.com.utilfx.components.tableview.cell.ComboBoxCell;
import br.com.utilfx.components.tableview.cell.TextFieldCell;
import br.com.utilfx.components.tableview.cellfactory.ButtonCellFactory;
import br.com.utilfx.components.tableview.cellfactory.ButtonWithTextfieldCellFactory;
import br.com.utilfx.components.tableview.cellfactory.ComboBoxCellFactory;
import br.com.utilfx.components.tableview.cellfactory.DateTextFieldCellFactory;
import br.com.utilfx.components.tableview.cellfactory.FilterTextFieldCellFactory;
import br.com.utilfx.components.tableview.cellfactory.FloatTextFieldCellFactory;
import br.com.utilfx.components.tableview.cellfactory.MaskTextFieldCellFactory;

/**
 * FXML Controller class
 *
 * @author Heverton Cruz
 */
public class FXML1Control implements Initializable {

    @FXML
    private Button btAdicionar;
    @FXML
    private Button btExcluir;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btAvancar;
    @FXML
    private Button btSelectCel;
    @FXML
    private ComboBox cbAuto;
    @FXML
    private TableView tvTable;
    
    private TableViewModel tableViewModel;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        final FilterString filterString = new FilterString();
        filterString.setMaxLenght(10);
        filterString.setTypesFilter(new String[]{"alnum"});
        //
        FloatString floatString = new FloatString();
        floatString.setPrefix("R$");
        floatString.setPrecision(9);
        floatString.setScale(2);
        floatString.setAllowsNegative(true);
        floatString.setFillDecimalPlacesWithZeros(true);
        floatString.setShowFloatWithComma(true);

        MaskString maskString = new MaskString("###.###.###-##");

        final List<Uf> listUf = new ArrayList();
        listUf.add(new Uf(1, "São Paulo", "SP"));
        listUf.add(new Uf(2, "Goiás", "GO"));
        listUf.add(new Uf(3, "Distrito Federal", "DF"));
        listUf.add(new Uf(4, "São Paulo", "SP"));

        List<Uf> listUf2 = new ArrayList();
        listUf2.add(new Uf(1, "São Paulo", "SP"));
        listUf2.add(new Uf(2, "Goiás", "GO"));
        listUf2.add(new Uf(3, "Distrito Federal", "DF"));
        listUf2.add(new Uf(4, "Distrito Federal", "DF"));
        listUf2.add(new Uf(5, "Distrito Federal", "DF"));
        listUf2.add(new Uf(6, "São Paulo", "SP"));

        // Descrição 
        ComboBoxModel.editable("descricao", cbAuto, FXCollections.observableArrayList(listUf2));

        Molde molde = new Molde();

        List<Molde> lista = new ArrayList<>();
        lista.add(molde);

        molde.setData(new Date());
        try {
            molde.setImagem(new Image(FXML1Control.class.getResource("cancelar15x15.png").openStream()));
        } catch (IOException ex) {
            Logger.getLogger(FXML1Control.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableViewModel = new TableViewModel(tvTable, TableViewModel.TipoTableView.EDITCELL, FXCollections.observableList(lista));

        // Ação para todos os componentes
        tableViewModel.setOnKeyEventColumns(new KeyEventColumns() {
            @Override
            public void handle(KeyEvent event) {
                System.err.println("Action Generica!");
            }
        });
        
        tableViewModel.setColumnWithButtonWithTextfield("texto", "Buscar", 230, new ButtonWithTextfieldCellFactory() {
            @Override
            public ButtonWithTextfieldCell call(TableColumn p) {
                final ButtonWithTextfieldCell cell = new ButtonWithTextfieldCell(tableViewModel, "Buscar", false);
                cell.getButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        HelpForm.focusCell(tableViewModel, cell.getIndex(), 1);
                    }
                });
                return cell;
            }
        });
        
        tableViewModel.setColumnWithTextField("texto", "Texto", 100, new FilterTextFieldCellFactory(tableViewModel, filterString, TextAlignment.RIGHT, true) {
            @Override
            public TextFieldCell call(TableColumn p) {
                final TextFieldCell cellTF = new TextFieldCell(tableViewModel, filterString, TextAlignment.RIGHT, false);
                cellTF.getTextField().setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {
                            cellTF.commitValue(null, cellTF.getTextField());
                            
                            Molde model = (Molde) tvTable.getSelectionModel().getSelectedItem();
                            //Somar
                            model.setTexto((Integer.parseInt(model.getTexto()) + 1) + "");
                            model.setLabel((Integer.parseInt(model.getTexto()) + 2) + "");
                            
                            HelpForm.focusCell(tableViewModel, cellTF.getIndex(), 5);
                        }
                    }
                });

                return cellTF;
            }
        });
        tableViewModel.setColumnWithTextField("mascara", "Máscara", 100, new MaskTextFieldCellFactory(tableViewModel, maskString, TextAlignment.LEFT));
        tableViewModel.setColumnWithTextField("data", "Data", 100, new DateTextFieldCellFactory(tableViewModel, new DateString(), TextAlignment.CENTER));
        
        tableViewModel.setColumnWithCheckBox("status", tableViewModel, "Status", 80);
        
        // uf objeto e descrição o campo que será exibido
        tableViewModel.setColumnWithComboBox("uf", "UF", 100, new ComboBoxCellFactory(tableViewModel, "descricao", FXCollections.observableArrayList(listUf)) {
            @Override
            public ComboBoxCell call(TableColumn tc) {
                final ComboBoxCell cellTF = new ComboBoxCell(tableViewModel, "descricao", FXCollections.observableArrayList(listUf));
                cellTF.getComboBox().setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        HelpForm.focusCell(tableViewModel, cellTF.getIndex(), 3);
                    }
                });

                return cellTF;

            }
        });
        tableViewModel.setColumnWithImageView("imagem", tableViewModel, "Imagem", 100);
        tableViewModel.setColumnWithButton("texto", "Ação", 75, new ButtonCellFactory() {
            @Override
            public ButtonCell call(TableColumn p) {
                final ButtonCell cell = new ButtonCell(tableViewModel, "show");
                cell.getButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        Molde molde = (Molde) cell.getTableView().getItems().get(cell.getTableRow().getIndex());
//                        System.out.println(molde);
                        HelpForm.focusCell(tableViewModel, cell.getIndex(), 5);
                    }
                });
                return cell;
            }
        });
        tableViewModel.setColumnWithTextField("numero", "Número", 100, new FloatTextFieldCellFactory(tableViewModel, floatString));
    
    }

    @FXML
    public void onCbAuto(ActionEvent event) {
    }

    @FXML
    public void onbtConfirmar(ActionEvent event) {
        System.err.println(cbAuto.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onbtOcultar(ActionEvent event) {
        tableViewModel.ocultarColuna("Número");
    }

    private int cont = 0;

    @FXML
    public void onbtAdicionar(ActionEvent event) {
        Molde m1 = new Molde();
        m1.setTexto("" + (cont++));
        m1.setUf(new Uf());
        m1.setData(new Date());
        m1.setNumero(BigDecimal.ZERO);
        m1.setMascara("00967812119");
        m1.setLabel("Campo label");
        try {
            m1.setImagem(new Image(FXML1Control.class.getResource("cancelar15x15.png").openStream()));
        } catch (IOException ex) {
            Logger.getLogger(FXML1Control.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableViewModel.newRow(m1, false);
            
        HelpForm.scrollToZera(tableViewModel, tvTable);
    }

}
