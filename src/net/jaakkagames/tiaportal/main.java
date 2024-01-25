package net.jaakkagames.tiaportal;

//Riippuvuudet
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getServer;

//Pääohjelma
public class main extends JavaPlugin
{
    private static main instance;

    public static main getInstance() {
        return instance;
    }

    //Muuttujat
    public static List<List<String>> logic_inputs = new ArrayList<>();
    public static List<List<String>> logic_outputs = new ArrayList<>();
    public static ArrayList<String> logic_list = new ArrayList<>();
    public static ArrayList<String> logic_ip = new ArrayList<>();
    public static ArrayList<Boolean> logic_connected = new ArrayList<>();

    public static ArrayList<Block> io_block_locations = new ArrayList<>();
    public static ArrayList<Boolean> io_block_is_input = new ArrayList<>();
    public static ArrayList<Boolean> io_block_values = new ArrayList<>();
    public static ArrayList<String> io_block_logic_and_ios = new ArrayList<>();
    public static Block testblock;
    public static int blockset = 0;
    public static WebSocketClient webSocketClient;
    public static Boolean connected_to_nodejs = false;

    public static Boolean test_in_run = false;
    //Apufuktiot
    public static int etsi_logiikka(String etsittavaArvo)
    {
        int i = 0;
        while(i < logic_list.size())
        {
            if (logic_list.get(i).equals(etsittavaArvo))
            {
                return i;
            }
            i++;
        }

        return -1;
    }


    public static void connectToNode(Logger logger, Server srv)
    {
        try
        {
            webSocketClient = new WebSocketClient(new URI("ws://localhost:3000"))
            {
                @Override
                public void onOpen(ServerHandshake handshakedata)
                {
                    logger.info("Connected to nodejs helper service");
                    srv.broadcastMessage(ChatColor.GREEN + "Connected to nodejs.");
                    connected_to_nodejs = true;
                }

                @Override
                public void onMessage(String message)
                {
                    remote_commands.kasittely(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote)
                {
                    logger.info("Connection closed to nodejs helper service.");
                    connected_to_nodejs = false;
                    srv.broadcastMessage(ChatColor.RED + "Connection lost to nodejs service");
                }

                @Override
                public void onError(Exception ex)
                {
                    logger.warning("Nodejs helper & WebSocket error: " + ex.getMessage());
                }


            };

            webSocketClient.connect();
        } catch (URISyntaxException e)
        {
            //getLogger().warning("WebSocket connection URI is invalid: " + e.getMessage());
        }
    }
    //Pluginin käynistymis asetukset
    @Override
    public void onEnable()
    {
        instance = this;
        //Asetetaan komentojen ja tapahtumien tarkkailijat
        getServer().getPluginManager().registerEvents(new events(), this);
        getCommand("tiaportal").setExecutor(new commands());
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[TiaPortal] Enabled!");
        connectToNode(getLogger(),getServer());
    }
    //Pluginin sammutus asetukset
    @Override
    public void onDisable()
    {
        instance = null;
        if (webSocketClient != null)
        {
            webSocketClient.close();
        }
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[TiaPortal] Disabled!");
    }

    public static void update_io_block(String logic_name, String io_name, Boolean value) {
        if (!io_block_logic_and_ios.isEmpty()) {
            int i = 0;
            while (i < io_block_logic_and_ios.size()) {
                if (io_block_logic_and_ios.get(i).equals(logic_name + " " + io_name)) {
                    Block muutettavan_palikan_sijainti = io_block_locations.get(i);
                    io_block_values.set(i,value);
                    Bukkit.getScheduler().runTask(main.getInstance(), () -> {
                        World world = muutettavan_palikan_sijainti.getWorld();
                        Block palikka = world.getBlockAt(muutettavan_palikan_sijainti.getLocation());
                        if (value) {
                            palikka.setType(Material.REDSTONE_BLOCK);
                        } else {
                            palikka.setType(Material.OBSIDIAN);
                        }
                    });
                }
                i++;
            }
        }
    }

}
