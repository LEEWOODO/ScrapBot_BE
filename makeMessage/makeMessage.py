#!/usr/bin/env python
# coding: utf-8
import psycopg2
import json
#key로는 각 신문사의 이름을 담으며, vlaue로는 각 신문기사의 정보를 담는 맵을 만들어야 한다.
itemMap={}
NEWS_NAMES = ["조선일보","중앙일보","동아일보","매일경제","한국경제","서울경제","머니투데이"]
class NewsArray:
    def __init__(self):
        self.__newsCompanyNodearray=[];
        #신문사들을 등록합니다.
        for NewsCompanyName in NEWS_NAMES:
            self.enrollNewsCompanyNode(NewsCompanyName);
            newsCompanyNode = self.getNode(NewsCompanyName);
            newsCompanyNode.add("0",False,"입력된 키워드가 포함된 기사 없음");
    def add(self,inputCompanyName,inputPageNumber,inputIsTop,inputTitle):
        #기존의 신문사가 등록되어있는지 확인
        if(self.isThereCompanyNameAlreayExist(inputCompanyName)==False):
            self.enrollNewsCompanyNode(inputCompanyName);#없으면 등록함
        #해당 신문사 노드 가져오기
        newsCompanyNode = self.getNode(inputCompanyName);
        #기존 "입력된 키워드가 포함된 기사 없음"을 지우기
        newsCompanyNode.eraseEmtpyNode();
        #기존의 신문에 해당 페이지등록
        newsCompanyNode.add(inputPageNumber,inputIsTop,inputTitle);
    def getNode(self,inputCompanyName):
        for count in range(0,len(self.__newsCompanyNodearray)):
            newsCompanyNode=self.__newsCompanyNodearray[count]
            if(inputCompanyName==newsCompanyNode.getNewsCompanyName()):
                return newsCompanyNode;
    def enrollNewsCompanyNode(self,inputCompanyName):
        self.__newsCompanyNodearray.append(NewsCompanyNode(inputCompanyName));
    #가지고있던 배열의 내용 중 넣으려는 이름이 있는지 확인하여 있으면 True 없으면 False를 반환
    def isThereCompanyNameAlreayExist(self,inputCompanyName):
        result = False;
        for newsCompanyNode in self.__newsCompanyNodearray:
            if(newsCompanyNode.getNewsCompanyName() == inputCompanyName):
                result= True;
        return result;
    def isEqual(self,CompanyName,inputCompanyName):
        result = CompanyName==inputCompanyName;
        return result;
    def printContents(self):
        string=""
        for CompanyNode in self.__newsCompanyNodearray:
            string=string+"\n▶"+CompanyNode.getNewsCompanyName()+"\n";
            string=string+CompanyNode.printContents(string);
        return string;
class NewsCompanyNode:
    def __init__(self,newsCompanyName):
        self.newsCompanyName=newsCompanyName;
        self.__pageArray=[]
    def getNewsCompanyName(self):
        return self.newsCompanyName;
    def add(self,inputPageNumber,inputIsTop,inputTitle):
        #기존의 페이지가 등록되어있는지 확인
        if(self.isTherePageAlreayExist(inputPageNumber)==False):
            self.enrollPageNode(inputPageNumber);#없으면 등록함
        #해당 페이지 노드 가져오기
        PageNode = self.getPage(inputPageNumber);
        #해당 페이지 노드에 제목과 탑여부를 등록
        PageNode.add(inputIsTop,inputTitle);
    def getPage(self,inputPageNumber):
        for count in range(0,len(self.__pageArray)):
            pageNode=self.__pageArray[count];
            if(inputPageNumber==pageNode.getPageNumber()):
                return pageNode;
    def enrollPageNode(self,inputPageNumber):
        self.__pageArray.append(PageNode(inputPageNumber));
        self.__pageArray.sort();
    def eraseEmtpyNode(self):
        for pageNode in self.__pageArray:
            if pageNode.pageNumber == "0":
                self.__pageArray.remove(pageNode);
    #가지고있던 배열의 내용 중 넣으려는 이름이 있는지 확인하여 있으면 True 없으면 False를 반환
    def isTherePageAlreayExist(self,inputPageNumber):
        result = False;
        for PageNode in self.__pageArray:
            if(PageNode.getPageNumber() == inputPageNumber):
                result= True;
        return result;
    def printContents(self,string):
        text=""
        for pageNode in self.__pageArray:
            text=text+pageNode.printContents(string);
        return text;
