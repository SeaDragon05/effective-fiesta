// DATA //
datablock ParticleData(RendermanAParticle)
{
   dragCoefficient      = 5.0;
   gravityCoefficient   = 0.0;
   inheritedVelFactor   = 0.0;
   windCoefficient      = 0;
   constantAcceleration = 0.0;
   lifetimeMS           = 4000;
   lifetimeVarianceMS   = 1000;
   useInvAlpha          = false;
   textureName          = "./images/void2";//not as spooky
   //colors[0]     = "0.1 0.1 0.1 0.7";
   //colors[1]     = "1 0 0 0.8";
   //colors[2]     = "1 1 1 0.5";
   colors[0]     = "1 1 1 1";
   colors[1]     = "1 1 1 1";
   colors[2]     = "1 1 1 1";
   sizes[0]      = 1;
   sizes[1]      = 1.5;
   sizes[2]      = 1.3;
   times[0]      = 0;
   times[1]      = 0.5;
   times[2]      = 1.0;
};

datablock ParticleEmitterData(RendermanAEmitter)
{
   ejectionPeriodMS = 35;
   periodVarianceMS = 0;
   ejectionVelocity = 0.0;
   ejectionOffset   = 1.8;
   velocityVariance = 0.0;
   thetaMin         = 0;
   thetaMax         = 0;
   phiReferenceVel  = 0;
   phiVariance      = 0;
   overrideAdvance = false;
   lifeTimeMS = 100;
   particles = "RendermanAParticle";

   doFalloff = true; //if we do fall off with this emitter it ends up flickering, for most emitters you want this TRUE

   emitterNode = GenericEmitterNode;        //used when placed on a brick
   pointEmitterNode = TenthEmitterNode; //used when placed on a 1x1 brick

   //uiName = "Scary Face";
};

datablock ParticleEmitterData(VoidAEmitter)
{
	dragCoefficient      = 5.0;
   	gravityCoefficient   = 0.0;
  	 inheritedVelFactor   = 0.0;
   	windCoefficient      = 0;
   	constantAcceleration = 0.0;
   	lifetimeMS           = 4000;
   	lifetimeVarianceMS   = 1000;
   	useInvAlpha          = false;
   	textureName          = "./images/void1";//not as spooky
   	colors[0]     = "0.1 0.1 0.1 0.7";
   	colors[1]     = "1 0 0 0.8";
   	colors[2]     = "1 1 1 0.5";
   	sizes[0]      = 5;
   	sizes[1]      = 5.5;
   	sizes[2]      = 5.3;
   	times[0]      = 0;
   	times[1]      = 0.5;
   	times[2]      = 1.0;
};

datablock ExplosionData(RendermanAExplosion)
{
   lifeTimeMS = 2000;
   emitter[0] = RendermanAEmitter;
   soundProfile = Glimpse2;
};

datablock ProjectileData(RendermanAProjectile)
{
   explosion           = RendermanAExplosion;

   armingDelay         = 0;
   lifetime            = 50;
   explodeOnDeath		= true;

   //uiName = "Face Scary";
};

datablock ParticleData(RendermanBParticle)
{
   dragCoefficient      = 5.0;
   gravityCoefficient   = 0.0;
   inheritedVelFactor   = 0.0;
   windCoefficient      = 0;
   constantAcceleration = 0.0;
   lifetimeMS           = 4000;//increased the time the face appears
   lifetimeVarianceMS   = 1000;//1 second of variation
   useInvAlpha          = false;
   textureName          = "./images/hello1";//spooky
   colors[0]     = "0.1 0.1 0.1 0.7";
   colors[1]     = "1 0 0 0.8";
   colors[2]     = "1 1 1 0.5";
   sizes[0]      = 1;
   sizes[1]      = 1.5;
   sizes[2]      = 1.3;
   times[0]      = 0;
   times[1]      = 0.5;
   times[2]      = 1.0;
};

