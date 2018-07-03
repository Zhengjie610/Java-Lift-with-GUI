package controller;

import au.edu.uts.ap.javafx.*;
import model.*;
import javafx.fxml.*;
import javafx.scene.control.*;

/**
 * The controller for building.fxml.
 */
public class BuildingController extends Controller<Building> {
    @FXML private Slider delaySl;

    public final Building getBuilding() { return model; }

    @FXML private void initialize() {
        // Start up the building. Don't forget to also shutdown the building
        // when the user clicks the "Exit" button.
        getBuilding().startup();
    }

    /**
     * This accessor method returns the selected number on the delay slider.
     *
     * @return the the selected delay
     */
    private int getDelay() {
        return (int)delaySl.getValue();
    }
}
