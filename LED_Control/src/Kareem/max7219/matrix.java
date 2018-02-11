/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kareem.max7219;

/**
 *
 * @author Kareem
 */

import java.io.IOException;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

public class matrix {
    
    	private static final boolean debug = false;
        
        protected static final short NUM_DIGITS = 8;
	//（>=1）
	protected short cascaded = 1;
	//
	protected byte[] buffer;
	//SPI
	protected SpiDevice spi;
        
        public static class Constants{
		public static byte MAX7219_REG_NOOP = 0x0;
		public static byte MAX7219_REG_DIGIT0 = 0x1;
		public static byte MAX7219_REG_DIGIT1 = 0x2;
		public static byte MAX7219_REG_DIGIT2 = 0x3;
		public static byte MAX7219_REG_DIGIT3 = 0x4;
		public static byte MAX7219_REG_DIGIT4 = 0x5;
		public static byte MAX7219_REG_DIGIT5 = 0x6;
		public static byte MAX7219_REG_DIGIT6 = 0x7;
		public static byte MAX7219_REG_DIGIT7 = 0x8;
		public static byte MAX7219_REG_DECODEMODE = 0x9;
		public static byte MAX7219_REG_INTENSITY = 0xA;
		public static byte MAX7219_REG_SCANLIMIT = 0xB;
		public static byte MAX7219_REG_SHUTDOWN = 0xC;
		public static byte MAX7219_REG_DISPLAYTEST = 0xF;
	}
        
        public static class Rows{
                public static byte Row1 = 0x01;
                public static byte Row2 = 0x02;
                public static byte Row3 = 0x04;
                public static byte Row4 = 0x08;
                public static byte Row5 = 0x10;
                public static byte Row6 = 0x20;
                public static byte Row7 = 0x40;
                public static byte Row8 = (byte) 0x80;
        }
        
        public matrix(){
		this.cascaded = 1;
		this.buffer = new byte[NUM_DIGITS*this.cascaded];
		
		try {
			if (!debug) {
				this.spi = SpiFactory.getInstance(SpiChannel.CS0, SpiDevice.DEFAULT_SPI_SPEED,SpiDevice.DEFAULT_SPI_MODE);
				
				command(matrix.Constants.MAX7219_REG_SCANLIMIT, (byte) 0x7);
				command(matrix.Constants.MAX7219_REG_DECODEMODE, (byte) 0x0);
				command(matrix.Constants.MAX7219_REG_DISPLAYTEST, (byte) 0x0);
				//command(Constants.MAX7219_REG_SHUTDOWN, (byte) 0x1);
				
				this.brightness((byte)3);
				//this.clear();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
        
        /**
	 * data register
	 * */
        public void command(byte register,byte data) throws Exception {
		
		int len = 2*this.cascaded;
		byte[] buf = new byte[len];
		
		for(int i=0;i<len;i+=2){
			buf[i]=register;
			buf[i+1]=data;
		}
		
		this._write(buf);
	}
        
        public void flush(){
		try{
			byte[] buf = this.buffer;
			
			for(short pos=0;pos<NUM_DIGITS;pos++){
				this._write(this._values(pos,buf));
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void brightness(byte intensity){
		try {
			if(intensity<0 || intensity>15) return;
			
			this.command(Constants.MAX7219_REG_INTENSITY, intensity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close(){
		try{
			this.clear();
			this.command(Constants.MAX7219_REG_SHUTDOWN, (byte)0x0);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void open(){
		try{
			this.command(Constants.MAX7219_REG_SHUTDOWN, (byte)0x1);
			this.clear();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void _write(byte[] buf) throws Exception{
		if(!debug){
			//for(byte b:buf) System.out.print(b+",");
			//System.out.println("");
			
			this.spi.write(buf);
		}
		else{
			for(byte b:buf) System.out.print(b+",");
			System.out.println("");
		}
	}
	/**
	  [position,data,position1,data1]
	 * */
	private byte[] _values(short position,byte[] buf) throws Exception {
		int len = 2*this.cascaded;
		byte[] ret = new byte[len];
		
		for(int i=0;i<this.cascaded;i++){
			ret[2*i]=(byte)((position+Constants.MAX7219_REG_DIGIT0)&0xff);
			ret[2*i+1]=buf[(i*NUM_DIGITS)+position];
			
		}
		return ret;
	}
	/**
	
	 * */
	private void _setbyte(int deviceId,short position,byte value){
		int offset = deviceId*NUM_DIGITS+position-Constants.MAX7219_REG_DIGIT0;
		this.buffer[offset]=value;
	}
        
        /**
	 *
	 * */
	public void clear(){
		
		try{
			for(int i=0;i<this.cascaded;i++){
				for(short j=0;j<NUM_DIGITS;j++){
					this._setbyte(i, (short)(j+Constants.MAX7219_REG_DIGIT0), (byte)0x00);
				}
			}
			this.flush();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
        
        public void pixel_on(int row, short col){
            byte value = 0x00;
            try{
                switch(row){
                        case 0: value = Rows.Row1;
                                break;
                        case 1: value = Rows.Row2;
                                break;
                        case 2: value = Rows.Row3;
                                break;
                        case 3: value = Rows.Row4;
                                break;
                        case 4: value = Rows.Row5;
                                break;
                        case 5: value = Rows.Row6;
                                break;
                        case 6: value = Rows.Row7;
                                break;
                        case 7: value = Rows.Row8;
                                break;
                        default: break;
                }
                            
                this._setbyte(0, (short)(col+Constants.MAX7219_REG_DIGIT0), value);
                this.flush();
                
            }catch(Exception ex){
			ex.printStackTrace();
		}	
	}
        
        public void pixel_off(int row, short col){
            try{
                this._setbyte(0, (short)(col+Constants.MAX7219_REG_DIGIT0), (byte)0x00);
                this.flush();
            }catch(Exception ex){
			ex.printStackTrace();
		}
        }
}
