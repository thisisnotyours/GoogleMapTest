package com.suek.practice11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWinAdapter implements GoogleMap.InfoWindowAdapter {       // ***** interface 에서 상속해주기 *****

    Context context;             //context 만 있어도 inflater 사용가능

    public MyInfoWinAdapter(Context context) {    //생성자
        this.context = context;
    }





    @Override
    public View getInfoWindow(Marker marker) { //정보창 틀 모양을 바꿀때
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {  //정보창 안의 모양(이미지첨부, 두개의 텍스드..etc)바꿀때
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.infowin, null);

        switch (marker.getTitle()){
            case "미래능력개발교육원":
                TextView tv= view.findViewById(R.id.tv);
                tv.setText("미래능력개발교육원");
                break;
            case "seoul":
                break;
        }


        return view;
    }
}
