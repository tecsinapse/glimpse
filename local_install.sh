mvn clean
mvn install
cd glimpse-cli
mvn assembly:assembly
cd ..
GLIMPSE_HOME="$HOME/.glimpse"
if [ -d $GLIMPSE_HOME ]
then
    rm -rf $GLIMPSE_HOME/lib
    rm $GLIMPSE_HOME/self-install.jar
fi
mkdir "$GLIMPSE_HOME"
cp glimpse-self-install/target/self-install.jar $GLIMPSE_HOME/self-install.jar
tar xvf glimpse-cli/target/glimpse-cli*.tar.gz -C $GLIMPSE_HOME