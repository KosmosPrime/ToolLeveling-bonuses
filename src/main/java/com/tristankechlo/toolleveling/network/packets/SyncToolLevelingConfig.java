package com.tristankechlo.toolleveling.network.packets;

import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class SyncToolLevelingConfig {

	public final String identifier;
	public final JsonObject json;

	public SyncToolLevelingConfig(String identifier, JsonObject json) {
		this.identifier = identifier;
		this.json = json;
	}

	public static void encode(SyncToolLevelingConfig msg, FriendlyByteBuf buffer) {
		buffer.writeUtf(msg.identifier);
		buffer.writeUtf(new Gson().toJson(msg.json));
	}

	public static SyncToolLevelingConfig decode(FriendlyByteBuf buffer) {
		String identifier = buffer.readUtf();
		JsonObject json = JsonParser.parseString(buffer.readUtf()).getAsJsonObject();
		return new SyncToolLevelingConfig(identifier, json);
	}

	public static void handle(SyncToolLevelingConfig msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientSyncToolLevelingConfig.handle(msg, context));
		});
		context.get().setPacketHandled(true);
	}
}
