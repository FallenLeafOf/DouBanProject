package com.guifa.doubanproject.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.guifa.doubanproject.R;
import com.guifa.doubanproject.beans.HomeBottomBean;
import com.guifa.doubanproject.fragment.BroadcastFragment;
import com.guifa.doubanproject.fragment.GroupFragment;
import com.guifa.doubanproject.fragment.HomeFragment;
import com.guifa.doubanproject.fragment.MediaFragment;
import com.guifa.doubanproject.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页，放置底部5个导航按钮
 */
public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.bottomTab)
    CommonTabLayout bottomTab;

    private List<Fragment> listFragment;
    private Fragment mFragment;

    private String[] mTitles = {"首页", "书影音", "广播", "小组", "我的"}; // 标题
    private int[] mIconSelectIds = {R.drawable.ic_tab_home_active, R.drawable.ic_tab_subject_active,
            R.drawable.ic_tab_status_active, R.drawable.ic_tab_group_active, R.drawable.ic_tab_profile_active}; // 选中时图标
    private int[] mIconUnSelectIds = {R.drawable.ic_tab_home_normal, R.drawable.ic_tab_subject_normal,
            R.drawable.ic_tab_status_normal, R.drawable.ic_tab_group_normal, R.drawable.ic_tab_profile_normal}; // 未选中时图标
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>(); // bottomTab的数据

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initBottomTab();
    }

    /**
     * 初始化底部tab
     */
    private void initBottomTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new HomeBottomBean(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        bottomTab.setTabData(mTabEntities);
        listFragment = new ArrayList<>();
        // 传参数可换成对应Fragment中的newInstance
        listFragment.add(new HomeFragment());
        listFragment.add(new MediaFragment());
        listFragment.add(new BroadcastFragment());
        listFragment.add(new GroupFragment());
        listFragment.add(new MineFragment());
        switchFragment(listFragment.get(0));
        //点击监听
        bottomTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(listFragment.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 切换Fragment
     */
    private void switchFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mFragment == null) {
            mFragment = fragment;
            ft.add(R.id.layFrame, fragment).show(fragment).commitAllowingStateLoss();
            return;
        }
        if (fragment != mFragment) {
            if (fragment.isAdded()) {
                ft.hide(mFragment).show(fragment).commitAllowingStateLoss();
            } else {
                ft.hide(mFragment).add(R.id.layFrame, fragment).commitAllowingStateLoss();
            }
            mFragment = fragment;
        }
    }


    /**
     * 物理键点击事件
     *
     * @param keyCode keyCode
     * @param event   event
     * @return super.onKeyDown(keyCode, event);
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            // 连续单击返回键间隔大于2秒，则提示用户
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(HomeActivity.this, "再按一次退出豆瓣App", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                // 退出app
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
