function playMusic() {
	//check to see if we can play music:
	//music is false (true if it is) %% enabled is false (true if it is)
	if (!$Pref::Rendermen::Music && !$Pref::Rendermen::Enabled) {//that worked
		$MUSIC_LOOP = schedule(25000,0,playMusic);
		return;
	}
	//play one set of music for one minute, check the renderman thing, and play another song
	//if ($rendermeninmap >= 6 || $harmlessPrepperCount >= 12) {
    		//3
       	// 	%number = getRandom(1, 2);
        //	if (%number == 1) {
    	//		serverPlay2D(M31);
    	//	}  else {	
    	//		serverPlay2D(M32);
    	//	}
    	//} else if ($rendermeninmap >= 1 || $harmlessPrepperCount >= 6) {
    		//2
       	 //	%number = getRandom(1, 3);
       	// 	if (%number == 1) {
    	//		serverPlay2D(M21);
    	//	} else if (%number == 2) {
    	//		serverPlay2D(M22);
    	//	} else {	
    	//		serverPlay2D(M23);
    	//	}
   	//} else {
       	// 	//1
       	// 	%number = getRandom(1, 3);
       	// 	if (%number == 1) {
        //		serverPlay2D(M11);
    	//	} else if (%number == 2) {
    	//		serverPlay2D(M12);
    	//	} else  {
    	//		serverPlay2D(M13);
    	//	}
    	//}*/
    	
    	if (%rendermeninmap <= 1 || $harmlessPrepperCount <= 3) {
    		//threat level 1
       	 	%number = getRandom(1, 3);
       	 	if (%number == 1) {
    			serverPlay2D(M11);
    		} else if (%number == 2) {
    			serverPlay2D(M12);
    		} else  {
    			serverPlay2D(M13);
    		}
    	} else if ($rendermeninmap <= 2 || $harmlessPrepperCount <= 6) {
    		//threat level 2
       	 	%number = getRandom(1, 3);
       	 	if (%number == 1) {
    			serverPlay2D(M21);
    		} else if (%number == 2) {
    			serverPlay2D(M22);
    		} else {	
    			serverPlay2D(M23);
    		}
    	} else {
       	 	%number = getRandom(1, 2);
       	 	if (%number == 1) {
    			serverPlay2D(M31);
    		}  else {	
    			serverPlay2D(M32);
    		}
    	}
    	
    	
	$MUSIC_LOOP = schedule(60000,0,playMusic);
}

playmusic();
