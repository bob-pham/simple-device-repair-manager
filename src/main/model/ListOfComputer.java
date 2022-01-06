package model;

import java.util.ArrayList;

public interface ListOfComputer {

    //EFFECTS: returns a list of service numbers of computers with CPU name
    ArrayList<Integer> serviceNumsIsCpu(String name, boolean repaired);

    //EFFECTS: returns a list of service numbers of computers with GPU name
    ArrayList<Integer> serviceNumsIsGpu(String name, boolean repaired);

}
