## SOLID Calculator

This is a simple program written in Java 11 using solid
principles. 

Functionality:
 * Calculate expression, like `(4*7 - 9/3) + 2^10`
 * Show last 5 operations (write `/history`)
 * Can exit (write `/exit`)
 
Contains tests for JUnit  
100% test coverage

To make it build or tested, set JAVA_HOME to JDK 11 directory, and run  
`./gradlew build` or `./gradlew test`
 
To create jar with calculator inside, use  
`./gradlew jar`  
It will be created in `build/libs`