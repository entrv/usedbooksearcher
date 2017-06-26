package kr.yookn.usedbooksearcher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import kr.yookn.usedbooksearcher.adapter.MessagesAdapter;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MessagesAdapter.MessageAdapterListener {
    private String yes24htmlPageUrl =
            "http://www.yes24.com/Mall/buyback/Search?CategoryNumber=018&SearchWord=%s&SearchDomain=BOOK,FOREIGN&BuybackAccept=N";
    private String aladinhtmlPageUrl
            = "http://www.aladin.co.kr/shop/usedshop/wc2b_search.aspx?ActionType=1&SearchTarget=Book&KeyWord=%s&x=22&y=30";
    private String interparkhtmlPageUrl
            = "http://book.interpark.com/display/buyGoods.do?_method=search&sc.searchWd=%s&sc.searchTarget=isbn&sc.searchTp=01&sc.row=20&sc.page=1";
    private String naverApiUrl = "https://openapi.naver.com/v1/search/book_adv.json?d_isbn=%s&d_isbn=&start=1";


    private static final String CLIENT_ID = "xRLvP4MeX5cxlZwnIZGW";
    private static final String CLIENT_SECRET = "9yZF_8a8HP";
    private static final String URL = "https://openapi.naver.com/v1/search/book_adv.json";


    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    String isbnString = "";
    String barcodeText = "";

    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat;
    ArrayList<IsbnInfo>   isbnInfos = new ArrayList<IsbnInfo>();


    private RecyclerView recyclerView;
    private MessagesAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    //private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;

    @Override
    public void onIconClicked(int position) {
        if (actionMode == null) {
           // actionMode = startSupportActionMode(actionModeCallback);
        }

       // toggleSelection(position);
    }

    @Override
    public void onIconImportantClicked(int position) {
        // Star icon is clicked,
        // mark the message as important
        IsbnInfo isbnInfo = isbnInfos.get(position);
        //isbnInfo.setImportant(!isbnInfo.isImportant());
        //isbnInfo.set(position, message);
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onMessageRowClicked(int position) {
        // verify whether action mode is enabled or not
        // if enabled, change the row state to activated
        if (mAdapter.getSelectedItemCount() > 0) {
            //enableActionMode(position);
        } else {
            // read the message which removes bold from the row
            IsbnInfo isbnInfo = isbnInfos.get(position);
            //isbnInfo.setRead(true);
            //isbnInfo.set(position, message);
            mAdapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), "Read: " + isbnInfo.getIsbn(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        // long press is performed, enable action mode
        //enableActionMode(position);
    }
    @Override
    public void onRefresh() {
        // swipe refresh is performed, fetch the messages again
        //getInbox();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                isbnString = "9791187799030";
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(isbnString);
                jsoupAsyncTask.execute();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new MessagesAdapter(this, isbnInfos, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        //actionModeCallback = new ActionModeCallback();

        // show loader and fetch messages
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        //getInbox();
                    }
                }
        );

        /*Button htmlTitleButton = (Button)findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });

        Button readBarcode = (Button)findViewById(R.id.read_barcode);
        readBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this)
                        .setBarcodeImageEnabled(true)
                        .setOrientationLocked(false)
                        .initiateScan();
            }
        });*/

        isbnString = "9788936473617";
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(isbnString);
        jsoupAsyncTask.execute();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("entrv", ">>> requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result.getFormatName().equals("EAN_13") || result.getFormatName().equals("EAN_10")) {
                Log.i("entrv", ">>> result.getContents()   :  " + result.getContents());
                Log.i("entrv", ">>> result.getFormatName()   :  " + result.getFormatName());


                isbnString = result.getContents();
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(isbnString);
                jsoupAsyncTask.execute();
            } else {
                Toast.makeText(MainActivity.this, "책 바코드 가 아닙니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        String isbnString = "";

        public JsoupAsyncTask(String isbnString) {
            this.isbnString = isbnString;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                Log.e("entrv", ">>> requestCode = " + isbnString );
                //isbnString = "9788936473617";

                IsbnInfo isbnInfo = new IsbnInfo(isbnString);

                String naverReplaceApiUrl = String.format(naverApiUrl,isbnString );
                java.net.URL url = new URL(naverReplaceApiUrl); //API 기본정보의 요청 url을 복사해오고 필수인 query를 적어줍니당!
                Log.e("entrv", ">>> naverApiUrl = " + naverApiUrl );
                URLConnection urlConn=url.openConnection(); //openConnection 해당 요청에 대해서 쓸 수 있는 connection 객체

                urlConn.setRequestProperty("X-Naver-Client-ID", CLIENT_ID);
                urlConn.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);

                BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String response;

                String msg = null;
                ByteArrayOutputStream baos = null;
                InputStream is = null;

                is = urlConn.getInputStream();

                baos = new ByteArrayOutputStream();

                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();
                response = new String(byteData);

                String bookTitle = "",bookLink="",bookImage="", bookAuthor="";
                String bookPrice ="", bookPublisher = "", bookPubdate = "", bookDescription = "";
                try {
                    JSONObject responseJSON = new JSONObject(response);
                    JSONArray array = responseJSON.getJSONArray("items");

                    JSONObject dataObj = array.getJSONObject(0);

                    bookTitle = (String) dataObj.get("title");
                    bookLink = (String) dataObj.get("link");
                    bookImage = (String) dataObj.get("image");
                    bookAuthor = (String) dataObj.get("author");
                    bookPrice = (String) dataObj.get("price");
                    bookPublisher = (String) dataObj.get("publisher");
                    bookPubdate = (String) dataObj.get("pubdate");
                    bookDescription = (String) dataObj.get("description");




                } catch (JSONException e) {
                    e.printStackTrace();
                }
                IsbnInfo.NaverBookItem naverBookItem = new IsbnInfo.NaverBookItem();
                naverBookItem.setTitle(bookTitle);
                naverBookItem.setLink(bookLink);
                naverBookItem.setImage(bookImage);
                naverBookItem.setAuthor(bookAuthor);
                naverBookItem.setPrice(bookPrice);
                naverBookItem.setPublisher(bookPublisher);
                naverBookItem.setPubdate(bookPubdate);
                naverBookItem.setDescription(bookDescription);

                isbnInfo.getNaverBookItems().add(naverBookItem);



                String originalMoney="",topMoney="",middleMoney="",lowMoney = "";
                String yes24ReplacehtmlPageUrl = String.format(yes24htmlPageUrl,isbnString );
                Document doc = Jsoup.connect(yes24ReplacehtmlPageUrl).get();
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

                String aladinReplacehtmlPageUrl = String.format(aladinhtmlPageUrl,isbnString );
                originalMoney="";topMoney="";middleMoney="";lowMoney = "";
                 doc = Jsoup.connect(aladinReplacehtmlPageUrl).get();
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

                String interparkReplacehtmlPageUrl = String.format(interparkhtmlPageUrl,isbnString );
                 doc = Jsoup.connect(interparkReplacehtmlPageUrl).get();
                 links = doc.select(".expectedPrice").select("strong");

                String poplink = doc.select(".prdImg").select("a").attr("onclick");
                Log.e("entrv", ">>> poplink = " + interparkhtmlPageUrl );
                String[] poplinkArray = poplink.split("'");

                //팝업창
                String interparkPopupUrl =  "http://book.interpark.com" + poplinkArray[1];
                doc = Jsoup.connect(interparkPopupUrl).get();
                String originalText = doc.select("#QUALITY_GRADE_STR1").attr("value");
                if (originalText.equals("전체")) {

                    topMoney = doc.select("#STR_BUY_PRICE1").attr("value");

                } else {
                    topMoney = doc.select("#STR_BUY_PRICE1").attr("value");
                    middleMoney = doc.select("#STR_BUY_PRICE2").attr("value");
                    lowMoney = doc.select("#STR_BUY_PRICE3").attr("value");
                }

                BookInfo bookInfo3 = new BookInfo("interpark",originalMoney, topMoney, middleMoney, lowMoney);





                isbnInfo.getBookinfo().add(bookInfo1);
                isbnInfo.getBookinfo().add(bookInfo2);
                isbnInfo.getBookinfo().add(bookInfo3);




                isbnInfos.add(isbnInfo);





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
            //textviewHtmlDocument.setText(htmlContentInStringFormat);
            mAdapter.notifyDataSetChanged();
        }
    }

}
