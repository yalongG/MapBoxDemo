# MapBoxDemo
mapbox基本的用法。包括增加marker、polygon、polyline、camera、source和layer。

```
implementation 'com.mapbox.mapboxsdk:mapbox-android-sdk:5.5.0'
```

### marker

```java
// 新增marker
mMap.addMarker(new MarkerOptions().position(new LatLng(-37.821648, 144.978594))
                        .title("marker"));
// 自定义marker
Icon icon = IconFactory.getInstance(this).fromResource(R.mipmap.ic_launcher);
mMap.addMarker(new MarkerViewOptions().position(new LatLng(-37.82164, 144.97859))
                        .icon(icon));
// 移除marker
if (marker != null) {
    mMap.removeMarker(marker);
}
 mMap.clear(); // 删除所有marker
```

### polygon

```java
// add polygon			
 List<LatLng> polygonLatLngList = new ArrayList<>();
 polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
 polygonLatLngList.add(new LatLng(-37.534611, 144.708873));
 polygonLatLngList.add(new LatLng(-37.530883, 144.678833));
 polygonLatLngList.add(new LatLng(-37.547115, 144.667503));
 polygonLatLngList.add(new LatLng(-37.530643, 144.660121));
 polygonLatLngList.add(new LatLng(-37.533529, 144.636260));
 polygonLatLngList.add(new LatLng(-37.521743, 144.659091));
 polygonLatLngList.add(new LatLng(-37.510677, 144.648792));
 polygonLatLngList.add(new LatLng(-37.515008, 144.664070));
 polygonLatLngList.add(new LatLng(-37.502496, 144.669048));
 polygonLatLngList.add(new LatLng(-37.515369, 144.678489));
 polygonLatLngList.add(new LatLng(-37.506346, 144.702007));
 polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
 mMap.addPolygon(new PolygonOptions().addAll(polygonLatLngList).fillColor(Color.YELLOW));

// remove polygon
mMap.removePolygon(polygon);
```

### polyline

```java
 // add polyline
 List<LatLng> polygonLatLngList = new ArrayList<>();
 polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
 polygonLatLngList.add(new LatLng(-37.534611, 144.708873));
 polygonLatLngList.add(new LatLng(-37.530883, 144.678833));
 polygonLatLngList.add(new LatLng(-37.547115, 144.667503));
 polygonLatLngList.add(new LatLng(-37.530643, 144.660121));
 polygonLatLngList.add(new LatLng(-37.533529, 144.636260));
 polygonLatLngList.add(new LatLng(-37.521743, 144.659091));
 polygonLatLngList.add(new LatLng(-37.510677, 144.648792));
 polygonLatLngList.add(new LatLng(-37.515008, 144.664070));
 polygonLatLngList.add(new LatLng(-37.502496, 144.669048));
 polygonLatLngList.add(new LatLng(-37.515369, 144.678489));
 polygonLatLngList.add(new LatLng(-37.506346, 144.702007));
 polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
 mMap.addPolyline(new PolylineOptions()
                        .addAll(polygonLatLngList).width(2f).color(Color.RED));
 // remove polyline
 mMap.removePolyline(polyline);
```

### camera

```java
// 将某个点移动到屏幕中间 moveCamera()   easeCamera()	 animateCamera()
// mMap.getCameraPosition()
 CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(51.50550, -0.07520))
                        .zoom(10) 
                        .build(); 
 mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);

// 将多个坐标点移动到屏幕中间
 List<LatLng> polygonLatLngList = new ArrayList<>();
 polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
 polygonLatLngList.add(new LatLng(-37.534611, 144.708873));
 polygonLatLngList.add(new LatLng(-37.530883, 144.678833));
 polygonLatLngList.add(new LatLng(-37.547115, 144.667503));
 polygonLatLngList.add(new LatLng(-37.530643, 144.660121));
 polygonLatLngList.add(new LatLng(-37.533529, 144.636260));
 polygonLatLngList.add(new LatLng(-37.521743, 144.659091));
 polygonLatLngList.add(new LatLng(-37.510677, 144.648792));
 polygonLatLngList.add(new LatLng(-37.515008, 144.664070));
 polygonLatLngList.add(new LatLng(-37.502496, 144.669048));
 polygonLatLngList.add(new LatLng(-37.515369, 144.678489));
 polygonLatLngList.add(new LatLng(-37.506346, 144.702007));
 polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
 for (int i = 0, j = polygonLatLngList.size(); i < j; i++) {
     mMap.addMarker(new MarkerOptions().position(polygonLatLngList.get(i)));
 }
LatLngBounds latLngBounds = new LatLngBounds.Builder().includes(polygonLatLngList).build();
mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 20));
```

### source and layer

