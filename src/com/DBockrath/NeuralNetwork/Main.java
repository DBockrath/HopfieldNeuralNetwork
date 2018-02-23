package com.DBockrath.NeuralNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.StringCharacterIterator;

public class Main {

    static final int NUMB_OF_ROWS_IN_DRAWING_BOARD = 5;
    enum Mode {DEFAULT, VERBOSE}
    static Main.Mode mode = Mode.DEFAULT;

    public static void main(String[] args) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // [# of neurons] = [# of columns] * [# of Rows]
        System.out.println("> Enter number of Neurons (Must be divisible by 5");
        int size = Integer.valueOf(bufferedReader.readLine());

        NeuralNetwork network = new NeuralNetwork(size);

        double[] input = new double[size];
        double[] output = new double[size];
        boolean flag = true;

        while (flag) {

            System.out.println("> What do you want to do? (Train, Run, Clear, Change Mode, Exit)");
            String command = bufferedReader.readLine();

            switch (command) {

                case "Train":
                    System.out.println("> Provide training pattern:");
                    input = getInput(new StringCharacterIterator((bufferedReader.readLine())), size);
                    network.train(input);
                    System.out.print(Matrix.getMatrix(input, NUMB_OF_ROWS_IN_DRAWING_BOARD).toPackedString());
                    System.out.println("> Training Finished");
                    break;

                case "Run":
                    System.out.println("> Enter input pattern:");
                    input = getInput(new StringCharacterIterator(bufferedReader.readLine()), size);
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

    static double[] getInput(StringCharacterIterator iterator, int size) {

        double[] input = new double[size];

        while (iterator.getIndex() < iterator.getEndIndex()) {

            input[iterator.getIndex()] = Double.parseDouble(String.valueOf(iterator.current()));
            iterator.next();

        }

        return input;

    }

}