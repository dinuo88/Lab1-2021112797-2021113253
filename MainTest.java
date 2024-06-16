import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author dinuo
 * @Date: 2024/06/14/17:35
 */
class MainTest {

    @Test
    void queryBridgeWords() throws IOException {
        Main.init("./test/test.txt");

        assertEquals(Main.queryBridgeWords(Main.graph, "new", "and"),
                "The bridge words from “new” to “and” is:life");

        assertEquals(Main.queryBridgeWords(Main.graph, "new@", "an#d"),
                "No new@ in the graph!\n" +
                        "No an#d in the graph!");

        assertEquals(Main.queryBridgeWords(Main.graph, "apple", "and"),
                "No apple in the graph!");

        assertEquals(Main.queryBridgeWords(Main.graph, "to", "new"),
                "No bridge words from word1 to word2!");

    }


    private static HashMap<String, Integer> map;
    private static int[][] graph;

    @BeforeAll
    public static void setUp() {
        // 初始化图与映射
        map = new HashMap<>();
        map.put("A", 0);
        map.put("B", 1);
        map.put("C", 2);

        // 初始化图的邻接矩阵
        graph = new int[][]{
                {0, 1, 4},
                {1, 0, 2},
                {4, 2, 0}
        };
    }

    @Test
    public void testWord1NotInGraph() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        calcShortestPath("X", "A");
        assertTrue(outContent.toString().contains("No X in the graph!"));
    }

    @Test
    public void testWord2NotInGraph() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        calcShortestPath("A", "Y");
        assertTrue(outContent.toString().contains("No Y in the graph!"));
    }

    @Test
    public void testStartEqualsEnd() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        calcShortestPath("A", "A");
        assertTrue(outContent.toString().contains("最短路径长度：0"));
        assertTrue(outContent.toString().contains("最短路径为：A"));
    }

    @Test
    public void testValidShortestPath() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        calcShortestPath("A", "C");
        assertTrue(outContent.toString().contains("最短路径长度：3"));
        assertTrue(outContent.toString().contains("最短路径为：A -> B -> C"));
    }

    // calcShortestPath方法定义
    public static void calcShortestPath(String word1, String word2) {
        if (!map.containsKey(word1)) {
            System.out.println("No " + word1 + " in the graph!");
            return;
        }
        if (!map.containsKey(word2)) {
            System.out.println("No " + word2 + " in the graph!");
            return;
        }

        int start = map.get(word1);
        int end = map.get(word2);
        int[] flag = new int[map.size()];
        int[] dist = new int[map.size()];
        int[] path = new int[map.size()];
        for (int i = 0; i < map.size(); i++) {
            dist[i] = graph[start][i];
        }
        flag[start] = 1;
        int pre = start;
        for (int i = 0; i < map.size() - 1; i++) {
            int k = -1;
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < map.size(); j++) {
                if (flag[j] == 1) {
                    continue;
                }
                if (min > dist[j]) {
                    k = j;
                    min = dist[j];
                }
            }
            path[k] = pre;
            flag[k] = 1;
            pre = k;
            for (int m = 0; m < map.size(); m++) {
                if (m == start || m == k) continue;
                if (dist[m] > dist[k] + graph[k][m]) {
                    dist[m] = dist[k] + graph[k][m];
                    path[m] = k;
                }
            }
        }
        System.out.println("最短路径长度：" + dist[end]);
        System.out.print("最短路径为：");
        print(path, start, end);
        System.out.println();
    }

    private static void print(int[] path, int start, int end) {
        if (start == end) {
            System.out.print(map.keySet().toArray()[start]);
            return;
        }
        print(path, start, path[end]);
        System.out.print(" -> " + map.keySet().toArray()[end]);
    }
}
