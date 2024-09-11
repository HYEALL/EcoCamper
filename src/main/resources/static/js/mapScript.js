var listValue = document.getElementById('list').value;
var list = JSON.parse(listValue);
console.log(list);

var size = Object.keys(list).length;
console.log(size);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 지도 컨테이너 및 옵션 설정
var mapContainer = document.getElementById('map'),
	mapOption = {
		center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도 중심 좌표 설정
		level: 3 // 지도 확대 레벨 설정
	};

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 생성
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 지도 타입 컨트롤 추가
var mapTypeControl = new kakao.maps.MapTypeControl();
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 줌 컨트롤 추가
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////


// 주소-좌표 변환 객체 생성
var geocoder = new kakao.maps.services.Geocoder();

// 마커 클러스터러 생성
var clusterer = new kakao.maps.MarkerClusterer({
	map: map,
	averageCenter: true, // 클러스터 중심 좌표 설정
	minLevel: 10 // 클러스터링 최소 레벨 설정
});

// HTML5 Geolocation API를 사용해 현재 위치 표시
// !현위치 마커 및 오류 처리 추가
if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(function(position) {
		var lat = position.coords.latitude; // 위도
		var lon = position.coords.longitude; // 경도
		var locPosition = new kakao.maps.LatLng(lat, lon); // 현위치 좌표 생성

		// 지도 중심을 현위치로 이동
		map.setCenter(locPosition);

		// 현위치 마커 추가
		var marker = new kakao.maps.Marker({
			map: map,
			position: locPosition,
			image: new kakao.maps.MarkerImage(
				'https://cdn-icons-png.flaticon.com/128/4668/4668400.png', // 현위치 아이콘
				new kakao.maps.Size(64, 69),
				{ offset: new kakao.maps.Point(27, 69) }
			)
		});
	}, function() {
		// 위치 정보를 가져오지 못했을 경우
		var geocoder = new kakao.maps.services.Geocoder(); // 주소 -> 좌표 변환 객체 생성

		// 주소로 좌표를 검색
		geocoder.addressSearch('서울 동작구 장승배기로 171', function(result, status) {
			if (status === kakao.maps.services.Status.OK) {
				var defaultPosition = new kakao.maps.LatLng(result[0].y, result[0].x); // 변환된 좌표

				map.setCenter(defaultPosition);

				// 기본 위치 마커 추가
				var marker = new kakao.maps.Marker({
					map: map,
					position: defaultPosition,
					image: new kakao.maps.MarkerImage(
						'https://cdn-icons-png.flaticon.com/128/4668/4668400.png', // 현위치 아이콘
						new kakao.maps.Size(64, 69),
						{ offset: new kakao.maps.Point(27, 69) }
					)
				});

				// 알림창 표시
				alert("현재 위치를 받아오지 못했습니다. \n KG에듀원 아이티뱅크 노량진학원의 위치가 현위치로 설정되었습니다.");
			}
		});
	});
} else {
	// 브라우저에서 위치 정보를 제공하지 않을 경우
	alert("GPS를 사용할 수 없습니다.");
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 공간 유형별 마커 이미지 생성 함수
function createMarkerImage(category) {
	let imageSrc;

	// 카테고리별 마커 이미지 설정
	switch (category) {
		case 'place_c01':
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/5695/5695223.png'; // 캠핑장
			break;
		case 'place_c02':
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/5695/5695160.png'; // 차박, 노지
			break;
		case 'place_c03':
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/5693/5693821.png'; // 글램핑, 카라반
			break;
		case 'place_c04':
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/5695/5695640.png'; // 백패킹, 하이킹
			break;
		case 'place_c05':
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/5695/5695240.png'; // 낚시 스팟
			break;
		case 'place_c06':
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/5693/5693894.png'; // 여행
			break;
		case 'place_c07':
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/5695/5695218.png'; // 액티비티
			break;
		case 'place_c08':
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/5695/5695257.png'; // 워터스포츠
			break;
		case 'place_c09':
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/5693/5693783.png'; // 스토어
			break;
		default:
			imageSrc = 'https://cdn-icons-png.flaticon.com/128/8234/8234611.png'; // 기본 이미지
			break;
	}

	var imageSize = new kakao.maps.Size(40, 43), // 마커 이미지 크기
		imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커 위치 조정

	return new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption); // 마커 이미지 반환
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 검색 & 필터 기능
function searchPlaces() {
    // 검색어 값 (검색어가 없을 경우 빈 문자열로 처리)
    const keyword = document.getElementById('keyword').value || '';

    // 선택된 지역 필터 값 (없으면 null로 설정)
    let selectedRegions = Array.from(document.querySelectorAll('input[name="region"]:checked')).map(cb => cb.value);
    if (selectedRegions.length === 0) {
        selectedRegions = null;
    }

    // 선택된 공간유형 필터 값 (없으면 null로 설정)
    let selectedCategories = Array.from(document.querySelectorAll('input[name="category"]:checked')).map(cb => cb.value);
    if (selectedCategories.length === 0) {
        selectedCategories = null;
    }

    // 선택된 편의시설 필터 값 (없으면 null로 설정)
    let selectedFacilities = Array.from(document.querySelectorAll('input[name="facilities"]:checked')).map(cb => cb.value);
    if (selectedFacilities.length === 0) {
        selectedFacilities = null;
    }

    // 선택된 주변환경 필터 값 (없으면 null로 설정)
    let selectedEnvironments = Array.from(document.querySelectorAll('input[name="environment"]:checked')).map(cb => cb.value);
    if (selectedEnvironments.length === 0) {
        selectedEnvironments = null;
    }

    // 선택된 계절 필터 값 (없으면 null로 설정)
    let selectedSeasons = Array.from(document.querySelectorAll('input[name="season"]:checked')).map(cb => cb.value);
    if (selectedSeasons.length === 0) {
        selectedSeasons = null;
    }

    // 필터와 검색어 데이터를 JSON으로 서버에 전달
    const searchData = {
        keyword: keyword,
        regions: selectedRegions,
        categories: selectedCategories,
        facilities: selectedFacilities,
        environments: selectedEnvironments,
        seasons: selectedSeasons
    };
	console.log('필터와 검색어 데이터를 JSON으로 서버에 전달', searchData);

    // 서버로 POST 요청
    fetch('/search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(searchData)
    })
    .then(response => response.json())
    .then(data => {
		// data가 잘 받아왔는지 콘솔에서 확인
		console.log("서버에서 받아온 데이터:", data);
		
        // 결과 처리 (예: 검색 결과 표시)
        displayResults(data);
    })
    .catch(error => console.error('Fetch  Error:', error));	// 에러 메시지 출력
}


