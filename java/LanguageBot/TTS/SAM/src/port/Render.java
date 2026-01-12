package port;

public class Render {
	final static int PHONEME_PERIOD = 1;
	final static int PHONEME_QUESTION = 2;

	final static int RISING_INFLECTION = 1;
	final static int FALLING_INFLECTION = 255;

	static int[] Pitches = new int[256]; // tab43008

	static int[] frequency1 = new int[256];
	static int[] frequency2 = new int[256];
	static int[] frequency3 = new int[256];

	static int[] amplitude1 = new int[256];
	static int[] amplitude2 = new int[256];
	static int[] amplitude3 = new int[256];

	static int[] sampledConsonantFlag = new int[256]; // tab44800

	private static int trans(int a, int b) {
		return (int) (((a * b) / 256) * 2);
	}

	static int timetable[][] = { { 162, 167, 167, 127, 128 }, { 226, 60, 60, 0, 0 }, { 225, 60, 59, 0, 0 },
			{ 200, 0, 0, 54, 55 }, { 199, 0, 0, 54, 54 } };

	static int oldtimetableindex = 0;
	private static void Output(int index, int A) {
		Sam.bufferpos += timetable[oldtimetableindex][index];
		oldtimetableindex = index;
		// write a little bit in advance
		for (int k = 0; k < 5; k++) {
			try {
				Sam.buffer[(int) (Sam.bufferpos / Main.audioQualityStep + k)] = (byte) ((A & 15) * 6);
			} catch (Exception e) {}
		}
 	}

	private static int RenderVoicedSample(int hi, int off, int phase1) {
		do {
			off %= 256;
			int bit = 8;
			int sample = RenderData.sampleTable[hi + off];
			//sample %= 256;
			do {
				if ((sample & 128) != 0)
					Output(3, 26);
				else
					Output(4, 6);
				sample <<= 1;
			} while (--bit != 0);
			off++;
			phase1++;
			phase1 %= 256;
		} while (phase1 != 0);
		return off;
	}

	private static void RenderUnvoicedSample(int hi, int off, int mem53) {
		off %= 256;
		do {
			int bit = 8;
			int sample = RenderData.sampleTable[hi + off];
			do {
				if ((sample & 128) != 0)
					Output(2, 5);
				else
					Output(1, mem53);
				sample <<= 1;
			} while (--bit >= 0);
			off += 1;
			off %= 256;
		} while (off != 0);
	}
	
	private static int RenderSample(int mem66, int consonantFlag, int pitchLocation) {
		//System.out.println("Rendering sample...");
		// mem49 == current phoneme's index
		consonantFlag %= 256;
		// mask low three bits and subtract 1 get value to
		// convert 0 bits on unvoiced samples.
		int hibyte = (consonantFlag & 7) - 1;
		int pmem66 = mem66;
		// determine which offset to use from table { 0x18, 0x1A, 0x17, 0x17, 0x17 }
		// T, S, Z 0 0x18
		// CH, J, SH, ZH 1 0x1A
		// P, F*, V, TH, DH 2 0x17
		// /H 3 0x17
		// /X 4 0x17

		int hi = hibyte * 256;
		// voiced sample?
		int pitchl = consonantFlag & 248;
		if (pitchl == 0) {
			// voiced phoneme: Z*, ZH, V*, DH
			pitchl = Pitches[pitchLocation] >> 4;
			pmem66 = RenderVoicedSample(hi, mem66, pitchl ^ 255);
		} else {
			RenderUnvoicedSample(hi, pitchl ^ 255, RenderData.tab48426[hibyte]);

		}
		return pmem66;
	}

