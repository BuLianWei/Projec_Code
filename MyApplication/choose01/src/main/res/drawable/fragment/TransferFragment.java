package drawable.fragment;

import android.app.DialogFragment;
import android.view.View;

import com.example.asus.myapplication.R;
import com.example.asus.myapplication.ui.dialog.ChooseContentDialog;
import com.example.asus.myapplication.utils.pan.MySurfaceView;

public class TransferFragment extends BaseFragment {
    public static final String TAG = "transfer";
private MySurfaceView mSurfaceView;
    @Override
    protected int getLayoutId() {
        return R.layout.tansfer_fragment;
    }


    @Override
    protected void initView() {

        mSurfaceView= (MySurfaceView) getView().findViewById(R.id.pan);
        mActionButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        DialogFragment dialogFragment = ChooseContentDialog.newInstance((MySurfaceView) getView().findViewById(R.id.pan));
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onPause() {
        super.onPause();
        mSurfaceView.isRunning=false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

