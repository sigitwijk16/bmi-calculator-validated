package com.gitz.bmiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private EditText ageEditText;
    private EditText feetEditText;
    private Button calculateButton;
    private EditText inchesEditText;
    private EditText weightEditText;
    private TextView resultText;
    private String feetText;
    private String inchesText;
    private String weightText;
    private String ageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setupButtonClickListener();
    }

    private void findViews() {
        maleRadioButton = findViewById(R.id.radio_button_male);
        femaleRadioButton = findViewById(R.id.radio_button_female);
        ageEditText = findViewById(R.id.edit_text_age);
        feetEditText = findViewById(R.id.edit_text_feet);
        inchesEditText = findViewById(R.id.edit_text_inches);
        weightEditText = findViewById(R.id.edit_text_weight);
        calculateButton = findViewById(R.id.button_calculate);
        resultText = findViewById(R.id.text_view_result);
    }

    private void setupButtonClickListener() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    boolean isFormValid = validateForm();

                    if (isFormValid) {
                        int age = Integer.parseInt(ageText);

                        double bmiResult = calculateBmi();

                        if (age >= 18) {
                            displayResult(bmiResult);
                        } else {
                            displayGuidance(bmiResult);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                    }
            }
        });
    }

    private boolean validateForm () {
        feetText = feetEditText.getText().toString();
        inchesText = inchesEditText.getText().toString();
        weightText = weightEditText.getText().toString();
        ageText = ageEditText.getText().toString();

        return !ageText.isEmpty() && !feetText.isEmpty() && !inchesText.isEmpty() && !weightText.isEmpty();
    }

    private double calculateBmi() {
        int feet = Integer.parseInt(feetText);
        int inches = Integer.parseInt(inchesText);
        int weight = Integer.parseInt(weightText);

        int totalInches = (feet * 12) + inches;
        double meterHeight = totalInches * 0.0254;

        return weight / (meterHeight * meterHeight);
    }

    private void displayResult(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String formattedBmi = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (bmi < 18.5) {
            fullResultString = formattedBmi + " - You are underweight";
        } else if (bmi > 25) {
            fullResultString = formattedBmi + " - You are overweight";
        } else {
            fullResultString = formattedBmi + " - You are a healthy weight";
        }

        resultText.setText(fullResultString);
    }


    private void displayGuidance(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String formattedBmi = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (maleRadioButton.isChecked()) {
            fullResultString = formattedBmi + " - As you are under 18, consult with your doctor for healthy range for boys";
        } else if (femaleRadioButton.isChecked()) {
            fullResultString = formattedBmi + " - As you are under 18, consult with your doctor for healthy range for girls";
        } else {
            fullResultString = formattedBmi + " - As you are under 18, consult with your doctor for healthy range";
        }

        resultText.setText(fullResultString);
    }
}