	private static void CreateFrames() {
		int X = 0;
	    int i = 0;
	    while(i < 256) {
	        // get the phoneme at the index
	        int phoneme = Sam.phonemeIndexOutput[i];
			int phase1;
			int phase2;
		
	        // if terminal phoneme, exit the loop
	        if (phoneme == 255) break;
		
	        if (phoneme == PHONEME_PERIOD)   AddInflection(RISING_INFLECTION, X);
	        else if (phoneme == PHONEME_QUESTION) AddInflection(FALLING_INFLECTION, X);

	        // get the stress amount (more stress = higher pitch)
	        phase1 = RenderData.tab47492[Sam.stressOutput[i] + 1];
		
	        // get number of frames to write
	        phase2 = Sam.phonemeLengthOutput[i];
		
	        // copy from the source to the frames list
	        do {
	            frequency1[X] = RenderData.modifiedFreq1data[phoneme];     // F1 frequency
	            frequency2[X] = RenderData.modifiedFreq2data[phoneme];     // F2 frequency
	            frequency3[X] = RenderData.modifiedFreq3data[phoneme];     // F3 frequency
	            amplitude1[X] = RenderData.modifiedAmpl1data[phoneme];     // F1 amplitude
	            amplitude2[X] = RenderData.modifiedAmpl2data[phoneme];     // F2 amplitude
	            amplitude3[X] = RenderData.modifiedAmpl3data[phoneme];     // F3 amplitude
	            sampledConsonantFlag[X] = RenderData.sampledConsonantFlags[phoneme];        // phoneme data for sampled consonants
	            
	            int newPitch = Sam.pitch + phase1;
	            Pitches[X] = (int) ((newPitch % 256));      // pitch
	            ++X;
	        } while(--phase2 != 0);
	        
	        ++i;
	    }
	}

// RESCALE AMPLITUDE
//
// Rescale volume from a linear scale to decibels.
//
	private static void RescaleAmplitude() {
		for (int i = 255; i >= 0; i--) {
			amplitude1[i] = RenderData.amplitudeRescale[amplitude1[i]];
			amplitude2[i] = RenderData.amplitudeRescale[amplitude2[i]];
			amplitude3[i] = RenderData.amplitudeRescale[amplitude3[i]];
		}
	}

// ASSIGN PITCH CONTOUR
//
// This subtracts the F1 frequency from the pitch to create a
// pitch contour. Without this, the output would be at a single
// pitch level (monotone).

	private static void AssignPitchContour() {
		for (int i = 0; i < 256; i++) {
			// subtract half the frequency of the formant 1.
			// this adds variety to the voice
			Pitches[i] -= (frequency1[i] >> 1);
		}
	}

	public static void RenderIt() {
		int t;
		if (Sam.phonemeIndexOutput[0] == 255) {
			return;// exit if no data
		}
		CreateFrames();
		t = CreateTransitions();
		if (!Sam.singmode) AssignPitchContour();
		RescaleAmplitude();

		if (Main.debug) {
			Debug.printOutput(sampledConsonantFlag, frequency1, frequency2, frequency3, amplitude1, amplitude2,
					amplitude3, Pitches);
		}

		ProcessFrames(t);

	}

	private static void AddInflection(int inflection, int pos) {
		int A;
		// store the location of the punctuation
		int end = pos;

		if (pos < 30) {
			pos = 0;
		} else {
			pos -= 30;
		}
		// FIXME: Explain this fix better, it's not obvious
		// ML : A =, fixes a problem with invalid pitch with '.'
		while ((A = Pitches[pos]) == 127)
			++pos;

		while (pos != end) {
			// add the inflection direction
			A += inflection;

			// set the inflection
			Pitches[pos] = A % 256;
			while ((++pos != end) && Pitches[pos] == 255)
				;
		}
	}
	// mouth formants (F1) 5..29
	private static final int mouthFormants5_29[] = { 0, 0, 0, 0, 0, 10, 14, 19, 24, 27, 23, 21, 16, 20, 14, 18, 14, 18, 18, 16, 13,
			15, 11, 18, 14, 11, 9, 6, 6, 6 };

	// throat formants (F2) 5..29
	private static final int throatFormants5_29[] = { 255, 255, 255, 255, 255, 84, 73, 67, 63, 40, 44, 31, 37, 45, 73, 49, 36, 30,
			51, 37, 29, 69, 24, 50, 30, 24, 83, 46, 54, 86, };

	// there must be no zeros in this 2 tables
	// formant 1 frequencies (mouth) 48..53
	private static final int mouthFormants48_53[] = { 20, 27, 40, 27, 18, 13 };

	// formant 2 frequencies (throat) 48..53
	private static final int throatFormants48_53[] = { 72, 39, 31, 43, 30, 34 };

