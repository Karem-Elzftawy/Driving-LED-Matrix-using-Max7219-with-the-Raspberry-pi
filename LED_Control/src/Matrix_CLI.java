/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kareem
 */

import Kareem.max7219.matrix;
import java.util.Scanner;

public class Matrix_CLI {
       /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
    
        matrix x = new matrix();
        
        x.open();
        
        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        
        while(true){
        //  prompt for the Row
        System.out.print("Enter the Row Number: ");
        // get the row as a int
        int row = scanner.nextInt();
        
        //  prompt for the column
        System.out.print("Enter the column Number: ");
        // get the col as a int
        short col = scanner.nextShort();
        x.clear();
        x.pixel_on(row, col);
        x.brightness((byte)15);
        }
        
        //x.close();
        
    }
    }
