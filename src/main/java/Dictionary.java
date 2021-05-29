import enums.PartOfSpeech;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class Dictionary extends Tree<Word> {
    public static final int MIN_ASCII_VALUE = 32;
    private final LinkedList<Word> words = new LinkedList<>();

    public Dictionary(File dictionary) {
        super();
        try {
            Scanner reader = new Scanner(new FileReader(dictionary));

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] split = line.split(": ");
                String word = split[0];
                String def = split[1];
                char[] letters = word.toLowerCase().toCharArray();
                Node<Word> current = this.getHead();
                for (char c : letters) {
                    if (current.getChildren()[c - MIN_ASCII_VALUE] == null) {
                        current.getChildren()[c - MIN_ASCII_VALUE] = new TreeNode<Word>();
                    }
                    current = current.getChildren()[c - MIN_ASCII_VALUE];
                }
                Word output = new Word(word, def);
                this.words.add(output);
                current.setData(new Word(word, def));
                if (current instanceof TreeNode) {
                    ((TreeNode<Word>) current).setFlag(true);
                }
            }
            //do something
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<Word> getWords() {
        return new LinkedList<>(this.words);
    }

    public Word addWord(String word, String definition) {
        return addWord(word, definition, null);
    }

    public Word addWord(String word, String definition, Collection<PartOfSpeech> partsOfSpeech) {
        return addWord(word, definition, partsOfSpeech, null);
    }

    public Word addWord(String word, String definition, Collection<PartOfSpeech> partsOfSpeech, Collection<String> synonyms) {
        Word output = new Word(word, definition, partsOfSpeech, synonyms);
        if (this.containsWord(word)) {
            Node<Word> node = findNode(output);
            node.setData(output);
        }
        char[] letters = word.toLowerCase().toCharArray();
        Node<Word> current = this.getHead();
        for (char c : letters) {
            if (current.getChildren()[c - MIN_ASCII_VALUE] == null) {
                current.getChildren()[c - MIN_ASCII_VALUE] = new TreeNode<Word>();
            }
            current = current.getChildren()[c - MIN_ASCII_VALUE];
        }
        this.words.add(output);
        current.setData(output);
        if (current instanceof TreeNode) {
            ((TreeNode<Word>) current).setFlag(true);
        }
        return output;
    }

    public Word getWord(String word) {
        return this.findNode(new Word(word)).getData();
    }

    public String getDefinition(String word) {
        if (!this.containsWord(word)) {
            return "No Definition Available For '" + word + "'";
        }
        char[] letters = word.toLowerCase().toCharArray();
        Node<Word> current = this.getHead();
        for (char c : letters) {
            if (current.getChildren()[c - MIN_ASCII_VALUE] == null) {
                return "(Definition not found)";
            }
            current = current.getChildren()[c - MIN_ASCII_VALUE];
        }

        Word output = current.getData();

        if (output == null) {
            return "(No Definition)";
        }

        return output.getDefinition();
    }

    public boolean containsWord(String word) {
        return getWord(word) != null;
    }

    //this method is not designed to be used externally, please refer to Dictionary.getWord() for a better use example
    public Node<Word> findNode(Word word) {
        char[] letters = word.getWord().toLowerCase().toCharArray();
        Node<Word> current = this.getHead();
        for (char c : letters) {
            if (current.getChildren()[c - MIN_ASCII_VALUE] == null) {
                //returns an empty node
                return new TreeNode<>(null);
            }
            current = current.getChildren()[c - MIN_ASCII_VALUE];
        }

        return current;
    }

    public boolean injectNode(Node<Word> inject) {
        return this.injectNode(inject, inject.getData());
    }

    public boolean injectNode(Node<Word> input, Word destination) {
        String allButLast = destination.getWord().substring(0, destination.getWord().length() - 1);
        String word = destination.getWord();
        int wordLen = word.length();

        char last = word.charAt(wordLen - 1);
        destination.setWord(allButLast);
        Node<Word> oneOver = findNode(destination);
        if (oneOver == null) {
            return false;
        }
        Node<Word> swap = oneOver.getChildren()[last - MIN_ASCII_VALUE];
        input.getData().setDefinition(swap.getData().getDefinition());
        oneOver.getChildren()[last - MIN_ASCII_VALUE].addSubNode(input);
        return true;
    }
}
