package com.takipi.oss.benchmarks.jmh.loops;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * 如何运行：
 * 
 * a) Via command-line:
 * 
 * $ mvn clean install
 * 
 * $ java -jar target/loops-jmh-1.0.jar
 * 
 * 
 * 
 *  Run complete. Total time: 00:17:41

Benchmark                                   Mode  Cnt  Score   Error  Units

LoopBenchmarkMain.forEachLambdaMaxInteger   avgt   10  0.532 ± 0.008  ms/op

LoopBenchmarkMain.forEachLoopMaxInteger     avgt   10  0.110 ± 0.001  ms/op

LoopBenchmarkMain.forMaxInteger             avgt   10  0.212 ± 0.004  ms/op

LoopBenchmarkMain.iteratorMaxInteger        avgt   10  0.111 ± 0.002  ms/op

LoopBenchmarkMain.lambdaMaxInteger          avgt   10  0.537 ± 0.015  ms/op

LoopBenchmarkMain.parallelStreamMaxInteger  avgt   10  0.349 ± 0.027  ms/op

LoopBenchmarkMain.streamMaxInteger          avgt   10  0.605 ± 0.065  ms/op
 * 
 * @author jacky
 * @date 2017年2月6日
 */
@State(Scope.Benchmark)
public class LoopBenchmarkMain {

    volatile int size = 100000;
    volatile List<Integer> integers = null;

    public static void main(String[] args) {
        LoopBenchmarkMain benchmark = new LoopBenchmarkMain();
        benchmark.setup();

        System.out.println("iteratorMaxInteger max is: " + benchmark.iteratorMaxInteger());
        System.out.println("forEachLoopMaxInteger max is: " + benchmark.forEachLoopMaxInteger());
        System.out.println("forEachLambdaMaxInteger max is: " + benchmark.forEachLambdaMaxInteger());
        System.out.println("forMaxInteger max is: " + benchmark.forMaxInteger());
        System.out.println("parallelStreamMaxInteger max is: " + benchmark.parallelStreamMaxInteger());
        System.out.println("streamMaxInteger max is: " + benchmark.streamMaxInteger());
        System.out.println("iteratorMaxInteger max is: " + benchmark.lambdaMaxInteger());
    }

    @Setup
    public void setup() {
        integers = new ArrayList<Integer>(size);
        populate(integers);
    }

    public void populate(List<Integer> list) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(10000000));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int iteratorMaxInteger() {
        int max = Integer.MIN_VALUE;
        for (Iterator<Integer> it = integers.iterator(); it.hasNext();) {
            max = Integer.max(max, it.next());
        }
        return max;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int forEachLoopMaxInteger() {
        int max = Integer.MIN_VALUE;
        for (Integer n : integers) {
            max = Integer.max(max, n);
        }
        return max;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int forEachLambdaMaxInteger() {
        final Wrapper wrapper = new Wrapper();
        wrapper.inner = Integer.MIN_VALUE;

        integers.forEach(i -> helper(i, wrapper));
        return wrapper.inner.intValue();
    }

    public static class Wrapper {

        public Integer inner;
    }

    private int helper(int i, Wrapper wrapper) {
        wrapper.inner = Math.max(i, wrapper.inner);
        return wrapper.inner;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int forMaxInteger() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            max = Integer.max(max, integers.get(i));
        }
        return max;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int parallelStreamMaxInteger() {
        Optional<Integer> max = integers.parallelStream().reduce(Integer::max);
        return max.get();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int streamMaxInteger() {
        Optional<Integer> max = integers.stream().reduce(Integer::max);
        return max.get();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int lambdaMaxInteger() {
        return integers.stream().reduce(Integer.MIN_VALUE, (a, b) -> Integer.max(a, b));
    }
}
