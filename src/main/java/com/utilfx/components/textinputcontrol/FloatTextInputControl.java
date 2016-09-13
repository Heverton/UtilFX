package com.utilfx.components.textinputcontrol;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;
import util.string.FloatString;
import util.string.FormatStringInterface;

/**
 * Aplica a formatação padrão de um TextInputControl para o tipo ponto flutuante
 * com as formatações definidas.
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class FloatTextInputControl implements FormatTextInputControlInterface {

    private FloatString fl;
    private TextInputControl textInputControl;
    private boolean clearFieldGainFocus = false;
    private FocusedChangeListener focusedChangeListener = new FocusedChangeListener();
    private TextChangeListener textChangeListener = new TextChangeListener();
    private AnchorChangeListener anchorChangeListener = new AnchorChangeListener();

    public FloatTextInputControl(FloatString fl) {
        this.fl = fl;
    }

    /**
     * Aplica uma formatação de número a um TextInputControl.
     *
     * @param textInputControl
     * @param fl
     * @return
     */
    public static FloatTextInputControl apply(TextInputControl textInputControl, FloatString fl) {
        FloatTextInputControl floatTextInputControl = new FloatTextInputControl(fl);
        floatTextInputControl.assign(textInputControl);
        floatTextInputControl.defaultVelue(textInputControl);
        return floatTextInputControl;
    }

    @Override
    public FormatStringInterface getFormatter() {
        return fl;
    }

    /**
     * Define se o conteúdo do campo será limpo quando receber foco.
     *
     * @param clearFieldGainFocus
     */
    public void setClearFieldGainFocus(boolean clearFieldGainFocus) {
        this.clearFieldGainFocus = clearFieldGainFocus;
    }

    public void defaultVelue(final TextInputControl textInputControl) {
        //Provoca o Listener do objeto com valor vazio
        //Define que quer preencher zeros à direita
        fl.setFillDecimalPlacesWithZeros(true);
        //Armazena o valor de um campo zerado
        String resultantZero = fl.apply("0");
        //Atualiza o conteúdo do TextField
        textInputControl.setText(resultantZero);
        //Seta o TextField na variável global
        this.textInputControl = textInputControl;
    }

    @Override
    public void assign(final TextInputControl textInputControl) {
        //Atribui os eventos padrão
        textInputControl.focusedProperty().addListener(focusedChangeListener);
        textInputControl.textProperty().addListener(textChangeListener);
        //Aplica a mudança na posição do ponteiro
        textInputControl.anchorProperty().addListener(anchorChangeListener);
        //Seta o TextField na variável global
        this.textInputControl = textInputControl;
    }

    /**
     * Seleciona apenas a parte numérica do campo.
     */
    private void selectNumber() {
        if (!fl.getPrefix().isEmpty() && !fl.getSuffix().isEmpty()) {
            textInputControl.selectRange(fl.getPrefix().length(), (textInputControl.getText().length() - fl.getSuffix().length()));
        } else if (!fl.getPrefix().isEmpty()) {
            textInputControl.selectRange(fl.getPrefix().length(), textInputControl.getText().length());
        } else if (!fl.getSuffix().isEmpty()) {
            textInputControl.selectRange(0, (textInputControl.getText().length() - fl.getSuffix().length()));
        } else {
            textInputControl.selectRange(0, textInputControl.getText().length());
        }
    }

    private class FocusedChangeListener implements ChangeListener<Boolean> {

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            //Se ganhar com o foco
            if (newValue) {
                //Se estiver habilitado para limpar o campo após perder o foco
                if (clearFieldGainFocus) {
                    textInputControl.clear();
                }
                //Se não for vazio
                if (!fl.getPrefix().isEmpty()) {
                    //Atualiza o conteúdo do TextField
                    textInputControl.setText(fl.getPrefix());
                }
            } else {
                //Verifica se o valor é nulo
                String estoqueAtual = (textInputControl.getText().isEmpty())
                        ? "0" : textInputControl.getText();
                //Define que quer preencher zeros à direita
                fl.setFillDecimalPlacesWithZeros(true);
                //Pega o valor atual do campo e aplica o filtro alterado
                String valorAtual = fl.apply(estoqueAtual);
                //Seta um valor padrão
                textInputControl.setText(valorAtual);
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    selectNumber();
                }
            });
        }
    }

    private class TextChangeListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> values, String oldText, String newText) {
            //Define que quer preencher zeros à direita
            fl.setFillDecimalPlacesWithZeros(true);
            //Armazena o valor de um campo zerado
            String resultantZero = fl.apply("0");
            //Define que não quer preencher zeros à direita
            fl.setFillDecimalPlacesWithZeros(false);
            //Garante que não haveram valores nulos
            oldText = (oldText == null) ? "" : oldText;
            oldText = fl.getPrefix().isEmpty() ? oldText : oldText.replace(fl.getPrefix(), "");
            oldText = fl.getSuffix().isEmpty() ? oldText : oldText.replace(fl.getSuffix(), "");

            newText = (newText == null) ? "" : newText;
            newText = fl.getPrefix().isEmpty() ? newText : newText.replace(fl.getPrefix(), "");
            newText = fl.getSuffix().isEmpty() ? newText : newText.replace(fl.getSuffix(), "");

            try {
                //Se o newText for vazio
                if (newText.isEmpty()) {
                    throw new NumberFormatException("O número está vazio.");
                }
                //Se não for vazio
                if (!oldText.isEmpty()) {
                    //Se o valor anterior era igual ao valor resultante padrão zerado
                    if (oldText.equals(resultantZero)) {
                        //Remove o que for semelhante ao valor zerado
                        newText = fl.apply(newText.replace(resultantZero, ""));
                    }
                }
                //Se achar o sinal positivo
                if (newText.indexOf("+") != -1) {
                    //Retira todos os sinais negativos
                    newText = newText.replaceAll("-", "");
                }
                //Aplica a formatação de ponto flutuante
                String resultant = fl.apply(newText);
                //Armazena o ponto flutuante se houver
                String floatPoint = "";
                //Se a escala for maior que zero
                if (fl.getScale() > 0) {
                    //Registra se o último caracter inserido é um ponto ou uma vírgula
                    boolean lastCaracterPointOrComma = false;
                    //Se o texto não for vazio
                    if (!newText.isEmpty()) {
                        //Remove o sufixo para permitir alterações
                        newText = newText.replace(fl.getSuffix(), "");
                        //Verifica se o último caracter é um ponto ou uma vírgula
                        String result = newText.substring(newText.length() - 1, newText.length());
                        lastCaracterPointOrComma = (result.equals(",") || result.equals("."));
                    }
                    //Se o último caracter inserido foi um ponto ou uma vírgula.
                    if (lastCaracterPointOrComma) {
                        floatPoint = (fl.isShowFloatWithComma()) ? "," : ".";
                    }
                }
                //Remove o sufixo para permitir alterações
                resultant = resultant.replace(fl.getSuffix(), "");
                //Atualiza o conteúdo do TextField
                textInputControl.setText(resultant + floatPoint + fl.getSuffix());

            } catch (NumberFormatException ex) {
                String resultant;
                //Quando o novo valor for vazio e o antigo tiver apenas um caractere
                if (oldText.length() == 1 && newText.isEmpty()) {
                    //Seta o valor zerado no campo
                    resultant = resultantZero;
                    //Seleciona os números
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            selectNumber();
                        }
                    });
                } else {
                    //Aplica a formatação de ponto flutuante
                    resultant = fl.apply(oldText);
                }

                //Atualiza o conteúdo do TextField
                textInputControl.setText(resultant);
            }
        }
    }

    private class AnchorChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (!fl.getPrefix().isEmpty()) {
                //A posição final é o prefixo mais 1(um) do espaço em branco entre o prefixo e o número
                int finalPrefix = fl.getPrefix().length();
                //Se o caret estiver sobre o caracter final do prefixo
                if (newValue.intValue() < finalPrefix) {
                    textInputControl.positionCaret(finalPrefix);
                }
            }
            if (!fl.getSuffix().isEmpty()) {
                //A posição inicial é a posição final da String menos o sufixo 
                //e o espaço em branco entre o número e o sufixo
                int initSuffix = textInputControl.getText().length() - fl.getSuffix().length();
                //Se o caret estiver sobre um caracter do sufixo
                if (newValue.intValue() > initSuffix) {
                    //Não permite avançar mais
                    textInputControl.positionCaret(initSuffix);
                }
            }
        }
    }
}
