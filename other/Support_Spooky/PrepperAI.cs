//outline of this ai:
//this is a simple ai, so don't think its all that because its more of a consistant checker for the preppers death
//setup the ai while appropriatly typing it (make it look and maybe move)
//let it do its thing while checking to see if its dead
//if its still alive, this will contribute to the spawn rate of the rendermen(on the todo list)

//there are three types of prepper in this mod:
//type 1 is the most unique one and only one can spawn at a time. This one has the scary face and will kill the target if its within a range.
//type 1 will ONLY KILL ONE TARGET AT A TIME SO CHILL IF YOU DONT HEAR LAUGHTER
//type 2 is the most common where it simply stares at the player not doing anything except for increasing the spawnrate of the rendermen while its alive
//type 3 is not as common as type 2, however this one stares as well with the exception of exploding and a rare chance of moving depending on the argo of prepper


function AIPlayer::harmlessLoop(%this) {
	//checks to see if we should kill the script
	if(%this.PShrineCheck()) {
		%number = getRandom(1, 5);
        	if (%number == 1) {
        		serverPlay3D(VCV1,%this.getPosition());
        	} else if ($number == 2) {
        		serverPlay3D(VCV2,%this.getPosition());
        	} else if ($number == 3) {
        		serverPlay3D(VCV3,%this.getPosition());
        	} else if ($number == 4) {
        		serverPlay3D(VCV4,%this.getPosition());
        	} else {
        		serverPlay3D(VCV5,%this.getPosition());
        	}
        	$harmlessPrepperCount -= 1;
        	%this.delete();
        	return "SHRINE";
    	}
    	if (%this.getDamageLevel() >= 100) {
        	$harmlessPrepperCount -= 1;
		%ch = getRandom(1,10);
		if (%ch == 1) {
    			serverPlay3D(VCD1,%this.getPosition());
    		} else if (%ch == 5) {
    			serverPlay3D(VCD2,%this.getPosition());
    		}
    		%this.delete();
        	return "KIA";
    	}
    	if($rendermancleanup == 1) { //clean up?
        	$harmlessPrepperCount -= 1;
		%number = getRandom(1, 5);
        	if (%number == 1) {
        		serverPlay3D(VCV1,%this.getPosition());
        	} else if ($number == 2) {
        		serverPlay3D(VCV2,%this.getPosition());
        	} else if ($number == 3) {
        		serverPlay3D(VCV3,%this.getPosition());
        	} else if ($number == 4) {
        		serverPlay3D(VCV4,%this.getPosition());
        	} else {
        		serverPlay3D(VCV5,%this.getPosition());
        	}
        	%this.delete();
        	return;//kill this script
    	}
    	%this.schedule($Pref::Rendermen::Prepperrate,harmlessLoop);//re schedule the script
}
function HarmlessIdle(%this) {
	//announce("void thing");
    	if (%this.getDamageLevel() >= 100) {
    		return;
    	}
	%number = getRandom(1, 20);
	if (%number == 1) {
		serverPlay3D(VCI1, %this.getPosition());
	} else if (%number == 2) {
		serverPlay3D(VCI2, %this.getPosition());
	} else if (%number == 3) {
		serverPlay3D(VCI3, %this.getPosition());
	} else if (%number == 4) {
		serverPlay3D(VCI4, %this.getPosition());
	} else if (%number == 5) {
		serverPlay3D(VCI5, %this.getPosition());
	} else if (%number == 6) {
		serverPlay3D(VCI6, %this.getPosition());
	} else if (%number == 7) {
		serverPlay3D(VCI7, %this.getPosition());
	} else if (%number == 8) {
		serverPlay3D(VCI8, %this.getPosition());
	} else if (%number == 9) {
		serverPlay3D(VCI9, %this.getPosition());
	} else if (%number == 10) {
		serverPlay3D(VCI10, %this.getPosition());
	} else if (%number == 11) {
		serverPlay3D(VCI11, %this.getPosition());
	} else if (%number == 12) {
		serverPlay3D(VCI12, %this.getPosition());
	} else if (%number == 13) {
		serverPlay3D(VCI13, %this.getPosition());
	} else if (%number == 14) {
		serverPlay3D(VCI14, %this.getPosition());
	} else if (%number == 15) {
		serverPlay3D(VCI15, %this.getPosition());
	} else if (%number == 16) {
		serverPlay3D(VCI16, %this.getPosition());
	} else if (%number == 17) {
		serverPlay3D(VCI17, %this.getPosition());
	} else if (%number == 18) {
		serverPlay3D(VCI18, %this.getPosition());
	} else if (%number == 19) {
		serverPlay3D(VCI19, %this.getPosition());
	} else  {
		serverPlay3D(VCI20, %this.getPosition());
	}
}

