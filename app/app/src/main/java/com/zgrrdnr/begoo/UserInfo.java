package com.zgrrdnr.begoo;

public class UserInfo
{
    String  username;
    String  password;
    int     score;
    int     dataOffset;
    int     level;
    int     difficulty;
    int     category;
    int     maxLevelRepCounter;
    int     numOfCards;
    int     minNumOfCardBase;

    public int getMinNumOfCardBase()
    {
        return minNumOfCardBase;
    }

    public void setMinNumOfCardBase(int minNumOfCardBase)
    {
        this.minNumOfCardBase = minNumOfCardBase;
    }

    public int getNumOfCards()
    {
        return numOfCards;
    }

    public void setNumOfCards(int numOfCards)
    {
        this.numOfCards = numOfCards;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDataOffset() {
        return dataOffset;
    }

    public void setDataOffset(int dataOffset) {
        this.dataOffset = dataOffset;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getMaxLevelRepCounter()
    {
        return maxLevelRepCounter;
    }

    public void setMaxLevelRepCounter(int maxLevelRepCounter)
    {
        this.maxLevelRepCounter = maxLevelRepCounter;
    }
}
