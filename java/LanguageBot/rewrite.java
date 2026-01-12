/*
gen 1:
   ability to create sentences baised on what is commonly used after each word

gen 2:
   text to speech
   allocation of words that begin sentences after the chars . ! ?
   can choose from this list to add more chaos
   added a filter to prevent unwanted characters from becoming seperate words (such as - )
   added 2 or 3 word modification, improved results
   results are more readable due to this. The more data it has, the better the results.

gen 3: 
   improved grammar by comparing information as the sentence is generated.
      stores word information before it was used to better determine if that word should be used
   further improvments added by researching the generated sentence for mistakes that are not in the data
      this will remove or add parts of the sentence, or even rewrite the sentence.
   
current generation: 3
*/
import java.util.*;
import javax.speech.Engine;
import java.util.Locale; 
import javax.speech.Central; 
import javax.speech.synthesis.Synthesizer; 
import javax.speech.synthesis.SynthesizerModeDesc; 
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
public class rewrite {
   public static void main(String[] args) {
      
      test_tweet_single_line();
      //test_tweet_double_line();
      //generate_random();
   }
   public static void compile() {
      final long startTime = System.currentTimeMillis();
      System.out.println("Compiling data. Please wait...");
      test_tweet_single_line();
      test_tweet_double_line();
      final long endTime = System.currentTimeMillis();
      System.out.println("Total time: " + ((endTime - startTime) / 1000) + " seconds");
   }







