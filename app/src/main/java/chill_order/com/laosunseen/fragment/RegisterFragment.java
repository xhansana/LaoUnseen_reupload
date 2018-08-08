package chill_order.com.laosunseen.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import chill_order.com.laosunseen.MainActivity;
import chill_order.com.laosunseen.R;
import chill_order.com.laosunseen.utility.MyAlert;

public class RegisterFragment extends Fragment {

	//            Explicit
	private Uri uri;
	private ImageView imageView;
	private boolean aBoolean = true;

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

//		Check Choose Photo
		if (aBoolean) {
//			None Choose Photo
			MyAlert myAlert = new MyAlert(getActivity());
			myAlert.normalDialog("ຍັງບໍ່ມີຮູບພາບ",
					"ກະລຸນາເລືອກຮູບກ່ອນ!");

		} else {

		}

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
				startActivityForResult(Intent.createChooser(intent, "Plese Choose App"), 1);

			}
		});

	}

	private void createToolbar() {
		Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
		((MainActivity) getActivity()).setSupportActionBar(toolbar);
		((MainActivity) getActivity()).getSupportActionBar().setTitle("Register New User");
		((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please Choose Photo And Fill All Blank!");
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
