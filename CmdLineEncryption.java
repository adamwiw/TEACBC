import java.io.*;

public class CmdLineEncryption {
    public static void decrypt(BufferedReader reader) {
        String cipherText = "";
        System.out.println("Input cipher text:");
        try {
            cipherText = reader.readLine();
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        int IV = 0x9212ca45;
        int KEY = 0xcdab6ba5;
        String[] cipherArray = cipherText.split(" ");
        int[][] cipher = new int[cipherArray.length / 2][2];
        int m = 0;
        try {
            for(int n = 0; n < cipherArray.length / 2; n++) {
                cipher[n][0] = Integer.parseInt(cipherArray[m]);
                cipher[n][1] = Integer.parseInt(cipherArray[m + 1]);
                m+=2;
            }
        } catch(NumberFormatException ex) {
            System.out.println("Exception! Are you sure you're putting in numbers?");
        }
        CBC decryption = new CBC(KEY, IV);
        System.out.println(CBC.decrypt(cipher));
    }
    
    public static void encrypt(BufferedReader reader) {
        String plainText = "";
        System.out.println("Input plain text:");
        try {
            plainText = reader.readLine();
        } catch (IOException ex) {
            System.out.println("IOException!");
        }
        
        int IV = 0x9212ca45;
        int KEY = 0xcdab6ba5;
        CBC encryption = new CBC(KEY, IV);
        int[][] cipher = CBC.encrypt(plainText);
        for(int n = 0; n < cipher.length; n++)
            System.out.print(cipher[n][0] + " " + cipher[n][1] + " ");
    }

    public static void main(String[] args) {
        BufferedReader reader =	new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        
        System.out.println("Do you want to decrypt or encrypt?");
        try {
            input = reader.readLine();
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        if(input.equals("decrypt"))
            decrypt(reader);
        else
            encrypt(reader);
    }
}