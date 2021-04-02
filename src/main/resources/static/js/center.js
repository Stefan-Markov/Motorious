function moveMap(map){
    map.setCenter({lat:42.69586549728161, lng:23.329053326199812});
    map.setZoom(14);
}

var platform = new H.service.Platform({
    apikey: window.apikey
});
var defaultLayers = platform.createDefaultLayers();

var map = new H.Map(document.getElementById('center'),
    defaultLayers.vector.normal.map,{
        center: {lat:50, lng:5},
        zoom: 12,
        pixelRatio: window.devicePixelRatio || 1
    });
window.addEventListener('resize', () => map.getViewPort().resize());


var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

var ui = H.ui.UI.createDefault(map, defaultLayers);

window.onload = function () {
    moveMap(map);
}