datablock ParticleEmitterData(RendermanBEmitter)
{
   ejectionPeriodMS = 35;
   periodVarianceMS = 0;
   ejectionVelocity = 0.0;
   ejectionOffset   = 1.8;
   velocityVariance = 0.0;
   thetaMin         = 0;
   thetaMax         = 0;
   phiReferenceVel  = 0;
   phiVariance      = 0;
   overrideAdvance = false;
   lifeTimeMS = 4000;
   particles = "RendermanBParticle";

   doFalloff = true; //if we do fall off with this emitter it ends up flickering, for most emitters you want this TRUE

   emitterNode = GenericEmitterNode;        //used when placed on a brick
   pointEmitterNode = TenthEmitterNode; //used when placed on a 1x1 brick

   //uiName = "Scary Face";
};

datablock ExplosionData(RendermanBExplosion)
{
   lifeTimeMS = 2000;
   emitter[0] = RendermanBEmitter;
   soundProfile = Glimpse2;
};

datablock ProjectileData(RendermanBProjectile)
{
   explosion           = RendermanBExplosion;

   armingDelay         = 0;
   lifetime            = 50;
   explodeOnDeath		= true;

   //uiName = "Face Scary";
};

///////////////////
// detector item //
///////////////////
datablock ItemData(RenderDetector)
{
	category = "Weapon";  // Mission editor category
	className = "Weapon"; // For inventory system

	 // Basic Item Properties
	shapeFile = "./models/detector.dts";
	rotate = false;
	mass = 1;
	density = 0.2;
	elasticity = 0.2;
	friction = 0.6;
	emap = true;

	//gui stuff
	uiName = "Renderman Detector";
	iconName = "./images/detector";
	doColorShift = false;
	colorShiftColor = "1.0 1.0 1.0 1.0";

	 // Dynamic properties defined by the scripts
	image = RenderDetectorImage;
	canDrop = true;
};

////////////////
//weapon image//
////////////////
datablock ShapeBaseImageData(RenderDetectorImage)
{
   // Basic Item properties
   shapeFile = "./models/detector.dts";
   emap = true;


   // Specify mount point & offset for 3rd person, and eye offset
   // for first person rendering.
   mountPoint = 0;
   offset = "0 0 0";
   eyeoffset = "0.7 1.2 -1";
   rotation = eulerToMatrix( "0 0 0" );

   // When firing from a point offset from the eye, muzzle correction
   // will adjust the muzzle vector to point to the eye LOS point.
   // Since this weapon doesn't actually fire from the muzzle point,
   // we need to turn this off.  
   correctMuzzleVector = true;

   // Add the WeaponImage namespace as a parent, WeaponImage namespace
   // provides some hooks into the inventory system.
   className = "WeaponImage";

   // Projectile && Ammo.
   item = RenderDetector;
   ammo = " ";
   projectile = gunProjectile; // the detector doesn't shoot bullets but we have to define some anyway
   projectileType = Projectile;

	casing = gunShellDebris;  // whatever
	shellExitDir        = "0.0 0.0 0.0";
	shellExitOffset     = "0 0 0";
	shellExitVariance   = 0.0;	
	shellVelocity       = 0.0;

   //melee particles shoot from eye node for consistancy
   melee = false;
   //raise your arm up or not
   armReady = true;

   doColorShift = false;
   colorShiftColor = RenderDetector.colorShiftColor;//"0.400 0.196 0 1.000";

   //casing = " ";


   // Images have a state system which controls how the animations
   // are run, which sounds are played, script callbacks, etc. This
   // state system is downloaded to the client so that clients can
   // predict state changes and animate accordingly.  The following
   // system supports basic ready->fire->reload transitions as
   // well as a no-ammo->dryfire idle state.

   // Initial start up state
	stateName[0]                     = "Activate";
	stateTimeoutValue[0]             = 0.0;
	stateTransitionOnTimeout[0]       = "Ready";
	stateSound[0]					= weaponSwitchSound;
};

