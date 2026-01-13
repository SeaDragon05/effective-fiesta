//the fuck is this spacing?


// FUNCTIONS //
// cansee function courtesy of Jimmg - thanks
function Player::canSeeObject(%player,%object)
{
	if(!isObject(%object))
	{
		//announce("Object doesn't exist!");
		return 0;
	}
    
    	if(%player == %object)
	{
		//announce("That's you!");
		return 0;
	}


	//The eye point is the position of the players camera
	%eye = %player.getEyePoint();
	//The position of the object
	//%to = posFromTransform(%object.getTransform());
	%to = %object.getEyePoint();
	// if(vectorDist(%eye,%to) < %range)
	// 	return 0;

	//Cast a raycast to try and collide with the object
	%ray = containerRaycast(%eye,%to,$TypeMasks::StaticShapeObjectType | $TypeMasks::VehicleObjectType | $TypeMasks::PlayerObjectType | $TypeMasks::FxBrickObjectType,%player);
	%col = getWord(%ray,0);
	//announce(%col);
	if(isObject(%col) && %col == %object)
	{
		//The position of the collision
		%pos = posFromRaycast(%ray);

		//Super Maths C skills go B)
		%resultant = vectorSub(%pos,%eye);
		%normal = vectorNormalize(%resultant);

		//Find the yaw from the resultant
		%resultRadians = mAtan(getWord(%normal,1),getWord(%normal,0));
		
		//Find the yaw from the eye vector
		%eyeVec = %player.getEyeVector();
		%eyeRadians = mAtan(getWord(%eyeVec,1),getWord(%eyeVec,0));

		//Convert both of them to degrees for easier understanding
		%resultDegrees = mRadToDeg(%resultRadians);
		%eyeDegrees = mRadToDeg(%eyeRadians);

		//Now the tricky part. In order for the object to be in sight, lets say that the object needs to be within 90 degrees of the players view
		//Change 90 to something smaller if you don't like how wide the view is
		if(%resultDegrees >= %eyeDegrees - 50 && %resultDegrees <= %eyeDegrees + 50) return 1;
	}
	//No object hit, or not the target object, return 0/false
	//announce("No object hit, or not the target object, return 0/false");
	return 0;
}

// Create some basic "Check" functions to save space later
function Player::RenderCheck(%this)
{
	InitContainerRadiusSearch(%this.getPosition(),9999,$TypeMasks::PlayerObjectType);
    	%seen1 = 0;
    	%seen2 = 0;
    	%seen3 = 0;
    	%seen4 = 0;
    	%seen5 = 0;
    	%seen6 = 0;
    	%seen7 = 0;
    	%seen8 = 0;
    	while((%targetObject=containerSearchNext()) !$= 0) {
        	if(%targetObject.isRenderman == 1) {
            		if(%this.canSeeObject(%targetObject)) {
                		if(%seen1 == 0) {
                   			%seen1 = %targetObject;
                		} else if(%seen2 == 0) {
                    			%seen2 = %targetObject;
                		} else if(%seen3 == 0) {
                    			%seen3 = %targetObject;
                		} else if(%seen4 == 0) {
                    			%seen4 = %targetObject;
                		} else if(%seen5 == 0) {
                    			%seen5 = %targetObject;
                		} else if(%seen6 == 0) {                	
                    			%seen6 = %targetObjet;
                		}
            		}
        	}
    	}
    	return %seen1 SPC %seen2 SPC %seen3 SPC %seen4 SPC %seen5;
}

function Player::ShrineCheck(%this)
{
	InitContainerRadiusSearch(%this.getPosition(),20,$TypeMasks::FxBrickObjectType);
    	while((%targetObject=containerSearchNext()) !$= 0) {
        	if(%targetObject.getDatablock().getName() $= "brickRenderSafe" && %targetObject.isRayCasting() == 1) {
            		return 1;
        	}
    	}
    	return 0;
}

