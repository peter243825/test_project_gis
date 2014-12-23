package dev.arcgis.buu;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;

import dev.arcgis.buu.utils.CalculateUtils;
import dev.arcgis.buu.utils.Constants;
import dev.arcgis.buu.utils.L;

import java.util.Timer;
import java.util.TimerTask;

public class ArcGISProjectActivity extends Activity {

    private MapView mMapView;
    private GraphicsLayer mLayerGps;
    private PictureMarkerSymbol locationSymbol;
    private Timer mGetLocationTimer;
    private Point mStartPoint;
    private Polyline mPoly;
    private boolean getLocationFirstTime = true;

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
        }, Constants.kRefreshMapTime * 1000, Constants.kRefreshMapTime * 1000);
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
        final Point gpsPoint = new Point(locx, locy);
        final Point mapPoint = CalculateUtils.getMapPointFromGPSPoint(gpsPoint, mMapView.getSpatialReference());
        L.i(ArcGISProjectActivity.class, "gpsPoint: " + gpsPoint);
        L.i(ArcGISProjectActivity.class, "mapPoint: " + mapPoint);

        //图层的创建
        final Graphic graphicPoint = new Graphic(mapPoint, locationSymbol);
        mLayerGps.addGraphic(graphicPoint);
        /*
        //划线
        if (mStartPoint == null) {
            mPoly = new Polyline();
            mStartPoint = mapPoint;
            mPoly.startPath((float) mStartPoint.getX(), (float) mStartPoint.getY());
            final Graphic graphicLine = new Graphic(mStartPoint, new SimpleLineSymbol(Color.RED, 2));
            mLayerGps.addGraphic(graphicLine);
        }
        mPoly.lineTo(mapPoint.getX(), mapPoint.getY());
        mLayerGps.addGraphic(new Graphic(mPoly, new SimpleLineSymbol(Color.BLACK, 2)));
        */
        //将中心点定位至当前坐标
        mMapView.centerAt(mapPoint, true);
        if (getLocationFirstTime) {
            mMapView.setScale(100000, true);
            getLocationFirstTime = false;
        }
    }

}
