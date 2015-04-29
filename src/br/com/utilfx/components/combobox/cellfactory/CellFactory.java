package br.com.utilfx.components.combobox.cellfactory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import br.com.utilfx.components.util.CellReflection;

/**
 * Faz a configuração da fábrica de células de um ComboBox, controlando a sua
 * atualização.
 *
 * @author Herberts Cruz
 * @version 1.0
 */
public class CellFactory implements Callback<ListView<Object>, ListCell<String>> {

    private final String projetion;

    public CellFactory(String projetion) {
        this.projetion = projetion;
    }

    @Override
    public ListCell<String> call(ListView<Object> o) {
        return new ListCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                //Se tiver o item ou foi definido como vazio
                if (item == null || empty) {
                    //Limpa os dados da célula
                    super.updateItem(null, empty);
                    setText(null);
                } else {
                    //Pega o valor do nó
                    String string = CellReflection.getValuePojo(item, projetion).toString();
                    //Atualiza a célula
                    super.updateItem(string, empty);
                    setText(string);
                }
            }
        };
    }
}
