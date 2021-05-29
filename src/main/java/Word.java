import enums.PartOfSpeech;

import java.util.*;

public class Word implements Comparable<Word> {
    private String word, definition;
    private Collection<PartOfSpeech> partsOfSpeech = new ArrayList<>();
    private LinkedList<String> synonyms = new LinkedList<>();

    public Word(String word) {
        this.word = word;
    }

    public Word(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public Word(String word, Collection<PartOfSpeech> partsOfSpeech) {
        this.word = word;
        this.partsOfSpeech = partsOfSpeech;
    }

    public Word(String word, String definition, Collection<PartOfSpeech> partsOfSpeech, Collection<String> synonyms) {
        this.word = word;
        this.definition = definition;
        this.partsOfSpeech = partsOfSpeech;
        if (synonyms != null) {
            this.synonyms = new LinkedList<>(synonyms);
        }
    }

    public Word(String word, String definition, Collection<PartOfSpeech> partsOfSpeech) {
        this.word = word;
        this.definition = definition;
        this.partsOfSpeech = partsOfSpeech;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Collection<PartOfSpeech> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public void setPartsOfSpeech(Collection<PartOfSpeech> partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }

    public Collection<String> getSynonyms() {
        return new LinkedList<>(synonyms);
    }

    public void setSynonyms(Collection<String> synonyms) {
        this.synonyms = new LinkedList<>(synonyms);
    }

    public void setSynonyms(String[] synonyms) {
        this.synonyms = new LinkedList<>(Arrays.asList(synonyms));
    }

    public void addSynonym(String synonym) {
        this.synonyms.add(synonym);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return this.word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, definition, partsOfSpeech);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", definition='" + definition + '\'' +
                ", partsOfSpeech=" + (Arrays.toString(partsOfSpeech.toArray(new PartOfSpeech[0]))) +
                '}';
    }

    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.word);
    }
}
