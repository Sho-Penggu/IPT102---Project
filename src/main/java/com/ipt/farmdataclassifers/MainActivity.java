package com.ipt.farmdataclassifers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize input fields
        final EditText pHInput = findViewById(R.id.editTextPH);
        final EditText nInput = findViewById(R.id.editTextN);
        final EditText pInput = findViewById(R.id.editTextP);
        final EditText kInput = findViewById(R.id.editTextK);
        final EditText moistureInput = findViewById(R.id.editTextSoilMoisture);
        final EditText rainfallInput = findViewById(R.id.editTextRainfall);
        final EditText tempInput = findViewById(R.id.editTextTemperature);
        final EditText humidityInput = findViewById(R.id.editTextHumidity);

        // Initialize output fields
        final TextView classificationOutput = findViewById(R.id.outputTextView);

        // Button to calculate classification and recommendations
        Button calculateButton = findViewById(R.id.btnClassify);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Read inputs
                    double pH = Double.parseDouble(pHInput.getText().toString());
                    double n = Double.parseDouble(nInput.getText().toString());
                    double p = Double.parseDouble(pInput.getText().toString());
                    double k = Double.parseDouble(kInput.getText().toString());
                    double moisture = Double.parseDouble(moistureInput.getText().toString());
                    double rainfall = Double.parseDouble(rainfallInput.getText().toString());
                    double temperature = Double.parseDouble(tempInput.getText().toString());
                    double humidity = Double.parseDouble(humidityInput.getText().toString());

                    // Classification logic
                    ArrayList<String> classifications = classifyData(pH, n, p, k, moisture, rainfall, temperature, humidity);

                    // Generate recommendations for all classifications
                    StringBuilder recommendations = new StringBuilder();
                    for (String classification : classifications) {
                        recommendations.append("- ").append(generateRecommendations(classification)).append("\n");
                    }

                    // Display results
                    classificationOutput.setText("Classifications: \n" + String.join(", ", classifications) +
                            "\n\nRecommendations: \n" + recommendations.toString().trim());

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Please enter valid inputs for all fields.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Function to classify data based on thresholds
    private ArrayList<String> classifyData(double pH, double n, double p, double k, double moisture, double rainfall, double temperature, double humidity) {
        ArrayList<String> classifications = new ArrayList<>();

        if (pH < 5.5) {
            classifications.add("Acidic Soil");
        }
        if (pH > 7.5) {
            classifications.add("Alkaline Soil");
        }
        if (n < 50) {
            classifications.add("Low Nitrogen");
        }
        if (p < 30) {
            classifications.add("Low Phosphorus");
        }
        if (k < 150) {
            classifications.add("Low Potassium");
        }
        if (moisture < 30) {
            classifications.add("Dry Soil");
        }
        if (rainfall < 100) {
            classifications.add("Low Rainfall");
        }
        if (temperature < 20) {
            classifications.add("Cold Temperature");
        }
        if (temperature > 35) {
            classifications.add("Hot Temperature");
        }
        if (humidity < 40) {
            classifications.add("Low Humidity");
        }
        if (classifications.isEmpty()) {
            classifications.add("Optimal Conditions");
        }

        return classifications;
    }

    // Function to generate recommendations based on classification
    private String generateRecommendations(String classification) {
        switch (classification) {
            case "Acidic Soil":
                return "Apply lime to increase soil pH.";
            case "Alkaline Soil":
                return "Add sulfur to lower soil pH.";
            case "Low Nitrogen":
                return "Use nitrogen-rich fertilizers.";
            case "Low Phosphorus":
                return "Apply phosphorus fertilizers like superphosphate.";
            case "Low Potassium":
                return "Use potassium-based fertilizers like muriate of potash.";
            case "Dry Soil":
                return "Increase irrigation or improve water retention.";
            case "Low Rainfall":
                return "Plan for supplemental irrigation.";
            case "Cold Temperature":
                return "Consider cold-tolerant crop varieties.";
            case "Hot Temperature":
                return "Provide shading or mulching to reduce heat stress.";
            case "Low Humidity":
                return "Increase humidity using water sprays or misters.";
            case "Optimal Conditions":
                return "Maintain current practices. Conditions are favorable.";
            default:
                return "No specific recommendation available.";
        }
    }
}
