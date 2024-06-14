import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author dinuo
 * @Date: 2024/06/14/17:35
 */
class MainTest {

    @Test
    void queryBridgeWords() throws IOException {
        Main.init("./test/test.txt");

//        assertEquals(Main.queryBridgeWords(Main.graph, "new", "and"),
//                "The bridge words from “new” to “and” is:life");

//        assertEquals(Main.queryBridgeWords(Main.graph, "new@", "an#d"),
//                "No new@ in the graph!\n" +
//                        "No an#d in the graph!");
//
        assertEquals(Main.queryBridgeWords(Main.graph, "apple", "and"),
                "No apple in the graph!");
//
//        assertEquals(Main.queryBridgeWords(Main.graph, "to", "new"),
//                "No bridge words from word1 to word2!");


    }
}