///////////////////
//glitchgun item //
///////////////////
datablock ItemData(GlitchGun)
{
	category = "Weapon";  // Mission editor category
	className = "Weapon"; // For inventory system

	 // Basic Item Properties
	shapeFile = "./models/glitchgun.dts";
	rotate = false;
	mass = 1;
	density = 0.2;
	elasticity = 0.2;
	friction = 0.6;
	emap = true;

	//gui stuff
	uiName = "Renderman GlitchGun";
	iconName = " ";
	doColorShift = false;
	colorShiftColor = RenderDetector.colorShiftColor;

	 // Dynamic properties defined by the scripts
	image = GlitchGunImage;
	canDrop = true;
};

////////////////
//weapon image//
////////////////
datablock ShapeBaseImageData(GlitchGunImage)
{
   // Basic Item properties
   shapeFile = "./models/glitchgun.dts";
   emap = true;


   // Specify mount point & offset for 3rd person, and eye offset
   // for first person rendering.
   mountPoint = 0;
   offset = "0 0 0";
   eyeoffset = "0.7 1.2 -1";
   rotation = eulerToMatrix( "0 0 0" );

   // When firing from a point offset from the eye, muzzle correction
   // will adjust the muzzle vector to point to the eye LOS point.
   // Since this weapon doesn't actually fire from the muzzle point,
   // we need to turn this off.  
   correctMuzzleVector = true;

   // Add the WeaponImage namespace as a parent, WeaponImage namespace
   // provides some hooks into the inventory system.
   className = "WeaponImage";

   // Projectile && Ammo.
   item = GlitchGun;
   ammo = " ";
   projectile = gunProjectile; // the ggun doesn't shoot any real bullets either but again we have to give it some
   projectileType = Projectile;

	casing = gunShellDebris;  // whatever
	shellExitDir        = "0.0 0.0 0.0";
	shellExitOffset     = "0 0 0";
	shellExitVariance   = 0.0;	
	shellVelocity       = 0.0;

   //melee particles shoot from eye node for consistancy
   melee = false;
   //raise your arm up or not
   armReady = true;

   doColorShift = false;
   colorShiftColor = RenderDetector.colorShiftColor;//"0.400 0.196 0 1.000";

   //casing = " ";


   // Images have a state system which controls how the animations
   // are run, which sounds are played, script callbacks, etc. This
   // state system is downloaded to the client so that clients can
   // predict state changes and animate accordingly.  The following
   // system supports basic ready->fire->reload transitions as
   // well as a no-ammo->dryfire idle state.

   // Initial start up state
	stateName[0]                     = "Activate";
	stateTimeoutValue[0]             = 0.0;
	stateTransitionOnTimeout[0]       = "Ready";
	stateSound[0]					= weaponSwitchSound;
    
	stateName[1]                    = "Ready";
	stateTransitionOnTriggerDown[1] = "initiate";
	stateAllowImageChange[1]        = true;
    
    stateName[2]                = "initiate";
	stateScript[2]              = "onInit";
	stateTimeoutValue[2]        = 1;
	stateTransitionOnTimeout[2] = "Ready";
	stateAllowImageChange[2]    = false;
};

// Add a playertype to "freeze" the player
datablock PlayerData(PlayerRenderFrozenArmor : PlayerStandardArmor)
{
   runForce = 0;
   runEnergyDrain = 0;
   minRunEnergy = 0;
   maxForwardSpeed = 0;
   maxBackwardSpeed = 0;
   maxSideSpeed = 0;
   horizResistFactor = 1.0; // no moar slidies
   firstPersonOnly = 1; // no moar peakies
   thirdPersonOnly = 0;

   maxForwardCrouchSpeed = 0;
   maxBackwardCrouchSpeed = 0;
   maxSideCrouchSpeed = 0;

   jumpForce = 0; //8.3 * 90;
   jumpEnergyDrain = 0;
   minJumpEnergy = 0;
   jumpDelay = 0;

	minJetEnergy = 0;
	jetEnergyDrain = 0;
	canJet = 0;

	uiName = "Frozen Player";
	showEnergyBar = false;
};
////////////////////////////////
//// Audio and music stuff: ////
////////////////////////////////

