import java.io.*;
import java.util.stream.*; 

class MergeSort {
    public static void main(String[] args) throws Exception {
        File inputFile = null;
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String inputString;
        ArrayList<Integer> inputList = new ArrayList<Integer>();
        int[] inputArray;

        // Reading the input file values
        if (args.length > 0) {
            inputFile = new File(args[0]);
        } else {
            throw new Exception("Argument is missing");
        }
        while ((inputString = br.readLine()) != null) {
           inputList.add(Integer.parseInt(inputString));
        }

        inputArray = inputList.stream().mapToInt(i->i).toArray();

        //Merge sort
        sort(inputArray, 0, size - 1);

        //Write sorted integers to output file
        try {
            File outputFile = new File("mergesort.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i=0; i<size; i++){
                bw.write(inputArray[i] + "");
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sort(int[] inputList, int leftIndex, int rightIndex){
        if(leftIndex < rightIndex){
            int middleIndex = (leftIndex + rightIndex)/2;

            sort(inputList, leftIndex, middleIndex);
            sort(inputList, middleIndex+1, rightIndex);

            mergeList(inputList, leftIndex, middleIndex, rightIndex);
        }
    }

    public void mergeList(int[] inputList, int leftIndex, int middleIndex, int rightIndex){
        int size = inputList.length;
        int size1 = middleIndex - leftIndex + 1, size2 = rightIndex - middleIndex;
        int[] leftTempArray = new int[size1], rightTempArray = new int[size2];

        //copy the array elements to temp arrays
        for(int i=0; i<size1; i++){
            leftTempArray[i] = inputList[leftIndex+i];
        }
        for(int i=0; i<size2; i++){
            rightTempArray[i] = inputList[middleIndex + 1 +i];
        }

        //merging the two temp arrays to original array
        int l=0, r=0, currentIndex = leftIndex;

        while(l<size1 && r<size2){
            inputList[currentIndex++] = (leftTempArray[l]<=rightTempArray)? leftTempArray[l++] : rightTempArray[r++];
        }

        while(l<size1){
            inputList[currentIndex++]=leftTempArray[l++]
        }

        while(r<size2){
            inputList[currentIndex++]=rightTempArray[r++]
        }
    }
}