	public static void SetMouthThroat(int mouth, int throat) {
		int newFrequency = 0;
		int pos = 5;

		// recalculate formant frequencies 5..29 for the mouth (F1) and throat (F2)
		while (pos < 30) {
			// recalculate mouth frequency
			int initialFrequency = mouthFormants5_29[pos];
			if (initialFrequency != 0) {
				newFrequency = trans(Sam.mouth, initialFrequency);
			}
			RenderData.modifiedFreq1data[pos] = newFrequency;

			// recalculate throat frequency
			initialFrequency = throatFormants5_29[pos];
			if (initialFrequency != 0) {
				newFrequency = trans(Sam.throat, initialFrequency);
			}
			RenderData.modifiedFreq2data[pos] = newFrequency;
			pos++;
		}

		// recalculate formant frequencies 48..53
		pos = 0;
		while (pos < 6) {
			// recalculate F1 (mouth formant)
			int initialFrequency = mouthFormants48_53[pos];
			RenderData.modifiedFreq1data[pos + 48] = trans(mouth, initialFrequency);

			// recalculate F2 (throat formant)
			initialFrequency = throatFormants48_53[pos];
			RenderData.modifiedFreq2data[pos + 48] = trans(throat, initialFrequency);
			pos++;
		}
	}

	private static int Read(int p, int Y) {
		switch (p) {
		case 168:
			return Pitches[Y];
		case 169:
			return frequency1[Y];
		case 170:
			return frequency2[Y];
		case 171:
			return frequency3[Y];
		case 172:
			return amplitude1[Y];
		case 173:
			return amplitude2[Y];
		case 174:
			return amplitude3[Y];
		default:
			System.out.printf("Error reading from tables");
			return 0;
		}
	}

	private static void Write(int p, int Y, int value) {
		switch (p) {
		case 168:
			Pitches[Y] = value;
			return;
		case 169:
			frequency1[Y] = value;
			return;
		case 170:
			frequency2[Y] = value;
			return;
		case 171:
			frequency3[Y] = value;
			return;
		case 172:
			amplitude1[Y] = value;
			return;
		case 173:
			amplitude2[Y] = value;
			return;
		case 174:
			amplitude3[Y] = value;
			return;
		default:
			System.out.printf("Error writing to tables\n");
			return;
		}
	}

	// linearly interpolate values
	private static void interpolate(int width, int table, int frame, int mem53) {
	//	width %= 256; table %= 256; frame %= 256;// mem53 %= 256;
		
		int sign = (mem53 < 0 ? 1 : 0);
		int remainder = Math.abs(mem53) % width;
		int div = mem53 / width;

		int error = 0;
		int pos = width;
		int val = Read(table, frame) + div;

		while (--pos > 0) {
			error += remainder;
			if (error >= width) { // accumulated a whole integer error, so adjust output
				error -= width;
				if (sign > 0)
					val--;
				else if (val > 0)
					val++; // if input is 0, we always leave it alone
			}
			Write(table, ++frame, val); // Write updated value back to next frame.
			val += div;
		}
	}

	private static void interpolate_pitch(int pos, int mem49, int phase3) {
		pos %= 256; mem49 %= 256; phase3 %= 256;
		// unlike the other values, the pitches[] interpolates from
		// the middle of the current phoneme to the middle of the
		// next phoneme

		// half the width of the current and next phoneme
		int cur_width = Math.abs(Sam.phonemeLengthOutput[pos]);
		int next_width = Math.abs(Sam.phonemeLengthOutput[pos+1]);
		// sum the values
		int width = cur_width + next_width;
		int pitch = Pitches[next_width + mem49] - Pitches[mem49 - cur_width];
		interpolate(width, 168, phase3, pitch);
	}

