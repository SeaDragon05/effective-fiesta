// Init RTB (Required mod)
$Pref::Rendermen::Faces = 0;

RTB_registerPref("Enabled", "Rendermen", "Pref::Rendermen::Enabled", "bool", "Support_Rendermenawe", 1, 0, 0); // implamented
//detector voice:
RTB_registerPref("Detector voice?", "Rendermen", "Pref::Rendermen::detectorVoice", "bool", "Support_Rendermenawe", 1, 0, 0);//AAAAAAAAAAAAAAAA
RTB_registerPref("Renderman Lights?", "Rendermen", "Pref::Rendermen::OwnLights", "bool", "Support_Rendermenawe", 1, 0, 0); // implamented
RTB_registerPref("Use music?", "Rendermen", "Pref::Rendermen::Music", "bool", "Support_Rendermenawe", 1, 0, 0);//added variable
RTB_registerPref("Renderman Agro", "Rendermen", "Pref::Rendermen::Speed","list Paranormal 250 Evil 150 Demonic 100 Insane 75 Nightmare 25","Support_Rendermenawe", 150, 0, 0); // implamented
RTB_registerPref("Renderman Spawnrate", "Rendermen", "Pref::Rendermen::Spawnrate","list Don't 1337 Unsafe 30 Dangerous 23 Frightening 19 Insane 13 Nightmare 7","Support_Rendermenawe", 19, 0, 0); // implamented
RTB_registerPref("Renderman Limit", "Rendermen", "Pref::Rendermen::Limit","int 1 100","Support_Rendermenawe", 20, 0, 0); // implamented 
RTB_registerPref("Renderman Cycle time (dev)", "Rendermen", "Pref::Rendermen::cycleTime","int 4000 8000","Support_Rendermenawe", 4000, 0, 0); // added variable
//this makes it so that when the map is dark, they come
RTB_registerPref("Should rendermen only spawn when its dark?", "Rendermen", "Pref::Rendermen::SpawnOnlyInDark", "bool", "Support_Rendermenawe", 1, 0, 0);//added variable
//should rendermen spawn using a void opening?
RTB_registerPref("Should rendermen *NOT PREPPER* use an opening in the void to spawn?", "Rendermen", "Pref::Rendermen::voidSpawn", "bool", "Support_Rendermenawe", 1, 0, 0);
//a fake perhaps? dono what to call it.
RTB_registerPref("Affect Map", "Rendermen", "Pref::Rendermen::Lights", "bool", "Support_Rendermenawe", 1, 0, 0); // buggy but implamented
//I modified the following function from change faces? to Use scary faces?
RTB_registerPref("Use scary faces?", "Rendermen", "Pref::Rendermen::Faces", "bool", "Support_Rendermenawe", 1, 0, 0);
RTB_registerPref("Admins always detect rendermen?", "Rendermen", "Pref::Rendermen::AdminAlwaysDetect", "bool", "Support_Rendermenawe", 0, 0, 0);

//prepper stuff
RTB_registerPref("Prepper Argo", "Rendermen", "Pref::Rendermen::ExplodeChance","list Safe 20 Dangerous 15 Euclid 10 Hazardous 7 Keter 2","Support_Rendermenawe", 150, 0, 0); // implamented
RTB_registerPref("Prepper Spawnrate", "Rendermen", "Pref::Rendermen::Prepperrate","list No 100 Paranormal 32 Dangerous 8 Euclid 4 Keter 2 Yes 1","Support_Rendermenawe", 19, 0, 0);
RTB_registerPref("Use prepper?", "Rendermen", "Pref::Rendermen::Prepper", "bool", "Support_Rendermenawe", 1, 0, 0);//added variable
RTB_registerPref("Can prepper kill?", "Rendermen", "Pref::Rendermen::Angeryprepper", "bool", "Support_Rendermenawe", 1, 0, 0);//added variable


// define main vars
$rendermeninmap = 0;
$prepperinmap = false;//for the detector because its weird seeing them without the ticking going off
$Pref::Renderman::cycleTime = 4000;//please don't remove this line unless you want chaos when starting up the server!
$Pref::Rendermen::Faces = 0;
$Pref::Rendermen::SpawnOnlyInDark = false;
$Pref::Rendermen::AdminAlwaysDetect = false;
$harmlessPrepperCount = 0;
//%Pref::Rendermen::Prepper;
$rendermancleanup = 0;
registerSpecialVar("GLOBAL","rendermen","$rendermeninmap"); // VCE: Use the replacer <var:global:rendermen> to access!

// load code
//exec("./Support_AdminEvents.cs"); bad code lmao
exec("./Functions.cs");
exec("./Datablocks.cs");
exec("./AI.cs");
exec("./PrepperAI.cs");
exec("./music.cs");

