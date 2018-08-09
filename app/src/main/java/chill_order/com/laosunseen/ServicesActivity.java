package chill_order.com.laosunseen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import chill_order.com.laosunseen.fragment.ServiceFragment;

public class ServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentServiceFragment, new ServiceFragment())
                    .commit();
        }


    }   //Main Method

}
