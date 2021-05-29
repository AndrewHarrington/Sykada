import enums.PartOfSpeech;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ThesaurusAddTests {

    public static final String THESAURUS_TXT = "thesaurus-really-really-really-short.txt"; //MUST BE A PRESENT FILE
    public static final String MASTER_WORD = "take"; //MUST BE A VALID WORD IN THE CHOSEN THESAURUS FILE

    private static final String TO_ADD = "pwn"; //MUST BE A WORD THAT ISN'T IN THE THESAURUS FILE
    private static final String DEF = "To get WRECKED!";
    private static final String[] SYNONYMS = {"destroy", "dominate", "decimate", "demolish", "defeat"};
    private static final PartOfSpeech[] PARTS_OF_SPEECH = {PartOfSpeech.VERB};

    private Thesaurus thesaurus = null;

    @Before
    public void before() {
        //TODO: REMOVE ME
        // This is a bug that will be in the final version.
        // It is intentionally restarting the thesaurus and wasting time.
        // But it is also keeping the tests consistent.
        this.thesaurus = new Thesaurus(FileUtils.toFile(getClass().getResource(THESAURUS_TXT)));

        try {
            this.thesaurus.get(TO_ADD);
            Assert.fail("Should not already contain the word to be added");

        } catch (IllegalArgumentException e) {
            //do nothing...
            //we wanted it to catch the exception to show that our word is not in the thesaurus
        }
    }

    @Test
    public void containsTest() {
        Assert.assertFalse(thesaurus.containsWord(TO_ADD));
        Assert.assertTrue(thesaurus.containsWord(MASTER_WORD));
    }

    @Test
    public void addCheck1() {
        //check add(String word)
        this.thesaurus.add(TO_ADD);
        Word response = this.thesaurus.get(TO_ADD);
        Assert.assertNotNull(response);
        Assert.assertEquals(TO_ADD, response.getWord());
    }

    @Test
    public void addCheck2() {
        //check add(String word, String[] synonyms)
        this.thesaurus.add(TO_ADD, SYNONYMS);
        Word response = this.thesaurus.get(TO_ADD);
        Assert.assertNotNull(response);
        Assert.assertEquals(TO_ADD, response.getWord());
        Assert.assertEquals(Arrays.asList(SYNONYMS), response.getSynonyms());
    }

    @Test
    public void addCheck3() {
        //check add(String word, String definition, String[] synonyms)
        this.thesaurus.add(TO_ADD, DEF, SYNONYMS);
        Word response = this.thesaurus.get(TO_ADD);
        Assert.assertNotNull(response);
        Assert.assertEquals(TO_ADD, response.getWord());
        //The Thesaurus structure is not built to handle definitions but it is capable because of the nature of the Word structure
        Assert.assertEquals(DEF, response.getDefinition());
        Assert.assertEquals(Arrays.asList(SYNONYMS), response.getSynonyms());
    }

    @Test
    public void addCheck4() {
        //check add(String word, String definition, PartOfSpeech[] partsOfSpeech, String[] synonyms)
        this.thesaurus.add(TO_ADD, DEF, PARTS_OF_SPEECH, SYNONYMS);
        Word response = this.thesaurus.get(TO_ADD);
        Assert.assertNotNull(response);
        Assert.assertEquals(TO_ADD, response.getWord());
        Assert.assertEquals(DEF, response.getDefinition());
        Assert.assertEquals(Arrays.asList(PARTS_OF_SPEECH), response.getPartsOfSpeech());
        Assert.assertEquals(Arrays.asList(SYNONYMS), response.getSynonyms());
    }
}
