#       Nth Prime Number Calculation Program
*Last updated on October 21, 2021*<br />
**Authors: Idea from Dr. Kevin Brewer. Code by Filip S. Ramazan**<br />
## How to run project:<br />
To run the code, compile the ```.java``` file the IDE of your choice, and then run PrimeNumber class. <br />
This project was made with [BlueJ](https://bluej.org/), and if you are using another IDE, delete all the files except<br />
the ```.java``` file. <br />

## What this project does:<br />
Basically, the program will ask the user for an integer, ***n***. The program will calculate, as fast as possible, the ***nth*** prime.<br />
The program will intermittently give the user updates on how many primes were found. After program is done, <br />
it will write all the primes from 2 to the _nth_ prime in a ```.txt``` file. This file will be **read-only**. After writing the text file, a ```.zip``` file will be compiled from the text file. The files are written in the program's path. _**After the first run of the program:**_ the program will collect all the primes from the said created file, and use them to optimize performance, doing the same task as above. If more primes are calculated, then those will be written to the file too.
