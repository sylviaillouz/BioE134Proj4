package org.ucb.c5.labsheet;

import org.ucb.c5.constructionfile.model.ConstructionFile;
import org.ucb.c5.constructionfile.model.Operation;
import org.ucb.c5.constructionfile.model.Step;
import org.ucb.c5.inventory.Inventory;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class LabsheetConstructor {

    private HashMap<Operation, List<Step>> stepMap;
    private FileWriter fw;
    private Inventory inventory;
    private String thread_val;
    private Thread thread;
    

    public void initiate() throws Exception {
        inventory = new Inventory();
        inventory.initiate();
        thread = new Thread();
        thread.initiate();
    }

    //creating multiple labSheets for the number of construction files in cfs
    public void run(List<ConstructionFile> cfs) throws Exception {
        //doing the same string creation operation for the number of labsheets (i.e. outputting one file for ALL operations)
        thread_val = thread.get();
        File file = new File("Desktop/construction.doc");
        fw = new FileWriter(file);
        ArrayList<String> labsheet = new ArrayList<>();
        for (ConstructionFile cf : cfs) {
            //populating a hashmap (e.g 'PCR' --> List<PCR>) that splits all steps of the same type
            stepMap = new HashMap<>();
            for (Step step : cf.getSteps()) {
                Operation op = step.getOperation();
                if (stepMap.containsKey(op)) {
                    stepMap.get(op).add(step);
                } else {
                    ArrayList<Step> stepList = new ArrayList<>();
                    stepList.add(step);
                    stepMap.put(op, stepList);
                }
            }

            for (Operation op : stepMap.keySet()) {
                List<Step> steps = stepMap.get(op);
                String sheet = null;
                switch(op) {
                    case acquire:

                        break;
                    case pcr:
                        //PCRSheetGenerator takes in a list of PCR steps
                        sheet = PCRSheetGenerator.run(steps, inventory, thread); //Map<String,String>;

                 //           PCR pcrstep = (PCR) step;
                        break;
                    case digest:
                        break;
                    case ligate:
                        break;
                    case assemble:
                        break;
                    case cleanup:
                        break;
                    case transform:
                        sheet = TransformSheetGenerator.run(steps, inventory, thread);
                        labsheet.add(sheet);
                        sheet = PlateSheetGenerator.run(steps, inventory, thread);
                        labsheet.add(sheet);
                        sheet = PickSheetGenerator.run(steps, inventory, thread);
                        labsheet.add(sheet);
                        sheet = MiniprepSheetGenerator.run(steps, inventory, thread);
                        break;
                    default:
                        throw new Exception(op.toString() + "Operation not found to make sheet");
                }
                labsheet.add(sheet);
            }

            fw.close();
        }
    }

    private void writePCRSheet() {

    }
    
}