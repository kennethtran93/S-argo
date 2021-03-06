package io.github.spugn.Sargo.Objects;

public class Character
{
    private String prefix;
    private String name;
    private int rarity;
    private String imagePath;

    public Character()
    {
        prefix = "";
        name = "null";
        rarity = 1;
        imagePath = "";
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setRarity(int rarity)
    {
        this.rarity = rarity;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getName()
    {
        return name;
    }

    public int getRarity()
    {
        return rarity;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    @Override
    public String toString()
    {
        if (prefix.isEmpty())
        {
            return rarity + "★ " + name;
        }
        return rarity + "★ [" + prefix + "] " + name;
    }

    public String toStringNoName()
    {
        if (prefix.isEmpty())
        {
            return rarity + "★ " + name;
        }
        return rarity + "★ [" + prefix + "]";
    }
}
