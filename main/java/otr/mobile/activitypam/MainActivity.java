package otr.mobile.activitypam;

import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.Manifest;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions;
import com.mapbox.maps.plugin.delegates.MapDelegateProvider;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;

public class MainActivity extends AppCompatActivity {


    MapView mapView;
    FloatingActionButton floatingActionButton;
    ImageButton realLoco, ibList;
    private final ActivityResultLauncher<String>activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result){
                Toast.makeText(MainActivity.this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this,"Permission not Granted",Toast.LENGTH_SHORT).show();

            }
        }
    });
    private final OnIndicatorBearingChangedListener onIndicatorBearingChangedListener = new OnIndicatorBearingChangedListener() {
        @Override
        public void onIndicatorBearingChanged(double v) {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
        }
    };
    private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener = new OnIndicatorPositionChangedListener() {
        @Override
        public void onIndicatorPositionChanged(@NonNull Point point) {
            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().center(point).zoom(20.0).build());
            getGestures(mapView).setFocalPoint(mapView.getMapboxMap().pixelForCoordinate(point));
        }
    };
    private final OnMoveListener onMoveListener = new OnMoveListener() {
        @Override
        public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
            getLocationComponent(mapView).removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
            getLocationComponent(mapView).removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
            getGestures(mapView).removeOnMoveListener(onMoveListener);
            floatingActionButton.show();
        }

        @Override
        public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
            return false;
        }

        @Override
        public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        floatingActionButton = findViewById(R.id.focusLocation);
        floatingActionButton.hide();

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                mapView.getMapboxMap().setCamera(new CameraOptions.Builder().zoom(20.0).build());
                LocationComponentPlugin locationComponentPlugin = getLocationComponent(mapView);
                locationComponentPlugin.setEnabled(true);
                locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);

//                AnnotationPlugin annotationPlugin = mapView.getPlugin(AnnotationPlugin.class.getSimpleName());
//                AnnotationConfig annotationConfig = new AnnotationConfig();
//                MapDelegateProvider mapDelegateProvider = mapView.getPlugin(MapDelegateProvider.class.getSimpleName());
//                CircleAnnotationOptions options = new CircleAnnotationOptions()
//                        .withPoint(Point.fromLngLat(36, 40)) // Koordinat marker
//                        .withCircleColor(Color.RED) // Warna marker
//                        .withCircleRadius(10f); // Radius marker
//                CircleAnnotationManager circleAnnotationManager = new CircleAnnotationManager(mapDelegateProvider, annotationConfig);
//                circleAnnotationManager.create(options);

                getGestures(mapView).addOnMoveListener(onMoveListener);
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                        getGestures(mapView).addOnMoveListener(onMoveListener);
                        floatingActionButton.hide();
                    }
                });
                realLoco = findViewById(R.id.realLoco);
                ibList = findViewById(R.id.ibList);
//                realLoco.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this, zoom.class);
//                        startActivity(intent);
//                    }
//                });

                realLoco.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ZoomFragment zoomFragment = new ZoomFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, zoomFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });


                ibList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, List.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}