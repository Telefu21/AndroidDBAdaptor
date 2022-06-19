package com.zgrrdnr.begoo;

public class CategoriesMiscParams
{
    private String  tableName = "";
    private int     timerValuePerCardMSec = 0;
    private int     delayTimeToEndLevelMSec = 0;
    private int     delayTimeToTurnMSec = 0;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
}
