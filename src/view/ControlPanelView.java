package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by mark_ on 07-Feb-17
 */
public class ControlPanelView extends JPanel {

    /**
     * Buttons with a in it are add and buttons with m in it are minus
     */

    //private final static Dimension dimension = new Dimension(130,40);

    //first button row
    private JButton addAdHocCar, add50AdHocCar;
    //second button row
    private JButton addReservationCar, add50ReservationCar;
    //third button row
    private JButton addPassCar, add50PassCar;
    //fourth button row
    private JButton orderedParking;
    //fifth button row
    private JButton a10AdHocWeek, m10AdHocWeek;
    //sixth button row
    private JButton a10AdHocWeekend, m10AdHocWeekend;
    //seventh button row
    private JButton a10ReservWeek, m10ReservWeek;
    //eighth button row
    private JButton a10ReservWeekend, m10ReservWeekend;
    //ninth button row
    private JButton a10PassWeek, m10PassWeek;
    //tenth button row
    private JButton a10PassWeekend, m10PassWeekend;
    //eleventh button row
    private JButton a10PassAmount, m10PassAmount;
    //twelfth button row
    private JButton aEntranceSpeed, mEntranceSpeed;
    //thirteenth button row
    private JButton aPaymentSpeed, mPaymentSpeed;
    //fourteenth
    private JButton aExitSpeed, mExitSpeed;


    private JPanel firstButtonRow, secondButtonRow, thirdButtonRow, fourthButtonRow, fifthButtonRow, sixthButtonRow, seventhButtonRow, eighthButtonRow,
            ninthButtonRow, tenthButtonRow, eleventhButtonRow, twelfthButtonRow, thirteenthButtonRow, fourteenthButtonRow, panel;

    /**
     * Constructor of controlPanelView. Creates the panel with all the buttons to control the simulation
     */
    public ControlPanelView(){
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        createFourthButtonRow();
        createFirstButtonRow();
        createSecondButtonRow();
        createThirdButtonRow();

        createFifthButtonRow();
        createSixthButtonRow();
        createSeventhButtonRow();
        createEighthButtonRow();
        createNinthButtonRow();
        createTenthButtonRow();
        createEleventhButtonRow();
        createTwelfthButtonRow();
        createThirteenthButtonRow();
        createFourteenthButtonRow();

        add(panel,BorderLayout.WEST);
    }

    /**
     * Creates first button row
     */
    private void createFirstButtonRow(){
        firstButtonRow = new JPanel();
        firstButtonRow.setLayout(new BoxLayout(firstButtonRow, BoxLayout.LINE_AXIS));

        addAdHocCar = new JButton("   add 1 AdHoc car");
        firstButtonRow.add(addAdHocCar);

        add50AdHocCar = new JButton("add 50 AdHoc cars");
        firstButtonRow.add(add50AdHocCar);

        panel.add(firstButtonRow);
    }

    /**
     * Creates second button row
     */
    private void createSecondButtonRow(){

        secondButtonRow = new JPanel();
        secondButtonRow.setLayout(new BoxLayout(secondButtonRow, BoxLayout.LINE_AXIS));

        addPassCar = new JButton("   add 1 Pass Car");
        secondButtonRow.add(addPassCar);

        add50PassCar = new JButton("add 50 Pass Cars");
        secondButtonRow.add(add50PassCar);

        panel.add(secondButtonRow);
    }

    /**
     * Creates third button row
     */
    private void createThirdButtonRow(){
        thirdButtonRow = new JPanel();
        thirdButtonRow.setLayout(new BoxLayout(thirdButtonRow, BoxLayout.LINE_AXIS));

        addReservationCar = new JButton("   add 1 reservation car");
        thirdButtonRow.add(addReservationCar);

        add50ReservationCar = new JButton("add 50 reservation cars");
        thirdButtonRow.add(add50ReservationCar);

        panel.add(thirdButtonRow);
    }

    /**
     * Creates fourth button row
     */
    private void createFourthButtonRow(){
        fourthButtonRow = new JPanel();
        fourthButtonRow.setLayout(new BoxLayout(fourthButtonRow, BoxLayout.LINE_AXIS));

        orderedParking = new JButton("ordered parking");
        fourthButtonRow.add(orderedParking);
/*
        add50PassCar = new JButton("add 50 Pass Cars");
        fourthButtonRow.add(add50PassCar);
*/
        panel.add(fourthButtonRow);
    }

