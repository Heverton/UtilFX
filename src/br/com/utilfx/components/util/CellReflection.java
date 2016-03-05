package br.com.utilfx.components.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import br.com.util.string.ManipuleString;

/**
 * Navega e executa os métodos get, is e set de uma classe até o último nó.
 *
 * @author Heverton Cruz, Heverton Cruz
 * @version 1.2
 */
public class CellReflection {

    /**
     * Captura o valor de um objeto pojo através da sintaxe de ponto formada
     * pelo nome das variáveis.
     *
     * Ex.: projetion_1.projetion_1-1.projetion_1-2 <=>
     * getVariavel_1().getVariavel_1-1().getVariavel_1-2();
     *
     * @param classe
     * @param projetion
     * @return
     */
    public static Object getValuePojo(Object classe, String projetion) {
        Object retorno = null;
        String[] nomesMetodos = projetion.split("\\.");

        if (classe != null) {
            for (String nomeMetodo : nomesMetodos) {
                try {
                    retorno = executeMetodo(classe, "get" + ManipuleString.toCamelCase(nomeMetodo, true));
                } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex1) {
                    try {
                        retorno = executeMetodo(classe, "is" + ManipuleString.toCamelCase(nomeMetodo, true));
                    } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex2) {
                        ex2.printStackTrace();
                        throw new RuntimeException("Método não encontrado, verifique a sintaxe de pontos.");
                    }
                }
                //Verifica se o resultado é uma coleção
                if (retorno instanceof Collection) {
                    throw new RuntimeException("Não é possível navegar em um nó que "
                            + "seja formado por uma coleção de dados.");
                }
                //Atribui o valor de retorno ao valor do objeto classe, para que se 
                //houver outro nó, ele seja executado
                classe = retorno;
            }
        }

        return retorno;
    }

    /**
     * Insere o valor de um objeto pojo através da sintaxe de ponto formada pelo
     * nome das variáveis.
     *
     * Ex.: projetion_1.projetion_1-1.projetion_1-2 <=>
     * getVariavel_1().getVariavel_1-1().setVariavel_1-2();
     *
     * @param classe
     * @param projetion
     * @param parametros
     */
    public static void setValuePojo(Object classe, String projetion, Object[] parametros) {

        String[] nomesMetodos = projetion.split("\\.");

        try {
            for (int i = 0; i < nomesMetodos.length; i++) {
                //Se for o último resultado
                if (i == nomesMetodos.length - 1) {
                    //Cria o nome do método
                    String nomeMetodo = "set" + ManipuleString.toCamelCase(nomesMetodos[i], true);
                    //Pega todos os métodos da classe
                    Method[] metodos = classe.getClass().getMethods();

                    for (int j = 0; j < metodos.length; j++) {
                        try {
                            //Pega os tipos dos parametros
                            Class[] tiposParametros = metodos[j].getParameterTypes();
                            //Se o nome do método atual for igual ao enviado e tiver 
                            //a mesma quantidade de parametros e tipos
                            if (metodos[j].getName().equals(nomeMetodo) && parametros.length == tiposParametros.length) {
                                executeMetodo(classe, nomeMetodo, tiposParametros, parametros);
                            }
                        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            //Se for o último resultado
                            if (i == metodos.length - 1) {
                                throw ex;
                            }
                        }
                    }
                } else {
                    classe = executeMetodo(classe, "get" + ManipuleString.toCamelCase(nomesMetodos[i], true));
                    //Verifica se o resultado é uma coleção
                    if (classe instanceof Collection) {
                        throw new RuntimeException("Não é possível navegar em um nó que "
                                + "seja formado por uma coleção de dados.");
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Método não encontrado, verifique a sintaxe de pontos.");
        }
    }

    /**
     * Executa um método.
     *
     * @param classe
     * @param nomeMetodo
     * @param tiposParametros
     * @param parametros
     * @return
     */
    private static Object executeMetodo(Object classe, String nomeMetodo) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return executeMetodo(classe, nomeMetodo, null, null);
    }

    /**
     * Executa um método.
     *
     * @param classe
     * @param nomeMetodo
     * @param tiposParametros
     * @param parametros
     * @return
     */
    private static Object executeMetodo(Object classe, String nomeMetodo, Class<?>[] tiposParametros, Object[] parametros) 
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (tiposParametros == null) {
            tiposParametros = new Class[]{};
        }
        if (parametros == null) {
            parametros = new Object[]{};
        }

        //Pega o objeto Method do método da classe
        Method metodo = classe.getClass().getMethod(nomeMetodo, tiposParametros);
        //Invoca o método com seus parametros
        return metodo.invoke(classe, parametros);
    }
}
