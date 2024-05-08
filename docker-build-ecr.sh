tag=$(git describe --always)

echo "Building am-ims v:"$tag
echo '===> Compiling & Packaging amweb/ims ...'

docker build \
  --q \
  -t 218291069433.dkr.ecr.us-east-1.amazonaws.com/amweb:latest \
  .

echo "Done building amweb/ims:"$tag