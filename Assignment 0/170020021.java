import java.io.*;
class ReadFromFile2 {
    public static void main(String[] args) throws Exception {
        File inputFile = null;
        if (args.length > 0) {
            inputFile = new File(args[0]);
        } else {
            throw new Exception("Argument is missing");
        }

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String inputString;
        int size = 0;
        long sum = 0, max = 0, min = Long.MAX_VALUE;
        double average = 0;
        while ((inputString = br.readLine()) != null) {
            long inputValue = Long.parseLong(inputString);
            ++size;
            sum += inputValue;
            if (inputValue > max) {
                max = inputValue;
            }

            if (inputValue < min) {
                min = inputValue;
            }
        }
        average = Double.parseDouble(roundOffTo2DecPlaces((1.0 * sum) / size));
        try {
            File outputFile = new File("output.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(size + "");
            bw.newLine();
            bw.write(min + "");
            bw.newLine();
            bw.write(max + "");
            bw.newLine();
            bw.write(sum + "");
            bw.newLine();
            bw.write(average + "");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static String roundOffTo2DecPlaces(double val) {
        return String.format("%.2f", val);
    }
}