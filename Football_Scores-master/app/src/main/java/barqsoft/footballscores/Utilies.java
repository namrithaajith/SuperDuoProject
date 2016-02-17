package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;
    public static String getLeague(int league_num,Context context)
    {
        switch (league_num)
        {
            //case SERIE_A : return "Seria A";

            case SERIE_A :  return context.getResources().getString(R.string.seriaa);
            case PREMIER_LEGAUE : return context.getResources().getString(R.string.premierleague);
            case CHAMPIONS_LEAGUE : context.getResources().getString(R.string.champions_league);
            case PRIMERA_DIVISION : context.getResources().getString(R.string.primeradivison);
            case BUNDESLIGA : return context.getResources().getString(R.string.seriaa);
            default: return context.getResources().getString(R.string.not_known);
        }
    }
    public static String getMatchDay(int match_day,int league_num,Context context)
    {

        if(league_num == CHAMPIONS_LEAGUE)
        {
            if (match_day <= 6)
            {
               // return "Group Stages, Matchday : 6";
                return context.getResources().getString(R.string.group_stage_text)+", "+context.getResources().getString(R.string.matchday_text)+" : 6";
            }
            else if(match_day == 7 || match_day == 8)
            {
                //return "First Knockout round";
                return context.getResources().getString(R.string.first_knockout_round);
            }
            else if(match_day == 9 || match_day == 10)
            {
                //return "QuarterFinal";
                return context.getResources().getString(R.string.quarter_final);
            }
            else if(match_day == 11 || match_day == 12)
            {
               // return "SemiFinal";
                return context.getResources().getString(R.string.semi_final);
            }
            else
            {
                //return "Final";
                return context.getResources().getString(R.string.final_text);
            }
        }
        else
        {
            //return "Matchday : " + String.valueOf(match_day);
            return context.getResources().getString(R.string.matchday_text);
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}

        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            case "Arsenal FC" : return R.drawable.arsenal;

            case "Manchester United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City FC" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion FC" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;
            case "Aston Villa FC": return R.drawable.aston_villa;
            case "Liverpool FC":return R.drawable.liverpool;
            case "Manchester City FC" : return R.drawable.manchester_city;
            case "Swansea City FC": return R.drawable.swansea_city_afc;
            case "Southampton FC": return R.drawable.southampton_fc;
            case "Chelsea FC": return R.drawable.chelsea;
            case "Newcastle United FC": return R.drawable.newcastle_united;
            default: return R.drawable.no_icon;
        }
    }
}