//soo much when you want some variations lmao

//all audio is classified to the following formula:
//ABC#
//A is the type of sound class
//B is the sub type of the sound
//C is the class of the sound, if needed
//# is the number of which one it is

//ambient stuff:
datablock AudioProfile(AC1) {//ambient close 1
	filename = "./sounds/ambient/close/175205__minigunfiend__scary-creaking-knocking-wood.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AC2) {//ambient close 2
	filename = "./sounds/ambient/close/541638__db123infane__amboint-horror-background-music-noise.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AC3) {//ambient close 3
	filename = "./sounds/ambient/close/creak5.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AD1) {//ambient distant 1
	filename = "./sounds/ambient/distant/397319__screamstudio__ambient-one-shot.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AD2) {//ambient distant 2
	filename = "./sounds/ambient/distant/460459__fission9__ambient-rumble.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AD3) {//ambient distant 3
	filename = "./sounds/ambient/distant/ambient1.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AD4) {//ambient distant 4
	filename = "./sounds/ambient/distant/creak4.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AL1) {//ambient loud 1
	filename = "./sounds/ambient/loud/ambient4.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AL2) {//ambient loud 2
	filename = "./sounds/ambient/loud/creak1.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AL3) {//ambient loud 3
	filename = "./sounds/ambient/loud/creak3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AO1) {//ambient other/etc 1
	filename = "./sounds/ambient/other/414266__patricklieberkind__ambient-horror-logo.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AO2) {//ambient other/etc 2
	filename = "./sounds/ambient/other/528484__audio-dread__high-ambient-horror-music.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AO3) {//ambient other/etc 3
	filename = "./sounds/ambient/other/628252__bloodpixelhero__horror-ambient.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AO4) {//ambient other/etc 4
	filename = "./sounds/ambient/other/ambient3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AO5) {//ambient other/etc 5
	filename = "./sounds/ambient/other/creak2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AW1) {//ambient wind 1 SCREAMS LMAO
	filename = "./sounds/ambient/wind/397091__janbezouska__soft-tonal-wind-01.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AW2) {//ambient wind 2
	filename = "./sounds/ambient/wind/ambient5.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(AW3) {//ambient wind 3
	filename = "./sounds/ambient/wind/ambient6.wav";
	description = AudioClosest3d;
	preload = false;
};
//common sounds:

datablock AudioProfile(CB1) {//common boom 1
	filename = "./sounds/common/boom.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(CB2) {//common boom 2
	filename = "./sounds/common/boom2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(CB3) {//common boom 3
	filename = "./sounds/common/boom3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(CB4) {//common boom 4
	filename = "./sounds/common/boom4.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(CB5) {//common boom 5
	filename = "./sounds/common/boom5.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(PlayerDeath) {//common Death
	filename = "./sounds/common/playertaken.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(PlayerDeath2) {//common Death
	filename = "./sounds/common/playertakenlong.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(PrepperLaugh) {//preppers laugh
	filename = "./sounds/common/prepper.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(PrepperLaugh2) {//preppers laugh
	filename = "./sounds/common/prepper2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(PrepperGone) {//common Death
	filename = "./sounds/common/prepperGone.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(CBQ) {//common boom quiet
	filename = "./sounds/common/quietboom.wav";
	description = AudioClosest3d;
	preload = false;
};

//horror stuff:

datablock AudioProfile(facesoond) {//face that appears
	filename = "./sounds/horror/appearingface.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(HJ1) {//horror jumpscare 1
	filename = "./sounds/horror/HESRIGHTTHERE.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(HJ2) {//horror jumpscare 2
	filename = "./sounds/horror/HESRIGHTTHERE2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(HJ3) {//horror jumpscare 3
	filename = "./sounds/horror/HESRIGHTTHERE3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(HJ4) {//horror jumpscare 4
	filename = "./sounds/horror/HESRIGHTTHERE4.wav";
	description = AudioClosest3d;
	preload = false;
};

