# Work Log

## Rachel Gao

### 5/22/24

researching more on how to implement images into SSTV audio, found a few documents about different types of sstv (some easier than others)

### 5/23/24

We decided to switch our topic to coding the md5 algorithm. I looked for a few resources and websites that explained each step of md5 and also started a skeleton for the code.

### 5/24/24

We worked together to start writing the code. We finished the first step of turning the string input into binary ascii and making a print function to check the code, which works. We started working on the padding step at the end of class.

### 5/24/24
I continued working on padding the input and finished the function but binary string representation shows integers padded with 1s in front which I'm not sure is okay (probably because byte is limited to -128 to 127). Added some code to the encode too. 

### 5/26/24
I realized that there was a flaw with padding and that it didn't account for inputs that were larger. I changed the function to return a 2D byte array instead and accounted for all the scenarios of sizes of inputs. The padding function works as intended and I tested it with different sizes. I also removed some code for conciseness.

### 5/28/24
We worked together in class to implement spliting the 512 bit blocks into a 2D int array and solved the problem with bytes having extra padded 1s for negative numbers. 

### 5/29/24
We worked together in class and continued making the first step of modular addition with the f function and using the words but there was an error with the addition. At home, I continued to work on the function by adding K and S steps but encountered an error.

### 5/30/24
We finished function f by fixing the shift step and changing ABCD to an array so that the values could be changed outside of the function.

### 5/31/24
We finished the G function but not sure if it works because the website we were following doesn't have the results. We plan to complete all the steps for just one block and check it with an online md5er or terminal.

### 6/3/24
We worked together in class to finish up the md5 function but there was an error and the md5 output is not the same from websites. We checked f and the f function should work.

### 6/5/24
We worked together to try and figure out what is wrong with our md5 function. We went back to looking for other websites that have the intermediary values of the md5. We changed K to be computed through a formula instead.

### 6/6/24
I tried to find out what was going wrong with the code but was unsuccessful. I used online calculators to manually calculate values but they gave different answers than the python program so I'm really confused now. Fixed some code in some areas with misconceptions.

## Nathaniel Ciu

### 2024/05/22
Figuring out if we want to stick with audio steganography, specifically SSTV (need to figure out how to do audio "processing" in java) or replicate md5 (don't need to figure out audio processing stuff, and might be more well documented?). Indecision and lack of drive for ethier. 

### 2024/05/23
Switched to replicating MD5 because we couldn't find great documentation to debugg SSTV. Found resources to help with replciating MD5, we also have a [google doc](https://docs.google.com/document/d/1L5EoYW-sUBFakbkqqNC-GjzaK4cZB_PYZ6wepM9pxTg/edit?usp=sharing) if you want to check that out? 

### 2024/05/24
Coded a function with Rachel to change strings into their binary equivalent for debugging. Kinda started a padding function for step 1 of md5.

### 2024/05/26
Worked on pad function

### 2024/05/27
Went on what rachel (correctly) called "a wild goose chase" because I made an error on a print statement. Pain is an intimate friend. 

### 2024/05/28
Worked on splitting the blocks into words. Wrote initialzation factors in.

### 2024/05/29
Worked on first step on MD5 main algorithim. Went on another goose chase because of an error message from parseInt. I love java. 

### 2024/05/30
Finished the functionF and started to move on to the next step 

### 2024/05/31
Changed how the functionF function works in order to use it for the whole md5 main alogrithm

### 2024/06/01 - 06/02
No work done due to trip and was working on a different project 4 a different class.

### 2024/06/03
Debugging MD5 main alogrithim b/c something went wrong 

### 2024/06/05
Continuing to try and fix our md5 problem

### 2024/06/06
Figured out what was going on in our python reference and will now use it to debug our md5 replicator.