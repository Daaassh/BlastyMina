/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolManager
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.wrappers.EnumWrappers$Particle
 *  org.bukkit.Particle
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 */
package org.me.blastymina.utils.particles;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.me.blastymina.BlastyMina;

public class ParticleManager {
    public Particle particle;
    public Player player;
    public ProtocolManager protocolManager = BlastyMina.getManager();
    public Block block;

    public ParticleManager(Player p, Block block, Particle particle) {
        this.particle = particle;
        this.player = p;
        this.block = block;
        this.sendParticlePacket();
    }

    public void sendParticlePacket() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);
        packet.getParticles().write(0, EnumWrappers.Particle.valueOf((String)this.particle.name()));
        packet.getFloat().write(0, (float) block.getX());
        packet.getFloat().write(1, (float) block.getY());
        packet.getFloat().write(2, (float) block.getZ());
        packet.getFloat().write(3, 0.0f);
        packet.getFloat().write(4, 0.75f);
        packet.getFloat().write(5, 0.0f);
        packet.getFloat().write(6, 1.0f);
        packet.getIntegers().write(0, 5);
        try {
            this.protocolManager.sendServerPacket(this.player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

