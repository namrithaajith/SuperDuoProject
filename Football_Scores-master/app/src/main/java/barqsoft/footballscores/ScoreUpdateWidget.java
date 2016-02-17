package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.service.myFetchService;

/**
 * Implementation of App Widget functionality.
 */
public class ScoreUpdateWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
       RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.score_update_widget);
       // views.setTextViewText(R.id.appwidget_text, widgetText);

        ScoresDBHelper mOpenHelper;
        Cursor mCursor;
        String SCORES_BY_DATE = DatabaseContract.scores_table.DATE_COL + " LIKE ?";

        int COL_HOME = 3;
        int COL_AWAY = 4;
        int COL_HOME_GOALS = 6;
        int COL_AWAY_GOALS = 7;

        update_scores(context);

        String[] fragmentdate = new String[1];

        Date fragmentdate1 = new Date(System.currentTimeMillis());
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = mformat.format(fragmentdate1);

        fragmentdate[0] = formatted;
        mOpenHelper = new ScoresDBHelper(context);

        mCursor = mOpenHelper.getReadableDatabase().query(DatabaseContract.SCORES_TABLE,
                null,SCORES_BY_DATE,fragmentdate,null,null,null);

        String todaysResult = "";

        if(mCursor.getCount()>0) {

            for (int i = 0; i < mCursor.getCount(); i++) {
                mCursor.moveToPosition(i);

                // To check whether the game has already finished or not.

                if (!mCursor.getString(COL_HOME_GOALS).equals("-1") &&
                        !mCursor.getString(COL_AWAY_GOALS).equals("-1")) {

                    todaysResult += mCursor.getString(COL_HOME) + "   " + mCursor.getString(COL_HOME_GOALS) + " --  " +
                            "  " + mCursor.getString(COL_AWAY_GOALS) + "   " + mCursor.getString(COL_AWAY) + "\n";
                }
            }


            views.setTextViewText(R.id.appwidget_text, todaysResult);
            views.setContentDescription(R.id.appwidget_text, todaysResult);


        }
        else
        {
            views.setTextViewText(R.id.appwidget_text,"No Data Available !!");
            views.setContentDescription(R.id.appwidget_text, "No Data Available !!");
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }



    static void update_scores(Context context)
    {
        Intent service_start = new Intent(context, myFetchService.class);
        context.startService(service_start);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

