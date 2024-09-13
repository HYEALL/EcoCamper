// 지도 컨테이너 및 옵션 설정
var mapContainer = document.getElementById('map'),
	mapOption = {
		center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도 중심 좌표 설정
		level: 3 // 지도 확대 레벨 설정
	};

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 생성

// 지도 타입 컨트롤 추가
var mapTypeControl = new kakao.maps.MapTypeControl();
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 줌 컨트롤 추가
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 주소-좌표 변환 객체 생성
var geocoder = new kakao.maps.services.Geocoder();

// 마커 클러스터러 생성
var clusterer = new kakao.maps.MarkerClusterer({
	map: map,
	averageCenter: true, // 클러스터 중심 좌표 설정
	minLevel: 10 // 클러스터링 최소 레벨 설정
});

// 현위치 마커 및 오류 처리
if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(function(position) {
		var lat = position.coords.latitude; // 위도
		var lon = position.coords.longitude; // 경도
		var locPosition = new kakao.maps.LatLng(lat, lon); // 현위치 좌표 생성

		map.setCenter(locPosition); // 지도 중심을 현위치로 이동

		// 현위치 마커 추가
		var marker = new kakao.maps.Marker({
			map: map,
			position: locPosition,
			image: new kakao.maps.MarkerImage(
				'https://cdn-icons-png.flaticon.com/128/4668/4668400.png',
				new kakao.maps.Size(64, 69),
				{ offset: new kakao.maps.Point(27, 69) }
			)
		});
	}, function() {
		// 위치 정보를 가져오지 못했을 경우
		geocoder.addressSearch('서울 동작구 장승배기로 171', function(result, status) {
			if (status === kakao.maps.services.Status.OK) {
				var defaultPosition = new kakao.maps.LatLng(result[0].y, result[0].x); // 변환된 좌표
				map.setCenter(defaultPosition);

				// 기본 위치 마커 추가
				var marker = new kakao.maps.Marker({
					map: map,
					position: defaultPosition,
					image: new kakao.maps.MarkerImage(
						'https://cdn-icons-png.flaticon.com/128/4668/4668400.png',
						new kakao.maps.Size(64, 69),
						{ offset: new kakao.maps.Point(27, 69) }
					)
				});

				alert("현재 위치를 받아오지 못했습니다. \n KG에듀원 아이티뱅크 노량진학원의 위치가 현위치로 설정되었습니다.");
			}
		});
	});
} else {
	alert("GPS를 사용할 수 없습니다.");
}

// 공간 유형별 마커 이미지 생성 함수
function categoryImage(category) {
	let imageSrc;

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
	return imageSrc;
}

function createMarkerImage(category) {
	let imageSrc = categoryImage(category);
	return new kakao.maps.MarkerImage(imageSrc, new kakao.maps.Size(40, 43), { offset: new kakao.maps.Point(27, 69) });
}

// 검색 & 필터 기능
function searchPlaces() {
    const keyword = document.getElementById('keyword').value || '';

    let regions = Array.from(document.querySelectorAll('input[name="region"]:checked')).map(cb => cb.value);
    if (regions.length === 0) regions = [];

    let categories = Array.from(document.querySelectorAll('input[name="category"]:checked')).map(cb => cb.value); 
    if (categories.length === 0) categories = [];

    let facilities = Array.from(document.querySelectorAll('input[name="facilities"]:checked')).map(cb => cb.value);
    if (facilities.length === 0) facilities = [];

    let environments = Array.from(document.querySelectorAll('input[name="environment"]:checked')).map(cb => cb.value);
    if (environments.length === 0) environments = [];

    let seasons = Array.from(document.querySelectorAll('input[name="season"]:checked')).map(cb => cb.value);
    if (seasons.length === 0) seasons = [];

    const searchData = {
        keyword: keyword,
        regions: regions,
        categories: categories, 
        facilities: facilities,
        environments: environments,
        seasons: seasons
    };
	
	console.log(searchData);

    fetch('/search', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(searchData)
    })
    .then(response => response.json())
    .then(data => {
        displayResults(data);
    })
    .catch(error => console.error('Fetch Error:', error));
}



