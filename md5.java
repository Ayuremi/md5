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
    public static byte[] paddingPassword(byte[] byteArray) throws Exception {
        // calculating how much padding is needed and return padded bytes 
        // I couldn't come up with a better way to do this
        int paddedArrayLength = 0;
        int multipleOf64 = 0; 

        while (paddedArrayLength < byteArray.length || paddedArrayLength == byteArray.length) {
            // reason for "paddedArrayLength == byteArray.length" is that final block must have at least 1 bit of padding
            multipleOf64++;
            paddedArrayLength = (multipleOf64 * 64); 
            // each block still needs 64 bytes due to the last byte of the block needing the # of bytes 
        }

        // Again could not think of a better way to do this 
        byte[] padded = new byte[paddedArrayLength];
        
        int paddedUpTo = 0;
        for (int blocks = 0; blocks < multipleOf64; blocks++) {

            if (blocks != multipleOf64 - 1) {
                for (int copyTimes = 0; copyTimes < 64; copyTimes++) { 
                    padded[paddedUpTo] = byteArray[paddedUpTo]; //copying original bytes into new array (figure out better var names)
                    paddedUpTo++;
                }

            } else if (blocks == multipleOf64 - 1)  { // last block has special properties 
                while (paddedUpTo < byteArray.length) {
                    padded[paddedUpTo] = byteArray[paddedUpTo]; //copying final original bytes into new array
                    paddedUpTo++;
                }
                padded[paddedUpTo] = (byte)128; //add '10000000' byte

                while (paddedUpTo < padded.length - 8) { // there's 8 bytes to display message length (Nathaniel thinks)
                    padded[paddedUpTo] = 0; // pad with 0's
                }

                // NOTE: NEED TO ADD BYTES TO DISPLAY MESSAGE LENGTH

            } else {
                throw new Exception("Problem with the blocks during padding");
            }
            blocks++;
        }

        // for(int x = 0; x < byteArray.length; x++){
        //     padded[x] = byteArray[x]; // copy original bytes into new array
        // }
        // padded[byteArray.length] = (byte)128; //add '10000000' byte
        // for(int x = byteArray.length + 1 ; x < 63; x++){
        //     padded[x] = 0; //pad with 0s
        // }
        // padded[63] = (byte)(byteArray.length*8); //last byte is # of bits

        //print test
        // for (int index = 0; index < padded.length; index++) {
        //     System.out.print(padded[index] + " ");
        // }
        printByteArray(padded);

        return padded;
    }

    public static void encode(String line){
        byte[] lineBytes = line.getBytes(StandardCharsets.US_ASCII);
        // reminder: still need to add if line > 63 bytes then split it up
        // for now, should only work with strings <= 63
        try {
            byte[] padded = paddingPassword(lineBytes);
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
