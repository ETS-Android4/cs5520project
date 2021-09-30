package android.example.homework22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText text;
    private Button button;
    private TextView number;
    private int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button_count);
        text = findViewById(R.id.editText_main);
        number = findViewById(R.id.number);
        // Restore the state.
        if (savedInstanceState != null) {
            number.setText(savedInstanceState.getString("number"));
        }
    }

    public void countUp(View view) {
        num++;
        number.setText(Integer.toString(num));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("number",number.getText().toString());

    }
}