package chill_order.com.laosunseen.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import chill_order.com.laosunseen.R;

public class MyAlert {

	private Context context;

	public MyAlert(Context context) {
		this.context = context;
	}

	public void normalDialog(String titleString, String messageString) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		builder.setIcon(R.drawable.ic_action_alert_bad);
		builder.setTitle(titleString);
		builder.setMessage(messageString);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});
		builder.show();

	}


}
