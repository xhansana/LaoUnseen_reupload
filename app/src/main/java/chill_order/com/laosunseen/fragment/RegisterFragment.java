package chill_order.com.laosunseen.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;

import chill_order.com.laosunseen.MainActivity;
import chill_order.com.laosunseen.R;
import chill_order.com.laosunseen.utility.MyAlert;
import chill_order.com.laosunseen.utility.UserModel;

public class RegisterFragment extends Fragment {

    //            Explicit
    private Uri uri;
    private ImageView imageView;
    private boolean aBoolean = true;
    private String nameString, emailString, passwordString, uidString, pathURLString,
            myPostString;
    private ProgressDialog progressDialog;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.menu_register, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemUpload) {
            uploadProcess();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void uploadProcess() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Upload Value Process");
        progressDialog.setMessage("Please Wait few Minus...");
        progressDialog.show();

        EditText nameEditText = getView().findViewById(R.id.edtUName);
        EditText emailEditText = getView().findViewById(R.id.edtEMail);
        EditText passwordEditText = getView().findViewById(R.id.edtPass);

//        Get Value From EditText
        nameString = nameEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

//        Check Choose Photo
        if (aBoolean) {
//            Non Choose Photo
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.normalDialog("Non Choose Photo",
                    "Please Choose Photo");

            progressDialog.dismiss();
        } else if (nameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty()) {

//            Have Space
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.normalDialog("Have Space",
                    "Please Fill All Every Blank");

            progressDialog.dismiss();

        } else {

//            No Space
            createAuthentication();
            uploadPhotoToFirebase();

        }


    }


    private void createPost() {

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Hello");
        myPostString = stringArrayList.toString();
        Log.d("9AugV1", "myPost ==> " + myPostString);

    }

    private void createAuthentication() {


        Log.d("8AugV1", "CreateAuthen Work");

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            uidString = firebaseAuth.getCurrentUser().getUid();
                            Log.d("8AugV1", "uidString ==> " + uidString);

                        } else {
                            MyAlert myAlert = new MyAlert(getActivity());
                            myAlert.normalDialog("Cannot Register",
                                    "Because ==> " + task.getException().getMessage());
                            Log.d("8AugV1", "Error ==> " + task.getException().getMessage());
                            progressDialog.dismiss();
                        }
                    }
                });


    }   //Create Authentication

    private void uploadPhotoToFirebase() {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference storageReference1 = storageReference.child("Avata/" + nameString);


        storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Success Upload Photo", Toast.LENGTH_SHORT).show();
                findPathURLphoto();
                createPost();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Cannot Upload Photo", Toast.LENGTH_SHORT).show();
                Log.d("8AugV1", "e==> " + e.toString());
                progressDialog.dismiss();
            }
        });


    }   // upload Photo

    private void findPathURLphoto() {

        try {

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            final String[] urlStrings = new String[1];

            storageReference.child("Avata").child(nameString)
                    .getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urlStrings[0] = uri.toString();
                            pathURLString = urlStrings[0];
                            Log.d("9AugV1", "urlStrings[0] ==> " + urlStrings[0]);
                            Log.d("9AugV1", "pathURL ==> " + pathURLString);

                            createDatabase();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("9AugV1", "e Error ==> " + e.toString());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    } //File Path

    private void createDatabase() {

        UserModel userModel = new UserModel(uidString, nameString, emailString, pathURLString, myPostString);


        UserModel userModel1 = new UserModel(uidString, nameString, emailString,
                pathURLString, myPostString);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("User");

        Log.d("9AugV1", "createDatabase: pathURL ==> " + pathURLString);

        databaseReference.child(uidString).setValue(userModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Register Success", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contentMainFragment, new ServiceFragment())
                                .commit();

                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("9AugV1", "e CreateDatabase ==> " + e.toString());
            }
        });


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create toolbar
        createToolbar();

        //Photo Contrtoller
        photoContrtoller();
    }    //Main Class


    @Override//after select Image
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {

            uri = data.getData();
            aBoolean = false;

            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 700, 600, true);
                imageView.setImageBitmap(bitmap1);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(getActivity(), "Please Choose Photo", Toast.LENGTH_SHORT).show();

        }

    }

    private void photoContrtoller() {
        imageView = getView().findViewById(R.id.imvPhoto);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please Choose App"), 1);

            }
        });
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("ສະໝັກບັນຊີຜູ່້ໃຊ້ໃໝ່");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("ກະລຸນາເລືອກຮູບພາບ ແລະ " +
                "ໃສ່ຂໍ້ຄວາມໃຫ້ຄົບກ່ອນ!");
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }
}
