import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.dot.DOTImporter;
import org.jgrapht.nio.ImportException;
import org.jgrapht.nio.dot.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.StringReader;


public class Grapher {


        public static Graph<String, DefaultEdge> parseGraph(String filePath) throws Exception {
            try {
                String fileContent = readDotFile(filePath);
                return createGraphFromString(fileContent);
            } catch (IOException | ImportException e) {
                throw new Exception("Error while parsing graph", e);
            }

        }

        private static String readDotFile(String filePath) throws IOException {
            return Files.readString(Path.of(filePath));
        }

        private static Graph<String, DefaultEdge> createGraphFromString(String dotContent) throws ImportException {
            Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
            DOTImporter<String, DefaultEdge> dotImporter = null;
            dotImporter = new DOTImporter<>();
            dotImporter.setVertexFactory(label -> label);
            dotImporter.importGraph(graph, new StringReader(dotContent));
            System.out.println("Graph successfully parsed!");
            return graph;
        }

    public static void main(String[] args) throws Exception {
        parseGraph("src/main/resources/test1.dot");
    }

}
