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


========================
BUILD OUTPUT DESCRIPTION
========================

When you build an Java application project that has a main class, the IDE
automatically copies all of the JAR
files on the projects classpath to your projects dist/lib folder. The IDE
also adds each of the JAR files to the Class-Path element in the application
JAR files manifest file (MANIFEST.MF).

To run the project from the command line, go to the dist folder and
type the following:

java -jar "Util.jar" 

To distribute this project, zip up the dist folder (including the lib folder)
and distribute the ZIP file.

Notes:

* If two JAR files on the project classpath have the same name, only the first
JAR file is copied to the lib folder.
* Only JAR files are copied to the lib folder.
If the classpath contains other types of files or folders, these files (folders)
are not copied.
* If a library on the projects classpath also has a Class-Path element
specified in the manifest,the content of the Class-Path element has to be on
the projects runtime path.
* To set a main class in a standard Java project, right-click the project node
in the Projects window and choose Properties. Then click Run and enter the
class name in the Main Class field. Alternatively, you can manually type the
class name in the manifest Main-Class element.