   public static void test_tweet_single_line() {//
      try {
         File file = new File("copypasta.txt");
         String sentence;
         Scanner scnr1;
         Scanner scnr2;
         Scanner scnr3;
         scnr1 = new Scanner(file);//previous
         scnr2 = new Scanner(file);//current
         scnr3 = new Scanner(file);//next
         sentence = scnr2.nextLine();
         scnr1.nextLine();
         scnr3.nextLine();//move all scanners
         //find first word
         //create a file and add the next word
         //find second word
         //create a file and add previous word and next word
         //repeat
         scnr3.next();//pull ahead
         String word = scnr2.next();
         create_txt(word);
         String next_word = scnr3.next();
         write_to_file(word, next_word, false);//next word
         File manifest = new File("manifest.txt");
         Scanner bword = new Scanner(manifest);
         String last_word = word;
         while (scnr2.hasNext()) {//this will scan the entire document for some reason. but it good. saved me time
            word = scnr2.next();
            create_txt(("data/" + word));
            create_txt(("data/20/" + word));
            if (last_word.contains(".")) {
               write_to_file("manifest", word, true);
            }
            next_word = scnr3.next();
            write_to_file(("data/20/" + word), last_word, true);//store previous word
            write_to_file(("data/" + word), next_word, false);//store next word
            last_word = word;
         }
      } catch (Exception e){
         System.out.print("e");
      }
   }
   //create double line and triple line
   public static void test_tweet_double_line() {//
      try {
         File file = new File("copypasta.txt");
         Scanner scnr1 = new Scanner(file);
         Scanner scnr2 = new Scanner(file);
         Scanner scnr3 = new Scanner(file);
      
         scnr1.nextLine();
         scnr2.nextLine();
         scnr3.nextLine();
      
         String word_1 = "";
         String word_2 = "";
         String word_3 = "";
         String word_4 = "";
         String pre__1 = "";
         String pre__2 = "";
      
         while (scnr1.hasNext()) {
            word_1 = scnr1.next();
            word_2 = scnr1.next();
            word_3 = scnr1.next();
            word_4 = scnr1.next();
         
         
            create_txt(("data/" + word_1));
            create_txt(("data/" + word_2));
            create_txt(("data/" + word_3));
            create_txt(("data/" + word_4));
            create_txt(("data/20/" + word_1));
            create_txt(("data/20/" + word_2));
            create_txt(("data/20/" + word_3));
            create_txt(("data/20/" + word_4));
         
            write_to_file("data/" + word_1, word_2, false);
            write_to_file("data/" + word_2, word_3, false);
            write_to_file("data/" + word_3, word_4, false);
            write_to_file("data/" + word_1, (word_2 + " " + word_3), false);
            write_to_file("data/" + word_2, (word_3 + " " + word_4), false);
            //write backwards data
            
            write_to_file("data/20/" + word_4, word_3, false);
            write_to_file("data/20/" + word_3, word_2, false);
            write_to_file("data/20/" + word_2, word_1, false);
            write_to_file("data/20/" + word_4, (word_2 + " " + word_3), false);
            write_to_file("data/20/" + word_3, (word_1 + " " + word_2), false);
            
            
            //create next set of dual data
            create_txt("data/" + (word_1 + " " + word_2));
            create_txt("data/" + (word_2 + " " + word_3));
            create_txt("data/" + (word_3 + " " + word_4));
            //create reverse set of dual data
            create_txt("data/20/" + (word_1 + " " + word_2));
            create_txt("data/20/" + (word_2 + " " + word_3));
            create_txt("data/20/" + (word_3 + " " + word_4));
            //write forward data
            write_to_file("data/" + (word_1 + " " + word_2), word_3, false);
            write_to_file("data/" + (word_2 + " " + word_3), word_4, false);
            write_to_file("data/" + (word_1 + " " + word_2), (word_3 + " " + word_4), false);
            //write backward data
            write_to_file("data/20/" + (word_1 + " " + word_2), pre__2, false);
            write_to_file("data/20/" + (word_2 + " " + word_3), word_1, false);
            write_to_file("data/20/" + (word_1 + " " + word_2), (pre__1 + " " + pre__2), false);//first run though will write down nothing
            write_to_file("data/20/" + (word_3 + " " + word_4), (word_1 + " " + word_2), false);
            
            pre__1 = word_1;
            pre__2 = word_2;
            
            word_1 = "";
            word_2 = "";
            word_3 = "";
            word_4 = "";
         
         
            scnr2.next();
           // scnr2.next();
         
            word_1 = scnr2.next();
            word_2 = scnr2.next();
            word_3 = scnr2.next();
            word_4 = scnr2.next();
         
            create_txt("data/" + word_1);
            create_txt("data/" + word_2);
            create_txt("data/" + word_3);
            create_txt("data/" + word_4);
            
            create_txt("data/20/" + word_1);
            create_txt("data/20/" + word_2);
            create_txt("data/20/" + word_3);
            create_txt("data/20/" + word_4);
            
         
            write_to_file("data/" + word_1, word_2, false);
            write_to_file("data/" + word_2, word_3, false);
            write_to_file("data/" + word_3, word_4, false);
            write_to_file("data/" + word_1, (word_2 + " " + word_3), false);
            write_to_file("data/" + word_2, (word_3 + " " + word_4), false);
            write_to_file("data/20/" + word_1, pre__2, false);
            write_to_file("data/20/" + word_2, word_1, false);
            write_to_file("data/20/" + word_3, word_2, false);
            write_to_file("data/20/" + word_4, word_3, false);
            write_to_file("data/20/" + word_1, (pre__1 + " " + pre__2), false);
            write_to_file("data/20/" + word_2, (pre__2 + " " + word_1), false);
            write_to_file("data/20/" + word_3, (word_1 + " " + word_2), false);
            write_to_file("data/20/" + word_4, (word_2 + " " + word_3), false);
            
            create_txt("data/" + (word_1 + " " + word_2));
            create_txt("data/" + (word_2 + " " + word_3));
            create_txt("data/" + (word_3 + " " + word_4));
            create_txt("data/20" + (word_1 + " " + word_2));
            create_txt("data/20" + (word_2 + " " + word_3));
            create_txt("data/20" + (word_3 + " " + word_4));
            
            write_to_file("data/" + (word_1 + " " + word_2), word_3, false);
            write_to_file("data/" + (word_2 + " " + word_3), word_4, false);
            write_to_file("data/" + (word_1 + " " + word_2), (word_3 + " " + word_4), false);
            write_to_file("data/20/" + (word_1 + " " + word_2), pre__2, false);
            write_to_file("data/20/" + (word_2 + " " + word_3), word_1, false);
            write_to_file("data/20/" + (word_3 + " " + word_4), word_2, false);
            write_to_file("data/20/" + (word_1 + " " + word_2), (pre__1 + " " + pre__2), false);
            write_to_file("data/20/" + (word_2 + " " + word_3), (pre__2 + " " + word_1), false);
            write_to_file("data/20/" + (word_3 + " " + word_4), (word_1 + " " + word_2), false);
            
         }
      } catch (Exception e) {}
   }
   public static int file_line_count(File file) {
      int count = 0;
      try {
         Scanner scnr = new Scanner(file);
         while (scnr.hasNextLine()) {
            count++;
            scnr.nextLine();
         }
      } catch (Exception e) {}
      return count;
   }
   public static void create_txt(String file_name) {//input should not have .txt at the end. that is added
      try {
         File myObj = new File(file_name + ".txt");
      } catch (Exception e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }

   public static void write_to_file(String file_name, String word, boolean manifest) {
      if (word.contains("-") || word.contains("/")) {
         return;//filter for crap
      }
      try {
         File file;
         file = new File(file_name + ".txt");
         String words = "";
         Scanner scnr = new Scanner(file);
         int lineNumber = 1;
         while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            words += line + "\n";
            lineNumber++;
         }
      
         FileWriter myWriter = new FileWriter(file);
         myWriter.write(words + word);
         myWriter.close();
      } catch (IOException e) {
      }
   }




