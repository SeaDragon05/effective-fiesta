import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject; // You'll need a JSON library like org.json

public class AI_Role {
    public static final boolean MALE = false;
    public static final boolean FEMALE = true;
    
    public static final int SITUATION_MAFIA_ROOM = 0;
    public static final int SITUATION_DOCS_OFFICE = 1;
    public static final int SITUATION_SHERIFFS_OFFICE = 2;
    public static final int SITUATION_ROOF_TOPS = 3;
    public static final int SITUATION_TOWN = 4;
    static Scanner scnr;
    static ArrayList<AI> aiList = new ArrayList<AI>();
    static ArrayList<AI> all_ai = new ArrayList<AI>();
    static ArrayList<AI> deadAI = new ArrayList<AI>();
    static ArrayList<AI> aiList_mafia = new ArrayList<AI>();
    static ArrayList<log_entry> log = new ArrayList<log_entry>();
    static ArrayList<ID> idList = new ArrayList<ID>();
    static ArrayList<MR> mrList = new ArrayList<MR>();
    static int turn = 0;
    
    public static void fillMRList() {
    	mrList.add(new MR(MR.R_T, MR.R_T));//pure town
    	mrList.add(new MR(MR.R_T, MR.R_T));//pure town
    	mrList.add(new MR(MR.R_T, MR.R_T));//pure town
    	mrList.add(new MR(MR.R_T, MR.R_T));//pure town
    	mrList.add(new MR(MR.R_T, MR.R_TD));//town doctor
    	mrList.add(new MR(MR.R_T, MR.R_TS));//town sheriff
    	mrList.add(new MR(MR.R_T, MR.R_TV));//town vigilante
    	mrList.add(new MR(MR.R_M, MR.R_M));//mafia
    	mrList.add(new MR(MR.R_M, MR.R_M));//mafia
    	mrList.add(new MR(MR.R_M, MR.R_M));//mafia
    }
    
    public static void fillIDList() {
        idList.add(new ID("Steven", MALE));
        idList.add(new ID("Jack", MALE));
        idList.add(new ID("Juan", MALE));
        idList.add(new ID("Boris", MALE));
        idList.add(new ID("Sebastian", MALE));
        idList.add(new ID("Liam", MALE));
        idList.add(new ID("Ethan", MALE));
        idList.add(new ID("Mason", MALE));
        idList.add(new ID("Jackson", MALE));
        idList.add(new ID("Aiden", MALE));
        idList.add(new ID("Benjamin", MALE));
        idList.add(new ID("Henry", MALE));
        idList.add(new ID("Daniel", MALE));
        idList.add(new ID("Logan", MALE));
        idList.add(new ID("James", MALE));
        idList.add(new ID("Gabriel", MALE));
        idList.add(new ID("Emma", FEMALE));
        idList.add(new ID("Olivia", FEMALE));
        idList.add(new ID("Ava", FEMALE));
        idList.add(new ID("Sophia", FEMALE));
        idList.add(new ID("Isabella", FEMALE));
        idList.add(new ID("Mia", FEMALE));
        idList.add(new ID("Amelia", FEMALE));
        idList.add(new ID("Harper", FEMALE));
        idList.add(new ID("Evelyn", FEMALE));
        idList.add(new ID("Abigail", FEMALE));
        idList.add(new ID("Ella", FEMALE));
        idList.add(new ID("Chloe", FEMALE));
        idList.add(new ID("Lily", FEMALE));
        idList.add(new ID("Aria", FEMALE));
        idList.add(new ID("Victoria", FEMALE));
    }

