# Simulation of Computer System

Simulate a Computer system consisting of CPU (Parent process) and Memory (Child process) through `Fork` and `Inter-process communication (IPC)` established through Pipe.

Sample input file: `input.txt`

This generates 3 random numbers (between 1 to 100) and displays them as Initial list.  
Then it sorts these numbers in ascending order and displays them as a sorted list.

Steps to execute the program: `Java CPU input.txt 20`,

1. Complie `CPU.java` & `Memory.java`, using `javac CPU.java` & `javac Memory.java`
2. Run the program using `java CPU <input_file> <timer>`  
   Example : `java CPU input.txt 20`  
   Sample output :

```
Initial List: 89 17 37
Sorted  List: 17 37 89
```

Note: Numbers in the list would be random (between 1 to 100) for each execution