function addRenderman(%pos) { // usage: "addRenderman(pos);"
    	if($rendermeninmap >= $Pref::Rendermen::Limit) {
        	announce("ERROR: Too many rendermen exist in the map to add another!");
        	return 0;
    	}
    	%this = new aiplayer(Renderman) {
		datablock = PlayerStandardArmor;
		position = %pos;
	};
	missionCleanup.add(%this);
	hideAllNodes(%this);
    	$rendermeninmap = $rendermeninmap + 1;
    	%type = getRandom(0,10);
    	if(%type <= 6) { // Type A
       	 	%this.type = "A";
    	} else if(%type <= 8) { // Type B
        	%this.type = "B";
    	} else if(%type >= 9) { // Type T
        	%this.type = "T";
    	}
    	%this.unhidenode(chest);
    	%this.unhidenode(pants);
  	%this.unhidenode(LShoe);
    	%this.unhidenode(RShoe);
    	%this.unhidenode(LArm);
   	%this.unhidenode(LHand);
    	%this.unhidenode(RArm);
    	%this.unhidenode(RHand);
   	%this.setnodecolor(chest, "0 0 0 1");
    	%this.setnodecolor(headskin, "0 0 0 1");
    	%this.setnodecolor(pants, "0 0 0 1");
   	%this.setnodecolor(LShoe, "0 0 0 1");
    	%this.setnodecolor(RShoe, "0 0 0 1");
    	%this.setnodecolor(LArm, "0 0 0 1");
   	%this.setnodecolor(LHand, "0 0 0 1");
    	%this.setnodecolor(RArm, "0 0 0 1");
    	%this.setnodecolor(RHand, "0 0 0 1");
    	%this.setdecalname("AAA-None");
    	%scaleX = (getRandom(1, 20) / 10);
    	%scaleY = getRandom(1, 20) / 10;
    	%this.setScale(%scaleX SPC %scaleY SPC "1.4");
    
    	if($Pref::Rendermen::OwnLights == 1) {
        	%this.light = new fxlight()
		{
			dataBlock = playerLight;
			enable = true;
			iconsize = 1;
			player = %this;
		};
		%this.light.attachToObject(%this);
    	}
    
    	// set face
    	%this.setfacename("rendermanface");
    
    	// Define vars
    	%this.isRenderman = 1;
    	%this.RendermanTarget = 0;
    	%this.canbreaklights = 1;
    	%this.RendermanType = "A";
    	%this.RendermanLifetime = getRandom(900, 10000); // lifetime (in renderticks or 100 ms)
    	%this.growled = 0;
    
   	 // Finally, start the new AI
    	%this.RendermanLoop();
    
    	// also return the renderman we just created
    	return %this;
}
//this is the original script that spawns in harmful preppers
//Preppers that are near its target will be killed
//edited to fit this version
function createPrepper(%player) {
	if ($Pref::Rendermen::Prepper == false) {
		$prepperinmap = false;
		//announce("Prepper was disabled, so it cant spawn right now");
		return;//dont want to do anything
	}
	if(!isObject(%player))
		return;
		
	%pPos = %player.getPosition();
	%fPos = vectorAdd(%pPos, getRandom(-30,30) SPC getRandom(-30,30) SPC getRandom(0,15) );
        %pPos = vectorAdd(%pPos, getRandom(-10,10) SPC getRandom(-10,10) SPC getRandom(0,15) );
	%this = new aiplayer(Prepper) {
		datablock = PlayerStandardArmor;
		position = %pPos;
		//attack = %player.client;//excuse me? WE ARE ATTACKING SINCE WHEN?!?!?!?
	};
	%this.player = %player;//As a java programmer, this scares me more than this mod
	missionCleanup.add(%this);
	hideAllNodes(%this);
	%this.unhidenode(chest);
	%this.unhidenode(pants);
	%this.unhidenode(LShoe);
	%this.unhidenode(RShoe);
	%this.unhidenode(LArm);
	%this.unhidenode(LHand);
	%this.unhidenode(RArm);
	%this.unhidenode(RHand);
	%this.setnodecolor(chest, "0 0 0 1");
	%this.setnodecolor(headskin, "0 0 0 1");
	%this.setnodecolor(pants, "0 0 0 1");
	%this.setnodecolor(LShoe, "0 0 0 1");
	%this.setnodecolor(RShoe, "0 0 0 1");
	%this.setnodecolor(LArm, "0 0 0 1");
	%this.setnodecolor(LHand, "0 0 0 1");
	%this.setnodecolor(RArm, "0 0 0 1");
	%this.setnodecolor(RHand, "0 0 0 1");
	%this.setdecalname("AAA-None");
	%this.setfacename("rendermanface");//edited line, replaced it with the scary
	if($Pref::Rendermen::OwnLights == 1) {
        	%this.light = new fxlight()
		{
			dataBlock = playerLight;
			enable = true;
			iconsize = 1;
			player = %this;
		};
		%this.light.attachToObject(%this);
   	}
    
	if ($Pref::Rendermen::ExplodeChance <= 7) {
		if (getRandom(1,$Pref::Rendermen::Prepperrate) == 1) {
			%this.setMoveObject(%player);//muhahahah
		}
	}
	//announce("Playing soons!");
	if (getRandom(0,1) == 1) {
		%player.client.play2D(PrepperLaugh);//only the target should hear this
	} else {
		%player.client.play2D(PrepperLaugh2);//only the target should hear this
	}
        
	if(isObject(%player)) {
		%this.setAimObject(%player);
	}
	$prepperinmap = true;
	%this.PrepperLoop();
	$BSD_Support_Delete = schedule(getRandom(3000, 7000), 0, deletePrepper, %this);
}
//this is the copy paste from support prepper. Spawns in a harlmess prepper
function createharmlessPrepper(%pos) {
	//if(!isObject(%player))
	//	return;
	if ($Pref::Rendermen::Prepper == false) {
		return;//dont want to do anything
	}
	$harmlessPrepperCount += 1;
	%this = new aiplayer(Prepper) {
		datablock = PlayerStandardArmor;
		position = %pos;
		//attack = %player.client;
	};
	missionCleanup.add(%this);
	hideAllNodes(%this);
	%this.unhidenode(chest);
	%this.unhidenode(pants);
	%this.unhidenode(LShoe);
	%this.unhidenode(RShoe);
	%this.unhidenode(LArm);
	%this.unhidenode(LHand);
	%this.unhidenode(RArm);
	%this.unhidenode(RHand);
	%this.setnodecolor(chest, "0 0 0 1");
	%this.setnodecolor(headskin, "0 0 0 1");
	%this.setnodecolor(pants, "0 0 0 1");
	%this.setnodecolor(LShoe, "0 0 0 1");
	%this.setnodecolor(RShoe, "0 0 0 1");
	%this.setnodecolor(LArm, "0 0 0 1");
	%this.setnodecolor(LHand, "0 0 0 1");
	%this.setnodecolor(RArm, "0 0 0 1");
	%this.setnodecolor(RHand, "0 0 0 1");
	%this.setdecalname("AAA-None");
	%this.setfacename("asciiTerror");
	if($Pref::Rendermen::OwnLights == 1) {
        	%this.light = new fxlight()
		{
			dataBlock = playerLight;
			enable = true;
			iconsize = 1;
			player = %this;
		};
		%this.light.attachToObject(%this);
    	}
    	%this.player = %player;
	%this.setMoveObject(%player);
	InitContainerRadiusSearch(%pos, 60, $TypeMasks::PlayerObjectType);
	while((%player = containerSearchNext()) != 0) {
		if(isObject(%player.client)) {
			%target = %player;
			break;
		}
	}
	if(isObject(%target))
		%this.setAimObject(%target);
	if (getRandom(1,$Pref::Rendermen::ExplodeChance) == 1) {
		//follow target lmao in a lmao kami kaze lmao FASHION L M A O HAHAHAHAH
		if (getRandom(1,$Pref::Rendermen::Prepperrate) == 1) {
			%this.setMoveObject(%player);//muhahahah
		}
		$BSD_Support_Delete = schedule(getRandom(800, 10000), 0, angeryPrepper, %this);//chance of exploding!
		%this.explosiveLoop();
	} else {
		$BSD_Support_Delete = schedule(getRandom(800, 60000), 0, deleteharmlessPrepper, %this);//sometimes they can just stick around lmao
		%this.harmlessLoop();
	}
}

function removeRenderman(%this) { // DO NOT remove rendermen ingame with %this.delete(); - use this command instead//wHaT iF I WaNT tO?!?!
    if(isObject(%this)) {
        $rendermeninmap -= 1;
        %this.delete();
    }
}

