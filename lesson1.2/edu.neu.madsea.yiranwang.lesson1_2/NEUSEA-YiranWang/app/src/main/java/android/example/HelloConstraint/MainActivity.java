package android.example.HelloConstraint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import HelloConstraint.R;

public class MainActivity extends AppCompatActivity {
    private int mCount = 0;
    private TextView mShowCount;
    private Button mZero;
    private Button mCountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        mZero = (Button) findViewById(R.id.button_label_zero);
        mCountButton = (Button) findViewById(R.id.button_count);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        ++mCount;
        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
            // If the current count is larger than 0, update the zero button to blue.
            if(mCount > 0) mZero.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.colorPrimary));
            else mZero.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.colorPrimaryGray));
            //If the current count is an odd number, update the count button to bluish green.
            if(mCount % 2 == 1) view.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.bluishGreen));
                //Else, update it to pink.
            else if(mCount % 2 == 0) view.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.pink));
        }

    }

    public void zeroIt(View view) {
        mCount = 0;
        view.setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorPrimaryGray));
        mCountButton.setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorPrimary));
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
    }
}