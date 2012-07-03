package com.jack.test.medialibraryproject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class ShowActivity extends Activity implements OnTabChangeListener  {
	
		private static final String TAG = "FragmentTabs";
	    public static final String TAB_EPISODES = "Episodes";
	    public static final String TAB_DETAILS = "Information";
	 
	    private TabHost mTabHost;
	    private int mCurrentTab;
	    private int imageId;

	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	        Bundle extras = getIntent().getExtras(); 
	        
	        if (extras != null) {
	            imageId = extras.getInt("imageId");
	        }

	        ActionBar actionBar = getActionBar();
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	        actionBar.setDisplayShowTitleEnabled(false);

	        ImageView imgArtwork = (ImageView) findViewById(R.id.artwork);
	        
	        imgArtwork.setImageResource(imageId);
	        
	        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
	        setupTabs();
		        mTabHost.setOnTabChangedListener(this);
		        mTabHost.setCurrentTab(mCurrentTab);
		        // manually start loading stuff in the first tab
		        updateTab(TAB_EPISODES, R.id.tab1);
		        
		   
		    ListView seasonList = (ListView) findViewById(R.id.season_list);	    
		    seasonList.setAdapter(new SeasonListAdapter(this));
		    
	    }
	 
	    private void setupTabs() {
	        mTabHost.setup(); // you must call this before adding your tabs!
	        mTabHost.addTab(newTab(TAB_EPISODES, 0, R.id.tab1));
	        mTabHost.addTab(newTab(TAB_DETAILS, 1, R.id.tab3));
	    }
	    
	    private TabSpec newTab(String tag, int labelId, int tabContentId) {
	        Log.d(TAG, "buildTab(): tag=" + tag);
	 	 
	        TabSpec tabSpec = mTabHost.newTabSpec(tag);
	        tabSpec.setIndicator(tag);
	        tabSpec.setContent(tabContentId);
	        return tabSpec;
	    }
	 
	    @Override
	    public void onTabChanged(String tabId) {
	        Log.d(TAG, "onTabChanged(): tabId=" + tabId);
	        if (TAB_EPISODES.equals(tabId)) {
	            updateTab(tabId, R.id.tab1);
	            mCurrentTab = 0;
	            return;
	        }
	        if (TAB_DETAILS.equals(tabId)) {
	            updateTab(tabId, R.id.tab3);
	            mCurrentTab = 1;
	            return;
	        }
	    }
	 
	    private void updateTab(String tabId, int placeholder) {
	        FragmentManager fm = getFragmentManager();
	        if (fm.findFragmentByTag(tabId) == null) {
	            fm.beginTransaction()
	                    .replace(placeholder, new EmptyFragment(), tabId)
	                    .commit();
	        }
	    }
	    
	    private static class SeasonListAdapter extends BaseAdapter {
	        private LayoutInflater mInflater;
	        
	        Context localContext;

	        public SeasonListAdapter(Context context) {
	            // Cache the LayoutInflate to avoid asking for a new one each time.
	            mInflater = LayoutInflater.from(context);
	            localContext = context;
	        }

	        /**
	         * The number of items in the list is determined by the number of speeches
	         * in our array.
	         *
	         * @see android.widget.ListAdapter#getCount()
	         */
	        public int getCount() {
	            return seasons.length;
	        }

	        /**
	         * Since the data comes from an array, just returning the index is
	         * sufficent to get at the data. If we were using a more complex data
	         * structure, we would return whatever object represents one row in the
	         * list.
	         *
	         * @see android.widget.ListAdapter#getItem(int)
	         */
	        public Object getItem(int position) {
	            return position;
	        }

	        /**
	         * Use the array index as a unique id.
	         *
	         * @see android.widget.ListAdapter#getItemId(int)
	         */
	        public long getItemId(int position) {
	            return position;
	        }

	        /**
	         * Make a view to hold each row.
	         *
	         * @see android.widget.ListAdapter#getView(int, android.view.View,
	         *      android.view.ViewGroup)
	         */
	        public View getView(int position, View convertView, ViewGroup parent) {
	            // A ViewHolder keeps references to children views to avoid unneccessary calls
	            // to findViewById() on each row.
	            ViewHolder holder;
	            
	            // When convertView is not null, we can reuse it directly, there is no need
	            // to reinflate it. We only inflate a new View when the convertView supplied
	            // by ListView is null.
	            if (convertView == null) {
	                convertView = mInflater.inflate(R.layout.season_list_item, null);

	                // Creates a ViewHolder and store references to the two children views
	                // we want to bind data to.
	                holder = new ViewHolder();
	                
	                holder.title = (TextView) convertView.findViewById(R.id.title);
	                
	                convertView.setTag(holder);
	            } else {
	                // Get the ViewHolder back to get fast access to the TextView
	                // and the ImageView.
	                holder = (ViewHolder) convertView.getTag();
	            }

                if(position == 0) {
                	DisplayMetrics metrics = localContext.getResources().getDisplayMetrics();
                	float dp15 = 15f;
                	float dp5 = 5f;

                	float fpixels15 = metrics.density * dp15;
                	int pixels15 = (int) (metrics.density * dp15 + 0.5f);
                	
                	float fpixels5 = metrics.density * dp5;
                	int pixels5 = (int) (metrics.density * dp5 + 0.5f);
                	
                	convertView.setPadding(pixels15, pixels5, pixels15, pixels15);
                }
                
	            holder.title.setText(seasons[position]);

	            return convertView;
	        }

	        static class ViewHolder {
	        	TextView title;
	        }
	    }
	    
	    
	    private static final String[] seasons = { "Season 1", "Season 2", "Season 3", "Season 4", "Season 5", "Season 6", "Season 7", "Season 8" };
    
}