// Player loop
function Player::CanSeeLoop(%this)
{
    	// reset playervars
    	%this.canSeeRender = 0;
    	%this.safe = 0;
    
    	if(%this.getMountedImage(0) $= nameToID("RenderDetectorImage") || ((%this.client.isAdmin || %this.client.isSuperAdmin) && $Pref::Rendermen::AdminAlwaysDetect == true)) { 
    		// trying to detect rendermen are we
        	//%this.detlure = 1; // renderman detector is a risk
        	//not any more you mokney you
        	//glitch sounds because glitch energy or something idk
            	if (getRandom(1,400) == 55) {
            		%this.client.play2D(glitch1);
            	} else if (getRandom(1,399) == 55) {
            		%this.client.play2D(glitch2);
            	}
            	
        	//begin with creating a message string to add on as detections occur
        	$message = "";
        	%message = %message SPC " Output:<color:ffffff> ";
        	if(%this.detector == 5 || %rendermeninmap >= 7) {
            		ServerPlay3D(Detector,%this.getPosition());
            		%message = %message SPC "Max level detection.";
        	} else if(%this.detector == 4 || %rendermeninmap >= 5) {
            		if(%this.ticknum == 0 || %this.ticknum == 2 || %this.ticknum == 4 || %this.ticknum == 6 || %this.ticknum == 8 || %this.ticknum == 10 || %this.ticknum == 12) {
                		ServerPlay3D(Detector,%this.getPosition());
            		}
            		%message = %message SPC "Very high levels detected.";
        	} else if(%this.detector == 3 || %rendermeninmap == 3) {
            		if(%this.ticknum == 0 || %this.ticknum == 3 || %this.ticknum == 6 || %this.ticknum == 9 || %this.ticknum == 12) {
                		ServerPlay3D(Detector,%this.getPosition());
            		}
            		%message = %message SPC "High levels detected within close range.";
        	} else if(%this.detector == 2 || %rendermeninmap == 2) {
            		if(%this.ticknum == 0 || %this.ticknum == 4 || %this.ticknum == 8 || %this.ticknum == 12) {
                		ServerPlay3D(Detector,%this.getPosition());
            		}
            		%message = %message SPC "Medium levels detected.";
        	} else if(%this.detector == 1 || %rendermeninmap == 1) {
            		if(%this.ticknum == 0) {
                		ServerPlay3D(Detector,%this.getPosition());
            		}
            		%message = %message SPC "Low levels detected.";
        	} else if(%this.detector == 0) {
            		%message = %message SPC "No levels detected.";
        	} else {
        		%message = %message SPC "An error occured. (You shouldn't see this)";
        	}
        
        	if (%this.client.isAdmin || %this.client.isSuperAdmin) {//admin perk
        		//%message = %message SPC "(admin only) Rendermen in game:";
        		//%message = %message SPC $rendermeninmap;
        		//%message = %message SPC $harmlessPrepperCount;
        	}
        
        	if ($prepperinmap == true) {//detect da prepper that WILL kill players
           		ServerPlay3D(prepperTone,%this.getPosition());
            		%message = %message SPC "<color:000000>| <color:FF0000>Detecting hazardus unusual readings. Vacate area.";
		} else {
			if ($harmlessPrepperCount > 0) {
				if ($harmlessPrepperCount <= 3) {
					if(%this.ticknum == 0) {
           					ServerPlay3D(prepperTone,%this.getPosition());
					}
            				%message = %message SPC "<color:000000>| <color:FF0000>Low unusual readings.";
				} else if ($harmlessPrepperCount <= 6) {
					if(%this.ticknum == 0 || %this.ticknum == 4 || %this.ticknum == 8 || %this.ticknum == 12) {
           					ServerPlay3D(prepperTone,%this.getPosition());
					}
            				%message = %message SPC "<color:000000>| <color:FF0000>Medium unusual readings.";
				} else if ($harmlessPrepperCount <= 9) {
					if(%this.ticknum == 0 || %this.ticknum == 3 || %this.ticknum == 6 || %this.ticknum == 9 || %this.ticknum == 12) {
           					ServerPlay3D(prepperTone,%this.getPosition());
					}
            				%message = %message SPC "<color:000000>| <color:FF0000>High unusual readings.";
				} else if ($harmlessPrepperCount <= 12) {
            				if(%this.ticknum == 0 || %this.ticknum == 2 || %this.ticknum == 4 || %this.ticknum == 6 || %this.ticknum == 8 || %this.ticknum == 10 || %this.ticknum == 12) {
           					ServerPlay3D(prepperTone,%this.getPosition());
					}
            				%message = %message SPC "<color:000000>| <color:FF0000>Very high unusual readings.";
				} else {
					if(%this.ticknum == 0 || %this.ticknum == 2 || %this.ticknum == 4 || %this.ticknum == 6 || %this.ticknum == 8 || %this.ticknum == 10 || %this.ticknum == 12) {
           					ServerPlay3D(prepperTone,%this.getPosition());
					}
            				%message = %message SPC "<color:000000>| <color:FF0000>Extremely high unusual readings.";
				}
			} else {
            			%message = %message SPC "<color:000000>| <color:FF0000>No unusual readings.";
            		}
		}
	
		//print message:
		%this.client.bottomPrint(%message, 1);
    	}
    
    	// detector still needs a ticker to work.
    	%this.ticknum = %this.ticknum+1;
    
    	if(%this.ticknum == 14) {
        	%this.ticknum = 0;
    	}
    
    	// player light check
    	if(isObject(%this.light)) {
        	%this.lightlure = 1; // light is a risk
    	} else {
        	%this.lightlure = 0;
    	}
    
    	if(%this.ShrineCheck()) // safe shrine check, boolean
    	{
        	%this.safe = 1;
        	if(%this.ticknum == 0 || %this.ticknum == 4 || %this.ticknum == 8 || %this.ticknum == 12) {
            		%this.detector = %this.detector-1; // decrease detector level
            		if(%this.detector <= -1) {
                		%this.detector = 0;
                		//announce("theres a shrine there, any ways...");
            		}
        	}
        	// this is to make it seem like the glitch energy slowly ebbed away when the player escaped, rather than just poofing
        	%this.schedule(100,canSeeLoop); // player is safe so do not check for rendermen
        	return;
    	}
    
	if(getWord(%this.RenderCheck(),0) != 0) // Is Render in view?
	{
        // if so, let him know...
            	%this.canSeeRender = %this.RenderCheck(); // ...by returning all renderman in the field of view
            	if(%this.lookstart == 0) { // these things only happen when the player first turns towards the renderman
                	%this.lookstart = 1;
                	%this.detector = 3;
                	//%this.allowedlooktime = 50;
                	if(VectorDist(%this.canSeeRender.getPosition(),%this.getPosition()) <= 21) { //dangerously close
                    		if(VectorDist(%this.canSeeRender.getPosition(),%this.getPosition()) <= 8) { //instadeath
                        		%this.allowedlooktime = %this.allowedlooktime-49;
                        		%this.detector = 5;
                    		} else {
                    			%this.allowedlooktime = %this.allowedlooktime-25;
                    			%number = getRandom(1,4);
                    			if (%number == 1) {
                            			%this.client.play2D(HJ1);
                    			} else if (%number == 2) {
                            			%this.client.play2D(HJ2);
                    			} else if (%number == 3) {
                            			%this.client.play2D(HJ3);
                    			} else {
                            			%this.client.play2D(HJ4);
                    			}
                    			%this.detector = 5;
                    		}
                	}
            	}
            	// start look damage
            	%this.allowedlooktime = %this.allowedlooktime-1;
        
            	if(%this.allowedlooktime >= 1) { // make sure not to accidentally destroy the universe by deviding by zero//LMAO WHAT
                	%this.whiteoutlevel = 1 / %this.allowedlooktime * 10;
            	}
        
            	if(%this.allowedlooktime <= 0 && isObject(%this.client)) {
               		%this.setWhiteOut(%this.whiteoutlevel); //%this.setWhiteOut(1);
                	if (getRandom(0,1) == 1) {
                		%this.client.play2D(PlayerDeath2);
                	} else {
                		%this.client.play2D(PlayerDeath);
                	}
                	//%this.client.play2D(PlayerDeath); // play it twice for higher volume.
               	 	hideAllNodes(%this);
                	%this.kill();
            		announce("was slain...");
                	return "DED";
                	//used the tick thing to reduce the sound overload
            	} else if(%this.allowedlooktime <= 10) {
                	%this.setWhiteOut(%this.whiteoutlevel); //%this.setWhiteOut(0.8);
                	if(%this.ticknum == 0 || %this.ticknum == 3 || %this.ticknum == 6 || %this.ticknum == 9 || %this.ticknum == 12) {
                		%this.client.play2D(Glimpse);
                		%this.client.play2D(RenderDeath);
                	}
            	} else if(%this.allowedlooktime <= 20) {
                	%this.setWhiteOut(%this.whiteoutlevel); //%this.setWhiteOut(0.7);
                	if(%this.ticknum == 0 || %this.ticknum == 3 || %this.ticknum == 6 || %this.ticknum == 9 || %this.ticknum == 12) {
                		%this.client.play2D(Glimpse);
                		%this.client.play2D(RenderDeath);
                	}
            	} else if(%this.allowedlooktime <= 35) {
                	%this.setWhiteOut(%this.whiteoutlevel); //%this.setWhiteOut(0.4);
                	if(%this.ticknum == 0 || %this.ticknum == 3 || %this.ticknum == 6 || %this.ticknum == 9 || %this.ticknum == 12) {
                		%this.client.play2D(Glimpse);
                	}
            	} else if(%this.allowedlooktime <= 40) {
                	//%this.setWhiteOut(%this.whiteoutlevel); //%this.setWhiteOut(0.3);
                	//%this.client.play2D(Glimpse);
            	} else if(%this.allowedlooktime <= 50) {
                	//%this.setWhiteOut(%this.whiteoutlevel); //%this.setWhiteOut(0.1);
                	//%this.client.play2D(Glimpse);
            	}
	} else {
        	%this.lookstart = 0;
        	if(%this.allowedlooktime <= 50) {
            		%this.allowedlooktime = %this.allowedlooktime+1;
        	}
        	if(%this.frozen == 0) { // if player isn't frozen
            		if(%this.ticknum == 0 || %this.ticknum == 7) {
                		if($rendermeninmap == 1) { // detector code - detects how many rendermen are about (Can't go over 2 unless one is near)
                					//Edit: Can detect all rendermen
                    			%this.detector = %this.detector-1; // decrease detector level - again, glitch energy ebbs away
                    			if(%this.detector <= 0) { // but make sure not to lower it below 1
                        			%this.detector = 1;
                    			}
                		} else if($rendermeninmap >= 2) {
                    			if (%rendermeninmap == 2) {
                       	 			%this.detector = 2;
                    			} else if (%rendermeninmap == 3) {
                        			%this.detector = 3;
                    			} else if(%rendermeninmap == 4){
                        			%this.detector = 4;
                    			} else {
                    				%this.detector = 5;
                    			}
                    			%this.detector = %this.detector-1; // decrease detector level - again, glitch energy ebbs away
                    			if(%this.detector <= $rendermeninmap) { // but make sure not to lower it below 2
                        			%this.detector = $renermeninmap;
                    			}
                		} else {
                    			if(%this.ticknum == 0 || %this.ticknum == 7) {
                        			%this.detector = %this.detector-1; // decrease detector level - again, glitch energy ebbs away
                        			if(%this.detector <= -1) { // but make sure not to messit up over in the process
                            				%this.detector = 0;
                        			}
                    			}
                		}
            		}
		}
    	}
    
    	if(%this.frozen == 1) { // wait for render to sod off before unfreezing the player
       	 	%unfreeze = 1;
        	%this.setVelocity("0 0 0");
        	//%this.client.play2D(QuietCaptureSound);
        	InitContainerRadiusSearch(%this.getPosition(),20,$TypeMasks::PlayerObjectType);
        	while((%targetObject=containerSearchNext()) !$= 0) {
            		if(%targetObject.isRenderman == 1) {
                		%unfreeze = 0;
            		}
        	}
        
        	if(%unfreeze == 1) {
            		%this.setDatablock(%this.prevDatablock);
            		%this.frozen = 0;
        	}
    	}
    	%this.schedule(100,canSeeLoop);
}

