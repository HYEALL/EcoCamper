create table usertable(
    name varchar2(30) not null,
    id varchar2(30) primary key,
    pwd varchar2(30) not null,
    age date not null,
    gender varchar2(30) not null,
    email varchar2(30) not null,
    tel varchar2(30) not null,
    addr varchar2(100) not null,
    logtime date not null,
    role varchar2(20) not null
);
select * from usertable;
delete usertable where id='hyeall';
update usertable set role='ADMIN' where id='admin';
alter table usertable add unique(email);
-- 주소 바꾸기
alter table usertable add zipcode varchar2(7); -- 우편번호
alter table usertable rename column addr to addr1; --- 도로명 주소 
alter table usertable add addr2 varchar2(200); -- 상세 주소
-- abcdefg9876 gil
commit;
desc usertable;
drop table usertable purge;
-----------------------------
create table Shop_Review(
    shopreviewseq number not null,
    shopreviewpcode varchar2(30) not null,  --외래키설정
    shopreviewid varchar2(30) not null, --외래키설정
    shopreviewcontent varchar2(4000) not null,
    shopreviewhit number default 0,
    logtime date default sysdate,
    
    foreign key (shopreviewid) references  usertable(id),
    foreign key (shopreviewpcode) references  Shop(pcode)    
);
create SEQUENCE seq_Shop_Review NOCACHE NOCYCLE;
drop SEQUENCE seq_Shop_Review;
select *from user_sequences;

drop table Shop_Review purge;
select*from Shop_Review;
insert into Shop_Review values (seq_Shop_Review.nextval,'0001','euneun','친환경휴지',0,sysdate);
delete Shop_Review where shopreviewid='hong';

select shopreviewseq, shopreviewid, shopreviewcontent, shopreviewhit, 
            to_char(logtime,'YYYY.MM.DD')as logtime from 
             (select rownum rn, tt.*from
             (select * from ShopReview order by logtime desc) tt)
              where rn >=1 and rn <=5;

commit;
---------------------------------------------------
create table Buylist(
    buyseq number not null,  
    buyid varchar2(50) not null, --외래키설정
   -- productname  varchar2(50) not null, -- pcode로 받아와서 넣어라
    productcode varchar2(50) not null, --외래키설정
    productqty  number,
    productprice  number,
   -- buyername   varchar2(50) not null, -- id 로  받아와서 넣어라?
    receivename varchar2(50) not null,
    baddress varchar2(50) not null,
    bphone  varchar2(50) not null,
    bpayment varchar2(50) not null,
    bcancel varchar2(50) default 'N',
    logtime date default sysdate,
    
    foreign key (buyid) references  usertable(id),
    foreign key (productcode) references  Shop(pcode)
);
-- 주소 바꾸기
alter table buylist add bzipcode varchar2(7); -- 우편번호
alter table buylist rename column baddress to baddr1; --- 도로명 주소 
alter table buylist add baddr2 varchar2(200); -- 상세 주소


drop table Buylist purge;

insert into Buylist values(seq_Buylist.nextval,'euneun','0002',1,5000,'곽은성', 
                            '서울','010','신용',sysdate);
insert into Buylist values(seq_Buylist.nextval,'euneun','0001',1,10000,'곽은성', '서울','010','신용',sysdate);
select*from Buylist;

delete Buylist where buyseq='12';

create SEQUENCE seq_Buylist NOCACHE NOCYCLE;
drop SEQUENCE seq_Buylist;
select *from user_sequences;

commit;

----------------------------------------------------
create table Shop(
    
    pcode varchar2(30) primary key,
    pname  varchar2(50) not null,
    ptype varchar2(50),
    pprice number,
    pqty number,                --재고수량
    phit number default 0,      --판매수량
    pimg varchar2(200),                   -- 파일명
    logtime date default sysdate            -- 작성일
);

drop table Shop purge;

insert into Shop values ('0001','친환경세제','자사',3000,2000,0,'cleaner.jpg',sysdate);
insert into Shop values ('0002','친환경휴지','자사',10000,2000,0,'roll.jpg',sysdate);
insert into Shop values ('0003','물아이스팩','자사',4000,5000,0,'water.jpg',sysdate);
select*from Shop;
select * from Shop where pcode='0001' and pname='친환경세제';
select * from Shop where pcode='0001';
delete Shop where productcode='0001';

