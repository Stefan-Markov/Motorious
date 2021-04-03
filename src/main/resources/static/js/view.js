function initMap() {
    // The location of Uluru
    // Motorious center location
    const uluru = { lat: 42.69586549728161, lng: 23.329053326199812 };
    // 42.69586549728161, 23.329053326199812
    // The map, centered at Uluru
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 12,
        center: uluru,
    });
    // The marker, positioned at Uluru
    const marker = new google.maps.Marker({
        position: uluru,
        map: map,
    });
}