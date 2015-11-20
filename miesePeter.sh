echo "gather TLE coverage"

export RegEx='#IF\|#ELSIF\|#ELSE\|#LOOP\|#LOCAL\|#BLOCK\|#WITH\|#INCLUDE\|#WITH_ERROR\|#FUNCTION\|#MENU\|#CALCULATE\|#PROGRESS\|#REM'
startfolder=`pwd`
. /etc/default/epages6
resultFolder="result"
rm -Rf $resultFolder
mkdir -p $resultFolder
outPutFile=$resultFolder/TleReport.txt
currentDate=`date`
echo  "{\"date\": \"$currentDate \" ," > $outPutFile
ls $EPAGES_CARTRIDGES/DE_EPAGES | while read cartridgename
do
	echo "{ \"cartridge\": \"$cartridgename\" ," >> $outPutFile
	echo " \"files\": [" >> $outPutFile
	find $EPAGES_CARTRIDGES/DE_EPAGES/$cartridgename -name "*.html" | while read fileName
	do		
		complexity=`grep -ow $RegEx $fileName | grep -c $RegEx`
		echo "{\"filename\": \"$fileName\", \"complexity\": $complexity }," >> $outPutFile
	done
	echo "]}," >> $outPutFile
done
echo "}" >> $outPutFile
cd $startfolder

