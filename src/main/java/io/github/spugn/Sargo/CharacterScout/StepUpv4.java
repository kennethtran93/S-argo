package io.github.spugn.Sargo.CharacterScout;

import io.github.spugn.Sargo.Managers.CommandManager;
import io.github.spugn.Sargo.Objects.Banner;
import io.github.spugn.Sargo.Objects.Character;
import io.github.spugn.Sargo.Objects.WarningMessage;
import sx.blah.discord.handle.obj.IChannel;

import java.util.List;

public class StepUpv4 extends CharacterScout
{
    public StepUpv4(IChannel channel, int bannerID, String choice, String discordID)
    {
        super(channel, bannerID, choice, discordID);
        run();
    }

    @Override
    protected void initBannerInfo()
    {
        USER.addBannerInfo(SELECTED_BANNER.getBannerName(), 1);
        bannerTypeData = 1;
    }

    @Override
    protected void modifyScoutData()
    {
        COPPER = COPPER - ((PLATINUM * 1.5) - PLATINUM);
        PLATINUM = PLATINUM * 1.5;

        switch (bannerTypeData)
        {
            case 1:
                multiScoutPrice = 125;
                break;
            case 3:
                multiScoutPrice = 125;
                COPPER = COPPER - ((PLATINUM * 1.5) - PLATINUM);
                PLATINUM = PLATINUM * 1.5;
                break;
            case 5:
                guaranteeOnePlatinum = true;
                break;
            case 6:
                COPPER = COPPER - ((PLATINUM * 2.0) - PLATINUM);
                PLATINUM = PLATINUM * 2.0;
            default:
                break;
        }
    }

    @Override
    protected void updateBannerData()
    {
        int currentStep = USER.getBannerData(SELECTED_BANNER.getBannerName());
        currentStep++;
        if (currentStep > 6)
        {
            USER.changeValue(SELECTED_BANNER.getBannerName(), 6);
        }
        else
        {
            USER.changeValue(SELECTED_BANNER.getBannerName(), currentStep);
        }
    }

    @Override
    protected Character randGoldCharacter()
    {
        /* GET A RANDOM GOLD VARIANT CHARACTER, IF THERE IS A PLATINUM
           VARIANT OF THAT CHARACTER IN THE BANNER THEN GET A NEW CHARACTER. */
        Character c = null;
        boolean charInBanner = true;
        int randIndex;
        Banner randBanner;
        List<Character> randCharacters;
        boolean sameName;
        boolean samePrefix;

        while(charInBanner)
        {
            randIndex = GOLD_BANNERS_V2.get(RNG.nextInt(GOLD_BANNERS_V2.size()));
            randBanner = BANNERS.get(randIndex - 1);
            randCharacters = randBanner.getCharacters();
            c = randCharacters.get(RNG.nextInt(randCharacters.size()));

            for (Character bc : SELECTED_BANNER.getCharacters())
            {
                sameName = c.getName().equalsIgnoreCase(bc.getName());
                samePrefix = c.getPrefix().equalsIgnoreCase(bc.getPrefix());

                if (!(sameName && samePrefix))
                {
                    charInBanner = false;
                }
                else
                {
                    charInBanner = true;
                    break;
                }
            }
        }

        return c;
    }

    @Override
    protected void setupScoutMenu()
    {
        if (!SIMPLE_MESSAGE)
        {
            switch (CHOICE.toLowerCase())
            {
                case "s":
                case "si":
                    scoutMenu.withTitle("Single Pull");
                    break;
                case "m":
                case "mi":
                    scoutMenu.withTitle("[Step Up v4] - Step " + bannerTypeData);
                    break;
                default:
                    scoutMenu.withTitle("[Step Up v4] - Unknown");
                    break;
            }
        }
        else
        {
            switch (CHOICE.toLowerCase())
            {
                case "s":
                case "si":
                    simpleMessage += "**Single Pull**" + "\n";
                    break;
                case "m":
                case "mi":
                    simpleMessage += "**[Step Up v4] - Step " + bannerTypeData + "**" + "\n";
                    break;
                default:
                    simpleMessage += "**[Step Up v4] - Unknown**" + "\n";
                    break;
            }
        }
    }

    @Override
    protected void run()
    {
        switch (CHOICE.toLowerCase())
        {
            case "s":
            case "si":
                if (userMemoryDiamonds < singleScoutPrice)
                {
                    CHANNEL.sendMessage(new WarningMessage("NOT ENOUGH MEMORY DIAMONDS", "You need **" + singleScoutPrice + "** Memory Diamonds to scout.\nUse '" + CommandManager.getCommandPrefix() + "**shop**' to get more Memory Diamonds.").get().build());
                    return;
                }

                if (CHOICE.equalsIgnoreCase("si") && !IMAGE_DISABLED)
                {
                    generateImage = true;
                }

                userMemoryDiamonds -= singleScoutPrice;
                USER.setMemoryDiamonds(userMemoryDiamonds);

                doSinglePull();
                break;
            case "m":
            case "mi":
                if (userMemoryDiamonds < multiScoutPrice)
                {
                    CHANNEL.sendMessage(new WarningMessage("NOT ENOUGH MEMORY DIAMONDS", "You need **" + multiScoutPrice + "** Memory Diamonds to scout.\nUse '" + CommandManager.getCommandPrefix() + "**shop**' to get more Memory Diamonds.").get().build());
                    return;
                }

                if (CHOICE.equalsIgnoreCase("mi") && !IMAGE_DISABLED)
                {
                    generateImage = true;
                }

                userMemoryDiamonds -= multiScoutPrice;
                USER.setMemoryDiamonds(userMemoryDiamonds);

                doMultiPull();
                updateBannerData();
                break;
            default:
                CHANNEL.sendMessage(new WarningMessage("UNKNOWN/UNAVAILABLE SCOUT TYPE", "Only `single` and `multi` scouts are available.").get().build());
                return;
        }

        if (stopScout)
            return;

        displayAndSave();
    }
}
