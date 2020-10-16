package cy.ucy.cs.Team6.HW5;
/**
 * In this class we accomplished to build an intelligent system that can learn to answer questions like the ones in TOEFL exams. In
 *	order to do that, the system will approximate the semantic similarity of any pair of words. The semantic
 *	similarity between two words is the measure of the closeness of their meanings.We measured the semantic similarity of pairs of words by first computing a semantic descriptor
 *	vector of each of the words, and then took the similarity measure as the cosine similarity between
 *	the two vectors. All methods that are needed for calculations are implemented below.
 *
 *@authors Marcos Charalambous & Sotiris Loizidis
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class TOEFL {
	/**
	 * function 1
	 * This function uses the StringTokenizer to first split the sentences each time the correct character appears
	 * and the split the sentence in words each time the correct character appears. The words are put in an arrayList
	 * of Strings and those arrayLists are put into the arrayList of the arrayLists
	 * @param text The text we are going to tokenize.
	 * @return Returns the completed arrayList
	 */
	 public static ArrayList<ArrayList<String>> getSentenceLists(String text) {
	 text = text.toLowerCase();
	 String x = "'-,;: ";
	 x += '"';
	 StringTokenizer Sentence = new StringTokenizer(text, ".?!");
	 ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
	 while (Sentence.hasMoreTokens()) {
	 ArrayList<String> temp = new ArrayList<String>();
	 StringTokenizer word = new StringTokenizer(Sentence.nextToken(), x);
	 while (word.hasMoreTokens()) {
	 temp.add(word.nextToken());
	 }
	 ans.add(temp);
	 }
	 return ans;
	 }
