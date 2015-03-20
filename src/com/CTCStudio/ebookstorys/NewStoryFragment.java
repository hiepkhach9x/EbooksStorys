package com.CTCStudio.ebookstorys;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.CTCStudio.ebookstorys.Adapter.NewBooksAdapter;
import com.CTCStudio.ebookstorys.Task.LoadXMLebooksTask;
import com.CTCStudio.ebookstorys.Task.loadEbooksListener;
import com.CTCStudio.ebookstorys.Util.Ebook;
import com.CTCStudio.ebookstorys.Util.NetworkReceiver;
import com.CTCStudio.ebookstorys.Util.Variable;

public class NewStoryFragment extends Fragment implements loadEbooksListener {
	ListView lv_NewEbooks;
	ArrayList<Ebook> lsEbooks;
	ProgressDialog dialog;
	private NetworkReceiver receiver = new NetworkReceiver();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dialog = new ProgressDialog(getActivity());
		dialog.setTitle(getResources()
				.getString(R.string.title_dialog_thongbao));
		dialog.setMessage(getResources().getString(
				R.string.title_dialog_noidung_tai));

		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		receiver = new NetworkReceiver();
		getActivity().registerReceiver(receiver, filter);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Variable.updateConnectedFlags(getActivity());
		if (NetworkReceiver.refreshDisplay) {
			dialog.show();
			new LoadXMLebooksTask(this)
					.execute("http://noiloiyeu.com/truyen/ebooks.xml");
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
			getActivity().unregisterReceiver(receiver);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_story_new,
				container, false);
		lv_NewEbooks = (ListView) rootView.findViewById(R.id.lvStory);

		return rootView;
	}

	@Override
	public void onLoadEbookDone(ArrayList<Ebook> result) {
		// TODO Auto-generated method stub
		NewBooksAdapter adapter = new NewBooksAdapter(getActivity(), result);
		lv_NewEbooks.setAdapter(adapter);
		dialog.dismiss();
	}

	@Override
	public void onLoadEbookError(ArrayList<Ebook> result) {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}

}
