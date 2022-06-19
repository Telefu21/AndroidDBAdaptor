package com.zgrrdnr.begoo;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;

import com.google.gson.Gson;

public class PlayGroundAdapter
{
    private Image[]                 dbRowItems;
    private CategoriesMiscParams[]  categoriesMiscParams;
    private Activity                activity;

    public PlayGroundAdapter(Activity activity)
    {
        this.activity = activity;

        DbAdapter categoriesDb = new DbAdapter(activity.getApplicationContext(), DbAdapter.DB_CARDS_DB_NAME, null, 1, DbAdapter.DB_CARDS_TB_NAME);

        dbRowItems = categoriesDb.getData(0, 0);

        categoriesMiscParams = new CategoriesMiscParams[dbRowItems.length];

        Gson gson = new Gson();

        for(int i = 0; i < dbRowItems.length; i++)
        {
            categoriesMiscParams[i] = gson.fromJson(dbRowItems[i].getCustomStr(), CategoriesMiscParams.class);
        }
    }

    public void launchPlayGround(int selectedCategoryIndex, String userName)
    {
        Intent intent = new Intent(activity, PlayGroundActivity.class);

        InterActivityData interActData = new InterActivityData();

        interActData.setDbName("CardsDb");
        interActData.setUserName(userName);
        interActData.setTbName(categoriesMiscParams[selectedCategoryIndex].getTableName());
        interActData.setDelayTimeToEndLevelMSec(categoriesMiscParams[selectedCategoryIndex].getDelayTimeToEndLevelMSec());
        interActData.setDelayTimeToTurnMSec(categoriesMiscParams[selectedCategoryIndex].getDelayTimeToTurnMSec());
        interActData.setTimerValuePerCardMSec(categoriesMiscParams[selectedCategoryIndex].getTimerValuePerCardMSec());
        interActData.setSelectedCategory(selectedCategoryIndex);
        interActData.setMaxLevelRepetitionCounterMaxValue(categoriesMiscParams[selectedCategoryIndex].getMaxLevelRepetitionCounterMaxValue());
        interActData.setMinDifficultyValue(categoriesMiscParams[selectedCategoryIndex].getMinDifficultyValue());
        interActData.setMaxDifficultyValue(categoriesMiscParams[selectedCategoryIndex].getMaxDifficultyValue());

        intent.putExtra("InterActData", interActData);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
        else
        {
            activity.startActivity(intent);
        }
    }

    public Image[] getDbRowItems()
    {
        return dbRowItems;
    }
}
