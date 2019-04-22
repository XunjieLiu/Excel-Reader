# Twitter Data Analyser Program Version 1.0 22/04/2019
This programm will analysis twitter data in Excel files and retrieve top 10 users and tweets from it. It also provides with functions of searching.

## Getting Started


### Prerequisites

Apache POI API, Lucene API and Junit are needed to be added to referenced library

```
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>4.0.1</version>
</dependency>

<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>4.0.1</version>
</dependency>

<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-core</artifactId>
    <version>8.0.0</version>
</dependency>

<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-analyzers-common</artifactId>
    <version>8.0.0</version>
</dependency>

<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-queryparser</artifactId>
    <version>8.0.0</version>
</dependency>

<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-queries</artifactId>
    <version>8.0.0</version>
</dependency>

<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```

### How to setup

The url of excel file which contains the data of twitter needed to be provided in package **dao -> ExcelDAOImpl.java -> read()**

```
OPCPackage pkg = OPCPackage.open(new File("D:\\Study\\Year 3\\CSE210\\CW\\dataset.xlsx"));
```

And url of folders which stores the lucene index files , in package **service -> Menu.java -> global variable "INDEX_DIR"**

```
private static final String INDEX_DIR = "D:\\Study\\Year 3\\CSE210\\CW\\luceneIndex";
```


## How to use

Run the **Main.java** in package service

```
javac Main.java
java Main
```

## Authors

* **Xunjie Liu** - *Initial work* - see also: https://github.com/XunjieLiu/Excel-Reader

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

