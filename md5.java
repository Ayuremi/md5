import java.nio.charset.StandardCharsets;

public class md5{
    public static void main(String args[]){
        // System.out.println("Main is called");

        if (args.length < 1) System.out.println("no mode specified");
        else{
            if (args[0].compareTo("encode") == 0) encode(args[1]);
            else if (args[0].compareTo("decode") == 0) decode(args[1]);
            else if (args[0].compareTo("test") == 0) {printBinary(args[1]);}
            else System.out.println("mode doesnt exist");
        }

    }

    public static void printBinary(String line) {
        String holder = "";
        byte[] lineBytes = line.getBytes(StandardCharsets.US_ASCII);

        for (int index = 0; index < lineBytes.length; index++) {
            String byteString = Integer.toBinaryString(lineBytes[index]);
            holder +=  String.format("%8s", byteString).replace(' ', '0') + "\n";
        }

        System.out.println(holder);
    }

    public static byte[] paddingPassword(byte[] byteString) {
        // calculating how much padding is needed and return padded bytes
        byte[] padded = new byte[64];
        for(int x = 0; x < byteString.length; x++){
            padded[x] = byteString[x]; // copy original bytes into new array
        }
        padded[byteString.length] = (byte)128; //add '10000000' byte
        for(int x = byteString.length + 1 ; x < 63; x++){
            padded[x] = 0; //pad with 0s
        }
        padded[63] = (byte)(byteString.length*8); //last byte is # of bits

        //print test
        // for (int index = 0; index < padded.length; index++) {
        //     System.out.print(padded[index] + " ");
        // }
        return padded;
    }

    public static void encode(String line){
        byte[] lineBytes = line.getBytes(StandardCharsets.US_ASCII);
        // reminder: still need to add if line > 63 bytes then split it up
        // for now, should only work with strings <= 63
        byte[] padded = paddingPassword(lineBytes);


    }   

    public static void decode(String line) {
        
    }

}
