package br.com.utilfx.components.textfield.filter;

import br.com.util.aritimetica.AlgebraFloat;
import java.math.BigDecimal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import br.com.util.string.ValidateString;

/**
 * Faz a formatação do campo para valores numéricos com ponto flutuante.
 * 
 * @author Herberts Cruz, Heverton Cruz
 * @version 1.2
 * @deprecated Substituir por FloatTextInputControl.
 */
public abstract class FloatFormatMask {

    /**
     * Limita o valor do TextField formatando-o para um tipo de número com casas
     * decimais, atribuindo um limite de caracteres.
     *
     * @param textField
     * @param limitCaractere
     */
    public static void numberFloat(TextField textField, int limitCaractere) {
        numberFloat(textField, limitCaractere, true, 2, null, null);
    }

    /**
     * Limita o valor do TextField formatando-o para um tipo de número com casas
     * decimais, atribuindo um limite de caracteres e permitindo ou não valores negativos.
     *
     * @param textField
     * @param limitCaractere 
     * @param permiteNegativo
     */
    public static void numberFloat(TextField textField, int limitCaractere, 
            boolean permiteNegativo) {
        numberFloat(textField, limitCaractere, permiteNegativo, 2, null, null);
    }

    /**
     * Limita o valor do TextField formatando-o para um tipo de número com casas
     * decimais, atribuindo um limite de caracteres, permitindo ou não valores 
     * negativos e adicionando um prefixo que defina um valor qualquer para ser 
     * exibido no início do número.
     *
     * @param textField
     * @param limitCaractere
     * @param permiteNegativo
     * @param prefixo
     */
    public static void numberFloat(TextField textField, int limitCaractere, 
            boolean permiteNegativo, String prefixo) {
        numberFloat(textField, limitCaractere, permiteNegativo, 2, prefixo, null);
    }

    /**
     * Limita o valor do TextField formatando-o para um tipo de número com casas
     * decimais, atribuindo um limite de caracteres, permitindo ou não valores 
     * negativos, adicionando um prefixo que defina um valor qualquer para ser 
     * exibido no início do número e um sufixo que defina um valor qualquer para 
     * ser exibido no fim do número.
     *
     * @param textField
     * @param limitCaractere
     * @param permiteNegativo
     * @param prefixo
     * @param sufixo
     */
    public static void numberFloat(TextField textField, int limitCaractere,
            boolean permiteNegativo, String prefixo, String sufixo) {
        numberFloat(textField, limitCaractere, permiteNegativo, 2, prefixo, sufixo);
    }

    /**
     * Limita o valor do TextField formatando-o para um tipo de número com casas
     * decimais, atribuindo um limite de caracteres, permitindo ou não valores 
     * negativos e definindo o número de casas decimais desejadas.
     *
     * @param textField
     * @param limitCaractere
     * @param permiteNegativo
     * @param casasDecimais
     */
    public static void numberFloat(TextField textField, int limitCaractere,
            boolean permiteNegativo, int casasDecimais) {
        numberFloat(textField, limitCaractere, permiteNegativo, casasDecimais, null, null);
    }

    /**
     * Limita o valor do TextField formatando-o para um tipo de número com casas
     * decimais, atribuindo um limite de caracteres, permitindo ou não valores 
     * negativos, definindo o número de casas decimais desejadas e adicionando 
     * um prefixo que defina um valor qualquer para ser exibido no início do número.
     *
     * @param textField
     * @param limitCaractere
     * @param permiteNegativo
     * @param casasDecimais
     * @param prefixo
     */
    public static void numberFloat(TextField textField, int limitCaractere,
            boolean permiteNegativo, int casasDecimais, String prefixo) {
        numberFloat(textField, limitCaractere, permiteNegativo, casasDecimais, prefixo, null);
    }
    
