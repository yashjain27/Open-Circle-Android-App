package tech.ceece.opencircle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Data fields
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
//        intent.putExtra("checkbox", checkBox.isChecked());
    }

    public void onSignUp(View v){
        intent = new Intent(this, SignUp.class);
        startActivity(new Intent(this, SignUp.class));
    }
}
