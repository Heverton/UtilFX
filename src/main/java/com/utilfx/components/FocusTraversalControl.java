package com.utilfx.components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Faz o controle de passagem de foco dos componentes.
 * 
 * @author Heverton Cruz
 * @version 1.0
 */
public class FocusTraversalControl {
    private KeyCode key;
    private Map<Node, EventHandler<KeyEvent>> originarios = new HashMap();
    
    private FocusTraversalControl(Map<Node, Node> combinacaoComponentes, KeyCode key) {
        this.key = key;
        
        Iterator<Node> componenteOriginarioi = combinacaoComponentes.keySet().iterator();

        while (componenteOriginarioi.hasNext()) {
            final Node componenteOriginario = componenteOriginarioi.next();
            addEvent(componenteOriginario, combinacaoComponentes.get(componenteOriginario));
        }
    }

    public FocusTraversalControl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public final void addEvent(Node componenteOriginario, final Node componenteDestinatario) {
        EventHandler handler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == key) {
                    componenteDestinatario.requestFocus();
                }
            }
        };
        //Atribui o evento ao componente
        componenteOriginario.addEventFilter(KeyEvent.KEY_RELEASED, handler);
        //Registra o componente originário e seu evento
        originarios.put(componenteOriginario, handler);
    }
    
    public final void removeEvent(Node componenteOriginario) {
        //Se foi cadastrado algum evente para o componente
        if(originarios.containsKey(componenteOriginario)) {
            EventHandler handler = originarios.get(componenteOriginario);
            componenteOriginario.removeEventFilter(KeyEvent.KEY_RELEASED, handler);
        } else {
            throw new NullPointerException("Não foi atribuido nenhum evento para este componente.");
        }
    }
    
    /**
     * Faz o controle de foco de um componente originário para um destinatário com a tecla padrão ENTER.
     *
     * @param combinacaoComponentes
     */
    public static FocusTraversalControl setFocusTraversalControl(Map<Node, Node> combinacaoComponentes) {
        return new FocusTraversalControl(combinacaoComponentes, KeyCode.ENTER);
    }
    
    /**
     * Faz o controle de foco de um componente originário para um destinatário com a tecla passada.
     *
     * @param combinacaoComponentes
     * @param key
     */
    public static FocusTraversalControl setFocusTraversalControl(Map<Node, Node> combinacaoComponentes, KeyCode key) {
        return new FocusTraversalControl(combinacaoComponentes, key);
    }
}
