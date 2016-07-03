package drawable.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asus.myapplication.R;

import java.util.Random;

/**
 * Author:   Lianwei Bu
 * Date:     2016/5/6
 * Description:
 */
public class SortitionFragment extends BaseFragment {
    ImageView img_draw; // 签
    Button btn_begin; // 开始抽签
    AnimationDrawable drawAnimation; // 抽签动画
    private int[] ids = new int[]{R.drawable.draw_lots_stop_01,
            R.drawable.draw_lots_stop_02, R.drawable.draw_lots_stop_03}; //抽签结果
    private Random random; //产生抽签结果随机数

    @Override
    protected int getLayoutId() {
        return R.layout.sortition_fragment;
    }

    @Override
    protected void initView() {
        img_draw = (ImageView) getView().findViewById(R.id.img_draw);
        btn_begin = (Button) getView().findViewById(R.id.drawlots_btn_begin);

        btn_begin.setOnClickListener(this);
        random = new Random();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawlots_btn_begin: //开始抽签
                try {
                    img_draw.setBackgroundResource(R.drawable.drawlots);
                    drawAnimation = (AnimationDrawable) img_draw.getBackground();
                    // 设置播放后重新启动
                    drawAnimation.stop();  // 结束动画播放
                    drawAnimation.start(); // 开始动画播放

                    int duration = 0; //抽签动画结束标志
                    for (int i = 0; i < drawAnimation.getNumberOfFrames(); i++) {
                        duration += drawAnimation.getDuration(i);
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            //动画结束后换成结果图片
                            int id = ids[random.nextInt(3)];
                            img_draw.setBackgroundResource(id);
                        }
                    }, duration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
