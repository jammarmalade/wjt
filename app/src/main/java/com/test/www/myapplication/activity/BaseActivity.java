package com.test.www.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.test.www.myapplication.R;
import com.test.www.myapplication.model.ActivityCollector;
import com.test.www.myapplication.model.BaseApplication;

import org.json.JSONArray;
import org.json.JSONObject;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "BaseActivity";
    private static ProgressDialog progressDialog;
    private long mExitTime;
    public String toolBarTitle = BaseApplication.getContext().getResources().getString(R.string.app_name);
    public int checkedItemId;
    public NavigationView navigationView;

    public static final String REQUEST_HOST = "http://192.168.1.10/php/www_weijingtong_com/";//公司
//    public static final String REQUEST_HOST = "http://192.168.1.46/wjt/";//家

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //加入活动管理器中
        ActivityCollector.addActivity(this);

        //获取当前 Activity 的名称
//        LogUtil.d(TAG, getClass().getSimpleName());
    }

    //初始化侧边栏导航栏等数据
    public void initNav(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置 toolbar 的图标
//        toolbar.setLogo(R.mipmap.ic_launcher);
        //顶部导航中间的文字
        toolbar.setTitle(toolBarTitle);
        setSupportActionBar(toolbar);//执行设定
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //选中状态
        navigationView.getMenu().getItem(checkedItemId).setChecked(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭所以活动
        ActivityCollector.removeActivity(this);
    }
    //按两次退出程序
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String choose = "";
        switch (item.getItemId()) {
            case R.id.nav_home:
                // 首页
                choose = "首页";
                //不是当前活动才启动
                if(checkedItemId!=0){
                    HomeActivity.actionStart(this);
                    finish();
                }
                break;
            case R.id.nav_business:
                //主要业务
                choose = "主要业务";
                if(checkedItemId!=1){
                    BusinessActivity.actionStart(this);
                    finish();
                }
                break;
            case R.id.nav_advantage:
                //核心优势
                choose = "核心优势";
                if(checkedItemId!=2){
                    AdvantageActivity.actionStart(this);
                    finish();
                }
                break;
            case R.id.nav_solution:
                //解决法案
                choose = "解决法案";
                if(checkedItemId!=3){
                    SolutionActivity.actionStart(this);
                    finish();
                }
                break;
            case R.id.nav_experience:
                //产品体验
                choose = "产品体验";
                if(checkedItemId!=4){
                    ExperienceActivity.actionStart(this);
                    finish();
                }
                break;
            case R.id.nav_about:
                //关于我们
                choose = "关于我们";
                if(checkedItemId!=5){
                    AboutActivity.actionStart(this);
                    finish();
                }
                break;
            case R.id.nav_cooperation:
                //合作
                choose = "合作";
                if(checkedItemId!=6){
                    CooperationActivity.actionStart(this);
                    finish();
                }
                break;
            default:
                break;
        }
        Toast.makeText(this, "135 "+choose, Toast.LENGTH_SHORT).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //显示对话框
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(BaseApplication.getContext());
            progressDialog.setMessage("正在加载...");
            //点击屏幕其它地方是否会消失
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    //关闭对话框
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    //解析返回的数据
    public Object parseResult(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData).getJSONObject("result");
            boolean status = jsonObject.getBoolean("status");
            String message = jsonObject.getString("message");
            if (status == false) {
                Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
                return "";
            }
            JSONArray result = jsonObject.getJSONArray("data");
            if(result.length()==0){
                return "empty";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //弹出消息
    public void mToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    //弹出消息
    public static void mToastStatic(String msg){
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    //根据资源id 获取 Bitmap 图片
    public static Bitmap getBitmapFromRes(int resId) {
        Resources res = BaseApplication.getContext().getResources();
        return BitmapFactory.decodeResource(res, resId);
    }
    //获取默认图片，加载之前
    public static Bitmap getPreLoadImg(){
        return BaseActivity.getBitmapFromRes(R.drawable.loading);
    }
}