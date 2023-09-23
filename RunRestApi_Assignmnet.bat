set "location= C:\Users\abhishekkumar18\Desktop\Assignment\RestAssuredAssignment"
call mvn -f %location%/pom.xml dependency:resolve
call mvn -f %location%/pom.xml compile
call mvn -f %location%/pom.xml test -DtestSuite=testng
pause