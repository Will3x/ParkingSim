package view;

import javax.swing.*;
import java.awt.*;

public class InfoView extends JPanel {
    private JLabel adhocCars;
    private JLabel parkingpassCars;
    private Dimension size;

    public InfoView(){
        size = new Dimension(200,180);
        setMinimumSize(size);

        adhocCars = new JLabel();
        parkingpassCars = new JLabel();

        add(adhocCars);
        add(parkingpassCars);
    }


    public Dimension getPreferredSize() {
        return new Dimension(200,180);
    }

    public void drawInfo(String name, int amount){
        if (name.equals("adhoc")){
            this.adhocCars.setText("Aantal gewone auto's " + amount);
        } else if (name.equals("pass")){
            this.parkingpassCars.setText("Aantal pashouders " + amount);
        }
    }
}
