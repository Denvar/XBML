package com.jack.test.medialibraryproject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Context mContext;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mContext = this;
        
        // Notice that setContentView() is not used, because we use the root
        // android.R.id.content as the container for each fragment

        // setup action bar for tabs
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
        
      actionBar.addTab(actionBar.newTab()
        //.setText("Movies")
		.setTag("movies")
        .setIcon(R.drawable.noun_project_313)
        .setTabListener(new TabListener(new TabGridFragment("movies"))));

      actionBar.addTab(actionBar.newTab()
        //.setText("TV Shows")
		.setTag("tv")
        .setIcon(R.drawable.noun_project_416)
        .setTabListener(new TabListener(new TabPosterFragment("tv"))));
        
      actionBar.addTab(actionBar.newTab()
        //.setText("Pictures")
		.setTag("pictures")
        .setIcon(R.drawable.noun_project_541)
        .setTabListener(new TabListener(new TabThinGridFragment("pictures"))));
        
        
        //actionBar.addTab(actionBar.newTab()
                //.setText("Movies")
        //		.setTag("movies")
        //        .setIcon(R.drawable.noun_project_313)
        //        .setTabListener(new TabListener(new TabGridFragment("movies"))));
        
        //actionBar.addTab(actionBar.newTab()
                //.setText("TV Shows")
        //		.setTag("tv")
        //        .setIcon(R.drawable.noun_project_416)
        //        .setTabListener(new TabListener(new TabContentFragment("tv"))));
                
        //actionBar.addTab(actionBar.newTab()
                //.setText("Pictures")
        //		.setTag("pictures")
        //        .setIcon(R.drawable.noun_project_541)
        //        .setTabListener(new TabListener(new TabThinGridFragment("pictures"))));
        
        //actionBar.addTab(actionBar.newTab()
                //.setText("Pictures")
        //		.setTag("music")
        //        .setIcon(R.drawable.noun_project_220)
        //        .setTabListener(new TabListener(new TabPosterFragment("music")))); 
        
        //actionBar.addTab(actionBar.newTab()
                //.setText("Pictures")
        //		.setTag("musicl")
        //        .setIcon(R.drawable.noun_project_928)
        //        .setTabListener(new TabListener(new TabPosterLargeFragment("musicl"))));

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        
        return true;
    }

    /**
     * A TabListener receives event callbacks from the action bar as tabs
     * are deselected, selected, and reselected. A FragmentTransaction
     * is provided to each of these callbacks; if any operations are added
     * to it, it will be committed at the end of the full tab switch operation.
     * This lets tab switches be atomic without the app needing to track
     * the interactions between different tabs.
     */
    private class TabListener implements ActionBar.TabListener {
        private TabContentFragment mFragment;
        private TabGridFragment mGridFragment;
        private TabThinGridFragment mThinGridFragment;
        private TabPosterFragment mPosterFragment;
        private TabPosterLargeFragment mPosterLargeFragment;

        public TabListener(TabContentFragment fragment) {
            mFragment = fragment;
        }
        
        public TabListener(TabGridFragment fragment) {
        	mGridFragment = fragment;
        }
        
        public TabListener(TabThinGridFragment fragment) {
        	mThinGridFragment = fragment;
        }
        
        public TabListener(TabPosterFragment fragment) {
        	mPosterFragment = fragment;
        }
        
        public TabListener(TabPosterLargeFragment fragment) {
        	mPosterLargeFragment = fragment;
        }


        public void onTabSelected(Tab tab, FragmentTransaction ft) {
        	if(tab.getTag().equals("movies")) {
                ft.add(android.R.id.content, mGridFragment, mGridFragment.getText());
        	} else if (tab.getTag().equals("tv")) {
                ft.add(android.R.id.content, mPosterFragment, mPosterFragment.getText());
        	} else if (tab.getTag().equals("music")) {
                ft.add(android.R.id.content, mPosterFragment, mPosterFragment.getText());
        	} else if (tab.getTag().equals("musicl")) {
                ft.add(android.R.id.content, mPosterLargeFragment, mPosterLargeFragment.getText());
        	} else {
                ft.add(android.R.id.content, mThinGridFragment, mThinGridFragment.getText());
        	}
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        	if(tab.getTag().equals("movies")) {
                ft.remove(mGridFragment);
        	} else if(tab.getTag().equals("tv")) {
                ft.remove(mPosterFragment);
        	} else if(tab.getTag().equals("music")) {
                ft.remove(mPosterFragment);
        	} else if(tab.getTag().equals("musicl")) {
                ft.remove(mPosterLargeFragment);
        	} else {
                ft.remove(mThinGridFragment);
        	}
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(MainActivity.this, "Reselected!", Toast.LENGTH_SHORT).show();
        }

    }

    private class TabPosterLargeFragment extends Fragment {
        private String mText;

        public TabPosterLargeFragment(String text) {
            mText = text;
        }

        public String getText() {
            return mText;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View fragView = inflater.inflate(R.layout.action_bar_tab_poster_large, container, false);
            
            GridView grid = (GridView) fragView.findViewById(R.id.gridview);
            grid.setAdapter(new EfficientPosterAdapterLarge(mContext));
            
            grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		            Toast.makeText(MainActivity.this, "W: " + v.getWidth() + " H: " + v.getHeight(), Toast.LENGTH_SHORT).show();
				}
            	
			});
            
            return fragView;
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);       
        }
       
      
    } 

    private class TabPosterFragment extends Fragment {
        private String mText;

        public TabPosterFragment(String text) {
            mText = text;
        }

        public String getText() {
            return mText;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View fragView = inflater.inflate(R.layout.action_bar_tab_poster, container, false);
            
            GridView grid = (GridView) fragView.findViewById(R.id.gridview);
            grid.setAdapter(new EfficientPosterAdapter(mContext));
            
            grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					Intent intent = new Intent(mContext, ShowActivity.class);
					intent.putExtra("imageId", gridThinItems[position]);
					
					startActivity(intent);
				}
            	
			});
            
            return fragView;
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);       
        }
       
      
    } 
    
    private class TabContentFragment extends ListFragment {
        private String mText;

        public TabContentFragment(String text) {
            mText = text;
        }

        public String getText() {
            return mText;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View fragView = inflater.inflate(R.layout.action_bar_tab_content, container, false);
            
            return fragView;
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            
            this.setListAdapter(new EfficientListAdapter(mContext));         
        }
       
        @Override
		public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);

            Toast.makeText(MainActivity.this, "W: " + v.getWidth() + " H: " + v.getHeight(), Toast.LENGTH_SHORT).show();
        }
      
    } 

    private class TabGridFragment extends Fragment {
        private String mText;

        public TabGridFragment(String text) {
            mText = text;
        }

        public String getText() {
            return mText;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View fragView = inflater.inflate(R.layout.action_bar_tab_grid, container, false);
            
            GridView grid = (GridView) fragView.findViewById(R.id.gridview);
            grid.setAdapter(new EfficientGridAdapter(mContext));
            
            grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		            Toast.makeText(MainActivity.this, "W: " + v.getWidth() + " H: " + v.getHeight(), Toast.LENGTH_SHORT).show();
				}
            	
			});
            
            return fragView;
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);       
        }
       
      
    } 

    private class TabThinGridFragment extends Fragment {
        private String mText;

        public TabThinGridFragment(String text) {
            mText = text;
        }

        public String getText() {
            return mText;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View fragView = inflater.inflate(R.layout.action_bar_tab_thin_grid, container, false);
            
            GridView grid = (GridView) fragView.findViewById(R.id.gridview);
            grid.setAdapter(new EfficientThinGridAdapter(mContext));
            
            grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		            Toast.makeText(MainActivity.this, "W: " + v.getWidth() + " H: " + v.getHeight(), Toast.LENGTH_SHORT).show();
				}
            	
			});
            
            return fragView;
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);       
        }
       
      
    } 
    
    private static class EfficientListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        
        Context localContext;

        public EfficientListAdapter(Context context) {
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
            return images.length;
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
                convertView = mInflater.inflate(R.layout.list_item_icon_text, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            convertView.setBackgroundResource(images[position]);

            return convertView;
        }

        static class ViewHolder {
        	
        }
    }

    private static class EfficientPosterAdapterLarge extends BaseAdapter {
        private LayoutInflater mInflater;
        
        Context localContext;

        public EfficientPosterAdapterLarge(Context context) {
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
            return posterLargeItems.length;
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
                convertView = mInflater.inflate(R.layout.poster_item_large, null);

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

            holder.title.setText(gridTtitles[position]);
            convertView.setBackgroundResource(posterLargeItems[position]);

            return convertView;
        }

        static class ViewHolder {
        	TextView title;
        }
    }

    private static class EfficientPosterAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        
        Context localContext;

        public EfficientPosterAdapter(Context context) {
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
            return posterItems.length;
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
                convertView = mInflater.inflate(R.layout.poster_item, null);

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

            holder.title.setText(gridTtitles[position]);
            convertView.setBackgroundResource(posterItems[position]);

            return convertView;
        }

        static class ViewHolder {
        	TextView title;
        }
    }
    
    private static class EfficientThinGridAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        
        Context localContext;

        public EfficientThinGridAdapter(Context context) {
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
            return imageUrls.length;
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
                convertView = mInflater.inflate(R.layout.grid_thin_item, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                
                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.image.

            return convertView;
        }

        static class ViewHolder {
        	ImageView image;
        }
    }
    
    private static class EfficientGridAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        
        Context localContext;

        public EfficientGridAdapter(Context context) {
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
            return gridItems.length;
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
                convertView = mInflater.inflate(R.layout.grid_item, null);

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

            holder.title.setText(gridTtitles[position]);
            convertView.setBackgroundResource(gridItems[position]);

            return convertView;
        }

        static class ViewHolder {
        	TextView title;
        }
    }

    
    private static final String[] imageUrls = {
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126318-simple-black-square-icon-animals-animal-bird.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126314-simple-black-square-icon-animals-animal-antz.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126315-simple-black-square-icon-animals-animal-bear.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126316-simple-black-square-icon-animals-animal-bear3.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126317-simple-black-square-icon-animals-animal-bear4-sc44.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126328-simple-black-square-icon-animals-animal-bull1-sc44.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126329-simple-black-square-icon-animals-animal-butterfly2.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126330-simple-black-square-icon-animals-animal-butterfly3.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126331-simple-black-square-icon-animals-animal-butterfly5-sc48.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126332-simple-black-square-icon-animals-animal-camel.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126333-simple-black-square-icon-animals-animal-camel2-sc36.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126334-simple-black-square-icon-animals-animal-cat-print.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126335-simple-black-square-icon-animals-animal-cat1.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126336-simple-black-square-icon-animals-animal-cat18.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126337-simple-black-square-icon-animals-animal-cat20.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126338-simple-black-square-icon-animals-animal-cat21.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126339-simple-black-square-icon-animals-animal-cat26.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126340-simple-black-square-icon-animals-animal-cat27.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126341-simple-black-square-icon-animals-animal-cat28.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126342-simple-black-square-icon-animals-animal-cat3.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126343-simple-black-square-icon-animals-animal-cat4.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126344-simple-black-square-icon-animals-animal-cat5-sc22.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126345-simple-black-square-icon-animals-animal-cat6.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126346-simple-black-square-icon-animals-animal-cat7.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126347-simple-black-square-icon-animals-animal-cow1.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126348-simple-black-square-icon-animals-animal-cow2.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126321-simple-black-square-icon-animals-animal-bird4-sc44.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126322-simple-black-square-icon-animals-animal-bird5-sc44.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126323-simple-black-square-icon-animals-animal-bird6-sc44.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126324-simple-black-square-icon-animals-animal-bird7-sc44.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126325-simple-black-square-icon-animals-animal-bird8-sc45.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126326-simple-black-square-icon-animals-animal-birdie1.png",
    	"http://icons.mysitemyway.com/wp-content/gallery/simple-black-square-icons-animals/thumbs/thumbs_126327-simple-black-square-icon-animals-animal-bull-face.png"
    };

    private static final String[] gridTtitles = { "CHUCK", "DEXTER", "FIREFLY", "FRIENDS", "GAME OF THRONES", "GRIMM", "HOUSE", "LIE TO ME", 
    	"SHERLOCK", "THE SOPRANOS", "SPACED", "SPARTACUS", "STARGATE ATLANTIS", "THE WALKING DEAD", "CHUCK", "DEXTER", "FIREFLY", "FRIENDS", 
    	"GAME OF THRONES", "GRIMM", "HOUSE", "LIE TO ME", 
    	"SHERLOCK", "THE SOPRANOS", "SPACED", "SPARTACUS", "STARGATE ATLANTIS", "THE WALKING DEAD", "CHUCK", "DEXTER", "FIREFLY", "FRIENDS", "GAME OF THRONES", "GRIMM", "HOUSE", "LIE TO ME", 
    	"SHERLOCK", "THE SOPRANOS", "SPACED", "SPARTACUS", "STARGATE ATLANTIS", "THE WALKING DEAD", "CHUCK", "DEXTER", "FIREFLY", "FRIENDS", 
    	"GAME OF THRONES", "GRIMM", "HOUSE", "LIE TO ME", 
    	"SHERLOCK", "THE SOPRANOS", "SPACED", "SPARTACUS", "STARGATE ATLANTIS", "THE WALKING DEAD"};

    private static final int[] gridItems = { R.drawable.grid01, R.drawable.grid02, R.drawable.grid03, R.drawable.grid04, 
		R.drawable.grid05, R.drawable.grid06, R.drawable.grid07, R.drawable.grid08 , R.drawable.grid09 , R.drawable.grid10,
		R.drawable.grid11, R.drawable.grid12, R.drawable.grid13, R.drawable.grid14, R.drawable.grid01, R.drawable.grid02, R.drawable.grid03, R.drawable.grid04, 
		R.drawable.grid05, R.drawable.grid06, R.drawable.grid07, R.drawable.grid08 , R.drawable.grid09 , R.drawable.grid10,
		R.drawable.grid11, R.drawable.grid12, R.drawable.grid13, R.drawable.grid14, R.drawable.grid01, R.drawable.grid02, R.drawable.grid03, R.drawable.grid04, 
		R.drawable.grid05, R.drawable.grid06, R.drawable.grid07, R.drawable.grid08 , R.drawable.grid09 , R.drawable.grid10,
		R.drawable.grid11, R.drawable.grid12, R.drawable.grid13, R.drawable.grid14, R.drawable.grid01, R.drawable.grid02, R.drawable.grid03, R.drawable.grid04, 
		R.drawable.grid05, R.drawable.grid06, R.drawable.grid07, R.drawable.grid08 , R.drawable.grid09 , R.drawable.grid10,
		R.drawable.grid11, R.drawable.grid12, R.drawable.grid13, R.drawable.grid14};
    
    private static final int[] gridThinItems = { R.drawable.grid001, R.drawable.grid002, R.drawable.grid003, R.drawable.grid004, 
		R.drawable.grid005, R.drawable.grid006, R.drawable.grid007, R.drawable.grid008 , R.drawable.grid009 , R.drawable.grid010,
		R.drawable.grid011, R.drawable.grid012, R.drawable.grid013, R.drawable.grid014};
    
    private static final int[] posterItems = { R.drawable.poster01, R.drawable.poster02, R.drawable.poster03, R.drawable.poster04, 
		R.drawable.poster05, R.drawable.poster06, R.drawable.poster07, R.drawable.poster08 , R.drawable.poster09 , R.drawable.poster10,
		R.drawable.poster11, R.drawable.poster12, R.drawable.poster13, R.drawable.poster14};
    
    private static final int[] posterLargeItems = { R.drawable.poster001, R.drawable.poster002, R.drawable.poster003, R.drawable.poster004, 
		R.drawable.poster005, R.drawable.poster006, R.drawable.poster007, R.drawable.poster008 , R.drawable.poster009 , R.drawable.poster010,
		R.drawable.poster011, R.drawable.poster012, R.drawable.poster013, R.drawable.poster014};
    
    private static final int[] images = { R.drawable.wide01, R.drawable.wide02, R.drawable.wide03, R.drawable.wide04, 
		R.drawable.wide05, R.drawable.wide06, R.drawable.wide07, R.drawable.wide08 , R.drawable.wide09 , R.drawable.wide10,
		R.drawable.wide11, R.drawable.wide12, R.drawable.wide13, R.drawable.wide14 };
    
}