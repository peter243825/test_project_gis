package dev.arcgis.buu;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;

import dev.arcgis.buu.utils.L;

import java.util.Timer;
import java.util.TimerTask;

public class ArcGISProjectActivity extends Activity {

    MapView mMapView;
    GraphicsLayer mLayerGps;
    PictureMarkerSymbol locationSymbol;
    Timer mGetLocationTimer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Retrieve the map and initial extent from XML layout
        mMapView = (MapView) findViewById(R.id.map);
        // Add dynamic layer to MapView
        mMapView.addLayer(new ArcGISTiledMapServiceLayer("http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer"));
        mLayerGps = new GraphicsLayer();
        mMapView.addLayer(mLayerGps);
        locationSymbol = new PictureMarkerSymbol(this.getResources().getDrawable(R.drawable.location));

        mGetLocationTimer = new Timer();
        mGetLocationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                markLocation(BaseApplication.getCurrentLocation());
            }
        }, 10 * 1000, 10 * 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.unpause();
    }

    /**
     * 在地图上显示当前位置
     * 
     * @param location
     */
    private void markLocation(final BDLocation location) {
        if (location == null) {
            return;
        }
        mLayerGps.removeAll();
        final double locx = location.getLongitude();
        final double locy = location.getLatitude();
        final Point wgspoint = new Point(locx, locy);
        final Point mapPoint =
                (Point) GeometryEngine.project(wgspoint, SpatialReference.create(4326), mMapView.getSpatialReference());
        L.i(ArcGISProjectActivity.class, "wgspoint: " + wgspoint);
        L.i(ArcGISProjectActivity.class, "mapPoint: " + mapPoint);

        //图层的创建
        final Graphic graphicPoint = new Graphic(mapPoint, locationSymbol);
        mLayerGps.addGraphic(graphicPoint);
        /*划线
        if (startPoint == null) {
          poly=new Polyline();
          startPoint = mapPoint;
          poly.startPath((float) startPoint.getX(),
              (float) startPoint.getY());
          Graphic graphicLine = new Graphic(startPoint,new SimpleLineSymbol(Color.RED,2));
          gLayerGps.addGraphic(graphicLine);
        }
          final Polyline poly = new Polyline();
          poly.lineTo(mapPoint.getX(), mapPoint.getY());
          mLayerGps.addGraphic(new Graphic(poly, new SimpleLineSymbol(Color.BLACK, 2)));*/
        mMapView.centerAt(mapPoint, true);
    }

}
