tag=$(git describe --always)

./gradlew build -x test

echo "Building am-ims v:"$tag
echo '===> Compiling & Packaging amweb/ims ...'

docker build \
  --quiet \
  -t 218291069433.dkr.ecr.us-east-1.amazonaws.com/amweb:latest \
  .

echo "Done building 218291069433.dkr.ecr.us-east-1.amazonaws.com/amweb:latest"
echo "Done building 218291069433.dkr.ecr.us-east-1.amazonaws.com/amweb:"$tag