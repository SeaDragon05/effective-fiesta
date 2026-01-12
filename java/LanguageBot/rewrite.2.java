public class th {
   public static void test_tweet_double_line() {
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


   public static void test_tweet_double_line() {
      //open the data file
      File file = new File("copypasta.txt");
      //init scanners for file
      Scanner scnr1 = new Scanner(file);
      Scanner scnr2 = new Scanner(file);
      Scanner scnr3 = new Scanner(file);
      //go to line one
      scnr1.nextLine();
      scnr2.nextLine();
      scnr3.nextLine();
         //init all variables we need
      String word_1 = "";
      String word_2 = "";
      String word_3 = "";
      String word_4 = "";
      String word_1_p = "";
      String word_2_p = "";
      String word_3_p = "";
      String word_4_p = "";
         //word_# is present and is used to define the next word(s)
         //word_#_p is a saved version of word_# to define the previous word(s)
         
         //begin with the first 4 words
      word_1 = scnr1.next();
      word_2 = scnr1.next();
      word_3 = scnr1.next();
      word_4 = scnr1.next();
         //create the data:
      create_txt(("data/" + word_1));//forward data
      create_txt(("data/" + word_2));
      create_txt(("data/" + word_3));
      create_txt(("data/" + word_4));
      create_txt("data/" + (word_1 + " " + word_2));
      create_txt("data/" + (word_2 + " " + word_3));
      create_txt("data/" + (word_3 + " " + word_4));
      create_txt(("data/20/" + word_1));//backwards data
      create_txt(("data/20/" + word_2));
      create_txt(("data/20/" + word_3));
      create_txt(("data/20/" + word_4));
      create_txt("data/20/" + (word_1 + " " + word_2));
      create_txt("data/20/" + (word_2 + " " + word_3));
      create_txt("data/20/" + (word_3 + " " + word_4));//this will fill
      //write to forward data first
      write_to_file(("data/" + word_1), word_2, false);
      write_to_file(("data/" + word_2), word_3, false);
      write_to_file(("data/" + word_3), word_4, false);
      write_to_file(("data/" + word_1), (word_2 + " " + word_3), false);//write double data bridge
      write_to_file(("data/" + word_2), (word_3 + " " + word_4), false);
      write_to_file("data/" + (word_1 + " " + word_2), word_3, false);//double data to single data
      write_to_file("data/" + (word_2 + " " + word_3), word_4, false);
      write_to_file("data/" + (word_1 + " " + word_2), (word_3 + " " + word_4), false);//double to double
      //store forward data to previous data:
      word_1_p = word_1;
      word_2_p = word_2;
      word_3_p = word_3;
      word_4_p = word_4;
      //set the scanner to the new location
      scnr1.next();
      scnr1.next();//(x2)
      //begin loop now that we have our previous data
      while (scnr1.hasNext()) {//this may generate some overstepping issues with the data since it may not count the last 1 or 2 words
         //set the data to the new words
         word_1 = scnr1.next();
         word_2 = scnr1.next();
         word_3 = scnr1.next();
         word_4 = scnr1.next();
         //create new files with new data
         create_txt(("data/" + word_1));//forward data
         create_txt(("data/" + word_2));
         create_txt(("data/" + word_3));
         create_txt(("data/" + word_4));
         create_txt("data/" + (word_1 + " " + word_2));
         create_txt("data/" + (word_2 + " " + word_3));
         create_txt("data/" + (word_3 + " " + word_4));
         create_txt(("data/20/" + word_1));//backwards data
         create_txt(("data/20/" + word_2));
         create_txt(("data/20/" + word_3));
         create_txt(("data/20/" + word_4));
         create_txt("data/20/" + (word_1 + " " + word_2));
         create_txt("data/20/" + (word_2 + " " + word_3));
         create_txt("data/20/" + (word_3 + " " + word_4));
         //write to forward data first
         write_to_file(("data/" + word_1), word_2, false);
         write_to_file(("data/" + word_2), word_3, false);
         write_to_file(("data/" + word_3), word_4, false);
         write_to_file(("data/" + word_1), (word_2 + " " + word_3), false);//write double data bridge
         write_to_file(("data/" + word_2), (word_3 + " " + word_4), false);
         write_to_file("data/" + (word_1 + " " + word_2), word_3, false);//double data to single data
         write_to_file("data/" + (word_2 + " " + word_3), word_4, false);
         write_to_file("data/" + (word_1 + " " + word_2), (word_3 + " " + word_4), false);//double to double
         //write to backwards data second
         //word_1:
         write_to_file(("data/20/" + word_1), (word_1_p + " " + word_2_p), false);  
         //word_1 + word_2
         write_to_file(("data/20/" + word_1 + " " + word_2), (word_1_p + " " + word_2_p), false);
         //word_2
         //word_2 + 3
         //3
         //3 + 4
         //4
         word_1_p = word_1;
         word_2_p = word_2;
         word_3_p = word_3;
         word_4_p = word_4;
         //set the scanner to the new location
         scnr1.next();
         scnr1.next();//(x2)
      
      }
   }
   
}







build 3 words from the already built sentence./
check to see if they are possible
if they are:
   check to see if it can be carried with the last two words of the already built sentence
      if they are:
         add it to the sentence
         break;
      if they arent
         restart the code (go to 202)
if they arent
   restart the code (go to 202)
   
   
   
   
      String sentence = "what";
      String word_1;
      String word_2;
      String word_3;
      String word_0;
      
      while (true) {
         for (int i = 0; i < 3; i++) {
            Random rand2 = new Random();
            File file2 = new File("data/" + newWord + ".txt");
            int lineCount2 = file_line_count(file2);
            if (lineCount2 == 0) {
               lineCount2 = 1;
            }
            int randNum2 = rand2.nextInt(Math.abs(lineCount2));// so that it don't break
            if (i == 0) {
               word_1 = get_word(file2, randNum2, false);
            } else if (i == 1) {
               word_2 = get_word(file2, randNum2, false);
            } else {
               word_3 = get_word(file2, randNum2, false);
            }
         }
         if (check_words(word_1, word_2, word_3)) {
         
         }
      }  