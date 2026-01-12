package port;

public class Reciter {
	
	static int currentCharacter, globalPosition;
	static int[] inputTemp = new int[257];
	static int[] input;
	static boolean shouldRun = true;
	
	// Checks the flags of predefined character flags and the flags of mask
	public static int getCharacterFlags(int position, int flagCheck) {
		flagCheck %= 256; position %= 256;
		globalPosition = position;
	    return ReciterData.characterFlags[inputTemp[position]] & flagCheck;
	}
	
	public static int match(int[] text) {
		for (int i = 0; i < text.length; i++) {
			currentCharacter = inputTemp[globalPosition++];
			globalPosition%=256;
			if (currentCharacter != text[i]) {
				return 0; 
			}
		}
		return 1;
	}
	
	public static int getRuleByte(int address, int offset) {//short mem62 DO NOT APPLY %=256 TO ADDRESS
		offset%=256;
		if (address >= 37541) {
			address -= 37541;
			return ReciterData.rules2[address+offset];
		}
		address -= 32000;
		return ReciterData.rules[address+offset];
	}
	
	
	public static int handle_ch2(int character, int mem) {
		character%=256; mem%=256;
	    globalPosition = mem;
	    int tmp = ReciterData.characterFlags[inputTemp[globalPosition]];
	    if (character == ' ') {
	        if ((tmp & 128) != 0) {
	        	return 1;
	        }
	    } else if (character == '#') {
	        if ((tmp & 64) == 0) {
	        	return 1;
	        }
	    } else if (character == '.') {
	        if ((tmp & 8) == 0) {
	        	return 1;
	        }
	    } else if (character == '^') {
	        if ((tmp & 32) == 0) {
	        	return 1;
	        }
	    } else {
	    	return -1;
	    }
	    return 0;
	}
	
	public static int handle_ch(int character, int mem) {
		character%=256;mem%=256;
	    int tmp;
	    globalPosition = mem;
	    tmp = ReciterData.characterFlags[inputTemp[globalPosition]];
	    if (character == ' ') {
	        if ((tmp & 128) != 0) {
	        	return 1;
	        }
	    } else if (character == '#') {
	        if ((tmp & 64) == 0) {
	        	return 1;
	        }
	    } else if (character == '.') {
	        if((tmp & 8) == 0) {
	        	return 1;
	        }
	    } else if (character == '&') {
	        if((tmp & 16) == 0) {
	            if (inputTemp[globalPosition] != 72) return 1;
	            ++globalPosition; globalPosition%=256;
	        }
	    } else if (character == '^') {
	        if ((tmp & 32) == 0) {
	        	return 1;
	        }
	    } else if (character == '+') {
	        globalPosition = mem;
	        character = inputTemp[globalPosition];
	        if ((character != 69) && (character != 73) && (character != 89)) {
	        	return 1;
	        }
	    } else {
	    	return -1;
	    }
	    return 0;
	}
	
	//made the following variables for better coding adventures
	
	private static int outputPosition = 255;//mem56      //output position for phonemes
	private static int currentRuleFlag;//mem57
	private static int currentCharPosition;//mem58
	private static int charProcessingPosition;//mem59
	private static int characterComparisonPosition;//mem60
	private static int ruleProcessingPosition = 255;//mem61
	private static int currentRule; //mem62 SHORT DO NOT APPLY %=256

	private static int currentCharacterPosition;//mem64      // position of '=' or current character
	private static int ruleEndPosition;//mem65     // position of ')'
	private static int ruleStartPosition;//mem66     // position of '('
	private static int r;//unknown use

	private static int offset = 0;

