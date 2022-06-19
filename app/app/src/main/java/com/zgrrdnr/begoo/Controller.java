package com.zgrrdnr.begoo;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import java.util.Timer;
import java.util.TimerTask;

public class Controller implements View.OnClickListener
{
    private static final int        BACK_IMG_DB_ROW_INDEX = 0;
    private static final int        MATCH_IMG_DB_ROW_INDEX = 1;
    private static final int        BACKGROUND_IMG_DB_ROW_INDEX = 2;
    private static final int        IMG_DB_ROW_INDEX_OFFSET = 3;

    private static final int        GRID_LAYOUT_X_RATIO = 80;
    private static final int        GRID_LAYOUT_Y_RATIO = 80;

    private GridLayout              gridLayout;
    private MemoryCard[]            memCards, openCards;
    private AppCompatActivity       activity;
    private Image[]                 dbRowItems;
    private int                     faultCounter = 0;
    private int                     counterTimer = 0;
    private int                     matchedCounter = 0;
    private int                     dataOffset = 0;
    private int                     upperLevelDbRowOffset = 0;
    private CountDownTimer          countDownTimer;
    private Button                  backBt;
    private String                  userName;
    private UserInfo                userInfo;
    private DbAdapter               userInfoDb;
    private GridAndImgProperties    gridAndImgProperties;
    private InterActivityData       configParams;

    public Controller(AppCompatActivity activity, GridLayout gridLayout, InterActivityData params)
    {
        userInfoDb = new DbAdapter(activity.getApplicationContext(), DbAdapter.DB_USER_INFO_DB_NAME, null, 1, DbAdapter.DB_USER_INFO_TB_NAME);

        userName = params.getUserName(); 
        userInfo = userInfoDb.getData(userName);

        gridAndImgProperties = getGridAndImgProperties(getNumOfCards(), GRID_LAYOUT_X_RATIO, GRID_LAYOUT_Y_RATIO);

        backBt = new Button(activity);
        backBt.setText("BACK");
        backBt.setOnClickListener(this);

        memCards = new MemoryCard[getNumOfCards()];
        openCards = new MemoryCard[userInfo.getDifficulty()];

        this.gridLayout = gridLayout;
        this.activity = activity;

        configParams = params;

        dataOffset = userInfo.getDataOffset();

        for (int i = 0; i < openCards.length; i++)
        {
            openCards[i] = null;
        }

        for (int i = 0; i < memCards.length; i++)
        {
            memCards[i] = new MemoryCard(activity);
            memCards[i].setOnClickListener(this);
        }

        arrangeTheCards(memCards, userInfo.getDifficulty(), dataOffset);

        shuffleTheCards(memCards);

        setCounterTimer(getNumOfCards() * configParams.getTimerValuePerCardMSec(), 1000);

        setLayout(memCards);

        gridLayout.addView(backBt);
    }

    public static GridAndImgProperties getGridAndImgProperties(int numOfImg, int gridLayoutXRatio, int gridLayoutYRatio)
    {
        GridAndImgProperties gridAndImgProperties = new GridAndImgProperties();

        //int screenWidth = activity.getResources().getSystem().getDisplayMetrics().widthPixels;
        //int CardWidth = dbRowItems[0].getBmp().getWidth();

        //int numOfCardAtOneRow = (int)Math.floor(screenWidth/CardWidth);
        /*Needs to be fixed !!!*/
        gridAndImgProperties.maxColumnCount = 5;
        gridAndImgProperties.columnCount = 5;
        gridAndImgProperties.imgXScaleFactor = (float)0.5;
        gridAndImgProperties.imgYScaleFactor = (float)0.5;
        gridAndImgProperties.rowCount = 5;
        gridAndImgProperties.maxRowCount = 5;

        return gridAndImgProperties;
    }

    private int getNumOfCards()
    {
        int numOfCard = userInfo.getNumOfCards();

        if(numOfCard == 0)
        {
            numOfCard = userInfo.getMinNumOfCardBase() * userInfo.getDifficulty();
        }

        return (numOfCard + userInfo.getDifficulty());
    }

    private void setCounterTimer(long startValueMs, long timeIntervalMs)
    {
        TextView counterTimerView = new TextView(activity);

        addViewToPosition(counterTimerView,0,2,0,1);  /*Needs to be fixed !!!*/

        countDownTimer = new CountDownTimer(startValueMs, timeIntervalMs)
        {
            public void onTick(long millisUntilFinished)
            {
                counterTimer = (int) (millisUntilFinished / 1000);

                String zerosStr = "";

                if(counterTimer >= 10 && counterTimer < 100)
                {
                    zerosStr = "0";
                }

                if(counterTimer < 10)
                {
                    zerosStr = "00";
                }

                counterTimerView.setText(zerosStr + String.valueOf(counterTimer));
            }
            public  void onFinish()
            {
                counterTimer = 0;

                counterTimerView.setText("000");

                delayForLevelEnded(configParams.getDelayTimeToEndLevelMSec());
            }
        }.start();
    }

