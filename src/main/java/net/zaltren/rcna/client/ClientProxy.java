package net.zaltren.rcna.client;

import net.zaltren.rcna.client.discord.DiscordRPCManager;
import net.zaltren.rcna.client.ingameinfo.InGameInfoRegistrar;
import net.zaltren.rcna.common.CommonProxy;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        DiscordRPCManager.init();
        Runtime.getRuntime().addShutdownHook(new Thread(DiscordRPCManager::shutdown));
        InGameInfoRegistrar.register();
    }

}