```java
// 添加光栅source和光栅layer
TileSet autoNavTileSet = new TileSet("8",String url);
RasterSource autoNavImageSourec = new RasterSource(SOURCE_ID,
                        autoNavTileSet, 256);
mMap.addSource(autoNavImageSourec);
RasterLayer baseMapLayer = new RasterLayer(LAYER_ID, SOURCE_ID);
mMap.addLayer(baseMapLayer);

// 添加矢量source
TileSet labelTileSet = new TileSet("8", String url);
VectorSource labelVectorSource = new VectorSource(VECTOR_SOURCE_ID, labelTileSet);
mMap.addSource(labelVectorSource);
// 添加 fill layer
FillLayer labelLayer = new FillLayer(VECTOR_LAYER_ID, VECTOR_SOURCE_ID);
labelLayer.setProperties(fillColor("color"));
mMap.addLayer(labelLayer);
// 添加 line layer
LineLayer lineLayer = new LineLayer(VECTOR_LAYER_LINE_ID, VECTOR_SOURCE_ID);
lineLayer.setProperties(lineColor(Color.BLUE));
mMap.addLayer(lineLayer);

// 添加geojson source
GeoJsonSource geoJsonSource = new GeoJsonSource(GEOJSON_SOURCE_ID,
                                                loadGeoJsonFromAsset("white_house.geojson"));
mMap.addSource(geoJsonSource);
LineLayer layer = new LineLayer(GEOJSON_LAYER_ID, GEOJSON_SOURCE_ID);
layer.setProperties(lineColor(Color.YELLOW), lineWidth(5f));
mMap.addLayer(layer);

// 自定义line layer
List<Position> lineCoordinates = new ArrayList<>();
for (int i = 0, j = getPositionList().size(); i < j; i++) {        
    lineCoordinates.add(Position.fromCoordinates(
    getPositionList().get(i).getLongitude(), getPositionList().get(i).getLatitude()));
 }
LineString lineString = LineString.fromCoordinates(lineCoordinates);
FeatureCollection featureCollection = FeatureCollection.
    fromFeatures(new Feature[]{Feature.fromGeometry(lineString)});
 Source source = new GeoJsonSource(LINE_SOURCE_ID, featureCollection);
 mMap.addSource(source);
 LineLayer lineLayer1 = new LineLayer(LINE_LAYER_ID, LINE_SOURCE_ID);
 lineLayer1.setProperties(
     PropertyFactory.lineCap(LINE_CAP_ROUND),
     PropertyFactory.lineJoin(LINE_JOIN_ROUND),
     PropertyFactory.lineWidth(2f),
     PropertyFactory.lineColor(Color.GREEN));
mMap.addLayer(lineLayer1);

// 自定义 fill layer
List<Position> positionList = new ArrayList<>();
for (int i = 0, j = getPositionList().size(); i < j; i++) {
    positionList.add(Position.fromCoordinates(
        getPositionList().get(i).getLongitude(), getPositionList().get(i).getLatitude()));
}
List<List<Position>> listList = new ArrayList<>();
listList.add(positionList);
Polygon polygon = Polygon.fromCoordinates(listList);
FeatureCollection featureCollection1 = FeatureCollection.fromFeatures(
    new Feature[]{Feature.fromGeometry(polygon)});
Source fillSource = new GeoJsonSource(FILL_SOURCE_ID, featureCollection1);
mMap.addSource(fillSource);
FillLayer fillLayer = new FillLayer(FILL_LAYER_ID, FILL_SOURCE_ID);
fillLayer.setProperties(fillColor(Color.RED));
mMap.addLayer(fillLayer);

//自定义 symbol layer 显示文字
List<Feature> textFeature = new ArrayList<>();
for (int i = 0, j = getPositionList().size(); i < j; i++) {
    JsonObject object = new JsonObject();
    object.addProperty("position", "第" + i + "个"); // 自定义一些值
    textFeature.add(Feature.fromGeometry(
        Point.fromCoordinates(
            Position.fromCoordinates(getPositionList().get(i).getLongitude(),
                                     etPositionList().get(i).getLatitude()))
        , object));
}
FeatureCollection mFeature = FeatureCollection.fromFeatures(textFeature);
mMap.addSource(new GeoJsonSource(TEXT_SOURCE_ID, mFeature));
SymbolLayer textLayer = new SymbolLayer(TEXT_LAYER_ID, TEXT_SOURCE_ID);
textLayer.setProperties(
    PropertyFactory.textField("{position}"), // 显示对应的值
    PropertyFactory.textSize(15f),
    PropertyFactory.textColor(Color.DKGRAY)
);
mMap.addLayer(textLayer);
```

getPositionList()

```java
// 获取点
private List<LatLng> getPositionList() {
    CameraPosition position2 = new CameraPosition.Builder()
        target(new LatLng(-37.522585, 144.685699)) 
        .zoom(12)
        .build(); 
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position2), 2000);
    List<LatLng> polygonLatLngList = new ArrayList<>();
    polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
    polygonLatLngList.add(new LatLng(-37.534611, 144.708873));
    polygonLatLngList.add(new LatLng(-37.530883, 144.678833));
    polygonLatLngList.add(new LatLng(-37.547115, 144.667503));
    polygonLatLngList.add(new LatLng(-37.530643, 144.660121));
    polygonLatLngList.add(new LatLng(-37.533529, 144.636260));
    polygonLatLngList.add(new LatLng(-37.521743, 144.659091));
    polygonLatLngList.add(new LatLng(-37.510677, 144.648792));
    polygonLatLngList.add(new LatLng(-37.515008, 144.664070));
    polygonLatLngList.add(new LatLng(-37.502496, 144.669048));
    polygonLatLngList.add(new LatLng(-37.515369, 144.678489));
    polygonLatLngList.add(new LatLng(-37.506346, 144.702007));
    polygonLatLngList.add(new LatLng(-37.522585, 144.685699));
    return polygonLatLngList;
}
```

