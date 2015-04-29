package br.com.utilfx.components.textinputcontrol;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;
import br.com.util.string.FilterString;
import br.com.util.string.FormatStringInterface;

/**
 * Aplica um filtro a um TextInputControl.
 *
 * @author Herberts Cruz
 * @version 1.0
 */
public class FilterTextInputControl implements FormatTextInputControlInterface {

    private FilterString filter;
    private TextInputControl textInputControl;
    private TextChangeListener listenerTextProperty = new TextChangeListener();

    public FilterTextInputControl(FilterString filter) {
        this.filter = filter;
    }

    /**
     * Aplica um filtro a um TextInputControl.
     * 
     * @param textInputControl
     * @param filter 
     * @return  
     */
    public static FilterTextInputControl apply(TextInputControl textInputControl, FilterString filter) {
        FilterTextInputControl filterTextInputControl = new FilterTextInputControl(filter);
        filterTextInputControl.assign(textInputControl);
        
        return filterTextInputControl;
    }
    
    @Override
    public FormatStringInterface getFormatter() {
        return filter;
    }
    
    @Override
    public void assign(final TextInputControl textInputControl) {
        //Atribui os eventos padrão
        textInputControl.textProperty().addListener(listenerTextProperty);
        //Seta o TextField na variável global
        this.textInputControl = textInputControl;
    }
    
    private class TextChangeListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> values, String oldText, String newText) {
            //Filtra o valor enviado
            final String altNewText = filter.apply(newText);
            
            textInputControl.setText(altNewText);
        }
    }
}
