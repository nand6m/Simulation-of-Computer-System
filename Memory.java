import java.io.*;

public class Memory {
    static int[] memory_address = new int[2000]; //Array of 2000 values
    static String find = null;
    static BufferedReader read_Instruction = null, read_Input_File = null;

    public static void main(String[] args1) {
        try {
            read_Input_File = new BufferedReader(new FileReader(args1[0])); //Read user program with file reader
        } catch (FileNotFoundException e) {
            System.out.println("Error in reading file");
            System.exit(1);
        }

        try {
            String line = null;
            int count = 0;
            while ((line = read_Input_File.readLine()) != null) {
                try {
                    if(!line.trim().isEmpty() && !"".equals(line)){
                        String[] list = line.split(" ");
                        if(list[0].startsWith(".")){
                            count = Integer.parseInt(list[0].trim().substring(1));
                        }
                        else if(!list[0].trim().isEmpty() && Character.isDigit( list[0].trim().charAt(0)))
                        {
                            memory_address[count++] = Integer.parseInt(list[0].trim());
                        }
                    }
                } catch (NumberFormatException e) {                                     
                    System.out.println(line);
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            System.out.println("Error initiating memory");
            System.exit(1);
        }

        //Instruction fetch start

        read_Instruction = new BufferedReader(new InputStreamReader(System.in)); //pipe reader
        while (true) {
            try {
                find = read_Instruction.readLine();
            } //try reading program counter from pipe
            catch (IOException e) {
                System.out.println("Error reading memory");
                System.exit(0);
            }
            if (find.equals("end"))
                System.exit(0);
            if (find != null) {
                try {
                    System.out.println(memory_address[Integer.parseInt(find)]);    //write in pipe (fetch instruction)
                } catch (NumberFormatException e) {
                    memory_address[Integer.parseInt(find.split(" ")[0])] = Integer.parseInt(find.split(" ")[1]); //store instruction
                }
            }
            else
            {
                System.out.println("Invalid Input");
                System.exit(0);
            }
        }                //Instruction fetch end
    }
}
