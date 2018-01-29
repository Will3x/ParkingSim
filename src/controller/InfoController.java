package controller;

import model.Model;
import model.PieChart02;
import view.InfoView;

public class InfoController {
    private Model model;
    private InfoView view;
    private PieChart02 pie;

    public InfoController(InfoView view, Model model){
        this.view = view;
        this.model = model;
        this.pie = pie;
    }

    public void updateView(){
        view.drawInfo("adHoc", model.getAdHocCars());
        view.drawInfo("pass", model.getPassCars());
    }
}