	private static int CreateTransitions() {
		int mem49 = 0;
		int pos = 0;
		while (true) {
			int next_rank;
			int rank;
			int speedcounter;
			int phase1;
			int phase2;
			int phase3;
			int transition;

			int phoneme = Sam.phonemeIndexOutput[pos];
			int next_phoneme = Sam.phonemeIndexOutput[pos + 1];

			if (next_phoneme == 255)
				break; // 255 == end_token

			// get the ranking of each phoneme
			next_rank = RenderData.blendRank[next_phoneme];
			rank = RenderData.blendRank[phoneme];

			// compare the rank - lower rank value is stronger
			if (rank == next_rank) {
				// same rank, so use out blend lengths from each phoneme
				phase1 = RenderData.outBlendLength[phoneme];
				phase2 = RenderData.outBlendLength[next_phoneme];
			} else if (rank < next_rank) {
				// next phoneme is stronger, so us its blend lengths
				phase1 = RenderData.inBlendLength[next_phoneme];
				phase2 = RenderData.outBlendLength[next_phoneme];
			} else {
				// current phoneme is stronger, so use its blend lengths
				// note the out/in are swapped
				phase1 = RenderData.outBlendLength[phoneme];
				phase2 = RenderData.inBlendLength[phoneme];
			}

			mem49 += Sam.phonemeLengthOutput[pos];

			speedcounter = mem49 + phase2;
			phase3 = mem49 - phase1;
			transition = phase1 + phase2; // total transition?

			if (((transition - 2) & 128) == 0) {
				int table = 169;
				interpolate_pitch(pos, mem49, phase3);
				while (table < 175) {
					// tables:
					// 168 pitches[]
					// 169 frequency1
					// 170 frequency2
					// 171 frequency3
					// 172 amplitude1
					// 173 amplitude2
					// 174 amplitude3

					int value = Read(table, speedcounter) - Read(table, phase3);
					interpolate(transition, table, phase3, value);
					table++;
				}
			}
			++pos;
		}

		// add the length of this phoneme
		return mem49 + Sam.phonemeLengthOutput[pos];
	}

	private static void CombineGlottalAndFormants(int phase1, int phase2, int phase3, int Y) {
		//phase1 %= 256; phase2 %= 256; phase3 %= 256; Y %= 256;
		int tmp;
		tmp = RenderData.multtable[RenderData.sinus[phase1 % 256] | amplitude1[Y]];
		tmp += RenderData.multtable[RenderData.sinus[phase2 % 256] | amplitude2[Y]];
		tmp += tmp > 255 ? 1 : 0; // if addition above overflows, we for some reason add one;
		tmp%=256;
		tmp += RenderData.multtable[RenderData.rectangle[phase3 % 256] | amplitude3[Y]];
		tmp += 136;
		tmp >>= 4; // Scale down to 0..15 range of C64 audio.

		Output(0, tmp & 0xf);
	}

	// PROCESS THE FRAMES
	//
	// In traditional vocal synthesis, the glottal pulse drives filters, which
	// are attenuated to the frequencies of the formants.
	//
	// SAM generates these formants directly with sin and rectangular waves.
	// To simulate them being driven by the glottal pulse, the waveforms are
	// reset at the beginning of each glottal pulse.
	//
	private static void ProcessFrames(int mem48) {
		int speedcounter = 72;
		int phase1 = 0;
		int phase2 = 0;
		int phase3 = 0;
		int mem66 = 0; // !! was not initialized

		int Y = 0;
		int glottal_pulse = Pitches[0];
		int mem38 = glottal_pulse - (glottal_pulse >> 2); // mem44 * 0.75
		while (mem48 != 0) {
			int flags = sampledConsonantFlag[Y];
			// unvoiced sampled phoneme?
			if ((flags & 248) != 0) {
				mem66 = RenderSample(mem66, flags, Y);
				// skip ahead two in the phoneme buffer
				Y += 2;
				mem48 -= 2;
				speedcounter = Sam.speed;
			} else {
				CombineGlottalAndFormants(phase1, phase2, phase3, Y);
				speedcounter--;
				if (speedcounter == 0) {
					Y++; // go to next amplitude
					// decrement the frame count
					mem48--;
					if (mem48 == 0) {
						return;
					}
					speedcounter = Sam.speed;
				}

				--glottal_pulse;

				if (glottal_pulse != 0) {
					// not finished with a glottal pulse

					--mem38;
					// within the first 75% of the glottal pulse?
					// is the count non-zero and the sampled flag is zero?
					if ((mem38 != 0) || (flags == 0)) {
						// reset the phase of the formants to match the pulse
						phase1 += frequency1[Y];
						phase2 += frequency2[Y];
						phase3 += frequency3[Y];
						continue;
					}

					// voiced sampled phonemes interleave the sample with the
					// glottal pulse. The sample flag is non-zero, so render
					// the sample for the phoneme.
					mem66 = RenderSample(mem66, flags, Y);
				}
			}
			glottal_pulse = Pitches[Y] % 256;
			mem38 = glottal_pulse - (glottal_pulse >> 2); // mem44 * 0.75

			// reset the formant wave generators to keep them in
			// sync with the glottal pulse
			phase1 = 0;
			phase2 = 0;
			phase3 = 0;
		}
	}
}
