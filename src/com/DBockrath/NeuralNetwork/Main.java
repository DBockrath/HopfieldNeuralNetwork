package com.DBockrath.NeuralNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.StringCharacterIterator;

public class Main {

    // Training numbers 5-9 have not been updated yet. Refer to numbers 0-4 to see the new format

    static final int NUMB_OF_ROWS_IN_DRAWING_BOARD = 8; // Change this value to the number of rows in the input data before running
    enum Mode {DEFAULT, VERBOSE}
    static Main.Mode mode = Mode.DEFAULT;

    public static void main(String[] args) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // [# of neurons] = [# of columns] * [# of Rows]
        System.out.println("> Enter number of Neurons (Must be divisible by " + NUMB_OF_ROWS_IN_DRAWING_BOARD + ")");
        int size = Integer.valueOf(bufferedReader.readLine());

        NeuralNetwork network = new NeuralNetwork(size);

        double[] input = new double[size];
        double[] output = new double[size];
        boolean flag = true;

        while (flag) {

            String userText = null;
            System.out.println("> What do you want to do? (Train, Run, Clear, Change Mode, Exit)");
            String command = bufferedReader.readLine();

            switch (command) {

                case "Train":
                    System.out.println("> Provide training pattern:");
                    userText = bufferedReader.readLine();
                    input = getInput(userText);
                    network.train(input);
                    System.out.print(Matrix.getMatrix(input, NUMB_OF_ROWS_IN_DRAWING_BOARD).toPackedString());
                    System.out.println("> Training Finished");
                    break;

                case "Run":
                    System.out.println("> Enter input pattern:");
                    userText = bufferedReader.readLine();
                    input = getInput(userText);
                    output = network.run(input);

                    System.out.println("> Input Pattern:");
                    System.out.print(Matrix.getMatrix(input, NUMB_OF_ROWS_IN_DRAWING_BOARD).toPackedString());
                    System.out.println("> Output Pattern:");
                    System.out.print(Matrix.getMatrix(output, NUMB_OF_ROWS_IN_DRAWING_BOARD).toPackedString());
                    break;

                case "Clear":
                    network.getWeightMatrix().clear();
                    break;

                case "Change Mode":
                    System.out.println("> Enter new running mode: (Default, Verbose)");
                    if (bufferedReader.readLine().equals("Verbose")) mode = Mode.VERBOSE;
                    else mode = Mode.DEFAULT;
                    break;

                case "Exit":
                    flag = false;
                    break;

            }

        }

        System.exit(0);

    }

    static double[] getInput(String input) {

        double[] returnData = new double[input.length()];

        for (int i = 0; i < input.length(); i++) {

            returnData[i] = Double.parseDouble(String.valueOf(input.charAt(i)));

        }

        return returnData;

    }

}