// MAIN MOD FUNCTION //
function RMOD_Check() {
   	// %cycleTime = 8000;//setting the scheduler to 8 seconds due to the 'boom' music samples being so long and unable to layer correctly/
    	//modifying code when you u n d e r s t a n d it is fun
    	cancel($RMOD_check);
	%a = -1;
	%say[%a++] = "Hmmmm....";
	%say[%a++] = "Is someone there?";
	%say[%a++] = "dun dun dun...";
	%say[%a++] = "I see you";
	%say[%a++] = "Looking for me?";
	%say[%a++] = "What are you looking at?";
	//added messages: (FIGHT ME)
	%say[%a++] = "6^B4#$45I#SEE%YOU32.X@s52";
    
    	//bug test
    	//announce($harmlessPrepperCount);
    	//another bug fix because im too lazy to find out why the prepper count goes negative:
    	if ($harmlessPrepperCount < 0) {
    		$harmlessPrepperCount = 0;
    		//i know this isn't the best way to fix this issue
    		//There is something thats decreasing the count twice when it should only do it once.
    		//spagetti code moment
    	}
   	if($Pref::Rendermen::Enabled == 0) { // also, is the mod allowed to work?
    		//rant alert: (keep in mind that I am a java dev)
    
    		//I shouldn't say this, but this is a very ineffective way of checking to see if the mod is allowed to work. This script should NOT RUN outside of the server/game.
    		//Let me explain: If you were to run this mod for the first time on start up of a new game session, this script will load and keep running reguardless if you exit the 
    		//server. (this only happens if you host a server and then get off without exiting the game entirely). If you were to change gamemodes, then rendermen will CONTINUE
    		//to spook if they are STILL ENABLED. I doubt that BL has a proper way to deactivate this script.
    		//I have memories of playing offline with the renderman mod and then getting bored. I then decided to play the unlimited mining mod. It creeped me out that they still 
    		//spawned IN A DIFFERENT GAME MODE that DOES NOT INCLUDE THIS MOD. I mined with 3 rendermen following me, unable to kill me since there was some error that I quite don't
    		//remember. (probably because of certain scripts not loading)
    		//I guess this is a product of spagetti code within this game engine where it does not terminate all in game processes that are created by addons. 
    		//Or that C - Sharp has no way of storing processes/threads into a list that acts as pointers or id's for the processes/threads.
    		//In java, this would be easy as you would have an array list that contains all threads. (I am aware of the memory leaks that this causes when not handled correctly). 
    		//You have a loop that kills each process by calling each one in the list. This is what I do with my personal projects as this is very efficient. 
    		//(I.e: enemies have their own thread and kill the process when they die)
    		// Since this is using C sharp, there really isn't a way of doing this. I am still amazed by how this language works without having the need 
    		//to create threads that do not interfear with the main game thread. I get that its more forgiving than java, and more easy to learn as you don't need to go the verbose way.
    		//But there needs to be a way of terminating these processes when the game changes
        	$RMOD_check = schedule( 25000, 0, RMOD_Check, %say[getRandom(0,%a)] ); // if not give it a while and check again
		return;
    	}
    
    	cancel($RMOD_check);
    
    	if(getRandom(0,25) == 0) // message?
	{
		echo( "\c2" @ %say[getRandom(0,%a)] );
	}
   
    
    	// Sounds (Common)
    	//had to redo this section lol
   	if ($rendermeninmap >= 9 || $harmlessPrepperCount >= 15) {
    		serverPlay2D(CB5);
    	} else if ($rendermeninmap >= 6 || $harmlessPrepperCount >= 12) {
    		serverPlay2D(CB4);
    	} else if ($rendermeninmap >= 4 || $harmlessPrepperCount >= 9) {
    		serverPlay2D(CB3);
    	} else if ($rendermeninmap >= 2 || $harmlessPrepperCount >= 6) {
    		serverPlay2D(CB2);
    	} else if ($rendermeninmap == 1 || $harmlessPrepperCount >= 3) {
    		serverPlay2D(CB1);
    	} else {
        	serverPlay2D(CBQ);
    	}
    
    	// If there are players...
    	if(clientGroup.getCount() > 0)
	{
        	%player = clientGroup.getObject( getRandom(0, clientGroup.getCount()-1) ).player; // pick one
        
		if(isObject(%player)) // player is spawned
		{
            		%pPos = %player.getPosition();
	    		%fPos = vectorAdd(%pPos, getRandom(-30,30) SPC getRandom(-30,30) SPC getRandom(0,15) );
            		%pPos = vectorAdd(%pPos, getRandom(-10,10) SPC getRandom(-10,10) SPC getRandom(0,15) );
            
            		// Random Checks //
            		// These are in categories now!
            
            		// Light Fluxuatiupwifwndwdwi //what tf?
            		%foundalight = 0;//this is why I prefer java's implementation of defining data types so that we know wtf we are looking at
            		if($Pref::Rendermen::Lights == 1 && getRandom(1,6) == 3) {
                		InitContainerRadiusSearch(%pPos,75,$TypeMasks::FxBrickObjectType);
                		while((%targetObject=containerSearchNext()) !$= 0) {
                    			if(%targetObject.getLightID() !$= -1) { // light found?
                        			if(getRandom(1,2) == 1) {
                            				if(getRandom(1,3) == 1) {
                                				schedule(getRandom(100,1300),0,renderBreakLight,%targetObject);
                            				} else {
                                				schedule(getRandom(100,1300),0,renderAffectLight,%targetObject);
                            				}
                        			}
                    			}
                		}
            		}
            		//prepper stuff:
            		if ($Pref::Rendermen::Prepperrate != 100) {
           	 		//spawn in prepper? a harmle- I mean.. a mostly harmless spoop?
    	   	 		if (getRandom(1,$Pref::Rendermen::Prepperrate) == 1) {
    	  	  			createharmlessPrepper(%fPos);
    	  	  	
    	  	  		}
          	  		//spawn in prepper? a harmful and leathal spoop?
    	  	  		if (getRandom(1,$Pref::Rendermen::Prepperrate) == 1 && ($prepperinmap == false && $Pref::Rendermen::Angeryprepper != false)) {
					//$prepperinmap = true;
    	   	 			createPrepper(%player);
    	    			}
    	   	 		//spawn another prepper to keep on da SpOoPY?
    	    			if (getRandom(1,$Pref::Rendermen::Prepperrate) == 1) {
    	    				createharmlessPrepper(%pPos);//different loc so they dont stack LMAO
    	    			}
    	    		} 
    	    		if (getRandom(1,20) == 10) {
    	    			//spawn in a glimpse object infront of the player
    	    			//idk how to do this lmao
    	    	
    	    			//get player pos
    	    	
    	    			//get pos infront of player
    	    	
    	    			//create a prepper infront of player
    	    	
    	    			//have the prepper look at the player
    	    	
    	    			//play sound?
    	    	
    	    			//remove prepper in like 800 milliseconds
    	    			
    	    			
    	    			%what = getRandom(1,5);
    	    			if (%what == 1) {
    	    				serverPlay2D(VCR1);
    	    			} else if (%what == 2) {
    	    				serverPlay2D(VCR2);
    	    			} else if (%what == 3) {
    	    				serverPlay2D(VCR3);
    	    			} else if (%what == 4) {
    	    				serverPlay2D(VCR4);
    	    			} else {
    	    				serverPlay2D(VCR5);
    	    			}
    	    		}
            		// Faces
           	 	if(getRandom(1,6) == 1) { // AAAAAAAAAAAAAAAAAAAAAAAAAAA
                		if(getRandom(1,2) == 1) { // pick one.
                    			%p = new projectile()
                    			{
                        			dataBlock 	 = RendermanAProjectile;
                        			initialVelocity  = 0;
                        			initialPosition  = %pPos;
                    			};
                    			serverPlay3D(facesoond, %p.getPosition);
                    			missionCleanup.add(%p);
                		} else {
                    			%p = new projectile()
                    			{
                        			dataBlock        = RendermanBProjectile;
                        			initialVelocity  = 0;
                        			initialPosition  = %pPos;
                    			};
                    			serverPlay3D(facesoond, %p.getPosition);
                    			missionCleanup.add(%p);
                		}
            
                		$RMOD_check = schedule($Pref::Renderman::cycleTime,0,RMOD_CHECK);  
                		return; // disallow other random events until next RMOD tick
            		} 
            
            		// Rendermen
            		if(getRandom(1,$Pref::Rendermen::Spawnrate) == 1) { // add a renderman?
                		if($Pref::Rendermen::Spawnrate != 1337) {
    	    				//announce("Adding a harmful spoop!");
    	    				if ($Pref::Rendermen::voidSpawn == true) {
    	    					addRendermanThroughVoid(%fPos);
    	    				} else {
                    				addRenderman(%fPos);
                    			}
                		}
            		}
            
            		// Sounds (Uncommon)
            		if(getRandom(1,11) == 1) {
   				if ($rendermeninmap >= 5 || $harmlessPrepperCount >= 10) {
   					%sound = getRandom(1,3); // pick
                			if (%sound == 1) {
                	    			serverPlay2D(AL1);
                			} else if (%sound == 2) {
                	    			serverPlay2D(AL2);
                			} else {
                	    			serverPlay2D(AL3);
                			}
    				} else if ($rendermeninmap >= 2 || $harmlessPrepperCount >= 6) {
    					%sound = getRandom(1,3); // pick
                			if (%sound == 1) {
                	    			serverPlay2D(AC1);
                			} else if (%sound == 2) {
                	    			serverPlay2D(AC2);
                			} else {
                	    			serverPlay2D(AC3);
                			}
            			} else if ($rendermeninmap >= 1 || $harmlessPrepperCount >= 3) {
                			%sound = getRandom(1,4); // pick
                			if (%sound == 1) {
                	    			serverPlay2D(AD1);
                			} else if (%sound == 2) {
                	    			serverPlay2D(AD2);
                			} else if (%sound == 3) {
                	    			serverPlay2D(AD3);
                			} else {
                	    			serverPlay2D(AD4);
                			}
                		} else {
                			if (%sound == 1) {
                	    			serverPlay2D(AW1);
                			} else if (%sound == 2) {
                	    			serverPlay2D(AW2);
                			} else  {
                	    			serverPlay2D(AW3);
                			}
                		}
                		$RMOD_check = schedule($Pref::Renderman::cycleTime,0,RMOD_CHECK);  
                		return; // disallow other random events until next RMOD tick
            		} else if (getRandom(1,10) == 1) {
            			%sound = getRandom(1,5); // pick
                			if (%sound == 1) {
               					serverPlay2D(AO1);
                			} else if (%sound == 2) {
                				serverPlay2D(AO2);
                			} else if ($sound == 3) {
                				serverPlay2D(AO3);
                			} else if ($sound == 4) {
                				serverPlay2D(AO4);
               				} else {
                				serverPlay2D(AO5);
                			}
                		$RMOD_check = schedule($Pref::Renderman::cycleTime,0,RMOD_CHECK);  
               			 return; // disallow other random events until next RMOD tick
            		}
        	}
    	}
    	$RMOD_check = schedule($Pref::Renderman::cycleTime,0,RMOD_CHECK); // schedule another bunch of tests in 4 seconds
}

