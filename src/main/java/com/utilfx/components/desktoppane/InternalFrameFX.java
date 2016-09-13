/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilfx.components.desktoppane;

import javafx.embed.swing.JFXPanel;
import javax.swing.JInternalFrame;

/**
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class InternalFrameFX extends JInternalFrame {  

    public InternalFrameFX(JFXPanel fXPanel) {
        this.add(fXPanel);
    }
    
}
