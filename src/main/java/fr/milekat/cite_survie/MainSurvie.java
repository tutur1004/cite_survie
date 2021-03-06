package fr.milekat.cite_survie;

import fr.milekat.cite_survie.engine.AntiAFKTimer;
import fr.milekat.cite_survie.engine.DecoTimer;
import fr.milekat.cite_survie.event.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class MainSurvie extends JavaPlugin {
    private static MainSurvie mainSurvie;
    public static ArrayList<Player> isSafeSpawn = new ArrayList<>();
    public static HashMap<Player, Integer> playerCombat = new HashMap<>();
    public static HashMap<Zombie, Inventory> playerInventory = new HashMap<>();
    public static HashMap<Player, Location> lastPlayerLocation = new HashMap<>();
    public static HashMap<Player, Integer> timesPlayerAFK = new HashMap<>();
    private BukkitTask timerDeco;
    private BukkitTask timerAfk;

    @Override
    public void onEnable() {
        mainSurvie = this;
        // Events
        getServer().getPluginManager().registerEvents(new HammerMine(),this);
        getServer().getPluginManager().registerEvents(new ElytraSpeedDisable(),this);
        getServer().getPluginManager().registerEvents(new SpawnProtect(),this);
        getServer().getPluginManager().registerEvents(new AntiDecoCmb(),this);
        getServer().getPluginManager().registerEvents(new AntiAFK(),this);
        getServer().getPluginManager().registerEvents(new VillagersCustom(),this);
        getServer().getPluginManager().registerEvents(new PiglinTradeDisable(),this);
        getServer().getPluginManager().registerEvents(new SendToEnd(),this);
        getServer().getPluginManager().registerEvents(new RaidNerf(),this);
        // Engines
        timerDeco = new DecoTimer().runTask();
        timerAfk = new AntiAFKTimer().runTask();
    }

    @Override
    public void onDisable() {
        for (Zombie zombie: MainSurvie.playerInventory.keySet()) zombie.remove();
        timerDeco.cancel();
        timerAfk.cancel();
    }

    public static MainSurvie getMainSurvie() {
        return mainSurvie;
    }
}
