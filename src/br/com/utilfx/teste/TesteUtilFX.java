package br.com.utilfx.teste;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import util.string.DateString;
import util.string.FilterString;
import util.string.FloatString;
import util.string.MaskString;
import br.com.utilfx.components.tableview.TableViewModel;

/**
 *
 * @author usuario
 */
public class TesteUtilFX extends Application {

    @Override
    public void start(Stage primaryStage) {

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
        //
        MaskString maskString = new MaskString("###.###.###-##");

        DateString dtString = new DateString();

        Uf uf1 = new Uf();
        uf1.setId(1);
        uf1.setDescricao("DF");

        Uf uf2 = new Uf();
        uf2.setId(2);
        uf2.setDescricao("GO");

        Uf uf3 = new Uf();
        uf3.setId(2);
        uf3.setDescricao("SP");

        List<Uf> listUf = new ArrayList();
        listUf.add(uf1);
        listUf.add(uf2);
        listUf.add(uf3);

        Molde molde = new Molde();

        List<Molde> lista = new ArrayList<>();
        lista.add(molde);

        final TableView tableView = new TableView();

        
        tableView.setItems(FXCollections.observableList(lista));
        TableViewModel tableViewModel = new TableViewModel(tableView, TableViewModel.TipoTableView.EDITCELL);
        /*
        tableViewModel.setColumnWithButtonWithTextfield("texto", "Buscar", 75, new ButtonWithTextfieldCellFactory() {
            @Override
            public ButtonWithTextfieldCell call(TableColumn p) {
                final ButtonWithTextfieldCell cell = new ButtonWithTextfieldCell("Buscar");
                cell.getButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {

                        AlertDialog.open(null, ((TextField) cell.getButton().getGraphic()).getText());
                        TableViewModel.selectCelNext();

                    }
                });
                return cell;
            }
        });

        tableViewModel.setColumnWithTextField("texto", "Texto", 100, new FilterTextFieldCellFactory(filterString) {
            @Override
            public TextFieldCell call(TableColumn p) {
                final TextFieldCell cellTF = new TextFieldCell(filterString);

                cellTF.getTextField().setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent t) {
                        if (t.getCode() == KeyCode.ENTER) {

                            Molde molde1 = (Molde) cellTF.getTableView().getSelectionModel().getSelectedItem();
                            molde1.setMascara("01360317147");
                            cellTF.commitValue(filterString, cellTF.getTextField());
                            HelpForm.refreshTable(tableView);

                            int linhaAtual = TableViewModel.getPosition().getRow();
                            int colunaAtual = TableViewModel.getPosition().getColumn() + 1;
                            TableViewModel.selectCel(linhaAtual, colunaAtual);

                        }
                    }
                });
                return cellTF;
            }
        });

        tableViewModel.setColumnWithTextField("mascara", "Máscara", 100, new MaskTextFieldCellFactory(maskString));
        tableViewModel.setColumnWithTextField("data", "Data", 100, new DateTextFieldCellFactory(dtString));
        tableViewModel.setColumnWithCheckBox("status", "Status", 80);
        tableViewModel.setColumnWithComboBox("uf.descricao", "UF", 100, new ComboBoxCellFactory("descricao", FXCollections.observableArrayList(listUf)));
        tableViewModel.setColumnWithImageView("imagem", "Imagem", 100);

        tableViewModel.setColumnWithButton("texto", "Ação", 75, new ButtonCellFactory() {
            @Override
            public ButtonCell call(TableColumn p) {
                final ButtonCell cell = new ButtonCell("show");
                cell.getButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        //Molde molde = (Molde) cell.getTableView().getSelectionModel().getSelectedItem();
                        Molde molde = (Molde) cell.getTableView().getItems().get(cell.getTableRow().getIndex());
                        System.out.println(molde);

                    }
                });
                return cell;
            }
        });

        tableViewModel.setColumnWithTextField("numero", "Número", 100,  new FloatTextFieldCellFactory(floatString));

        try {
            molde.setImagem(new Image(TesteUtilFX.class.getResource("/utilfx/teste/cancelar15x15.png").openStream()));
        } catch (IOException ex) {
            Logger.getLogger(TesteUtilFX.class.getName()).log(Level.SEVERE, null, ex);
        }

        FloatString fs = new FloatString();
        fs.setSuffix("%");
        fs.setPrecision(4);
        fs.setScale(2);
        fs.setFillDecimalPlacesWithZeros(true);
        fs.setShowFloatWithComma(true);

        Button btAdicionar = new Button();
        btAdicionar.setText("Adicionar");
        btAdicionar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                Molde m1 = new Molde();
                m1.setUf(new Uf());
                m1.setTexto("Linha1123");
                m1.setData(new Date());
                m1.setNumero(BigDecimal.ZERO);
                m1.setMascara("00967812119");

                TableViewModel.newRow(m1);

            }
        });

        Button btExcluir = new Button();
        btExcluir.setText("Excluir");
        btExcluir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                TableViewModel.delLastRow();
            }
        });

        Button btCancelar = new Button();
        btCancelar.setText("Cancelar");
        btCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                Molde m1 = new Molde();
                m1.setUf(new Uf());
                m1.setTexto("Linha1123");
                m1.setData(new Date());
                m1.setNumero(BigDecimal.ZERO);
                m1.setMascara("00967812119");

                List<Molde> lista = new ArrayList<>();
                lista.add(m1);

                TableViewModel.alterEditRow(m1);
            }
        });

        Label lbLinha = new Label();
        final TextField txLinha = new TextField();
        txLinha.setPrefHeight(26);

        Label lbColuna = new Label();
        final TextField txColuna = new TextField();
        txColuna.setPrefHeight(26);

        Button btSelectCel = new Button();
        btSelectCel.setText("Selecionar Celula");
        btSelectCel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                TableViewModel.selectCel(Integer.parseInt(txLinha.getText()), Integer.parseInt(txColuna.getText()));
            }
        });

        Button btAvancar = new Button();
        btAvancar.setText("Avancar");
        btAvancar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                TableViewModel.selectCelNext();
            }
        });

        TextField textField = new TextField();
        textField.setPrefHeight(26);
        FloatTextInputControl.apply(textField, fs);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView, textField, btAdicionar, btExcluir, txLinha, txColuna, btSelectCel, btAvancar, btCancelar);

        StackPane root = new StackPane();
        root.getChildren().addAll(vBox);

        Scene scene = new Scene(root, 700, 300);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
    }

    public void scrollNodeInTopScrollPane(Node n, ScrollPane s) {
        final Node node = n;
        final ScrollPane clientTopScrollPane = s;
        AnimationTimer timer = new AnimationTimer() {
            long lng = 0;

            @Override
            public void handle(long l) {
                if (lng == 0) {
                    lng = l;
                }
                if (l > lng + 100000000) {
                    if (node.getLocalToSceneTransform().getTy() > 20) {
                        clientTopScrollPane.setVvalue(clientTopScrollPane.getVvalue() + 0.05);
                        if (clientTopScrollPane.getVvalue() == 1) {
                            this.stop();
                        }
                    } else {
                        this.stop();
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        launch(args);
//    }
    
    
}