RMOD_Check();

function renderBreakLight(%targetObject) {
    if(%targetObject.getDatablock().getName() !$= "brickRenderLight" && %targetObject.isBroken != 1) { // can we do something about this light?
        %targetObject.isBroken = 1;
        %targetObject.prevLight = %targetObject.getLightID().getDatablock();
        %targetObject.prevFx = %targetObject.getColorFxID();
        %targetObject.setLight(0);
        %targetObject.setColorFx(0);
        %delay = getRandom(1800, 18000);
        schedule(%delay,0,renderRestoreLight,%targetObject);
        schedule(%delay,0,serverPlay3D,MapLightsOn,%targetObject.getPosition());
        
        if(isObject(%targetObject.emitter)) { // if the target has an emitter we'll assume it's a torch or something
            %targetObject.prevEmitter = %targetObject.emitter.getEmitterDatablock().getName();
            %targetObject.setEmitter(0);
        } else {
            serverPlay3D(MapLightsOff,%targetObject.getPosition());
            %targetObject.prevEmitter = 0;
        }
    } else {
        renderAffectLight(%targetObject);
    }
}

function renderAffectLight(%targetObject) {
    %targetObject.prevLight = %targetObject.getLightID().getDatablock();
    %targetObject.prevFx = %targetObject.getColorFxID();
    %targetObject.setLight(0);
    %targetObject.setColorFx(0);
    
    if(isObject(%targetObject.emitter)) { // if the target has an emitter we'll assume it's a torch or something
        %targetObject.prevEmitter = %targetObject.emitter.getEmitterDatablock().getName();
        %targetObject.setEmitter(0);
    } else {
        %targetObject.prevEmitter = 0;
    }
    
    schedule(250,0,renderRestoreLight,%targetObject);  // just flicker
}

