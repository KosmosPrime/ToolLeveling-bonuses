package com.tristankechlo.toolleveling;

import com.tristankechlo.toolleveling.client.ToolLevelingTableRenderer;
import com.tristankechlo.toolleveling.client.ToolLevelingTableScreen;
import com.tristankechlo.toolleveling.network.ClientNetworkHelper;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class FabricToolLevelingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientNetworkHelper.setup(); // register client related network stuff

        MenuScreens.register(ToolLeveling.TLT_MENU.get(), ToolLevelingTableScreen::new);
        BlockEntityRenderers.register(ToolLeveling.TLT_BLOCK_ENTITY.get(), ToolLevelingTableRenderer::new);
    }

}
