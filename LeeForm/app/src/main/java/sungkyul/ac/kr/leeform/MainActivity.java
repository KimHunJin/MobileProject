package sungkyul.ac.kr.leeform;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.navdrawer.SimpleSideDrawer;

import sungkyul.ac.kr.leeform.adapter.MainFragmentAdapter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private SimpleSideDrawer mSlidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabInitialization();

        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mSlidingMenu = new SimpleSideDrawer(MainActivity.this);
        mSlidingMenu.setLeftBehindContentView( R.layout.nav_view );

        ImageView imgNav = (ImageView)findViewById(R.id.imgNav);
        imgNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingMenu.toggleLeftDrawer();
            }
        });


//        mDrawer.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mSlidingMenu.toggleLeftDrawer();
//
//                return true;
//            }
//        });
//        mNavigationView = (NavigationView) findViewById(R.id.nvView);
//        initDrawerContent(mNavigationView);


    }


//    private void SelectDrawerItem(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_home_fragment: {
//                startActivity(new Intent(getApplication(), LoginActivity.class));
//                break;
//            }
//            case R.id.menu_list_fargment: {
//                startActivity(new Intent(getApplication(), RegisterActivity.class));
//                break;
//            }
//        }
//
//        item.setChecked(true);
//        setTitle(item.getTitle());
//        mDrawer.closeDrawers();
//    }

//    private void initDrawerContent(NavigationView nView) {
//        nView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                SelectDrawerItem(menuItem);
//                return true;
//            }
//        });
//    }

    private void tabInitialization() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(mainFragmentAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.mainTab);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mainFragmentAdapter.getTabView(i));
        }
    }
}
