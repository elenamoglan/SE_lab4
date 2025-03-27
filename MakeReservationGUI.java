package com.example.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class MakeReservationGUI extends Application {
    private final List<String> reservations = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Make a Reservation");

        Label titleLabel = new Label("Car Rental Reservation");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        ComboBox<String> vehicleTypeCombo = new ComboBox<>();
        vehicleTypeCombo.getItems().addAll("Sedan", "SUV", "Convertible", "Truck");
        ComboBox<String> pickupOfficeCombo = new ComboBox<>();
        pickupOfficeCombo.getItems().addAll("Downtown", "Airport", "Suburbs");

        Button checkAvailabilityButton = new Button("Check Availability");
        Label availabilityLabel = new Label();

        Button confirmButton = new Button("Confirm Reservation");
        Label confirmationLabel = new Label();
        confirmButton.setDisable(true);

        checkAvailabilityButton.setOnAction(e -> {
            boolean available = Math.random() > 0.2; // 80% chance available
            if (available) {
                double price = 50 + Math.random() * 100; // Random price between 50 and 150
                availabilityLabel.setText("Vehicle available.");
                confirmButton.setUserData(price); // Store price in button
                confirmButton.setDisable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Available Vehicles");
                alert.setHeaderText("No vehicles available at the selected office.");
                alert.setContentText("Please change your preferences or cancel the reservation.");
                alert.showAndWait();
                confirmButton.setDisable(true);
            }
        });

        confirmButton.setOnAction(e -> {
            String rentalNumber = "RN" + (int) (Math.random() * 10000);

            // Retrieve stored price
            double price = (double) confirmButton.getUserData();

            Alert priceAlert = new Alert(Alert.AlertType.CONFIRMATION);
            priceAlert.setTitle("Confirm Price");
            priceAlert.setHeaderText("Do you agree to the price?");
            priceAlert.setContentText("The price is: $" + String.format("%.2f", price));

            ButtonType agreeButton = new ButtonType("Agree");
            ButtonType changePreferencesButton = new ButtonType("Change Preferences");
            ButtonType cancelButton = new ButtonType("Cancel");

            priceAlert.getButtonTypes().setAll(agreeButton, changePreferencesButton, cancelButton);

            double finalPrice = price;
            priceAlert.showAndWait().ifPresent(response -> {
                if (response == agreeButton) {
                    String reservationDetails = "Rental Number: " + rentalNumber + "\n" +
                            "Start Date: " + startDatePicker.getValue() + "\n" +
                            "End Date: " + endDatePicker.getValue() + "\n" +
                            "Vehicle Type: " + vehicleTypeCombo.getValue() + "\n" +
                            "Pickup Office: " + pickupOfficeCombo.getValue() + "\n" +
                            "Price: $" + String.format("%.2f", finalPrice);

                    reservations.add(reservationDetails);

                    Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                    confirmationAlert.setTitle("Reservation Confirmed");
                    confirmationAlert.setHeaderText("Your reservation has been confirmed!");
                    confirmationAlert.setContentText(reservationDetails);
                    confirmationAlert.showAndWait();

                    confirmationLabel.setText("Reservation confirmed! Rental Number: " + rentalNumber);
                } else if (response == changePreferencesButton) {
                    availabilityLabel.setText("Please update your preferences and check again.");
                    confirmButton.setDisable(true);
                } else {
                    availabilityLabel.setText("Reservation canceled.");
                    confirmButton.setDisable(true);
                }
            });
        });

        VBox layout = new VBox(10, titleLabel, new Label("Start Date:"), startDatePicker,
                new Label("End Date:"), endDatePicker, new Label("Vehicle Type:"), vehicleTypeCombo,
                new Label("Pickup Office:"), pickupOfficeCombo, checkAvailabilityButton, availabilityLabel,
                confirmButton, confirmationLabel);
        layout.setPadding(new Insets(15));

        primaryStage.setScene(new Scene(layout, 350, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
