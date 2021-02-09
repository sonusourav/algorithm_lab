import java.io.*;
import java.util.ArrayList; 
import java.util.stream.*; 

class RadixSort{
    public static void main(String[] args) throws Exception {
        File inputFile = null;
        String inputString;
        ArrayList<Integer> inputList = new ArrayList<Integer>();
        int[] inputArray, outputArray;
        int size = 0;
        int digitLen = 0;

        // Reading the input file values
        if (args.length == 2) {
            inputFile = new File(args[0]);
        } else if(args.length >2){
            throw new Exception("Excess arguments");
        } else {
            throw new Exception("Argument is missing");
        }

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        
        //Getting the length of each number
        digitLen = Integer.parseInt(args[1]);

        //Storing the numbers in an arraylist
        while ((inputString = br.readLine()) != null) {
           inputList.add(Integer.parseInt(inputString));
        }

        //Converting list to an array
        inputArray = inputList.stream().mapToInt(i->i).toArray();
        size = inputArray.length;

        //Implementing Radix sort
        outputArray = radixSorting(inputArray, digitLen);

        //Write sorted integers to output file
        try {
            File outputFile = new File("radix.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i=0; i<size; i++){
                bw.write(outputArray[i] + "");
                if(i!=size-1)
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int[] radixSorting(int[] inputArray, int digitLength){

        //Implement Counting sort on each digit of numbers
        int[] outputArray = inputArray;
        for(int i=1; i<=digitLength; i++){
            outputArray = countingSort(outputArray, digitLength - i);
        }

        return outputArray;
    }

    public static int[] countingSort(int[] inputArray, int position){

        //initialising a array to store the occurences of values in inputArray
        int[] countArray =  new int[10];
        int inputLength = inputArray.length;
        int[] outputArray = new int[inputLength];
        
        //counting and storing the occurences in countArray
        for(int i=0; i< inputLength; i++){
            int valAtPosition = getValueAtPosition(inputArray[i], position);
            countArray[valAtPosition]++;
        }

        //Save cumulative sum at each index
        for(int i=1; i<10; i++){
            countArray[i] += countArray[i-1];
        }

        for(int i=inputLength-1; i >= 0; i--){
            int valAtPosition = getValueAtPosition(inputArray[i], position);
            outputArray[countArray[valAtPosition]-1] = inputArray[i];
            countArray[valAtPosition]--;
        }

        return outputArray;
    }

    //Get the digit at given position in a number
    private static int getValueAtPosition(int number, int position){
        String numberToString = Integer.toString(number);
        char stringToChar= numberToString.charAt(position);
        return Character.getNumericValue(stringToChar);
    }
}