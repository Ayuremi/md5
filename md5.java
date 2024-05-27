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

    // NOTE: I (Nathaniel) did not test this (ran out of time)
    public static byte[][] paddingPassword(byte[] byteArray) throws Exception {
        // calculating how much padding is needed and return padded bytes 
        // I couldn't come up with a better way to do this
        // int paddedArrayLength = 0;
        // int multipleOf64 = 0; 

        // while (paddedArrayLength < byteArray.length || paddedArrayLength == byteArray.length) {
        //     // reason for "paddedArrayLength == byteArray.length" is that final block must have at least 1 bit of padding
        //     multipleOf64++;
        //     paddedArrayLength = (multipleOf64 * 64); 
        //     // each block still needs 64 bytes due to the last byte of the block needing the # of bytes 
        // }

        // // Again could not think of a better way to do this 
        // byte[] padded = new byte[paddedArrayLength];
        
        // int paddedUpTo = 0;
        // for (int blocks = 0; blocks < multipleOf64; blocks++) {

        //     if (blocks != multipleOf64 - 1) {
        //         for (int copyTimes = 0; copyTimes < 64; copyTimes++) { 
        //             padded[paddedUpTo] = byteArray[paddedUpTo]; //copying original bytes into new array (figure out better var names)
        //             paddedUpTo++;
        //         }

        //     } else if (blocks == multipleOf64 - 1)  { // last block has special properties 
        //         while (paddedUpTo < byteArray.length) {
        //             padded[paddedUpTo] = byteArray[paddedUpTo]; //copying final original bytes into new array
        //             paddedUpTo++;
        //         }
        //         padded[paddedUpTo] = (byte)128; //add '10000000' byte

        //         while (paddedUpTo < padded.length - 8) { // there's 8 bytes to display message length (Nathaniel thinks)
        //             padded[paddedUpTo] = 0; // pad with 0's
        //         }

        //         // NOTE: NEED TO ADD BYTES TO DISPLAY MESSAGE LENGTH

        //     } else {
        //         throw new Exception("Problem with the blocks during padding");
        //     }
        //     blocks++;
        // }

        // rachel: this is my take on finding how many 'blocks' of byte[] we need
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
        padded2D[curBlock][byteArray.length % 64] = (byte)128; //add '10000000' byte
        // check whether to pad on last block, or last two depending on remainingBytes
        if (remainingBytes < 56){
            for(int x = (byteArray.length % 64) + 1 ; x < 56; x++){ // stop on byte 57
                padded2D[curBlock][x] = 0; //pad with 0s
            }
        }
        else if (remainingBytes >= 56){
            for(int x = (byteArray.length % 64) + 1 ; x < 64; x++){ // completely fill 2nd to last block
                padded2D[curBlock][x] = 0; //pad with 0s
            }
            for(int x = 0; x < 56; x++){ // completely fill 2nd to last block
                padded2D[curBlock + 1][x] = 0; //pad with 0s
            }
            curBlock += 1;
        }
        // add message length bits to last 8 bytes
        for (int x = 0; x < 8; x++){
            padded2D[curBlock][63-x] = (byte)(bits >> (x * 8));
        }
        // and done!

        //print test
        // for (byte[] arr : padded2D) {
        //     printByteArray(arr);
        //     System.out.println("");
        // }

        // return padded;
        
        return padded2D;
    }

    public static void encode(String line){
        byte[] lineBytes = line.getBytes(StandardCharsets.US_ASCII);
        // reminder: still need to add if line > 63 bytes then split it up
        // for now, should only work with strings <= 63
        try {
            byte[][] padded = paddingPassword(lineBytes);
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
            holder += array[index] + ", ";
        }

        System.out.println(holder); 

    }

}
