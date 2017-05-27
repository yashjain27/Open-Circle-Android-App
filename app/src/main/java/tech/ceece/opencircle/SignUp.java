package tech.ceece.opencircle;

import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.InputMismatchException;

public class SignUp extends AppCompatActivity {
    //Data field
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup);
    }

    public void onSignUp(View v){
        try {
            editText = (EditText) findViewById(R.id.editText);
            String userName = editText.getText().toString();
            editText = (EditText) findViewById(R.id.editText2);
            String firstName = editText.getText().toString();
            editText =  (EditText) findViewById(R.id.editText3);
            String lastName = editText.getText().toString();
            editText = (EditText) findViewById(R.id.editText4);
            String email = editText.getText().toString();
            editText = (EditText) findViewById(R.id.editText5);
            String phoneNumber = editText.getText().toString();
            editText = (EditText) findViewById(R.id.editText6);
            String password = editText.getText().toString();
            Password pass = new Password(password);
            editText = (EditText) findViewById(R.id.editText9);
            String rePassword = editText.getText().toString();

            if(!password.equals(rePassword))
                throw new InputMismatchException();

            //Create account and try adding to the DataBase
            Account account = new Account(userName, firstName, lastName, email, phoneNumber, pass);
            AccountDataBase.getAccountDataBase().addAccount(account);
            Toast.makeText(this, "Congratulations! You have successfully signed up!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Test.class);
            intent.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } catch (InputMismatchException e) {
            Toast.makeText(this, "Passwords don't match! Try again!", Toast.LENGTH_SHORT).show();
        } catch (IllegalPasswordException e){
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (UserNameAlreadyExistsException e){
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (AccountAlreadyExistsException e){
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
