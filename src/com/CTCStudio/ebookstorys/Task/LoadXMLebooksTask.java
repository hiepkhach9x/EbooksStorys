package com.CTCStudio.ebookstorys.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Xml;

import com.CTCStudio.ebookstorys.Util.Ebook;

public class LoadXMLebooksTask extends
		AsyncTask<String, Void, ArrayList<Ebook>> {
	private final String EBOOK = "bookstore";
	private final String BOOK = "book";
	private final String TITLE = "title";
	private final String AUTHOR = "author";
	private final String LINK = "link";
	private final String PUBLISHED = "published";
	private final String DESCRIPTION = "description";
	loadEbooksListener rs;
	boolean Error = false;

	public LoadXMLebooksTask(loadEbooksListener rs) {
		// TODO Auto-generated constructor stub
		this.rs = rs;
		Error = false;
	}

	@Override
	protected ArrayList<Ebook> doInBackground(String... params) {
		ArrayList<Ebook> result = null;
		try {
			result = loadXmlFromNetwork(params[0]);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Error = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Error = true;
		}
		return result;
	}

	@Override
	protected void onPostExecute(ArrayList<Ebook> result) {
		if (!Error)
			rs.onLoadEbookDone(result);
		else
			rs.onLoadEbookError(result);
		super.onPostExecute(result);
	}

	private ArrayList<Ebook> loadXmlFromNetwork(String urlString)
			throws XmlPullParserException, IOException {
		InputStream stream = null;
		ArrayList<Ebook> entries = null;
		try {
			stream = downloadUrl(urlString);
			entries = parse(stream);
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
		return entries;
	}

	//
	private static final String ns = null;

	public ArrayList<Ebook> parse(InputStream in)
			throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			in.close();
		}
	}

	private InputStream downloadUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(1000 /* milliseconds */);
		conn.setConnectTimeout(1500 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		// Starts the query
		conn.connect();
		InputStream stream = conn.getInputStream();
		return stream;
	}

	private ArrayList<Ebook> readFeed(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		ArrayList<Ebook> entries = new ArrayList<Ebook>();

		parser.require(XmlPullParser.START_TAG, ns, EBOOK);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the Ebook tag
			if (name.equals(BOOK)) {
				entries.add(readEbook(parser));
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	private Ebook readEbook(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, BOOK);
		String title = null;
		String author = null;
		String link = null;
		String published = null;
		String description = null;
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals(TITLE)) {
				title = readTitle(parser);
			} else if (name.equals(AUTHOR)) {
				author = readAuthor(parser);
			} else if (name.equals(LINK)) {
				link = readLink(parser);
			} else if (name.equals(PUBLISHED)) {
				published = readPublished(parser);
			} else if (name.equals(DESCRIPTION)) {
				description = readDescription(parser);
			} else {
				skip(parser);
			}
		}
		return new Ebook(title, author, link, published, description);
	}

	private String readPublished(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, PUBLISHED);
		String published = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, PUBLISHED);
		return published;
	}

	private String readTitle(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, TITLE);
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, TITLE);
		return title;
	}

	private String readLink(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, LINK);
		String link = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, LINK);
		return link;
	}

	private String readAuthor(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, AUTHOR);
		String author = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, AUTHOR);
		return author;
	}

	private String readDescription(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, DESCRIPTION);
		String description = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, DESCRIPTION);
		return description;
	}

	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

}
