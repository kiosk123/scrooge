 FROM tomcat
 ADD ./build/libs/scrooge.war /usr/local/tomcat/webapps/
 CMD ["catalina.sh", "run"]