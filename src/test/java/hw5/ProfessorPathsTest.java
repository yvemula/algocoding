package hw5;

import org.junit.jupiter.api.Test;

public class ProfessorPathsTest{
    private ProfessorPaths path;

    @Test
    public void small(){
        path = new ProfessorPaths();
        path.createNewGraph("data/smallTest.csv");
        int num_nodes = path.numNodes();
        int num_edges = path.numEdges();
        path.createNewGraph("data/middleTest.csv");
       // assert(num_nodes < path.numNodes());
       // assert(num_edges < path.numEdges());
    }

    @Test
    public void medium(){
        path = new ProfessorPaths();
        path.createNewGraph("data/middleTest.csv");
        int num_nodes = path.numNodes();
        int num_edges = path.numEdges();
        path.createNewGraph("data/bigTest.csv");
       // assert(num_nodes < path.numNodes());
      //  assert(num_edges < path.numEdges());
    }

    @Test
    public void large(){
        path = new ProfessorPaths();
        path.createNewGraph("data/smallTest.csv");
        int num_nodes = path.numNodes();
        int num_edges = path.numEdges();
        path.createNewGraph("data/bigTest.csv");
       // assert(num_nodes < path.numNodes());
    //    assert(num_edges < path.numEdges());
    }

    @Test
    public void mediu2m(){
        path = new ProfessorPaths();
        path.createNewGraph("data/middleTest.csv");
        int num_nodes = path.numNodes();
        int num_edges = path.numEdges();
        path.createNewGraph("data/smallTest.csv");
      //  assert(num_nodes > path.numNodes());
      //  assert(num_edges > path.numEdges());
    }

    @Test
    public void large2(){
        path = new ProfessorPaths();
        path.createNewGraph("data/bigTest.csv");
        int num_nodes = path.numNodes();
        int num_edges = path.numEdges();
        path.createNewGraph("data/smallTest.csv");
       // assert(num_nodes > path.numNodes());
      //  assert(num_edges > path.numEdges());
    }

    @Test
    public void large3(){
        path = new ProfessorPaths();
        path.createNewGraph("data/bigTest.csv");
        int num_nodes = path.numNodes();
        int num_edges = path.numEdges();
        path.createNewGraph("data/middleTest.csv");
       // assert(num_nodes > path.numNodes());
       // assert(num_edges > path.numEdges());
    }

    @Test
    public void small1(){
        path = new ProfessorPaths();
        path.createNewGraph("data/smallTest.csv");
        int num_nodes = path.numNodes();
        int num_edges = path.numEdges();
        path.createNewGraph("data/smallTest.csv");
        assert(num_nodes == path.numNodes());
        assert(num_edges == path.numEdges());
    }

    @Test
    public void medium4(){
        path = new ProfessorPaths();
        path.createNewGraph("data/middleTest.csv");
        int num_nodes = path.numNodes();
        int num_edges = path.numEdges();
        path.createNewGraph("data/middleTest.csv");
        assert(num_nodes == path.numNodes());
        assert(num_edges == path.numEdges());
    }

    @Test
    public void large1(){
        path = new ProfessorPaths();
        path.createNewGraph("data/bigTest.csv");
        int num_nodes = path.numNodes();
        int num_edges = path.numEdges();
        path.createNewGraph("data/bigTest.csv");
        assert(num_nodes == path.numNodes());
        assert(num_edges == path.numEdges());
    }

    @Test
    public void smallcsv(){
        path = new ProfessorPaths();
        path.createNewGraph("data/smallTest.csv");

        String path1 = path.findPath("Yathin Vemula", "Sid Srini");
        String path2 = path.findPath("Sid Srini", "Yathin Vemula");
        assert(path1.length() == path2.length());
        assert(!path1.equals(path2));

        path1 = path.findPath("Sid Nandy", "Sid Srini");
        path2 = path.findPath("Sid Srini", "Sid Nandy");
        assert(path1.length() == path2.length());
        assert(!path1.equals(path2));

        path1 = path.findPath("Ben Norheim", "Rayhan Khan");
        path2 = path.findPath("Rayhan Khan", "Ben Norheim");
        assert(path1.length() == path2.length());
        assert(!path1.equals(path2));
    }
    @Test
    public void mediumpath(){
        path = new ProfessorPaths();
        path.createNewGraph("data/middleTest.csv");
        String path1 = path.findPath("Skylar Garcia", "Alex Jones");
        String test1 = "path from Skylar Garcia to Alex Jones:\nSkylar Garcia to Alex Jones via PHYS-1100";
        path1 = path1.replaceAll("\\s", "");
        test1 = test1.replaceAll("\\s", "");
       // assert(path1.equals(test1));
        path1 = path.findPath("Alex Jones", "Morgan Martinez");
        test1 = "path from Alex Jones to Morgan Martinez:\nAlex Jones to Morgan Martinez via CSCI-2200";
        path1 = path1.replaceAll("\\s", "");
        test1 = test1.replaceAll("\\s", "");
        //assert(path1.equals(test1));
        path1 = path.findPath("Alex Jones", "Reese Johnson");
        test1 = "path from Alex Jones to Reese Johnson:\nAlex Jones to Reese Johnson via ENGR-1100";
        path1 = path1.replaceAll("\\s", "");
        test1 = test1.replaceAll("\\s", "");
        //assert(path1.equals(test1));
    }

    @Test
    public void largePath(){
        path = new ProfessorPaths();
        path.createNewGraph("data/hugeTest.csv");
        String path1 = path.findPath("First101 Last372", "First46 Last479 "); //Path exists and is of length 2
        String check = "path from First101 Last372 to First46 Last479 :\nFirst101 Last372 to First46 Last479 via bsCDs";
        path1 = path1.replaceAll("\\s", "");
        check = check.replaceAll("\\s", "");
        //assert(path1.equals(check));
        path1 = path.findPath("First371 Last127", "First371 Last127"); //to itself
        check = "path from First371 Last127 to First371 Last127:\n";
        path1 = path1.replaceAll("\\s", "");
        check = check.replaceAll("\\s", "");
        //assert(path1.equals(check));
        path1 = path.findPath("First19 Last477267", "First180 Last188"); //first one isnt a node
        check = "unknown professor First19 Last477267\n";
        path1 = path1.replaceAll("\\s", "");
        check = check.replaceAll("\\s", "");
        //assert(path1.equals(check));
    }
}