commit;


-----------------------------
-- 테이블 생성

drop table map;
create SEQUENCE place_seq NOCACHE NOCYCLE;
CREATE TABLE map (
    place_seq NUMBER PRIMARY KEY,                     -- 장소 고유번호, key값
    place_category VARCHAR2(100),                     -- 장소 카테고리
    place_address VARCHAR2(255),                      -- 주소
    place_name VARCHAR2(255),                         -- 장소명
    place_postcode VARCHAR2(20),                      -- 우편번호
    place_oldaddr VARCHAR2(255),                      -- 지번
    place_pic VARCHAR2(4000),                         -- 장소 사진 여러장 (콤마로 구분된 문자열로 저장)
    place_description VARCHAR2(1000),                 -- 장소 설명
    place_keypoint VARCHAR2(500),                     -- 주요사항
    place_precaution VARCHAR2(1000),                  -- 투숙객 주의사항
    place_bookingLink VARCHAR2(1000),                 -- 예약링크 (해당 장소 홈페이지 링크)
    place_tel VARCHAR2(20),                           -- 업체 전화번호
    place_editorScore NUMBER(3,2),                    -- 에디터 평점
    place_cleanScore NUMBER(3,2),                     -- 청결도
    place_sceneScore NUMBER(3,2),                     -- 경치
    place_independenceScore NUMBER(3,2),              -- 사이트 독립성
    place_facilityScore NUMBER(3,2),                  -- 시설구비
    place_facility VARCHAR2(1000),                  -- 시설 (콤마로 구분된 문자열로 저장)
    place_environment VARCHAR2(1000),                 -- 주변환경 (콤마로 구분된 문자열로 저장)
    place_season VARCHAR2(1000),                      -- 계절 (콤마로 구분된 문자열로 저장)
    place_youtubeLink VARCHAR2(1000),                       -- 유튜브 링크
    place_youtubeTitle VARCHAR2(255),                 -- 유튜브 제목
    place_youtubeVideo VARCHAR2(1000)                 -- 유튜브 썸네일
);

-- 전체 데이터 검색
SELECT * FROM map;
-- 특정 place_seq에 대한 검색
SELECT * FROM map WHERE place_seq = 'pl_001';
-- 특정 place_category에 대한 검색
SELECT * FROM map WHERE place_category = '캠핑장';

INSERT INTO map (
    place_seq, place_category, place_address, place_name, place_postcode, 
    place_oldaddr, place_pic, place_description, place_keypoint, place_precaution, 
    place_bookingLink, place_tel, place_editorScore, place_cleanScore, 
    place_sceneScore, place_independenceScore, place_facilityScore, 
    place_facility, place_environment, place_season, place_youtubeLink, 
    place_youtubeTitle, place_youtubeVideo
) VALUES (
    place_seq.nextval,                 -- place_seq
    'place_c01',                        -- place_category
    '서울 마포구 한강난지로 22 한강시민공원', -- place_address
    '한강난지캠핑장',                 -- place_name
    '03900',                        -- place_postcode
    '상암동 495-81',                -- place_oldaddr
    NULL,                           -- place_pic (이미지 URL을 저장할 경우 삽입)
    NULL,                           -- place_description (추가 설명이 있는 경우 삽입)
    '안녕하세요! ?\n오늘도 좋은 하루 보내세요.\n행복한 일만 가득하시길 바랍니다! ?\n화이팅! ?',  -- place_keypoint (주요사항이 있는 경우 삽입)
    '오늘의 날씨는 정말 맑아요! ??\n산책 가기 딱 좋은 날씨네요. ??♂?\n모두들 즐거운 하루 되세요! ?',       -- place_precaution
    'https://yeyak.seoul.go.kr',   -- place_bookingLink
    '02-373-2021',                 -- place_tel
    4.25,                          -- place_editorScore
    5.0,                           -- place_cleanScore
    3.5,                           -- place_sceneScore
    4.0,                           -- place_independenceScore
    4.5,                           -- place_facilityScore
    'place_f05, place_f06, place_f07', -- place_facilities
    'place_e01, place_e03', -- place_environment
    'place_s01, place_s03', -- place_season
    NULL,                          -- youtubeLink
    NULL,                          -- place_youtubeTitle
    NULL                           -- place_youtubeVideo
);

