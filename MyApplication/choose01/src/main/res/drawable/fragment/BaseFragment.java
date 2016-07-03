package drawable.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.myapplication.ui.activity.MainActivity;

public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    protected FloatingActionButton mActionButton;
    protected int screenW;// 获取屏幕分辨率宽度
    protected int screenH;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenW = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        screenH = getActivity().getWindowManager().getDefaultDisplay().getHeight();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),container,false);
    }

    protected abstract int getLayoutId() ;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionButton = ((MainActivity) getActivity()).fab;
        initView();
    }


    protected abstract void initView();
}
