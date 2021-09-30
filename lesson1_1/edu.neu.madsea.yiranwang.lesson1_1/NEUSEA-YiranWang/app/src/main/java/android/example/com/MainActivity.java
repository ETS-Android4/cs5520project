package android.example.com;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "This is for debugging!");
        Log.i("MainActivity", "This is an info!");
        Log.w("MainActivity", "This is a warning!");
        Log.e("MainActivity", "This is an error!");
        Log.v("MainActivity", "Hello World!");

    }
}