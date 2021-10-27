# JMH Java基准测试工具套件

## 什么是JMH

### 官网

 http://openjdk.java.net/projects/code-tools/jmh/ 

## 创建JMH测试

1. 创建Maven项目，添加依赖

   ```java
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <properties>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
           <encoding>UTF-8</encoding>
           <java.version>1.8</java.version>
           <maven.compiler.source>1.8</maven.compiler.source>
           <maven.compiler.target>1.8</maven.compiler.target>
       </properties>
   
       <groupId>mashibing.com</groupId>
       <artifactId>HelloJMH2</artifactId>
       <version>1.0-SNAPSHOT</version>
   
   
       <dependencies>
           <!-- https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-core -->
           <dependency>
               <groupId>org.openjdk.jmh</groupId>
               <artifactId>jmh-core</artifactId>
               <version>1.21</version>
           </dependency>
               
          <!-- 官方提供的样例程序>   
          <dependency>
               <groupId>org.openjdk.jmh</groupId>
               <artifactId>jmh-samples</artifactId>
               <version>1.21</version>
           </dependency>
   
           <!-- https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-generator-annprocess -->
           <dependency>
               <groupId>org.openjdk.jmh</groupId>
               <artifactId>jmh-generator-annprocess</artifactId>
               <version>1.21</version>
               <scope>test</scope>
           </dependency>
       </dependencies>
   
   
   </project>
   ```

2. idea安装JMH插件 JMH plugin v1.0.3或者JMH Java Microbenchmark Harness

3. 由于用到了注解，打开运行程序注解配置

   > compiler -> Annotation Processors -> Enable Annotation Processing

4. 定义需要测试类PS (ParallelStream)

   ```java
   package com.mashibing.jmh;
   
   import java.util.ArrayList;
   import java.util.List;
   import java.util.Random;
   
   public class PS {
   
   	static List<Integer> nums = new ArrayList<>();
   	static {
   		Random r = new Random();
   		for (int i = 0; i < 10000; i++) nums.add(1000000 + r.nextInt(1000000));
   	}
   
   	static void foreach() {
   		nums.forEach(v->isPrime(v));
   	}
   
   	static void parallel() {
   		nums.parallelStream().forEach(PS::isPrime);
   	}
   	
   	static boolean isPrime(int num) {
   		for(int i=2; i<=num/2; i++) {
   			if(num % i == 0) return false;
   		}
   		return true;
   	}
   }
   ```

5. 写单元测试

   > 这个测试类一定要在test package下面
   >
   > ```java
   > package com.mashibing.jmh;
   > 
   > import org.openjdk.jmh.annotations.Benchmark;
   > 
   > import static org.junit.jupiter.api.Assertions.*;
   > 
   > public class PSTest {
   >     @Benchmark
   >     public void testForEach() {
   >         PS.foreach();
   >     }
   > }
   > ```

6. 运行测试类，如果遇到下面的错误：

   ```java
   ERROR: org.openjdk.jmh.runner.RunnerException: ERROR: Exception while trying to acquire the JMH lock (C:\WINDOWS\/jmh.lock): C:\WINDOWS\jmh.lock (拒绝访问。), exiting. Use -Djmh.ignoreLock=true to forcefully continue.
   	at org.openjdk.jmh.runner.Runner.run(Runner.java:216)
   	at org.openjdk.jmh.Main.main(Main.java:71)
   ```

   这个错误是因为JMH运行需要访问系统的TMP目录，解决办法是：

   打开RunConfiguration -> Environment Variables -> include system environment viables

7. 阅读测试报告

```java
# JMH version: 1.21
# VM version: JDK 11.0.2, Java HotSpot(TM) 64-Bit Server VM, 11.0.2+9-LTS
# VM invoker: C:\work\jdk\jdk-11.0.2\bin\java.exe
# VM options: -javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2021.2.2\lib\idea_rt.jar=7269:C:\Program Files\JetBrains\IntelliJ IDEA 2021.2.2\bin -Dfile.encoding=UTF-8
# Warmup: 1 iterations, 3 s each
# Measurement: 1 iterations, 3 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: com.mashibing.jmh.PSTest.testForEach

# Run progress: 0.00% complete, ETA 00:00:24
# Fork: 1 of 2
# Warmup Iteration   1: 4.487 ops/s
Iteration   1: 4.455 ops/s

# Run progress: 25.00% complete, ETA 00:00:21
# Fork: 2 of 2
# Warmup Iteration   1: 6.233 ops/s
Iteration   1: 6.264 ops/s


Result "com.mashibing.jmh.PSTest.testForEach":
  5.359 ops/s


# JMH version: 1.21
# VM version: JDK 11.0.2, Java HotSpot(TM) 64-Bit Server VM, 11.0.2+9-LTS
# VM invoker: C:\work\jdk\jdk-11.0.2\bin\java.exe
# VM options: -javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2021.2.2\lib\idea_rt.jar=7269:C:\Program Files\JetBrains\IntelliJ IDEA 2021.2.2\bin -Dfile.encoding=UTF-8
# Warmup: 1 iterations, 3 s each
# Measurement: 1 iterations, 3 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: com.mashibing.jmh.PSTest.testForEach1

# Run progress: 50.00% complete, ETA 00:00:14
# Fork: 1 of 2
# Warmup Iteration   1: 0.193 s/op
Iteration   1: 0.192 s/op

# Run progress: 75.00% complete, ETA 00:00:07
# Fork: 2 of 2
# Warmup Iteration   1: 0.206 s/op
Iteration   1: 0.206 s/op


Result "com.mashibing.jmh.PSTest.testForEach1":
  0.199 s/op


# Run complete. Total time: 00:00:28

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark             Mode  Cnt  Score   Error  Units
PSTest.testForEach   thrpt    2  5.359          ops/s
PSTest.testForEach1   avgt    2  0.199           s/op

Process finished with exit code 0

```



