# README for the server app

## Deployment on AWS
- Create an AWS instance:
    - Select `Amazon Linux 2 AMI` in the first step
    - In the second step, select `Next: Configure Instance Details`
    - Click `Next: ...` until you reach `Step 6: Configure Security Group`
    - Add `Custom TCP Rule`, set `Port Range` to `3000` (to be aligned with the Node.js server port we need to have exposed), and set `Source` to `Anywhere` (to be adjusted if additional security mechanisms are added).
    - Launch your instance.
- On the AWS instance, install Docker + Git, add your user to the group `docker` log out after that (`$` indicates your own environment, and `#` indicates the AWS instance's environment):
```bash
$ ssh -i YOUR_PEM_FILE.pem ec2-user@YOUR_AWS_INSTANCE_IP.amazonaws.com
# sudo yum update -y
# sudo yum -y install docker git
# sudo service docker start
# sudo usermod -a -G docker ec2-user
# exit
```
- Now log into your AWS instance again with your new user permissions and start the Node.js server in a custom Docker container specified in `Dockerfile`:
```bash
$ ssh -i YOUR_PEM_FILE.pem ec2-user@YOUR_AWS_INSTANCE_IP.amazonaws.com

# git clone https://github.com/xiumingxu/blockchain.git
# LOCAL_DIR=$PWD/blockchain/server/centralServiceApp
# cd $LOCAL_DIR

# docker build -t server_node .

# CONTAINER_APP_DIR=/usr/src/app
# PORT=3000

# docker run -it --rm -p $PORT:$PORT -v $LOCAL_DIR:$CONTAINER_APP_DIR -w $CONTAINER_APP_DIR server_node app.js
```