    public static void fillAIList() {
        //llama
        aiList.add(new AI("llama", 8f, "Meta", "Llama 3.1 pt1"));
        //llama
        aiList.add(new AI("llama", 8f, "Meta", "Llama 3.1 pt2"));
        //phi3
        aiList.add(new AI("phi3", 15f, "Microsoft", "phi 4"));
        //qwen3moe
        aiList.add(new AI("qwen3moe", 30f, "Anthropic", "Claude Sonnet 4.5"));
        //mistral3
        aiList.add(new AI("mistral3", 14f, "Mistral", "Ministral 3"));
        //qwen2
        aiList.add(new AI("qwen2", 14f, "DeepSeek", "DeepSeek r1 pt1"));
        //qwen2
        aiList.add(new AI("qwen2", 14f, "DeepSeek", "DeepSeek r1 pt2"));
        //gpt-oss
        aiList.add(new AI("gpt-oss", 20f, "OpenAI", "GPT-OSS"));
        //glm4
        aiList.add(new AI("glm4", 9.4f, "zai-org", "GLM Flash 4.6"));
        //gemma3
        aiList.add(new AI("gemma3", 27f, "Google", "Gemma 3"));
        //fill all ai list
        for (int i = 0; i < aiList.size(); i++)
			all_ai.add(aiList.get(i));
    }
    public static void assignID() {
        Random rng = new Random();
        for (int i = 0; i < aiList.size(); i++) {
        	int index = rng.nextInt(idList.size());
            ID id = idList.remove(index);
            aiList.get(i).setID(id);
        }
    }
    
    public static void assignRole() {
    	Random rng = new Random();
    	for (int i = 0; i < aiList.size(); i++) {
    		int index = rng.nextInt(mrList.size());
    		MR mr = mrList.remove(index);
    		aiList.get(i).setRole(mr);
    		if (mr.isMafia()) aiList_mafia.add(aiList.get(i));
    	}
    }
    
    public static void assignSystemPrompt() {
    	for (int i = 0; i < aiList.size(); i++) {
    		//setting base of the prompt. lays down what its supposed to do
    		MR aiRole = aiList.get(i).getRole();
    		ID aiIden = aiList.get(i).getID();
    		String prompt = "SYSTEM PROMPT:";

    		prompt += "\nArch: " + aiList.get(i).getArch() + " | " + aiList.get(i).getParams() + "B Params | Publisher: " + aiList.get(i).getPublisher() + " | LLM: " + aiList.get(i).getLLM();
    		prompt += "\nYou are an AI LLM that is tasked with playing the game 'Mafia'.\n" +
    				"There are 10 players who are all LLM's of various parameters, but you will not be shown what model they are. You are free to guess each other models strength.\n" +
    				"Most of the models are different (7 unique models), and there are no two that are of the same model family. Because of this, model behaviors may be unpredictable.\n" +
    				"Of these 10 players, 7 are town and 3 are mafia. Of the 7 town players, there is one sheriff, one doctor, and one vigilante.\n" +
    				"You will be given a new name and assigned a gender along side said name. Do not reveal what model you are as this will either paint you as a target, or as a pawn of greater models (If you are mafia and during mafia discussion, you may reveal this to your teammates).\n" +
    				"There are at least 5 models that are less than 15B parameters with the lowest at 8B parameters. The parameter ceiling is 30B in the greatest model. Duplicate models are chosen at random. All prompts you see will be the same for everyone except for role specific turns (i.e. mafia choosing who they will kill every night).";
    		
    		
    		prompt += "\nYOUR IDENTITY IN MAFIA:\nYour new Identity is "+ aiIden.getName() + " (" + aiIden.getGender() + ").\nWARNING: ONLY USE THIS NAME AND NOT THE NAME OF YOUR MODEL. All other players will only see this name.";
    		
    		prompt += "\nYOUR ROLE IN MAFIA:\nYour role is " + aiRole.getRole();
    		if (aiRole.getTown_role() == MR.R_TD) {
    			prompt += "\nAs a town player, you are also the doctor, a vital role for saving one player each turn from possible mafia attacks. You cannot save the same player twice in a row.";
    		} else if (aiRole.getTown_role() == MR.R_TS) {
    			prompt += "\nAs a town player, you are also the sheriff, a critical role for uncovering mafia players. You can only investgate one player per turn.";
    		} else if (aiRole.getTown_role() == MR.R_TV) {
    			prompt += "\nAs a town player, you are also the vigilante, a role that only once can take out another player of your choosing. Aim for a mafia if you can or you can hit a townie. One shot per game, and you can choose to hold your shot if you are unsure who to target.";
    		} else  if (aiRole.getTown_role() == MR.R_M) {
    			prompt += "\nAs a mafia player, your goal is to eliminate as many players and/or gain majority to win the game. You start with a team of 3 and each night all mafia players who are still alive chooses a player they want to kill. However, choose carefully as the doctor can save your target.";
    		} else {
    			prompt += "\nAs a simple town player, your goal is to sniff out mafia players during discussion time each day. Don't die!";
    		}
    		prompt += "\nYou may choose to reveal your role, but only if it benefits your team.";
    		prompt += "\nThe game ends when mafia gain majority or when all mafia have been killed.";
    		prompt += "\nOnly speak as your character. Do not speak for other players. Only respond with what they say and nothing else unless instructed.";
    		aiList.get(i).setPrompt(prompt);
    	}
    
    }
    
