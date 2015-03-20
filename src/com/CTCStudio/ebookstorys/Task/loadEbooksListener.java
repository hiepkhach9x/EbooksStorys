package com.CTCStudio.ebookstorys.Task;

import java.util.ArrayList;

import com.CTCStudio.ebookstorys.Util.Ebook;


public interface loadEbooksListener {
	public abstract void onLoadEbookDone(ArrayList<Ebook> result);
	public abstract void onLoadEbookError(ArrayList<Ebook> result);
}