function renderRestoreLight(%targetObject) {
    %targetObject.setLight(%targetObject.prevLight);
    %targetObject.setEmitter(%targetObject.prevEmitter);
    %targetObject.setColorFx(%targetObject.prevFx);
    
    if(isObject(%targetObject.emitter)) {
        %targetObject.isBroken = 0;
        return;
    }
    
    if(getRandom(1,3) != 1) {
        schedule(100,0,renderAffectLight,%targetObject);
    } else {
        %targetObject.isBroken = 0;
    }
}

// Detector bricks use a loop to work
function FxDTSBrick::DetectorLoop(%this) {
    if($rendermeninmap >= 1 && %this.isRayCasting() == 1) {
        InitContainerRadiusSearch(%this.getPosition(),170,$TypeMasks::PlayerObjectType);
        while((%targetObject=containerSearchNext()) !$= 0) {
            if(%targetObject.isRenderman == 1) {
                %distance = VectorDist(%this.getPosition(),%targetObject.getPosition());
            } else {
                %distance = 100; // "safe" distance
            }
        }
        // detector brick needs a ticker to work as well
        %this.ticknum = %this.ticknum+1;
    
        if(%this.ticknum == 14) {
            %this.ticknum = 0;
        }
        
        if(%distance >= 120) {
            if(%this.ticknum == 0 || %this.ticknum == 7) {
                serverPlay3D(Detector,%this.getPosition());
                %this.setColorFX(3);
            }
        } else if(%distance >= 80) {
            if(%this.ticknum == 0 || %this.ticknum == 4 || %this.ticknum == 8 || %this.ticknum == 12) {
                serverPlay3D(Detector,%this.getPosition());
                %this.setColorFX(3);
            }
        } else if(%distance >= 40) {
            if(%this.ticknum == 0 || %this.ticknum == 2 || %this.ticknum == 4 || %this.ticknum == 6 || %this.ticknum == 8 || %this.ticknum == 10 || %this.ticknum == 12) {
                serverPlay3D(Detector,%this.getPosition());
                %this.setColorFX(3);
            }
        } else {
            serverPlay3D(Detector,%this.getPosition());
            %this.setColorFX(3);
        }
        %this.schedule(100,setColorFX,0);
    }
    %this.schedule(150,DetectorLoop);
}

// PACKAGED FUNCTIONS //
package RMod
{
    function GameConnection::spawnPlayer(%this)
    {
        %return = parent::spawnPlayer(%this);
        //%this.player.client.centerPrint("WARNING: <color:FFFFFF>This mod is NOT FOR THE FAINT OF HEART! If you are offended over scary images, or easily scared, please leave. This mod is currently a work in progess. Any content shown here may change and does not reflect the final version.");
        %this.player.CanSeeLoop();
        %this.allowedlooktime = 50;
        %this.frozen = 0;
        return %return;//what? you can return a return?? NANI!?!?!?
    }
    
    function brickRenderDetect::onplant(%data,%this) {
        Parent::onPlant(%this);
        %this.DetectorLoop();
    }
    
    function brickRenderDetectonLoadplant(%data,%this) {
        Parent::onLoadPlant(%this);
        %this.DetectorLoop();
    }
};

activatePackage(RMod);

