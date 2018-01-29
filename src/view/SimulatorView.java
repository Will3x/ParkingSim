package view;

import controller.*;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

public class SimulatorView {

    //Models
    private Model simulatorModel;
    private Winnings winningsModel;

    //Controllers
    private GraphController graphController;
    private TimeController timeController;
    private InfoController infoController;
    private WinningsController winningsController;
    private SimulatorController simulatorController;
    private EventController eventController;
    private AddEventController addEventController;
    private StepController stepController;

    //Views
    private HistoGraph histoGraph;
    private LineGraph lineGraph;
    private StepInterface stepInterface;
    private CarParkView carParkView;
    private TimeView timeView;
    private InfoView infoView;
    private EventView eventView;
    private view.addEventView addEventView;
    private Timer timer;


    /***
     * Constructor for Simulator. Creates all Models, Views and Controllers.
     */
    public SimulatorView() {

        //Winnings

        WinningsView winningsView = new WinningsView();
        //winningsModel = new Winnings(0.0, 0.0);
        winningsController = new WinningsController(winningsView);

        //Models
        simulatorModel = new Model(3, 6, 30);


        //SimulatorView
        timeView = new TimeView();
        carParkView = new CarParkView(this,simulatorModel);

        histoGraph = new HistoGraph(800, 200, 10000, 0, winningsController);
        lineGraph = new LineGraph(800, 200, simulatorModel.getNumberOfPlaces() * simulatorModel.getNumberOfRows() * simulatorModel.getNumberOfFloors());
        stepInterface = new StepInterface();
        infoView = new InfoView();
        eventView = new EventView();
//        controlPanelView = new ControlPanelView();
        addEventView = new addEventView();


        //Controllers
        //graphController = new GraphController(simulatorModel, histoGraph, lineGraph);
        timeController = new TimeController(timeView);
        graphController = new GraphController(simulatorModel, histoGraph, lineGraph);
        infoController = new InfoController(infoView, simulatorModel);
        eventController = new EventController(eventView, simulatorModel);
        addEventController = new AddEventController(addEventView, simulatorModel);
//        controlPanelController = new ControlPanelController(simulatorModel, controlPanelView);
        //infoController = new InfoController(infoView, simulatorModel);
        stepController = new StepController(stepInterface, simulatorModel);

        simulatorController = new SimulatorController(carParkView, simulatorModel);

        //Controllers
//        //timeController = new TimeController(timeView, time);
//        graphController = new GraphController(simulatorModel, histoGraph, lineGraph);
//        infoController = new InfoController(infoView, simulatorModel);
//        eventController = new EventController(eventView, simulatorModel);
        //simulatorController = new SimulatorController(carParkView, simulatorModel, timeController, graphController, infoController, eventController, stepInterface);


        makeJPanel();

        //Calls all updates for simulators
        simulatorController.updateCarParkView();
//        graphController.updateView();
//        infoController.updateView();

        simulate();

    }

    /**
     * Makes the JPanel where all views are going to be added.
     */
    private void makeJPanel(){
        JFrame jFrame = new JFrame();
        JPanel jPanel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Opbrengst", histoGraph);
        tabbedPane.addTab("Auto's", lineGraph);

        JPanel pnl = new JPanel();
        pnl.setPreferredSize(new Dimension(lineGraph.getWidth(), lineGraph.getHeight()));
        pnl.add(tabbedPane);
        JPanel carParkPanel = new JPanel();
        carParkPanel.setLayout(new FlowLayout(1));
        carParkPanel.add(carParkView);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
        southPanel.add(stepInterface);
        southPanel.add(pnl);

        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.add(infoView, BorderLayout.NORTH);
        westPanel.add(eventView, BorderLayout.SOUTH);

        jPanel.add(carParkPanel, BorderLayout.CENTER);
        jPanel.add(timeView, BorderLayout.NORTH);
        jPanel.add(westPanel, BorderLayout.WEST);
        jPanel.add(southPanel, BorderLayout.SOUTH);


        jFrame.setJMenuBar(makeMenu());

        jFrame.add(jPanel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(true);
        jFrame.setVisible(true);
    }

    /**
     * Makes the menu.
     * @return menuBar - Return the JMenuBar that is made.
     */
    private JMenuBar makeMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu( "Add");

        JMenuItem eventItem = eventItem();

        menu.add(eventItem);
        menuBar.add(menu);
        return menuBar;
    }

    /**
     * Makes a menuItem
     * @return addEvent - JMenuItem that is made
     */
    private JMenuItem eventItem() {
        JMenuItem addEvent = new JMenuItem("Add Event");
        addEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEventView.makeFrame();
            }
        });
        return addEvent;
    }

    /**
     * Will run as long as there are steps left.
     */
    private void simulate() {
        timer = new java.util.Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (0 < simulatorModel.getSteps()) {
                    //counter++;
                    tick();
                    simulatorModel.reduceOneStep();
                    stepInterface.disableStepButtons();
                } else {
                    stepInterface.disableStop();
                    //cancel();
                }
            }
        }, 0, 10);
    }

    /**
     * Calls every method that should be called when a minute passes.
     */
    private void tick() {
        //timeController.updateTime();
        //timeController.setModelTime(model.getDay(), model.getHour(),model.getMinute());
        timeController.updateTime();
        simulatorModel.handleReservations();
        simulatorModel.handleExit();
        simulatorModel.handleEntrance();
        simulatorModel.tick();
        updateViews();
    }

    /**
     * Updates the views.
     */
    private void updateViews() {
        simulatorController.updateCarParkView();
        graphController.updateView();
        infoController.updateView();
        eventController.updateView();
    }
}