## JMH中的基本概念

1. **@Warmup**
   预热，由于JVM中对于特定代码会存在优化（本地化），预热对于测试结果很重要
   
   ```java
   @Target({ElementType.METHOD,ElementType.TYPE})
   @Retention(RetentionPolicy.RUNTIME)
   @Inherited
   public @interface Warmup {
       int BLANK_ITERATIONS = -1;
       int BLANK_TIME = -1;
       int BLANK_BATCHSIZE = -1;
       // 预热次数
       int iterations() default BLANK_ITERATIONS;
       // 每次预热时间，这里指的是预热的时间，在预热时间内，会执行多次方法，方法执行的次数不确定
       int time() default BLANK_TIME;
       // 时间类型
       TimeUnit timeUnit() default TimeUnit.SECONDS;
       // 每次操作执行地方法数量
       int batchSize() default BLANK_BATCHSIZE;
   }
   ```
   
2. **@Mesurement**
   总共执行多少次测试

   ```java
   @Inherited
   @Target({ElementType.METHOD,ElementType.TYPE})
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Measurement {
       int BLANK_ITERATIONS = -1;
       int BLANK_TIME = -1;
       int BLANK_BATCHSIZE = -1;
       // 执行次数
       int iterations() default BLANK_ITERATIONS;
   	// 每次执行时间，这里指的是执行的时间，在执行时间内，会执行多次方法，方法执行的次数不确定
       int time() default BLANK_TIME;
       // 时间类型
       TimeUnit timeUnit() default TimeUnit.SECONDS;
   	// 每次操作执行地方法数量
       int batchSize() default BLANK_BATCHSIZE;
   }
   ```

3. **@Benchmark**
   测试哪一段代码,类似于Junit中的@Test

   ```java
   @Target(ElementType.METHOD)
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Benchmark {
   }
   ```

4. **@Benchmark mode**
     基准测试的模式

   ```java
   @Inherited
   @Target({ElementType.METHOD, ElementType.TYPE})
   @Retention(RetentionPolicy.RUNTIME)
   public @interface BenchmarkMode {
       Mode[] value();
   }
   
   public enum Mode {
     	// 吞吐量：每单位时间的操作数
       Throughput("thrpt", "Throughput, ops/time"),
     	// 平均时间：每次操作的平均时间
       AverageTime("avgt", "Average time, time/op"),
       // 采样时间：对每个操作的时间进行采样
       SampleTime("sample", "Sampling time"),
       // 单次激发时间：测量单次操作的时间
       SingleShotTime("ss", "Single shot invocation time"),
       // 元模式：所有基准模式。这对于内部JMH测试非常有用。
       All("all", "All benchmark modes"),
       ;
   }
     
   ```

   ```java
   // 测试报告
   Benchmark                                   Mode  Cnt  Score   Error  Units
   PSTest.testForEach2                        thrpt    2  5.494          ops/s
   PSTest.testForEach2                         avgt    2  0.173           s/op
   PSTest.testForEach2                       sample   33  0.188 ± 0.006   s/op
   PSTest.testForEach2:testForEach2·p0.00    sample       0.177           s/op
   PSTest.testForEach2:testForEach2·p0.50    sample       0.193           s/op
   PSTest.testForEach2:testForEach2·p0.90    sample       0.196           s/op
   PSTest.testForEach2:testForEach2·p0.95    sample       0.207           s/op
   PSTest.testForEach2:testForEach2·p0.99    sample       0.208           s/op
   PSTest.testForEach2:testForEach2·p0.999   sample       0.208           s/op
   PSTest.testForEach2:testForEach2·p0.9999  sample       0.208           s/op
   PSTest.testForEach2:testForEach2·p1.00    sample       0.208           s/op
   PSTest.testForEach2
   ```

5. **@Fork**

   进行 fork 的次数。可用于类或者方法上。如果 fork 数是2的话，则 JMH 会 fork 出两个进程来进行测试。

   ```java
   @Inherited
   @Target({ElementType.METHOD,ElementType.TYPE})
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Fork {
       int BLANK_FORKS = -1;
       String BLANK_ARGS = "blank_blank_blank_2014";
   
       int value() default BLANK_FORKS;
   }
   ```

6. **@Threads**

   每个进程中的测试线程，可用于类或者方法上。一般选择为cpu乘以2。如果配置了 Threads.MAX ，代表使用 Runtime.getRuntime().availableProcessors() 个线程。

   ```java
   @Inherited
   @Target({ElementType.METHOD,ElementType.TYPE})
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Threads {
       int MAX = -1;
       int value();
   }
   ```

7. **@OutputTimeUnit**

   输出时间的单位类型

   ```java
   @Inherited
   @Target({ElementType.METHOD,ElementType.TYPE})
   @Retention(RetentionPolicy.RUNTIME)
   public @interface OutputTimeUnit {
       TimeUnit value();
   }
   ```

   

## Next

官方样例：
http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/

