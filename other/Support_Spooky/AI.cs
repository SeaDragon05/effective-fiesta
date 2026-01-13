// Renderman AI //

// OUTLINE
// >Rendermen start by finding a "target". #
// >They stalk the targeted player. #
// >Sometimes their target will change #
// >Up to five rendermen can be on the map at the time #
// >Rendermen sometimes try to teleport closer to the target, but can't teleport in front of them. #
// >Upon getting very close to the target (Right behind them) they will make a sound. #
// >Looking at a renderman that is very close kills the player #
// >Looking at one that is far away will kill over time #
// >If a renderman loses it's target it ceases to exist. #
// >Suddenly catching sight of a close up renderman will play a jumpscare sound. #
// >Rendermen can't move when they're being observed by players. #
// >Rendermen not visible to the player indoors will play ambient sounds. #

// first, checks
function AIPlayer::rendermanCanSeeObject(%player,%object) // this time, %player is renderman & %object is it's target
{
	if(!isObject(%object))
	{
		//announce("Object doesn't exist!");
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
		if(%resultDegrees >= %eyeDegrees - 50 && %resultDegrees <= %eyeDegrees + 50)
			//announce("Should be returning 1 right here!");
			return 1;
	}
	//No object hit, or not the target object, return 0/false
	//announce("No object hit, or not the target object, return 0/false");
	return 0;
}

function AIPlayer::ShrineCheck(%this)
{
	InitContainerRadiusSearch(%this.getPosition(),20,$TypeMasks::FxBrickObjectType);
	while((%targetObject=containerSearchNext()) !$= 0) {
        	if(%targetObject.getDatablock().getName() $= "brickRenderSafe") {
			return 1;
        	}
	}
	return 0;
}

function AIPlayer::getPosInFrontOfRenderman(%this,%dist)
{
	return vectorAdd(%this.getEyePoint(),vectorScale(%this.getEyeVector(),%dist));
}

function Player::getPosInFrontOfPlayer(%this,%dist)
{
    	return vectorAdd(%this.getEyePoint(),vectorScale(%this.getEyeVector(),%dist));
}

function AIPlayer::getRendermanTarget(%this) { // this is how rendermen find a "target".
    	%pos = %this.getPosition();
    
    	InitContainerRadiusSearch(%pos,1200,$TypeMasks::PlayerObjectType);
    	while((%targetObject=containerSearchNext()) !$= 0) {
        	if(isObject(%targetObject.client)) { // That IS a player, right? This isn't the time for chasing your friends about.
            		return %targetObject; // Set the found player as the target
        	}
    	}
   	return 0;
}   

