/**
 * Write a description of class TEA here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TEA {
    static int KEY;
    final static int DELTA = 0x9e3779b9; // Delta    

    /**
     * Constructor for objects of class TEA
     */
    public TEA(int KEY) {
        KEY = KEY;
    }
    
    /**
     * Tiny Encryption Algorithm encryption
     */
    public static int[] teaEncrypt(int[] plainText) {
        int l = plainText[0];
        int r = plainText[1];
        int mult = 255 / 32;
        int sum = 0;
        for(int i = 0; i < 32; i++) {
            sum += DELTA;
            l += ((r << 4) + KEY) ^ (r + sum) ^ ((r >> 5) + KEY);
            r += ((l << 4) + KEY) ^ (l + sum) ^ ((l >> 5) + KEY);
        }
        int[] cipherText = {l, r};
        return cipherText;
    }
    
    /**
     * Tiny Encryption Algorithm decryption
     */
    public static int[] teaDecrypt(int[] cipherText) {
        int mult = 255 / 32;
        int l = cipherText[0];
        int r = cipherText[1];
        int sum = DELTA << 5;
        
        for(int i = 0; i < 32; i++) {
            r -= ((l << 4) + KEY) ^ (l + sum) ^ ((l >> 5) + KEY);
            l -= ((r << 4) + KEY) ^ (r + sum) ^ ((r >> 5) + KEY);
            sum -= DELTA;
        }
        int[] plainText = {l, r};
        return plainText;
    }
}