function displayResults(data) {
    const placesList = document.getElementById('placesList');
    placesList.innerHTML = '';
	
    if (data.length === 0) {
        placesList.innerHTML = '<li>검색 결과가 없습니다.</li>';
    } else {
        data.forEach(place => {
            const listItem = document.createElement('li');
            listItem.textContent = place.place_name;
            placesList.appendChild(listItem);
			
			// 주소 리스트를 순회하며 마커 생성 및 클러스터러에 추가
			geocoder.addressSearch(place.place_address, function(result, status) {
				if (status === kakao.maps.services.Status.OK) {
					var coords = new kakao.maps.LatLng(result[0].y, result[0].x); // 좌표 생성
					var markerImage = createMarkerImage(place.place_category); // 카테고리 기반 이미지 생성
					var marker = new kakao.maps.Marker({
						position: coords, // 마커 위치 설정
						image: markerImage // 마커 이미지 설정
					});

					// 클러스터러에 마커 추가
					clusterer.addMarker(marker);

					// 인포윈도우 생성
					var infowindow = new kakao.maps.InfoWindow({
						content: '<div style="width:150px;text-align:center;padding:6px 0;">' + item.place_name + '</div>' // 인포윈도우 내용
					});

					// 커스텀 오버레이에 표시할 콘텐츠 동적 생성
					var content = document.createElement('div');
					content.className = 'wrap';
					content.innerHTML =
						'<div class="info">' +
						'    <div class="title">' +
						'        ' + place.place_name +
						'        <div class="close" title="닫기"></div>' + // 오버레이 닫기 버튼
						'    </div>' +
						'    <div class="body">' +
						'        <div class="img">' +
						'            <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70">' +
						'        </div>' +
						'        <div class="desc">' +
						'            <div class="ellipsis">' + place.place_address + '</div>' +
						'            <div class="jibun ellipsis">(우) ' + place.place_postcode + '  (지번) ' + item.place_oldaddr + '</div>' +
						'            <div><a href="' + place.place_bookinglink + '" target="_blank" class="link">홈페이지</a></div>' +
						'        </div>' +
						'    </div>' +
						'</div>';

					var overlay = new kakao.maps.CustomOverlay({
						content: content, // 오버레이에 표시할 콘텐츠
						position: marker.getPosition(), // 오버레이 위치 설정
						map: null  // 처음엔 오버레이 표시하지 않음
					});

					// 오버레이 열림 상태를 관리하기 위한 변수
					var overlayVisible = false;

					// 마커 클릭 시 커스텀 오버레이 표시
					kakao.maps.event.addListener(marker, 'click', function() {
						overlay.setMap(map); // 오버레이 지도에 표시
						overlayVisible = true; // 오버레이 열림 상태 변경
					});

					// 오버레이 닫기 버튼 클릭 이벤트 처리
					var closeBtn = content.querySelector('.close');
					closeBtn.onclick = function() {
						overlay.setMap(null); // 오버레이 지도에서 제거
						overlayVisible = false; // 오버레이 닫힘 상태 변경
					};

					// 마커에 마우스 오버 시 인포윈도우 표시 (오버레이가 열려있지 않을 때만)
					kakao.maps.event.addListener(marker, 'mouseover', function() {
						if (!overlayVisible) {
							infowindow.open(map, marker); // 인포윈도우 표시
						}
					});

					// 마커에서 마우스 아웃 시 인포윈도우 닫기
					kakao.maps.event.addListener(marker, 'mouseout', function() {
						infowindow.close(); // 인포윈도우 닫기
					});

				} else {
					console.error("Geocoder failed: " + status); // 지오코딩 실패 시 오류 출력
				}
				
			});
        });
    }
}




///////////////////////////////////////////////////////////////////////////////////////////////////////////////