    private void delayForLevelEnded(long delay)
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        levelEnded();
                    }
                });
            }
        }, delay);
    }

    private void levelEnded()
    {
        int currentScore = calculateCurrentScore(faultCounter, counterTimer);
        int newTotalScore = calculateTotalScore(currentScore);

        if(currentScore != 0)
        {
            if(getNumOfCards() >= gridAndImgProperties.maxColumnCount * gridAndImgProperties.maxRowCount)
            {
                if(userInfo.getMaxLevelRepCounter() >= configParams.getMaxLevelRepetitionCounterMaxValue())
                {
                    userInfoDb.updateData(userName, "level", 0);

                    userInfoDb.updateData(userName, "maxLevelRepCounter", 0);

                    userInfoDb.updateData(userName, "numOfCards", 0);

                    if(userInfo.getDifficulty() == configParams.getMaxDifficultyValue())
                    {
                        userInfoDb.updateData(userName, "difficulty", configParams.getMinDifficultyValue());
                    }
                    else
                    {
                        userInfoDb.updateData(userName, "difficulty", userInfo.getDifficulty() + 1);
                    }

                }
                else
                {
                    userInfoDb.updateData(userName, "maxLevelRepCounter", userInfo.getMaxLevelRepCounter() + 1);
                }
            }
            else
            {
                userInfoDb.updateData(userName, "level", (userInfo.getLevel() + 1));
            }

            userInfoDb.updateData(userName, "score", newTotalScore);
            userInfoDb.updateData(userName, "numOfCards", getNumOfCards());
            userInfoDb.updateData(userName, "dataOffset", upperLevelDbRowOffset);
        }

        userInfo = userInfoDb.getData(userName);

        Intent intent = new Intent(activity, ScoreActivity.class);

        intent.putExtra(InterActivityData.NAME_SCORE, currentScore);
        intent.putExtra(InterActivityData.NAME_TOTAL_SCORE, newTotalScore);
        intent.putExtra(InterActivityData.NAME_SELECTED_CATEGORY, configParams.getSelectedCategory());
        intent.putExtra(InterActivityData.NAME_USER_NAME, userName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
        else
        {
            activity.startActivity(intent);
        }

        return;
    }

    private int calculateCurrentScore(int faultCounter, int counterTimer)
    {
        return (counterTimer * userInfo.getDifficulty() * (userInfo.getLevel() + 1));
    }

    private int calculateTotalScore(int currentScore)
    {
        return(currentScore + userInfo.getScore());
    }

    @Override
    public void onClick(View view)
    {
        if(view == backBt)
        {
            activity.finish();
            return;
        }

        MemoryCard clickedCard = (MemoryCard) view;

        if ((clickedCard.getCardState() == CardState.OPEN) || (clickedCard.getCardState() == CardState.MATCHED)) {
            return;
        }

        clickedCard.setCardState(CardState.OPEN);

        for (int i = 0; i < openCards.length; i++) {
            if (openCards[i] == null) {
                openCards[i] = clickedCard;

                if (i > 0) {
                    if (openCards[i].getFrontSidePictureId() == openCards[i - 1].getFrontSidePictureId()) {
                        if (i == (openCards.length - 1)) {
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setAllMatched();
                                        }
                                    });
                                }
                            }, configParams.getDelayTimeToTurnMSec());

                            break;
                        }
                    } else {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setAllClosed();
                                    }
                                });
                            }
                        }, configParams.getDelayTimeToTurnMSec());

                        break;
                    }
                }
                break;
            }
        }
    }

    private void setAllClosed() {
        for (int i = 0; i < openCards.length; i++) {
            if (openCards[i] != null) {
                openCards[i].setCardState(CardState.CLOSED);
                openCards[i] = null;
            }
        }

        faultCounter++;
    }

    private void setAllMatched() {
        for (int i = 0; i < openCards.length; i++) {
            openCards[i].setCardState(CardState.MATCHED);
            openCards[i] = null;
            matchedCounter++;
        }

        if(matchedCounter >= memCards.length)
        {
            delayForLevelEnded(configParams.getDelayTimeToEndLevelMSec());
            countDownTimer.cancel();
        }
    }

    private void arrangeTheCards(MemoryCard[] cards, int gameDifficulty, int dbRowOffset)
    {
        DbAdapter memCardsDb = new DbAdapter(this.activity.getApplicationContext(), configParams.getDbName(), null, 1, configParams.getTbName());

        int numOfRows = (int)(cards.length / gameDifficulty);
        int totalNumOfRows = (int)memCardsDb.getRowCount();

        dbRowItems = new Image[numOfRows + IMG_DB_ROW_INDEX_OFFSET];

        /*Get First IMG_DB_ROW_INDEX_OFFSET row since they are back and matched image and other specific items*/
        Image[] dbRowItemsTemp = memCardsDb.getData(IMG_DB_ROW_INDEX_OFFSET, 0);

        for (int i = 0; i < dbRowItemsTemp.length; i++)
        {
            dbRowItems[i] = dbRowItemsTemp[i];
        }

        /*Skip First IMG_DB_ROW_INDEX_OFFSET row since they are back and matched image and other specific items*/
        if((totalNumOfRows >= (dbRowOffset + IMG_DB_ROW_INDEX_OFFSET + numOfRows)) || (dbRowOffset >= (totalNumOfRows - IMG_DB_ROW_INDEX_OFFSET)))
        {
            if(dbRowOffset >= (totalNumOfRows - IMG_DB_ROW_INDEX_OFFSET))
            {
                dbRowOffset = 0;
            }

            dbRowItemsTemp = memCardsDb.getData(numOfRows, (dbRowOffset + IMG_DB_ROW_INDEX_OFFSET));

            for (int i = 0; i < dbRowItemsTemp.length; i++)
            {
                dbRowItems[i + IMG_DB_ROW_INDEX_OFFSET] = dbRowItemsTemp[i];
            }

            upperLevelDbRowOffset = dbRowOffset + numOfRows;
        }
        else
        {
            int i, j, rowToRead;

            rowToRead = totalNumOfRows - (dbRowOffset + IMG_DB_ROW_INDEX_OFFSET);

            dbRowItemsTemp = memCardsDb.getData(rowToRead, (dbRowOffset + IMG_DB_ROW_INDEX_OFFSET));

            for (i = 0; i < dbRowItemsTemp.length; i++)
            {
                dbRowItems[i + IMG_DB_ROW_INDEX_OFFSET] = dbRowItemsTemp[i];
            }

            rowToRead = numOfRows - rowToRead;

            dbRowItemsTemp = memCardsDb.getData(rowToRead, IMG_DB_ROW_INDEX_OFFSET);

            for (j = 0; j < dbRowItemsTemp.length; j++)
            {
                dbRowItems[j + i + IMG_DB_ROW_INDEX_OFFSET] = dbRowItemsTemp[j];
            }

            upperLevelDbRowOffset = rowToRead;
        }

        for (int i = 0; i < dbRowItems.length; i++)
        {
            dbRowItems[i].setBmp(BmpProcessor.scaleBmp(gridAndImgProperties.imgXScaleFactor, gridAndImgProperties.imgYScaleFactor,dbRowItems[i].getBmp()));
        }

        ImageRepository.getInstance().setImage(dbRowItems);

        ((ConstraintLayout)activity.findViewById(R.id.pgcl)).setBackground(new BitmapDrawable(activity.getResources(), (ImageRepository.getInstance().getImage()[BACKGROUND_IMG_DB_ROW_INDEX].getBmp())));

        for (int i = 0; i < cards.length; i++)
        {
            memCards[i].setBackSidePictureId(BACK_IMG_DB_ROW_INDEX);
            memCards[i].setMatchedPictureId(MATCH_IMG_DB_ROW_INDEX);
            memCards[i].setFrontSidePictureId((i % numOfRows) + IMG_DB_ROW_INDEX_OFFSET);
            memCards[i].setCardState(CardState.INIT);
        }
    }

    private void addViewToPosition(View vw, int colStart, int colSpan, int rowStart, int rowSpan)
    {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();

        params.width = GridLayout.LayoutParams.WRAP_CONTENT;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(colStart, colSpan);
        params.rowSpec = GridLayout.spec(rowStart, rowSpan);

        gridLayout.addView(vw, params);
    }

    private void setLayout(MemoryCard[] cards)
    {
        gridLayout.setColumnCount(gridAndImgProperties.columnCount);
        gridLayout.setRowCount(gridAndImgProperties.rowCount);

        for (int i = 0; i < cards.length; i++)
        {
            gridLayout.addView(memCards[i]);
        }
    }

    private void shuffleTheCards(MemoryCard[] cards)
    {
        for (int i = 0; i < cards.length; i++)
        {
            int r = (int) (Math.random() * cards.length);
            MemoryCard tempCard = cards[r];
            cards[r] = cards[i];
            cards[i] = tempCard;
        }
    }

    public Bitmap getBmp(int pictureId)
    {
        return dbRowItems[pictureId].getBmp();
    }

    public void calculateMinCardResolution()
    {
        int screenSize =  (activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

        switch(screenSize)
        {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                Log.d("abc", "X Large screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                Log.d("abc", "Large screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                Log.d("abc", "Normal screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                Log.d("abc", "Small screen");
                break;
            default:
                Log.d("abc", "Neither of them screen");
                break;
        }
    }
}
