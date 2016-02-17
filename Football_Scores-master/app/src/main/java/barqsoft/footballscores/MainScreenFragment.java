package barqsoft.footballscores;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import barqsoft.footballscores.service.myFetchService;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainScreenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    public scoresAdapter mAdapter;
    public static final int SCORES_LOADER = 0;
    private String[] fragmentdate = new String[1];
    private int last_selected_item = -1;

    public MainScreenFragment()
    {
    }

    private void update_scores()
    {
        Intent service_start = new Intent(getActivity(), myFetchService.class);


        getActivity().startService(service_start);
    }
    public void setFragmentDate(String date)
    {
        fragmentdate[0] = date;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        update_scores();
        System.out.println("Fetched the results after updatescores........");

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        System.out.println("container......"+container);
        final ListView score_list = (ListView) rootView.findViewById(R.id.scores_list);
        mAdapter = new scoresAdapter(getActivity(),null,0);
        score_list.setAdapter(mAdapter);
        getLoaderManager().initLoader(SCORES_LOADER, null, this);
        mAdapter.detail_match_id = MainActivity.selected_match_id;

        System.out.println("mAdapter.detail_match_id = MainActivity.selected_match_id......"+mAdapter.detail_match_id);
        score_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ViewHolder selected = (ViewHolder) view.getTag();
                mAdapter.detail_match_id = selected.match_id;
                MainActivity.selected_match_id = (int) selected.match_id;
                mAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        CursorLoader cursorLoaderData ;
        cursorLoaderData =  new CursorLoader(getActivity(),DatabaseContract.scores_table.buildScoreWithDate(),
                null,null,fragmentdate,null);




        return cursorLoaderData;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        //Log.v(FetchScoreTask.LOG_TAG,"loader finished");
        //cursor.moveToFirst();
        /*
        while (!cursor.isAfterLast())
        {
            Log.v(FetchScoreTask.LOG_TAG,cursor.getString(1));
            cursor.moveToNext();
        }
        */

        int i = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            i++;


            System.out.println("Cursor Data[0]-->"+cursor.getString(0));
            System.out.println("Cursor Data[1]-->"+cursor.getString(1));
            System.out.println("Cursor Data[2]-->"+cursor.getString(2));
            System.out.println("Cursor Data[3]-->"+cursor.getString(3));
            System.out.println("Cursor Data[4]-->"+cursor.getString(4));
            System.out.println("Cursor Data[5]-->"+cursor.getString(5));
            System.out.println("Cursor Data[6]-->"+cursor.getString(6));
            System.out.println("Cursor Data[7]-->"+cursor.getString(7));
            System.out.println("Cursor Data[8]-->"+cursor.getString(8));
            System.out.println("Cursor Data[9] matchday-->"+cursor.getString(9));
           // System.out.println("Cursor Data[10] matchstatus-->"+cursor.getString(10));

            cursor.moveToNext();



        //    System.out.println("Cursor Data[0]-->"+cursor.getString(6));


        }
        //Log.v(FetchScoreTask.LOG_TAG,"Loader query: " + String.valueOf(i));
        mAdapter.swapCursor(cursor);



        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {
        mAdapter.swapCursor(null);
    }


}
