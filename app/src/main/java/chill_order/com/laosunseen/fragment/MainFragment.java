package chill_order.com.laosunseen.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import chill_order.com.laosunseen.R;
import chill_order.com.laosunseen.utility.MyAlert;

public class MainFragment extends Fragment {

    private String emailString, passwordString;

    private ProgressDialog progressDialog;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {//Method main
        super.onActivityCreated(savedInstanceState);
//Check Status
        checkStatus();

        //Register Controller
        registerController();

//		Login Controller
        loginController();

    }   //Method Main


    private void checkStatus() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMainFragment, new ServiceFragment())
                    .commit();
        }
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegist);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                replace fragment

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void loginController() {

        TextView textView = getView().findViewById(R.id.btnLogin);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText emailEditText = getView().findViewById(R.id.edtEMaillLogin);
                EditText passwordEditText = getView().findViewById(R.id.edtPassLogin);

//        Get Value From EditText

                emailString = emailEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();
//	Check Authentication
                if (emailString.isEmpty() || passwordString.isEmpty()) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog("Have Space",
                            "Please Fill all Textbox!");
                } else {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Login...");
                    progressDialog.setMessage("Please Wait few Minus...");
                    progressDialog.show();
                    checkAuthentication();
                }
            }
        });
    }

    private void checkAuthentication() {
//				Replace fragment Login To Service
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(emailString, passwordString)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                                    getActivity()
                                            .getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.contentMainFragment, new ServiceFragment())
                                            .addToBackStack(null)
                                            .commit();
                                    progressDialog.dismiss();
                                } else {
                                    progressDialog.dismiss();
                                    MyAlert myAlert = new MyAlert(getActivity());
                                    myAlert.normalDialog("Email Or Password Incorrect",
                                            "Please Try Again!!!");
                                }
                            }
                        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;

    }

}
