/**
 * Write a description of class CBC here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CBC extends TEA {
    static int init;
    
    /**
     * Constructor for objects of class CBC
     */
    public CBC(int KEY, int init) {
        super(KEY);
        init = init;
    }
    
    /**
    * Encrypt the text using CBC
    */
    public static int[][] encrypt(String plainText) {
        int blocks = ((plainText.length() - (plainText.length() % 8)) / 8);
        int[][] c = new int[blocks][];
        String blk, l, r;
        int i, j;
        
        if((plainText.length() % 8) == 0) {
            for(i = 0, j = 0; i < blocks; i++, j = j + 8) {
                blk = plainText.substring(j, j + 8);
                l = blk.substring(0, 4);
                r = blk.substring(4, blk.length());
                if(i == 0) {
                    int[] p = {byteint(l) ^ init, byteint(r) ^ init};
                    c[i] = teaEncrypt(p);
                } else {
                    int[] p = {byteint(l) ^ c[i - 1][0], byteint(r) ^ c[i - 1][1]};
                    c[i] = teaEncrypt(p);
                }
            }
        } else {
            blocks++;
            
            for(i = 0, j = 0; i < blocks - 1; i++, j = j + 8) {
                blk = plainText.substring(j, j + 8);
                l = blk.substring(0, 4);
                r = blk.substring(4, blk.length());
                if(i == 0) {
                    int[] p = {byteint(l) ^ init, byteint(r) ^ init};
                    c[i] = teaEncrypt(p);
                } else {
                    int[] p = {byteint(l) ^ c[i - 1][0], byteint(r) ^ c[i - 1][1]};
                    c[i] = teaEncrypt(p);
                }
            }
            blk = plainText.substring(j, plainText.length());
            if(blk.length() < 8 && blk.length() > 0) {
                while(blk.length() < 8) {
                    blk = blk + " ";
                }
                l = blk.substring(0, 4); 
                r = blk.substring(4, blk.length());
                if(i == 0) {
                    int[] p = {byteint(l) ^ init, byteint(r) ^ init};
                    c[i] = teaEncrypt(p);
                } else {
                    int[] p = {byteint(l) ^ c[i - 1][0], byteint(r) ^ c[i - 1][1]};
                    c[i] = teaEncrypt(p);
                }
            }
        }
        return c;
    }
    
    /**
     * Decrypt the cipher with CBC
     */
    public static String decrypt(int[][] c) {
        String plainText = "";
        for(int i = 0; i < c.length; i++) {
            int[] p_blk = teaDecrypt(c[i]);
            if(i == 0) {
                p_blk[0] = init ^ p_blk[0];
                p_blk[1] = init ^ p_blk[1];
            } else {
                p_blk[0] = c[i - 1][0] ^ p_blk[0];
                p_blk[1] = c[i - 1][1] ^ p_blk[1];
            }
            plainText = plainText + intbyte(p_blk[0]) + intbyte(p_blk[1]);
        }
        return plainText;
    }
    
    /**
     * Convert a stream of bytes(ASCII) into an integer
     */
    public static int byteint(String bytes) {
        StringBuilder bs = new StringBuilder();
        byte[] b = bytes.getBytes();

        for(byte ob: b)
			if(ob < 32)
				bs.append("000").append(Integer.toBinaryString(ob));
			else if(ob >= 32 && ob < 64)
				bs.append("00").append(Integer.toBinaryString(ob));
			else if(64 <= ob && ob < 128)
				bs.append("0").append(Integer.toBinaryString(ob));
			else
				bs.append(Integer.toBinaryString(ob));
		return Integer.parseInt(bs.toString(), 2);
	}
	
	/**
     * Convert an integer into a stream of bytes
     */
    public static String intbyte(int num) {
		String bs = Integer.toBinaryString(num);
		
		while(bs.length() < 32)
			bs = "0" + bs;
			
		byte[] byteArray = new byte[4];
		for(int m = 0, n = 0; m < bs.length(); m += 8, n++) {
			String b = bs.substring(m, m + 8);
			byteArray[n] = (byte)Integer.parseInt(b, 2);
		}
		
		String result = "";
		for(byte ob: byteArray)
			result = result + (char)ob;
		return result;
	}
}