package com.CTCStudio.ebookstorys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.CTCStudio.ebookstorys.Util.Variable;

public class StoryDownloadFragment extends Fragment {
	ListView lv_ebooks;
	static List<File> epubs;
	static List<String> names;
	ArrayAdapter<String> adapter;
	static File selected;
	boolean firstTime;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_story_download,
				container, false);

		if ((epubs == null) || (epubs.size() == 0)) {
			epubs = epubList(Environment.getExternalStorageDirectory());
		}

		ListView list = (ListView) rootView.findViewById(R.id.lvStory);
		names = fileNames(epubs);
		adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_ebooks,
				R.id.tvNameEbooks, names);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View itemView,
					int position, long itemId) {
				selected = epubs.get(position);
				Intent storyIntent = new Intent(getActivity(),
						StoryActivity.class);
				storyIntent.putExtra(Variable.EBOOKPATH, selected.getAbsolutePath());
				startActivity(storyIntent);
			}
		});

		list.setAdapter(adapter);
		return rootView;
	}

	// TODO: hardcoded string
	private List<String> fileNames(List<File> files) {
		List<String> res = new ArrayList<String>();
		for (int i = 0; i < files.size(); i++) {
			res.add(files.get(i).getName().replace(".epub", ""));
			/*
			 * NOTE: future res.add(files.get(i).getName().replace(".epub",
			 * "").replace(".e0", ""));
			 */
		}
		return res;
	}

	// TODO: hardcoded string
	// TODO: check with mimetype, not with filename extension
	private List<File> epubList(File dir) {
		List<File> res = new ArrayList<File>();
		if (dir.isDirectory()) {
			File[] f = dir.listFiles();
			if (f != null) {
				for (int i = 0; i < f.length; i++) {
					if (f[i].isDirectory()) {
						res.addAll(epubList(f[i]));
					} else {
						String lowerCasedName = f[i].getName().toLowerCase();
						if (lowerCasedName.endsWith(".epub")) {
							res.add(f[i]);
						}

						/*
						 * NOTE: future if ((lowerCasedName.endsWith(".epub"))
						 * || (lowerCasedName.endsWith(".e0"))) { res.add(f[i]);
						 * }
						 */
					}
				}
			}
		}
		return res;
	}

	@SuppressWarnings("unused")
	private void refreshList() {
		epubs = epubList(Environment.getExternalStorageDirectory());
		names.clear();
		names.addAll(fileNames(epubs));
		this.adapter.notifyDataSetChanged();
	}
}
