package fr.milekat.cite_survie.event;

import fr.milekat.cite_survie.MainSurvie;
import net.craftersland.data.bridge.api.events.SyncCompleteEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpawnProtect implements Listener {

    @EventHandler (ignoreCancelled = true)
    public void onSpawnDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && MainSurvie.isSafeSpawn.contains((Player) event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onSpawnHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && MainSurvie.isSafeSpawn.contains((Player) event.getEntity())) {
            event.setCancelled(true);
            if (event.getDamager() instanceof Player) denyMsg(((Player) event.getDamager()));
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onSpawnShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player && MainSurvie.isSafeSpawn.contains((Player) event.getEntity())) {
            event.setCancelled(true);
            denyMsg(((Player) event.getEntity()));
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onSpawnPotionThrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof ThrownPotion)) return;
        Location ploc = event.getEntity().getLocation();
        if (ploc.getBlockX() < 50 && ploc.getBlockX() > -50 && ploc.getBlockZ() < 50 && ploc.getBlockZ() > -50) {
            event.setCancelled(true);
            denyMsg(((Player) event.getEntity()));
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onSpawnPotionSplash(PotionSplashEvent event) {
        Location ploc = event.getEntity().getLocation();
        if (ploc.getBlockX() < 50 && ploc.getBlockX() > -50 && ploc.getBlockZ() < 50 && ploc.getBlockZ() > -50) {
            event.setCancelled(true);
            denyMsg(((Player) event.getEntity()));
        }
    }

    @EventHandler
    public void onSpawnSet(PlayerMoveEvent event) {
        Location ploc = event.getPlayer().getLocation();
        if (MainSurvie.isSafeSpawn.contains(event.getPlayer())) {
            /* Sortie de la zone rouge */
            if (ploc.getBlockX() > 50 || ploc.getBlockX() < -50 || ploc.getBlockZ() > 50 || ploc.getBlockZ() < -50) {
                MainSurvie.isSafeSpawn.remove(event.getPlayer());
                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cVous n'êtes plus protégé."));
            }
        } else {
            /* Entrée dans la zone verte */
            if ((ploc.getBlockX() < 25 && ploc.getBlockX() > -25 && ploc.getBlockZ() < 25 && ploc.getBlockZ() > -25)) {
                MainSurvie.isSafeSpawn.add(event.getPlayer());
                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aVous êtes désormais safe."));
            }
        }
    }

    @EventHandler
    public void onJoinProtect(PlayerJoinEvent event) {
        MainSurvie.isSafeSpawn.add(event.getPlayer());
    }

    @EventHandler (ignoreCancelled = true)
    public void onSpawnPlace(BlockPlaceEvent event) {
        Location ploc = event.getPlayer().getLocation();
        if (ploc.getBlockX() < 50 && ploc.getBlockX() > -50 && ploc.getBlockZ() < 50 && ploc.getBlockZ() > -50) {
            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                event.setCancelled(true);
                denyMsg(event.getPlayer());
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onSpawnBreak(BlockBreakEvent event) {
        Location ploc = event.getPlayer().getLocation();
        if (ploc.getBlockX() < 50 && ploc.getBlockX() > -50 && ploc.getBlockZ() < 50 && ploc.getBlockZ() > -50) {
            if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                event.setCancelled(true);
                denyMsg(event.getPlayer());
            }
        }
    }

    private void denyMsg(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cVous êtes actuellement protegé."));
    }
}