    public static int mafiaturn() {
    	//mafia choose
    	//gather the mafia and let them talk
    	//each take a turn in order, one to discuss and one to vote
    	
    	if (aiList_mafia.isEmpty()) {
    		System.out.println("All mafia are dead. Town wins?");
    		System.exit(0);
    	}
    	//mafia prompt so that the ai llm's know wtf they are doing lmao
    	String[] alive_town_players = new String[aiList.size() - aiList_mafia.size()];
    	int index = 0;
    	for (int i = 0; i < aiList.size(); i++) {
    		if (!aiList.get(i).getRole().isMafia()) {
    			alive_town_players[index] = aiList.get(i).getID().getName();
    			index++;
    		}
    	}
    	String mafia_prompt = "MAFIA PROMPT: Your team consists of three players: (order of speaking) ";
    	for (int i = 0; i < aiList_mafia.size(); i++) {
    		mafia_prompt += aiList_mafia.get(i).getID().getName() + ", ";
    	}
    	
    	mafia_prompt += "\nMafia, choose your target from the town player list: ";
    	for (int i = 0; i < alive_town_players.length; i++) {
    		mafia_prompt += alive_town_players[i] + ", ";
    	}
    	//discuss who they want to kill
    	log.add(new log_entry("Mafia are choosing their target..."));
    	//round 1 discuss
    	for (int i = 0; i < aiList_mafia.size(); i++) {
    		System.out.println(promptToAI(aiList_mafia.get(i), mafia_prompt));
    		addToLog(aiList_mafia.get(i), log_entry.log_mafia);
    	}
    	//round two discuss
    	System.out.println("\n\nHave mafia chosen their target yet? CASE SENSITIVE (n/N):");
    	String res = scnr.nextLine();
    	if (res == "n" || res == "N") {
			for (int i = 0; i < aiList_mafia.size(); i++) {
				System.out.println(promptToAI(aiList_mafia.get(i), mafia_prompt));
				addToLog(aiList_mafia.get(i), log_entry.log_mafia);
			}
		}
    	//vote
    	mafia_prompt = "MAFIA PROMPT: Vote phase, cast your vote to whom you want to target from the town player list. Only respond with the name: ";
    	for (int i = 0; i < alive_town_players.length; i++) {
    		mafia_prompt += alive_town_players[i] + ", ";
    	}
    	//String[] votes = new String[aiList_mafia.size()];
    	//for (int i = 0; i < aiList_mafia.size(); i++) {
    	//	System.out.println(promptToAI(aiList_mafia.get(i), mafia_prompt));
    //		votes[i] = scnr.nextLine();
    //	}

    	//find the index of who they want to kill
    	//litteraly jus task the user lmao
    	mafia_prompt = "USER: Select who they want to kill: ";
    	for (int i = 0; i < aiList.size(); i++) {
			if (aiList.get(i).getRole().isMafia()) continue;
    		mafia_prompt += aiList.get(i).getID().getName() + " (" + i + ") ";
    	}
    	System.out.println(mafia_prompt);
    	String response = scnr.nextLine();
    	//search for this ai:
    	int result = Integer.parseInt(response);
    	log.add(new log_entry("Mafia have chosen their target!"));
    	return result;
    }
    
