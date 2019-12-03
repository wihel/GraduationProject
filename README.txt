*MSSQL과 연동했으므로 데이터베이스를 import하거나 쿼리를 입력시켜 데이터베이스 테이블을 만든뒤

프로그램을 실행해야한다.

데이터베이스는 DatataBase폴더에 있다.

한 프로젝트 안에 서버프로그램과 클라이언트 프로그램이 같이 들어있다.

처음실행은 /src-pratice-ServerManager.java를 실행한다.

ServerManager.java를 실행하면 메인화면이 켜짐과 함께 서버도 동작한다.

그다음 /src- Client- ClientLoginMain.java를 실행한후 회원가입을 한후 로그인하시면 프로그램 실행이 된다.



---------------------------------세부 파일 정보-----------------------------------------

서버 메인 UI -> pratice패키지에 ServerManager.java

서버키는 클래스는 pratice패키지에 ServerStart.java

여기에 while 무한루프 switch case readUTF 다있음..

(pratice패키지 나머지는 다 쓸모없는거)


클라이언트 로그인 -> Client패키지에 ClientLoginMain.java

클라이언트 로그인 했을 때 로그인화면,클라이언트 서버 커넥션 스레드,  클라이언트 서버로 부터 데이터 
readutf하는 부분 -> Client패키지에 LastSeat.java


Server패키지에 ManagerMain1이 서버켰을 때 같이 실행되는 UI

Server패키지에 ManagerMainMenuView.java가 재고 조회 Table

Server패키지에 ManagerMainUserlist.java가 유저정보 조회 Table

동하형 상품주문 UI 만든거 -> menulist패키지에 다 들어있음


클라이언트 로그인 부
Client패키지랑 Client_DB