function AIPlayer::explosiveLoop(%this) {
	//checks to see if we should kill the script
	if(%this.PShrineCheck()) {
		%number = getRandom(1, 5);
        	if (%number == 1) {
        		serverPlay3D(VCV1,%this.getPosition());
        	} else if ($number == 2) {
        		serverPlay3D(VCV2,%this.getPosition());
        	} else if ($number == 3) {
        		serverPlay3D(VCV3,%this.getPosition());
        	} else if ($number == 4) {
        		serverPlay3D(VCV4,%this.getPosition());
        	} else {
        		serverPlay3D(VCV5,%this.getPosition());
        	}
        	$harmlessPrepperCount -= 1;
        	%this.delete();
        	return "SHRINE";
    	}
    	if (%this.getDamageLevel() >= 100) {
    		%this.delete();
        	$harmlessPrepperCount -= 1;
        	return "KIA";
    	}
    	if($rendermancleanup == 1) { //clean up?
        	$harmlessPrepperCount -= 1;
		%number = getRandom(1, 5);
        	if (%number == 1) {
        		serverPlay3D(VCV1,%this.getPosition());
        	} else if ($number == 2) {
        		serverPlay3D(VCV2,%this.getPosition());
        	} else if ($number == 3) {
        		serverPlay3D(VCV3,%this.getPosition());
        	} else if ($number == 4) {
        		serverPlay3D(VCV4,%this.getPosition());
        	} else {
        		serverPlay3D(VCV5,%this.getPosition());
        	}
        	%this.delete();
        	return;
    	}
    	%this.schedule($Pref::Rendermen::Prepperrate,explosiveLoop);
}
function AIPlayer::prepperLoop(%this) {
	//checks to see if we should kill the script
	//first, we need to check if we can kill
	$prepperinmap = true;//to make sure that this is running and not cause dups
	if(%this.PShrineCheck()) {
		%number = getRandom(1, 5);
        	if (%number == 1) {
        		serverPlay3D(VCV1,%this.getPosition());
        	} else if ($number == 2) {
        		serverPlay3D(VCV2,%this.getPosition());
        	} else if ($number == 3) {
        		serverPlay3D(VCV3,%this.getPosition());
        	} else if ($number == 4) {
        		serverPlay3D(VCV4,%this.getPosition());
        	} else {
        		serverPlay3D(VCV5,%this.getPosition());
        	}
        	$prepperinmap = false;
        	%this.delete();
        	return "SHRINE";
    	}
	//announce("H:" SPC %this.getDamageLevel());
	if (%this.getDamageLevel() >= 100) {
		//announce("Defeated");
		%ch = getRandom(1,10);
		if (%ch == 1) {
    			serverPlay3D(VCD1,%this.getPosition());
    		} else if (%ch == 5) {
    			serverPlay3D(VCD2,%this.getPosition());
    		}
		%this.delete();
        	$BSD_Support_Delete = schedule(6000, 0, removePrepper);//this is so that it doesn't spawn in right away
		return;
	} else {
		//announce("NO");
		//announce("Dmg:" SPC %this.getDamageLevel());
	}
	//%this.setWhiteOut(Point.Subtract(%this.getPosition(), %this.client.getPosition()).Length/11);
    	if($rendermancleanup == 1) { //clean up?
        	$prepperinmap = false;
		%number = getRandom(1, 5);
        	if (%number == 1) {
        		serverPlay3D(VCV1,%this.getPosition());
        	} else if ($number == 2) {
        		serverPlay3D(VCV2,%this.getPosition());
        	} else if ($number == 3) {
        		serverPlay3D(VCV3,%this.getPosition());
        	} else if ($number == 4) {
        		serverPlay3D(VCV4,%this.getPosition());
        	} else {
        		serverPlay3D(VCV5,%this.getPosition());
        	}
		%this.delete();
        	return;
    	}
    	%this.schedule($Pref::Rendermen::Prepperrate,prepperLoop);
}
function removePrepper() {
	$prepperinmap = false;
}
//end of life things
function angeryPrepper(%this) {
	if (%this.getDamageLevel() >= 100) {
		return;
	}
	$harmlessPrepperCount -= 1;
	for (%i = 0; %i <=3; %i += 1) {
		%proj = new Projectile() {
			datablock = "tankshellProjectile";
			initialPosition = %this.getPosition();
		};
		%proj.explode();//explode!?!?!
	}
	%this.delete();
}
function deleteharmlessPrepper(%this) {
	if (%this.getDamageLevel >= 100) {
		return;
	}
	$harmlessPrepperCount -= 1;
	HarmlessIdle(%this);
	if(!isObject(%this))
		return;
	%this.delete();
}

function deletePrepper(%this) {
	$prepperinmap = false;
	if(!isObject(%this)) {
		return;
	}
	if (VectorDist(%this.getPosition(),%this.player.getPosition()) <= 11) {
                if (getRandom(0,1) == 1) {
                	%this.player.client.play2D(PlayerDeath2);
                } else {
                	%this.player.client.play2D(PlayerDeath);
                }
                %this.setWhiteOut(0);
                hideAllNodes(%this.player);
		%this.player.kill();
		announce("was slain ...rather harshly...");//cant exactly make a custom kill feed thing. If you know, please tell me or make the change here
	} else {
		//announce("target was not within distance:");
		//announce("My  loc:" SPC %this.getPosition());
		//announce("His loc:" SPC %this.player.getPosition());
	}
	serverPlay3D(prepperGone, %this.getPosition());
	%this.delete();
}
//stolen functions: (added a p at the start so that we dont confuse modified functions with the renderman ai)
function AIPlayer::PShrineCheck(%this)
{
	InitContainerRadiusSearch(%this.getPosition(),20,$TypeMasks::FxBrickObjectType);
   	while((%targetObject=containerSearchNext()) !$= 0) {
        	if(%targetObject.getDatablock().getName() $= "brickRenderSafe") {
            		return 1;
        	}
    	}
    	return 0;
}
