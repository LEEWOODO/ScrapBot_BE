#!/usr/bin/env python
# coding: utf-8

# In[ ]:


#!/usr/bin/env python
# coding: utf-8

# In[1]:

import sys
sys.path.append("/usr/local/lib/python3.7/site-packages")
print("sys.path")
from selenium import webdriver
from bs4 import BeautifulSoup
import Users
import datetime
import json
import schedule
import time
from multiprocessing import Pool


# In[2]:


import psycopg2


# In[3]:


class keywordObj:
    def __init__(self,keyword):
        print('init')
        super().__init__()
        self.keyword = str(keyword)
        self.papers=[]
 

    def addPaper(self,paper):
        self.papers.append(paper)
    
    def show(self):
        print(
            "키워드 : {}".format(self.keyword)
        )
        for n in self.papers:
            n.show()
        


# In[7]:


class paperObj:
    def __init__(self,title,num,url):
        #print('페이지객체 생성')
        super().__init__()
        self.title = str(title)
        self.num= num
        self.url=str(url)
    def show(self):
        print(
            """
            제목 : {}.[{}]
            url : {}""".format(self.title, self.num, self.url)
        )


# In[8]:


class articleObj:
    def __init__(self,newscompany,title,num,url):
        super().__init__()
        self.newscompany=str(newscompany)
        self.num= str(num)
        self.title = str(title)
        self.top=False
        self.text =str(text)
        
        self.keywords=[]
        self.url=str(url)
        
    def show(self):
        print(
            """
            제목 : {}/{}
            url : {}""".format(self.title, self.num, self.url)
        )
    def showWithKeywords(self):
        print(
            """
            제목 : {}.[{}] 키워드 {}
            url : {}""".format(self.title, self.num,self.keywords ,self.url)
        )
    def addKeyword(self,keyword):
        self.keywords.append(keyword)
        
    def showForClient(self):
        if(self.top==True):
            print("""{}/ {} 톱""".format(self.title, self.num))
            return """{}/ {} 톱""".format(self.title, self.num)
        else:        
            print("""{}/ {}""".format(self.title, self.num))
            return """{}/ {}""".format(self.title, self.num)
            
    def showForClientUpgrade(self):
        if(self.top==True):
            print("""\t{}/{} 톱 키워드 {}\n\turl : {}""".format(self.title, self.num,self.keywords,self.url))
        else:        
            print("""\t{}/{} 키워드 {}\n\turl : {}""".format(self.title, self.num,self.keywords,self.url))
            
    def setTop(self):
        self.top=True
        


# In[9]:


#함수 정의 
def ishasKeywords(aritcleText,keyWords):
    num =str(aritcleText).find(keyWords)
    #print(num)
    if(num!= -1):
        return True
    else:
        return False
    
    


# In[6]:


