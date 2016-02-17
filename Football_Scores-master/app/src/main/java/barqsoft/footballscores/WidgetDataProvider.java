package barqsoft.footballscores;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;
import android.database.Cursor;

/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static ScoresDBHelper mOpenHelper;
    private static final String TAG = "WidgetDataProvider";
    public static final String LOG_TAG = "WidgetDataProvider";
    private Cursor mCursor;
    private static final String SCORES_BY_DATE =
            DatabaseContract.scores_table.DATE_COL + " LIKE ?";


    Context mContext = null;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;

    }

    @Override
    public void onCreate() {

        mOpenHelper = new ScoresDBHelper(mContext);


    }

    @Override
    public void onDataSetChanged() {

        //initData();

        // Refresh the cursor
        if (mCursor != null) {
            mCursor.close();
        }

         String[] fragmentdate = new String[1];

        Date fragmentdate1 = new Date(System.currentTimeMillis());
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = mformat.format(fragmentdate1);

        fragmentdate[0] = formatted;


        /*mCursor  = mOpenHelper.getReadableDatabase().query(
                DatabaseContract.SCORES_TABLE,
                null,fragmentdate,null,null,null);*/

        mCursor = mOpenHelper.getReadableDatabase().query(DatabaseContract.SCORES_TABLE,
                null,SCORES_BY_DATE,fragmentdate,null,null,null);




    }

    @Override
    public void onDestroy() {

        if (mCursor != null) {
            mCursor.close();
        }

    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.scores_list_item_forwidget);


        int COL_HOME = 3;
        int COL_AWAY = 4;
        int COL_HOME_GOALS = 6;
        int COL_AWAY_GOALS = 7;

      //  String todaysResult = "";

        if (mCursor.moveToPosition(position)) {

            if (!mCursor.getString(COL_HOME_GOALS).equals("-1") &&
                    !mCursor.getString(COL_AWAY_GOALS).equals("-1")) {

               // todaysResult += mCursor.getString(COL_HOME) + " " + mCursor.getString(COL_HOME_GOALS) + " --  " +
               //         " " + mCursor.getString(COL_AWAY_GOALS) + " " + mCursor.getString(COL_AWAY) + "\n";

                view.setTextViewText(R.id.home_name_widget, mCursor.getString(COL_HOME)+"  ");
                view.setContentDescription(R.id.home_name_widget, "The home team is " + mCursor.getString(COL_HOME));



                view.setTextViewText(R.id.score_textview_widget, mCursor.getString(COL_HOME_GOALS) + "-" + mCursor.getString(COL_AWAY_GOALS));
                view.setContentDescription(R.id.score_textview_widget,"The score is "+mCursor.getString(COL_HOME_GOALS) + "-" + mCursor.getString(COL_AWAY_GOALS));

                view.setTextViewText(R.id.away_name_widget, "  "+mCursor.getString(COL_AWAY));
                view.setContentDescription(R.id.away_name_widget,"The away team is "+mCursor.getString(COL_AWAY));


            }
        }






        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

















}