	public static int textToPhonemes(int[] _input) {
		input = _input;
		inputTemp[0] = ' ';
		
		// secure copy of input
		// because input will be overwritten by phonemes
		
		for (int i = 1; i < 255; i++) {
			int ch = _input[i] & 127;
			if (ch >= 112) {
				ch = ch & 95;
			} else if (ch >= 96) {
				ch = ch & 79;
			}
			inputTemp[i+1] = ch;
		}
		inputTemp[255] = 27;//insert ending flag
		
		return phonemes();
	}
	
	
	private static int phonemesSection1() {
		while(true) {
			globalPosition = ++ruleProcessingPosition % 256;
			currentCharacterPosition = inputTemp[globalPosition];
			if (currentCharacterPosition == '[') {
				globalPosition = ++outputPosition;
				input[globalPosition] = 155;
				return 1;//end the entire phonemes program
			}
		
			if (currentCharacterPosition != '.') {
				return 0;//break the loop outside this loop 
			}
			//currentCharacterPosition is '.' at this point
		
			globalPosition++;globalPosition%=256;
		
			currentCharacter = ReciterData.characterFlags[inputTemp[globalPosition]] & 1;
		
			if (currentCharacter != 0) {
				return 0;//break the loop outside this loop
			}
		
			//currentCharacter is 0
		
			outputPosition++;outputPosition%=256;
			globalPosition = outputPosition;
		
			currentCharacter = '.';
		
			input[globalPosition] = '.';
		}
	}
	
	
	
	private static int phonemesSection2() {
		while(true) {
			if (phonemesSection1() == 1) {
				if (Main.debug) System.out.println("Phonemes section 1 returned 1");
				return 1;//end command
			}
			
			currentRuleFlag = ReciterData.characterFlags[currentCharacterPosition];
		
			if ((currentRuleFlag & 2) != 0) {
				currentRule = 37541;
				return FindRule();
			} 
			
			if (currentRuleFlag != 0) {
				return 0;//break command
			}
			
			inputTemp[globalPosition] = ' ';
			globalPosition = ++outputPosition;
			
			if (globalPosition > 120) {
				if (globalPosition >= 256) {
					input[255] = 155;
				} else {
					input[globalPosition] = 155;
				}
				return 1;//end command
			}
			input[globalPosition] = 32;
		}
	}
	
	private static int phonemes() {//pos36554
		while (true) {
			if (!shouldRun) {
				return 0;
			}
			if(phonemesSection2() == 1) {
				System.out.println("Reciter: success");
				//Main.globalInput = input;
				shouldRun = false;
				return 1;
			}
			if ((currentRuleFlag & 128) == 0) {
				//System.out.println("Reciter: failure");
				return 0;//return 0;
			}
			
			globalPosition = currentCharacterPosition - 'A';
			
			currentRule = ReciterData.tab37489[globalPosition] | (ReciterData.tab37515[globalPosition] * 256);
			
			FindRule();
		}
	}
	
	
	private static int FindRule() {//pos36700
		//finds the next rule
		offset%=256;
		while ((getRuleByte(++currentRule, offset) & 128) == 0) {
		}
		while (getRuleByte(currentRule, ++offset) != '(') {
			offset%=256;
		}
		ruleStartPosition = offset%256;
		while (getRuleByte(currentRule, ++offset) != ')') {
			offset%=256;
		}
		ruleEndPosition = offset%256;
		while ((getRuleByte(currentRule, ++offset) & 127) != '=') {
			offset%=256;
		}
		currentCharacterPosition = offset%256;
		
		characterComparisonPosition = globalPosition = ruleProcessingPosition;

		// compare the string within the bracket
		
		offset = ruleStartPosition + 1;
		
		while (true) {
			if (getRuleByte(currentRule, offset) != inputTemp[globalPosition]) {
				return FindRule();//goto pos36700;
				//this is possible recursion (i'm sorry for your eyes to witness this)
			}
			if (++offset == ruleEndPosition) break;
			characterComparisonPosition = ++globalPosition;
		}
		
		// the string in the bracket is correct
		charProcessingPosition = ruleProcessingPosition;
		
		while (true) {
			while (true) {
				ruleStartPosition--;
				currentRuleFlag = getRuleByte(currentRule, ruleStartPosition);
				if ((currentRuleFlag & 128) != 0) {
					currentCharPosition = characterComparisonPosition;
					return pos37184();//goto pos37184;
				}
				globalPosition = currentRuleFlag & 127;
				if ((ReciterData.characterFlags[globalPosition] & 128) == 0) {
					break;
				}
				if (inputTemp[charProcessingPosition-1] != currentRuleFlag) {
					return FindRule();//goto pos36700;
				}
				--charProcessingPosition;
			}
			//ch = currentRuleFlag;
			r = handle_ch2(currentRuleFlag, charProcessingPosition-1);
			
			if (r == -1) {
				switch(currentRuleFlag) {
				case '&':
					if(getCharacterFlags(charProcessingPosition,16) == 0) {
						if (inputTemp[globalPosition] != 'H') {
							r = 1;
						} else {
							currentCharacter = inputTemp[--globalPosition];
							if ((currentCharacter != 'C') && (currentCharacter != 'S')) {
								r = 1;
							}
						}
					}
					break;
				case '@':
					if (getCharacterFlags(charProcessingPosition-1,4) == 0) {
						currentCharacter = inputTemp[globalPosition];
						if (currentCharacter != 72) {
							r = 1;
						}
						if ((currentCharacter != 84) && (currentCharacter != 67) && (currentCharacter != 83)) {
							r = 1;
						}
					}
					break;
				case '+':
	                globalPosition = charProcessingPosition;
	                currentCharacter = inputTemp[--globalPosition];
	                if ((currentCharacter != 'E') && (currentCharacter != 'I') && (currentCharacter != 'Y')) {
	                	r = 1;
	                }
	                break;
	            case ':':
	                while (getCharacterFlags(charProcessingPosition-1,32) == 1) {
	                	--charProcessingPosition;
	                }
	                continue;
	            default:
	                return 0;
				}
			}
			if (r == 1) {
				return FindRule();//goto pos36700;
			}
			
			charProcessingPosition = globalPosition;
		}
	}
	
