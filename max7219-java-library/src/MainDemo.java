import java.io.IOException;

import cn.sharetop.max7219.Led;


public class MainDemo {

	public static void main(String[] args){
		
		//وœ‰ن¸¤ه‌—(8*8)çڑ„ه±ڈه¹•
		Led c = new Led((short)2);
		
		//و‰“ه¼€è®¾ه¤‡
		c.open();
		
		//و—‹è½¬270ه؛¦ï¼Œç¼؛çœپن¸¤ن¸ھه±ڈه¹•وک¯ن¸ٹن¸‹وژ’هˆ—ï¼Œوˆ‘éœ€è¦پçڑ„وک¯ه·¦هڈ³وژ’
		c.orientation(270);
		
		//DEMO1: è¾“ه‡؛ن¸¤ن¸ھه­—و¯چ
		//c.letter((short)0, (short)'Y',false);
		//c.letter((short)1, (short)'C',false);
		//c.flush();
		
		//DEMO2: è¾“ه‡؛ن¸¤ن¸ھو±‰ه­—ï¼Œو¯”è¾ƒCHOU
		//c.letter((short)0, (short)0,Font.CHN_FONT,false);
		//c.letter((short)1, (short)1,Font.CHN_FONT,false);
		//c.flush();
		
		//DEMO3: è¾“ه‡؛ن¸€ن¸²ه­—و¯چ
		c.showMessage("Hello 0123456789$");
		
		try {
			System.in.read();
			c.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
