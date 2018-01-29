package controller;

import model.Model;
import view.HistoGraph;
import view.LineGraph;
import javax.swing.*;

public class GraphController {
    Model model;
    private HistoGraph histoGraph;
    private LineGraph lineGraph;
    private JFrame jFrame;


    public GraphController(Model model, HistoGraph histoGraph, LineGraph lineGraph){
        this.model = model;
        this.histoGraph = histoGraph;
        this.lineGraph = lineGraph;
        updateView();
    }

    public void updateView(){
        //histoGraph.update();
        lineGraph.update(model.getAdHocCars(), model.getPassCars(), model.getReservationCars());
    }
    public int getNumberOfOpenSpots(){
        return model.getNumberOfOpenSpots();
    }
}