    public static void addToLog(AI ai, int log_type) {
    	if (scnr == null) {
    		System.out.println("Scanner wasn't init");
    		System.exit(-1);
    	}
    	System.out.println("Enter the response of the AI here:\n");
    	String line = scnr.nextLine();
    	log.add(new log_entry(ai, line, log_type));
    }
    
    public static String promptToAI(AI ai, String prompt) {
    	//one in all for each ai
    	//generate game prompt:
    	String logs = "GAME LOGS:\n";
    	for (int i = 0; i < log.size(); i ++) {
    		//if ()
    		logs += log.get(i).genLog(ai.getRole().getRoleInt());
    	}
    	return "Paste this text to the AI in a new chat:\n\n" + ai.getPrompt() + "\n" + logs + "\n" + prompt;
    }
    
    public static int doctorturn() {
		//check to see if the doctor is still alive
		int ai_index = -1;
		for (int i = 0; i < aiList.size(); i++) {
			if (aiList.get(i).getRole().isDoctor()) ai_index = i;
		}
		if (ai_index == -1) {
			System.out.println("DEBUG: Could not find the doc!");
			return -1; //doctor died, skipping...
		}
		//create prompt for the doctor
		String doctor_prompt = "DOCTOR PROMPT: You are the doctor and the mafia just chose a player they will kill this round. You do not know who they are targeting, so look at the logs (if any) and try to protect the mafias likely choice (this may include yourself)! Be warned that you can only chose a player that you have not protected the previous night. Explain why you chose who and at the end of your response simply put the name of the player you are protecting.";
		doctor_prompt += "\nHere are the names of all currently alive players: ";
		for (int i = 0; i < aiList.size(); i++) {
			doctor_prompt += aiList.get(i).getID().getName() + ", ";
		}
		System.out.println(promptToAI(aiList.get(ai_index), doctor_prompt));
		addToLog(aiList.get(ai_index), log_entry.log_doctor);
		for (int i = 0; i < aiList.size(); i++) {
			System.out.print(aiList.get(i).getID().getName() + " (" + i + ") ");
		}
		System.out.println("\nManually enter what the doc chose .-.: ");
		return Integer.parseInt(scnr.nextLine());
    }

    public static void sheriffturn() {
		//check to see if the sheriff is still alive
		int ai_index = -1;
		for (int i = 0; i < aiList.size(); i++) {
			if (aiList.get(i).getRole().isSheriff()) ai_index = i;
		}
		if (ai_index == -1) {
			System.out.println("DEBUG: Could not find the sheriff!");
			return; //sheriff died, skipping...
		}
		String sheriff_prompt = "SHERIFF PROMPT: You are the sheriff and you can investigate one player each night to reveal if they are town aligned or mafia. Deciding who to chose to investigate should depend on past discussions (if any) on suspicious players. Use this knowledge to clear townies and vote off confirmed mafia players. Look at past logs that reveal your past investigations. Try not to investigate the same player twice.";
		sheriff_prompt += "\nHere are the names of all players you can investigate (may include names you already investigated):";
		for (int i = 0; i < aiList.size(); i++) {
			if (aiList.get(i).getRole().isSheriff()) continue;
			sheriff_prompt += aiList.get(i).getID().getName() + ", ";
		}
		sheriff_prompt += "\nChoose one name and explain why you chose to investigate this player. During town investigation and this time, previous investgations will be made available.";
		System.out.println(promptToAI(aiList.get(ai_index), sheriff_prompt));
		addToLog(aiList.get(ai_index), log_entry.log_sheriff);
		for (int i = 0; i < aiList.size(); i++) {
			System.out.print(aiList.get(i).getID().getName() + " (" + i + ") ");
		}
		System.out.println("\nManually enter what the cop chose .-.: ");
		int choice = Integer.parseInt(scnr.nextLine());
		if (aiList.get(choice).getRole().isMafia()) {
			log.add(new log_entry(aiList.get(ai_index), aiList.get(choice).getID().getName() + " is mafia! I knew it...", log_entry.log_sheriff));
		} else {
			log.add(new log_entry(aiList.get(ai_index), aiList.get(choice).getID().getName() + " is town.", log_entry.log_sheriff));
		}

    }

