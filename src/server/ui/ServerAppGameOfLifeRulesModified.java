package server.ui;

import server.exceptions.InvalidDensityException;
import server.exceptions.InvalidDimensionException;
import server.model.Vector3;
import server.model.WorldGenerator;
import server.model.WorldManager;
import server.rulesets.NeighbourhoodCellular;

import java.util.HashSet;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Main app
 */
public class ServerAppGameOfLifeRulesModified {
    public static void main(String[] args) {
        System.out.print("Starting server... ");

        NeighbourhoodCellular gameOfLife = new NeighbourhoodCellular();
        gameOfLife.setLowerBound(1);
        gameOfLife.setUpperBound(3);

        Set<Vector3> neighbourhood = new HashSet<>();

        neighbourhood.add(new Vector3(1,0,-1));
        neighbourhood.add(new Vector3(1,0,0));
        neighbourhood.add(new Vector3(1,0,1));
        neighbourhood.add(new Vector3(0,0,-1));
        neighbourhood.add(new Vector3(0,0,1));
        neighbourhood.add(new Vector3(-1,0,-1));
        neighbourhood.add(new Vector3(-1,0,0));
        neighbourhood.add(new Vector3(-1,0,1));

        gameOfLife.setNeighbourhood(neighbourhood);

        WorldManager.getInstance().setRule(gameOfLife);

        Thread worldThread = new Thread(WorldManager.getInstance());

        // Generate
        try {
            WorldGenerator.generate(0.1, 40, 20, 40);
        } catch (InvalidDensityException | InvalidDimensionException e) {
            e.printStackTrace();
        }

        //worldThread.start();
        System.out.println("OK");

        WorldManager.getInstance().addObserver(ConsoleUI.getInstance());
        WorldManager.getInstance().addObserver(PopulationGraphGUI.getInstance());
        WorldManager.getInstance().addObserver(VisualGUI.getInstance());

        // Launch all windows
        //Application.launch(PopulationGraphGUI.class, args);
        VisualGUI.main(args);
    }
}
