package code;

public class Reciter implements ReciterInterface {
	private int A, X;

	public static int[] inputtemp = new int[256];

	private int Code37055(int npos, int mask) {
		X = npos;
		return ReciterTabs.tab36376[inputtemp[X]] & mask;
	}

	private int match(String str) {
		char[] temp = str.toCharArray();
		int i = 0;
		while (i < temp.length) {
			int ch = temp[i];
			A = inputtemp[X++];
			if (A != ch)
				return 0;
			++i;
		}
		return 1;
	}

	private int GetRuleByte(int mem62, int Y) {
		int address = mem62;
		if (mem62 >= 37541) {
			address -= 37541;
			return ReciterTabs.rules2[address + Y];
		}
		address -= 32000;
		return ReciterTabs.rules[address + Y];
	}

	private int handle_ch2(int ch, int mem) {
		int tmp;
		X = mem;
		tmp = ReciterTabs.tab36376[inputtemp[mem]];
		if (ch == ' ') {
			if ((tmp & 128) != 0)
				return 1;
		} else if (ch == '#') {
			if ((tmp & 64) == 0)
				return 1;
		} else if (ch == '.') {
			if ((tmp & 8) == 0)
				return 1;
		} else if (ch == '^') {
			if ((tmp & 32) == 0)
				return 1;
		} else
			return -1;
		return 0;
	}

	private int handle_ch(int ch, int mem) {
		int tmp;
		X = mem;
		tmp = ReciterTabs.tab36376[inputtemp[X]];
		if (ch == ' ') {
			if ((tmp & 128) != 0)
				return 1;
		} else if (ch == '#') {
			if ((tmp & 64) == 0)
				return 1;
		} else if (ch == '.') {
			if ((tmp & 8) == 0)
				return 1;
		} else if (ch == '&') {
			if ((tmp & 16) == 0) {
				if (inputtemp[X] != 72)
					return 1;
				++X;
			}
		} else if (ch == '^') {
			if ((tmp & 32) == 0)
				return 1;
		} else if (ch == '+') {
			X = mem;
			ch = inputtemp[X];
			if ((ch != 69) && (ch != 73) && (ch != 89))
				return 1;
		} else
			return -1;
		return 0;
	}

	int mem56, mem57, mem58, mem59, mem60, mem61, mem62, mem63, mem64, mem65, mem66;
	int Y, r;
	@Override
	public int TextToPhonemes(String input) {
		inputtemp[0] = SamTabs.convert(' ');
		char[] text = input.toCharArray();
		// secure copy of input
		// because input will be overwritten by phonemes
		X = 0;
		do {
			A = (SamTabs.convert(text[X]) & 127);
			if (A >= 112)
				A = A & 95;
			else if (A >= 96)
				A = A & 79;
			inputtemp[++X] = A;
		} while (X < 255);
		inputtemp[255] = 27;
		mem56 = mem61 = 255;
		// this next part is wonky and I don't expect it to work first try
		// original code contained a few goto's which by todays standards make you
		// retarded if you use them
		// but this code was written a long time ago so it passes the old timer check
		// its just that its super hard to figure this out so... yeah...

		outterloop: while (true) {

			pos36554: while (true) {
				while (true) {
					X = ++mem61;
					mem64 = inputtemp[X];
					if (mem64 == '[') {
						X = ++mem56;
						text[X] = 155;
						return 1;
					}

					if (mem64 != '.')
						break;
					X++;
					A = ReciterTabs.tab36376[inputtemp[X]] & 1;
					if (A != 0)
						break;
					mem56++;
					X = mem56;
					A = '.';
					text[X] = '.';
				}
				mem57 = ReciterTabs.tab36376[mem64];
				if ((mem57 & 2) != 0) {
					mem62 = 37541;
					
				}

				if (mem57 != 0)
					break;
				inputtemp[X] = ' ';
				X = ++mem56;
				if (X > 120) {
					text[X] = 155;
					return 1;
				}
				text[X] = 32;
			}

			if ((mem57 & 128) == 0)
				return 0;

			// go to the right rules for this character.
			X = mem64 - 'A';
			mem62 = ReciterTabs.tab37489[X] | (ReciterTabs.tab37515[X] << 8);
		}

	}
	
