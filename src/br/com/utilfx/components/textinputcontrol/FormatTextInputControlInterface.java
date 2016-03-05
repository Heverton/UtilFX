package br.com.utilfx.components.textinputcontrol;

import javafx.scene.control.TextInputControl;
import br.com.util.string.FormatStringInterface;

/**
 * Interface para objetos de formatação de TextInputControl.
 * 
 * @author Heverton Cruz
 * @version 1.0
 */
public interface FormatTextInputControlInterface {
    /**
     * Atribui a formatação ao TextInputControl.
     * 
     * @param textInputControl 
     */
    public void assign(final TextInputControl textInputControl);
    
    /**
     * Retorna o formatador do TextInputControl.
     * @return 
     */
    public FormatStringInterface getFormatter();
}
