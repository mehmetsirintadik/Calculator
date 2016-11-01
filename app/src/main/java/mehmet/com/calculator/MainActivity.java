package mehmet.com.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    public int[] numericButtons = {R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour,
            R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine};
    public int[] operationButtons = {R.id.btnAdd, R.id.btnSubTrack, R.id.btnMultiply, R.id.btnDivide};

    public TextView textScreen;

    public boolean stateError;
    public boolean lastNumeric;
    public boolean lastDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textScreen = (TextView) findViewById(R.id.textScreen);
        setNumericOnClickListener();
        setOperationOnClickListener();

        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastNumeric && !stateError && !lastDot){
                    textScreen.append(".");
                    lastDot = true;
                    lastNumeric = false;
                }
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textScreen.setText("");
                lastNumeric = false;
                lastDot = false;
                stateError = false;
            }
        });

        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEqual();
            }
        });

    }


    public void setNumericOnClickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (stateError){
                    textScreen.setText(button.getText());
                    stateError = false;
                }else{
                    textScreen.append(button.getText());
                }
                lastNumeric = true;
            }
        };

        for (int id: numericButtons ) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    public void setOperationOnClickListener(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if(lastNumeric && !stateError){
                    textScreen.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;
                }
            }
        };

        for (int id: operationButtons) {
            findViewById(id).setOnClickListener(listener);
        }

    }

    public void onEqual() {
        if (lastNumeric && !stateError){
            String txt = textScreen.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try{
                double result = expression.evaluate();
                textScreen.setText(Double.toString(result));
                lastDot=true;
            }catch (ArithmeticException ex){
                textScreen.setText(ex.toString());
                stateError =true;
                lastNumeric = false;
            }
        }
    }
}