	private static int endLoop() {
		do {
			globalPosition = currentCharPosition+1;
			if (ReciterData.characterFlags[inputTemp[globalPosition+1]] == 'E') {
				currentCharacter = inputTemp[++globalPosition];
				if (currentCharacter == 'L') {
					if (inputTemp[++globalPosition] != 'Y') {
						return FindRule();//goto pos36700;
					} else if ((currentCharacter != 'R') && (currentCharacter != 'S') && (currentCharacter != 'D') && match(convertToIntArray("FUL")) == 0) {
						return FindRule();//goto pos36700;
					}
				}
			} else {
				if (match(convertToIntArray("ING")) == 0) {
					return FindRule();//goto pos36700;
				}
				currentCharPosition = globalPosition;
			}
			pos37184();
		} while (currentCharacter == '%');
		return 0;
	}
	
	private static int pos37184() {
        r = 0;
        do {
            while (true) {
                offset = ruleEndPosition + 1;
                if(offset == currentCharacterPosition) {
                	ruleProcessingPosition = characterComparisonPosition;
                    
                    if (Main.debug) Debug.PrintRule(currentRule);
                    
                    while(true) {
                    	currentRuleFlag = currentCharacter = getRuleByte(currentRule, offset);
                        currentCharacter = currentCharacter & 127;
                        if (currentCharacter != '=') input[++outputPosition] = currentCharacter;
                        if ((currentRuleFlag & 128) != 0)  return phonemes();//goto pos36554;
                        offset++;
                    }
                }
                ruleEndPosition = offset;
                currentRuleFlag = getRuleByte(currentRule, offset);
                if((ReciterData.characterFlags[currentRuleFlag] & 128) == 0) break;
                if (inputTemp[currentCharPosition+1] != currentRuleFlag) {
                    r = 1;
                    break;
                }
                ++currentCharPosition;
            }

            if (r == 0) {
                currentCharacter = currentRuleFlag;
                if (currentCharacter == '@') {
                    if(getCharacterFlags(currentCharPosition+1, 4) == 0) {
                        currentCharacter = inputTemp[globalPosition];
                        if ((currentCharacter != 82) && (currentCharacter != 84) && 
                            (currentCharacter != 67) && (currentCharacter != 83)) r = 1;
                    } else {
                        r = -2;
                    }
                } else if (currentCharacter == ':') {
                    while (getCharacterFlags(currentCharPosition+1, 32) == 1) {
                    	currentCharPosition = globalPosition;
                    }
                    r = -2;
                } else r = handle_ch(currentCharacter, currentCharPosition+1);
            }

            if (r == 1) return FindRule();//goto pos36700;
            if (r == -2) { 
                r = 0;
                continue;
            }
            if (r == 0) currentCharPosition = globalPosition;
        } while (r == 0);
		return endLoop();
	}
	
	
	
	



	private static int[] convertToIntArray(String string) {
		int[] result = new int[string.length()];
		for (int i = 0;i < result.length; i++) {
			result[i] = string.charAt(i);
		}
		return result;
	}
}
