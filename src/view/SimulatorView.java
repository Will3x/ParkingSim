package view;

import javax.swing.*;
import java.awt.*;
import controller.Controller;
import model.Model;


public class SimulatorView extends JFrame {

    private CarParkView carParkView;
    private SettingsView setView;
    //private PiechartView pieView;

    private JPanel toolbar;
    private JPanel mainPanel;
    private JButton start;
    private JButton pause;
    private JButton quit;
    private SimulatorView simView;

    private JMenuBar menuBar;
    private JMenu setMenu;



    public SimulatorView(Model model) {

        carParkView = new CarParkView(simView,model );
        setView = new SettingsView(this, model);
        //pieView = new PiechartView(this, model);

        Controller controller = new Controller(model);

        mainPanel = new JPanel();
        this.toolbar = new JPanel();

        start = new JButton("start");
        pause = new JButton("pause");
        quit = new JButton("quit");

        setTitle("Menu Example");
        setSize(150, 150);

        // Creates a menubar for a JFrame
        menuBar = new JMenuBar();

        // Add the menubar to the frame
        setJMenuBar(menuBar);

        // Define and add two drop down menu to the menubar
        setMenu = new JMenu("Settings");
        menuBar.add(setMenu);

        // Create and add simple menu item to one of the drop down menu
        JMenuItem editSettings = new JMenuItem("edit settings");
        setMenu.add(editSettings);

        start.addActionListener(controller);
        pause.addActionListener(controller);
        quit.addActionListener(controller);

        toolbar.add(start);
        toolbar.add(pause);
        toolbar.add(quit);

        mainPanel.add(carParkView);
        //mainPanel.add(pieView);

        Container contentPane = getContentPane();

        contentPane.add(mainPanel, BoxLayout.X_AXIS);
        contentPane.add(setView, BorderLayout.NORTH); //TODO: Move to menu bar.

        toolbar.setLayout(new GridLayout(1, 0));

        setTitle("Parkeer Simulator");

        pack();
        setVisible(true);
        setResizable(false);


    }


    public CarParkView getCarParkView() {
        return carParkView;
    }
}