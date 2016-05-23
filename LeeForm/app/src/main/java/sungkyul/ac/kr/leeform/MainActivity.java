package sungkyul.ac.kr.leeform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.navdrawer.SimpleSideDrawer;

import sungkyul.ac.kr.leeform.activity.SettingActivity;
import sungkyul.ac.kr.leeform.activity.member.PurchaseListActivity;
import sungkyul.ac.kr.leeform.activity.navigation.MyPageActivity;
import sungkyul.ac.kr.leeform.activity.search.KnowHowSearchActivity;
import sungkyul.ac.kr.leeform.activity.search.MaterialSearchActivity;
import sungkyul.ac.kr.leeform.adapter.MainFragmentAdapter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private SimpleSideDrawer mSlidingMenu;
    private TabLayout tabLayout;
    private TabLayout.Tab tab;
    private ListView lstNavItem;

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

        ImageView imgSearch = (ImageView)findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabLayout.getSelectedTabPosition()==0) {
                    Intent it = new Intent(getApplicationContext(), KnowHowSearchActivity.class);
                    startActivity(it);
                } else if(tabLayout.getSelectedTabPosition()==1) {
                    startActivity(new Intent(getApplicationContext(), MaterialSearchActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(),"커뮤니티에선 지원하지 않는 기능입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        lstNavItem = (ListView) mSlidingMenu.findViewById(R.id.lstNavItem);
        lstNavItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    // 내 정보
                    case 0:
                        Intent intent=new Intent(getApplicationContext(), MyPageActivity.class);
                        startActivity(intent);
                        break;

                    // 구매내역
                    case 1:
                        Intent itPurchaseList = new Intent(getApplicationContext(), PurchaseListActivity.class);
                        startActivity(itPurchaseList);
                        break;

                    // 판매자 등록
                    case 2:
                        break;

                    // 설정
                    case 3:

                        Intent itSetting = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(itSetting);
                        break;
                }
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


        tabLayout = (TabLayout) findViewById(R.id.mainTab);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tab = tabLayout.getTabAt(i);
            tab.setCustomView(mainFragmentAdapter.getTabView(i));
        }
    }
}
