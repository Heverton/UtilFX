package br.com.utilfx.components.combobox.stringconverter;

import java.util.HashMap;
import java.util.Map;
import javafx.util.StringConverter;
import br.com.utilfx.components.util.CellReflection;

/**
 * Pega o objeto da célula e retira dele o valor do nó, controlando a sua
 * atualização para visualização.
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class CellStringConverter extends StringConverter<Object> {

    private final String projetion;
    private final Map<String, Object> pojos = new HashMap();

    public CellStringConverter(String projetion) {
        this.projetion = projetion;
    }

    @Override
    public String toString(Object pojo) {
        if (pojo != null) {
            //Pega o valor do nó
            String string = CellReflection.getValuePojo(pojo, projetion).toString();
            this.pojos.put(string, pojo);
            return string;
        } else {
            return "";
        }
    }

    @Override
    public Object fromString(String string) {
        return pojos.get(string);
    }

}
