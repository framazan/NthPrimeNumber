
/**
 * This program outputs the nth prime number based on user input of n (positive integer). Recall 2 is the first prime number.
 * The program writes the prime numbers to a read-only file and zips the file.
 * 
 * Example of input/output:
 * Enter an integer, n, to find the nth prime:
 * 1000
 * The 1000th prime is: 7919
 *
 * @author Filip Ramazan
 * @version 10/1/2021 - Fall 2021
 * @javaversion 11.0.10
 */

import java.util.Scanner; //allows input from user
import java.util.ArrayList; //to use an list and its properties
import java.time.temporal.ChronoUnit; //to save time it takes program to calc nth prime
import java.io.BufferedReader; //buffered reader for file!
import java.io.FileReader; //reader
import java.time.*; //to measure time it takes for program to calc nth prime
import java.io.IOException; //to catch any ioexception
import java.io.FileNotFoundException; //in case the primes file is not found
import java.io.BufferedWriter; //java file writer
import java.io.FileWriter; //write to file using os
import java.nio.file.*; //to define a file in java
import java.util.zip.*; //to zip a file in java
import java.util.*;
import java.io.*;


public class PrimeNumber
{
    Scanner sc = new Scanner(System.in); //the input scanner
    int n; //user value for nth prime
    String nthprime; //the nth prime
    final static String filePath = "./primelist.txt"; //prime list file path 
    ArrayList<Integer> prime_numbers = new ArrayList<Integer>(); //list of prime numbers
    /**
     * Constructor for objects of class PrimeNumber
     */
    public PrimeNumber()
    {
      try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
        changeFilePermissions(true, false); //change text file permission to writable and readable
        boolean file_read= false; //the file has not been read yet.
        String line = bufferedReader.readLine();
        while(line != null) { //until no more primes on each line
          if(line == "\n") {
              //don't do anything for newline
            } else {
           prime_numbers.add(Integer.parseInt(line)); //change the line string to an int
           line = bufferedReader.readLine(); //read the next line
            }
          } 
        file_read = true; //the file has been read
        System.out.println("Prime list file was found. The program will use it to increase performance."); //informing user
        changeFilePermissions(false, false);//file to readable and not writable
        }
       catch (FileNotFoundException e) { //in case the prime list file is not found
            changeFilePermissions(true, false); //file to writable
            prime_numbers.add(2); //add the two basic primes to the list
            prime_numbers.add(3);
            System.out.println("Prime list file could not be found. The program will be less performant."); //inform user
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
                String fileContent = "2\n3\n"; // add basic primes to file
                bufferedWriter.write(fileContent); //write
                System.out.println("File was written with minimal prime numbers and will be used for future program reference.");//inform
            } catch (IOException ex) {
                // Exception handling
                System.out.println("File could not written. Program continues with decreased performance.");
              }
            changeFilePermissions(false, false);
        } catch(IOException e) {
          if(contains(2) == false) {
              prime_numbers.add(2);
          }
          if(contains(3) == false) {
              prime_numbers.add(3);
          }
          System.out.println("IOException was thrown and caught. Prime list file was not rendered completely. The program will be less performant.");
       } catch (NumberFormatException e) {
            System.out.println("Number formatting exception was caught. Prime list file was not rendered. Program will be less performant.");
            if(contains(2) == false) { 
                //if list doesn't contain basic primes add them
                if(prime_numbers.size() > 0){
                    prime_numbers.clear();
                }
                prime_numbers.add(2);
              }
            if(contains(3) == false) {
                if(prime_numbers.size() > 1){
                    prime_numbers.clear();
                }
                prime_numbers.add(3);
              }
        }
      getPositiveIntInput("Enter an integer n to find the nth prime:");
      
      ZonedDateTime start = ZonedDateTime.now(); //start stopwatch
      int q = 0;
      boolean notPrime = false;
      int i = prime_numbers.get(prime_numbers.size()-1)+2; //i is the prime to be tested
      try{
          nthprime = Integer.toString(prime_numbers.get(n-1)); //if the prime is already in the prime list, then just print it
      } catch (IndexOutOfBoundsException j) {
        while(prime_numbers.size() < n){
         while(prime_numbers.get(q) <=  Math.sqrt(i)){
            //if remainder of i divided by the qth prime found is 0, i is not prime
            if ((i%prime_numbers.get(q))==0){ 
                notPrime = true;
                break;
             }
            q++;
            //System.out.println(prime_numbers.toString()); //for testing
         }
         //if prime:
         if(!notPrime){
            prime_numbers.add(i); //add i to 
            //System.out.println("Number tested: " + i); //for testing
            //System.out.println("Last tested q: " + q);
            if (prime_numbers.size() % 70000 == 0) {
                System.out.println("Program has currently found " + prime_numbers.size() + " primes."); //print progress
            }
         }
         notPrime = false;
         i += 2; //always go to the next odd number
       }
       nthprime = Integer.toString(prime_numbers.get(prime_numbers.size()-1)); //nth prime is defined as the last number in the prime listd
      }
      
      long end = start.until(ZonedDateTime.now(), ChronoUnit.MILLIS); // stop stopwatch
      addPrimesToFile(); //add everything in the prime list to the file
      System.out.printf( "\nThe nth prime is: " + "%,d",Integer.parseInt(nthprime)); //give user nth prime
      System.out.println("\nTo calculate the "+ n +"th prime, it took: " + end + " milliseconds."); //state the time taken
     }
    private boolean contains(int number) {
        return prime_numbers.contains(number); //this method check to see if a number is in the prime list
    }
    public int getPositiveIntInput(String message) {    
        System.out.print(message); //print the message to user
        while (true) {
            String line = sc.nextLine(); //clear out new line char
            line = line.replace(",",""); //replace any commas as possible seperators e.g. 10,000,000
            try {
                n = Integer.parseInt(line); //parse the input to int
                if (n > 0) {
                    return n; //if n is larger than zero it is valid
                }
            } catch (NumberFormatException e) {
                // ok to ignore: let it fall through to print error message and try next time
            }
            System.out.print("Error: input must be a positive integer.\n" + message); //give error as restart input process
        }
    }
    public void addPrimesToFile(){
        changeFilePermissions(true, false); //change to writable file
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for(int f:prime_numbers) {
                bufferedWriter.write(f + "\n"); //add each prime in list to the file with a newline char
                }
            }
         catch (FileNotFoundException e) { 
            //if file is not found, create is and add all primes in list
            System.out.println("\nPrime list file was not found. Creating it now...");
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
                for(int f:prime_numbers) {
                    bufferedWriter.write(Integer.toString(f)+"\n"); //loop through all prime number in the list
                    }
            } catch (IOException y) {
                System.out.println("\nCouldn't write primes to file. Future program instances will be less performant."); //don't write primes if ioexception occurs.
            }
         } catch (IOException y) {
             System.out.println("\nCouldn't write primes to file. Future program instances will be less performant."); //don't write the primes
           }
        System.out.println("\nThe prime numbers have been added to text file for future use.\nAdding zip file of prime number list to directory.");
        zipFile(filePath); //zip text file
        changeFilePermissions(false, true); //change file to readable not writable
    }
    private void changeFilePermissions(boolean write, boolean print){
        File file = new File(filePath);
        // check if file exists
        boolean exists = file.exists();
        if(exists == true) {
            // changing the file permissions
            file.setExecutable(true); //alaways executable
            file.setReadable(true); //always readble
            file.setWritable(write); //argument is boolean that sets writable
            if(print == true){
            // printing the permissions associated with the file currently
            System.out.println("Prime list text file is:\n\nExecutable: " + file.canExecute()+"\n\nReadable: " + file.canRead());
            System.out.println("\nWritable: "+ file.canWrite());
            }
        } else {
            System.out.println("\nPrime number file cannot be found. Permissions not updated."); //no permsission updated if no file exists
        }
    }
    private void zipFile(String path){
        try {
            changeFilePermissions(true, false); //making the file writable
            File file = new File(path); //defining a file
            String zipFileName = file.getName().concat(".zip"); //getting a new filename with .zip
            FileOutputStream filezip = new FileOutputStream(zipFileName);
            ZipOutputStream finalfile = new ZipOutputStream(filezip);// Creating zip outputstream for zipping the file by creating objects
            finalfile.putNextEntry(new ZipEntry(file.getName()));

            byte[] bytes = Files.readAllBytes(Paths.get(filePath)); //read all the bytes in the text file
        
            finalfile.write(bytes, 0, bytes.length); //write all the bytes
            finalfile.closeEntry(); //close the entry to the file
            
            finalfile.close(); //close the file
            System.out.println("\nThe prime list text file was added in a .zip format if needed.");
            changeFilePermissions(false, false); //no longer writable
        } catch (FileNotFoundException ex) {
            System.err.format("The file %s does not exist", filePath + ". The text file was not zipped.") ; //no file exists
        } catch (IOException ex) {
            System.err.println("I/O error when zipping text file. Prime list file no longer was zipped."); //io exception, nothing happens
        }
    }
}