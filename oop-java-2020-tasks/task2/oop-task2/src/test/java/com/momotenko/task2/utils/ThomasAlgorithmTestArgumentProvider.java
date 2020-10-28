package com.momotenko.task2.utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ThomasAlgorithmTestArgumentProvider implements ArgumentsProvider {

    List<Double> test1A = new ArrayList<>(Arrays.asList(0.0,3.0,-5.0,2.0));
    List<Double> test1B = new ArrayList<>(Arrays.asList(1.0,2.0,-20.0,-4.0));
    List<Double> test1C = new ArrayList<>(Arrays.asList(2.0,-1.0,-5.0,0.0));
    List<Double> test1D = new ArrayList<>(Arrays.asList(4.0,-5.0,-1.0,3.0));

    List<Double> test2A = new ArrayList<>(Arrays.asList(0.0,6.0,6.0,4.0,-7.0,5.0));
    List<Double> test2B = new ArrayList<>(Arrays.asList(2.0,4.0,2.0,-8.0,2.0,-5.0));
    List<Double> test2C = new ArrayList<>(Arrays.asList(3.0,2.0,5.0,-9.0,-8.0,0.0));
    List<Double> test2D = new ArrayList<>(Arrays.asList(8.0,4.0,1.0,-8.0,5.0,-8.0));

    List<Double> test3A = new ArrayList<>(Arrays.asList(0.0,1.0,3.0,1.0,2.0,1.0));
    List<Double> test3B = new ArrayList<>(Arrays.asList(5.0,5.0,5.0,10.0,9.0,2.0));
    List<Double> test3C = new ArrayList<>(Arrays.asList(4.0,3.0,1.0,1.0,3.0,0.0));
    List<Double> test3D = new ArrayList<>(Arrays.asList(6.0,8.0,7.0,-3.0,2.0,1.0));

    private Double getRandom(int cap){
        return Double.valueOf(new Random().nextInt(2*cap)- cap);
    }

    private List<Double> generateList(int cap){
        List<Double> result = new ArrayList<>(cap);

        for(int i = 0; i < cap;++i){
            result.add(getRandom(cap));
        }

        return result;
    }

    private List<List<Double>> generateTestMatrixAndD(int cap){
        return new ArrayList<>(Arrays.asList(generateList(cap),generateList(cap),generateList(cap),generateList(cap)));
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(new ArrayList(Arrays.asList(test1A,test1B,test1C,test1D)),
                new ArrayList(Arrays.asList(test2A,test2B,test2C,test2D)),
                new ArrayList(Arrays.asList(test3A,test3B,test3C,test3D)),
                generateTestMatrixAndD(500),
                generateTestMatrixAndD(1000),
                generateTestMatrixAndD(5000),
                generateTestMatrixAndD(10000),
                generateTestMatrixAndD(15000),
                generateTestMatrixAndD(30000),
                generateTestMatrixAndD(50000),
                generateTestMatrixAndD(100000),
                generateTestMatrixAndD(500000),
                generateTestMatrixAndD(1000000)
                ).map(Arguments::of);
    }
}
