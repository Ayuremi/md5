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

    public static void encode(String line){
        
    }

    public static void decode(String line) {
        
    }

}
