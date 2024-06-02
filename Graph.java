import java.io.File;

public class Graph {
    public static void start(String[] nodes, String[] preline, String[] weight){

        Graphviz gv = new Graphviz();
        //定义每个节点的style
        String nodesty = "[style = record]";
        //String linesty = "[dir=\"none\"]";

        gv.addln(gv.start_graph());//SATRT
        gv.addln("edge[style=\"dashed\"]");
        gv.addln("size =\"8,8\";");
        //设置节点的style
        for(int i=0;i<nodes.length;i++){
            gv.addln(nodes[i]+" "+nodesty);
        }
        for(int i=0;i<preline.length;i++){
            gv.addln(preline[i]+" "+" [dir=\"forward\"]"+" "+" [label=\""+weight[i]+"\"]");
        }
        gv.addln(gv.end_graph());//END
        //节点之间的连接关系输出到控制台
        System.out.println(gv.getDotSource());
        //输出什么格式的图片(gif,dot,fig,pdf,ps,svg,png,plain)
        String type = "png";
        //输出到文件夹以及命名
        File out = new File("./Graph/Graph." + type);   // Linux
        //File out = new File("c:/eclipse.ws/graphviz-java-api/out." + type);    // Windows
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }
}
