package io.github.spugn.Sargo.Listeners;

import io.github.spugn.Sargo.Managers.CommandManager;
import io.github.spugn.Sargo.Objects.WarningMessage;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class MessageListener
{
    private IDiscordClient client;

    public MessageListener(IDiscordClient discordClient)
    {
        client = discordClient;
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event)
    {
        try
        {
            new CommandManager(client, event).run();
        }
        catch (RateLimitException e)
        {
            event.getChannel().sendMessage(new WarningMessage("RATE LIMIT EXCEPTION", "Slow down with the messages!").get().build());
        }
        catch (DiscordException e)
        {
            event.getChannel().sendMessage(new WarningMessage("DISCORD EXCEPTION", "Something went wrong.").get().build());
        }
        catch (MissingPermissionsException e)
        {
            event.getChannel().sendMessage(new WarningMessage("MISSING PERMISSIONS EXCEPTION", "Not enough permissions.").get().build());
        }
    }
}
