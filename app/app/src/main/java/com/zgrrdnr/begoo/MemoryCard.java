package com.zgrrdnr.begoo;

import android.content.Context;

public class MemoryCard extends androidx.appcompat.widget.AppCompatImageButton
{
    private CardState       cardState           = CardState.UNINITIALIZED;
    private int             frontSidePictureId  = 0;
    private int             backSidePictureId   = 0;
    private int             matchedPictureId    = 0;
    private String          frontSideText       = "";
    private String          backSideText        = "";

    public MemoryCard(Context context)
    {
        super(context);
    }

    public int getFrontSidePictureId()
    {
        return frontSidePictureId;
    }

    public void setFrontSidePictureId(int frontSidePictureId)
    {
        this.frontSidePictureId = frontSidePictureId;
    }

    public void setBackSidePictureId(int backSidePictureId)
    {
        this.backSidePictureId = backSidePictureId;
    }

    public void setMatchedPictureId(int matchedPictureId)
    {
        this.matchedPictureId = matchedPictureId;
    }

    public CardState getCardState()
    {
        return cardState;
    }

    synchronized public void setCardState(CardState cardState)
    {
        this.cardState = cardState;

        switch(cardState)
        {
            case UNINITIALIZED:
                break;
            case INIT:
            case CLOSED:
                setImageBitmap(ImageRepository.getInstance().getImage()[backSidePictureId].getBmp());
                break;
            case OPEN:
                setImageBitmap(ImageRepository.getInstance().getImage()[frontSidePictureId].getBmp());
                break;

            case MATCHED:
                setImageBitmap(ImageRepository.getInstance().getImage()[matchedPictureId].getBmp());
                break;
        }
    }
}
