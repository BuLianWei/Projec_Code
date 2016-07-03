package drawable.fragment;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.asus.myapplication.R;
import com.example.asus.myapplication.utils.coin.TossAnimation;
import com.example.asus.myapplication.utils.coin.TossImageView;

import java.util.Random;

public class CoinFragment extends BaseFragment {
    public static final String TAG = "coin";
    private TossImageView mTossImageView;
    private RelativeLayout mRelativeLayout;
    Random random = new Random();
    @Override
    protected int getLayoutId() {
        return R.layout.coin_fragment;
    }

    @Override
    protected void initView() {
        mRelativeLayout = (RelativeLayout) getView().findViewById(R.id.rLayout);
        mTossImageView = new TossImageView(getActivity());
        mTossImageView.setImageResource(R.mipmap.front);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.width = 400;
        layoutParams.height = 400;
        layoutParams.bottomMargin=100;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTossImageView.setLayoutParams(layoutParams);
        mRelativeLayout.addView(mTossImageView);
        mActionButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        mTossImageView.cleareOtherAnimation();

        TranslateAnimation translateAnimation0 = new TranslateAnimation(0, 0, 0, -screenH+800);
        translateAnimation0.setDuration(3000);
        TranslateAnimation translateAnimation1 = new TranslateAnimation(0, 0, 0, screenH-800);
        translateAnimation1.setDuration(3000);
        translateAnimation1.setStartOffset(3000);
        mTossImageView.setInterpolator(new DecelerateInterpolator())
                .setFrontDrawable(getActivity().getResources().getDrawable(R.mipmap.front))
                .setReversetDrawable(getActivity().getResources().getDrawable(R.mipmap.reverse))
                .setDuration(6000)
                .setCircleCount(40)
                .setXAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                .setYAxisDirection(TossAnimation.DIRECTION_NONE)
                .setZAxisDirection(TossAnimation.DIRECTION_NONE)
                .setResult(random.nextInt(2) == 0 ? TossImageView.RESULT_FRONT : TossImageView.RESULT_REVERSE);

        mTossImageView.addOtherAnimation(translateAnimation0);
        mTossImageView.addOtherAnimation(translateAnimation1);

        mTossImageView.startToss();
//        if (random.nextInt(2)==0)
//            mTossImageView.setImageResource(R.mipmap.front);
//        else
//            mTossImageView.setImageResource(R.mipmap.reverse);
    }
}
