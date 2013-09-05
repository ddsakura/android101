package com.example.androidpractice1;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewFrag extends ListFragment {
	String[] presidents = { "1", "2", "3", "4", "5", "6", "7"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, presidents));
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
	    Toast.makeText(getActivity(), "The choice" + presidents[position], Toast.LENGTH_SHORT).show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    return inflater.inflate(R.layout.listfragment, container, false);
	}
}
