package AI;

import objects.GameData;

public class AITest {
	static AIMaster AI;
	public static void main(String[] args) {
		GameData.Setup();
		AI = new AIMaster();
		AI.Test();
	}
}
 