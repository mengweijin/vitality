## MinIO

### docker 安装
官方文档：[https://min.io/docs/minio/container/index.html](https://min.io/docs/minio/container/index.html)

GNU/Linux or MacOS
```bash
mkdir -p ~/minio/data

docker run \
   -p 9000:9000 \
   -p 9090:9090 \
   --name minio \
   -v ~/minio/data:/data \
   -e "MINIO_ROOT_USER=ROOTNAME" \
   -e "MINIO_ROOT_PASSWORD=CHANGEME123" \
   quay.io/minio/minio server /data --console-address ":9090"
```
Windows
```bash
docker run \
   -p 9000:9000 \
   -p 9090:9090 \
   --name minio \
   -v D:\minio\data:/data \
   -e "MINIO_ROOT_USER=ROOTUSER" \
   -e "MINIO_ROOT_PASSWORD=CHANGEME123" \
   quay.io/minio/minio server /data --console-address ":9090"
```
docker 启动完成后，访问：http://localhost:9000  和  http://localhost:9090

不挂在卷：
```bash
docker run \
  -p 9000:9000 \
  -p 9090:9090 \
  --name minio \
  -e "MINIO_ROOT_USER=root" \
  -e "MINIO_ROOT_PASSWORD=minioadmin" \
  quay.io/minio/minio server /data --console-address ":9090"
```
