import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

class Anagrams{
    static HashMap<Integer, LinkedList<String>> hashMap = new HashMap<Integer, LinkedList<String>>();
    static int NO_OF_CHARS = 256;
    static BufferedWriter bw;

    public static void main(String[] args) throws Exception {
        File wordFile, queryFile = null;
        String word, query;
        int M = 0;

        // Reading the input file values
        if (args.length == 3) {
            wordFile = new File(args[0]);
            queryFile = new File(args[2]);
        } else if(args.length >3){
            throw new Exception("Excess arguments");
        } else {
            throw new Exception("Argument is missing");
        }

        BufferedReader wordBR = new BufferedReader(new FileReader(wordFile));
        BufferedReader queryBR = new BufferedReader(new FileReader(queryFile));

        //Getting the size of hashtable
        M = Integer.parseInt(args[1]);

        //Storing the numbers in a hasmap
        while ((word = wordBR.readLine()) != null) {
           int generatedHash = hash(word, M);
           saveInHashMap(generatedHash, word);
        }

        wordBR.close();

        try {
            File outputFile = new File("anagrams.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean isFirstLine = true;

        while ((query = queryBR.readLine()) != null) {
            int generatedHash = hash(query, M);
            String listOfAnagrams = "";
            
            LinkedList<String> list = hashMap.get(generatedHash);

            for(String words: list){
                if(areAnagram(query, words)){
                    listOfAnagrams = listOfAnagrams + " " +  words ;
                }
            }
            writeToFile(listOfAnagrams.trim(), isFirstLine);
            isFirstLine = false;
         }

         queryBR.close();
         bw.close();
    }

    public static void writeToFile(String listOfAnagrams, boolean isFirstLine){
        try {
            if(!isFirstLine)
                bw.newLine();
            bw.write(listOfAnagrams);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static boolean areAnagram(String str1, String str2){

        // Create a count array and initialize all values as 0
        int count[] = new int[NO_OF_CHARS];
        int i;

        // If both strings are of different length. Removing
        // this condition will make the program fail for strings
        // like "aaca" and "aca"
        if (str1.length() != str2.length())
            return false;
    
        // For each character in input strings, increment count
        // in the corresponding count array
        for (i = 0; i<str1.length(); i++) {
            count[str1.charAt(i)]++;
            count[str2.charAt(i)]--;
        }

        // See if there is any non-zero value in count array
        for (i = 0; i < NO_OF_CHARS; i++){
            if (count[i]!=0)
                return false;
        }

        return true;
    }

    public static void saveInHashMap(Integer hash, String word){
        if(hashMap.containsKey(hash)){
            hashMap.get(hash).addFirst(word);
        }else{
            LinkedList<String> newLinkedList = new LinkedList<String>();
            newLinkedList.add(word);
            hashMap.put(hash, newLinkedList);
        }
    }

    public static int hash(String word, int M){
        int lenOfWord = word.length();
        int hashValue = 0;
        for(int i=0; i<lenOfWord; i++){
            //hashValue = ((hashValue%M)  + (Character.getNumericValue(word.charAt(i))%M))%M;
            hashValue +=  Character.getNumericValue(word.charAt(i));
        }
        return (hashValue%M);
    }
}