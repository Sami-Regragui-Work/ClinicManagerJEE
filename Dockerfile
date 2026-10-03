FROM tomcat:10-jdk25-temurin

# Copy WAR file to Tomcat
COPY target/clinic-manager.war /usr/local/tomcat/webapps/

# Expose port 8080
EXPOSE 8080

CMD ["catalina.sh", "run"]