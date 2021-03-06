package com.utilfx.components.textinputcontrol;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.string.DateString;
import util.string.FormatStringInterface;

/**
 * Interface de formatação de máscara para um TextInputControl. 
 * 
 * @author Heverton Cruz
 * @version 1.0
 */
public class DateTextInputControl implements FormatTextInputControlInterface {

    private DateString dtString;
    private TextInputControl textInputControl;
    private int positionCaret = -1;
    private TextChangeListener listenerTextProperty = new TextChangeListener();
    private AnchorChangeListener listenerAnchorProperty = new AnchorChangeListener();
    
    /**
     * Insere a máscara.
     * 
     * A máscara recebe os seguinte caracteres:
     * 
     * # - Caracter numérico.
     * U - Caracter alfabético. Tranforma-o em maiúsculo.
     * L - Caracter alfabético. Tranforma-o em minúsculo.
     * A - Caracter alfanumérico.
     * ? - Representa um caracter qualquer. O caracter interrogação é apenas 
     * representativo, só o use se literalmente você quiser uma interrogação na 
     * máscara.
     * 
     * @param mask 
     */
    public DateTextInputControl(DateString dtString) {
        this.dtString = dtString;
    }
    
    /**
     * Aplica a máscara no TextInputControl.
     * 
     * A máscara recebe os seguinte caracteres:
     * 
     * # - Caracter numérico.
     * U - Caracter alfabético. Tranforma-o em maiúsculo.
     * L - Caracter alfabético. Tranforma-o em minúsculo.
     * A - Caracter alfanumérico.
     * ? - Representa um caracter qualquer. O caracter interrogação é apenas 
     * representativo, só o use se literalmente você quiser uma interrogação na 
     * máscara.
     * 
     * @param TextInputControl textInputControl
     * @param String mask 
     * @return 
     */
    public static DateTextInputControl apply(TextInputControl textInputControl, DateString dtString) {
        DateTextInputControl dateTextInputControl = new DateTextInputControl(dtString);
        dateTextInputControl.assign(textInputControl);
        
        return dateTextInputControl;
    }

    @Override
    public FormatStringInterface getFormatter() {
        return dtString;
    }
    
    @Override
    public void assign(final TextInputControl textInputControl) {
        //Atribui os eventos padrão
        textInputControl.textProperty().addListener(listenerTextProperty);
        //Aplica a mudança na posição do ponteiro de acordo com o necessário
        textInputControl.anchorProperty().addListener(listenerAnchorProperty);
        //Aplica configuração da posição do caret quando utilizar as teclas
        textInputControl.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                //Ajusta o valor da posição do caret 
                //Permite mover o ponteiro para a direita
                if(event.getCode() == KeyCode.RIGHT) {
                    positionCaret++;
                //Permite mover o ponteiro para a esquerda
                } else if(event.getCode() == KeyCode.LEFT) {
                    positionCaret--;
                //Permite mover o ponteiro para a extrema direita
                } else if(event.getCode() == KeyCode.END) {
                    positionCaret = textInputControl.getLength();
                //Permite mover o ponteiro para a extrema esquerda
                } else if(event.getCode() == KeyCode.HOME) {
                    positionCaret = 0;
                }
            }
        });
        //Seta o TextField na variável global
        this.textInputControl = textInputControl;
    }
    
    /**
     * Remove os ouvintes responsáveis pela formação da máscara. 
     * 
     * @return boolean
     */
    public boolean removeMask(TextInputControl textInputControl) {
        try {
            textInputControl.textProperty().removeListener(listenerTextProperty);
            textInputControl.anchorProperty().removeListener(listenerAnchorProperty);
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }
    
    private class TextChangeListener implements ChangeListener<String> {
        
        private String caractersMask = "";
        
        @Override
        public void changed(ObservableValue<? extends String> values, String oldText, String newText) {
            //Substitui os caracteres especiais da máscara por underline
            caractersMask = dtString.getMask().replaceAll("[#ULA]", "_");
            //Retira tudo que fizer para da máscara após o último caracter que não for máscara
            String underlineOldText = getRemoveMaskNextValue(oldText);
            String underlineNewText = getRemoveMaskNextValue(newText);
            //Se não for igual
            if(!oldText.equals(newText)) {
                //Valor com a máscara aplicada
                String newTextWithMask = dtString.apply(newText);
                //Se a quantidade de caracteres de oldText restante for maior 
                //que a quantidade de caracteres de newText restante
                if(underlineOldText.length() > underlineNewText.length()) {
                    //Se for vazio e a quantidade de caracteres do oldText for 
                    //menor que a da máscara
                    if(underlineNewText.isEmpty() 
                            && underlineOldText.length() < caractersMask.length()) {
                        //Apaga o conteúdo do TextInputControl
                        textInputControl.clear();
                    } else {
                        //Reescreve o texto no TextInputControl
                        textInputControl.setText(newTextWithMask);
                    }
                    //Define a posição do ponteiro
                    positionCaret = underlineNewText.length();
                //Se for diferente do que estava antes    
                } else {
                    //Reescreve o texto no TextInputControl
                    textInputControl.setText(newTextWithMask);
                    //Busca a posição do primeiro underline
                    int positionUnderline = newTextWithMask.indexOf("_");
                    //Define a posição do ponteiro
                    positionCaret = (positionUnderline == -1) 
                            ? newTextWithMask.length() 
                            : positionUnderline;
                }
            }
        }
    
        /**
         * Remove do texto toda parte da máscara que estiver após o valor digitado.
         * 
         * @param text
         * @return 
         */
        private String getRemoveMaskNextValue(String text) {
            String underlineText = "";
            
            //Pega os caracteres de text antes de qualquer caracter underline
            int posicaoUnderlineText = text.indexOf("_");
            if(posicaoUnderlineText != -1) {
                //Somente se o index for maior que zero
                if(posicaoUnderlineText > 0) {
                    //Pega o último caracter de text sem underline
                    String lastCaracterMask = caractersMask.substring(posicaoUnderlineText-1, 
                            posicaoUnderlineText);
                    //Se o último caracter for diferente dos caracteres da máscara
                    if(!lastCaracterMask.equals("_")) {                
                        caractersMask = caractersMask.substring(0, caractersMask.length()-1);
                    }
                    //Pega a posição do último carater underline da máscara + uma casa
                    posicaoUnderlineText = caractersMask.substring(0, 
                            posicaoUnderlineText).lastIndexOf("_")+1;
                }
                
                underlineText = text.substring(0, posicaoUnderlineText);
            }
            
            return underlineText;
        }
    }
    
    private class AnchorChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            //Muda a posição do ponteiro
            textInputControl.positionCaret(positionCaret);
        }
    }
}
