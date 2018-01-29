package controller;

import main.Simulator;
import model.Model;
import model.Time;
import view.ControlPanelView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class ControlPanelController{
        private model.Model model;
        private view.ControlPanelView controlPanelView;
        private Time time = Time.getInstance();

        public ControlPanelController(Model model, ControlPanelView controlPanelView){
            this.model = model;
            this.controlPanelView = controlPanelView;

            createFristRowListeners();
            createSecondRowListeners();
            createThirdRowListeners();
            createFourthRowListeners();
            createFifthRowListeners();
            createSixthRowListeners();
            createSeventhRowListeners();
            createEighthRowListeners();
            createNinthRowListeners();
            createTenthRowListeners();
            createEleventhRowListeners();
            createTwelfthRowListeners();
            createThirteenthRowListeners();
            createFourteenthRowListeners();

        }

        private void createFristRowListeners(){
            controlPanelView.addAdHocListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    model.addNewCar(null, "ADHOC");
                }
            });

            controlPanelView.add50AdHocListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.addNewCar(50, "ADHOC");
                }
            });

        }

        /**
         * Creates listeners for the second button row of the control panel
         */
        private void createSecondRowListeners() {
            controlPanelView.addPassListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.addNewCar(null, "PASS");
                }
            });
            controlPanelView.add50PassListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.addNewCar(50, "PASS");
                }
            });
        }

        /**
         * Creates listeners for the third button row of the control panel
         */
        private void createThirdRowListeners() {
            controlPanelView.addReservationListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.addNewCar(null, "RSRV");
                }
            });
            controlPanelView.add50ReservationsListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.addNewCar(50, "RSRV");
                }
            });
        }

        /**
         * Creates listeners for the fourth button row of the control panel
         */
        private void createFourthRowListeners() {
            controlPanelView.addOrderedParkingListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.switchOCDParking();
                }
            });
        }

        /**
         * Creates listeners for the fifth button row of the control panel
         */
        private void createFifthRowListeners() {
            controlPanelView.aAdHocWeekListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusWeekDayArrivals();
                }
            });

            controlPanelView.mAdHocWeekListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusWeekDayArrivals();
                }
            });
        }

        /**
         * Creates listeners for the sixth button row of the control panel
         */
        private void createSixthRowListeners() {
            controlPanelView.aAdHocWeekendListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusWeekendArrivals();
                }
            });

            controlPanelView.mAdHocWeekendListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusWeekendArrivals();
                }
            });
        }

        /**
         * Creates listeners for the seventh button row of the control panel
         */
        private void createSeventhRowListeners() {
            controlPanelView.a10ReservWeekListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusWeekDayReservations();
                }
            });

            controlPanelView.m10ReservWeekListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusWeekDayReservations();
                }
            });
        }

        /**
         * Creates listeners for the eighth button row of the control panel
         */
        private void createEighthRowListeners() {
            controlPanelView.a10ReservWeekendListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusWeekendReservations();
                }
            });

            controlPanelView.m10ReservWeekendListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusWeekendReservations();
                }
            });
        }

        /**
         * Creates listeners for the ninth button row of the control panel
         */
        private void createNinthRowListeners() {
            controlPanelView.a10PassWeekListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusWeekDayPassArrivals();
                }
            });

            controlPanelView.m10PassWeekListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusWeekDayPassArrivals();
                }
            });
        }

        /**
         * Creates listeners for the tenth button row of the control panel
         */
        private void createTenthRowListeners() {
            controlPanelView.a10PassWeekendListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusWeekendPassArrivals();
                }
            });

            controlPanelView.m10PassWeekendListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusWeekendPassArrivals();
                }
            });
        }

        /**
         * Creates listeners for the eleventh button row of the control panel
         */
        private void createEleventhRowListeners() {
            controlPanelView.a10PassAmountListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusPassholderAmmount();
                }
            });

            controlPanelView.m10PassAmountListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusPassholderAmmount();
                }
            });
        }

        /**
         * Creates listeners for the twelfth  button row of the control panel
         */
        private void createTwelfthRowListeners() {
            controlPanelView.aEntranceSpeedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusEnterSpeed();
                }
            });

            controlPanelView.mEntranceSpeedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusEnterSpeed();
                }
            });
        }

        /**
         * Creates listeners for the thirteenth  button row of the control panel
         */
        private void createThirteenthRowListeners() {
            controlPanelView.aPaymentSpeedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusPaymentSpeed();
                }
            });

            controlPanelView.mPaymentSpeedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusPaymentSpeed();
                }
            });
        }

        /**
         * Creates listeners for the fourteenth  button row of the control panel
         */
        private void createFourteenthRowListeners() {
            controlPanelView.aPaymentSpeedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.plusExitSpeed();
                }
            });

            controlPanelView.mPaymentSpeedListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    model.minusExitSpeed();
                }
            });
        }

    }

