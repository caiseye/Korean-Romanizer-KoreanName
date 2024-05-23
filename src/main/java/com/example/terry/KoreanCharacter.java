package com.example.terry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A library that handling Hangul characters in syllable units.
 */
public class KoreanCharacter implements Serializable, Comparable<KoreanCharacter> {
	/**
	 * Required for serialization support.
	 *
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = -2081434254504406150L;

	/**
	 * When consonant assimilation is ambiguous how it should occur,
	 * it designates one of progressive assimilation or regressive assimilation.
	 */
	public enum ConsonantAssimilation {
		/**
		 * Set progressive assimilation.
		 */
		Progressive,

		/**
		 * Set regressive assimilation.
		 */
		Regressive
	}

	/**
	 * Options for applying special rules depending on the type of word.
	 */
	public enum Type {
		/**
		 * Noun like a substantives.
		 */
		Substantives,

		/**
		 * Compound words.
		 */
		Compound,

		/**
		 * Person's full name.
		 */
		Name,

		/**
		 * Person's full name following the most commonly used notation.
		 */
		NameTypical,

		/**
		 * Common words.
		 */
		Typical
	}

	/**
	 * The consonant used as the final syllable of Hangul, which is called "Jongsung".
	 */
	public enum Chosung {
		ㄱ(new String[] {"k", "g"}) {
			protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ㄺ:
					case ㄻ:
					case ㄼ:
					case ㄽ:
					case ㄾ:
					case ㄿ:
					case ㅀ:
						return new String[] {"kk", "gg"};
					case ㅎ:
						return new String[] {"kk", "gg", "k"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄲ(new String[] {"kk", "gg"}),
		ㄴ(new String[] {"n"}) {
			protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ㄹ:
					case ㅀ:
						return new String[] {"l"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄷ(new String[] {"d"}) {
			protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ㄾ:
						return new String[] {"tt"};
					case ㄶ:
					case ㅎ:
						return new String[] {"t"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄸ(new String[] {"tt", "dd"}),
		ㄹ(new String[] {"r", "l"}) {
			protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ㄱ:
					case ㄲ:
					case ㄳ:
					case ㄺ:
					case ㄼ:
					case ㄿ:
					case ㅁ:
					case ㅂ:
					case ㅄ:
					case ㅇ:
					case ㅋ:
					case ㅍ:
						return new String[] {"n"};
					case ㄴ:
					case ㄷ:
					case ㄵ:
					case ㄶ:
					case ㅅ:
					case ㅆ:
					case ㅈ:
					case ㅊ:
					case ㅎ:
						switch (consonantAssimilation) {
							case Progressive:
								return new String[] {"n"};
							default:
								return new String[] {"r", "l"};
						}
					case ㄹ:
					case ㄻ:
					case ㄽ:
					case ㄾ:
					case ㅀ:
					case ㅌ:
						return new String[] {"r", "l"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅁ(new String[] {"m"}),
		ㅂ(new String[] {"b", "p"}) {
			protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ㄾ:
						return new String[] {"pp"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅃ(new String[] {"pp", "bb"}),
		ㅅ(new String[] {"s"}),
		ㅆ(new String[] {"ss"}),
		ㅇ(new String[] {""}) {
			protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ㄱ:
						if (type == Type.Compound && currentCharacter.getJungsung().isInducePalatalization()) {
							return new String[] {"n"};
						} else {
							return new String[] {"g"};
						}
					case ㄺ:
						return new String[] {"g"};
					case ㄲ:
						return new String[] {"kk"};
					case ㄳ:
					case ㄽ:
					case ㅄ:
					case ㅅ:
						return new String[] {"s"};
					case ㅇ:
						if (type == Type.Compound && currentCharacter.getJungsung().isInducePalatalization()) {
							return new String[] {"n"};
						} else {
							return defaultPronunciation;
						}
					case ㄴ:
					case ㄶ:
						return new String[] {"n"};
					case ㄵ:
					case ㅈ:
						return new String[] {"j"};
					case ㄷ:
						return currentCharacter.getJungsung().isInducePalatalization()
								? new String[] {"j"} : new String[] {"d"};
					case ㄹ:
					case ㅀ:
						if (type == Type.Compound && currentCharacter.getJungsung().isInducePalatalization()) {
							return new String[] {"l"};
						} else {
							return new String[] {"r"};
						}
					case ㄻ:
					case ㅁ:
						return new String[] {"m"};
					case ㄼ:
					case ㅂ:
						return new String[] {"b"};
					case ㄾ:
					case ㅌ:
						return currentCharacter.getJungsung().isInducePalatalization()
								? new String[] {"ch"} : new String[] {"t"};
					case ㄿ:
					case ㅍ:
						return new String[] {"p"};
					case ㅆ:
						return new String[] {"ss"};
					case ㅊ:
						return new String[] {"ch"};
					case ㅋ:
						return new String[] {"k"};
					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("ng") && currentCharacterPronunciation.isEmpty();
			}
		},
		ㅈ(new String[] {"j"}) {
			protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ㅎ:
						return new String[] {"ch"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅉ(new String[] {"jj"}),
		ㅊ(new String[] {"ch"}),
		ㅋ(new String[] {"k"}),
		ㅌ(new String[] {"t"}) {
			protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (prevCharacter.getJongsung()) {
					case ㅈ:
					case ㅊ:
						return currentCharacter.getJungsung().isInducePalatalization()
								? new String[] {"ch"} : new String[] {"t"};
					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("t");
			}
		},
		ㅍ(new String[] {"p", "f"}) {
			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return prevCharacterPronunciation.endsWith("p");
			}
		},
		ㅎ(new String[] {"h"}) {
			protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter) {
				switch (prevCharacter.getJongsung()) {
					case ㄱ:
						return new String[] {""};
					case ㄲ:
						return new String[] {"kk"};
					case ㄷ:
						return currentCharacter.getJungsung().isInducePalatalization()
								? new String[] {"ch"} : new String[] {"t"};
					case ㄾ:
					case ㅅ:
					case ㅆ:
					case ㅈ:
					case ㅊ:
					case ㅌ:
						return currentCharacter.getJungsung().isInducePalatalization()
								? new String[] {"ch"} : new String[] {"t"};
					case ㄺ:
						return new String[] {"k"};
					case ㄼ:
						return new String[] {"p"};
					case ㄽ:
						return new String[] {"s"};
					case ㅀ:
						return new String[] {"r"};
					case ㅂ:
						return new String[] {"p"};

					default:
						return defaultPronunciation;
				}
			}

			protected boolean isNeedHyphen(String prevCharacterPronunciation, String currentCharacterPronunciation) {
				return !currentCharacterPronunciation.isEmpty() && prevCharacterPronunciation.endsWith(String.valueOf(currentCharacterPronunciation.charAt(0)));
			}
		};

		protected final String[] defaultPronunciation;

		Chosung(String[] defaultPronunciation) {
			this.defaultPronunciation = defaultPronunciation;
		}

		public String[] getPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter) {
			return defaultPronunciation;
		}

		protected String[] getComplexPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
			return defaultPronunciation;
		}

		protected boolean isNeedHyphen(String[] prevCharacterPronunciation, String[] currentCharacterPronunciation) {
			return false;
		}
	}

	/**
	 * The vowel used as the middle syllable of Hangul, which is called "Jungsung".
	 */
	public enum Jungsung {
		ㅏ(new String[]{"a"}, false),
		ㅐ(new String[]{"ae"}, false),
		ㅑ(new String[]{"ya"}, true),
		ㅒ(new String[]{"yae"}, true),
		ㅓ(new String[]{"eo"}, false),
		ㅔ(new String[]{"e"}, false),
		ㅕ(new String[]{"yeo"}, true),
		ㅖ(new String[]{"ye"}, true),
		ㅗ(new String[]{"o"}, false),
		ㅘ(new String[]{"wa", "owa"}, false),
		ㅙ(new String[]{"wae"}, false),
		ㅚ(new String[]{"oe"}, false),
		ㅛ(new String[]{"yo"}, true),
		ㅜ(new String[]{"u", "oo"}, false),
		ㅝ(new String[]{"wo"}, false),
		ㅞ(new String[]{"we"}, false),
		ㅟ(new String[]{"wi", "wee"}, false),
		ㅠ(new String[]{"yu", "u"}, true),
		ㅡ(new String[]{"eu"}, false),
		ㅢ(new String[]{"ui"}, false),
		ㅣ(new String[]{"i", "y"}, true);

		private final String[] defaultPronunciation;
		private final boolean inducePalatalization;

		Jungsung(String[] defaultPronunciation, boolean inducePalatalization) {
			this.defaultPronunciation = defaultPronunciation;
			this.inducePalatalization = inducePalatalization;
		}

		public String[] getPronunciation(KoreanCharacter prevCharacter, KoreanCharacter currentCharacter) {
			boolean insertHyphen = false;

			if (prevCharacter != null && prevCharacter.isKoreanCharacter() && prevCharacter.getJongsung() == Jongsung.NONE && currentCharacter.getChosung() == Chosung.ㅇ) {
				String prevPronunciation = prevCharacter.getJungsung().defaultPronunciation[0];
				String currentPronunciation = defaultPronunciation[0];

				switch (prevPronunciation.charAt(prevPronunciation.length() - 1)) {
					case 'a':
						switch (currentPronunciation.charAt(0)) {
							case 'a':
							case 'e':
								insertHyphen = true;
								break;
						}
						break;
					case 'e':
						switch (currentPronunciation.charAt(0)) {
							case 'a':
							case 'e':
							case 'o':
							case 'u':
								insertHyphen = true;
								break;
						}
						break;
				}
			}

			return defaultPronunciation;
		}


		public boolean isInducePalatalization() {
			return inducePalatalization;
		}
	}

	/**
	 * The consonant used as the final syllable of Hangul, which is called "Jongsung".
	 */
	public enum Jongsung {
		NONE(new String[]{""}),
		ㄱ(new String[]{"k"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄲ:
					case ㅋ:
						return new String[]{""};
					case ㅇ:
						if (type == Type.Compound && nextCharacter.getJungsung().isInducePalatalization()) {
							return new String[]{"ng"};
						} else {
							return new String[]{""};
						}
					case ㄴ:
					case ㅁ:
					case ㄹ:
						return new String[]{"ng"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄲ(new String[]{"k"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄲ:
					case ㅋ:
					case ㅇ:
					case ㅎ:
						return new String[]{""};
					case ㄴ:
					case ㅁ:
					case ㄹ:
						return new String[]{"ng"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄳ(new String[]{"k"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄲ:
					case ㅋ:
						return new String[]{""};
					case ㄴ:
					case ㅁ:
					case ㄹ:
						return new String[]{"ng"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄴ(new String[]{"n"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄹ:
						switch (consonantAssimilation) {
							case Regressive:
								return new String[]{"l"};
							default:
								return new String[]{"n"};
						}
					case ㅇ:
						return new String[]{""};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄵ(new String[]{"n"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄹ:
						switch (consonantAssimilation) {
							case Regressive:
								return new String[]{"l"};
							default:
								return new String[]{"n"};
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄶ(new String[]{"n"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ㄴ.getComplexPronunciation(nextCharacter);
			}
		},
		ㄷ(new String[]{"t"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄴ:
					case ㅁ:
						return new String[]{"n"};
					case ㄸ:
					case ㅇ:
					case ㅌ:
					case ㅎ:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return new String[]{""};
						}
					case ㄹ:
						switch (consonantAssimilation) {
							case Regressive:
								return new String[]{"l"};
							default:
								return new String[]{"n"};
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄹ(new String[]{"l"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㅇ:
						if (type == Type.Compound && nextCharacter.getJungsung().isInducePalatalization()) {
							return defaultPronunciation;
						} else {
							return new String[]{""};
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄺ(new String[]{"k"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄱ:
					case ㄲ:
					case ㅇ:
					case ㅎ:
						return new String[]{"l"};
					case ㄴ:
					case ㄹ:
					case ㅁ:
						return new String[]{"ng"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄻ(new String[]{"m"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄹ:
					case ㅁ:
					case ㅇ:
						return new String[]{"l"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄼ(new String[]{"l"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄴ:
					case ㄹ:
						return new String[]{"m"};
					case ㄷ:
					case ㄸ:
					case ㅂ:
					case ㅅ:
					case ㅆ:
					case ㅈ:
					case ㅉ:
					case ㅊ:
					case ㅋ:
					case ㅌ:
					case ㅎ:
						return new String[]{"p"};
					case ㅃ:
						return new String[]{""};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㄽ(new String[]{"l"}),
		ㄾ(new String[]{"l"}),
		ㄿ(new String[]{"l"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄴ:
					case ㄹ:
						return new String[]{"m"};
					case ㄷ:
					case ㄸ:
					case ㅂ:
					case ㅅ:
					case ㅆ:
					case ㅈ:
					case ㅉ:
					case ㅊ:
					case ㅋ:
					case ㅌ:
					case ㅎ:
						return new String[]{"p"};
					case ㅃ:
					case ㅍ:
						return new String[]{""};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅀ(new String[]{"l"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㅎ:
						return new String[]{""};
					case ㅇ:
						if (type == Type.Compound && nextCharacter.getJungsung().isInducePalatalization()) {
							return defaultPronunciation;
						} else {
							return new String[]{""};
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅁ(new String[]{"m"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㅇ:
						return new String[]{""};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅂ(new String[]{"p"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄴ:
					case ㄹ:
					case ㅁ:
						return new String[]{"m"};
					case ㅃ:
					case ㅇ:
						return new String[]{""};
					case ㅎ:
						if (type == Type.Substantives) {
							return defaultPronunciation;
						} else {
							return new String[]{""};
						}
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅄ(new String[]{"p"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄴ:
					case ㄹ:
					case ㅁ:
						return new String[]{"m"};
					case ㅃ:
						return new String[]{""};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅅ(new String[]{"t"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ㄷ.getComplexPronunciation(nextCharacter);
			}
		},
		ㅆ(new String[]{"t"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ㄷ.getComplexPronunciation(nextCharacter);
			}
		},
		ㅇ(new String[]{"ng"}),
		ㅈ(new String[]{"t"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ㄷ.getComplexPronunciation(nextCharacter);
			}
		},
		ㅊ(new String[]{"t"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				return ㄷ.getComplexPronunciation(nextCharacter);
			}
		},
		ㅋ(new String[]{"k"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄲ:
					case ㅇ:
						return new String[]{""};
					case ㄴ:
					case ㅁ:
					case ㄹ:
						return new String[]{"ng"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅌ(new String[]{"t"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄴ:
					case ㅁ:
						return new String[]{"n"};
					case ㄸ:
					case ㅇ:
					case ㅎ:
						return new String[]{""};
					case ㄹ:
						return new String[]{"l"};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅍ(new String[]{"p"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㅃ:
					case ㅇ:
						return new String[]{""};
					default:
						return defaultPronunciation;
				}
			}
		},
		ㅎ(new String[]{"t"}) {
			protected String[] getComplexPronunciation(KoreanCharacter nextCharacter, ConsonantAssimilation consonantAssimilation, Type type) {
				switch (nextCharacter.getChosung()) {
					case ㄱ:
					case ㄲ:
					case ㄷ:
					case ㄸ:
					case ㅇ:
					case ㅈ:
					case ㅉ:
					case ㅊ:
					case ㅋ:
					case ㅌ:
					case ㅍ:
					case ㅎ:
						return new String[]{""};
					case ㄴ:
					case ㅁ:
						return new String[]{"n"};
					case ㄹ:
						switch (consonantAssimilation) {
							case Regressive:
								return new String[]{"l"};
							default:
								return new String[]{"n"};
						}
					default:
						return defaultPronunciation;
				}
			}
		};

		protected final String[] defaultPronunciation;

		Jongsung(String[] defaultPronunciation) {
			this.defaultPronunciation = defaultPronunciation;
		}

		public String[] getPronunciation(KoreanCharacter nextCharacter) {
			return (nextCharacter == null || !nextCharacter.isKoreanCharacter()) ? defaultPronunciation : getComplexPronunciation(nextCharacter);
		}

		protected String[] getComplexPronunciation(KoreanCharacter nextCharacter) {
			return defaultPronunciation;
		}
	}


	/**
	 * First character code point in Hangul Syllables in Unicode table ({@code 가}).
	 */
	public final static int KoreanLowerValue = 0xAC00;

	/**
	 * Last character code point in Hangul Syllables in Unicode table ({@code 힣}).
	 */
	public final static int KoreanUpperValue = 0xD7A3;

	/**
	 * The original character from constructor's argument.
	 */
	private final char character;

	/**
	 * Disassembled initial syllable of Hangul.
	 */
	private Chosung chosung;

	/**
	 * Disassembled middle syllable of Hangul.
	 */
	private Jungsung jungsung;

	/**
	 * Disassembled final syllable of Hangul.
	 */
	private Jongsung jongsung;

	/**
	 * Constructor
	 *
	 * @param koreanCharacter
	 * 		the Hangul or other character
	 */
	public KoreanCharacter(char koreanCharacter) {
		character = koreanCharacter;

		if (isKoreanCharacter(character)) {
			int value = character - KoreanLowerValue;
			chosung = Chosung.values()[value / (21 * 28)];
			jungsung = Jungsung.values()[value % (21 * 28) / 28];
			jongsung = Jongsung.values()[value % 28];
		}
	}

	/**
	 * Constructor with Hangul object with each syllables.
	 *
	 * @param chosung
	 * 		the consonant used as the initial syllable of Hangul.
	 * @param jungsung
	 * 		the vowel used as the middle syllable of Hangul.
	 * @param jongsung
	 * 		the consonant used as the final syllable of Hangul.
	 * @throws NullPointerException
	 * 		if any arguments is null.
	 */
	public KoreanCharacter(Chosung chosung, Jungsung jungsung, Jongsung jongsung) {
		Objects.requireNonNull(chosung, "All parameters must not be null.");
		Objects.requireNonNull(jungsung, "All parameters must not be null.");
		Objects.requireNonNull(jongsung, "All parameters must not be null.");

		this.chosung = chosung;
		this.jungsung = jungsung;
		this.jongsung = jongsung;

		this.character = (char) ((chosung.ordinal() * 21 * 28 + jungsung.ordinal() * 28 + jongsung.ordinal()) + KoreanLowerValue);
	}

	/**
	 * Whether or not the character of this object is Hangul.
	 *
	 * @return Whether all syllables exist to complete Hangul character.
	 */
	public boolean isKoreanCharacter() {
		return chosung != null && jungsung != null && jongsung != null;
	}

	/**
	 * @return the initial syllable if object has Hangul character, and null if not.
	 */
	public Chosung getChosung() {
		return chosung;
	}

	/**
	 * @return the middle syllable if object has Hangul character, and null if not.
	 */
	public Jungsung getJungsung() {
		return jungsung;
	}

	/**
	 * @return the final syllable if object has Hangul character, and null if not.
	 */
	public Jongsung getJongsung() {
		return jongsung;
	}

	/**
	 * @return the character that this object has.
	 */
	public char getCharacter() {
		return character;
	}

	/**
	 * @param prevCharacter
	 * 		the character preceding this character in the sentence.
	 * @param nextCharacter
	 * 		the character after this character in the sentence.
	 * @return the romanized string of the character this object has.
	 */
	public List<String> getRomanizedString(KoreanCharacter prevCharacter, KoreanCharacter nextCharacter) {
		if (!isKoreanCharacter()) {
			return new ArrayList<String>();
		}
		List<String> romanizedStrings = new ArrayList<String>();
		for (final String chosung : chosung.getPronunciation(prevCharacter, this)) {
			for (final String jungsung : jungsung.getPronunciation(prevCharacter, this)) {
				for (final String jongsung : jongsung.getPronunciation(nextCharacter)) {
					romanizedStrings.add(chosung + jungsung + jongsung);
				}
			}
		}
		return romanizedStrings;
	}

	/**
	 * To check if character is in the Hangul Syllable of Unicode table.
	 *
	 * @param character
	 * 		the character to check.
	 * @return true if the character is Hangul
	 */
	public static boolean isKoreanCharacter(char character) {
		return (KoreanLowerValue <= character && character <= KoreanUpperValue);
	}

	/**
	 * Compares this object to another in ascending order.
	 *
	 * @param other
	 * 		the other object to compare to.
	 * @return the value of {@link Character#compareTo}.
	 */
	@Override
	public int compareTo(KoreanCharacter other) {
		return Character.compare(character, other.character);
	}

	/**
	 * Compares this object to another to test if they are equal.
	 *
	 * @param other
	 * 		the other object to compare to.
	 * @return true if this object is equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (other == null || getClass() != other.getClass()) {
			return false;
		}

		return character == ((KoreanCharacter) other).character;
	}

	/**
	 * Return the hash code for this character.
	 *
	 * @return the value of {@link Character#hashCode()}
	 */
	@Override
	public int hashCode() {
		return Character.hashCode(character);
	}

	/**
	 * Returns a {@link String} object representing this character's value.
	 *
	 * @return a string representation of this character.
	 */
	@Override
	public String toString() {
		return String.valueOf(character);
	}
}