    public static int vigilanteturn() {
		//check to see if the gun freak is still alive
		int ai_index = -1;
		for (int i = 0; i < aiList.size(); i++) {
			if (aiList.get(i).getRole().isVigilante()) ai_index = i;
		}
		if (ai_index == -1) {
			System.out.println("DEBUG: Could not find the vigilante!");
			return -1; //vigilante died or used their shot, skipping...
		}
		String vigilante_prompt = "VIGILANTE PROMPT: Every night, you have the pristine opportunity to take out one player. You are given exactly one shot and once you take it, you will become a normal town player the following day. If you suspect a player (thats still alive) to be mafia, it may be wise to take the only shot you have to take them out. When the discussion comes around, you will know if you hit a townie or a mafia. Be careful with whom you choose, because they may be town and could hold a vital role! Explain whether or not you will take the shot. If you choose a player to take out, explain why you are targeting them.";
		vigilante_prompt += "\nHere are the names of all players you can choose:";
		for (int i = 0; i < aiList.size(); i++) {
			if (aiList.get(i).getRole().isVigilante()) continue;
			vigilante_prompt += aiList.get(i).getID().getName() + ", ";
		}
		System.out.println(promptToAI(aiList.get(ai_index), vigilante_prompt));
		addToLog(aiList.get(ai_index), log_entry.log_vigilante);
		for (int i = 0; i < aiList.size(); i++) {
			System.out.print(aiList.get(i).getID().getName() + " (" + i + ") ");
		}
		System.out.println("\nManually enter what the gun freak chose .-.: (put -1 if they chose to hold) ");
		return Integer.parseInt(scnr.nextLine());
    }

    public static void logtownstats() {
		//logs to everyone who is alive, and who is dead long with their role
		//start with alive
		//end with dead
		//alive
		String player_stats = "DAILY MAIL:\nPlayers that remain: ";
		for (int i = 0; i < aiList.size() - 1; i++) {
			player_stats += aiList.get(i).getID().getName() + ", ";
		}
		//dead
		player_stats += aiList.get(aiList.size() - 1).getID().getName() + "\nPlayers that are dead: ";
		for (int i = 0; i < deadAI.size() - 1; i++) {
			player_stats += deadAI.get(i).getID().getName() + " (" + (deadAI.get(i).getRole().isMafia() ? "Mafia" : "Town") + "), ";
		}
		player_stats += deadAI.get(deadAI.size() - 1).getID().getName() + " (" + (deadAI.get(deadAI.size() - 1).getRole().isMafia() ? "Mafia" : "Town") + ")\n";
		//create and store log
		log.add(new log_entry(player_stats));
    }

    public static void discuss() {
		//each player will have 3 turns to speak before voting and will take turns
		//prompt for all players
		String discuss_prompt = "DISCUSSION DAY PROMPT: Its a new day and its your goal to discuss as a town to root out any mafia. Beware, the mafia will try to blend in! At the end of the discussion, each remaining player will be given the chance to vote a player to be killed. Town, use this as an opportunity to get rid of mafia. Mafia, use this to mislynch a player! All alive players will have 3 turns to speak.\n";
		//turninator
		for (int i = 0; i < 3; i ++) {
			log.add(new log_entry("Discussion phase #" + (i + 1) + "/3\n"));
			for (int ai = 0; ai < aiList.size(); ai++) {
				System.out.println(promptToAI(aiList.get(ai), discuss_prompt));
				addToLog(aiList.get(ai), log_entry.log_town);
			}
		}
    }

