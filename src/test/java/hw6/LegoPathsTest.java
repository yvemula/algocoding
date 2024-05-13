package hw6;

import org.junit.jupiter.api.Test;

public class LegoPathsTest{
    private static LegoPaths path;

    @Test
    public void smallMedium(){
        path = new LegoPaths();
        path.createNewGraph("data/smallTest.csv");
        path.createNewGraph("data/middleTest.csv");
    }

    @Test
    public void mediumLarge(){
        path = new LegoPaths();
        path.createNewGraph("data/middleTest.csv");
        path.createNewGraph("data/bigTest.csv");
    }

    @Test
    public void largeSM(){
        path = new LegoPaths();
        path.createNewGraph("data/smallTest.csv");
        path.createNewGraph("data/bigTest.csv");
    }

    @Test
    public void MidleeSmall(){
        path = new LegoPaths();
        path.createNewGraph("data/middleTest.csv");
        path.createNewGraph("data/smallTest.csv");
    }

    @Test
    public void largetoSmall(){
        path = new LegoPaths();
        path.createNewGraph("data/bigTest.csv");
        path.createNewGraph("data/smallTest.csv");
    }

    @Test
    public void MediumfromLarge(){
        path = new LegoPaths();
        path.createNewGraph("data/bigTest.csv");
        path.createNewGraph("data/middleTest.csv");
    }

    @Test
    public void smalluptwo(){
        path = new LegoPaths();
        path.createNewGraph("data/smallTest.csv");
        path.createNewGraph("data/smallTest.csv");
    }

    @Test
    public void mediumoftwo(){
        path = new LegoPaths();
        path.createNewGraph("data/middleTest.csv");
        path.createNewGraph("data/middleTest.csv");
    }

    @Test
    public void largeoftwo(){
        path = new LegoPaths();
        path.createNewGraph("data/bigTest.csv");
        path.createNewGraph("data/bigTest.csv");
    }

  

}