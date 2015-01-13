package com.example.slidemenu;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ninh.kenhvlog.R;
import com.ninh.kenhvlog.MainActivity;
import com.ninh.kenhvlog.VideoActivity_Lamvietanh;
import com.ninh.kenhvlog.VideoActivity_ToanShinoda;
import com.ninh.kenhvlog.VideoActivity_annguy;
import com.ninh.kenhvlog.VideoActivity_duhocsinh;
import com.ninh.kenhvlog.VideoActivity_huyme;
import com.ninh.kenhvlog.VideoActivity_jv;

public class MenuOptionFragment extends Fragment {

	private Activity _activity;
	private ListView _listMenu;
	
	String[] names = {"Jvevermind", "HuyMeProductions's", "Toàn shinoda","Du Học Sinh","Lâm Việt Anh","An Nguy","RAY - CK","Ryan Higa","D-CROWN NGUYEN","David So Comedy"};
	Integer[] icons = {R.drawable.jvm, R.drawable.huyme, R.drawable.toanshin, R.drawable.duhocsinh, R.drawable.lamvietanh, R.drawable.annguy,R.drawable.ray,R.drawable.nigahiga,R.drawable.dcrown,R.drawable.david};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.slideout_menu_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			return;
		}
		_activity = (MenuActivity) getActivity();
		_listMenu = (ListView) _activity.findViewById(R.id.lvMenu);
		
		MenuAdapter menuAdapter = new MenuAdapter(getActivity());
		
		for(int i=0; i<names.length; i++) {
			menuAdapter.add(new MenuItem(names[i], icons[i]));
		}
		
		_listMenu.setAdapter(menuAdapter);
		//_listMenu.setAdapter(new MenuAdapter(_activity, android.R.layout.simple_list_item_1, _activity.getResources() .getStringArray(R.array.menu_slide_item)));
		_listMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(final AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				AnimationListener listener = new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						Intent intent = null;
						//switch (arg2) {
						//case 0:
							MainActivity.pos = arg2;
							MainActivity.onChange = true;
							//break;

						//default:
						//	break;
						//}
					}
				};
				((MenuActivity) getActivity()).getSlideoutHelper().close(listener);
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		getActivity();
		super.onAttach(activity);
	}
	
	class MenuItem {
		private String name;
		private int icon;
		public MenuItem() { }
		public MenuItem(String name, int icon) {
			this.name = name;
			this.icon = icon;
		}
		
		public String getName() {
			return name;
		}
		
		public int getIcon() {
			return icon;
		}
	}
	
	class MenuAdapter extends ArrayAdapter<MenuItem> {

		public MenuAdapter(Context context) {
			super(context, 0);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item, null);
			((ImageView)(convertView.findViewById(R.id.menu_icon))).setImageResource(getItem(position).getIcon());
			((TextView)(convertView.findViewById(R.id.menu_name))).setText(getItem(position).getName());
			return convertView;
		}
	}
}