    /**
     * Creates fifth button row
     */
    private void createFifthButtonRow(){
        fifthButtonRow = new JPanel();
        fifthButtonRow.setLayout(new BoxLayout(fifthButtonRow, BoxLayout.LINE_AXIS));

        m10AdHocWeek = new JButton("-10 AdHoc/hour week");
        fifthButtonRow.add(m10AdHocWeek);

        a10AdHocWeek = new JButton("+10 AdHoc/hour week");
        fifthButtonRow.add(a10AdHocWeek);

        panel.add(fifthButtonRow);
    }

    /**
     * Creates sixth button row
     */
    private void createSixthButtonRow() {
        sixthButtonRow = new JPanel();
        sixthButtonRow.setLayout(new BoxLayout(sixthButtonRow, BoxLayout.LINE_AXIS));

        m10AdHocWeekend = new JButton("-10 AdHoc/hour weekend");
        sixthButtonRow.add(m10AdHocWeekend);

        a10AdHocWeekend = new JButton("+10 AdHoc/hour weekend");
        sixthButtonRow.add(a10AdHocWeekend);

        panel.add(sixthButtonRow);
    }

    /**
     * Creates seventh button row
     */
    private void createSeventhButtonRow() {
        seventhButtonRow = new JPanel();
        seventhButtonRow.setLayout(new BoxLayout(seventhButtonRow, BoxLayout.LINE_AXIS));

        m10ReservWeek = new JButton("-10 Reservations/hour week");
        seventhButtonRow.add(m10ReservWeek);

        a10ReservWeek = new JButton("+10 Reservations/hour week");
        seventhButtonRow.add(a10ReservWeek);

        panel.add(seventhButtonRow);
    }

    /**
     * Creates eighth button row
     */
    private void createEighthButtonRow() {
        eighthButtonRow = new JPanel();
        eighthButtonRow.setLayout(new BoxLayout(eighthButtonRow, BoxLayout.LINE_AXIS));

        m10ReservWeekend = new JButton("-10 Reservations/hour weekend");
        eighthButtonRow.add(m10ReservWeekend);

        a10ReservWeekend = new JButton("+10 Reservations/hour weekend");
        eighthButtonRow.add(a10ReservWeekend);

        panel.add(eighthButtonRow);
    }

    /**
     * Creates ninth button row
     */
    private void createNinthButtonRow() {
        ninthButtonRow = new JPanel();
        ninthButtonRow.setLayout(new BoxLayout(ninthButtonRow, BoxLayout.LINE_AXIS));

        m10PassWeek = new JButton("-10 Pass/hour week");
        ninthButtonRow.add(m10PassWeek);

        a10PassWeek = new JButton("+10 Pass/hour week");
        ninthButtonRow.add(a10PassWeek);

        panel.add(ninthButtonRow);
    }

    /**
     * Creates tenth button row
     */
    private void createTenthButtonRow() {
        tenthButtonRow = new JPanel();
        tenthButtonRow.setLayout(new BoxLayout(tenthButtonRow, BoxLayout.LINE_AXIS));

        m10PassWeekend = new JButton("-10 Pass/hour weekend");
        tenthButtonRow.add(m10PassWeekend);

        a10PassWeekend = new JButton("+10 Pass/hour weekend");
        tenthButtonRow.add(a10PassWeekend);

        panel.add(tenthButtonRow);
    }

    /**
     * Creates eleventh button row
     */
    private void createEleventhButtonRow() {
        eleventhButtonRow = new JPanel();
        eleventhButtonRow.setLayout(new BoxLayout(eleventhButtonRow, BoxLayout.LINE_AXIS));

        m10PassAmount = new JButton("-10 Passholders");
        eleventhButtonRow.add(m10PassAmount);

        a10PassAmount = new JButton("+10 Passholders");
        eleventhButtonRow.add(a10PassAmount);

        panel.add(eleventhButtonRow);
    }

