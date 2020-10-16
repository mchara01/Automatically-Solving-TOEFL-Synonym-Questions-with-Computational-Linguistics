## Automatically Solving TOEFL Synonym Questions with Computational Linguistics

### Description
One type of question faced by TOEFL test takers is the "Synonym Question", where students are asked to select the synonym of a word from a list of options.

For example:

Q1. vexed  (Answer: (a) annoyed)
* a. annoyed
* b. amused
* c. frightened
* d. excited
  
In TOEFL.java I designed an intelligent system that learns to answer such questions. The learning / training of the system will be done by processing a large body of English text (ie without the use of dictionaries).

To do this, the system will use the semantic similarity of any word pair. The semantic similarity between two words is the measure of the closeness of their meanings.

To answer the TOEFL question, the system must calculate the semantic similarity between the word given to you and all possible answers and select the answer with the highest semantic similarity to the given word.

### Usage
Compile:

`javac -classpath .:stdlib.jar TOEFL.java`

Execute:

`java -classpath .:stdlib.jar TOEFL <list_of_training_files> <TOEFL_test_file>`

Argument <TOEFL_test_file> is a file containing a list of TOEFL question and there respective list of synonyms to choose from. The program will try to answer these questions for you after it has been trained with the <list_of_training_files>, given also as arguments to the program. For a test file you can use the TestFile.txt provided and for training the files in the Training folder can be used. The output of the program can be found at out-text. Bear in mind that the execution time depends on the number of files given for training and there size.
