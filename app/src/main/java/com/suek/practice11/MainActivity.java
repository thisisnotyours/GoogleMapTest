package com.suek.practice11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    //구글지도를 제어하는 참조변수
    GoogleMap GMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager= getSupportFragmentManager();
        final SupportMapFragment mapFragment= (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);



        // 베스트위치정보 제공자를 얻으려면 위치정보제공에 대한 퍼미션 작업을 해야함
        // 위치정보는 [동적 퍼미션- 앱을 실행할때 다이얼로그를 통해 사용자에게 위치정보 허가 여부 확인]
        // AndroidManigest.xml 퍼미션은 먼저하고..
        // 동적퍼미션 작업 (마시멜로우 버전 이상에서만...)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){                    // 위치정보에 대한 퍼미션 허가를 받았는지 스스로 체크부터..
            int permissionResult= checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);    //퍼미션 결과를 숫자로 받음
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {            // 퍼미션 요청 다이얼로그 화면 보이게하는 메소드 호출
                String[] permissions= new String[]{Manifest.permission.ACCESS_FINE_LOCATION};     //배열 몇개짜리 쓰지말고 바로 초기화..
                requestPermissions(permissions, 10);
            }
        }



        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {       //여기서 구글지도가 나옴

                GMap= googleMap;

                // 원하는 좌표객채 생성-1
                LatLng seoul= new LatLng(37.56, 126.97);

                //마커옵션 객체생성(마커의 설정)
                MarkerOptions markerOptions= new MarkerOptions();
                markerOptions.position(seoul);
                markerOptions.title("서울");
                markerOptions.snippet("대한민국의 수도");

                //지도에 마커추가
                GMap.addMarker(markerOptions);

                //원하는 자표위치로 카메라 이동, 마커표시가 됨
                //GMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));

                //카메라 이동을 스무스하게 효과 주면서 Zoom 까지 적용
                GMap.animateCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));





                // 마커 여러개 추가하기
                // 원하는 좌표객채 생성-2
                LatLng mrhi= new LatLng(37.5608, 127.0346);

                MarkerOptions markerOptions1= new MarkerOptions();
                markerOptions1.position(mrhi);
                markerOptions1.title("미래능력개발교육원");
                markerOptions1.snippet("http://www.mrhi.or.kr");
                markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.button_blue));
                markerOptions1.anchor(0.5f, 1.0f);   //그림이미지(x축=0, y축=1)의 좌표

                //GMap.addMarker(markerOptions1);   //지도에 마커추가
                Marker marker= GMap.addMarker(markerOptions1);  //추가된 마커객체를 리턴해줌
                marker.showInfoWindow();

                //GMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mrhi, 16));  //카메라를 mrhi 로 이동- 화면에 balloon 이 보임




                //지도의 정보창을 클릭했을때 반응하기
                GMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String title= marker.getTitle();

                        if(title.equals("미래능력개발교육원")){        //뜨는 정보창이 seoul 과 미래능력교육개발교육원 두가지가 있으니 어떤건지 물어보기
                            //교육원 홈페이지로 이동(본인앱에서 다른 앱으로, 웹브라우저 실행)
                            Intent intent= new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            Uri uri= Uri.parse("http://www.mrhi.or.kr");
                            intent.setData(uri);

                            startActivity(intent);     //새로운화면 스타트
                        }
                    }
                });
                //위에 seoul 로 위치설정이 되어있으니 카메라 위치 변경하기
                GMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mrhi, 16));



                // [번외]정보창의 커스텀 모양 만들기 -> 정보창을 만들어주는 Adapter 객체를 생성해야함 (아답터에서 다른모양 만들기)
                MyInfoWinAdapter adapter= new MyInfoWinAdapter(MainActivity.this);
                GMap.setInfoWindowAdapter(adapter);

                //줌 컨트롤 보이도록 설정하기
                UiSettings settings= GMap.getUiSettings();        //화면의 세팅을 담당
                settings.setZoomControlsEnabled(true);            // + - 줌 컨트롤 생김

                //내 위치 보여주기 [위치정보제공 퍼미션 작업 필요- 동적퍼미션]
                GMap.setMyLocationEnabled(true);



            }
        });

    }
}
