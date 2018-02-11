/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package led_control;

/**
 *
 * @author Kareem
 */

import Kareem.max7219.matrix;

public class LED_Control {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
        //int row = 2;
        //short col = 4;
        
        matrix x = new matrix();
        
        x.open();
        for(int row=0; row<=7; row++){
            for(short col=0; col<=7; col++){
                x.pixel_on(row,col); 
                Thread.sleep(1000);
                x.pixel_off(row,col);
            }
        }
        
        x.close();
    }
    
}
