package io.github.spugn.Sargo.Listeners;

import io.github.spugn.Sargo.GUI.GUI;
import io.github.spugn.Sargo.XMLParsers.SettingsParser;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

public class ReadyListener
{
    private final IDiscordClient CLIENT;
    private String botName;

    public ReadyListener(IDiscordClient discordClient)
    {
        CLIENT = discordClient;
    }

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event)
    {
        SettingsParser settings = new SettingsParser();

        if (!settings.isNoGUI())
        {
            new GUI(CLIENT.getOurUser().getName() + "#" + CLIENT.getOurUser().getDiscriminator());
        }

        if (settings.getSecretWord().equalsIgnoreCase("Ushi"))
        {
            botName = "S'ushi";
        }
        else
        {
            botName = "S'argo";
        }


        if (settings.isUseMention())
        {
            CLIENT.changePlayingText(botName + " | @" + CLIENT.getOurUser().getName() + " help");
        }
        else
        {
            CLIENT.changePlayingText(botName + " | " + settings.getCommandPrefix() + "help");
        }
    }
}


