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
    private String htmlPageUrl = "http://www.yes24.com/Mall/buyback/Search?CategoryNumber=018&SearchWord=9788936473617&SearchDomain=BOOK,FOREIGN&BuybackAccept=N";
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
                Document doc = Jsoup.connect(htmlPageUrl).get();
                Elements links = doc.select(".bbG_price > table > tbody > tr");


                String originalMoney="";
                String topMoney="",middleMoney="",lowMoney = "";
                for (Element link : links) {
                    htmlContentInStringFormat += (link.attr("abs:href")
                            + "("+link.text().trim() + ")\n");
                    String[] priceInfo = link.text().trim().split(",");

                         originalMoney = priceInfo[0];

                         topMoney = priceInfo[1];

                         middleMoney = priceInfo[2];

                         lowMoney =priceInfo[3];

                }
                BookInfo bookInfo1 = new BookInfo("yes24",originalMoney, topMoney, middleMoney, lowMoney);
                BookInfo bookInfo2 = new BookInfo("interpark",originalMoney, topMoney, middleMoney, lowMoney);
                IsbnInfo isbnInfo = new IsbnInfo("1234");
                isbnInfo.getBookinfo().add(bookInfo1);
                isbnInfo.getBookinfo().add(bookInfo2);

                BookInfo bookInfo3 = new BookInfo("yes24-1",originalMoney, topMoney, middleMoney, lowMoney);
                BookInfo bookInfo4 = new BookInfo("interpark-2",originalMoney, topMoney, middleMoney, lowMoney);
                IsbnInfo isbnInfo2 = new IsbnInfo("5678");

                isbnInfo2.getBookinfo().add(bookInfo3);
                isbnInfo2.getBookinfo().add(bookInfo4);


                isbnInfos.add(isbnInfo);
                isbnInfos.add(isbnInfo2);


                for (int i=0; i < isbnInfos.size(); i++) {

                    IsbnInfo row = isbnInfos.get(i);
                    Log.d("entrv","aa >> " + row.getIsbn());

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
