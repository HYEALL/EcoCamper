var listValue = document.getElementById('list').value;
var list = JSON.parse(listValue);
console.log(list);

var size = Object.keys(list).length;
console.log(size);  

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

//////////////////////////////////////////////////////////////////////////////////////////
// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

//////////////////////////////////////////////////////////////////////////////////////////
// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

//////////////////////////////////////////////////////////////////////////////////////////
// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
if (navigator.geolocation) {
         
         // GeoLocation을 이용해서 접속 위치를 얻어옵니다
         navigator.geolocation.getCurrentPosition(function(position) {
            
            var lat = position.coords.latitude, // 위도
                lon = position.coords.longitude; // 경도
            
            var imageSrc = 'https://cdn-icons-png.flaticon.com/128/4668/4668400.png',
               imageSize = new kakao.maps.Size(64, 69), 
               imageOption = {offset : new kakao.maps.Point(27, 69)},
               markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
               markerPosition = new kakao.maps.LatLng(lat, lon);
            
            // 마커를 표시합니다
            var marker = new kakao.maps.Marker({
               position : markerPosition,
               image : markerImage
            });

            marker.setMap(map);
            map.setCenter(markerPosition);
         });
      } else {   // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다 (임시로 학원 위치로 설정)
         //var locPosition = new kakao.maps.LatLng(33.450701, 126.570667), message = 'geolocation을 사용할수 없어요..';
         //displayMarker(locPosition, message);
         
         // 주소에 따라 마커를 생성하여 지도에 표시합니다
         
             geocoder.addressSearch('서울 동작구 장승배기로 171 아이비빌딩 301호', function(result, status) {
                 if (status === kakao.maps.services.Status.OK) {

                     var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                  
                  var imageSrc = 'https://cdn-icons-png.flaticon.com/128/4668/4668400.png',
                     imageSize = new kakao.maps.Size(64, 69),
                     imageOption = { offset: new kakao.maps.Point(27, 69) },
                     markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                     markerPosition = coords;

                  // 마커를 표시합니다
                  var marker = new kakao.maps.Marker({
                     position: markerPosition,
                     image: markerImage
                  });

                  marker.setMap(map);
                  map.setCenter(markerPosition);
                  
                 } 
             });
         
      }

      // 지도에 마커와 인포윈도우를 표시하는 함수입니다
      function displayMarker(locPosition, message) {
         
         // 마커를 생성합니다
         var marker = new kakao.maps.Marker({
            map : map,
            position : locPosition
         });

         var iwContent = message,    // 인포윈도우에 표시할 내용
            iwRemoveable = true;

         // 인포윈도우를 생성합니다
         var infowindow = new kakao.maps.InfoWindow({
            content : iwContent,
            removable : iwRemoveable
         });

         // 인포윈도우를 마커위에 표시합니다 
         infowindow.open(map, marker);
         
         // 지도 중심좌표를 접속위치로 변경합니다
         map.setCenter(locPosition);
      }    

//////////////////////////////////////////////////////////////////////////////////////////


// 주소에 따라 마커를 생성하여 지도에 표시합니다
list.forEach(function(list) {
    geocoder.addressSearch(list.place_address, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {

            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 결과값으로 받은 위치를 마커로 표시합니다
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

            // 마커에 표시할 인포윈도우를 생성합니다 
            var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;">' + list.place_name + '</div>' // 장소 이름
            });

            // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
            kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
        } 
    });
});

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}

