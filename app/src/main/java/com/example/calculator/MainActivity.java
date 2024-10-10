package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Boolean firstOpened = true;
    TextView solution, result;
    MaterialButton buttonC, buttonOpenB, buttonCloseB;
    MaterialButton button0, button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonPlus, buttonMinus, buttonMult, buttonDiv, buttonEqual;
    MaterialButton buttonAC, buttonDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        result = findViewById(R.id.result_tv);
        solution = findViewById(R.id.solution_tv);
        assignID(buttonC,R.id.button_c);
        assignID(buttonOpenB,R.id.button_open_bracket);
        assignID(buttonCloseB,R.id.button_close_bracket);
        assignID(button0,R.id.button_0);
        assignID(button1,R.id.button_1);
        assignID(button2,R.id.button_2);
        assignID(button3,R.id.button_3);
        assignID(button4,R.id.button_4);
        assignID(button5,R.id.button_5);
        assignID(button6,R.id.button_6);
        assignID(button7,R.id.button_7);
        assignID(button8,R.id.button_8);
        assignID(button9,R.id.button_9);
        assignID(buttonPlus,R.id.button_plus);
        assignID(buttonMinus,R.id.button_minus);
        assignID(buttonMult,R.id.button_multiply);
        assignID(buttonDiv,R.id.button_divide);
        assignID(buttonEqual,R.id.button_equal);
        assignID(buttonAC,R.id.button_ac);
        assignID(buttonDec,R.id.button_decimal);

        


    }

    /**
     * Assigns button objects to the defined buttions in activity_main.xml and sets there click listeners
     * @param btn the MaterialButton object
     * @param id the id assigned to each button in activity_main.xml
     */
    public void assignID(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    /**
     * Defines the function of the buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        if(firstOpened){
            solution.setText("");
            result.setText("0");
            firstOpened = false;
        }
        String buttonText = button.getText().toString();
        String dataToCalculate = solution.getText().toString();

        if(buttonText.equals("AC")){
            solution.setText("");
            result.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solution.setText(result.getText());
            return;
        }
        if(buttonText.equals("C")){
            if(dataToCalculate.length() == 1){
                solution.setText("");
                result.setText("0");
                return;
            }else{
                dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
            }
        }else{
            dataToCalculate = dataToCalculate+buttonText;
        }

        solution.setText(dataToCalculate);

        String finalResult = getResults(dataToCalculate);
        if(!finalResult.equals("Error")){
            result.setText(finalResult);
        }
    }

    /**
     * Evaluates the equation entered by the user
     * @param data The string of numbers and functions you want to evaluate
     * @return The result of the equation
     */
    String getResults(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
        }catch(Exception e){
            return "Error";
        }
    }
}