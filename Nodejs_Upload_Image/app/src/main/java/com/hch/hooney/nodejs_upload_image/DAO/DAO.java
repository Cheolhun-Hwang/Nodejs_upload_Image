package com.hch.hooney.nodejs_upload_image.DAO;

import java.util.ArrayList;

/**
 * Created by hooney on 2018. 1. 16..
 */

public class DAO {
    public static ArrayList<InfoPoint> PoingList;

    public DAO() {
        PoingList = new ArrayList<InfoPoint>();
    }

    public void LoadPointData(){
        for(int i = 0 ; i<10;i++){
            PoingList.add(new InfoPoint("2018-1-"+(i+1), ((i+1)*5), "Context "+i));
        }
    }
}
