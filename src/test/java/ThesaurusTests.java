import org.apache.commons.io.FileUtils;
import org.junit.*;

public class ThesaurusTests {

    public static final String DICTIONARY_TXT = "dictionary.txt"; // MUST BE A PRESENT FILE
    public static final String THESAURUS_TXT = "thesaurus-really-really-really-short.txt"; //MUST BE A PRESENT FILE
    public static final String MASTER_WORD = "take"; //MUST BE A VALID WORD IN THE CHOSEN THESAURUS FILE


    private static double startTime;
    private Thesaurus thesaurus = null;

    @BeforeClass
    public static void beforeClass() {
        startTime = System.nanoTime();
    }

    @Before
    public void before() {
        //TODO: REMOVE ME
        // This is a bug that will be in the final version.
        // It is intentionally restarting the thesaurus and wasting time.
        // But it is also keeping the tests consistent.
        this.thesaurus = new Thesaurus(FileUtils.toFile(getClass().getResource(THESAURUS_TXT)));
    }

    @Test
    public void startupTest() {
        //empty as this test is a timer for how long it takes to start up
        //this can be used to gain insight into the running time of the algorithms
    }

    @Test
    public void sizeTest() {
        long num = this.thesaurus.getNodes().size();
        System.out.println("Total nodes after initialization (> number of lines in thesaurus): " + num);
    }

    @Test
    public void retrievalTest() {
        Word word = this.thesaurus.get(MASTER_WORD);
        //Did it exist?
        Assert.assertNotNull(word);
        //Is it even thr right word?
        Assert.assertEquals(MASTER_WORD, word.getWord());
        //empty because a dictionary was not assigned to this test class
        Assert.assertEquals("", word.getDefinition());
        //There should be synonyms for every word. It should never be empty
        Assert.assertFalse(word.getSynonyms().isEmpty());
    }

    @Test
    public void synoCheck() {
        Word word = this.thesaurus.get(MASTER_WORD);
        for (String synonym : word.getSynonyms()) {
            Word syno = thesaurus.get(synonym);
            //Make sure every synonym is actually in the graph
            Assert.assertNotNull(syno);
            //make sure they are all connected properly
            Assert.assertTrue(Thesaurus.isConnected(word, syno));
        }
    }


    @Test
    public void removeTest() {
        Assert.assertTrue(this.thesaurus.containsWord(MASTER_WORD));

        this.thesaurus.removeChild(new Word(MASTER_WORD));

        Assert.assertFalse(this.thesaurus.containsWord(MASTER_WORD));
    }

    @AfterClass
    public static void afterClass() {
        double endTime = System.nanoTime();
        double timeElapsed = endTime - startTime;

        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
        System.out.println("Execution time in seconds: " + timeElapsed / 1000000000);
        double seconds = timeElapsed / 1000000000.0;
        System.out.println("Execution time in minutes: " + seconds / 60);
    }
}
