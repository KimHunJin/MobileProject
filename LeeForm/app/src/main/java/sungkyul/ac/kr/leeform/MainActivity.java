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
import android.widget.ArrayAdapter;
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
import sungkyul.ac.kr.leeform.utils.BackPressCloseHandler;
import sungkyul.ac.kr.leeform.utils.LoadActivityList;

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;
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

        // 로그아웃 할 때 열려있는 액티비티 모두 닫기 위해 리스트에 저장
        new LoadActivityList().actList.add(MainActivity.this);

        tabInitialization();
        initializeLayout();
        setListener();

    }

    /**
     * 화면에 보여줄 정보 초기화
     */
    private void initializeLayout(){
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mSlidingMenu = new SimpleSideDrawer(MainActivity.this);
        mSlidingMenu.setLeftBehindContentView( R.layout.nav_view );
        lstNavItem = (ListView) mSlidingMenu.findViewById(R.id.lstNavItem);

        // 취소버튼 눌렀을 때 핸들러
        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    /**
     * 리스너 설정
     */
    private void setListener(){
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
                    overridePendingTransition(R.anim.commons_slide_from_right,R.anim.commons_slide_to_left);
                } else if(tabLayout.getSelectedTabPosition()==1) {
                    startActivity(new Intent(getApplicationContext(), MaterialSearchActivity.class));
                    overridePendingTransition(R.anim.commons_slide_from_right,R.anim.commons_slide_to_left);
                } else {
                    Toast.makeText(getApplicationContext(),"커뮤니티에선 지원하지 않는 기능입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        String[] item=getResources().getStringArray(R.array.nav);

        lstNavItem = (ListView) mSlidingMenu.findViewById(R.id.lstNavItem);
        ArrayAdapter<String> yada= new ArrayAdapter<String>(this,R.layout.nav_item,item);
        lstNavItem.setAdapter(yada);

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
    }

    //취소버튼 눌렀을 때
    @Override
    public void onBackPressed() {
        // 네비게이션이 열려있으면
        if(!mSlidingMenu.isClosed()){
            mSlidingMenu.closeLeftSide();
            return;
        }
        //핸들러 작동
        backPressCloseHandler.onBackPressed();
        Toast.makeText(getApplicationContext(), "한 번 더 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
    }

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
