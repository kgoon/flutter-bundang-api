# flutter-bundang-api
플러터 스터디 과제 구현에 사용될 백엔드 API


## 로컬 개발서버
### DataStore 에뮬레이터 시작

[App Engine 프로젝트하위 데이터 폴더 지정](https://cloud.google.com/datastore/docs/tools/datastore-emulator?hl=ko#emulator_data_directories)
````
gcloud beta emulators datastore start --data-dir=target/api-1.0-SNAPSHOT
````

데이터스토어 에뮬레이터 접속정보 시스템 환경변수 지정
````
export DATASTORE_EMULATOR_HOST=localhost:8081
````
