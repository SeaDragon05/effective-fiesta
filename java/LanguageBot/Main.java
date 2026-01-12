/*
gen 1:
   ability to create sentences baised on what is commonly used after each word

gen 2:
   text to speech
   allocation of words that begin sentences after the chars . ! ?
   can choose from this list to add more chaos
   added a filter to prevent unwanted characters from becoming seperate words (such as - )
   added 2 or 3 word modification, improved results
   results are more readable due to this

gen 3: 
   improved grammar by comparing information as the sentence is generated.
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
public class Main {
   static Scanner scnrin = new Scanner(System.in);// user input through console
   static File manifest = new File("manifest.txt");//contains the list of all word files
   static String tweets   = "tweets.txt"  ;//contains sentences for the ai to learn from
   public static void main(String[] args) {
      final long startTime = System.currentTimeMillis();
      //Say("Compiling data. Stage 1 out of 3");
      //test_tweet_single_line(2);//scan da friken doc
      //Say("Compiling data. Stage 2 out of 3");
      //test_tweet_double_line();
      //Say("Compiling data. Stage 3 out of 3");
      //test_tweet_tripple_line();
      final long endTime = System.currentTimeMillis();
   
      Say("Total execution time: " + ((endTime - startTime) / 1000) + "seconds");
      //ask user fir a word to begin a sentence
      //search that word, and pick an after.
      //add that word, and search that word for an after
      //repeat 10 times.
      //Say("Enter a word to generate a sentence: ");
      //String word = scnrin.nextLine();
      //Say("Auto generation enable?");
      //String response = scnrin.nextLine();
      //if (response.contains("y")) {
         while (true) {
            //generate_random();
            generate_random();
         }
      //} else {
      //   while (true) {
       //     Say("Enter a word to generate a sentence: ");
        //    String word = scnrin.nextLine();
       //     generate(word);
      //   }
      //}
   }
   public static void generate(String word) {
      String sentence = word;
      Random rand = new Random(15);
      File file = new File("data/" + word + ".txt");
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
         int lineCount2 = file_line_count(file2);
         if (lineCount2 == 0) {
            lineCount2 = 1;
         }
         int randNum2 = rand2.nextInt(Math.abs(lineCount2));// so that it don't break
         newWord = get_word(file2, randNum2, false);
         sentence += " " + newWord;
         if (sentence.contains(".")) {
            break;
         }
      }  
      //System.out.println(sentence); 
      Say(sentence);
   }
   public static void generate_random() {
   
      File file = new File("manifest.txt");
      String word = get_random_word(file);
      
      String sentence = "";
      Random rand = new Random();
      int lineCount = file_line_count(file);
      if (lineCount == 0) {
         lineCount = 1;
      }
      int randNum = rand.nextInt(lineCount);// so that it don't break
      String newWord = get_word(file, randNum, false);
      sentence += newWord;
      int three = 0;
      String word_1 = "";
      String word_2 = "";
      for (int i = 0; i < 400; i++) {//create sentence
         Random rand2 = new Random();
         File file2 = new File("data/" + newWord + ".txt");
         int lineCount2 = file_line_count(file2);
         if (lineCount2 == 0) {
            lineCount2 = 1;
         }
         int randNum2 = rand2.nextInt(Math.abs(lineCount2));// so that it don't break
         newWord = get_word(file2, randNum2, false);
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
      Say(data);
   }
   public static void generate_random_while_gen() {
   
      File file = new File("manifest.txt");
      String word = get_random_word(file);
      
      String sentence = "";
      Random rand = new Random();
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
         int lineCount2 = file_line_count(file2);
         if (lineCount2 == 0) {
            lineCount2 = 1;
         }
         int randNum2 = rand2.nextInt(Math.abs(lineCount2));// so that it don't break
         newWord = get_word(file2, randNum2, false);
         Say(" " + newWord);
         if (newWord == ""){
            break;}
         sentence += " " + newWord;
         if (sentence.contains(".") || sentence.contains("?")) {
            System.out.println();
            break;
         }
      }  
      //System.out.println(sentence); 
      
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
   public static void Say(String thing) {
      Random rnd = new Random();
      System.out.println(thing);
      try { 
            // Set property as Kevin Dictionary 
         System.setProperty("freetts.voices", 
               "com.sun.speech.freetts.en.us." 
                  + "cmu_us_kal.KevinVoiceDirectory"); 
         
            // Register Engine 
         Central.registerEngineCentral( 
                "com.sun.speech.freetts"
                + ".jsapi.FreeTTSEngineCentral"); 
         
            // Create a Synthesizer 
         Synthesizer synthesizer 
                = Central.createSynthesizer( 
                    new SynthesizerModeDesc(Locale.US)); 
            // Allocate synthesizer 
         synthesizer.allocate(); 
            // Resume Synthesizer 
         synthesizer.resume(); 
      
            // Speaks the given text 
            // until the queue is empty. 
         synthesizer.speakPlainText( 
                thing, null); 
         synthesizer.waitEngineState( 
                Synthesizer.QUEUE_EMPTY); 
      
            // Deallocate the Synthesizer. 
      } 
      
      catch (Exception e) { 
         e.printStackTrace(); 
      } 
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
   public static void test_tweet_single_line(int amount) {//
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
            if (last_word.contains(".")) {
               word = scnr2.next();
               create_txt(word);
               write_to_file("manifest", word, true);
               next_word = scnr3.next();
               //write_to_file(word, last_word, true);//previous word
               write_to_file(word, next_word, false);//next word
               last_word = word;
            } else {
               word = scnr2.next();
               create_txt(word);
               next_word = scnr3.next();
               //write_to_file(word, last_word, true);//previous word
               write_to_file(word, next_word, false);//next word
               last_word = word;
            }
         }
      } catch (Exception e){}
   }
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
      
         while (scnr1.hasNext()) {
            word_1 = scnr1.next();
            word_2 = scnr1.next();
            word_3 = scnr1.next();
            word_4 = scnr1.next();
         
         
            create_txt(word_1);
            create_txt(word_2);
            create_txt(word_3);
            create_txt(word_4);
         
            write_to_file(word_1, word_2, false);
            write_to_file(word_2, word_3, false);
            write_to_file(word_3, word_4, false);
            write_to_file(word_1, (word_2 + " " + word_3), false);
            write_to_file(word_2, (word_3 + " " + word_4), false);
         
            create_txt((word_1 + " " + word_2));
            create_txt((word_2 + " " + word_3));
            create_txt((word_3 + " " + word_4));
         
            write_to_file((word_1 + " " + word_2), word_3, false);
            write_to_file((word_2 + " " + word_3), word_4, false);
            write_to_file((word_1 + " " + word_2), (word_3 + " " + word_4), false);
         
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
         
            create_txt(word_1);
            create_txt(word_2);
            create_txt(word_3);
            create_txt(word_4);
         
            write_to_file(word_1, word_2, false);
            write_to_file(word_2, word_3, false);
            write_to_file(word_3, word_4, false);
            write_to_file(word_1, (word_2 + " " + word_3), false);
            write_to_file(word_2, (word_3 + " " + word_4), false);
         
            create_txt((word_1 + " " + word_2));
            create_txt((word_2 + " " + word_3));
            create_txt((word_3 + " " + word_4));
         
            write_to_file((word_1 + " " + word_2), word_3, false);
            write_to_file((word_2 + " " + word_3), word_4, false);
            write_to_file((word_1 + " " + word_2), (word_3 + " " + word_4), false);
         }
      } catch (Exception e) {}
   }
   public static boolean check_words(String word_1, String word_2, String word_3) {
      try {
         File file = new File("data/" + word_1 + " " + word_2 + " " + word_3);
         return true;//file was found
      } catch (Exception e) {
         return false;//file does not exist, not a possible word combo
      }
   }
   public static void test_tweet_tripple_line() {//im not doing quads, even if that had four words
      try {
      
         File file = new File("copypasta.txt");
         Scanner scnr1 = new Scanner(file);
         Scanner scnr2 = new Scanner(file);
      
         scnr1.nextLine();
         scnr2.nextLine();
         
         while (scnr1.hasNext()) {
            String word_1 = scnr1.next();
            String word_2 = scnr1.next();
            String word_3 = scnr1.next();
            String word_4 = scnr1.next();
            String word_5 = scnr1.next();
            String word_6 = scnr1.next();
            
            tress(word_1, word_2, word_3, word_4, word_5, word_6);
            
            scnr2.next();
           // scnr2.next();
           // scnr2.next();
            
            word_1 = scnr2.next();
            word_2 = scnr2.next();
            word_3 = scnr2.next();
            word_4 = scnr2.next();
            word_5 = scnr2.next();
            word_6 = scnr2.next();
            
            
            tress(word_1, word_2, word_3, word_4, word_5, word_6);
            
         }
      } catch(Exception e){}
   }     
   //methos utilities:
   public static void create_txt(String file_name) {//input should not have .txt at the end. that is added
      try {
         File myObj = new File("data/" + file_name + ".txt");
         if (myObj.createNewFile()) {
         //   System.out.println("File created: " + myObj.getName());
         } else {
         //   System.out.println("File already exists: " + file_name + ".txt");
         }
      } catch (IOException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }
   public static void write_to_file(String file_name, String word, boolean manifest) {
      if (word.contains("-")) {
         return;//filter for crap
      }
      try {
         File file;
         if (!manifest) {
            file = new File("data/" + file_name + ".txt");
         } else {
            file = new File(file_name + ".txt");
         }
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
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }
   public static void tress(String word_1, String word_2, String word_3, String word_4, String word_5, String word_6) {
      //this took forever
      create_txt(word_1);
      create_txt(word_2);
      create_txt(word_3);
      create_txt(word_4);
      create_txt(word_5);
      //create_txt(word_6);//this is worthless btw. dont enable it or it will create unwanted files
         //word_6 does not get any information stored in it
      create_txt((word_1 + " " + word_2));
      create_txt((word_2 + " " + word_3));
      create_txt((word_3 + " " + word_4));
      create_txt((word_4 + " " + word_5));
      //create_txt((word_5 + " " + word_6));
            
      create_txt((word_1 + " " + word_2 + " " + word_3));
      create_txt((word_2 + " " + word_3 + " " + word_4));
      create_txt((word_3 + " " + word_4 + " " + word_5));
      //create_txt((word_4 + " " + word_5 + " " + word_6));
      
      
      write_to_file(word_1, word_2, false);
      write_to_file(word_2, word_3, false);
      write_to_file(word_3, word_4, false);
      write_to_file(word_4, word_5, false);
      write_to_file(word_5, word_6, false);
            
      write_to_file(word_1, (word_2 + " " + word_3), false);
      write_to_file(word_2, (word_3 + " " + word_4), false);
      write_to_file(word_3, (word_4 + " " + word_5), false);
      write_to_file(word_4, (word_5 + " " + word_6), false);
            
      write_to_file(word_1, (word_2 + " " + word_3 + " " + word_4), false);
      write_to_file(word_2, (word_3 + " " + word_4 + " " + word_5), false);
      write_to_file(word_3, (word_4 + " " + word_5 + " " + word_6), false);
            
      write_to_file((word_1 + " " + word_2), word_3, false);
      write_to_file((word_2 + " " + word_3), word_4, false);
      write_to_file((word_3 + " " + word_4), word_5, false);
      write_to_file((word_4 + " " + word_5), word_6, false);
            
      write_to_file((word_1 + " " + word_2), (word_3 + " " + word_4), false);
      write_to_file((word_2 + " " + word_3), (word_4 + " " + word_5), false);
      write_to_file((word_3 + " " + word_4), (word_5 + " " + word_6), false);
            
      write_to_file((word_1 + " " + word_2), (word_3 + " " + word_4 + " " + word_5), false);
      write_to_file((word_2 + " " + word_3), (word_4 + " " + word_5 + " " + word_6), false);
            
      write_to_file((word_1 + " " + word_2 + " " + word_3), word_4, false);
      write_to_file((word_2 + " " + word_3 + " " + word_4), word_5, false);
      write_to_file((word_3 + " " + word_4 + " " + word_5), word_6, false);
            
      write_to_file((word_1 + " " + word_2 + " " + word_3), (word_4 + " " + word_5), false);
      write_to_file((word_2 + " " + word_3 + " " + word_4), (word_5 + " " + word_6), false);
            
      write_to_file((word_1 + " " + word_2 + " " + word_3), (word_4 + " " + word_5 + " " + word_6), false);
      
      return;
   }
}