class PageNode:
    def __init__(self,pageNumber):
        self.pageNumber = pageNumber;
        self.__TitleNodeArray=[];
    def __lt__(self, other):
        #print(self.pageNumber+" vs "+other.pageNumber)
        if self.pageNumber[0]=="A" or self.pageNumber[0]=="B" or self.pageNumber[0]=="C" or self.pageNumber[0]=="D" or self.pageNumber[0]=="E" or self.pageNumber[0]=="F" or self.pageNumber[0]=="G" or self.pageNumber[0]=="H" or self.pageNumber[0]=="I" or self.pageNumber[0]=="J"  or self.pageNumber[0]=="K" or self.pageNumber[0]=="L" or self.pageNumber[0]=="M" or self.pageNumber[0]=="N" or self.pageNumber[0]=="O" or self.pageNumber[0]=="P" or self.pageNumber[0]=="Q" or self.pageNumber[0]=="R" or self.pageNumber[0]=="S"  or self.pageNumber[0]=="T" or self.pageNumber[0]=="U" or self.pageNumber[0]=="V" or self.pageNumber[0]=="W" or self.pageNumber[0]=="X" or self.pageNumber[0]=="Y" or self.pageNumber[0]=="Z": 
            #문자가 들어가있을 경우
            if(self.pageNumber[0])<(other.pageNumber[0]):
                return True;
            elif (self.pageNumber[0])>(other.pageNumber[0]):
                return False;
            elif (self.pageNumber[0])==(other.pageNumber[0]):
                #print(self.pageNumber[1:]);
                return int(self.pageNumber[1:]) < int(other.pageNumber[1:]);
        else :
            #숫자만 들어있을 경우
            #print("number vs number");
            return int(self.pageNumber) < int(other.pageNumber)
    def getPageNumber(self):
        return self.pageNumber;
    def add(self,inputIsTop,inputTitle):
        #기존의 기사가 등록되어있는지 확인
         if(self.isThereTitleAlreayExist(inputTitle)==False):
            self.enrollTitleNode(inputTitle,inputIsTop);#없으면 등록함
        #해당 기사 가져오기
    def isThereTitleAlreayExist(self,inputTitle):
        result = False;
        for titleNode in self.__TitleNodeArray:
            if(titleNode.getTitle() == inputTitle):
                result= True;
        return result;
    def enrollTitleNode(self,inputTitle,inputIsTop):
        if(inputIsTop==True):
            self.__TitleNodeArray.insert(0,TitleNode(inputTitle,inputIsTop));
        else:
            self.__TitleNodeArray.append(TitleNode(inputTitle,inputIsTop));
    def printContents(self,string):
        resultText= "";
        for TitleNode in self.__TitleNodeArray:
            if(TitleNode.getIsTop()):
                resultText=resultText+"     "+TitleNode.getTitle()+" / "+self.pageNumber+"면 톱\n";
            else:
                resultText=resultText+"     "+TitleNode.getTitle()+" / "+self.pageNumber+"면\n";
        return resultText;
class TitleNode:
    def __init__(self,inputTitle,inputIsTop):
        self.inputTitle = inputTitle;
        self.inputIsTop = inputIsTop;
    def getTitle(self):
        return self.inputTitle;
    def getIsTop(self):
        return self.inputIsTop;
def ishasKeywords(aritcleText,keyWords):
    num =str(aritcleText).find(keyWords)
    #print(num)
    if(num!= -1):
        return True
    else:
        return False
#return value is reords and it used like that
# for record in records:
#    newsCompany = record[1]
def getArticlesAsrecords():
    connection = psycopg2.connect(user = "postgres", password = "postgres", host = "34.64.253.11",port = "5432", database = "postgres")
    cursor = connection.cursor()
    now = datetime.datetime.now()
    query = """select * from articletable order by newcompany where articletable.regdate = (%s)"""
    record_to_insert =(str(now.strftime('%Y%m%d'))
    cursor.execute(query,record_to_insert)
    records = cursor.fetchall() 
    return records
import Users       #user들을 불러올 py 코드
import datetime    #시간 측정용 패키지
import json
def makeMsgByJson(userJson_txt,records):
    userAndMsgMap={}
    users={}
    with open(userJson_txt+'.txt')as json_file:
        userJson=json.load(json_file)
        print(userJson)
        users=userJson.keys();
        print(users)
        for userName in users:
            newsArray = NewsArray();
            userAndMsgMap[userName]=""
            message = ""
            keyWords = userJson[userName]
            print("userName : "+userName)
            print("keywords : "+str(keyWords))
            currentNewsCompany=""
            for record in records:
                #setting values start
                newsCompany = record[1]
                articleTitle= record[2]
                articleToporNot= record[3]
                aritcleText=record[4]
                pageNumber=record[5]
                #setting values end
                if currentNewsCompany=="" or currentNewsCompany!=newsCompany:
                    currentNewsCompany=newsCompany
                    message=message+"\n"+currentNewsCompany+"\n"
                for keyWord in keyWords:
                    if(ishasKeywords(aritcleText,keyWord)==True or ishasKeywords(articleTitle,keyWord)==True):
                        newsArray.add(newsCompany,pageNumber,articleToporNot,articleTitle);
                        break
            message_string = newsArray.printContents();
            if(message_string==""):
                message_string="해당 키워드가 포함된 기사가 발행되지 않았습니다."
            userAndMsgMap[userName]= message_string
        print (userAndMsgMap)
        with open('MSG_'+userJson_txt[2:]+'Map.txt', 'w') as f:
            json.dump(userAndMsgMap, f, ensure_ascii=False)
def findArticleContainingKeyword():
    userAndMsgMap={} # 유저이름을 키, 보낼 메시지가 값으로 이뤄져있는 딕셔너리 자료형이다 이것을 반환값으로 설정할것.
    records = getArticlesAsrecords()
    #userName = 유저 이름
    #예) 정지원
    users={}
    makeMsgByJson("J_reporterPart1",records)
    makeMsgByJson("J_userPart2",records)
    makeMsgByJson("J_userPart6",records)
    return userAndMsgMap
findArticleContainingKeyword()
