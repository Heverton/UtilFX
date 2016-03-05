package br.com.utilfx.components.textfield.filter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import util.string.ValidateString;

/**
 * Faz a formatação do campo para valores numéricos com ponto flutuante.
 *
 * @author Heverton Cruz
 * @version 2.0
 * @deprecated Substituir por FloatTextInputControl.
 */
public class FloatFormat {

    private enum TipoNumerico {
        DECIMAL, INTEIRO
    };
    private TextField textField;
    private String newPrefixo = "";
    private String newSufixo = "";
    private boolean permiteNegativo = true;
    private boolean limparAoGanharFoco = false;
    private int casasDecimais = 2;
    private int limitCaractere = 20;
    private FocusedDecimalChangeListener focusedDecimalChangeListener = new FocusedDecimalChangeListener();
    private FocusedInteiroChangeListener focusedInteiroChangeListener = new FocusedInteiroChangeListener();
    private TextDecimalChangeListener textDecimalChangeListener = new TextDecimalChangeListener();
    private TextInteiroChangeListener textInteiroChangeListener = new TextInteiroChangeListener();

    private FloatFormat(final TextField textField) {
        //Verifica se o componente é nulo
        if (textField == null) {
            throw new NullPointerException("O componente TextField não pode ser nulo.");
        }
        //Atribui os eventos padrão
        textField.focusedProperty().addListener(focusedDecimalChangeListener);
        textField.textProperty().addListener(textDecimalChangeListener);

        EventHandler handlerMouseClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    selecionaNumero();
                }
            }
        };
        //Aplica limpar o campo quando clicar no campo
        textField.setOnMouseClicked(handlerMouseClicked);

        ChangeListener listenerAnchorProperty = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (!newPrefixo.isEmpty()) {
                    //Se o caret estiver sobre um caracter do sufixo
                    if (newValue.intValue() < newPrefixo.length()) {
                        //Não permite retroceder mais
                        textField.positionCaret(newPrefixo.length());
                    }
                }
                if(!newSufixo.isEmpty()) {
                    int inicioSufixo = textField.getText().length() - newSufixo.length();
                    //Se o caret estiver sobre um caracter do sufixo
                    if (newValue.intValue() > inicioSufixo) {
                        //Não permite avançar mais
                        textField.positionCaret(inicioSufixo);
                    }
                }
            }
        };
        //Aplica a mudança na posição do ponteiro
        textField.anchorProperty().addListener(listenerAnchorProperty);
        //Seta o TextField na variável global
        this.textField = textField;
        //Seta um valor padrão inicial para o TextField
        setValorPadraoDoTipo(TipoNumerico.DECIMAL);
    }

    private class FocusedDecimalChangeListener implements ChangeListener<Boolean> {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue.booleanValue()) {
                setValorPadraoDoTipo(TipoNumerico.DECIMAL);
            } else if (limparAoGanharFoco) {
                setValorPadraoDoTipo(TipoNumerico.DECIMAL, true);
            }
            
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    selecionaNumero();
                }
            });
        }
    }

    private class FocusedInteiroChangeListener implements ChangeListener<Boolean> {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue.booleanValue()) {
                setValorPadraoDoTipo(TipoNumerico.INTEIRO);
            } else if (limparAoGanharFoco) {
                setValorPadraoDoTipo(TipoNumerico.INTEIRO, true);
            }
            
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    selecionaNumero();
                }
            });
        }
    }

    private class TextDecimalChangeListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> values, String oldText, String newText) {
            //Garante que não haveram valores nulos
            oldText = (oldText == null) ? "" : oldText;
            newText = (newText == null) ? "" : newText;
            //Calcula a quantidade de caracteres permitidos para o sufixo para garantir as casas decimais como esperado
            int limitCaractereSufixo = limitCaractere - ((newPrefixo + newSufixo).length() + casasDecimais + 2);
            //Retira os caracteres não numéricos, vírgula e ponto
            //e troca o ponto por vírgula
            String oldValor = oldText.replaceAll("[^0-9,.]", "").replaceAll("\\.", ",");
            String newValor = newText.replaceAll("[^0-9,.]", "").replaceAll("\\.", ",");
            //Limita a quantidade de caracteres no campo
            if (newText.length() <= limitCaractere) {
                String valorSufixo;
                String valorPrefixo;
                String valorResultante;
                //Se tiver uma vírgula
                if (newValor.indexOf(",") != -1) {
                    //Pega todos os dígitos antes da vírgula
                    valorSufixo = newValor.substring(0, newValor.indexOf(","));
                    //Se não houver dígitos antes da vírgula
                    valorSufixo = (valorSufixo.isEmpty()) ? "0" : valorSufixo;
                    //Retira os zeros a esquerda e acrescenta a vírgula ao final do número
                    valorSufixo = Integer.parseInt(valorSufixo) + "";
                    //Pega todos os dígitos depois da vírgula e retira qualquer vírgula a mais colocada
                    valorPrefixo = newValor.substring(newValor.indexOf(",") + 1).replaceAll(",", "");
                    //Se o número de caracteres for maior que o número permitido
                    valorPrefixo = (valorPrefixo.length() > casasDecimais)
                            ? valorPrefixo.substring(0, casasDecimais) : valorPrefixo;
                    //Acrescenta a vírgula se existir algum valor de prefixo
                    valorPrefixo = "," + valorPrefixo;
                } else {
                    //Controla a quantidade de caracteres para garantir as casas decimais como esperado
                    valorSufixo = (newValor.length() < limitCaractereSufixo) ? newValor : newValor.substring(0, limitCaractereSufixo);
                    valorPrefixo = (newValor.equals("0") && !oldValor.equals("0,")) ? "," + addZerosDireta("", casasDecimais) : "";
                }
                //Resultado da concatenação de sufixo com o prefixo
                valorResultante = valorSufixo + valorPrefixo;
                //Pega o caracter traço do número negativo se houver, se o valor 
                //resultante não for zero e não houver o caracter de soma na String
                String negativo = (newText.indexOf("-") != -1
                        && !valorResultante.matches("0+,0+")
                        && newText.indexOf("+") == -1
                        && permiteNegativo) ? "-" : "";
                //Formata para o valor que ficará no campo
                String valorFinal = newPrefixo + negativo + valorResultante + newSufixo;
                //Seta o valor no campo
                textField.setText(valorFinal);
            } else {
                //Se passar do limite coloca o valor anterior
                textField.setText(oldText);
            }
        }
    }

    private class TextInteiroChangeListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> values, String oldText, String newText) {
            //Garante que não haveram valores nulos
            oldText = (oldText == null) ? "" : oldText;
            newText = (newText == null) ? "" : newText;
            //Retira os caracteres não numéricos, vírgula e ponto
            //e troca o ponto por vírgula, também retira casas decimais se houverem
            String valor = (newText.indexOf(".") != -1) 
                    ? newText.substring(0, newText.indexOf(".")).replaceAll("[^0-9]", "") 
                    : newText.replaceAll("[^0-9]", "");
            //Limita a quantidade de caracteres no campo
            if (newText.length() <= limitCaractere) {
                //Retira os zeros a esquerda e acrescenta a vírgula ao final do número
                String valorInteiro = Integer.parseInt((valor.isEmpty()) ? "0" : valor) + "";
                //Pega o caracter traço do número negativo se houver, se o valor 
                //resultante não for zero e não houver o caracter de soma na String
                String negativo = (newText.indexOf("-") != -1
                        && !valorInteiro.matches("0+")
                        && newText.indexOf("+") == -1
                        && permiteNegativo) ? "-" : "";
                //Formata para o valor que ficará no campo
                String valorFinal = newPrefixo + negativo + valorInteiro + newSufixo;
                //Seta o valor no campo
                textField.setText(valorFinal);
            } else {
                //Se passar do limite coloca o valor anterior
                textField.setText(oldText);
            }
        }
    }

    /**
     * Seta um valor padrão inicial para o TextField de acordo com o tipo.
     *
     * @param tipo
     */
    private void setValorPadraoDoTipo(TipoNumerico tipo) {
        setValorPadraoDoTipo(tipo, false);
    }

    /**
     * Seta um valor padrão inicial para o TextField de acordo com o tipo e 
     * da a opção de informar se este será zerado ou não.
     *
     * @param tipo
     * @param isSetZerado
     */
    private void setValorPadraoDoTipo(TipoNumerico tipo, boolean isSetZerado) {
        String valor = (isSetZerado) ? "0" : textField.getText();
        
        if (tipo == TipoNumerico.DECIMAL) {
            textField.setText(newPrefixo + addZerosDireta(valor, casasDecimais) + newSufixo);
        } else {
            textField.setText(newPrefixo + addZerosDireta(valor) + newSufixo);
        }
    }

    /**
     * Seleciona apenas a parte numérica do campo.
     */
    private void selecionaNumero() {
        if (!newPrefixo.isEmpty() && !newSufixo.isEmpty()) {
            textField.selectRange(newPrefixo.length(), textField.getText().length()-newSufixo.length());
        } else if(!newPrefixo.isEmpty()) {
            textField.selectRange(newPrefixo.length(), textField.getText().length());
        } else if(!newSufixo.isEmpty()) {
            textField.selectRange(0, textField.getText().length()-newSufixo.length());
        } else {
            textField.selectRange(0, textField.getText().length());
        }
    }

    /**
     * Adiciona caracteres "0" ao final da string passada por parametros na
     * quantidade pedida.
     *
     * @param valor
     * @param quantidade
     * @return String
     */
    private String addZerosDireta(String valor) {
        return addZerosDireta(valor, 0);
    }

    /**
     * Adiciona caracteres "0" ao final da string passada por parametros na
     * quantidade pedida.
     *
     * @param valor
     * @param quantidade
     * @return String
     */
    private String addZerosDireta(String valor, int casasDecimais) {
        String valorSufixo;
        String valorPrefixo;
        //Se tiver uma vírgula
        if (valor.matches(".*[,.].*")) {
            //Converte ponto em vírgula
            valor = valor.replaceAll("\\.", ",");
            //Pega o índice da posição depois da vírgula
            int posicaoDepoisVirgula = valor.indexOf(",");
            //Pega o valor antes da vírgula com a vírgula
            valorSufixo = valor.substring(0, posicaoDepoisVirgula);
            //Pega o valor depois da vírgula
            valorPrefixo = (casasDecimais > 1) ? valor.substring(posicaoDepoisVirgula) : "";
        } else {
            valorSufixo = valor;
            valorPrefixo = (casasDecimais > 1) ? "," : "";
        }
        //Adiciona os zeros à esquerda
        while (valorPrefixo.length() <= casasDecimais && casasDecimais > 1) {
            valorPrefixo += "0";
        }

        return (valorSufixo + valorPrefixo);
    }

    public String getPrefixo() {
        return newPrefixo;
    }

    public void setPrefixo(String prefixo) {
        if (prefixo.matches("[0-9,.+-]+")) {
            throw new RuntimeException("O sufixo não pode conter caracteres numéricos e nem os caracteres \",.+-\".");
        }
        
        this.newPrefixo = (ValidateString.isNullOrEmpty(prefixo)) ? "" : prefixo + " ";
    }

    public String getSufixo() {
        return newSufixo;
    }

    public void setSufixo(String sufixo) {
        if (sufixo.matches("[0-9,.+-]+")) {
            throw new RuntimeException("O sufixo não pode conter caracteres numéricos e nem os caracteres \",.+-\".");
        }
        
        this.newSufixo = (ValidateString.isNullOrEmpty(sufixo)) ? "" : " " + sufixo;
    }

    public boolean isPermiteNegativo() {
        return permiteNegativo;
    }

    public void setPermiteNegativo(boolean permiteNegativo) {
        this.permiteNegativo = permiteNegativo;
    }

    public boolean isLimparAoGanharFoco() {
        return limparAoGanharFoco;
    }

    public void setLimparAoGanharFoco(boolean limparAoGanharFoco) {
        this.limparAoGanharFoco = limparAoGanharFoco;
    }

    public int getCasasDecimais() {
        return casasDecimais;
    }

    public void setCasasDecimais(int casasDecimais) {
        if (casasDecimais < 1) {
            throw new ArithmeticException("O número de casas decimais deve ser definido com no mínimo 1(uma).");
        }

        this.casasDecimais = casasDecimais;
    }

    public int getLimitCaractere() {
        return limitCaractere;
    }

    public void setLimitCaractere(int limitCaractere) {
        this.limitCaractere = limitCaractere;
    }

    public static FloatFormat numberFloat(TextField textField) {
        return new FloatFormat(textField);
    }

    /**
     * Converte o campo para aceitar apenas valores decimais ou inteiro.
     * 
     * @param valor 
     */
    public void exibirCasasDecimais(boolean valor) {
        //Se deve ser Decimal
        if (valor) {
            //Inteiro
            textField.focusedProperty().removeListener(focusedInteiroChangeListener);
            textField.textProperty().removeListener(textInteiroChangeListener);
            //Decimal
            textField.focusedProperty().removeListener(focusedDecimalChangeListener);
            textField.textProperty().removeListener(textDecimalChangeListener);
            textField.focusedProperty().addListener(focusedDecimalChangeListener);
            textField.textProperty().addListener(textDecimalChangeListener);

            setValorPadraoDoTipo(TipoNumerico.DECIMAL);
            //Se deve ser Inteiro
        } else {
            //Decimal
            textField.focusedProperty().removeListener(focusedDecimalChangeListener);
            textField.textProperty().removeListener(textDecimalChangeListener);
            //Inteiro
            textField.focusedProperty().removeListener(focusedInteiroChangeListener);
            textField.textProperty().removeListener(textInteiroChangeListener);
            textField.focusedProperty().addListener(focusedInteiroChangeListener);
            textField.textProperty().addListener(textInteiroChangeListener);

            setValorPadraoDoTipo(TipoNumerico.INTEIRO);
        }
    }
}
