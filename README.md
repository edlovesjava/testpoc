# testpoc


## frontend

running in dev

run backend first

```
cd frontend
npm install
npm start
```

runs localhost:3000 (connects to localhost:8080)

## production build frontend

```
npm run build
cp -R build ../src/main/resources/static
```



## backend

running back end

```
./gradlew bootRun

http://localhost:8080

```
