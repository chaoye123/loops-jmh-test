# loops-jmh-playground

## Latest results:

|Benchmark                                  | Mode  |Cnt  |Score    |Error | Units|
|---------                                  | ----  |---  |-----    |----- | -----|
|LoopBenchmarkMain.forEachLambdaMaxInteger  | avgt  | 10  |0.159 ?  |0.069 | ms/op|
|LoopBenchmarkMain.forEachLoopMaxInteger    | avgt  | 10  |0.116 ?  |0.001 | ms/op|
|LoopBenchmarkMain.forMax2Integer           | avgt  | 10  |0.102 ?  |0.001 | ms/op|
|LoopBenchmarkMain.forMaxInteger            | avgt  | 10  |0.103 ?  |0.001 | ms/op|
|LoopBenchmarkMain.iteratorMaxInteger       | avgt  | 10  |0.116 ?  |0.001 | ms/op|
|LoopBenchmarkMain.lambdaMaxInteger         | avgt  | 10  |0.603 ?  |0.001 | ms/op|
|LoopBenchmarkMain.parallelStreamMaxInteger | avgt  | 10  |0.090 ?  |0.002 | ms/op|
|LoopBenchmarkMain.streamMaxInteger         | avgt  | 10  |0.123 ?  |0.003 | ms/op|

