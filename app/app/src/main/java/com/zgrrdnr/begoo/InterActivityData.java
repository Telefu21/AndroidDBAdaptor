package com.zgrrdnr.begoo;

import android.os.Parcel;
import android.os.Parcelable;

public class InterActivityData implements Parcelable
{
    static final String NAME_SCORE = "score";
    static final String NAME_TOTAL_SCORE = "totalScore";
    static final String NAME_SELECTED_CATEGORY = "selectedCategory";
    static final String NAME_USER_NAME = "userName";

    private int     timerValuePerCardMSec = 0;
    private int     delayTimeToEndLevelMSec = 0;
    private int     delayTimeToTurnMSec = 0;
    private int     selectedCategory = 0;
    private String  tbName = "";
    private String  dbName = "";
    private String  userName = "";

    private int     maxLevelRepetitionCounterMaxValue = 7;
    private int     minDifficultyValue = 2;
    private int     maxDifficultyValue = 4;

    public int getMaxLevelRepetitionCounterMaxValue() {
        return maxLevelRepetitionCounterMaxValue;
    }

    public void setMaxLevelRepetitionCounterMaxValue(int maxLevelRepetitionCounterMaxValue) {
        this.maxLevelRepetitionCounterMaxValue = maxLevelRepetitionCounterMaxValue;
    }

    public int getMinDifficultyValue() {
        return minDifficultyValue;
    }

    public void setMinDifficultyValue(int minDifficultyValue) {
        this.minDifficultyValue = minDifficultyValue;
    }

    public int getMaxDifficultyValue() {
        return maxDifficultyValue;
    }

    public void setMaxDifficultyValue(int maxDifficultyValue) {
        this.maxDifficultyValue = maxDifficultyValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSelectedCategory()
    {
        return selectedCategory;
    }

    public void setSelectedCategory(int selectedCategory)
    {
        this.selectedCategory = selectedCategory;
    }

    public int getTimerValuePerCardMSec() {
        return timerValuePerCardMSec;
    }

    public void setTimerValuePerCardMSec(int timerValuePerCardMSec) {
        this.timerValuePerCardMSec = timerValuePerCardMSec;
    }

    public int getDelayTimeToEndLevelMSec() {
        return delayTimeToEndLevelMSec;
    }

    public void setDelayTimeToEndLevelMSec(int delayTimeToEndLevelMSec) {
        this.delayTimeToEndLevelMSec = delayTimeToEndLevelMSec;
    }

    public int getDelayTimeToTurnMSec() {
        return delayTimeToTurnMSec;
    }

    public void setDelayTimeToTurnMSec(int delayTimeToTurnMSec) {
        this.delayTimeToTurnMSec = delayTimeToTurnMSec;
    }

    protected InterActivityData(Parcel in)
    {
        timerValuePerCardMSec = in.readInt();
        delayTimeToEndLevelMSec = in.readInt();
        delayTimeToTurnMSec = in.readInt();
        selectedCategory = in.readInt();
        tbName = in.readString();
        dbName = in.readString();
        userName = in.readString();
        maxLevelRepetitionCounterMaxValue = in.readInt();
        minDifficultyValue = in.readInt();
        maxDifficultyValue = in.readInt();
    }

    public InterActivityData()
    {

    }

    public String getTbName()
    {
        return tbName;
    }

    public void setTbName(String tbName)
    {
        this.tbName = tbName;
    }

    public String getDbName()
    {
        return dbName;
    }

    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(timerValuePerCardMSec);
        dest.writeInt(delayTimeToEndLevelMSec);
        dest.writeInt(delayTimeToTurnMSec);
        dest.writeInt(selectedCategory);
        dest.writeString(tbName);
        dest.writeString(dbName);
        dest.writeString(userName);
        dest.writeInt(maxLevelRepetitionCounterMaxValue);
        dest.writeInt(minDifficultyValue);
        dest.writeInt(maxDifficultyValue);
    }

    @SuppressWarnings("unused")
    public static final Creator<InterActivityData> CREATOR = new Creator<InterActivityData>()
    {
        @Override
        public InterActivityData createFromParcel(Parcel in)
        {
            return new InterActivityData(in);
        }

        @Override
        public InterActivityData[] newArray(int size)
        {
            return new InterActivityData[size];
        }
    };
}