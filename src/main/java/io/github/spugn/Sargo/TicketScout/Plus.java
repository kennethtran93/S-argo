package io.github.spugn.Sargo.TicketScout;

import io.github.spugn.Sargo.Objects.Item;
import io.github.spugn.Sargo.Utilities.GitHubImage;
import sx.blah.discord.handle.obj.IChannel;

public class Plus extends TicketScout
{

    public Plus(IChannel channel, String choice, String discordID)
    {
        super(channel, choice, discordID);
        run();
    }

    @Override
    protected void initItems()
    {
        medallionsAndKeyList.add(new Item("Void Medallion", 3, 7500));
        medallionsAndKeyList.add(new Item("Fire Medallion", 3, 7500));
        medallionsAndKeyList.add(new Item("Water Medallion", 3, 7500));
        medallionsAndKeyList.add(new Item("Wind Medallion", 3, 7500));
        medallionsAndKeyList.add(new Item("Holy Medallion", 3, 7500));
        medallionsAndKeyList.add(new Item("Dark Medallion", 3, 7500));
        medallionsAndKeyList.add(new Item("Earth Medallion", 3, 7500));
    }

    @Override
    protected int scout()
    {
        double d;
        d = RNG.nextDouble() * 100;

        double exchangeSwordR3 = 8;
        double exchangeSwordR4 = 1;
        double medallions = 63;
        double stardust = 24;

        /* EXCHANGE SWORD R3 */
        if (d < exchangeSwordR3)
        {
            return 1;
        }
        /* EXCHANGE SWORD R4 */
        else if (d < exchangeSwordR3 + exchangeSwordR4)
        {
            return 2;
        }
        /* MEDALLIONS */
        else if (d < exchangeSwordR3 + exchangeSwordR4 + medallions)
        {
            return 3;
        }
        /* STARDUST */
        else if (d < exchangeSwordR3 + exchangeSwordR4 + medallions + stardust)
        {
            return 4;
        }
        /* RAINBOW ESSENCE */
        else
        {
            return 5;
        }
    }

    @Override
    protected Item getItem(int value)
    {
        Item item;

        /* EXCHANGE SWORD R3 */
        if (value == 1)
        {
            item = new Item("Exchange Sword R3", 1, -1);
        }
        /* EXCHANGE SWORD R4 */
        else if (value == 2)
        {
            item = new Item("Exchange Sword R4", 1, -1);
        }
        /* MEDALLIONS  */
        else if (value == 3)
        {
            item = medallionsAndKeyList.get(RNG.nextInt(medallionsAndKeyList.size()));
        }
        /* STARDUST */
        else if (value == 4)
        {
            item = new Item("Stardust (Medium)", 20, 1000);
        }
        /* RAINBOW ESSENCE */
        else
        {
            item = new Item("Rainbow Essence", 1, -1);
        }

        giveCol(item);
        return item;
    }

    @Override
    protected void setupScoutMenu()
    {
        if (!SIMPLE_MESSAGE)
        {
            scoutMenu.withAuthorName("Plus Ticket Scout");
            scoutMenu.withThumbnail(new GitHubImage("images/System/Plus_Ticket.png").getURL());
            switch (CHOICE.toLowerCase())
            {
                case "pts":
                case "ptsi":
                    scoutMenu.withTitle("[Ticket Scout] - Single Pull");
                    break;
                case "ptm":
                case "ptmi":
                    scoutMenu.withTitle("[Ticket Scout] - Multi Pull");
                    break;
                default:
                    scoutMenu.withTitle("[Ticket Scout] - Unknown");
                    break;
            }
        }
        else
        {
            simpleMessage += "**Plus Ticket Scout**" + "\n";
            switch (CHOICE.toLowerCase())
            {
                case "nts":
                case "ntsi":
                    simpleMessage += "**[Ticket Scout] - Single Pull**" + "\n";
                    break;
                case "ntm":
                case "ntmi":
                    simpleMessage += "**[Ticket Scout] - Multi Pull**" + "\n";
                    break;
                default:
                    simpleMessage += "**[Ticket Scout] - Unknown**" + "\n";
                    break;
            }
        }
    }

    @Override
    protected void run()
    {
        switch (CHOICE.toLowerCase())
        {
            case "pts":
            case "ptsi":
                if (CHOICE.equalsIgnoreCase("ptsi") && !IMAGE_DISABLED)
                {
                    generateImage = true;
                }

                doSinglePull();
                break;
            case "ptm":
            case "ptmi":
                if (CHOICE.equalsIgnoreCase("ptmi") && !IMAGE_DISABLED)
                {
                    generateImage = true;
                }

                doMultiPull();
                break;
            default:
                return;
        }

        if (stopScout)
            return;

        displayAndSave();
    }
}