function AIPlayer::rendermanLoop(%this) {
   	// Should we still be around?
    	if(%this.RendermanLifetime <= 0 || $Pref::Rendermen::Enabled == 0) {
        	%this.selfdestruct = 1; // we're not going to be around for much longer but we can't just vanish in front of someone
    	} else {
        	%this.RendermanLifetime = %this.RendermanLifetime-1;
    	}
    
    	if($rendermancleanup == 1) { //clean up?
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
        	removeRenderman(%this);
    	}
    	
    	// Do we have a target? If not get one
    	if(%this.RendermanTarget == 0) {
        	%this.RendermanTarget = %this.getRendermanTarget();
        	//announce(%victumcl.name SPC ", Im cOMeiNg tO GeT YOu!");//why not play a sound?
        	//serverPlay3D(Hunt,%this.getPosition());
        	%number = getRandom(1, 3);
        	if (%number == 1) {
        		%this.RendermanTarget.client.play2D(VCH1);
        	} else if ($number == 2) {
        		%this.RendermanTarget.client.play2D(VCH2);
        	} else {
        		%this.RendermanTarget.client.play2D(VCH3);
        	}
        	//find anything?
        	if (%this.RendermanTarget == 0) {
            		removeRenderman(%this); // can't find a target so despawns
            		return "NOTARG";
        	}
    	}
    	// now that we have a target...
    	// do we see him?
    	if(%this.rendermanCanSeeObject(%this.RendermanTarget) == 1)  {
        	%targinplainview = 1;
    	} else {
        	%targinplainview = 0;
    	}
    	// is he alive/still about?
    	if(!isObject(%this.RendermanTarget) || %this.RendermanTarget.safe == 1) {
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
        	removeRenderman(%this); // target is lost so renderman despawns
        	return "TLOST";
    	}
    
    	if(%this.shrineCheck()) { // rendermen cannot go within 20 TUs of a shrine without dying
        	//serverPlay3D(BotVanish,%this.getPosition());
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
        	removeRenderman(%this);
        	return "SHRINE";
    	}
        
    	%this.setAimObject(%this.RendermanTarget); // face him
    	// can anyone see us?
    	%pos = %this.getPosition();
    	InitContainerRadiusSearch(%pos,9999,$TypeMasks::PlayerObjectType);
   	while((%targetObject=containerSearchNext()) !$= 0) {
    		if(isObject(%targetObject.client)) {
        		if(getWord(%targetObject.canSeeRender,0) $= %this || getWord(%targetObject.canSeeRender,1) $= %this || getWord(%targetObject.canSeeRender,2) $= %this || getWord(%targetObject.canSeeRender,3) $= %this || getWord(%targetObject.canSeeRender,4) $= %this || getWord(%targetObject.canSeeRender,5) $= %this) { // this line is rather long, isn't it?
                		// this means they can
                		%this.schedule(100,rendermanLoop); // so we wait,
                		%this.setAimObject(%targetObject); // and face him
                		if(getrandom(1,30) == 3) { //possibly change him to your target for this (If he already is this won't matter)
                    			%this.RendermanTarget = %targetObject;
                		} // woo unnecisary brackets
                		return "INVIEW";
            		}
		}
	}
    	// nobody sees us.
    	// SELF DESTRUCT (If appropriate)
    	if(%this.selfdestruct == 1) {//Why not call a seperate function for this?
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
        	removeRenderman(%this);
        	return "SELFDESTRUCT";
    	}
    	//Are we a dead boi?
    	if (%this.getDamageLevel() >= 10) {
    		announce("A renderman was killed!");
    		%this.kill();
    		if (getRandom(1, 2) == 1) {
    			serverPlay2D(VCD1);//for all to hear the glory in the defeat of a renderman lmao
    		} else {
    			serverPlay2D(VCD2);//for all to hear the glory in the defeat of a renderman lmao
    		}
        	removeRenderman(%this);
        	return "KIA";
    	} else {
    		//announce("1");
    	}
    	// time to chase the target.
    
    	// are we close enough?
    	if(VectorDist(%this.getPosition(),%this.RendermanTarget.getPosition()) <= 7) {
        	%this.schedule(200,rendermanLoop);
        	if(%this.growled == 0 && %this.RendermanTarget.frozen == 0 && %targinplainview == 1) {
            		serverPlay3D(RenGrowl,%this.getPosition());
            		%this.RendermanTarget.prevDatablock = %this.RendermanTarget.getDatablock();
            		%this.RendermanTarget.setDatablock(PlayerRenderFrozenArmor); // freeze the target
            		%this.RendermanTarget.frozen = 1; // tell their loopcode they froze
           		if(!$Pref::Rendermen::Speed <= 100) { // with agro lower than "demonic", this state is easily escapable.
                		schedule(13000,0,removeRenderman,%this);
            		}
            		// show me your war face.
            		if($Pref::Rendermen::Faces == false) {
                		%this.setfacename("asciiterror");
            		} else {
                		%this.setfacename("rendermanface");
            		}
            		%this.RendermanTarget.detector = 3; // make their detector go haywire - that'll scare em'
            		//Causing too much sounds at once will overflow the sound buffer making important movements silent. Changed from 5 to 3
            		%creep = getRandom(1,6);
            		if(%creep == 1) {
                		ServerPlay2D(VB1);
            		} else if(%creep == 2) {
                		ServerPlay2D(VB2);
            		} else if (%creep == 3) {
                		ServerPlay2D(VB3);
            		} else if (%creep == 4) {
                		ServerPlay2D(VB4);
            		} else if (%creep == 5) {
                		ServerPlay2D(VB5);
            		} else {
                		ServerPlay2D(VB6);
            		}
        	}
        	%this.growled = 1;
        	//if(%targinplainview == 1) {
         	//   %this.RendermanTarget.client.play2D(QuietCaptureSound);
        	//}
        
        	return "CLSENOUGH";
    	}
    
    	%mf = %this.getPosInFrontOfRenderman(1); // the bot support in BL is terrible, so we'll slowly tp the bot towards the player instead
    	%mf = vectorSub(%mf,"0 0 2.880"); // compensate for render trying to be dolan
    					//whos dolan? DOLAN??!?!?!? WHAT?!??!
    
    	if(%this.justteleported == 1) { // did we do teleporty?
        	if(getWord(%this.RendermanTarget.canSeeRender,0) $= %this || getWord(%this.RendermanTarget.canSeeRender,1) $= %this || getWord(%this.RendermanTarget.canSeeRender,2) $= %this || getWord(%this.RendermanTarget.canSeeRender,3) $= %this || getWord(%this.RendermanTarget.canSeeRender,4) $= %this || getWord(%this.RendermanTarget.canSeeRender,5) $= %this) { // this line is rather long, isn't it?
            		// if we teleported somewhere that can be seen by the target,
            		%this.RendermanTeleport(); // try again - we'll have a specific function for jumpscaring later on
        	}
    	}
    
    	%this.justteleported = 0;
    	%this.setTransform(%mf);
    
    	if(getRandom(1,26) == 5) { // do teleporty?
    		//increased the chance of this happening: 56 to 26
        	%this.RendermanTeleport();
        	%this.justteleported = 1;
   	 }
    
    	if(getRandom(1,56) == 5) { // teleport right behind the player?
    		//increased the chance of this happening: 126 to 56
        	%this.RendermanSneak();
        	%this.justteleported = 1;
    	}
    	
    	// finally, rerun the AI code
    	if(%this.type $= "T") {
       		%this.schedule($Pref::Rendermen::Speed / 2,rendermanLoop);  // Type T moves twice as fast no matter what
    	} else {
        	if(%this.RendermanTarget.detlure == 1 || %this.RendermanTarget.lightlure == 1) {
            		%this.schedule($Pref::Rendermen::Speed,rendermanLoop); // normal renderman speed
        	} else { // if the player isn't attracting attention (by holding a detector, having a light etc...)
            		%this.schedule($Pref::Rendermen::Speed * 1.6,rendermanLoop); // slow renderman down slightly
        	}
    	}
    	return "SUCCESS";
}