// 검색 결과 목록과 마커를 표출하는 함수
function displayResults(data) {

	var listEl = document.getElementById('placesList'),
		fragment = document.createDocumentFragment();

	// 검색 결과 목록에 추가된 항목들을 제거
	removeAllChildNods(listEl);

	// 지도에 표시되고 있는 마커를 제거
	removeMarker();

	if (data.length === 0) {
		placesList.innerHTML = '<li>검색 결과가 없습니다.</li>';
	} else {
		data.forEach(place => {
			console.log("Place 데이터:", place);

			// 검색 결과를 리스트로 추가
			const listItem = getListItem(place);
			fragment.appendChild(listItem);

			// 마커 생성
			geocoder.addressSearch(place.place_address, function(result, status) {
				if (status === kakao.maps.services.Status.OK) {
					var coords = new kakao.maps.LatLng(result[0].y, result[0].x); // 좌표 생성
					var markerImage = createMarkerImage(place.place_category); // 카테고리 기반 이미지 생성
					var marker = new kakao.maps.Marker({
						position: coords, // 마커 위치 설정
						image: markerImage // 마커 이미지 설정
					});

					// 클러스터에 마커 추가
					clusterer.addMarker(marker);

					// 인포윈도우 생성
					var infowindow = new kakao.maps.InfoWindow({
						content: '<div style="width:150px;text-align:center;padding:6px 0;">' + place.place_name + '</div>'
					});

					// 마커에 마우스 오버 시 인포윈도우 표시
					kakao.maps.event.addListener(marker, 'mouseover', function() {
						infowindow.open(map, marker);
					});

					// 마커에서 마우스 아웃 시 인포윈도우 닫기
					kakao.maps.event.addListener(marker, 'mouseout', function() {
						infowindow.close();
					});
				}
			});
		});
	}
	listEl.appendChild(fragment); // 리스트 항목 추가
}

// 검색결과 항목을 Element로 반환하는 함수
function getListItem(place) {
    var el = document.createElement('li'),
        itemStr = '<article>' +
            '<div class="title-with-icon">' +  // div로 묶어서 한 줄에 배치
            '<img src="' + categoryImage(place.place_category) + '" alt="마커이미지">' +
            '<h5>' + place.place_name + '</h5>' +
            '</div>';
    
    // 별 점수를 표시할 div 추가
    itemStr += '<div class="editor-score">';
    
    if (place.place_editorscore) {
        // 점수를 반올림하여 1~5 사이의 자연수로 처리
        let roundedScore = Math.round(place.place_editorscore);
        
        // 점수가 1 미만인 경우 1로, 5를 초과하는 경우 5로 제한
        if (roundedScore < 1) roundedScore = 1;
        if (roundedScore > 5) roundedScore = 5;
        
        // 꽉 찬 별 이미지를 반올림된 점수만큼 출력
        for (let i = 0; i < roundedScore; i++) {
            itemStr += '<img src="https://cdn-icons-png.flaticon.com/128/1055/1055473.png" alt="별" class="star">';
        }
		itemStr += '   ' + place.place_editorscore;
    }
    
    itemStr += '</div>';  // editor-score 닫기
    
    // 이미지 처리
    if (place.place_pic) {
        itemStr += '<div class="pic">' +
                    '<img src="' + place.place_pic + '" alt="이미지" class="main_pic">' +
                    '</div>';
    } else {    // 이미지가 없는 경우 default 이미지 출력
        itemStr += '<div class="pic">' +
                    '<img src="https://cdn-icons-png.flaticon.com/128/8863/8863763.png" alt="이미지" class="main_pic">' +
                    '</div>';
    }
    
	if(place.place_address) {
		itemStr += '<span>' + place.place_address + '</span>';
	}
	
	if(place.place_oldaddr && place.place_postcode) {
		itemStr += '<span class="jibun">지번: ' + place.place_oldaddr + '  (우) ' + place.place_postcode + '</span>';
	} else if(place.place_oldaddr) {
		itemStr += '<span class="jibun">지번: ' + place.place_oldaddr + '</span>';
	} else {
		itemStr += '<span class="jibun">(우) ' + place.place_postcode + '</span>';
	}
		
	if(place.place_tel) {
		itemStr += '<span>전화번호: ' + place.place_tel + '</span>';
	}
    
    // 상세보기 바로가기 추가
    itemStr += '<span><a href="">상세보기</a></span>'
				'</article>';
				
    el.innerHTML = itemStr;
    el.className = 'item';
    return el;
}

// 마커와 클러스터 초기화 함수
function removeAllChildNods(el) {
	while (el.hasChildNodes()) {
		el.removeChild(el.lastChild);
	}
}

function removeMarker() {
	clusterer.clear(); // 클러스터러에 있는 마커 제거
}
