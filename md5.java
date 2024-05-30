import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class md5 {
    public static void main(String args[]){
        // System.out.println("Main is called");


        if (args.length < 1) System.out.println("no mode specified");
        else{
            if (args[0].compareTo("encode") == 0) encode(args[1]);
            else if (args[0].compareTo("decode") == 0) decode(args[1]);
            else if (args[0].compareTo("test") == 0) {}
            else System.out.println("mode doesnt exist");
        }


    }

    public static void printBinary(String line) {
        String holder = "";
        byte[] lineBytes = line.getBytes(StandardCharsets.US_ASCII);


        for (int index = 0; index < lineBytes.length; index++) {
            String byteString = Integer.toBinaryString(lineBytes[index]);
            holder +=  String.format("%8s", byteString).replace(' ', '0') + " ";
        }

        System.out.println(holder);
    }

    public static String printBinary(byte byteLine) { // overloading 
        //System.out.println(String.format("%8s", Integer.toBinaryString(byteLine)).replace(' ', '0') + " ");
        return String.format("%8s", Integer.toBinaryString(byteLine)).replace(' ', '0') + " ";
        // changed it to return for clearer debugging msgs
    }

    


    public static byte[][] paddingPassword(byte[] byteArray) throws Exception {

        int multipleOf64 = byteArray.length / 64; 
        int remainingBytes = byteArray.length % 64;
        long bits = byteArray.length * 8; // number of bits size of 8 bytes
        // NOTE: there is no failsafe for number of bits > 2^64 
        // however, we don't need to take this into consideration bc it is unlikely
        // for any input to be greater than that. Also java strings are limited in size,
        // well under 2^64. See: https://stackoverflow.com/questions/3394503/maximum-length-for-md5-input-output
        
        int blocks = multipleOf64;
        byte[][] padded2D;
        if (remainingBytes < 56) blocks += 1; //if < 56 bytes, only 1 more block needed
        else if (remainingBytes >= 56) blocks += 2; //if >= 56 bytes, 2 more blocks needed 
        padded2D = new byte[blocks][64];

        // then, copy over the bytes into the 2D array
        int curBlock = 0;
        for(int x = 0; x < byteArray.length; x++){
            padded2D[curBlock][x % 64] = byteArray[x]; // copy original bytes into new array
            if (x % 64 == 63) curBlock++; // move to next block after every 64 bytes
        }
        padded2D[curBlock][byteArray.length % 64] = (byte) 128; // add '10000000' byte (does not give '10000000' but instead '111111....000000') | Fixed in splitIntoWords
        // check whether to pad on last block, or last two depending on remainingBytes
        if (remainingBytes < 56){
            for(int x = (byteArray.length % 64) + 1 ; x < 56; x++){ // stop on byte 57
                padded2D[curBlock][x] = 0; //pad with 0s
            }
        }
        else if (remainingBytes >= 56) {
            for(int x = (byteArray.length % 64) + 1 ; x < 64; x++) { // completely fill 2nd to last block
                padded2D[curBlock][x] = 0; //pad with 0s
            } 
            for(int x = 0; x < 56; x++) { // completely fill 2nd to last block
                padded2D[curBlock + 1][x] = 0; //pad with 0s
            }
            curBlock += 1;
        }
        // add message length bits to last 8 bytes
        for (int x = 0; x < 8; x++) {
            padded2D[curBlock][63-x] = (byte)(bits >> (x * 8));
            // may give out leading 1's | Fixed in splitIntoWords
        }
        // and done!

        // print test
        // for (byte[] arr : padded2D) {
        //     // int index = 0;
        //     // for (byte element : arr) { 
                
        //     //     if (index < 10) System.out.println(index + ":  " + printBinary(element) );
        //     //     else System.out.println(index + ": " + printBinary(element) );

        //     //     index++;
        //     // }
        //     printByteArray(arr);
        //     System.out.println("End of block");
        // }
        
        return padded2D;
    }

    public static int[][] splitIntoWords(byte[][] padded) {
        
        int[][] wordList = new int[padded.length][16]; // 16 words in 512 bit block

        // storing as an int 
        for (int x = 0; x < padded.length; x++) {
            for (int y = 0; y < 64;) {
                
                int holder = padded[x][y];
                for (int z = 0; z < 4; z++) {
                    holder = holder << 8;
                    holder = holder | (padded[x][y++] & 0xff);  // leading 1's are fixed here

                }
                wordList[x][y/4 - 1] = holder;
            }
        }

        // print test
        // for (int[] arr: wordList) {
        //     for (int integer: arr) {
        //         System.out.println(String.format("%32s", Integer.toBinaryString(integer)).replace(' ', '0') + " ");
        //     }
        // }

        return wordList;
    }

    public static void functionF(int[][] wordList, int block, int step, int A, int B, int C, int D) {
        
        int[] Mi = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

        long fResult = (B & C) | (~B & D); 
        // Check
        // System.out.println(0xfedcba98 == fResult);
        long FandA = (A + fResult) % 0x100000000L;
        // System.out.println(FandA == 0xffffffff);
        long FAM = (FandA + wordList[block][Mi[step]]) % 0x100000000L;
        // System.out.println(wordList[block][Mi[step]] == 0x54686579);
        // System.out.println(FAM == 0x54686578);


    }


    public static void encode(String line){
        byte[] lineBytes = line.getBytes(StandardCharsets.US_ASCII);
    
        try {
            // padding
            byte[][] padded = paddingPassword(lineBytes);

            // splitting into "words"
            int[][] wordList = splitIntoWords(padded);

            // initialization vectors (keeping the same var names as website) 
            int A = 0x01234567;
            int B = 0x89abcdef;
            int C = 0xfedcba98;
            int D = 0x76543210;
            
            functionF(wordList, 0, 0, A, B, C, D);


            // the other Mi for other rounds 
            // [1,6,11,0,5,10,15,4,9,14,3,8,13,2,7,12],
            // [5,8,11,14,1,4,7,10,13,0,3,6,9,12,15,2],
            // [0,7,14,5,12,3,10,1,8,15,6,13,4,11,2,9]];
            
        } catch (Exception e) {
            System.out.println(e);
        }


    }   

    public static void decode(String line) {
        
    }

    //helper functions 
    public static void printByteArray(byte[] array) {
        String holder = "";

        for (int index = 0; index < array.length; index++) {
            holder += index + ": " + array[index] + "\n";
        }

        System.out.println(holder); 

    }

    public static int binaryToDecimal(String binaryString) throws Exception {
        int decimal = 0;
  
        for (int len = binaryString.length() - 1; len > -1; len--) {
            if ( (binaryString.charAt(len) + "").compareTo("1") == 0) {
            if (len == binaryString.length() - 1) decimal += 1;
            else if (len == binaryString.length() - 2) decimal += 2;
            else if (len == binaryString.length() - 3) decimal += 4;
            else if (len == binaryString.length() - 4) decimal += 8;
            else if (len == binaryString.length() - 5) decimal += 16;
            else if (len == binaryString.length() - 6) decimal += 32;
            else if (len == binaryString.length() - 7) decimal += 64;
            else if (len == binaryString.length() - 8) decimal += 128;
            else throw new Exception("Binary String has a 1 in an unexpected place"); 
            }
        }

        return decimal;
    }

}
