package com.DBockrath.NeuralNetwork;

import java.util.stream.IntStream;

public class NeuralNetwork {

    private Matrix weightMatrix;

    public NeuralNetwork(int size) {

        weightMatrix = new Matrix(size, size);

    }

    public Matrix getWeightMatrix() {

        return weightMatrix;

    }

    public void train(double[] input) throws Exception {

        double[] bipolarInput = toBipolar(input);
        Matrix bipolarMatrix = Matrix.toRowMatrix(bipolarInput);
        Matrix transposeBipolarMatrix = bipolarMatrix.transpose();
        Matrix multiplyMatrix = transposeBipolarMatrix.multiply(bipolarMatrix);
        Matrix subtractMatrix = multiplyMatrix.subtract(Matrix.identity(weightMatrix.getData().length));

        if (Main.mode == Main.Mode.VERBOSE) {

            System.out.println("<-- Calculate Contribution Matrix -->");
            System.out.println("[1] Obtain bipolar matrix for input \n" + bipolarMatrix);
            System.out.println("[2] Transpose bipolar matrix \n" + transposeBipolarMatrix);
            System.out.println("[3] Multiply \n" + multiplyMatrix);
            System.out.println("[4] Contribution matrix: \n" + subtractMatrix);
            System.out.println("<-- Update Weight Matrix -->");
            System.out.println("Current weight matrix: \n" + weightMatrix.toString("N", "N"));

        }

        weightMatrix = weightMatrix.add(subtractMatrix);

        if (Main.mode == Main.Mode.DEFAULT) {

            System.out.println("New weight matrix: \n" + weightMatrix.toString("N", "N"));

        }

    }

    public double[] run(double[] input) {

        double[] bipolarInput  = toBipolar(input);
        double[] output = new double[input.length];
        Matrix bipolarMatrix = Matrix.toRowMatrix(bipolarInput);

        if (Main.mode == Main.Mode.VERBOSE) {

            System.out.println("<-- Run -->");
            System.out.println("[1] Weight matrix: \n" + weightMatrix.toString("N", "N"));
            System.out.println("[2] Obtain bipolar matrix for input \n" + bipolarMatrix);
            System.out.println("[3] Dot product bipolar matrix and each of the columns in weight matrix");

        }

        IntStream.range(0, input.length).forEach( column -> {

            try {

                Matrix columnMatrix = weightMatrix.getColumnMatrix(column);
                double dotProductResult = bipolarMatrix.dotProduct(columnMatrix);

                if (Main.mode == Main.Mode.VERBOSE) {

                    System.out.println("[3." + String.format("%02d", column) + "] (bipolar matrix) . (Weight matrix column " + String.format("%02d", column) + ") = ");

                }

                if (dotProductResult > 0) {

                    output[column] = 1.00;

                    if (Main.mode == Main.Mode.VERBOSE) System.out.println(" " + dotProductResult + "  > 0  ==>  1");

                } else {

                    output[column] = 0;

                    if (Main.mode == Main.Mode.VERBOSE) System.out.println(" " + dotProductResult + "  <= 0  ==>  0");

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        });

        return output;

    }

    static double[] toBipolar(double[] pattern) {

        double[] bipolarPattern = new double[pattern.length];

        IntStream.range(0, pattern.length).forEach( row -> {

            bipolarPattern[row] = (pattern[row] * 2) - 1;

        });

        return bipolarPattern;

    }

    static double[] fromBipolar(double[] bipolarPattern) {

        double[] pattern = new double[bipolarPattern.length];

        IntStream.range(0, bipolarPattern.length).forEach( row -> {

            pattern[row] = (bipolarPattern[row] + 1) / 2;

        });

        return pattern;

    }

}