    /**
     * Creates twelfth button row
     */
    private void createTwelfthButtonRow() {
        twelfthButtonRow = new JPanel();
        twelfthButtonRow.setLayout(new BoxLayout(twelfthButtonRow, BoxLayout.LINE_AXIS));

        mEntranceSpeed = new JButton("-1 Entrance Speed");
        twelfthButtonRow.add(mEntranceSpeed);

        aEntranceSpeed = new JButton("+1 Entrance Speed");
        twelfthButtonRow.add(aEntranceSpeed);

        panel.add(twelfthButtonRow);
    }

    /**
     * Creates thirteenth button row
     */
    private void createThirteenthButtonRow() {
        thirteenthButtonRow = new JPanel();
        thirteenthButtonRow.setLayout(new BoxLayout(thirteenthButtonRow, BoxLayout.LINE_AXIS));

        mPaymentSpeed = new JButton("-1 Payment Speed");
        thirteenthButtonRow.add(mPaymentSpeed);

        aPaymentSpeed = new JButton("+1 Payment Speed");
        thirteenthButtonRow.add(aPaymentSpeed);

        panel.add(thirteenthButtonRow);
    }

    /**
     * Creates fourteenth button row
     */
    private void createFourteenthButtonRow() {
        fourteenthButtonRow = new JPanel();
        fourteenthButtonRow.setLayout(new BoxLayout(fourteenthButtonRow, BoxLayout.LINE_AXIS));

        mExitSpeed = new JButton("-1 Exit Speed");
        fourteenthButtonRow.add(mExitSpeed);

        aExitSpeed = new JButton("+1 Exit Speed");
        fourteenthButtonRow.add(aExitSpeed);

        panel.add(fourteenthButtonRow);
    }

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void addAdHocListener(ActionListener listener)       {
        addAdHocCar.addActionListener(listener);
    }

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void add50AdHocListener(ActionListener listener) {
        add50AdHocCar.addActionListener(listener);
    }

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void addPassListener(ActionListener listener) {
        addPassCar.addActionListener(listener);
    }

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void add50PassListener(ActionListener listener) {
        add50PassCar.addActionListener(listener);
    }

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void addReservationListener(ActionListener listener) {
        addReservationCar.addActionListener(listener);
    }

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void add50ReservationsListener(ActionListener listener) {
        add50ReservationCar.addActionListener(listener);
    }

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void addOrderedParkingListener(ActionListener listener) {
        orderedParking.addActionListener(listener);
    }

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void aAdHocWeekListener(ActionListener listener) {a10AdHocWeek.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void mAdHocWeekListener(ActionListener listener) {m10AdHocWeek.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void aAdHocWeekendListener(ActionListener listener) {a10AdHocWeekend.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void mAdHocWeekendListener(ActionListener listener) {m10AdHocWeekend.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void a10ReservWeekListener(ActionListener listener) {a10ReservWeek.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void m10ReservWeekListener(ActionListener listener) {m10ReservWeek.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void a10ReservWeekendListener(ActionListener listener) {a10ReservWeekend.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void m10ReservWeekendListener(ActionListener listener) {m10ReservWeekend.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void a10PassWeekListener(ActionListener listener) {a10PassWeek.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void m10PassWeekListener(ActionListener listener) {m10PassWeek.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void a10PassWeekendListener(ActionListener listener) {a10PassWeekend.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void m10PassWeekendListener(ActionListener listener) {m10PassWeekend.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void a10PassAmountListener(ActionListener listener) {a10PassAmount.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void m10PassAmountListener(ActionListener listener) {m10PassAmount.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void aEntranceSpeedListener(ActionListener listener) {aEntranceSpeed.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void mEntranceSpeedListener(ActionListener listener) {mEntranceSpeed.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void aPaymentSpeedListener(ActionListener listener) {aPaymentSpeed.addActionListener(listener);}

    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     */
    public void mPaymentSpeedListener(ActionListener listener) {mPaymentSpeed.addActionListener(listener);}

    /*
    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     *//*
    public void aExitSpeedListener(ActionListener listener) {aExitSpeed.addActionListener(listener);}
*//*
    /**
     * Adds listener to button
     * @param listener - listener to be listened to
     *//*
    public void mExitSpeedListener(ActionListener listener) {mExitSpeed.addActionListener(listener);}*/
}
