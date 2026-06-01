package org.maplibre.android.testapp.activity.maplayout

import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.*
import org.maplibre.android.maps.*
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.SymbolLayer
import org.maplibre.android.style.sources.*
import org.maplibre.geojson.Feature

import org.maplibre.android.testapp.R
import org.maplibre.android.testapp.styles.TMapSource
import org.maplibre.android.testapp.styles.TestStyles
import org.maplibre.android.testapp.utils.ApiKeyUtils
import org.maplibre.android.testapp.utils.NavUtils


/**
 * Test activity showcasing a simple MapView without any MapLibreMap interaction.
 */
class SimpleMapActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private var mapLibreMap: MapLibreMap? = null
    private lateinit var mapStyleImage: Button
    private lateinit var mapStyleDefault: Button
    private lateinit var mapStyleBlue: Button


    private lateinit var mapStyleId: String;

    private lateinit var tMapSource: TMapSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // activity uses singleInstance for testing purposes
                // code below provides a default navigation when using the app
                NavUtils.navigateHome(this@SimpleMapActivity)
            }
        })
        setContentView(R.layout.activity_map_simple)

        mapStyleImage = findViewById(R.id.map_style_image)
        mapStyleDefault = findViewById(R.id.map_style_default)
        mapStyleBlue = findViewById(R.id.map_style_blue)

        mapStyleImage.setOnClickListener {

//            mapLibreMap?.setStyle( Style.Builder().fromUri(TestStyles.ASSET_TIANDITU_W_STYLE))
            tMapSource?.setMapStyle(TMapSource.TMapStyle.MAP_TYPE_SATELLITE)


        }

        mapStyleDefault.setOnClickListener {
//            mapLibreMap?.setStyle( Style.Builder().fromUri(TestStyles.ASSET_YOUMAP_DEFAULT_STYLE))
            tMapSource?.setMapStyle(TMapSource.TMapStyle.MAP_TYPE_NORMAL)
        }
        mapStyleBlue.setOnClickListener {
//            mapLibreMap?.setStyle( Style.Builder().fromUri(TestStyles.ASSET_YOUMAP_BLUE_STYLE))
            tMapSource?.setMapStyle(TMapSource.TMapStyle.MAP_TYPE_BLUE)
        }

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            mapLibreMap = it

            // 隐藏地图上的 logo 和 attribution
            it.uiSettings.apply {
                isLogoEnabled = true

                isZoomInOutEnabled = true
                isCompassEnabled = true
                isAttributionEnabled = false

                setZoomInOutMargins(25, 25, 85, 25)
            }

            it.cameraPosition = CameraPosition.Builder()
                .tilt(45.0)
                .bearing(0.0)
                .zoom(9.0)
                .target(LatLng(34.263, 108.953)).build()


            tMapSource = TMapSource(mapLibreMap!!)

            tMapSource.setMapStyle(TMapSource.TMapStyle.MAP_TYPE_NORMAL){ style->

                // Add an icon to reference later
                if (style.getImage("marker-icon") == null) {
                    style.addImage(
                        "marker-icon",
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.ic_marker
                        )
                    )


                }
                // 自定义 geojson 点
                val pointJsonSource = GeoJsonSource(
                    "point", Feature.fromJson(
                        """
                {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": [108.953, 34.263]
                    },
                    "properties": {
                        "name": "我是一个点"
                    }
                } """.trimIndent()
                    )
                );

                style.addSource(pointJsonSource)
                style.addLayer(
                    SymbolLayer("point-symbol", "point").withProperties(
                        PropertyFactory.iconImage("marker-icon"),
                        PropertyFactory.iconSize(1.0f),
                    )
                )
            }


            return@getMapAsync;

            // 记载 自定义服务地址
            /**
             * 要想正确记载自定义的服务地址，需要在 AndroidManifest.xml 中配置网络权限以及网络跨域配置
             */
            val styleBuilder = Style.Builder().fromUri(TestStyles.ASSET_TIANDITU_W_STYLE)



            it.setStyle(styleBuilder) { style ->



            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // activity uses singleInstance for testing purposes
                // code below provides a default navigation when using the app
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
