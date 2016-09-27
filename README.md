# UtilFX
Framework de Componentes para JavaFX

========================
CHECK LIST
========================
- COMBOX EDITALVEL
- COMBOX COM PESQUISA NA COMBOX
- MASCARA PARA CAMPOS EM TEMPO DE EXECUÇÃO
- CAREGAR FXML COM ANNOTATION E DENTRO DO JAR EM QUALQUER LOCAL DO JAR- 
- TABELA SIMPLES
- TABELA EDITAL 
- TABELA EDITAL COM CAMPOS DE MASCARA
- TABELA EDITAL COM CAMPOS DE MASCARA
- CAIXA DE DIALAGO
ETC;


EXEMPLOS:

//---------------------------------------------------

//PASSO 1
@FXMLControl(name="FXML1.fxml")
public class FXML1Control extends FXMLInitializable {

//PASSO 2
FXML1Control fxml = new FXML1Control();
fxml.init();
Scene scene = new Scene(fxml.getRoot());
stage.setScene(scene);
stage.show();

//---------------------------------------------------

final FilterString filterString = new FilterString();
filterString.setMaxLenght(10);
filterString.setTypesFilter(new String[]{"alnum"});

//---------------------------------------------------

MaskString maskString = new MaskString("###.###.###-##");

//---------------------------------------------------

ComboBoxModel.editable("descricao", cbAuto, FXCollections.observableArrayList(listUf2));

//---------------------------------------------------

TableViewModel tableViewModel = new TableViewModel(tvTable, TableViewModel.TipoTableView.EDITCELL, FXCollections.observableList(lista));

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
                }
            }
        });
        return cellTF;
    }
});


========================
COMO USAR
========================

<distributionManagement>
    <repository>
        <id>internal.repo</id>
        <name>Temporary Staging Repository</name>
        <url>file://${project.build.directory}/mvn-repo</url>
    </repository>
</distributionManagement>

<dependencies>
    <dependency>
        <groupId>br.com.util</groupId>
        <artifactId>com-util</artifactId>
        <version>1.2</version>
    </dependency>
</dependencies>



