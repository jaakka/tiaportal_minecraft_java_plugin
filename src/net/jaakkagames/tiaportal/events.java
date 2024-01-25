package net.jaakkagames.tiaportal;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

import static net.jaakkagames.tiaportal.main.*;
import static org.bukkit.Bukkit.getLogger;


public class events implements Listener
{
    String last_data_send = "";
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        if(blockset == 1)
        {
            int x = event.getBlock().getLocation().getBlockX();
            int y = event.getBlock().getLocation().getBlockY();
            int z = event.getBlock().getLocation().getBlockZ();
            World world = event.getBlock().getWorld();
            Location location = new Location(world, x, y, z);
            testblock = world.getBlockAt(location); //huom Block on tyyppi
            Player player = event.getPlayer();
            player.sendMessage(ChatColor.GREEN + "Test block set!");
            blockset=2;
            event.setCancelled(true);
        }
        int i = 0;
        while(i<io_block_locations.size())
        {
            if(io_block_locations.get(i).getLocation().equals(event.getBlock().getLocation()))
            {
                io_block_locations.remove(i);
                io_block_is_input.remove(i);
                io_block_values.remove(i);
                io_block_logic_and_ios.remove(i);
                event.getPlayer().sendMessage("Io block removed.");
                break;
            }
            i++;
        }
    }

    @EventHandler
    public void JoinEvent(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if(!connected_to_nodejs)
        {
            player.sendMessage(ChatColor.RED + "[TiaPortal] Warning, connection to nodeJs service failed!");
        }
        else
        {
            player.sendMessage(ChatColor.GREEN + "[TiaPortal] Connected to nodejs service.");
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();

        if (itemInHand.getType() == Material.STICK && itemInHand.hasItemMeta() &&
                (itemInHand.getItemMeta().getCustomModelData() == 1 || itemInHand.getItemMeta().getCustomModelData() == 2
                || itemInHand.getItemMeta().getCustomModelData() == 3 || itemInHand.getItemMeta().getCustomModelData() == 4)) {

                int part_type = 0;
                int i = 0;
                String io_name = "";
                String logic_name = "";
                String io_txt = "";
                String io_output = "";
                Boolean are_active = false;
            while(i<io_block_locations.size())
                {
                    if(io_block_locations.get(i).getLocation().equals(block.getLocation()))
                    {
                        String logiikkajaio = io_block_logic_and_ios.get(i);
                        String[] sarjetty = logiikkajaio.split(" ");
                        logic_name = sarjetty[0];
                        io_name = sarjetty[1];
                        are_active = io_block_values.get(i);
                        if(are_active)
                        {
                            io_output = ChatColor.GREEN + "ON";
                        }
                            else
                        {
                            io_output = ChatColor.RED + "OFF";
                        }
                        if(io_block_is_input.get(i))
                        {
                            io_txt ="input";
                            part_type = 1;
                        }
                            else
                        {
                            io_txt="output";
                            part_type = 2;
                        }
                            break;
                    }
                    i++;
                }

            if(part_type == 0 && (action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK ) &&
                    (!last_data_send.equals(block.getLocation().getBlockX() + "_" + block.getLocation().getBlockY() + "_" + block.getLocation().getBlockZ() + "_" + are_active)))
            {
                last_data_send = block.getLocation().getBlockX()+"_"+block.getLocation().getBlockY()+"_"+block.getLocation().getBlockZ()+"_"+are_active;
                player.sendMessage("not logic io block.");

                if (itemInHand.hasItemMeta()) {
                    if (itemInHand.getItemMeta().getCustomModelData() == 2 || itemInHand.getItemMeta().getCustomModelData() == 3 || itemInHand.getItemMeta().getCustomModelData() == 4)
                    {
                        ItemMeta meta = itemInHand.getItemMeta();
                        meta.setCustomModelData(2);
                        itemInHand.setItemMeta(meta);
                        player.getInventory().setItemInMainHand(itemInHand);
                    }
                }
                event.setCancelled(true);
            }

            if (action == Action.RIGHT_CLICK_BLOCK && part_type != 0 && (itemInHand.getItemMeta().getCustomModelData() == 2
                    || itemInHand.getItemMeta().getCustomModelData() == 3 || itemInHand.getItemMeta().getCustomModelData() == 4)
            && (!last_data_send.equals(block.getLocation().getBlockX() + "_" + block.getLocation().getBlockY() + "_" + block.getLocation().getBlockZ() + "_" + are_active))) {

                last_data_send = block.getLocation().getBlockX()+"_"+block.getLocation().getBlockY()+"_"+block.getLocation().getBlockZ()+"_"+are_active;
                player.sendMessage(ChatColor.GREEN + "This is logic "+logic_name+", io "+io_txt+" "+io_name+" , value is "+io_output);

                if(are_active == true) {
                    if (itemInHand.hasItemMeta()) {
                        ItemMeta meta = itemInHand.getItemMeta();
                        meta.setCustomModelData(4);
                        itemInHand.setItemMeta(meta);
                        player.getInventory().setItemInMainHand(itemInHand);
                    }
                }else{
                    if (itemInHand.hasItemMeta()) {
                        ItemMeta meta = itemInHand.getItemMeta();
                        meta.setCustomModelData(3);
                        itemInHand.setItemMeta(meta);
                        player.getInventory().setItemInMainHand(itemInHand);
                    }
                }
            }
            if (action == Action.LEFT_CLICK_BLOCK && part_type != 0 && itemInHand.getItemMeta().getCustomModelData() == 1) {
                player.sendMessage(ChatColor.GREEN + "You got logic "+logic_name+" , "+io_txt+" for io "+io_name+" block.");
                if(part_type==1)
                {
                    ItemStack item = new ItemStack(Material.REDSTONE_LAMP, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§aInput §fblock");
                    List<String> lore = new ArrayList<>();
                    lore.add("§7For " + logic_name + " logic");
                    lore.add("§7to " + io_name + " io.");
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    player.getInventory().addItem(item);
                }
                    else
                {
                    ItemStack item = new ItemStack(Material.OBSIDIAN, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§bOutput §fblock");
                    List<String> lore = new ArrayList<>();
                    lore.add("§7For " + logic_name + " logic");
                    lore.add("§7to " + io_name + " io.");
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    player.getInventory().addItem(item);
                }
            }

        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block placedBlock = event.getBlockPlaced();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if ((itemInHand.getType() == Material.OBSIDIAN || itemInHand.getType() == Material.REDSTONE_LAMP) && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
            List<String> lore = itemInHand.getItemMeta().getLore();
            String itemname = itemInHand.getItemMeta().getDisplayName();
            if(itemname.equals("§bOutput §fblock"))
            {
                String[] lore_rivi = lore.get(0).split(" ");
                String[] lore_rivi2= lore.get(1).split(" ");
                player.sendMessage("Added logic "+lore_rivi[1]+" output block for io "+lore_rivi2[1]);
                io_block_locations.add(placedBlock);
                io_block_is_input.add(false);
                io_block_values.add(false); //tähän voisi tehdä update pyynnön
                io_block_logic_and_ios.add(lore_rivi[1]+" "+lore_rivi2[1]);
            }
            else if(itemname.equals("§aInput §fblock"))
            {
                String[] lore_rivi = lore.get(0).split(" ");
                String[] lore_rivi2= lore.get(1).split(" ");
                player.sendMessage("Added logic "+lore_rivi[1]+" input block for io "+lore_rivi2[1]);
                io_block_locations.add(placedBlock);
                io_block_is_input.add(true);
                io_block_values.add(false); //tähän voisi tehdä update pyynnön
                io_block_logic_and_ios.add(lore_rivi[1]+" "+lore_rivi2[1]);
            }
        }
    }

    @EventHandler
    public void onRedstoneChange(BlockRedstoneEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.REDSTONE_LAMP)
        {
            boolean lampOn = event.getNewCurrent() > 0; // true, jos lamppu on päällä
            int i = 0;
            while(i < io_block_locations.size())
            {
                if(io_block_locations.get(i).getLocation().equals(block.getLocation()) && io_block_is_input.get(i))
                {
                    io_block_values.set(i,lampOn);
                    String[] logic_and_io = io_block_logic_and_ios.get(i).split(" ");

                    if (lampOn)
                    {
                        webSocketClient.send("ioset " + logic_and_io[0] +" "+ logic_and_io[1]+" true");
                    }
                    else
                    {
                        webSocketClient.send("ioset " + logic_and_io[0] +" "+ logic_and_io[1]+" false");
                    }
                    break;
                }
                i++;
            }
        }
    }

}