INSERT INTO map (
    place_seq, place_category, place_address, place_name, place_postcode, 
    place_oldaddr, place_pic, place_description, place_keypoint, place_precaution, 
    place_bookingLink, place_tel, place_editorScore, place_cleanScore, 
    place_sceneScore, place_independenceScore, place_facilityScore, 
    place_facility, place_environment, place_season, place_youtubeLink, 
    place_youtubeTitle, place_youtubeVideo
) VALUES (
    place_seq.nextval,                      -- place_seq
    'place_c01',                    -- place_category (캠핑장)
    '경기 과천시 막계동 산 59-2',  -- place_address
    '서울대공원 캠핑장',            -- place_name
    '13829',                        -- place_postcode
    NULL,                           -- place_oldaddr
    NULL,                           -- place_pic
    NULL,                           -- place_description
    '와우! 🎉 드디어 주말이에요!\n무엇을 할 계획인가요? 🎮\n즐거운 시간 보내세요! 😄',                           -- place_keypoint
    '좋은 아침입니다! 🌅\n오늘도 멋진 하루 되세요.\n여러분의 꿈을 응원합니다! 🌟',       -- place_precaution
    'http://www.seoulcamp.co.kr',  -- place_bookingLink
    '02-502-3836',                 -- place_tel
    4.5,                           -- place_editorScore
    4.8,                           -- place_cleanScore
    4.0,                           -- place_sceneScore
    4.3,                           -- place_independenceScore
    4.7,                           -- place_facilityScore
    'place_f01, place_f02, place_f03', -- place_facilities (화장실, 샤워실, 매점)
    'place_e04, place_e06',          -- place_environment (산, 숲, 공원, 유원지)
    'place_s01, place_s02',          -- place_season (봄, 여름)
    NULL,                          -- youtubeLink
    NULL,                          -- place_youtubeTitle
    NULL                           -- place_youtubeVideo
);

INSERT INTO map (
    place_seq, place_category, place_address, place_name, place_postcode, 
    place_oldaddr, place_pic, place_description, place_keypoint, place_precaution, 
    place_bookingLink, place_tel, place_editorScore, place_cleanScore, 
    place_sceneScore, place_independenceScore, place_facilityScore, 
    place_facility, place_environment, place_season, place_youtubeLink, 
    place_youtubeTitle, place_youtubeVideo
) VALUES (
    place_seq.nextval,                      -- place_seq
    'place_c02',                    -- place_category (차박, 노지)
    '인천 남동구 인주대로 624',    -- place_address
    '오렘지차박캠핑',               -- place_name
    '21571',                       -- place_postcode
    '구월동 201-32',               -- place_oldaddr
    NULL,                          -- place_pic
    NULL,                          -- place_description
    '안녕하신가요? 🙌\n모두 건강히 잘 지내고 계시길 바랍니다.\n사랑과 행복이 가득한 하루 되세요! 💖

',                          -- place_keypoint
    '오늘도 화이팅입니다! 💥\n모두 함께 힘내봅시다! 🏋️‍♀️\n성공적인 하루 보내세요! 💼',       -- place_precaution
    NULL,                          -- place_bookingLink
    '010-3999-5847',               -- place_tel
    4.0,                           -- place_editorScore
    4.2,                           -- place_cleanScore
    3.8,                           -- place_sceneScore
    3.9,                           -- place_independenceScore
    4.1,                           -- place_facilityScore
    'place_f04, place_f06, place_f17', -- place_facilities (낚시, 전기사용, 주차)
    'place_e02, place_e04',          -- place_environment (호수, 강, 산, 숲)
    'place_s02, place_s04',          -- place_season (여름, 겨울)
    NULL,                          -- youtubeLink
    NULL,                          -- place_youtubeTitle
    NULL                           -- place_youtubeVideo
);

