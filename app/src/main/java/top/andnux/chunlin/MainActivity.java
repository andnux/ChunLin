package top.andnux.chunlin;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import top.andnux.mvp.NormalModel;
import top.andnux.mvp.NormalPresenter;
import top.andnux.mvp.NormalView;
import top.andnux.mvp.annotation.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends CLActivity<Object, NormalView, NormalModel, NormalPresenter> {

    @Override
    protected void onCreated(@Nullable Bundle savedInstanceState) {

    }

    public void onClick(View view) {
        startActivity(new Intent(this, Main2Activity.class));
    }
}