//	public static ArrayList<ArrayList<String>> getSentenceLists(String text) {
//		text = text.toLowerCase();
//		text = text.replaceAll("'-,;: ", " ");
//		text = text.replace('"', ' ');
//		StringTokenizer Sentence = new StringTokenizer(text, ".?!");
//		ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
//		while (Sentence.hasMoreTokens()) {
//			ArrayList<String> temp = new ArrayList<String>();
//			temp.add(Sentence.nextToken());
//			ans.add(temp);
//		}
//		return ans;
//	}

	/**
	 * function 2
	 * We take the arrayList of file names and run through the files. For each file in the try-catch block we take each newL and append it to the stringbuilder
	 * and then use the first function to put the string as an element of the slff arrayList.
	 *  
	 * @param filenames an arrayList which contains the names of the filenames we are going to tokenize to arrayLists
	 * @return
	 **/
	public static ArrayList<ArrayList<String>> get_sentence_lists_from_files(ArrayList<String> filenames) {
		ArrayList<ArrayList<String>> slff = new ArrayList<ArrayList<String>>();
		BufferedReader reader = null;
		for (String st : filenames) {
			try {
				File file = new File(st);
				reader = new BufferedReader(new FileReader(file));
				StringBuilder s = new StringBuilder("");
				String j;
				while ((j = reader.readLine()) != null) {
					s.append(j+" ");
				}
				slff = (getSentenceLists(s.toString()));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return slff;
	}

	/**
	 * function 3 We visualize the arrayList of arrayLists as the text of sentences.
	 * Each arrayList of Strings in the bigger arrayList(text) is the sentence. In
	 * the sentence we have words(Strings) In the first for-each loop we make an
	 * arrayList which will contain all the words in the text only one time, so we
	 * can make the hashmap only for once per word.
	 * 
	 * In the next sequence of loops the external for-each is used to cycle through
	 * each different word to be made into a hashmap. The next for-each is to cycle
	 * through the sentences of the text. If any given sentence contains the word
	 * under-check, we proceed to run through the whole sentence and add to the
	 * smaller hashmap the words and their count. if the word doen't exist in the
	 * hashmap we add it and set it's value to 1 or we add it once again increasing
	 * the value. When the for-each loop which goes through the sentences
	 * finishes(we sacnned the whole text for that word) then we add the hashmap as
	 * the value of the bigger hashmap and the world under-check as the key. We then
	 * refresh the smaller hashmap.
	 * 
	 * @param sentences
	 *            An arrayList which contains arrayLists of Strings
	 * @return
	 */
	public static HashMap<String, HashMap<String, Integer>> build_semantic_descriptors(
			ArrayList<ArrayList<String>> sentences) {
		HashMap<String, HashMap<String, Integer>> semantic = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> word = new HashMap<String, Integer>();
		ArrayList<String> scan = new ArrayList<String>();
		for (ArrayList<String> words : sentences) {
			for (String single : words) {
				if (!scan.contains(single)) {
					scan.add(single);
				}
			}
		}
		for (String wUnderCheck : scan) {
			for (ArrayList<String> sentence : sentences) {
				if (sentence.contains((wUnderCheck))) {
					for (String x : sentence) {
						if (!x.equals(wUnderCheck)) {
							if (!word.containsKey(x))
								word.put(x, 1);
							else
								word.put(x, word.get(x) + 1);
						}
					}
				}
			}
			semantic.put(wUnderCheck, word);
			word = new HashMap<String, Integer>();
		}
		return semantic;
	}
	/**
	 * Use of the pseudo-code to calculate the cosine-similarity
	 * @param vec1 The vector 1 
	 * @param vec2 The vector 2
	 * @return
	 */
	public static double cosine_similarity(HashMap<String, Integer> vec1, HashMap<String, Integer> vec2) {
		if (vec1 == null || vec2 == null || vec1.isEmpty() || vec2.isEmpty()) {
			return -1;
		}
		double dot_product = 0.0;
		for (String w : vec1.keySet()) {
			if (vec2.containsKey(w)) {
				dot_product += vec1.get(w) * vec2.get(w);
			}
		}
		return dot_product / (norm(vec1) * norm(vec2));
	}
	/**
	 * Use of the pseudo-code in order to use it in the cosine_similarity
	 * @param vec 
	 * @return return the value to cosine_similarity
	 */
	public static double norm(HashMap<String, Integer> vec) {
		double sum_of_squares = 0.0;
		for (String w : vec.keySet()) {
			sum_of_squares += vec.get(w) * vec.get(w);
		}
		return (Math.sqrt(sum_of_squares));
	}
	/**
	 * This method uses the cosine_similarity method in order to calculate which is the most similar word
	 * We create an array equal to the size of the array of choices we are given and check if the word is in our "dictionary"
	 * (semantic_descriptors). If not we give the value -1 making it completely opposite meaning, if it exists then we calculate it's similarity and put the value in the table
	 * We make check through the table and return the first word with the higher value of similarity.
	 * @param word
	 * @param choices
	 * @param semantic_descriptors
	 * @return
	 */
	public static String most_similar_word(String word, String[] choices,
			HashMap<String, HashMap<String, Integer>> semantic_descriptors) {
		double[] same = new double[choices.length];
		for (int i = 0; i < same.length; i++) {
			if (semantic_descriptors.containsKey(word)) {
				same[i] = cosine_similarity(semantic_descriptors.get(choices[i]), semantic_descriptors.get(word));
			} else
				same[i] = -1;
		}
		double max = same[0];
		String bc = choices[0];
		for (int i = 1; i < same.length; i++) {
			if (same[i] > max) {
				max = same[i];
				bc = choices[i];
			}
		}
		return bc;
	}
	/**
	 * This method uses the cosine_similarity method in order to calculate which is the most similar word
	 * We create an array equal to the size of the array of choices we are given and check if the word is in our "dictionary"
	 * (semantic_descriptors). If not we give the value -1 making it completely opposite meaning, if it exists then we calculate it's similarity and put the value in the table
	 * We make check through the table and return the first word with the higher value of similarity.
	 * @param word
	 * @param choices
	 * @param semantic_descriptors
	 * @return
	 */
	public static String run_similarity_test(String filename,
			HashMap<String, HashMap<String, Integer>> semantic_descriptors) {
		String out = "";
		BufferedReader reader = null;
		try {
			File file = new File(filename);
			reader = new BufferedReader(new FileReader(file));
			String[] s;
			String temps[] = new String[2];
			String sim;
			String j = "";
			int countt = 0;
			int countf = 0;
			while ((j = reader.readLine()) != null) {
				s = new String[4];
				s = j.split(" ");
				temps[0] = s[2];
				temps[1] = s[3];
				sim = most_similar_word(s[0], temps, semantic_descriptors);
				if (!sim.equals(s[1])) {
					out += sim + ": false " + "\n";
					countf++;
				} else {
					out += sim + ": true " + "\n";
					countt++;
				}
			}
			out += countt + " true" + "\n";
			out += countf + " false" + "\n";
			int percent = countt * 100 / (countt + countf);
			out += "success rate: " + percent + "%";
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out;
	}
	/**
	 * This function uses a try catch block using the buffer to save the result in text file.
	 * @param onoma The name of the text file that will be created
	 * @param table The name of the string that is going to be placed in the text file
	 */
	public static void Save(String onoma, String table) {
		try {
			File file = new File("out-" + onoma);// DIMIOURGIA OUTPUT FILE.
			FileWriter fw = new FileWriter(file);
			BufferedWriter buffer = new BufferedWriter(fw);
			buffer.write(table);// EISAGWGI DEDOMENON STO ARXEIO.
			buffer.close();
			fw.close();
		} catch (IOException e) {// SE PERIPTOSI SFALMATOS,TIPOSI EXCEPTION.
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String s = "";
		ArrayList<String> filenames = new ArrayList<String>();
		ArrayList<ArrayList<String>> files = new ArrayList<ArrayList<String>>();
		for (int i=0; i<args.length-1; i++){
		filenames.add(args[i]);
		}
		files = get_sentence_lists_from_files(filenames);
		HashMap<String, HashMap<String, Integer>> test = new HashMap<String, HashMap<String, Integer>>();
		test = build_semantic_descriptors(files);
		s = run_similarity_test(args[args.length-1], test);
		Save("text", s);
	}
}