INSERT INTO map (
    place_seq, place_category, place_address, place_name, place_postcode, 
    place_oldaddr, place_pic, place_description, place_keypoint, place_precaution, 
    place_bookingLink, place_tel, place_editorScore, place_cleanScore, 
    place_sceneScore, place_independenceScore, place_facilityScore, 
    place_facility, place_environment, place_season, place_youtubeLink, 
    place_youtubeTitle, place_youtubeVideo
) VALUES (
    place_seq.nextval,                      -- place_seq
    'place_c03',                    -- place_category (글램핑, 카라반)
    '서울 서초구 청계산로 140-94', -- place_address
    '청계산장글램핑',              -- place_name
    '06805',                       -- place_postcode
    '원지동 207-1',                -- place_oldaddr
    NULL,                          -- place_pic
    '드디어 봄이 왔어요! 🌷\n꽃들이 활짝 피고 있어요. 🌻\n모두들 꽃 구경 다녀오세요! 🌸',                          -- place_description
    '지금은 휴식 시간이네요. 🛋️\n따뜻한 차 한 잔 어떠세요? ☕\n잠시 쉬어가는 것도 중요해요. 😌',            -- place_keypoint
    '입실 14시 / 퇴실 11시',       -- place_precaution
    'https://www.instagram.com/azurevalley_', -- place_bookingLink
    '0507-1387-3699',              -- place_tel
    4.3,                           -- place_editorScore
    4.5,                           -- place_cleanScore
    4.2,                           -- place_sceneScore
    4.4,                           -- place_independenceScore
    4.6,                           -- place_facilityScore
    'place_f01, place_f02, place_f08', -- place_facilities (화장실, 샤워실, 수영장)
    'place_e40, place_e05',          -- place_environment (산, 숲, 섬)
    'place_s03, place_s04',          -- place_season (가을, 겨울)
    NULL,                          -- youtubeLink
    NULL,                          -- place_youtubeTitle
    NULL                           -- place_youtubeVideo
);

INSERT INTO map (
    place_seq, place_category, place_address, place_name, place_postcode, 
    place_oldaddr, place_pic, place_description, place_keypoint, place_precaution, 
    place_bookingLink, place_tel, place_editorScore, place_cleanScore, 
    place_sceneScore, place_independenceScore, place_facilityScore, 
    place_facility, place_environment, place_season, place_youtubeLink, 
    place_youtubeTitle, place_youtubeVideo
) VALUES (
    place_seq.nextval,                      -- place_seq
    'place_c05',                    -- place_category (낚시스팟)
    '서울 용산구 용산동6가 450',    -- place_address
    '한강시민공원이촌지구낚시터',   -- place_name
    '04376',                       -- place_postcode
    NULL,                          -- place_oldaddr
    NULL,                          -- place_pic
    NULL,                          -- place_description
    '새로운 도전을 시작해보세요! 🛫\n두려워하지 말고, 도전하세요! 💪\n응원합니다! 🎯',                          -- place_keypoint
    '오늘도 고생 많으셨어요! 😌\n편안한 밤 보내세요. 🌙\n내일도 힘내서 파이팅! 💪',                          -- place_precaution
    NULL,                          -- place_bookingLink
    NULL,                          -- place_tel
    3.8,                           -- place_editorScore
    3.9,                           -- place_cleanScore
    4.0,                           -- place_sceneScore
    3.7,                           -- place_independenceScore
    4.1,                           -- place_facilityScore
    'place_f04, place_f10',         -- place_facilities (낚시, 체험 프로그램)
    'place_e01, place_e03',          -- place_environment (바다, 계곡)
    'place_s01, place_s02',          -- place_season (봄, 여름)
    NULL,                          -- youtubeLink
    NULL,                          -- place_youtubeTitle
    NULL                           -- place_youtubeVideo
);

