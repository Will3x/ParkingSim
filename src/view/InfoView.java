package view;

import javax.swing.*;
import java.awt.*;

public class InfoView extends JPanel {
    private JLabel adhocCars;
    private JLabel parkingpassCars;

    public InfoView(){
        setMinimumSize(getPreferredSize());

        adhocCars = new JLabel();
        parkingpassCars = new JLabel();

        add(adhocCars);
        add(parkingpassCars);
    }


    public Dimension getPreferredSize() {
        return new Dimension(200,50);
    }

    public void drawInfo(String name, int amount){
        if (name.equals("adHoc")){
            this.adhocCars.setText("Aantal gewone auto's " + amount);
        }
        if (name.equals("pass")){
            this.parkingpassCars.setText("Aantal pashouders " + amount);
        }
    }
}
