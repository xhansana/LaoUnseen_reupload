package chill_order.com.laosunseen.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import chill_order.com.laosunseen.MainActivity;
import chill_order.com.laosunseen.R;
import chill_order.com.laosunseen.utility.MyAlert;

public class ServiceFragment extends Fragment {


    private String nameString, currentpostString, uidString, postSring;
    private String tag = "10AugV1";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FindMyMe();

//Post Controller
        postController();

    }    // Main Method

    private void FindMyMe() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        uidString = firebaseAuth.getCurrentUser().getUid();
        Log.d(tag, "FindMyMe: " + uidString);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("User").child(uidString);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();

                nameString = String.valueOf(map.get("nameString"));
                currentpostString = String.valueOf(map.get("myPostString"));
                Log.d(tag, "name ==>: " + nameString);
                Log.d(tag, "post ==>: " + currentpostString);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void postController() {
        Button button = getView().findViewById(R.id.btnPost);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = getView().findViewById(R.id.edtPost);
                postSring = editText.getText().toString().trim();
                if (postSring.isEmpty()) {
                    MyAlert myAlert = new MyAlert(getContext());
                    myAlert.normalDialog("Post False!"
                            , "Please type On Post...");
                } else {

                    editCurrentPost(postSring);
                    editText.setText("");

                }
            }
        });

    }

    private void editCurrentPost(String postSring) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("User").child(uidString);

        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("myPostString", changeMyData(postSring));
        databaseReference.updateChildren(stringObjectMap);

    }

    private String changeMyData(String postSring) {

        String resultString = null;

        resultString = currentpostString.substring(1,
                currentpostString.length() - 1);

        String[] strings = resultString.split(",");

        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < strings.length; i += 1) {

            stringArrayList.add(strings[i]);
        }

        stringArrayList.add(postSring);

        return stringArrayList.toString();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service, container, false);

        return view;
    }
}
