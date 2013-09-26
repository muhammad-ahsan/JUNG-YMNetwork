/*
 * MUHAMMAD Ahsan
 * <muhammad.ahsan@gmail.com>
 *
 * Parsing Yahoo Instant Messenger Friend's network
 * using in-memory JUNG Framework
 *
 */

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author ahsan
 */
public class YMNetwork {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        /*
         * In-Memory graph
         * Graph <V, E> where V = Vertex type and E = Edge type
         * Input Data
         * Inverted
         */

        Graph<Integer, String> G = new SparseMultigraph<>();

        BufferedReader Reader = new BufferedReader(new FileReader(args[0]));
        String nextLine = Reader.readLine();
        if (nextLine != null && !nextLine.isEmpty()) {
            StringTokenizer Tokenizer = new StringTokenizer(nextLine, " ");
            String Nodes = Tokenizer.nextToken();

            System.out.println("G = (V,E)");
            System.out.println("+++++++++++++");
            System.out.println("|V| = " + Nodes);
            String Edges = Tokenizer.nextToken();
            System.out.println("|E| = " + Edges);
            System.out.println("+++++++++++++");

            int UserID = 1;

            nextLine = Reader.readLine();
            try {
                // Order is mandatory
                while (nextLine != null && !nextLine.isEmpty()) {
                    StringTokenizer T = new StringTokenizer(nextLine, " ");
                    //++++++++++++++++++++//
                    boolean User = G.addVertex(UserID);
                    //++++++++++++++++++++//
                    while (T.hasMoreElements() == true) {
                        int FriendID = Integer.parseInt(T.nextToken().trim());
                        //++++++++++++++++++++//
                        // Insert UserID and FriendID in graph
                        boolean Friend = G.addVertex(FriendID);
                        // Optimization Check (Edge check only if both exist before)
                        if (User == false && Friend == false) {
                            // Check if link exist before
                            boolean HasEdge = G.isNeighbor(UserID, FriendID);
                            if (HasEdge == false) {
                                /*
                                 * Edge Naming convention
                                 * Name = UserID_FriendID
                                 */
                                String EdgeID = UserID + "_" + FriendID;
                                G.addEdge(EdgeID, UserID, FriendID, EdgeType.UNDIRECTED);
                            }
                        } else {
                            /*
                             * Edge Naming convention
                             * Name = UserID_FriendID
                             */
                            String EdgeID = UserID + "_" + FriendID;
                            G.addEdge(EdgeID, UserID, FriendID, EdgeType.UNDIRECTED);
                        }
                        //++++++++++++++++++++//
                    }

                    UserID++;
                    nextLine = Reader.readLine();
                }
                System.out.println("++++++++++++++++++");
                System.out.println("File load finished");
                System.out.println("++++++++++++++++++");
                System.out.println("In memory graph statistics");
                System.out.println("|V| = " + G.getVertexCount());
                System.out.println("|E| = " + G.getEdgeCount());

            } catch (NumberFormatException | IOException e) {
                throw new IllegalArgumentException(nextLine);
            }
        } else {
            System.out.println("File is empty");
        }
    }
}
