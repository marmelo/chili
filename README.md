# Chili

[![Build Status](https://travis-ci.org/marmelo/chili.svg?branch=master)](https://travis-ci.org/marmelo/chili)

**Chili** is a collection of Java annotations that simplify common tasks.

These annotations allow to dynamically alter the functionality of class methods
without changing their implementation. Chili relies on [Guice AOP](https://github.com/google/guice/wiki/AOP)
to process the annotations and requires JDK 1.7 or higher.

The following [annotations](#annotations) are currently implemented:

- [@Memoize](#memoize) - Cache the method computed result.


## Configuration

There are no major Chili releases so you can only find the latest snapshot release at
Sonatype's repository. You need to add the following dependency to your ```pom.xml```:

```xml
<dependency>
  <groupId>me.defying.chili</groupId>
  <artifactId>chili</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

Then, you need to configure Sonatype snapshot repository:

```xml
<repositories>
  <repository>
    <id>sonatype.oss.snapshots</id>
    <name>Sonatype OSS Snapshot Repository</name>
    <url>http://oss.sonatype.org/content/repositories/snapshots</url>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```

## Usage

Are you in a hurry? Please refer to the provided [```chili-example```](https://github.com/marmelo/chili/tree/master/chili-example) project.

To use Chili annotations you only need to perform three steps:

1. Add annotations to your code;
2. Create a Guice ```Injector``` registering a ```ChiliModule``` instance;
3. Compile and run your project.

Not acquainted with Guice? Please read its [documentation](https://github.com/google/guice/wiki/Motivation).

Lets create a simple class with a [@Memoize](#memoize) annotation:

```java
public class MemoizeService {

    /**
     * Returns the given value raised to the power of two.
     * 
     * @param x the base value.
     * @return the value {@code x}<sup>2</sup>.
     */
    @Memoize
    public int power(int x) {
        return x * x;
    }
}
```

The [@Memoize](#memoize) annotation will store the method results and return them when
the same inputs occur again. That is, if we call multiple times the ```power``` method
with the same argument it will only perform the calculation the first time and return
cached results in the next invocations.

Nevertheless, the [@Memoize](#memoize) annotation alone produces no effect. We need to
introduce a Guice ```Injector``` to intercept the method invocation and inject new meaning
to it.

To create an empty ```Injector``` you only need to write:

```java
Guice.createInjector();
```

The ```createInjector``` method receives a list of modules that contain the application
[bindings](https://github.com/google/guice/wiki/Bindings). To simplify this process it
is provided a ```ChiliModule``` class with all the Chili annotations bindings configured.

```java
Guice.createInjector(new ChiliModule());
```

If your are already using Guice in you project you may add the ```ChiliModule``` next to yours.

```java
Guice.createInjector(new MyExistingModule(), new ChiliModule());
```

Now that you have created the ```Injector``` you need to use it to inject the annotation
bindings into your code.

```java
public class MemoizeExample {
    
    public static void main(String[] args) {
        // create the injector
        Injector injector = Guice.createInjector(new ChiliModule());
 
        // create an injected instance of the service
        MemoizeService service = injector.getInstance(MemoizeService.class);
        
        // cache miss
        service.power(3);
        
        // cache hit
        service.power(3);
    }
}
```

Please refer to the [```chili-example```](https://github.com/marmelo/chili/tree/master/chili-example)
project for further examples.


## Annotations

#### @Memoize

Stores results of expensive function calls and returns the cached result when the
same inputs occur again. Please refer to [Wikipedia](https://en.wikipedia.org/wiki/Memoization)
for further details.

The [@Memoize](#memoize) annotation accepts the following elements:

| Element    | Type     | Default      | Description  |
| ---------- | -------- | ------------ | ------------ |
| ```size```       | long     | 0L           | Maximum entries the cache may have. |
| ```time```       | long     | 0L           | For how long the method invocation should be cached according to the ```unit``` value. If unspecified or equal to zero the cache will not be time bound. |
| ```unit```       | TimeUnit | MILLISECONDS | The ```TimeUnit``` the ```time``` value refers to. |
| ```statistics``` | boolean  | false        | Whether or not cache statistics should be logged. |

When the ```size``` and ```time``` values are active, both are enforced.
