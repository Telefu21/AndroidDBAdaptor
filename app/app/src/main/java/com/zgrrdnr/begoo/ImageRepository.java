package com.zgrrdnr.begoo;

public class ImageRepository
{
    private static ImageRepository _instance = null;

    private Image[] image;

    public static ImageRepository getInstance()
    {
        if (_instance == null)
        {
            _instance = new ImageRepository();
        }

        return _instance;
    }

    public Image[] getImage()
    {
        return image;
    }

    public void setImage(Image[] image)
    {
        this.image = image;
    }
}