   public static void generate_random() {
      //begin generation by loading the manifest file
      File file = new File("manifest.txt");
      //return a random word to begin sentence
      String word = get_random_word(file);
      //init return string
      String sentence = word;
      Random rand = new Random();
      //return the file cound of manifest
      int lineCount = file_line_count(file);
      if (lineCount == 0) {
         lineCount = 1;
      }
      int randNum = rand.nextInt(lineCount);// so that it don't break
      String newWord = get_word(file, randNum, false);
      sentence += " " + newWord;
   
      
      for (int i = 0; i < 400; i++) {//create sentence
         Random rand2 = new Random();
         File file2 = new File("data/" + newWord + ".txt");
         File file3 = new File("data/20/" + newWord + ".txt");
         int lineCount2 = file_line_count(file2);
         if (lineCount2 == 0) {
            lineCount2 = 1;
         }
         int randNum2 = rand2.nextInt(Math.abs(lineCount2));// so that it don't break
         newWord = get_word(file2, randNum2, false);
         //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         //NEED TO ADD LEARNING AI THAT LOGICALY PICKS WORDS WHILE GENERATING GRAMMATICALY CORRECT SENTENCES
         //AI NEEDS TO STORE ALL WORDS USED WHEN PICKING. LEARN FROM DATA PROVIDED EXCPLICITLY HOW SENTENCES ARE WRITTEN
         //THIS CANNOT BE LEFT AT RANDOM PAST GEN 2. THIS IS GEN 3. AI DOES NOT NEED TO KNOW WHAT ITS EXACTLY TALKING ABOUT
         //
         //sentence += method_name(sentence, file2);
         //return type is string, while method inputs are String data, File current_word_data
         //
         //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         sentence += " " + newWord;
         if (sentence.contains(".")) {
            break;
         }
      }  
      //System.out.println(sentence); 
      //clean up the sentence:
      Scanner input = new Scanner(sentence);
      String data = "";
      while (input.hasNext()) {
         String th = input.next();
         if (!th.contains(".")) {
            data += th + " ";
         } else {
            data += th;
            break;
         }
      }
      System.out.println(data);
   }

   public static String get_word(File file, int lineNum, boolean before) {
      String returnWord = "";
      String line = "";
      try {
         Scanner scnr = new Scanner(file);
         for (int i = 0; i < lineNum; i ++) {
            line = scnr.nextLine();
         }
         returnWord = scnr.nextLine();
      } catch (Exception e){}   
      return returnWord;
   }
   public static String get_random_word(File file) {
      Random rand = new Random(file_line_count(file));
      String returnWord = "";
      String line = "";
      try {
         Scanner scnr = new Scanner(file);
         for (int i = 0; i < rand.nextInt(); i ++) {
            line = scnr.nextLine();
         }
         returnWord = scnr.next();
      } catch (Exception e){}   
      return returnWord;
   }
}