### Automatically Solving TOEFL Synonym Questions with Computational Linguistics

One type of question faced by TOEFL test takers is the "Synonym Question", where students are asked to select the synonym of a word from a list of options.

For example:
1. vexed  (Answer: (a) annoyed)
* a. annoyed
* b. amused
* c. frightened
* d. excited
  
In TOEFL.java I designed an intelligent system that learns to answer such questions. The learning / training of the system will be done by processing a large body of English text (ie without the use of dictionaries).

To do this, the system will use the semantic similarity of any word pair. The semantic similarity between two words is the measure of the closeness of their meanings.

To answer the TOEFL question, the system must calculate the semantic similarity between the word given to you and all possible answers and select the answer with the highest semantic similarity to the given word.