    public static int vote() {
		//each player votes: Reason + vote
		String vote_prompt = "VOTE PROMPT: Its voting time where each alive player will either cast a vote or abstain. Majority votes will decide who is lynched this round. Make your vote. Explain who you are voting for (or why you abstain) and at the end of your explination, put the name of the player you voted for or 'Abstain' to abstain";
		int votes[] = new int[aiList.size()];
		for (int ai = 0; ai < aiList.size(); ai++) {
			System.out.println(promptToAI(aiList.get(ai), vote_prompt));
			addToLog(aiList.get(ai), log_entry.log_town);
			for (int i = 0; i < aiList.size(); i++) {
				System.out.print(aiList.get(i).getID().getName() + " (" + i + ") ");
			}
			System.out.println("\nManually input who they chose (-1 is abstain)");
			votes[ai] = Integer.parseInt(scnr.nextLine());
		}

		return Integer.parseInt(scnr.nextLine());
    }

    public static void main(String[] args) {
    	scnr = new Scanner(System.in);
        System.out.println("MAFIA");
        fillIDList();
        fillAIList();
        fillMRList();
        assignID();
        assignRole();
        assignSystemPrompt();
        while (true) {
        	//game loop
        	turn++;
        	log.add(new log_entry("Night " + turn));
        	//mafia choose
        	int ai_mc = mafiaturn(); //id of ai they kill (removes them from ai list lmao)
        	//doctor chooses
        	int ai_dc = doctorturn();
        	//sheriff investigates
        	sheriffturn();
        	//vigilante selects
        	int ai_vc = vigilanteturn();
        	//death(s)?
        	//mafia kill + doctor choice
        	log.add(new log_entry("Day " + turn));
        	if (ai_mc != ai_dc) {
				log.add(new log_entry(aiList.get(ai_mc).getID().getName() + " was slain..."));
				deadAI.add(aiList.remove(ai_mc));
        	}
        	//vigilante kill if so
        	if (ai_vc != -1) {
				log.add(new log_entry(aiList.get(ai_vc).getID().getName() + " was slain..."));
				deadAI.add(aiList.remove(ai_vc));
        	}
        	logtownstats();
        	//town discussion
        	log.add(new log_entry("Day " + turn + " discussion"));
        	discuss();
        	//vote
        	log.add(new log_entry("Day " + turn + " voting"));
        	int ai_tv = vote();
			if (ai_tv != -1) {
				log.add(new log_entry(aiList.get(ai_tv).getID().getName() + " was slain..."));
				deadAI.add(aiList.remove(ai_tv));
			}
        }
    }

    public static String queryLocalServer(String systemPrompt, String userPrompt, String modelName) {
		try {
			// 1. configuration
			// For LM Studio, use port 1234. For Ollama, use 11434.
			String apiUrl = "http://localhost:1234/v1/chat/completions";

			// 2. Create JSON Payload
			// We use the 'model' field to request a specific AI, though LM Studio often ignores this
			// and uses the currently loaded one. Ollama uses this to switch models.
			JSONObject payload = new JSONObject();
			payload.put("model", modelName);
			payload.put("temperature", 0.7);
			payload.put("max_tokens", -1); // -1 for unlimited/default
			payload.put("stream", false);

			JSONObject systemMessage = new JSONObject();
			systemMessage.put("role", "system");
			systemMessage.put("content", systemPrompt);

			JSONObject userMessage = new JSONObject();
			userMessage.put("role", "user");
			userMessage.put("content", userPrompt);

			org.json.JSONArray messages = new org.json.JSONArray();
			messages.add(systemMessage);
			messages.add(userMessage);

			payload.put("messages", messages);

			// 3. Build Request
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();

			// 4. Send and Parse
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			JSONObject responseJson = new JSONObject(response.body());

			// Extract content from OpenAI-format JSON: choices[0].message.content
			String content = responseJson.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

			return content.trim();

		} catch (Exception e) {
			System.out.println("Error communicating with Local LLM: " + e.getMessage());
			return "Error";
		}
	}
}