def crollingArticle(newspaperCode,resultname):
    userContents = {}
    newsCompany=""              # 신문 회사 ✓
    pageNumber=""               # 페이지번호 ✓
    articleToporNot = False     # 기사 최상단 여부 ✓
    articleTitle = ""           # 기사 제목 ✓
    aritcleText=""              # 기사 본문영역 html ✓
    articleURL=""               # 기사 url
    articleHTML=""              # 기사 HTML
    
    
    for userName in Users.getList():
        keyWords = Users.users[userName]
        now = datetime.datetime.now()
        print("userName : "+userName)
        print("keywords : "+str(keyWords))
        options = webdriver.ChromeOptions()
        options.add_argument('headless')
        options.add_argument('window-size=1920x1080')
        options.add_argument("disable-gpu")
        driver = webdriver.Chrome('./chromedriver',chrome_options=options)
        driver.implicitly_wait(3)
        newspaperArticleArray={}
        
        #신문사 접속
        for newpaperkey in newspaperCode:
            print(newpaperkey)
            #신문회사 세팅 완료
            newsCompany = newpaperkey
            driver.get('https://media.naver.com/press/'+newspaperCode[newpaperkey]+'/newspaper?date='+str(now.strftime('%Y%m%d'))+'#lnb')
            #driver.get('https://media.naver.com/press/'+newspaperCode[newpaperkey]+'/newspaper?date=20200219#lnb')
            print(str(now.strftime('%Y%m%d')))
            html = driver.page_source
            soup = BeautifulSoup(html, 'html.parser')
            notices = soup.find_all("div", "newspaper_wrp")
            i =1;
            pages={}
            
            for n in notices:
                print(n.pre)
                number =0
                #페이지 넘버 세팅완료
                pageNumber = n.em.text
                articlesForPaper = n.findAll("a")
                for w in articlesForPaper:
                    #페이지 상단여부 세팅완료
                    articleToporNot=False
                    if number ==0:
                        articleToporNot =True
                    number=number+1
                    #페이지 제목 세팅완료
                    articleTitle = w.strong.text
                    articleURL = w["href"]
                    #새로운 브라우저로의 접속
                    finder = webdriver.Chrome('./chromedriver',chrome_options=options)
                    finder.get(articleURL)
                    #접속해서 키워드를 찾는 코드
                    articleHTML = finder.page_source
                    print("기사제목 : "+articleTitle)
                    articleSoup = BeautifulSoup(articleHTML, 'html.parser')
                    #페이지 본문 세팅 완료
                    aritcleText=articleSoup.find('div','newsct_article')
                    try:
                        for a in aritcleText.find_all("a"):
                            a.decompose()
                    except:
                        articleSoup = BeautifulSoup(articleHTML,'html.parser')
                        aritcleText=articleSoup.find('article',"main_article")
                        for a in aritcleText.find_all("a"):
                            a.decompose()
                        print(aritcleText)
                    #db연결
                    try:
                        connection = psycopg2.connect(user = "postgres", password = "postgres", host = "34.64.253.11",port = "5432", database = "postgres")
                        cursor = connection.cursor()
                        # Print PostgreSQL Connection properties
                        print ( connection.get_dsn_parameters(),"\n")
                        
                        c='\''
                        articleTitle = articleTitle.replace(c, "\"")
                        #해당 기사가 디비에 이미 있는지 확인하는 작업
                        select ="""SELECT id, newcompany, title, topornot, text, pagenumber FROM public.articletable where title ='%s';"""% (articleTitle,)

#     record_to_select= (articleTitle)
                        cursor.execute(select)
                        records = cursor.fetchall() 
                        count =0
                        for record in records:
                            count=count +1
                        
                        if count == 0:
                            #db insert 수행
                            insert="""INSERT INTO public.articletable(
                            newcompany, title, pagenumber,topornot, text,  urlpath, regdate, newscompany_id)
                            VALUES (%s, %s, %s, %s, %s, %s, %s, %s);"""
                            insert_past ="""insert into articletable (newcompany ,title, pagenumber, topornot, text) values (%s,%s,%s,%s,%s);""" 
                            record_to_insert = (str(newsCompany), str(articleTitle), str(pageNumber),articleToporNot,str(aritcleText)
                            #urlpath
                            ,str(articleURL)
                            #regdate 등록날짜
                            ,str(now.strftime('%Y%m%d'))
                            #newscompany_id 신문사 id
                            ,str(newspaperCode[newpaperkey])
                            )
                            cursor.execute(insert, record_to_insert)
                            


                            connection.commit()
                            count = cursor.rowcount
                            print (count, "Record inserted successfully into mobile table")
                    except (Exception, psycopg2.Error) as error :
                        if(connection):
                            print("Failed to insert record into")
                            print(error)
                    finally:
                        #closing database connection.
                        if(connection):
                            cursor.close()
                            connection.close()
                            print("PostgreSQL connection is closed")

                    
                    
                    finder.close()
                    


# In[4]:


newscode={
    "서울경제":"011"
}


crollingArticle(newscode,"result3")

