public class md5{
    public static void main(String args[]){
        
        if (args.length() < 1) System.out.println("no mode specified");
        else{
            if (args[0].compareTo("encode") == 0) encode(args[1]);
            //if (args[0].compareTo("decode") == 0) decode(args[1]);
            else System.out.println("mode doesnt exist");
        }

    }

    public static void encode(String line){
        
    }

}
