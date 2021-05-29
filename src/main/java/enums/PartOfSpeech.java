package enums;

public enum PartOfSpeech {
    NOUN("noun", "n"),
    VERB("verb", "v"),
    ADJECTIVE("adjective", "adj"),
    ADVERB("adverb", "adv"),
    PREPOSITION("preposition", "prep"),
    INTERJECTION("interjection", "interj"),
    ADVERBIAL_PHRASE("adverbial phrase", "adv.phr"),
    CONJUNCTION("conjunction", "conj"),
    PRONOUN("pronoun", "pron");
    private final String full, abbreviation;

    PartOfSpeech(String full, String abbreviation) {
        this.full = full;
        this.abbreviation = abbreviation;
    }

    public static PartOfSpeech get(String abbreviation) {
        for (PartOfSpeech p : PartOfSpeech.values()) {
            if (abbreviation.equals(p.abbreviation)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return full;
    }
}
