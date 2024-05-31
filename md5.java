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

    public static void function(int[][] wordList, int[] M, int[] K, int[] S, int block, int step, int[] initial, String functionName) {
        
        // switched to long for now because adding ints can make int overflow
        long fResult = -1;

        // Specfies array start pos for all arrays
        int start = -1;

         // A = 0, B = 1, C = 2, D = 3
        if (functionName.compareTo("F") == 0 ) {
            fResult = (initial[1] & initial[2]) | (~initial[1] & initial[3]); 
            start = 0;

        } else if (functionName.compareTo("G") == 0 ) {
            // fResult = (B & D) | (C & ~D); 
            fResult = (initial[1] & initial[3]) | (initial[2] & ~initial[3]); 
            start = 16;

        } else if (functionName.compareTo("H") == 0 ) {
            //fResult = (B ^ C ^ D) 
            fResult = (initial[1] ^ initial[2] ^ initial[3]); 
            start = 32;

        } else if (functionName.compareTo("I") == 0 ) {
            // (B, C, D) = C⊕(B∨¬D)
            fResult = ( initial[3] ^ (initial[1] | initial[3]) ); 
            start = 48;

        } else {
            System.out.print("Function does not exist");
        }
        
        // G (B, C, D) = (2c34dfa2 ∧ 4b976282)∨(de1673be ∧ ¬4b976282)
        // fResult = (0x2c34dfa2 & 0x4b976282) | (0xde1673be & ~0x4b976282); 

        
        // Check
        // System.out.println(0xfedcba98 == fResult);  // for F
        // System.out.println(fResult == 0x1c1453be); // for G


        long FandA = (initial[0] + fResult) % 0x100000000L;
        // System.out.println(FandA == 0xffffffff);  // for F

        long FAM = (FandA + wordList[block][M[start + step]]) % 0x100000000L;
        // System.out.println(FAM == 0x54686578);  // for F

        long FAMK = (FAM + K[start + step]) % 0x100000000L;
        // System.out.println(FAMK == 0x2bd309f0);  // for F

        int FAMKS = (int) ((FAMK << S[start + step]) | (FAMK >>> (32 - S[start + step])));
        // System.out.println(FAMKS == 0xe984f815);  // for F

        long FAMKSB = (FAMKS + initial[1]) % 0x100000000L;
        // System.out.println( FAMKSB == 0x7330C604); // for F


        initial[0] = initial[3]; 
        initial[3] = initial[2]; 
        initial[2] = initial[1];
        initial[1] = (int) FAMKSB;

    }

   

    public static void encode(String line){
        byte[] lineBytes = line.getBytes(StandardCharsets.US_ASCII);
    
        try {
            // padding
            byte[][] padded = paddingPassword(lineBytes);

            // splitting into "words"
            int[][] wordList = splitIntoWords(padded);

            int[] initial = new int[]{0x01234567, 0x89abcdef, 0xfedcba98, 0x76543210}; // A, B, C, D

            // Word Order
            int[] M = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
                                 1,6,11,0,5,10,15,4,9,14,3,8,13,2,7,12,
                                 5,8,11,14,1,4,7,10,13,0,3,6,9,12,15,2,
                                 0,7,14,5,12,3,10,1,8,15,6,13,4,11,2,9 };

            // precomputed table but function is floor(232 × abs(sin(i + 1)))
            int[] K = new int[]{0xD76AA478, 0xE8C7B756, 0x242070DB, 0xC1BDCEEE,
                                0xF57C0FAF, 0x4787C62A, 0xA8304613, 0xFD469501,
                                0x698098D8, 0x8B44F7AF, 0xFFFF5BB1, 0x895CD7BE,
                                0x6B901122, 0xFD987193, 0xA679438E, 0x49B40821,
                                0xF61E2562, 0xC040B340, 0x265E5A51, 0xE9B6C7AA,
                                0xD62F105D, 0x02441453, 0xD8A1E681, 0xE7D3FBC8,
                                0x21E1CDE6, 0xC33707D6, 0xF4D50D87, 0x455A14ED,
                                0xA9E3E905, 0xFCEFA3F8, 0x676F02D9, 0x8D2A4C8A,
                                0xFFFA3942, 0x8771F681, 0x699D6122, 0xFDE5380C,
                                0xA4BEEA44, 0x4BDECFA9, 0xF6BB4B60, 0xBEBFBC70,
                                0x289B7EC6, 0xEAA127FA, 0xD4EF3085, 0x04881D05,
                                0xD9D4D039, 0xE6DB99E5, 0x1FA27CF8, 0xC4AC5665,
                                0xF4292244, 0x432AFF97, 0xAB9423A7, 0xFC93A039, 
                                0x655B59C3, 0x8F0CCC92, 0xFFEFF47D, 0x85845DD1,
                                0x6FA87E4F, 0xFE2CE6E0, 0xA3014314, 0x4E0811A1,
                                0xF7537E82, 0xBD3AF235, 0x2AD7D2BB, 0xEB86D391};
            
            int[] S = new int[]{7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,
                                5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,
                                4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,
                                6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21};

            // System.out.println("Before");
            // for (int element: initial) {
            //     System.out.println(element + " ");
            // }

            // for (int block = 0; block < wordList.length; block++) {
                for (int step = 0; step < 16; step++) { // we do functionF 16 times because 
                    function(wordList, M, K, S, 0, step, initial, "F");
                }
                function(wordList, M, K, S, 0, 0, initial, "G");

            // }

            // System.out.println("After");
            // testing
            // for (int element: initial) {
            //     System.out.println(element + " ");
            // }


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
