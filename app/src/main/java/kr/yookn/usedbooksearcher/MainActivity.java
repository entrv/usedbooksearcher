package kr.yookn.usedbooksearcher;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String yes24htmlPageUrl =
            "http://www.yes24.com/Mall/buyback/Search?CategoryNumber=018&SearchWord=9788936473617&SearchDomain=BOOK,FOREIGN&BuybackAccept=N";
    private String aladinhtmlPageUrl
            = "http://www.aladin.co.kr/shop/usedshop/wc2b_search.aspx?ActionType=1&SearchTarget=Book&KeyWord=9788936473617&x=22&y=30";
    private String interparkhtmlPageUrl
            = "http://book.interpark.com/display/buyGoods.do?_method=search&sc.searchWd=9788936473617&sc.searchTarget=isbn&sc.searchTp=01&sc.row=20&sc.page=1";


    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat;
    ArrayList<IsbnInfo>   isbnInfos = new ArrayList<IsbnInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textviewHtmlDocument = (TextView)findViewById(R.id.textView);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod());

        Button htmlTitleButton = (Button)findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {



                String originalMoney="",topMoney="",middleMoney="",lowMoney = "";

                Document doc = Jsoup.connect(yes24htmlPageUrl).get();
                Elements links = doc.select(".bbG_price > table > tbody ");

                for (Element link : links) {
                    htmlContentInStringFormat += (link.attr("abs:href")
                            + "("+link.text().trim() + ")\n");
                    String[] priceInfo = link.text().trim().split("원");

                         originalMoney = priceInfo[0];

                         topMoney = priceInfo[1];

                         middleMoney = priceInfo[2];

                         lowMoney =priceInfo[3];

                }
                BookInfo bookInfo1 = new BookInfo("yes24",originalMoney, topMoney, middleMoney, lowMoney);


                originalMoney="";topMoney="";middleMoney="";lowMoney = "";
                 doc = Jsoup.connect(aladinhtmlPageUrl).get();
                 links = doc.select(".c2b_tablet3");
                int ij = 0;
                for (Element link : links) {
                    htmlContentInStringFormat += (link.attr("abs:href")
                            + "("+link.text().trim() + ")\n");

                    if (ij == 0 ) {
                        originalMoney = link.text().trim();
                    }
                    if (ij == 1 ) {
                        topMoney = link.text().trim();
                    }
                    if (ij == 2 ) {
                        middleMoney = link.text().trim();
                    }
                    if (ij == 3 ) {
                        lowMoney = link.text().trim();
                    }
                    ij++;
                }
                BookInfo bookInfo2 = new BookInfo("aladin",originalMoney, topMoney, middleMoney, lowMoney);


                originalMoney="";topMoney="";middleMoney="";lowMoney = "";
                 doc = Jsoup.connect(interparkhtmlPageUrl).get();
                 links = doc.select(".expectedPrice").select("strong");
                topMoney = links.text().trim();


                BookInfo bookInfo3 = new BookInfo("interpark",originalMoney, topMoney, middleMoney, lowMoney);




                IsbnInfo isbnInfo = new IsbnInfo("1234");
                isbnInfo.getBookinfo().add(bookInfo1);
                isbnInfo.getBookinfo().add(bookInfo2);
                isbnInfo.getBookinfo().add(bookInfo3);




                isbnInfos.add(isbnInfo);


                for (int i=0; i < isbnInfos.size(); i++) {

                    IsbnInfo row = isbnInfos.get(i);
                    Log.d("entrv","aa >> " + row.getIsbn());
                    for (int j=0; j < isbnInfo.getBookinfo().size(); j++) {
                        BookInfo b = isbnInfo.getBookinfo().get(j);
                        Log.d("entrv","bb >> " +b.getSitename() + " >> " + b.getTopMoney());
                    }

                }

                        /*
                        List<CD> cds = new List<CD>();
CD cd = new CD("Thriller", someBitMap);
cd.getTracks().add(new Track("I'm bad", someMusic));
cds.add(cd);
                        */

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textviewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }

}
