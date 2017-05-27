package tech.ceece.opencircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView textView = new TextView(this);
        textView.setText(AccountDataBase.getAccountDataBase().toString());
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_test);
        viewGroup.addView(textView);
    }

    /**
     * Pauses activity when back is pressed
     */
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
