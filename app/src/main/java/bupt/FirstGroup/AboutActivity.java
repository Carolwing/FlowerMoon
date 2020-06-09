package bupt.FirstGroup;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bupt.FirstGroup.helpers.BallDataAdapter;
import bupt.FirstGroup.models.BallData;

public class AboutActivity extends AppCompatActivity {
    //private ListView _listView;
    //private String[] data={"游戏介绍","游戏背景","开发人员","游戏介绍","游戏背景","开发人员","游戏介绍","游戏背景","开发人员"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //保存图像的资源id
        int[] imageid=new int[]{R.drawable.total,R.drawable.hp_grover};
        //字符串数组，用于指定列表项中的文字
        String[] title=new String[]{"全部","开发人员"};
        List<Map<String,Object>> listitem=new ArrayList<Map<String, Object>>();
        for (int i=0;i<imageid.length;i++){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("image",imageid[i]);
            map.put("name",title[i]);
            listitem.add(map);
        }
        //创建适配器
        SimpleAdapter adapter=new SimpleAdapter(this,listitem,R.layout.list_item,new String[]{"name","image"},
                new int[]{R.id.item_name,R.id.item_image});
        ListView listView=(ListView)findViewById(R.id.about_list);
        listView.setAdapter(adapter);
        listView.setDividerHeight(0);


        /*setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this._listView = (ListView)this.findViewById(R.id.about_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
        _listView.setAdapter(adapter);//指定适配器*/

        /*
        BallDataAdapter data = new BallDataAdapter(this);

        data.add(new BallData(R.string.about_ball_normal_title,
                R.string.about_ball_normal_content,
                R.drawable.ball_normal));
        data.add(new BallData(R.string.about_ball_oneup_title,
                R.string.about_ball_oneup_content,
                R.drawable.ball_oneup));
        data.add(new BallData(R.string.about_ball_speeder_title,
                R.string.about_ball_speeder_content,
                R.drawable.ball_speeder));
        data.add(new BallData(R.string.about_ball_bomb_title,
                R.string.about_ball_bomb_content,
                R.drawable.ball_bomb));
        data.add(new BallData(R.string.about_ball_multiplier_title,
                R.string.about_ball_multiplier_content,
                R.drawable.ball_multiplier));
        data.add(new BallData(R.string.about_ball_skull_title,
                R.string.about_ball_skull_content,
                R.drawable.ball_skull));

        this._listView.setAdapter(data);*/

    }
}