    /**
     * Limita o valor do TextField formatando-o para um tipo de número com casas
     * decimais, atribuindo um limite de caracteres, permitindo ou não valores 
     * negativos, definindo o número de casas decimais desejadas, adicionando um 
     * prefixo que defina um valor qualquer para ser exibido no início do número 
     * e um sufixo que defina um valor qualquer para ser exibido no fim do número.
     *
     * @param textField
     * @param limitCaractere
     * @param casasDecimais
     * @param permiteNegativo
     * @param prefixo
     * @param sufixo
     */
    public static void numberFloat(final TextField textField, final int limitCaractere,
            final boolean permiteNegativo, final int casasDecimais, String prefixo, String sufixo) {
        if (limitCaractere <= 3) {
            throw new ArithmeticException("O limite de caracteres deve ser definido com no mínimo 3(três).");
        }
        if (casasDecimais < 1) {
            throw new ArithmeticException("O número de casas decimais deve ser definido com no mínimo 1(uma).");
        }

        //Verifica se o prefixo e o sufixo foram definidos
        final String newPrefixo = (ValidateString.isNullOrEmpty(prefixo)) ? "" : prefixo + " ";
        final String newSufixo = (ValidateString.isNullOrEmpty(sufixo)) ? "" : " " + sufixo;

        //Seta um valor padrão inicial para o TextField
        textField.setText(newPrefixo + addZerosDireta("0.", casasDecimais) + newSufixo);

        ChangeListener listenerTextProperty = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> values, String oldText, String newText) {
                //Garante que não haveram valores nulos
                oldText = (oldText == null) ? "" : oldText;
                newText = (newText == null) ? "" : newText;

                //Se tiver um sufixo - oldText
                if (!newSufixo.isEmpty() && oldText.length() > 1) {
                    //Se o último caracter foi apagado
                    if (oldText.indexOf(newSufixo.charAt(newSufixo.length() - 1)) == -1
                            && !oldText.matches("[0-9.]+")) {
                        //Apaga também a quantidade de carateres do sufixo
                        oldText = oldText.substring(0, oldText.length() - newSufixo.length());
                    }
                }
                
                //Se tiver um sufixo - newText
                if (!newSufixo.isEmpty() && newText.length() > 1) {
                    //Se o último caracter foi apagado
                    if (newText.indexOf(newSufixo.charAt(newSufixo.length() - 1)) == -1
                            && !newText.matches("[0-9.]+")) {
                        //Apaga também a quantidade de carateres do sufixo
                        newText = newText.substring(0, newText.length() - newSufixo.length());
                    }
                }

                //Limita a quantidade de caracteres no campo
                if (newText.length() <= limitCaractere) {
                    //Garante a retirada do traço de valor negativo quando o valor for zero
                    newText = (oldText.replaceAll("[^0-9-]", "").equals("-"+addZerosDireta("", casasDecimais))) ? "0" : newText;
                    //Retira tudo que não for número
                    String valor = newText.replaceAll("[^0-9]", "");
                    
                    //Divide o valor por um inteiro e retorna um novo valor com o tanto de casas decimais solicitadas
                    textField.setText(newPrefixo + 
                            (permiteNegativo && (newText.indexOf("-") != -1 && !newText.matches("[0-9]*\\.[1-9]*-")) ? "-" : "") + 
                            AlgebraFloat.divisao(
                                new BigDecimal(valor),
                                new BigDecimal(addZerosDireta("1", casasDecimais)), casasDecimais).toString() + newSufixo);
                } else {
                    //Se passar do limite coloca o valor anterior
                    textField.setText(oldText);
                }
            }
        };

        //Aplica a conversão do número digitado para decimal
        textField.textProperty().addListener(listenerTextProperty);

        ChangeListener listenerAnchorProperty = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //Coloca o ponteiro sempre no final
                textField.positionCaret(textField.getText().length());
            }
        };

        //Aplica a mudança na posição do ponteiro
        textField.anchorProperty().addListener(listenerAnchorProperty);
    }

    /**
     * Adiciona caracteres "0" ao final da string passada por parametros na
     * quantidade pedida.
     *
     * @param valor
     * @param quantidade
     * @return String
     */
    private static String addZerosDireta(String valor, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            valor += "0";
        }

        return valor;
    }
}
