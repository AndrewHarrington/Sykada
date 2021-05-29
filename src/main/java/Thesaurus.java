import enums.PartOfSpeech;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Thesaurus extends Graph<Word> {

    public Thesaurus(File thesaurus) {
        this(thesaurus, null);
    }

    public Thesaurus(File thesaurus, Dictionary definitions) {
        //Extra work being done here moderately slows the system
        super(generateWords(thesaurus, definitions));
        Set<Word> words = this.getNodes();

        for (Word w : words) {
//            w.setThesaurus(this);
            for (String syno : new LinkedList<>(w.getSynonyms())) {
                Word syn = new Word(syno);
                //Does it already exist in the network?
                if (!this.containsChild(syn)) {
                    //No?
                    syn.setSynonyms(Collections.singletonList(w.getWord()));
                    this.addChild(syn);
                } else {
                    //Yes?
                    this.get(syno).addSynonym(w.getWord());
                }
                this.addConnection(w, syn);
                this.addConnection(syn, w);
            }
        }
    }

    public Word add(String word) {
        Word w = new Word(word);
        this.addChild(w);
        return w;
    }

    public Word add(String word, String[] synonyms) {
        Word w = new Word(word);
        w.setSynonyms(Arrays.asList(synonyms));
        this.addChild(w);
        this.addSynonymsToGraph(w);
        return w;
    }

    public Word add(String word, String definition, String[] synonyms) {
        Word w = new Word(word);
        w.setDefinition(definition);
        w.setSynonyms(Arrays.asList(synonyms));
        this.addChild(w);
        this.addSynonymsToGraph(w);
        return w;
    }

    public Word add(String word, String definition, PartOfSpeech[] partsOfSpeech, String[] synonyms) {
        Word w = new Word(word);
        w.setDefinition(definition);
        w.setPartsOfSpeech(Arrays.asList(partsOfSpeech));
        w.setSynonyms(Arrays.asList(synonyms));
        this.addChild(w);
        this.addSynonymsToGraph(w);
        return w;
    }

    public boolean containsWord(String word) {
        return this.containsChild(new Word(word));
    }

    public Word get(String s) {
        return this.find(new Word(s));
    }

    public static boolean isConnected(Word first, Word second) {
        return first.getSynonyms().contains(second.getWord())
                && second.getSynonyms().contains(first.getWord());
    }

    public boolean addSynonymsToGraph(Word w) {
        //this must be done manually in order to maintain the generic nature of Graph.containsChild(T data)
        if (w == null || !this.containsChild(w)) {
            return false;
        }
        for (String syno : new LinkedList<>(w.getSynonyms())) {
            Word syn = new Word(syno);
            //Does it already exist in the network?
            if (!this.containsChild(syn)) {
                //No?
                syn.setSynonyms(Collections.singletonList(w.getWord()));
                this.addChild(syn);
            } else {
                //Yes?
                this.get(syno).addSynonym(w.getWord());
            }
            this.addConnection(w, syn);
            this.addConnection(syn, w);
        }
        return true;
    }

    private static HashSet<Word> generateWords(File thesaurus, Dictionary definitions) {
        HashSet<Word> words = new HashSet<>();
        try {
            Scanner reader = new Scanner(new FileReader(thesaurus));
            LinkedList<PartOfSpeech> parts = new LinkedList<>();
            LinkedList<String> synonyms = new LinkedList<>();
            String line, definition;

            //for each word in the thesaurus
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                String[] split = line.split("(\\.)|(;)");

                String word = split[0].split(" ")[0].trim();

                String firstPartOfSpeech = split[0].split(" ")[1];
                PartOfSpeech first = PartOfSpeech.get(firstPartOfSpeech);
                parts.add(first);

                for (int i = 1; i < split.length; i++) {
                    String parse = split[i].trim();

                    //for a pos base
                    if (parse.contains("--")) {
                        int index = parse.indexOf("--") + 2;
                        String abrev = parse.substring(index);
                        parts.add(PartOfSpeech.get(abrev));
                    } else {
                        //for a numbered base
                        String[] synosToParse = parse.split(":");
                        String syno = synosToParse[0].trim();
                        if (!syno.equals("")) {
                            while (Character.isDigit(syno.charAt(0))) {
                                syno = syno.substring(1);
                            }
                            String[] synos = syno.split(", ");
                            //just gonna make one final trim here
                            for (int j = 0; j < synos.length; j++) {
                                synos[j] = synos[j].trim();
                            }
                            Collections.addAll(synonyms, synos);
                        }
                    }
                }

                if (definitions != null) {
                    definition = definitions.getDefinition(word);
                } else {
                    definition = "";
                }

                words.add(new Word(word, definition, new LinkedList<>(parts), new LinkedList<>(synonyms)));
                synonyms.clear();
                parts.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
}