// EVENTS
registerOutputEvent(fxDTSBrick, setRendermenActive,"bool 1");
registerOutputEvent(fxDTSBrick, setRendermanAgro,"list Paranormal 250 Evil 150 Demonic 100 Insane 75 Nightmare 25");
registerOutputEvent(fxDTSBrick, incRendermanAgro,"int 1 4 1");
registerOutputEvent(fxDTSBrick, decRendermanAgro,"int 1 4 1");
registerOutputEvent(fxDTSBrick, setRendermanSpawnRate,"list Don't 1337 Unsafe 30 Dangerous 23 Frightening 19 Insane 13 Nightmare 7");
registerOutputEvent(fxDTSBrick, incRendermanSpawnRate,"int 1 4 1");
registerOutputEvent(fxDTSBrick, decRendermanSpawnRate,"int 1 4 1");
registerOutputEvent(fxDTSBrick, setRendermanLimit,"int 1 5 1");
registerOutputEvent(fxDTSBrick, incRendermanLimit,"int 1 4 1");
registerOutputEvent(fxDTSBrick, decRendermanLimit,"int 1 4 1");
registerOutputEvent(fxDTSBrick, spawnRenderman);

// Idiot proof!
registerAdminOnlyOutputEvent(fxDTSBrick, setRendermenActive, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, setRendermanAgro, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, incRendermanAgro, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, decRendermanAgro, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, setRendermanSpawnRate, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, incRendermanSpawnRate, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, decRendermanSpawnRate, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, setRendermanLimit, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, incRendermanLimit, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, decRendermanLimit, 2);
registerAdminOnlyOutputEvent(fxDTSBrick, spawnRenderman, 1);

// actual code:
function fxDTSBrick::setRendermenActive(%this,%bool) {
    $Pref::Rendermen::Enabled = %bool;
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
}

function fxDTSBrick::setRendermanAgro(%this,%val) {
    $Pref::Rendermen::Speed = %val;
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
}

function fxDTSBrick::incRendermanAgro(%this,%val) {
    %newpref = $Pref::Rendermen::Speed;
    if($Pref::Rendermen::Speed == 250) {
        %newpref = 150;
    }
    if($Pref::Rendermen::Speed == 1500) {
        %newpref = 100;
    }
    if($Pref::Rendermen::Speed == 100) {
        %newpref = 75;
    }
    if($Pref::Rendermen::Speed == 75) {
        %newpref = 25;
    }
    $Pref::Rendermen::Speed = %newpref;
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
    if(%val > 1) {
        %this.incRendermanAgro(%val-1);
    }
}

function fxDTSBrick::decRendermanAgro(%this,%val) {
    %newpref = $Pref::Rendermen::Speed;
    if($Pref::Rendermen::Speed == 25) {
        %newpref = 75;
    }
    if($Pref::Rendermen::Speed == 1500) {
        %newpref = 250;
    }
    if($Pref::Rendermen::Speed == 100) {
        %newpref = 150;
    }
    if($Pref::Rendermen::Speed == 75) {
        %newpref = 100;
    }
    $Pref::Rendermen::Speed = %newpref;
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
    if(%val > 1) {
        %this.decRendermanAgro(%val-1);
    }
}

function fxDTSBrick::setRendermanSpawnRate(%this,%val) {
    $Pref::Rendermen::Spawnrate = %val;
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
}

function fxDTSBrick::incRendermanSpawnRate(%this,%val) {
    %newpref = $Pref::Rendermen::Spawnrate;
    if($Pref::Rendermen::Spawnrate == 30) {
        %newpref = 23;
    }
    if($Pref::Rendermen::Spawnrate == 23) {
        %newpref = 19;
    }
    if($Pref::Rendermen::Spawnrate == 19) {
        %newpref = 13;
    }
    if($Pref::Rendermen::Spawnrate == 13) {
        %newpref = 7;
    }
    $Pref::Rendermen::Spawnrate = %newpref;
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
    if(%val > 1) {
        %this.incRendermanSpawnRate(%val-1);
    }
}

function fxDTSBrick::decRendermanSpawnRate(%this,%val) {
    %newpref = $Pref::Rendermen::Spawnrate;
    if($Pref::Rendermen::Spawnrate == 3) {
        %newpref = 13;
    }
    if($Pref::Rendermen::Spawnrate == 13) {
        %newpref = 19;
    }
    if($Pref::Rendermen::Spawnrate == 19) {
        %newpref = 23;
    }
    if($Pref::Rendermen::Spawnrate == 23) {
        %newpref = 30;
    }
    $Pref::Rendermen::Spawnrate = %newpref;
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
    if(%val > 1) {
        %this.decRendermanSpawnRate(%val-1);
    }
}

function fxDTSBrick::setRendermanLimit(%this,%val) {
    $Pref::Rendermen::Limit = %val;
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
}

function fxDTSBrick::incRendermanLimit(%this,%val) {
    $Pref::Rendermen::Limit = $Pref::Rendermen::Limit+%val;
    if($Pref::Rendermen::Limit > 20) {
        $Pref::Rendermen::Limit = 20;
    }
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
}

function fxDTSBrick::decRendermanLimit(%this,%val) {
    $Pref::Rendermen::Limit = $Pref::Rendermen::Limit-%val;
    if($Pref::Rendermen::Limit < 1) {
        $Pref::Rendermen::Limit = 1;
    }
    for(%i=0;%i<ClientGroup.getCount();%i++)
    {
        %cl = ClientGroup.getObject(%i);
        if(%cl.isSuperAdmin && %cl.hasRTB && %cl.hasPrefList) {
            RTBSC_SendPrefValues(%cl);
        }
    }
    RTBSC_savePrefValues();
}

function fxDTSBrick::spawnRenderman(%this) {
    %pos = vectorAdd(%this.getPosition(),"0 0 1");
    addRenderman(%pos);
}

function servercmdmakerenderman(%cl,%victim) {
    %victimcl = findclientbyname(%victim);
    if(%cl.isAdmin || %cl.isSuperAdmin) {
        if(isObject(%victimcl) && isObject(%victimcl.player)) {
            %victimcl.player.isRenderman = 1;
            announce(%victimcl.name SPC "is now a Renderman! Run!");
            ServerPlay2D(CatchSound);
        }
    }
}

function servercmdrendermancount() {
	announce("There are" SPC $rendermeninmap SPC "renderman currently in the map");
}

function servercmdwhoisgettingtracked() {
	announce("Renderman is currently tracking:");
	announce(%this.RendermanTarget.client.name);
	announce(%this.RendermanTarget);
	announce(%object);
	announce(%player);
	announce(%victum);
	announce("Rendermans scale is:");
	announce(%sound);
}


function servercmdrendermandeath(%cl,%victim) {
    %victimcl = findclientbyname(%victim);
    if(%cl.isAdmin || %cl.isSuperAdmin) {
        if(isObject(%victimcl) && isObject(%victimcl.player)) {
            %haha = %victimcl.player.getPosInFrontOfPlayer(-2);
            %result = addRenderman(%haha);
            
            if(%result == 0) {
                announce("Too many rendermen exist right now to do that. Try again in a minute or so. If you cannot, then do a /clearrendermen command.",5);
            }
        }
    }
}

