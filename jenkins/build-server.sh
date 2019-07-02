cd ..
cd app/polling-app-server
mvn clean package -DskipTests=true
docker build -t polls-app-server:latest .
docker tag polls-app-server 10.115.8.91:5000/polls-app-server
docker push 10.115.8.91:5000/polls-app-server
