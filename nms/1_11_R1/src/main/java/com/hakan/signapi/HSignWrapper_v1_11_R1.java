package com.hakan.signapi;

import com.hakan.signapi.api.HSign;
import com.hakan.signapi.api.HSignAPI;
import com.hakan.signapi.api.HSignWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.block.CraftSign;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class HSignWrapper_v1_11_R1 extends HSignWrapper {

    public HSignWrapper_v1_11_R1(HSignAPI hSignAPI) {
        super(hSignAPI);
    }

    @Override
    public void open(Player player, HSign hSign, Consumer<String[]> signLinesConsumer) {

        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        BlockPosition blockPosition = new BlockPosition(player.getLocation().getBlockX(), 1, player.getLocation().getBlockZ());

        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld) player.getWorld()).getHandle(), blockPosition);
        packet.block = CraftMagicNumbers.getBlock(hSign.getSignType()).getBlockData();
        playerConnection.sendPacket(packet);

        IChatBaseComponent[] components = CraftSign.sanitizeLines(hSign.getLines());
        TileEntitySign sign = new TileEntitySign();
        sign.setPosition(new BlockPosition(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()));
        System.arraycopy(components, 0, sign.lines, 0, sign.lines.length);
        playerConnection.sendPacket(sign.getUpdatePacket());

        PacketPlayOutOpenSignEditor outOpenSignEditor = new PacketPlayOutOpenSignEditor(blockPosition);
        playerConnection.sendPacket(outOpenSignEditor);

        this.signCallback.put(blockPosition + player.getName(), signLinesConsumer);
    }

    @Override
    public void startListener(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
                if (packet instanceof PacketPlayInUpdateSign) {
                    PacketPlayInUpdateSign packetSign = (PacketPlayInUpdateSign) packet;

                    Bukkit.getScheduler().runTask(plugin, () -> {
                        BlockPosition position = packetSign.a();
                        String id = position + player.getName();

                        if (signCallback.containsKey(id)) {
                            Block block = player.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ());
                            block.setType(block.getType());

                            int m = 0;
                            String[] lines = new String[packetSign.b().length];
                            for (String line : packetSign.b()) {
                                lines[m] = line;
                                m++;
                            }

                            signCallback.remove(id).accept(lines);
                        }
                    });
                }
                super.channelRead(ctx, packet);
            }
        };
        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", "sign_api_pipeline_channel_" + player.getName(), channelDuplexHandler);
    }

    @Override
    public void stopListener(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove("sign_api_pipeline_channel_" + player.getName());
            return null;
        });
    }
}
