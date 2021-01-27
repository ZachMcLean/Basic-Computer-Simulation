// Program: Basic Computer
// Authors: Zach McLean, Mohammed Rehaan, Gary Lowe
// Creation Date: 10/29/18
// Purpose: Simulate a machine code program that fetches, decodes, and executes.

import java.util.*; // Utilites package
import java.io.*;   // Io Package

   public class BasicComputer
   {
         static int accumulator = 0;         // Accumulator to hold values
         static int instructionRegister = 0; // Decodes Operand and opCode
         static int opCode = 0;              // Determines the operation that will be performed
         static int operand = 0;             // Tells counter where to point to in memory
         static int programCounter = 0;      // Increments every time program executes, points at memory
         
         static boolean error = false;    // Error variable for errors
         static int[] ram = new int[100]; // Memory
         
         static String fileName; // variable to hold address of a file
         
      
      public static void main(String[] args) throws IOException
      {
         
         
         // Intialize memory
         for(int i = 0; i < 100; i++)
            ram[i] = 0;
         
         System.out.println("\t\t\t\t\t\t--Basic-Computer-Simulation--"); // Title
         System.out.println();
         
         LoadRam(); // Loads machine code from file into memory
            
         while(opCode != 50 && error == false) // loop for computer simulation; fetch, decode, execute.
         {
            // Calls fetch function and passes the counter as an argument. stores the return value on instruction register
            instructionRegister = fetch(programCounter); 
            dump();
            decode();
            dump();
            execute();
            dump();
         }
         memoryDump();
      }
      
      /**
      Fetch increments the counter and returns the element of memory that it points to.
      @param location is a variable that holds the location in memory that the counter should point to.
      if loaction is greater than 100 or less than 0 it will return and error code and halt the program.
      */
      
      public static int fetch(int location) throws IOException
      {
         if(location > 100 || location < 0)
         {
            error = true;
            System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
            System.out.println("\t\t\t\t\t\t--Location does not exist in memory--");
            System.out.println();
            return(50);
         }
         else
         {
            programCounter++;
            return(ram[location]);
         }
      }
      
      /**
      Decode splits the instruction register into the opCode and operand
      if the IR is more than 5000 it will print an error code and halt the program.
      opcode is equal to the first two digits of the IR
      operand is equal to the last two digits of the IR
      */
      public static void decode()
      {
         if(instructionRegister > 5000)
         {
            System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
            System.out.println("\t\t\t\t\t\t--Invalid operation Code--");
            System.out.println();
            error = true;
         }
         else
         {
            opCode = (instructionRegister/100);
            operand = (instructionRegister%100);
         }
      }
      
      /**
      Execute corresponing opCode.
      */
      public static void execute()
      {
         Scanner keyboard = new Scanner(System.in);
         String data = "";
         int number;
         char ascii;
         final int accMax = 9999;
         final int accMin = -9999;
         
         switch(opCode)
         {
            case 34: // Write Value
            {
               for(int i = 0; i < accumulator; i++)
                  data = data + ram[operand+i];
               System.out.println("\t\t\t\t\t\tWrite Value: " + data);
               System.out.println();
               break;
            }
            
            case 35: // Write Ascii
            {
               System.out.print("\t\t\t\t\t\tWrite Ascii Character: ");
               for(int i = 0; i < accumulator; i++)
               {
                  ascii = (char)ram[operand+i];
                  System.out.print(ascii);
               }
               System.out.println();
               System.out.println();
               break;
            }
            
            case 33: // Read
            {
               System.out.print("Please enter a number between -9999 and 9999: ");
               number = keyboard.nextInt();
               System.out.println();
               
               if(number > 9999 || number < -9999)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Data Overflow Error--");
                  System.out.println();
                  error = true;
               }
               else
               {
                  ram[operand] = number;
               }
               break;
            }
            
            case 32: // Write
            {
               System.out.println();
               System.out.println("\t\t\t\t\t\tWrite: " + ram[operand]);
               System.out.println();
               System.out.println();
               break;
            }
            
            case 31: // Load
            {
               accumulator = ram[operand];
               break;
            }
            
            case 30: // Store
            {
               ram[operand] = accumulator;
               break;
            }
            
            case 21: // Add
            {
               accumulator = accumulator + ram[operand];
               
               if(accumulator > accMax)
               {
               System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
               System.out.println("\t\t\t\t\t\t--Accumulator Overloaded--");
               System.out.println();
               error = true;
               }
               break;
            }
            
            case 20: // Subtract
            {
               accumulator = accumulator - ram[operand];
               
               if(accumulator < accMin)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Accumulator UnderLoaded--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            case 11: // Divide
            {
               if(operand == 0)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Attempt to divide by zero--");
                  System.out.println();
                  error = true;
               }
               
               accumulator = accumulator / ram[operand];
               
               if(accumulator < accMin)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Accumulator UnderLoaded--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 29: // LoadImm
            {
               accumulator = operand;
               
               if(accumulator < accMin || accumulator > accMax)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Accumulator Error--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 06: // AddImm
            {
               accumulator = accumulator + operand;
               
               if(accumulator > accMax)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Accumulator OverLoaded--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 07: // DecImm
            {
               accumulator = accumulator - operand;
               
               if(accumulator < accMin)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Accumulator UnderLoaded--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 8: // MultImm
            {
               accumulator = accumulator * operand;
               
               if(accumulator > accMax)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Accumulator OverLoaded--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 9: // DivideImm
            {
               if(operand == 0)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Attempt to divide by zero--");
                  System.out.println();
                  error = true;
               }
               
               accumulator = accumulator / operand;
               
               if(accumulator < accMin)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Accumulator UnderLoaded--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 10: // Multiply
            {
               accumulator = accumulator * ram[operand];
               
               if(accumulator > accMax)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Accumulator OverLoaded--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 43: // Branch
            {
               programCounter = operand;
               if(programCounter < 0 || programCounter > 99)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Branch Memory Error--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 42: // Branch Negative
            {
               if(accumulator < 0)
                  programCounter = operand;
               if(programCounter < 0 || programCounter > 99)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Branch memory error--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 41: // Branch Positive
            {
               if(accumulator > 0)
                  programCounter = operand;
               if(programCounter < 0 || programCounter > 99)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Branch memory error--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 40: // Branch zero
            {
               if(accumulator == 0)
                  programCounter = operand;
               if(programCounter < 0 || programCounter > 99)
               {
                  System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
                  System.out.println("\t\t\t\t\t\t--Branch memory error--");
                  System.out.println();
                  error = true;
               }
               break;
            }
            
            case 25: // Increment
            {
               ram[operand]++;
               break;
            }
            
            case 26: // Decrement
            {
               ram[operand]--;
               break;
            }

            case 50: // Halt
            {
               System.out.println("\t\t\t\t\t\t--Basic-Computer execution terminated--");
               System.out.println();
               break;
            }
            default:
            {
               error = true;
               System.out.println("--Basic-Computer execution abnormally terminated with the fatal error--" );
               System.out.println("\t\t\t\t\t\t--Instruction " + opCode + " is not supported--");
               System.out.println();
            }
         }
      }
      
      /**
      LoadRam recieves a file from the keyboard and sets it equal to the variable fileName
      It takes the contents and stores them into the array.
      */
      public static void LoadRam() throws IOException
      {
         Scanner keyboard = new Scanner(System.in);
         
         System.out.print("Enter the name of a file: ");
         fileName = keyboard.nextLine();
         System.out.println();
         
         File file = new File(fileName);
         Scanner inputFile = new Scanner(file);
         
         int counter = 0;
         
         while(inputFile.hasNext())
         {
            ram[counter] = inputFile.nextInt();
            counter++;
         }
         inputFile.close();
      }
      
      /**
      Dump displays the registers and stored info.
      */
      public static void dump() 
      {
         System.out.println("Accumulator: " + accumulator + " ");
         System.out.println("Counter: " + programCounter + " ");
         System.out.println("Instruction Register: " + instructionRegister + " ");
         System.out.println("opCode: " + opCode + " ");
         System.out.println("Operand: " + operand + " ");
         System.out.println("error(t/f): " + error + " ");
         System.out.println();
      }
      
      /**
      memoryDump prints the contents of memory 
      */
      public static void memoryDump()
      {
      System.out.println(" \t\t\t0 \t\t 1 \t  2 \t\t3 \t\t 4 \t  5 \t\t6 \t\t 7 \t  8 \t\t9");
         for(int i = 0; i < ram.length; i++)
         {
           if(i % 10 == 0)
            {
               System.out.println("");
               System.out.printf("%02d \t", i);
            }
            System.out.printf("+%05d ", ram[i]);
         }
         System.out.println();
      }
   }