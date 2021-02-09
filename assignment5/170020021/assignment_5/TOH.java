package assignment_5;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class TOH {

    static PrintStream fileStream;

    public static void main(String[] args) throws Exception {
        int towerSize = 0;

        // Reading the stack size
        if (args.length > 0) {
            towerSize = Integer.parseInt(args[0]);
        } else {
            throw new Exception("Argument is missing");
        }

        Stack aStack = new Stack(towerSize, 'A');
        Stack bStack = new Stack(towerSize, 'B');
        Stack cStack = new Stack(towerSize, 'C');

        try {
            File outputFile = new File("toh.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }

            fileStream = new PrintStream(outputFile);
            int i = towerSize;
            while (i > 0) {
                aStack.push(i);
                fileStream.println("Push disk " + i + " to Stack A");
                i--;
            }

            // A, B and C are names of rods
            towerOfHanoi(towerSize, aStack, cStack, bStack);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void towerOfHanoi(int sizeOfTower, Stack fromRod, Stack toRod, Stack auxRod) {
        if (sizeOfTower == 1) {
            executePull(1, fromRod, toRod);
            executePush(1, fromRod, toRod);
            return;
        }

        towerOfHanoi(sizeOfTower - 1, fromRod, auxRod, toRod);

        executePull(sizeOfTower, fromRod, toRod);
        executePush(sizeOfTower, fromRod, toRod);

        towerOfHanoi(sizeOfTower - 1, auxRod, toRod, fromRod);
    }

    static void executePush(int disk, Stack from, Stack to){
        to.push(disk);
        fileStream.println("Push disk " + disk + " to Stack " + to.name);
    }

    static void executePull(int disk, Stack from, Stack to){
        from.pop();
        fileStream.println("Pop disk " + disk + " from Stack " + from.name);
    }
}
