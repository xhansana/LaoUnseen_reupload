package chill_order.com.laosunseen.fragment;

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


import java.util.zip.Inflater;

import chill_order.com.laosunseen.MainActivity;
import chill_order.com.laosunseen.R;

public class ServiceFragment extends Fragment {

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//  Create Toolbar
		createToolbar();

	}    // Main Method

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_service, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.itemSignOut) {

			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	private void createToolbar() {
		Toolbar toolbar = getView().findViewById(R.id.toolbarService);
		((MainActivity)getActivity()).setSupportActionBar(toolbar);
		setHasOptionsMenu(true);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_service, container, false);
		return view;
	}
}
