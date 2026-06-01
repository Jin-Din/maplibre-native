package org.maplibre.android.testapp.styles;

import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.Style;
import org.maplibre.android.style.layers.Layer;

import java.io.IOException;
import java.util.List;

public class TMapSource {

    private final MapLibreMap mapLibreMap;

    private String _mapStyleId;

    public TMapSource(MapLibreMap mapLibreMap) {
        this.mapLibreMap = mapLibreMap;
    }
    public void init(String key) {
        // 注入token
    }
    public void setMapStyle(String mapStyleId) {
        setMapStyle(mapStyleId,null);
    }
    public void setMapStyle(String mapStyleId, Style.OnStyleLoaded onStyleLoadedCallback) {

        if (mapStyleId == null) {
            return;
        }

        Style.Builder newStyleBuilder = new Style.Builder().fromUri(getMapStyleUrl(mapStyleId));
        if(this._mapStyleId == null) {
            // 直接加载地图样式
            this.mapLibreMap.setStyle(newStyleBuilder,onStyleLoadedCallback);
            this._mapStyleId = mapStyleId;
            return;
        }
        if (_mapStyleId.equals(mapStyleId)) {
            return;
        }

        // 对比地图样式
        boolean isOldVector = this._mapStyleId.startsWith("vector_");
        boolean isNewVector = mapStyleId.startsWith("vector_");

       if(isNewVector && isOldVector){
           // 如果都是矢量地图样式，不改变source,只更换图层
           Style currentStyle = this.mapLibreMap.getStyle();
//           // 查找当前图层中以“base_vector_”开头的 图层,并移除
           List<Layer> layers = currentStyle.getLayers();
//           for (Layer layer : layers) {
//               if (layer.getId().startsWith("base_vector_")) {
//                   currentStyle.removeLayer(layer);
//               }
//           }
           // 获取移除后剩下图层中第一个图层的id.首先判断当前剩余图层是否为空
           // 再次获取当前图层列表
           layers = currentStyle.getLayers();
           if(layers.isEmpty()){
               // 如果当前剩余图层为空,则直接加载地图样式

           }
           else{
           // 如果当前剩余图层不为空,则获取第一个图层的id,新图层都添加到第一个图层前面
           String firstLayerId = layers.get(0).getId();

           }
//
//           this._mapStyleId = mapStyleId;
//
//           return;

       }
        this.mapLibreMap.setStyle(newStyleBuilder,onStyleLoadedCallback);
        this._mapStyleId = mapStyleId;
    }

    public String getMapStyleId() {
        return _mapStyleId;
    }
    public String MAP_TYPE_NORMAL_URL = "asset://styles/vector_style_3857_proxy_default.json";
//    public String MAP_TYPE_NORMAL_URL = "youmap://210.74.129.84:8520/styles/vector_style_3857_proxy.json";
    public String MAP_TYPE_BLUE_URL = "asset://styles/vector_style_3857_proxy_blue.json";
    public String MAP_TYPE_SATELLITE_URL = "asset://styles/tdt_w.json";

    public Style.Builder getMapStyleBuilder(String mapStyleId) {
        return new Style.Builder().fromUri(getMapStyleUrl(mapStyleId));
    }

    private String getMapStyleUrl(String mapStyleId) {
        return switch (mapStyleId) {
            case TMapStyle.MAP_TYPE_NORMAL -> MAP_TYPE_NORMAL_URL;
//            case TMapStyle.MAP_TYPE_DARK:
//                return "asset://styles/vector_style_3857_proxy_dark.json";
            case TMapStyle.MAP_TYPE_BLUE -> MAP_TYPE_BLUE_URL;
            case TMapStyle.MAP_TYPE_SATELLITE -> MAP_TYPE_SATELLITE_URL;
            default -> mapStyleId;
        };
    }

private void readStyleUriRaw(String mapStyleId) {
    String styleUri = getMapStyleUrl(mapStyleId);
    if(styleUri.startsWith("asset://")){
        // 从assets目录加载地图样式
        // 读取文件

    }
}

    public static class TMapStyle {
        public final static String MAP_TYPE_NORMAL = "vector_normal";
//        public final static String MAP_TYPE_DARK = "vector_dark";
        public final static String MAP_TYPE_BLUE = "vector_blue";
        public final static String MAP_TYPE_SATELLITE = "image_satellite";
    }
}
