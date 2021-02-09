package assignment2_solutions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

class BST {
    static BinarySearchTree bst;

    public static void main(String[] args) throws Exception {
        File inputFile = null;
        String inputString;
        bst = new BinarySearchTree();

        // Reading the input file values
        if (args.length > 0) {
            inputFile = new File(args[0]);
        } else {
            throw new Exception("Argument is missing");
        }
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        StringBuilder builder = new StringBuilder();

        // Write sorted integers to output file
        try {
            File outputFile = new File("bst.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            while ((inputString = br.readLine()) != null) {
                builder = new StringBuilder(callAppropriateFunction(bst.root, inputString));
                bw.write(builder.toString());
                bw.newLine();
            }
            bw.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String callAppropriateFunction(Node curNode, String inputString) {
        StringTokenizer st = new StringTokenizer(inputString);
        String outputString = "";
        switch (InputType.valueOf(st.nextToken())) {
            case insert: {
                int val = Integer.parseInt(st.nextToken());
                bst.root = bst.insert(curNode, val);
                outputString = val + " inserted";
                break;
            }
            case inorder: {
                outputString = bst.traversal(0);
                break;
            }
            case preorder: {
                outputString = bst.traversal(1);
                break;
            }
            case postorder: {
                outputString = bst.traversal(2);
                break;
            }
            case search: {
                int val = Integer.parseInt(st.nextToken());
                boolean result = bst.search(curNode, val);
                if (result)
                    outputString = val + " found";
                else
                    outputString = val + " not found";
                break;
            }
            case minimum: {
                int min = bst.minimumNode(curNode);
                outputString = (min!=-1)? (min + "") : "";
                break;
            }
            case maximum: {
                int max = bst.maximumNode(curNode);
                outputString = (max!=-1)? (max + "") : "";
                break;
            }
            case successor: {
                int val = Integer.parseInt(st.nextToken());
                int result = bst.successor(val);
                if (result == -2) {
                    outputString = val + " does not exist";
                } else if (result == -1) {
                    outputString = "successor of " + val + " does not exist";
                } else {
                    outputString = result + "";
                }
                break;
            }
            case predecessor: {
                int val = Integer.parseInt(st.nextToken());
                int result = bst.predecessor(val);
                if (result == -2) {
                    outputString = val + " does not exist";
                } else if (result == -1) {
                    outputString = "predecessor of " + val + " does not exist";
                } else {
                    outputString = result + "";
                }
                break;
            }
            default:
                break;
        }
        return outputString;
    }
}