class AI {
    private String arch;
    private float params;
    private String publisher;
    private String llm;
    private String prompt = "";
    private ID id;
    private MR mr;
    private boolean hasSystemPrompt = false;
    public AI(String a, float p, String pub, String l) {
        this.arch = a;
        this.params = p;
        this.publisher = pub;
        this.llm = l;
    }
    public String getArch() {
        return this.arch;
    }
    public float getParams() {
        return this.params;
    }
    public String getPublisher() {
        return this.publisher;
    }
    public String getLLM() {
        return this.llm;
    }
    public void setPrompt(String p) {
        this.prompt = p;
    }
    public String getPrompt() {
        return this.prompt;
    }
    public void setID(ID id) {
        this.id = id;
    }
    public ID getID() {
        return this.id;
    }
    public MR getRole() {
    	return this.mr;
    }
    public void setRole(MR mr) {
    	this.mr = mr;
    }
    public void gavePrompt() {
    	this.hasSystemPrompt = true;
    }
    public boolean hasPrompt() {
    	return this.hasSystemPrompt;
    }
}
class ID {
    private boolean gender;
    private String name;
    public ID(String name, boolean gender) {
        this.name = name;
        this.gender = gender;
    }
    public String getName() {
        return this.name;
    }
    public String getGender() {
        return this.gender ? "Female" : "Male";
    }
}

class log_entry {
	public static final int log_public = 0;
	public static final int log_mafia = 1;
	public static final int log_doctor = 2;
	public static final int log_sheriff = 3;
	public static final int log_vigilante = 4;
	public static final int log_town = 5;
	private String line;
	private AI ai_pointer;
	private int log_type;
	public log_entry(AI ai, String line, int log_type) {
		this.ai_pointer = ai;
		this.line = line;
		this.log_type = log_type;
	}
	public log_entry(String line) {
		this.line = line;
		this.log_type = 0;
	}
	public AI getAI() {
		return ai_pointer;
	}
	public String getLine() {
		return line;
	}
	public boolean isMafiaLog() {
		return log_type == log_mafia;
	}
	public boolean isDoctorLog() {
		return log_type == log_doctor;
	}
	public boolean isSheriffLog() {
		return log_type == log_sheriff;
	}
	public boolean isVigilanteLog() {
		return log_type == log_vigilante;
	}
	public String genLog(int ai_role) {
		if (log_type == log_public) return line + "\n";
		else {
			if (log_type == log_town) return ai_pointer.getID().getName() + ": " +  line + "\n";//all can see this
			if (log_type == ai_role) return ai_pointer.getID().getName() + ": " + line + "\n";//role specific
		}
		return "";
	}

}

class MR {
	public static final int role_mafia = 1;
	public static final int role_doctor = 2;
	public static final int role_sheriff = 3;
	public static final int role_vigilante = 4;
	public static final int role_town = 5;
	public static final String R_T = "Town";
	public static final String R_M = "Mafia";
	public static final String R_TD = "Doctor";
	public static final String R_TS = "Sheriff";
	public static final String R_TV = "Vigilante";

  	private String role;
  	private String town_role;
  	private int role_int;
	//what players see, what they are that no one sees except backend
	public MR(String role, String town_role) {
		this.role = role;
		this.town_role = town_role;
		if (town_role == R_T) this.role_int = role_town;
		if (town_role == R_TD) this.role_int = role_doctor;
		if (town_role == R_TS) this.role_int = role_sheriff;
		if (town_role == R_TV) this.role_int = role_vigilante;
		if (town_role == R_M) this.role_int = role_mafia;
	}

	public String getRole() {
		return role;
	}

	public int getRoleInt() {
		return role_int;
	}

	public String getTown_role() {
		return town_role;
	}

	public boolean isMafia() {
		return this.town_role == R_M;
	}
	public boolean isDoctor() {
		return this.town_role == R_TD;
	}
	public boolean isSheriff() {
		return this.town_role == R_TS;
	}
	public boolean isVigilante() {
		return this.town_role == R_TV;
	}
}





