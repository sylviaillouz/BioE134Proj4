package org.ucb.c5.Inventory;

import java.util.HashMap;
import java.util.Queue;

public class Box{
    private String boxDescription;
    private String boxName;
    private String boxThread;
    private String labLocation;
    private String boxTemperature;
    private Queue<Location> emptySpots;
    private HashMap<String, HashMap<Double, Location>> nameToConcToLoc;
    private HashMap[][] boxGrid;

    public Box (String name, String boxThread, String description, String labLocation, String temperature, Queue<Location> emptySpots, HashMap<String, HashMap<Double, Location>> nameToConcToLoc, HashMap[][] boxGrid) {

        this.boxName = name;
        this.boxDescription = description;
        this.boxThread = boxThread;
        this.labLocation = labLocation;
        this.boxTemperature = temperature;
        this.emptySpots = emptySpots;
        this.nameToConcToLoc = nameToConcToLoc;
        this.boxGrid = boxGrid;
    }

    public String getBoxName() {
        return boxName;
    }
    public String getBoxThread() {
        return boxThread;
    }
    public String getBoxDescription() {
        return boxDescription;
    }
    public String getLabLocation() {
        return labLocation;
    }
    public String getBoxTemperature() {
        return boxTemperature;
    }
    public Queue getEmptySpots() {
        return emptySpots;
    }
    public HashMap[][] getBoxGrid() {
        return boxGrid;
    }
    public boolean containsName(String name){
        return nameToConcToLoc.containsKey(name);
    }
    public Location put(String name, Double conc){
        Location filled = emptySpots.remove();
        HashMap concentrationAndLocation = new HashMap<Double, Location>();
        concentrationAndLocation.put(conc, filled);
        nameToConcToLoc.put(name, concentrationAndLocation);
        //update boxGrid of box attributes to values, used later to write output file
        int row = Integer.valueOf(filled.getRow());
        int col = Integer.valueOf(filled.getCol());
        HashMap<String, String> attributeMap = new HashMap();
        attributeMap.put("name", name);
        attributeMap.put("concentration", conc + " uM");
        boxGrid[row][col] = attributeMap;
        return filled;
    }
    public HashMap<Double, Location> get(String name){
        return nameToConcToLoc.get(name);
    }
}