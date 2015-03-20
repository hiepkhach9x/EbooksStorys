package com.CTCStudio.ebookstorys.Task;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.AsyncTask;

import com.CTCStudio.ebookstorys.Util.Ebook;
import com.CTCStudio.ebookstorys.Util.XMLParser;

public class LoadEbooksTask extends AsyncTask<String, Void, ArrayList<Ebook>> {
	Context ctx;
	loadEbooksListener ls;
	// XML node keys
	static final String KEY_ITEM = "book"; // parent node
	static final String KEY_TITLE = "title";
	static final String KEY_AUTHOR = "author";
	static final String KEY_LINK = "link";
	static final String KEY_PUBLISHED = "published";
	static final String KEY_DESC = "description";
	private Ebook ebook;
	private boolean Error;

	public LoadEbooksTask(Context ctx, loadEbooksListener ls) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		this.ls = ls;
		Error = false;
	}

	@Override
	protected ArrayList<Ebook> doInBackground(String... params) {
		// TODO Auto-generated method stub
		String strURL = params[0];
		ArrayList<Ebook> ebooks = new ArrayList<Ebook>();
		try {
			XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(strURL); // getting XML
			Document doc = parser.getDomElement(xml); // getting DOM element

			NodeList nl = doc.getElementsByTagName(KEY_ITEM);
			// looping through all item nodes <item>
			for (int i = 0; i < nl.getLength(); i++) {
				ebook = null;
				Element e = (Element) nl.item(i);
				// adding each child node to HashMap key => value
				ebook.setTitle(parser.getValue(e, KEY_TITLE));
				ebook.setAuthor(parser.getValue(e, KEY_AUTHOR));
				ebook.setLink(parser.getValue(e, KEY_LINK));
				ebook.setPublished(parser.getValue(e, KEY_PUBLISHED));
				ebook.setDescription(parser.getValue(e, KEY_DESC));

				// adding HashList to ArrayList
				ebooks.add(ebook);
			}
		} catch (Exception ex) {
			Error = true;
		}
		return ebooks;
	}

	@Override
	protected void onPostExecute(ArrayList<Ebook> result) {
		// TODO Auto-generated method stub
		if (!Error)
			ls.onLoadEbookError(result);
		else
			ls.onLoadEbookError(result);
		super.onPostExecute(result);
	}
}
