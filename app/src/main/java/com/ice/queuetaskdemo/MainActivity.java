package com.ice.queuetaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ice.queuetaskdemo.db.CellModel;
import com.ice.queuetaskdemo.db.OrgModel;
import com.ice.queuetaskdemo.db.UploadStatus;
import com.ice.queuetaskdemo.manager.UploadManger;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_save_data).setOnClickListener(v -> {
            saveData();
        });
    }

    private void saveData(){
        for(int j= 0; j<50; j++){
            OrgModel orgModel = new OrgModel();
            orgModel.setId(UUID.randomUUID().toString());
            orgModel.setUploadState(UploadStatus.INIT.getStatus());
            ArrayList<CellModel> cellModels = new ArrayList<>();
            for(int i = 0; i<100; i++){
                CellModel cellModel = new CellModel();
                cellModel.setId(null);
                cellModel.setAge(i);
                cellModel.setName("ice"+i);
                cellModels.add(cellModel);
            }
            UploadManger.getInstance(MainActivity.this).saveData(orgModel, cellModels);
        }

    }

}