package controller;

import model.Model;
import view.InfoView;

public class InfoController {
    private Model model;
    private InfoView view;

    public InfoController(InfoView view, Model model){
        this.view = view;
        this.model = model;
    }

    public void updateView(){
        view.drawInfo("adHoc", model.getAdHocCars());
        view.drawInfo("pass", model.getPassCars());
    }
}
