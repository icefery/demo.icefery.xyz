package xyz.icefery.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import xyz.icefery.demo.R;

public class TabFragment extends Fragment {
    private final String title;
    private final Integer imgResId;

    private TextView titleTv;
    private ImageView imgIv;

    public TabFragment(String title, Integer imgResId) {
        this.title = title;
        this.imgResId = imgResId;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("TabFragment 生命周期", this.title + " ==> Attach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TabFragment 生命周期", this.title + " ==> Create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("TabFragment 生命周期", this.title + " ==> CreateView");

        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        // 获取组件引用
        this.titleTv = view.findViewById(R.id.title_tv);
        this.imgIv = view.findViewById(R.id.img_iv);

        // 设置组件的属性
        this.titleTv.setText(this.title);
        this.imgIv.setImageResource(this.imgResId);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TabFragment 生命周期", this.title + " ==> ActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TabFragment 生命周期", this.title + " ==> Start");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TabFragment 生命周期", this.title + " ==> Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TabFragment 生命周期", this.title + " ==> Pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TabFragment 生命周期", this.title + " ==> Stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TabFragment 生命周期", this.title + " ==> Destroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("TabFragment 生命周期", this.title + " ==> Detach");
    }
}