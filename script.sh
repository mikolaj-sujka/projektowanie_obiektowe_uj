#!/usr/bin/env bash 

# This script is used to run the tests on the remote server 
LOGIN="sujka_1165433"
PASSWORD="TiFju9495" 
SPK_ADDRESS="149.156.43.64" 

HOST="${LOGIN}@${SPK_ADDRESS}"
FILE_NAME="simulation.zip"

# Create a tarball with the source code
rm -f $FILE_NAME
zip -r $FILE_NAME edu Simulation.java

# Check if sshpass is installed
sshpass_installed=$(which sshpass)
if [ -z "$sshpass_installed" ]; then
    echo "sshpass is not installed"
    sudo apt-get install -y sshpass
fi
export SSHPASS=$PASSWORD

REMOTE_PATH="/home/$LOGIN"

# Removing the old tarball if it exists
sshpass -e ssh $HOST "rm -f ${REMOTE_PATH}/${FILE_NAME}"

# Copy the new tarball to the remote server
sshpass -e scp ./$FILE_NAME $HOST:$REMOTE_PATH

# Run the client.jar with the tarball on the remote server
sshpass -e ssh $HOST "java -jar /scratch/uforamus/client.jar 172.30.24.15 ${REMOTE_PATH}/${FILE_NAME}"