import enums.PartOfSpeech;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TreeTests {
    public static final String DICTIONARY_TXT = "dictionary.txt",
            KNOWN_WORD = "hydrate",
            KNOWN_DEFINITION = "To form into a hydrate; to combine with water.",
            TEST_ADD_WORD = "pwn",
            TEST_ADD_DEFINITION = "To get WRECKED!";
    private static final String[] TEST_ADD_SYNONYMS = {"destroy", "dominate", "decimate", "demolish", "defeat"};
    private static final PartOfSpeech[] TEST_ADD_PARTS_OF_SPEECH = {PartOfSpeech.VERB};
    private Dictionary dictionary;

    @Before
    public void before() {
        this.dictionary = new Dictionary(FileUtils.toFile(getClass().getResource(DICTIONARY_TXT)));
        //verify that the dictionary does not contain the test word
        Collection<Word> words = this.dictionary.getWords();
        Assert.assertFalse(words.contains(new Word(TEST_ADD_WORD)));
    }

    @Test
    public void containsTest() {
        Assert.assertFalse(dictionary.containsWord(TEST_ADD_WORD));
        Assert.assertTrue(dictionary.containsWord(KNOWN_WORD));
    }

    @Test
    public void buildTest() {
        Collection<Word> words = dictionary.getWords();
        for (Word w : words) {
            String foundDef = dictionary.getDefinition(w.getWord());
            Assert.assertEquals(w.getDefinition(), foundDef);
        }
    }

    @Test
    public void getWordTest() {
        Assert.assertTrue(dictionary.containsWord(KNOWN_WORD));
        Word output = dictionary.getWord(KNOWN_WORD);
        Assert.assertEquals(KNOWN_WORD, output.getWord());
        Assert.assertEquals(KNOWN_DEFINITION, output.getDefinition());
    }

    @Test
    public void addTest1() {
        //Testing Dictionary.addWord(String word, String definition)
        Assert.assertFalse(dictionary.containsWord(TEST_ADD_WORD));

        this.dictionary.addWord(TEST_ADD_WORD, TEST_ADD_DEFINITION);

        Assert.assertTrue(dictionary.containsWord(TEST_ADD_WORD));

        String responseDefinition = dictionary.getDefinition(TEST_ADD_WORD);
        Assert.assertEquals(TEST_ADD_DEFINITION, responseDefinition);
        //There should not be any synonyms because we did not give the structure any synonyms
        Assert.assertTrue(dictionary.getWord(TEST_ADD_WORD).getSynonyms().isEmpty());
    }

    @Test
    public void addTest2() {
        //Testing Dictionary.addWord(String word, String definition, Collection<PartOfSpeech> partsOfSpeech)
        Assert.assertFalse(dictionary.containsWord(TEST_ADD_WORD));

        this.dictionary.addWord(TEST_ADD_WORD, TEST_ADD_DEFINITION, Arrays.asList(TEST_ADD_PARTS_OF_SPEECH));

        Assert.assertTrue(dictionary.containsWord(TEST_ADD_WORD));

        String responseDefinition = dictionary.getDefinition(TEST_ADD_WORD);
        Assert.assertEquals(TEST_ADD_DEFINITION, responseDefinition);
        //synonyms should never be null, just an empty list
        Assert.assertNotNull(dictionary.getWord(TEST_ADD_WORD).getSynonyms());
        //it shouldn't be this because it should be an empty list
        Assert.assertNotEquals(Arrays.asList(TEST_ADD_SYNONYMS), dictionary.getWord(TEST_ADD_WORD).getSynonyms());
        //see... empty list
        Assert.assertEquals(new ArrayList<String>(), dictionary.getWord(TEST_ADD_WORD).getSynonyms());

        Assert.assertEquals(Arrays.asList(TEST_ADD_PARTS_OF_SPEECH), dictionary.getWord(TEST_ADD_WORD).getPartsOfSpeech());

    }

    @Test
    public void addTest3() {
        //Testing Dictionary.addWord(String word, String definition, Collection<PartOfSpeech> partsOfSpeech, Collection<String> synonyms)
        Assert.assertFalse(dictionary.containsWord(TEST_ADD_WORD));

        this.dictionary.addWord(TEST_ADD_WORD, TEST_ADD_DEFINITION, Arrays.asList(TEST_ADD_PARTS_OF_SPEECH), Arrays.asList(TEST_ADD_SYNONYMS));

        Assert.assertTrue(dictionary.containsWord(TEST_ADD_WORD));

        String responseDefinition = dictionary.getDefinition(TEST_ADD_WORD);
        Assert.assertEquals(TEST_ADD_DEFINITION, responseDefinition);
        Assert.assertNotNull(dictionary.getWord(TEST_ADD_WORD).getSynonyms());
        Assert.assertEquals(Arrays.asList(TEST_ADD_SYNONYMS), dictionary.getWord(TEST_ADD_WORD).getSynonyms());
        Assert.assertEquals(Arrays.asList(TEST_ADD_PARTS_OF_SPEECH), dictionary.getWord(TEST_ADD_WORD).getPartsOfSpeech());
    }
}