//items (LMAO I DIDN"T INCLUE THAT ONE SOUND IN HORROR!!!LMALMAOMLAOMOAMLA)

datablock AudioProfile(Detector) {//Item detect 1
	filename = "./sounds/items/detectorSound.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(preppertone) {//Item detect 2
	filename = "./sounds/items/preppertone.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(GlitchGunSound) {//Item fire 1
	filename = "./sounds/items/glitchgunfire.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(Glimpse) {//annoying sound
	filename = "./sounds/horror/glimpse.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(RenderDeath) {//annoying sound
	filename = "./sounds/horror/renderdeath.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(glitch1) {//annoying sound
	filename = "./sounds/ambient/other/glitch.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(glitch2) {//annoying sound
	filename = "./sounds/ambient/other/glitch2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(void1) {//void opening sound
	filename = "./sounds/common/rendermanspawn1.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(void2) {//void opening sound
	filename = "./sounds/common/rendermanspawn2.wav";
	description = AudioClosest3d;
	preload = false;
};


//music (THERES SO MUCH LMAO)

datablock AudioProfile(M11) {//Music 1 (section) 1 (first one)
	filename = "./sounds/music/1/horrortexture2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(M12) {//Music 1 2 
	filename = "./sounds/music/1/horrortexture3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(M13) {//Music 1 3 
	filename = "./sounds/music/1/horrortexture5.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(M21) {//Music 2 1
	filename = "./sounds/music/2/mansion.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(M22) {//Music 2 2
	filename = "./sounds/music/2/horrortexture4.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(M23) {//Music 2 3
	filename = "./sounds/music/2/nightincastle.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(M31) {//Music 3 1
	filename = "./sounds/music/3/horrorhit1.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(M32) {//Music 3 2
	filename = "./sounds/music/3/ambient2.wav";
	description = AudioClosest3d;
	preload = false;
};

//voice (this took a loooooooong time)

