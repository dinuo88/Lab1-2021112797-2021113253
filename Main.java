import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author dinuo
 * @Date: 2024/05/13/15:49
 */
public class Main {

    static int[][] graph = new int[20][20];
    static HashMap<String,Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("功能1：请输入文件路径生成有向图：");
        String fileName = in.nextLine();
        showDirectedGraph(fileName);
        System.out.println("-----------------------");
        System.out.println("图初始化完成");

        int flag = 0;
        while (flag == 0){
            menu();
            int n = in.nextInt();
            in.nextLine();
            switch (n){
                case 1:
                    Set<String> strings1 = map.keySet();
                    List<String> strings = new ArrayList<>();
                    for(String s : strings1){
                        strings.add(s);
                    }
                    String[] node = strings.toArray(new String[0]);

                    List<String> list1 = new ArrayList<>();
                    List<String> list2 = new ArrayList<>();
                    for(int i = 0;i < map.size();i++){
                        for(int j = 0;j < map.size();j++){
                            if(graph[i][j] != 100000){
                                list1.add(getKeyByValue(i) + " -> " + getKeyByValue(j));
                                list2.add(String.valueOf(graph[i][j]));
                            }
                        }
                    }

                    String[] edge = list1.toArray(new String[0]);
                    String[] weight = list2.toArray(new String[0]);
                    Graph.start(node,edge,weight);

                case 2:
                    System.out.print("输入两个英文单词（以空格为分界线）：");
                    String[] str = in.nextLine().split(" ");
                    queryBridgeWords(str[0],str[1]);
                    break;
                case 3:
                    System.out.print("请输入字符串文本：");
                    String str2 = in.nextLine();
                    generateNewText(str2);
                    break;
                case 4:
                    System.out.print("输入两个英文单词（以空格为分界线）：");
                    String[] str3 = in.nextLine().split(" ");
                    calcShortestPath(str3[0],str3[1]);
                    break;
                case 5:
                    for(int i = 0;i < 4;i++){
                        System.out.print("第" + (i+1) + "次随机游走：");
                        randomWalk();
                    }
                    break;
                case 6:
                    flag = 1;
                    break;
                default:
                    System.out.println("输入错误，请重新输入！");
                    break;
            }

        }
    }

    public static void  menu(){
        System.out.println("------------------------------------");
        System.out.println("1. 功能2：显示生成的有向图");
        System.out.println("2. 功能3：查找桥接词");
        System.out.println("3. 功能4：根据bridge word生成新文本");
        System.out.println("4. 功能5：计算两个单词之间的最短路径");
        System.out.println("5. 功能6：随机游走4次");
        System.out.println("6. ----退出");
        System.out.println("------------------------------------");
    }

    public static void showDirectedGraph(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream f1 = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(f1,StandardCharsets.UTF_8));

        String regex = "[A-Za-z]+";
        Matcher m = null;
        Pattern p = Pattern.compile(regex);

        String line = null;


        int k = 0;
        List<String> words = new ArrayList<>();
        while((line = br.readLine()) != null){
            m = p.matcher(line);
            while(m.find()){
                words.add(m.group(0).toLowerCase());
            }
        }
        for(int i = 0; i < words.size() - 1; i++){
            String first = words.get(i);
            String second = words.get(i+1);
            if(!map.containsKey(first))
                map.put(first,k++);
            if(!map.containsKey(second))
                map.put(second,k++);
            graph[map.get(first)][map.get(second)]++;
        }

        f1.close();
        br.close();
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if(graph[i][j] == 0)
                    graph[i][j] = 100000;
            }
        }
    }

    public static void queryBridgeWords(String word1, String word2){
        if (!map.containsKey(word1)){
            System.out.println("No " + word1 + " in the graph!");
        }
        if(!map.containsKey(word2)){
            System.out.println("No " + word2 + " in the graph!");
            return;
        }

        int start = map.get(word1);
        int end = map.get(word2);

        List<String> str = new ArrayList<>();
        for (int i = 0;i < map.size();i++){
            if(i == start || i == end){
                continue;
            }
            if(graph[start][i] != 100000 && graph[i][end] != 100000){
                str.add(getKeyByValue(i));
            }
        }

        if(str.isEmpty()){
            System.out.println("No bridge words from word1 to word2!");
        }else if(str.size() == 1){
            System.out.println("The bridge words from “" + word1 + "” to “" + word2 + "” is:" + str.get(0));
        }else {
            System.out.print("The bridge words from “" + word1 + "” to “" + word2 + "” are:");
            for(int i = 0;i < str.size() - 1;i++){
                System.out.print(str.get(i) + ",");
            }
            System.out.println("and " + str.get(str.size() - 1) + ".");
        }
    }

    public static List<String> queryBridgeWords1(String word1, String word2){
        if (!map.containsKey(word1) || !map.containsKey(word2)){
            return null;
        }

        int start = map.get(word1);
        int end = map.get(word2);

        List<String> str = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            if(i == start || i == end){
                continue;
            }
            if(graph[start][i] != 100000 && graph[i][end] != 100000){
                str.add(getKeyByValue(i));
            }
        }

        if(str.isEmpty()) {
            return null;
        }
        return str;
    }

    public static String getKeyByValue(int value){
        for(Map.Entry entry :  map.entrySet()){
            if((int)entry.getValue() == value){
                return (String) entry.getKey();
            }
        }
        return null;
    }

    public static void generateNewText(String str){

        String regex = "[A-Za-z]+";
        Matcher m = null;
        Pattern p = Pattern.compile(regex);
        m = p.matcher(str);
        List<String> strlist = new ArrayList<>();
        while (m.find()){
            strlist.add(m.group(0).toLowerCase());
        }
        System.out.print(strlist.get(0));
        for(int i = 0;i < strlist.size() - 1;i++){
            List<String> res = queryBridgeWords1(strlist.get(i),strlist.get(i+1));
            if (res == null) {
                System.out.print(" " +  strlist.get(i+1));

                continue;
            }
            System.out.print(" " + res.get(0) + " " +  strlist.get(i+1));
            System.out.println();
        }
    }

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

    public static void print(int[] path, int start, int end) {
        if (start == end) {
            System.out.print(getKeyByValue(start));
            return;
        }
        print(path, start, path[end]);
        System.out.print("->" + getKeyByValue(end));
    }

    public static void randomWalk(){

        int[][] flag = new int[map.size()][map.size()];
        for(int i = 0;i < map.size();i++){
            for(int j = 0;j < map.size();j++){
                if(graph[i][j] == 100000){
                    flag[i][j] = 2;
                }else{
                    flag[i][j] = 1;
                }
            }
        }
        int k = 0;
        System.out.print(getKeyByValue(k));

        while (true) {
            List<Integer> list = new ArrayList<>();
            for(int i = 0;i <  map.size();i++){
                if(flag[k][i] == 1 || flag[k][i] == 0){
                    list.add(i);
                }
            }
            if(list.isEmpty()){
                break;
            }else if(list.size() == 1){
                flag[k][list.get(0)] = 0;
                k = list.get(0);
                System.out.print("->" + getKeyByValue(k));
            }else {
                int size = list.size();
//                int random = (int) (Math.random() * size);
                SecureRandom secureRandom = new SecureRandom();
                int random = secureRandom.nextInt(size);
                if(flag[k][list.get(random)] == 0){
                    break;
                }
                flag[k][list.get(random)] = 0;
                k = list.get(random);
                System.out.print("->" + getKeyByValue(k));
            }
        }
        System.out.println();
    }
}