function AIPlayer::RendermanTeleport(%this) { // teleport a renderman randomly
    	%pPos = %this.RendermanTarget.getPosition();
    	%tPos = vectorAdd(%pPos, getRandom(-80,80) SPC getRandom(-80,80) SPC 0 );
    	%this.setTransform(%tPos);
    	serverPlay3D(BotVanish,%tPos);
}

function AIPlayer::RendermanSneak(%this) { // another teleport, but appears right behind the player
    	%pPos = %this.RendermanTarget.getPosition();
    	%tPos = %this.getPosInFrontOfPlayer(-3); // using a minus number like this gives me a space directly BEHIND the player
    	%this.setTransform(%tPos);
    	// no sound
}

function FxDTSBrick::RenderHordeRestoreLight(%this) { // bricks with lights will break when the Horde event happens.
    	%this.setLight(%this.PrevLight);
}

// brick light check
function FxDTSBrick::RenderCheck(%this)
{
	InitContainerRadiusSearch(%this.getPosition(),70,$TypeMasks::PlayerObjectType);
    	%seen = 0;
    	while((%targetObject=containerSearchNext()) !$= 0) {
        	if(%targetObject.isRenderman == 1) {
            		if(%seen == 0) {
                		%seen = %targetObject;
            		}
        	}
    	}
    	return %seen;
}

function servercmdclearrendermen(%cl) {
    	if(%cl.isAdmin || %cl.isSuperAdmin) {
        	$rendermancleanup = 1;
        	announce(%cl.name SPC "cleared all Rendermen!");
        	$rendermeninmap = 0; // this command can be used to fix stupid players who killed the rendermen "by mistake" using hacks
        			//This issue has been fixed. Renderman are now killable.
        	ServerPlay2D(VCC);
        	schedule(1000,0,endrendermancleanup);
    	}
}

function endrendermancleanup() {
    	$rendermancleanup = 0;
    	$rendermeninmap = 0;//this fixes a bug that I found. Not sure where it came from, but the renderman count doesn't change
}
// special returns are for debugging with the trace function - type trace(1); in console to see all renderman AI output
