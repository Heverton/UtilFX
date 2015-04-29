package br.com.utilfx.components.tableview.editcommit;

import br.com.util.data.DataHora;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import br.com.util.string.ValidateString;
import br.com.utilfx.components.util.CellReflection;

/**
 * Administra a atualização do objeto da célula, mantendo-o de acordo com a
 * visualização.
 *
 * @author Herberts Cruz
 * @version 1.0
 */
public class CellEditCommit implements EventHandler<TableColumn.CellEditEvent<Object, Object>> {

    private final String projetion;
    private final boolean isObject;

    public CellEditCommit(String projetion) {
        this.projetion = projetion;
        this.isObject = false;
    }

    public CellEditCommit(String projetion, boolean isObject) {
        this.projetion = projetion;
        this.isObject = isObject;
    }

    @Override
    public void handle(TableColumn.CellEditEvent<Object, Object> c) {

        Object obj = null;
                            
        if (isObject) {
            //Manda em forma de objeto
            obj = c.getNewValue();            
        } else {
            //Seta o valor resebido no objeto para caso nenhuma das opões abaixo seja ativada 
            //Salvar, Esta opção é usada para valores <> de Date ou BigDecimal
            obj = c.getNewValue().toString();

            //Cria a variavel para ser usada nas comparações
            String newValue;
            //Verifica se o valor passado para ser salvo é uma data
            newValue = c.getNewValue().toString().replaceAll("[0-9|/]", "");

            if (newValue.isEmpty() && c.getNewValue().toString().contains("/")) {
                try {
                    //Caso o valor seja uma data converte o valor para Date
                    obj = (Date) DataHora.converterStringEmDate(c.getNewValue().toString(), DataHora.Formato.PT_DATA_SEPARADOR_BARRA_ANO_4DIGITOS);
                } catch (ParseException ex) {
                    Logger.getLogger(CellEditCommit.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Verifica se a variavel é de ponto flutuante
                newValue = c.getNewValue().toString().replaceAll("[^0-9.,]", "").replace(",", ".");
                //verifica se o valor tem ponto
                if (ValidateString.isNumber(newValue) && (c.getNewValue().toString().contains(",") || c.getNewValue().toString().contains("."))) {
                    obj = new BigDecimal(newValue);
                    //Verifica se o valor tem virgula
                } else if (ValidateString.isNumber(newValue, true, true) && (c.getNewValue().toString().contains(",") || c.getNewValue().toString().contains("."))) {
                    obj = new BigDecimal(newValue);
                }
            }
        }

        //Seta a alteração no pojo
        CellReflection.setValuePojo(
                c.getTableView().getItems().get(c.getTablePosition().getRow()),
                projetion,
                new Object[]{obj});
    }
}
