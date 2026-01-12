package port;

public interface RenderInterface {
	abstract void Render();
	abstract void SetMouthThroat(int mouth, int throat);

	abstract void ProcessFrames(int mem48);
	abstract void RenderSample(int[] mem66, int consonantFlag, int mem49);
	abstract int CreateTransitions();
}