INSERT INTO map (
    place_seq, place_category, place_address, place_name, place_postcode, 
    place_oldaddr, place_pic, place_description, place_keypoint, place_precaution, 
    place_bookingLink, place_tel, place_editorScore, place_cleanScore, 
    place_sceneScore, place_independenceScore, place_facilityScore, 
    place_facility, place_environment, place_season, place_youtubeLink, 
    place_youtubeTitle, place_youtubeVideo
) VALUES (
    place_seq.nextval,                      -- place_seq
    'place_c08',                    -- place_category (워터스포츠)
    '경기 가평군 가평읍 북한강변로 814', -- place_address
    '가평빠지',                    -- place_name
    '12427',                       -- place_postcode
    '가평읍 이화리 43-4',           -- place_oldaddr
    NULL,                          -- place_pic
    NULL,                          -- place_description
    '모두들 즐거운 저녁시간 되세요! 🍽️\n맛있는 저녁 식사하시고요. 🍲\n행복한 시간 보내세요! 😊',                          -- place_keypoint
    '일주일이 벌써 끝났네요! 📅\n주말엔 푹 쉬세요. 🛌\n모두들 좋은 주말 되시길! 🌈',                          -- place_precaution
    'https://redskis.modoo.at',    -- place_bookingLink
    '010-6228-1328',               -- place_tel
    4.7,                           -- place_editorScore
    4.9,                           -- place_cleanScore
    4.8,                           -- place_sceneScore
    4.5,                           -- place_independenceScore
    4.8,                           -- place_facilityScore
    'place_f09, place_f11, place_f12', -- place_facilities (뷰맛집, 놀이시설, 액티비티)
    'place_e02, place_e04',          -- place_environment (호수, 강, 산, 숲)
    'place_s02, place_s03',          -- place_season (여름, 가을)
    NULL,                          -- youtubeLink
    NULL,                          -- place_youtubeTitle
    NULL                           -- place_youtubeVideo
);

INSERT INTO map (
    place_seq, place_category, place_address, place_name, place_postcode, 
    place_oldaddr, place_pic, place_description, place_keypoint, place_precaution, 
    place_bookingLink, place_tel, place_editorScore, place_cleanScore, 
    place_sceneScore, place_independenceScore, place_facilityScore, 
    place_facility, place_environment, place_season, place_youtubeLink, 
    place_youtubeTitle, place_youtubeVideo
) VALUES (
    place_seq.nextval,                      -- place_seq
    'place_c07',                    -- place_category (액티비티)
    '서울 동대문구 왕산로22길 69',  -- place_address
    '항공시대',                    -- place_name
    '02584',                       -- place_postcode
    '용두동 118-12',               -- place_oldaddr
    NULL,                          -- place_pic
    NULL,                          -- place_description
    '오늘은 특별한 날이에요! 🎂\n모두 함께 축하해요! 🎈\n행복 가득한 하루 되세요! 🎉',           -- place_keypoint
    '사전 예약 필수',              -- place_precaution
    'http://www.paragliding.co.kr', -- place_bookingLink
    '02-929-9296',                 -- place_tel
    4.4,                           -- place_editorScore
    4.5,                           -- place_cleanScore
    4.3,                           -- place_sceneScore
    4.2,                           -- place_independenceScore
    4.6,                           -- place_facilityScore
    'place_f10, place_f12, place_f16', -- place_facilities (체험 프로그램, 액티비티, 에어컨)
    'place_e04, place_e07',          -- place_environment (산, 숲, 도심)
    'place_s01, place_s02, place_s03', -- place_season (봄, 여름, 가을)
    NULL,                          -- youtubeLink
    NULL,                          -- place_youtubeTitle
    NULL                           -- place_youtubeVideo
);

INSERT INTO map (
    place_seq, place_category, place_address, place_name, place_postcode, 
    place_oldaddr, place_pic, place_description, place_keypoint, place_precaution, 
    place_bookingLink, place_tel, place_editorScore, place_cleanScore, 
    place_sceneScore, place_independenceScore, place_facilityScore, 
    place_facility, place_environment, place_season, place_youtubeLink, 
    place_youtubeTitle, place_youtubeVideo
) VALUES (
    place_seq.nextval,                      -- place_seq
    'place_c04',                    -- place_category (백패킹, 하이킹)
    '서울 종로구 옥인동 산 3-39',   -- place_address
    '인왕산',                      -- place_name
    '03049',                       -- place_postcode
    NULL,                          -- place_oldaddr
    NULL,                          -- place_pic
    NULL,                          -- place_description
    '좋은 아침입니다! 🌞\n오늘도 밝게 웃으며 시작해봐요. 😊\n화이팅입니다! 💪',                 -- place_keypoint
    NULL,                 -- place_precaution
    NULL,                          -- place_bookingLink
    NULL,                          -- place_tel
    4.2,                           -- place_editorScore
    4.3,                           -- place_cleanScore
    4.8,                           -- place_sceneScore
    4.1,                           -- place_independenceScore
    4.0,                           -- place_facilityScore
    'place_f09, place_f17',         -- place_facilities (뷰맛집, 주차)
    'place_e04, place_e06',          -- place_environment (산, 숲, 공원, 유원지)
    'place_s03, place_s04',          -- place_season (가을, 겨울)
    NULL,                          -- youtubeLink
    NULL,                          -- place_youtubeTitle
    NULL                           -- place_youtubeVideo
);