function servercmdrendermanadmindetect(%cl) {
	if (%cl.isAdmin || %cl.isSuperAdmin) {
		$Pref::Rendermen::AdminAlwaysDetect = 1;
	}
}
//nice to see you here. This command is for debugging
function servercmdresetprepper(%cl) {
	if (%cl.isAdmin || %cl.isSuperAdmin) {
		announce("Prepper reseted (DEV)",5);
		$prepperinmap = false;
	}
}

//test commands
//TODO: REMOVE BEFORE SHIP
function addRendermanThroughVoid(%pos) {
	serverPlay3D(void1, %pos);
        schedule(1300,0,voidThing,%pos);
        schedule(3000,0,addRenderman,%pos);
        //announce("A renderman entered through an opening in our reality!");
        %what = getRandom(1, 2);
        if (%what == 1) {
        	serverPlay2D(VCD1);
        } else if (%what == 2) {
        	serverPlay2D(VCD2);
        } else {
        	serverPlay2D(VCD3);
 
        }
}

function servercmdvoid(%cl) {
	%pPos = %cl.player.getPosition();
	%fPos = vectorAdd(%pPos, getRandom(-30,30) SPC getRandom(-30,30) SPC getRandom(0,15) );
	serverPlay3D(void1, %fPos);
        schedule(1300,0,voidThing,%fPos);
        schedule(3000,0,createPrepperA,%fPos);
	announce("Void happpened");
}
function voidThing(%pos) {
	%p = new projectile() {
		dataBlock 	 = RendermanAProjectile;
		initialVelocity  = 0;
		initialPosition  = %pos;
        };
}


// GLITCHGUN
function GlitchGunImage::onInit(%this, %obj, %slot)
{
	schedule(800,0,GlitchGunEffect,%obj,%this);
    //announce(%this);
	
	return serverPlay3D(beep_key_sound, getWords(%obj.getTransform(), 0, 2));
}

function GlitchGunEffect(%this,%gun) {
    %this.setWhiteOut(1);
    
    InitContainerRadiusSearch(%this.getPosition(),20,$TypeMasks::FxBrickObjectType);
    while((%targetObject=containerSearchNext()) !$= 0) {
        if(%targetObject.getLightID() !$= -1) { // light found?
            renderBreakLight(%targetObject);
        }
    }
    InitContainerRadiusSearch(%this.getPosition(),15,$TypeMasks::PlayerObjectType);
    while((%targetObject=containerSearchNext()) !$= 0) {
        if(%targetObject.isRenderman == 1) { // renderman found?
            %targetObject.RendermanTeleport();
        } else {
            if(isObject(%targetObject.client)) {
                %targetObject.setWhiteOut(0.8);
                %targetObject.client.play2D(GlitchGunSound);
            }
        }
    }
    %this.renRemoveItem(%gun.Item.getId(),0,%this.client);
}

// "borrowed" this from the removeitem script - sorry Truce :/
function Player::renRemoveItem(%this,%item,%bool,%cl) {
	if(isObject(%this)) {
		if(%item>0) {
			for(%i=0;%i<5;%i++) {
				%tool=%this.tool[%i].getID();
				if(%tool==%item.getID()) {
					%this.tool[%i] = 0;
					messageClient(%cl,'MsgItemPickup','',%i,0);
					if(%this.currTool==%i) {
						%this.updateArm(0);
						%this.unMountImage(0);
					}
					if(!%bool)
						break;
				}
			}
		}
	}
}
function createPrepperA(%pos) {
	//if(!isObject(%player))
	//	return;
	%this = new aiplayer(Prepper) {
		datablock = PlayerStandardArmor;
		position = %pos;
		//attack = %player.client;
	};
	missionCleanup.add(%this);
	hideAllNodes(%this);
	%this.unhidenode(chest);
	%this.unhidenode(pants);
	%this.unhidenode(LShoe);
	%this.unhidenode(RShoe);
	%this.unhidenode(LArm);
	%this.unhidenode(LHand);
	%this.unhidenode(RArm);
	%this.unhidenode(RHand);
	%this.setnodecolor(chest, "0 0 0 1");
	%this.setnodecolor(headskin, "0 0 0 1");
	%this.setnodecolor(pants, "0 0 0 1");
	%this.setnodecolor(LShoe, "0 0 0 1");
	%this.setnodecolor(RShoe, "0 0 0 1");
	%this.setnodecolor(LArm, "0 0 0 1");
	%this.setnodecolor(LHand, "0 0 0 1");
	%this.setnodecolor(RArm, "0 0 0 1");
	%this.setnodecolor(RHand, "0 0 0 1");
	%this.setdecalname("AAA-None");
	%this.setfacename("asciiTerror");
	//%this.setMoveObject(%player);
	InitContainerRadiusSearch(%pos, 60, $TypeMasks::PlayerObjectType);
	while((%player = containerSearchNext()) != 0) {
		if(isObject(%player.client)) {
			%target = %player;
			break;
		}
	}
	if(isObject(%target))
		%this.setAimObject(%target);
	$BSD_Support_Delete = schedule(getRandom(1000, 5000), 0, deletePrepper, %this);
}
function createPrepperB(%pos) {
	//if(!isObject(%player))
	//	return;
	%this = new aiplayer(Prepper) {
		datablock = PlayerStandardArmor;
		position = %pos;
		//attack = %player.client;
	};
	missionCleanup.add(%this);
	hideAllNodes(%this);
	%this.unhidenode(chest);
	%this.unhidenode(pants);
	%this.unhidenode(LShoe);
	%this.unhidenode(RShoe);
	%this.unhidenode(LArm);
	%this.unhidenode(LHand);
	%this.unhidenode(RArm);
	%this.unhidenode(RHand);
	%this.setnodecolor(chest, "0 0 0 1");
	%this.setnodecolor(headskin, "0 0 0 1");
	%this.setnodecolor(pants, "0 0 0 1");
	%this.setnodecolor(LShoe, "0 0 0 1");
	%this.setnodecolor(RShoe, "0 0 0 1");
	%this.setnodecolor(LArm, "0 0 0 1");
	%this.setnodecolor(LHand, "0 0 0 1");
	%this.setnodecolor(RArm, "0 0 0 1");
	%this.setnodecolor(RHand, "0 0 0 1");
	%this.setdecalname("AAA-None");
	%this.setfacename("asciiTerror");
	%this.setMoveObject(%player);
	InitContainerRadiusSearch(%pos, 60, $TypeMasks::PlayerObjectType);
	while((%player = containerSearchNext()) != 0) {
		if(isObject(%player.client)) {
			%target = %player;
			break;
		}
	}
	announce("Health1:" SPC %this.getDamageLevel());
	
	if(isObject(%target)) {
		%this.setAimObject(%target);
	}
	$BSD_Support_Delete = schedule(3000, 0, sellheal, %this);
	return %this;
}

function sellheal(%this) {
	announce("health2:" SPC %this.getDamageLevel());
	announce("health3:" SPC %this.health);
	announce("healthM:" SPC %this.getMaxhealth());
	
	
}
