import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class md5 {
    public static void main(String args[]){

        if (args.length < 1) System.out.println("no mode specified");
        else{
            if (args[0].compareTo("encode") == 0){
                if (args.length == 1) encode("");
                else encode(args[1]);
            }
            
            // else if (args[0].compareTo("decode") == 0) decode(args[1]);
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

    public static String printhex(long value) {
        return String.format("%8x", value).replace(' ', '0') + " ";
    }

    public static String printhex(int value) {
        return String.format("%8x", value).replace(' ', '0') + " ";
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

    public static void function(int[][] wordList, int[] M, int[] K, int[] S, int block, int step, int[] initial) {
        
        int fResult = -1;

         // A = 0, B = 1, C = 2, D = 3
        if (step < 16) { // F
            // fResult = (B and C) or ((not B) and D)
            fResult = (initial[1] & initial[2]) | (~initial[1] & initial[3]); 
            // f = (b & c) | (~b & d);

            // System.out.println(0xfedcba98 == fResult);  // for F
            // correct 

        } else if (step < 32) { // G
            // fResult = (B & D) | (C & ~D); 
            fResult = (initial[1] & initial[3]) | (initial[2] & ~initial[3]); 
            // f = (b & d) | (c & ~d);

        } else if (step < 48) { // H
            //fResult = (B ^ C ^ D) 
            fResult = (initial[1] ^ initial[2] ^ initial[3]); 
            

        } else if (step < 64) { // I
            // (B, C, D) = C⊕(B∨¬D)
            fResult = ( initial[2] ^ (initial[1] | ~initial[3]) ); 
        } else {
            System.out.print("Function does not exist");
        }
        
        // G (B, C, D) = (2c34dfa2 ∧ 4b976282)∨(de1673be ∧ ¬4b976282)
        // fResult = (0x2c34dfa2 & 0x4b976282) | (0xde1673be & ~0x4b976282); 

        
        // Check
        // System.out.println(0x98badcfe == fResult);  // for F 0xfedcba98
        
        // System.out.println(printhex(fResult));
        // System.out.println(fResult == 0x1c1453be); // for G
        System.out.println("step : " + step);

        // switched to long for now because adding ints can make int overflow
        long FandA = ((initial[0]) + (fResult)) % 0x100000000L; 
        System.out.println("\nFandA");
        // System.out.println((int)FandA == 0xffffffff);  // for F
        System.out.println(printhex(FandA));
        // System.out.println(Long.toHexString(FandA));

        System.out.println("wordList is " + Integer.toHexString(wordList[block][M[step]]));

        long FAM = ((FandA + (wordList[block][M[step]])) % 0x100000000L) & 0xFFFFFFFFL;
        System.out.println("\nFAM");
        // System.out.println(FAM == 0x179656853L); // for F , rachel: got 54686578 from online calculator?
        System.out.println(printhex(FAM));
        // System.out.println(Long.toHexString(FAM));

        long FAMK = ((FAM + (K[step])) % 0x100000000L) & 0xFFFFFFFFL;
        System.out.println("\nFAMK");
        // System.out.println("\n" + Integer.toHexString(K[step]));
        // System.out.println(FAMK == 0x250d00ccbL);  // for F, rachel: got 2BD309F0
        System.out.println(printhex(FAMK));
        // 250d00ccb


        long FAMKS = ( (FAMK << S[step]) | (FAMK >> (32 - S[step])) ) & 0xFFFFFFFFL ;
        System.out.println("rotate_amount: " + S[step]);
        System.out.println("\nFAMKS");
        // System.out.println(FAMKS == 0x680665a8);  // for f | works if it's casted into an int
        //                                           // rachel: got E984F815
        System.out.println(printhex(FAMKS));

        long FAMKSB = ((FAMKS + (initial[1])) % 0x100000000L ) & 0xFFFFFFFFL;
        System.out.println("\nFAMKSB");
        // System.out.println("\n" + Integer.toHexString(initial[1]));
        // System.out.println( FAMKSB == 0x57d41131); // for F // 0x7330C604 // rachel: got D952A39E
        System.out.println(printhex(FAMKSB));
        // 57d41131

        // System.out.println(Integer.toHexString( (int) FAMKSB));
                                    //  cfcdeecf
                                    // bb3a5b2e02
                                    // 57d41131
        
        // int test = initial[1] + Integer.rotateLeft(initial[0] + fResult + wordList[block][M[step]] + K[step], S[step]);
        // actually gives the same thing i think??? which is kind of stupid that we did all that work lmao

        initial[0] = initial[3]; 
        initial[3] = initial[2]; 
        initial[2] = initial[1];
        initial[1] = (int)FAMKSB;

    }

   

    public static void encode(String line){
        byte[] lineBytes = line.getBytes(StandardCharsets.US_ASCII);
    
        try {
            // padding
            byte[][] padded = paddingPassword(lineBytes);

            // splitting into "words"
            int[][] wordList = splitIntoWords(padded);

            //printing out wordList
            // for (int[] arrays: wordList) {
            //     for (int elements: arrays) {
            //         System.out.println(String.format("%32s", Integer.toBinaryString(elements)).replace(' ', '0') + " ");
            //     }
            // }

            int[] OrigInitial = new int[]{0x67452301, (int)0xefcdab89L, (int)0x98badcfeL, 0x10325476};
            //for (int x = 0; x < 4; x++) System.out.println(x + ": " + Integer.toHexString(OrigInitial[x]));
            
            // Word Order
            int[] M = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
                                1,6,11,0,5,10,15,4,9,14,3,8,13,2,7,12,
                                5,8,11,14,1,4,7,10,13,0,3,6,9,12,15,2,
                                0,7,14,5,12,3,10,1,8,15,6,13,4,11,2,9 };

            // word order 


            // precomputed table but function is floor(232 × abs(sin(i + 1)))
            int[] K = new int[64];

            for (int index = 0; index < 64; index++) { 
                K[index] = (int)(long)( (1L << 32) * Math.abs(Math.sin(index + 1)) );
            }

            // printIntArray(K);



            // int[] K = new int[]{0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee,
            //                     0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501, 
            //                     0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be,
            //                     0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
            //                     0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa,
            //                     0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
            //                     0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed,
            //                     0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
            //                     0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c,
            //                     0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
            //                     0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05,
            //                     0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
            //                     0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039,
            //                     0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
            //                     0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1,
            //                     0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391};
            
            int[] S = new int[]{7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,
                                5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,
                                4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,
                                6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21};

                                // Reference from python code
                                // [7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
                                // 5,  9, 14, 20, 5,  9, 14, 20, 5,  9, 14, 20, 5,  9, 14, 20,
                                // 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
                                // 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21]
              

            // System.out.println("Before");
            // for (int element: initial) {
            //     System.out.println(element + " ");
            // }
            
            int[] initial = new int[4];
            // for (int block = 0; block < wordList.length; block++) {
            initial = OrigInitial.clone();
            for (int step = 0; step < 5; step++) { 
                function(wordList, M, K, S, 0, step, initial);
            }
            //}
            
            // for (int x = 0; x < 4; x++){
            //     long temp = (((long)initial[x] + (long)OrigInitial[x] )% 0x100000000L)& 0xFFFFFFFFL;
            //     OrigInitial[x] = (int)temp;
            // }
            
            // int[] temp = new int[]{0x799d1352,0x2c34dfa2,0xde1673be,0x4b976282};
            // int etargdhf = (temp[1] & temp[3]) | (temp[2] & ~temp[3]); 
            
            String hash = "";
            for (int x = 0; x < 4; x++){
                hash += Integer.toHexString(initial[x]) + " "; //should be originitial btw
                //hash += Integer.toBinaryString(initial[x]).replace(" ", "0") + " ";
                //hash += initial[x] + " ";
            }

            //System.out.println(String.format("%8x",etargdhf).replace(" ", "0"));
            System.out.println("\n" + hash);
            
            // }

            // System.out.println("After");
            // testing
            // for (int element: initial) {
            //     System.out.println(element + " ");
            // }
            
        } catch (Exception e) {
            System.out.println(e);
        }

    }   

    //helper functions 
    public static void printByteArray(byte[] array) {
        String holder = "";

        for (int index = 0; index < array.length; index++) {
            holder += index + ": " + array[index] + "\n";
        }

        System.out.println(holder); 

    }

    public static void printIntArray(int[] array) {
        String holder = "";

        for (int index = 0; index < array.length; index++) {
            holder += index + ": " + printhex(array[index]) + "\n";
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