INSERT INTO map (
    place_seq, place_category, place_address, place_name, place_postcode, 
    place_oldaddr, place_pic, place_description, place_keypoint, place_precaution, 
    place_bookingLink, place_tel, place_editorScore, place_cleanScore, 
    place_sceneScore, place_independenceScore, place_facilityScore, 
    place_facility, place_environment, place_season, place_youtubeLink, 
    place_youtubeTitle, place_youtubeVideo
) VALUES (
    place_seq.nextval,                      -- place_seq
    'place_c09',                    -- place_category (스토어)
    '서울 강서구 양천로24길 56',   -- place_address
    '차박스페이스',                -- place_name
    '07604',                       -- place_postcode
    '방화동 260-9',                -- place_oldaddr
    NULL,                          -- place_pic
    NULL,                          -- place_description
    NULL,                          -- place_keypoint
    NULL,                   -- place_precaution
    'https://m.smartstore.naver.com/chabakspace', -- place_bookingLink
    '010-3021-0371',               -- place_tel
    4.1,                           -- place_editorScore
    4.3,                           -- place_cleanScore
    3.9,                           -- place_sceneScore
    4.2,                           -- place_independenceScore
    4.4,                           -- place_facilityScore
    'place_f01, place_f03, place_f17', -- place_facilities (화장실, 매점, 주차)
    'place_e07, place_e08',          -- place_environment (도심, 농촌)
    'place_s02, place_s04',          -- place_season (여름, 겨울)
    NULL,                          -- youtubeLink
    NULL,                          -- place_youtubeTitle
    NULL                           -- place_youtubeVideo
);
commit;
-------------------------------

--------------------
--테이블생성
CREATE TABLE search (
    id NUMBER PRIMARY KEY,
    keyword VARCHAR2(100), 
    regions VARCHAR2(200), 
    categories VARCHAR2(200), 
    facilities VARCHAR2(200), 
    environments VARCHAR2(200), 
    seasons VARCHAR2(200)
);
-------------------------------

select * from tab;
commit;
-------------------------------
-- 테이블 생성
create table likes (
    likes_num number primary key, --  좋아요 누른넘버
    review_id number not null, -- 글 번호
    user_id varchar2(30)not null,
    foreign key (review_id) references feed(seq),
    foreign key (user_id) references  usertable(id)
);
insert into likes values(2, 4, 'eun');
select count review_id from likes where review_id = 1;
select * from likes;
   -- 시퀀스 객체 생성
create sequence likes_num nocycle nocache;
-- 시퀀스 삭제
drop sequence likes_num;
delete likes where review_id=5;
commit;
-- 테이블 생성
create table feed (
    seq number primary key,
    id varchar2(30)not null,
    outdoor varchar2(255) not null,
    feed_subject varchar2(255) not null, -- 글 제목
    feed_content varchar2(4000) not null, -- 글 내용
    feed_tag varchar2(4000) , --
    feed_file VARCHAR2(4000) , -- 첨부파일
    feed_type VARCHAR2(100),
    goods varchar2(100),
    place varchar2(100),
    good_num number default 0,
    good number default 0,
    hit number default 0, -- 조회수
   logtime date default sysdate -- 작성일
  
);
select * from feed;
-- 시퀀스 객체 생성
create sequence seq nocycle nocache;
-- 시퀀스 삭제
drop sequence seq;
commit;
drop table feed purge;
---------------------
create table save (

    save_num number primary key, --  저장한 누른넘버
    save_seq number not null, -- 글 번호
    save_id varchar2(30)not null, -- 유저 아이디
    foreign key (save_seq) references feed(seq) ON DELETE CASCADE,
    foreign key (save_id) references  usertable(id)
);

create sequence save_num nocycle nocache;
-- 시퀀스 삭제
drop sequence save_num;

drop table save purge;

select * from save; 
commit;


