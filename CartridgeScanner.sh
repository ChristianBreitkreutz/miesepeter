echo "gather TLE coverage"

ls -d */ | cut -f1 -d'/' | while read cartridgename
do
 sonar-runner -D sonar.projectName=DE_EPAGES::$cartridgename -D sonar.projectKey=DE_EPAGES::$cartridgename -D sonar.sources=./$cartridgename
done