datablock AudioProfile(VB1) {//voice behind 1
	filename = "./sounds/voice/behind/indoorgrowl.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VB2) {//voice behind 2
	filename = "./sounds/voice/behind/indoorgrowl2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VB3) {//voice behind 3
	filename = "./sounds/voice/behind/indoorgrowl3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VB4) {//voice behind 4
	filename = "./sounds/voice/behind/indoorgrowl4.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VB5) {//voice behind 5
	filename = "./sounds/voice/behind/indoorgrowl5.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VB6) {//voice behind 6
	filename = "./sounds/voice/behind/indoorgrowl6.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCV1) {//voice common vanish 1
	filename = "./sounds/voice/common/botvanish.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCV2) {//voice common vanish 2
	filename = "./sounds/voice/common/botvanish2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCV3) {//voice common vanish 3
	filename = "./sounds/voice/common/botvanish3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCV4) {//voice common vanish 4
	filename = "./sounds/voice/common/botvanish3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCV5) {//voice common vanish 5
	filename = "./sounds/voice/common/botvanish5.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCC) {//voice common cleared
	filename = "./sounds/voice/common/cleared.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCF1) {//voice common found 1
	filename = "./sounds/voice/common/foundyou.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCF2) {//voice common found 2
	filename = "./sounds/voice/common/foundyou2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCF3) {//voice common found 3
	filename = "./sounds/voice/common/foundyou3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCF4) {//voice common found 4
	filename = "./sounds/voice/common/foundyou4.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCH1) {//voice common hunt 1
	filename = "./sounds/voice/common/hunt.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCH2) {//voice common hunt 2
	filename = "./sounds/voice/common/hunt2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCH3) {//voice common hunt 3
	filename = "./sounds/voice/common/hunt3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCD1) {//voice common death 1
	filename = "./sounds/voice/common/renderdeath.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCD2) {//voice common death 2
	filename = "./sounds/voice/common/renderdeath2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCD3) {//voice common death 3
	filename = "./sounds/voice/common/renderdeath3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCD4) {//voice common death 4
	filename = "./sounds/voice/common/renderdeath4.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI1) {//voice common idle 1
	filename = "./sounds/voice/idle/idle.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI2) {//voice common idle2
	filename = "./sounds/voice/idle/idle2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI3) {//voice common idle3
	filename = "./sounds/voice/idle/idle3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI4) {//voice common idle4
	filename = "./sounds/voice/idle/idle4.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI5) {//voice common idle5
	filename = "./sounds/voice/idle/idle5.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI6) {//voice common idle6
	filename = "./sounds/voice/idle/idle6.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI7) {//voice common idle7
	filename = "./sounds/voice/idle/idle7.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI8) {//voice common idle8
	filename = "./sounds/voice/idle/idle8.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI9) {//voice common idle9
	filename = "./sounds/voice/idle/idle9.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI10) {//voice common idle10
	filename = "./sounds/voice/idle/idle10.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI11) {//voice common idle11
	filename = "./sounds/voice/idle/idle11.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI12) {//voice common idle12
	filename = "./sounds/voice/idle/idle12.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI13) {//voice common idle13
	filename = "./sounds/voice/idle/idle13.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI14) {//voice common idle14
	filename = "./sounds/voice/idle/idle14.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI15) {//voice common idle15
	filename = "./sounds/voice/idle/idle15.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI16) {//voice common idle16
	filename = "./sounds/voice/idle/idle16.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI17) {//voice common idle17
	filename = "./sounds/voice/idle/idle18.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI18) {//voice common idle18
	filename = "./sounds/voice/idle/idle18.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI19) {//voice common idle19
	filename = "./sounds/voice/idle/idle19.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCI20) {//voice common idle20
	filename = "./sounds/voice/idle/idle20.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCD1) {//voice common detector
	filename = "./sounds/voice/detector/voiddetected1.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCD2) {//voice common detector
	filename = "./sounds/voice/detector/voiddetected2.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCD3) {//voice common detector
	filename = "./sounds/voice/detector/voiddetected3.wav";
	description = AudioClosest3d;
	preload = false;
};

datablock AudioProfile(VCR1) {//voice common random
	filename = "./sounds/voice/detector/random1.wav";
	description = AudioClosest3d;
	preload = false;
};


datablock AudioProfile(VCR2) {//voice common random
	filename = "./sounds/voice/detector/random2.wav";
	description = AudioClosest3d;
	preload = false;
};


datablock AudioProfile(VCR3) {//voice common random
	filename = "./sounds/voice/detector/random3.wav";
	description = AudioClosest3d;
	preload = false;
};


datablock AudioProfile(VCR4) {//voice common random
	filename = "./sounds/voice/detector/random4.wav";
	description = AudioClosest3d;
	preload = false;
};


datablock AudioProfile(VCR5) {//voice common random
	filename = "./sounds/voice/detector/random5.wav";
	description = AudioClosest3d;
	preload = false;
};


/////////////////////////////////
//// Bricks and other stuff: ////
/////////////////////////////////

datablock fxDtsBrickData(brickRenderSafe : brick2x2Data)
{
	uiName = "Anti-Renderman Shrine";
	category = "RenderMan";
	subCategory = "Renderman Mod";
	indestructable = 1;
};

datablock fxDtsBrickData(brickRenderLight : brick2x2fRoundData)
{
	uiName = "Anti-Renderman Light";
	category = "RenderMan";
	subCategory = "Renderman Mod";
	indestructable = 1;
};

datablock fxDtsBrickData(brickRenderDetect : brick1x1fData)
{
	uiName = "Detector Brick";
	category = "RenderMan";
	subCategory = "Renderman Mod";
	indestructable = 1;
};