	private int pos36700() {
		pos36700: while (true) {
			// find next rule
			while ((GetRuleByte(++mem62, 0) & 128) == 0)
				;
			Y = 0;
			while (GetRuleByte(mem62, ++Y) != '(')
				;
			mem66 = Y;
			while (GetRuleByte(mem62, ++Y) != ')')
				;
			mem65 = Y;
			while ((GetRuleByte(mem62, ++Y) & 127) != '=')
				;
			mem64 = Y;
			mem60 = X = mem61;
			// compare the string within the bracket
			Y = mem66 + 1;

			while (true) {
				if (GetRuleByte(mem62, Y) != inputtemp[X])
					continue pos36700;
				if (++Y == mem65)
					break;
				mem60 = ++X;
			}

			// the string in the bracket is correct

			mem59 = mem61;

			while (true) {
				int ch;
				while (true) {
					mem66--;
					mem57 = GetRuleByte(mem62, mem66);
					if ((mem57 & 128) != 0) {
						mem58 = mem60;pos37184: while (true) {
							r = 0;
							do {
								while (true) {
									Y = mem65 + 1;
									if (Y == mem64) {
										mem61 = mem60;

										if (Main.debug)
											PrintRule(mem62);

										while (true) {
											mem57 = A = GetRuleByte(mem62, Y);
											A = A & 127;
											if (A != '=')
												text[++mem56] = A;
											if ((mem57 & 128) != 0)
												continue pos36554;
											Y++;
										}
									}
									mem65 = Y;
									mem57 = GetRuleByte(mem62, Y);
									if ((ReciterTabs.tab36376[mem57] & 128) == 0)
										break;
									if (inputtemp[mem58 + 1] != mem57) {
										r = 1;
										break;
									}
									++mem58;
								}

								if (r == 0) {
									A = mem57;
									if (A == '@') {
										if (Code37055(mem58 + 1, 4) == 0) {
											A = inputtemp[X];
											if ((A != 82) && (A != 84) && (A != 67) && (A != 83))
												r = 1;
										} else {
											r = -2;
										}
									} else if (A == ':') {
										while (Code37055(mem58 + 1, 32) == 1)
											mem58 = X;
										r = -2;
									} else
										r = handle_ch(A, mem58 + 1);
								}

								if (r == 1)
									continue pos36700;
								if (r == -2) {
									r = 0;
									continue;
								}
								if (r == 0)
									mem58 = X;
							} while (r == 0);

						}
						continue;
					}
					X = mem57 & 127;
					if ((ReciterTabs.tab36376[X] & 128) == 0)
						break;
					if (inputtemp[mem59 - 1] != mem57)
						continue pos36700;
					--mem59;
				}

				ch = mem57;

				r = handle_ch2(ch, mem59 - 1);
				if (r == -1) {
					switch (ch) {
					case '&':
						if (Code37055(mem59 - 1, 16) == 0) {
							if (inputtemp[X] != 'H')
								r = 1;
							else {
								A = inputtemp[--X];
								if ((A != 'C') && (A != 'S'))
									r = 1;
							}
						}
						break;

					case '@':
						if (Code37055(mem59 - 1, 4) == 0) {
							A = inputtemp[X];
							if (A != 72)
								r = 1;
							if ((A != 84) && (A != 67) && (A != 83))
								r = 1;
						}
						break;
					case '+':
						X = mem59;
						A = inputtemp[--X];
						if ((A != 'E') && (A != 'I') && (A != 'Y'))
							r = 1;
						break;
					case ':':
						while (Code37055(mem59 - 1, 32) == 1)
							--mem59;
						continue;
					default:
						return 0;
					}
				}

				if (r == 1)
					continue pos36700;

				mem59 = X;
			}
			do {
				X = mem58 + 1;
				if (inputtemp[X] == 'E') {
					if ((ReciterTabs.tab36376[inputtemp[X + 1]] & 128) != 0) {
						A = inputtemp[++X];
						if (A == 'L') {
							if (inputtemp[++X] != 'Y')
								continue pos36700;
						} else if ((A != 'R') && (A != 'S') && (A != 'D') && match("FUL") == 0)
							continue pos36700;
					}
				} else {
					if (match("ING") == 0)
						continue pos36700;
					mem58 = X;
				}

				pos37184: while (true) {
					r = 0;
					do {
						while (true) {
							Y = mem65 + 1;
							if (Y == mem64) {
								mem61 = mem60;

								if (Main.debug)
									PrintRule(mem62);

								while (true) {
									mem57 = A = GetRuleByte(mem62, Y);
									A = A & 127;
									if (A != '=')
										text[++mem56] = A;
									if ((mem57 & 128) != 0)
										continue pos36554;
									Y++;
								}
							}
							mem65 = Y;
							mem57 = GetRuleByte(mem62, Y);
							if ((ReciterTabs.tab36376[mem57] & 128) == 0)
								break;
							if (inputtemp[mem58 + 1] != mem57) {
								r = 1;
								break;
							}
							++mem58;
						}

						if (r == 0) {
							A = mem57;
							if (A == '@') {
								if (Code37055(mem58 + 1, 4) == 0) {
									A = inputtemp[X];
									if ((A != 82) && (A != 84) && (A != 67) && (A != 83))
										r = 1;
								} else {
									r = -2;
								}
							} else if (A == ':') {
								while (Code37055(mem58 + 1, 32) == 1)
									mem58 = X;
								r = -2;
							} else
								r = handle_ch(A, mem58 + 1);
						}

						if (r == 1)
							continue pos36700;
						if (r == -2) {
							r = 0;
							continue;
						}
						if (r == 0)
							mem58 = X;
					} while (r == 0);

				}
			} while (A == '%');
			return 0;
		}
	}
}
