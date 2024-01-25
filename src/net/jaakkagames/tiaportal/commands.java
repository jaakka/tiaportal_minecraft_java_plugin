package net.jaakkagames.tiaportal;

import com.sun.tools.javac.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.jaakkagames.tiaportal.main.*;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class commands implements CommandExecutor {

    @java.lang.Override
    public boolean onCommand(CommandSender sender, Command cmd, java.lang.String label, java.lang.String[] args) {

        if(!(sender instanceof Player)){
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("tiaportal"))
        {
            if(args.length<1) {
                player.sendMessage(ChatColor.YELLOW + "List of tiaportal plugin commands");
                player.sendMessage(ChatColor.GREEN + "/tiaportal test "+ChatColor.YELLOW +"Test connection to nodejs");
                player.sendMessage(ChatColor.YELLOW + "Logic commands:");
                player.sendMessage(ChatColor.GREEN + "/tiaportal logic add "+ChatColor.WHITE +" <logic alias> <ip>"+ChatColor.YELLOW +" Create logic");
                player.sendMessage(ChatColor.GREEN + "/tiaportal logic list "+ChatColor.YELLOW +" List all added logics.");
                player.sendMessage(ChatColor.GREEN + "/tiaportal logic tools "+ChatColor.YELLOW +" Get tools for logic blocks.");
                player.sendMessage(ChatColor.GREEN + "/tiaportal logic test "+ChatColor.WHITE +"<logic alias> "+ChatColor.YELLOW +" Test logic connection");
                player.sendMessage(ChatColor.YELLOW + "IO commands:");
                player.sendMessage(ChatColor.GREEN + "/tiaportal io add output "+ChatColor.WHITE +"<logic alias> <io> "+ChatColor.YELLOW +" Add output from logic");
                player.sendMessage(ChatColor.GREEN + "/tiaportal io add input "+ChatColor.WHITE +"<logic alias> <io> "+ChatColor.YELLOW +" Add input from logic");
                player.sendMessage(ChatColor.GREEN + "/tiaportal io list "+ChatColor.WHITE +"<logic alias> "+ChatColor.YELLOW +" List all logic variables");
                player.sendMessage(ChatColor.GREEN + "/tiaportal io get blocks "+ChatColor.WHITE +"<logic alias> "+ChatColor.YELLOW +" Get logic io blocks.");
                player.sendMessage(ChatColor.YELLOW + "/tiaportal io get input "+ChatColor.WHITE +"<logic alias> <io> "+ChatColor.YELLOW +" Get input block.");
                player.sendMessage(ChatColor.YELLOW + "/tiaportal io get output "+ChatColor.WHITE +"<logic alias> <io> "+ChatColor.YELLOW +" Get output block");
            }
                else
            {
                  if(args[0].equalsIgnoreCase("reconnect"))
                  {
                      connectToNode(getLogger(),getServer());
                      player.sendMessage(ChatColor.YELLOW + "Reconnecting...");
                  }
                  else if(args[0].equalsIgnoreCase("logic") && args[1].equalsIgnoreCase("add")) {
                      if(args.length<6)
                      {
                          player.sendMessage(ChatColor.RED + "You need add name, ip, user and password for new logic.");
                      }
                      else {
                          player.sendMessage(ChatColor.YELLOW + "Trying add logic " + args[2] + " at "+args[3]+"...");
                          webSocketClient.send("logic add " + args[2] +" "+ args[3]+" "+ args[4]+" "+ args[5]);
                      }
                  }
                  else if(args[0].equalsIgnoreCase("logic") && args[1].equalsIgnoreCase("list"))
                  {
                      player.sendMessage(ChatColor.YELLOW + "-=| Minecraft logic list |=-");
                      if(logic_list.isEmpty())
                      {
                          player.sendMessage(ChatColor.RED + "No logics added!");
                      }
                        else
                      {
                          int i = 0;
                          String lista = "";
                          while (i < logic_list.size()) {
                              if (lista.isEmpty()) {
                                  lista = logic_list.get(i);
                              } else {
                                  lista = lista + ", " + logic_list.get(i);
                              }
                              i++;
                          }
                          player.sendMessage(ChatColor.GREEN + lista);
                      }
                      player.sendMessage(ChatColor.YELLOW + "-=| ==================================== |=-");
                  }
                  else if(args[0].equalsIgnoreCase("logic") && args[1].equalsIgnoreCase("test"))
                  {
                      player.sendMessage(ChatColor.YELLOW + "Testing minecraft logic "+args[2]+ " connection to real logic ");

                      webSocketClient.send("logic test " + args[2]);
                  }

                  else if(args[0].equalsIgnoreCase("tools"))
                  {
                      player.sendMessage(ChatColor.GREEN + "You got logic tools.");
                      ItemStack item = new ItemStack(Material.STICK, 1);
                      ItemMeta meta = item.getItemMeta();
                      meta.setDisplayName("§dLogic §ftool");
                      List<String> lore = new ArrayList<>();
                      lore.add("§7Get logic io blocks");
                      lore.add("§7without data lost.");
                      meta.setCustomModelData(1);
                      meta.setLore(lore);
                      item.setItemMeta(meta);
                      player.getInventory().addItem(item);

                      ItemStack item2 = new ItemStack(Material.STICK, 1);
                      ItemMeta meta2 = item2.getItemMeta();
                      meta2.setDisplayName("§bFluke §fmeter");
                      List<String> lore2 = new ArrayList<>();
                      lore2.add("§7Get logic io");
                      lore2.add("§7block info.");
                      meta2.setCustomModelData(2);
                      meta2.setLore(lore2);
                      item.setItemMeta(meta2);
                      player.getInventory().addItem(item);
                  }

                  else if(args[0].equalsIgnoreCase("test"))
                  {
                      if(!test_in_run)
                      {
                          if(connected_to_nodejs)
                          {
                              test_in_run = true;
                              getServer().broadcastMessage(ChatColor.YELLOW + "Sending test message to tiaportal helper nodejs application.");
                              webSocketClient.send("test");
                          }
                            else
                          {
                              player.sendMessage(ChatColor.RED+ "Cannot send connection test message, because connection is closed.");
                          }
                      }
                        else
                      {
                          player.sendMessage(ChatColor.RED+ "Test is already running.");
                      }
                  }

                  else if(args[0].equalsIgnoreCase("io") && args[1].equalsIgnoreCase("testoutput"))
                  {
                        String logicname = args[2];
                        String ioname = args[3];
                        String iovalue = args[4];
                      webSocketClient.send("ioupdate_beta "+logicname+" "+ioname+" "+iovalue);
                  }

                  else if(args[0].equalsIgnoreCase("io") && args[1].equalsIgnoreCase("list"))
                  {
                      if(args.length<3)
                      {
                          player.sendMessage(ChatColor.YELLOW + "You need give more information.");
                      }
                      else
                      {
                          int logiikan_indeksi = etsi_logiikka(args[2]);
                          if(logiikan_indeksi==-1)
                          {
                              player.sendMessage(ChatColor.RED + "Logic not found.");
                          }
                            else
                          {
                              player.sendMessage(ChatColor.YELLOW + "-=| Minecraft logic " + args[2] + " io list |=-");
                              String inputs_str = "";
                              String outputs_str = "";
                              List<String> logic_input_list = logic_inputs.get(logiikan_indeksi);
                              List<String> logic_output_list = logic_outputs.get(logiikan_indeksi);
                              int i = 0;

                              while(i < logic_input_list.size())
                              {
                                  if(inputs_str.equals(""))
                                  {
                                      inputs_str = logic_input_list.get(i);
                                  }
                                    else
                                  {
                                      inputs_str = inputs_str +", "+ logic_input_list.get(i);
                                  }
                                  i++;
                              }
                              i=0;
                              while(i < logic_output_list.size())
                              {
                                  if(outputs_str.equals(""))
                                  {
                                      outputs_str = logic_output_list.get(i);
                                  }
                                  else
                                  {
                                      outputs_str = outputs_str +", "+ logic_output_list.get(i);
                                  }
                                  i++;
                              }

                              if(Objects.equals(inputs_str, "")){inputs_str="No inputs added.";}
                              if(Objects.equals(outputs_str, "")){outputs_str="No outputs added.";}
                              player.sendMessage(ChatColor.WHITE + "Inputs: " + ChatColor.GREEN + " " +inputs_str);
                              player.sendMessage(ChatColor.WHITE + "Outputs: " + ChatColor.GREEN + " " +outputs_str);
                              player.sendMessage(ChatColor.YELLOW + "-=| ==================================== |=-");
                          }
                      }

                  }
                  else if(args[0].equalsIgnoreCase("io") && args[1].equalsIgnoreCase("get")) {
                      if (args.length < 5) {

                          if (args[2].equalsIgnoreCase("blocks")) {
                              String logiikan_nimi = args[3];
                              int logiikan_id = etsi_logiikka(logiikan_nimi);
                              if (logiikan_id != -1) {
                                  List<String> vanha_lista = logic_outputs.get(logiikan_id);
                                  List<String> vanha_lista_two = logic_inputs.get(logiikan_id);
                                  int i = 0;
                                  while (i < vanha_lista.size()) {
                                      ItemStack item = new ItemStack(Material.OBSIDIAN, 1);
                                      ItemMeta meta = item.getItemMeta();
                                      meta.setDisplayName("§bOutput §fblock");
                                      List<String> lore = new ArrayList<>();
                                      lore.add("§7For " + logiikan_nimi + " logic");
                                      lore.add("§7to " + vanha_lista.get(i) + " io.");
                                      meta.setLore(lore);
                                      item.setItemMeta(meta);
                                      player.getInventory().addItem(item);
                                      i++;
                                  }
                                  i = 0;
                                  while (i < vanha_lista_two.size()) {
                                      ItemStack item = new ItemStack(Material.REDSTONE_LAMP, 1);
                                      ItemMeta meta = item.getItemMeta();
                                      meta.setDisplayName("§aInput §fblock");
                                      List<String> lore = new ArrayList<>();
                                      lore.add("§7For " + logiikan_nimi + " logic");
                                      lore.add("§7to " + vanha_lista_two.get(i) + " io.");
                                      meta.setLore(lore);
                                      item.setItemMeta(meta);
                                      player.getInventory().addItem(item);
                                      i++;
                                  }
                                  player.sendMessage(ChatColor.GREEN + "You got logic " + logiikan_nimi + " io blocks.");
                              } else {
                                  player.sendMessage(ChatColor.RED + "Logic " + args[3] + " not found!");
                              }
                          }else {
                              player.sendMessage(ChatColor.YELLOW + "You need give more information.");
                          }
                      } else {
                          if (args[2].equalsIgnoreCase("input")) {
                              ItemStack item = new ItemStack(Material.REDSTONE_LAMP, 1);
                              ItemMeta meta = item.getItemMeta();
                              meta.setDisplayName("§aInput §fblock");

                              List<String> lore = new ArrayList<>();
                              lore.add("§7For " + args[3] + " logic");
                              lore.add("§7to " + args[4] + " io.");
                              meta.setLore(lore);
                              item.setItemMeta(meta);
                              player.getInventory().addItem(item);
                              player.sendMessage(ChatColor.GREEN + "You got input block.");
                          } else if (args[2].equalsIgnoreCase("output")) {
                              ItemStack item = new ItemStack(Material.OBSIDIAN, 1);
                              ItemMeta meta = item.getItemMeta();
                              meta.setDisplayName("§bOutput §fblock");
                              // ei hyötyä meta.setCustomModelData(1);
                              List<String> lore = new ArrayList<>();
                              lore.add("§7For " + args[3] + " logic");
                              lore.add("§7to " + args[4] + " io.");
                              meta.setLore(lore);
                              item.setItemMeta(meta);
                              player.getInventory().addItem(item);
                              player.sendMessage(ChatColor.GREEN + "You got output block.");
                          }

                      }

                  }

/*
                      else if (args[2].equalsIgnoreCase("blocks"))
                  {
                      String logiikan_nimi = args[3];
                      int logiikan_id = etsi_logiikka(logiikan_nimi);
                      if(logiikan_id != -1)
                      {
                          List<String> vanha_lista = logic_outputs.get(logiikan_id);
                          List<String> vanha_lista_two = logic_inputs.get(logiikan_id);
                          int i = 0;
                          while(i<vanha_lista.size())
                          {
                              player.sendMessage(ChatColor.YELLOW +"kierros");
                              ItemStack item = new ItemStack(Material.OBSIDIAN,1);
                              ItemMeta meta = item.getItemMeta();
                              meta.setDisplayName("§bOutput §fblock");
                              List<String> lore = new ArrayList<>();
                              lore.add("§7For " + logiikan_nimi +" logic");
                              lore.add("§7to "+ vanha_lista.get(i) +" io.");
                              meta.setLore(lore);
                              item.setItemMeta(meta);
                              player.getInventory().addItem(item);
                              i++;
                          }
                          i = 0;
                          while(i<vanha_lista_two.size())
                          {
                              player.sendMessage(ChatColor.RED +"kierros");
                              ItemStack item = new ItemStack(Material.REDSTONE_LAMP, 1);
                              ItemMeta meta = item.getItemMeta();
                              meta.setDisplayName("§aInput §fblock");
                              List<String> lore = new ArrayList<>();
                              lore.add("§7For " + logiikan_nimi + " logic");
                              lore.add("§7to " + vanha_lista_two.get(i) + " io.");
                              meta.setLore(lore);
                              item.setItemMeta(meta);
                              player.getInventory().addItem(item);
                              i++;
                          }
                          player.sendMessage(ChatColor.GREEN + "You got logic "+logiikan_nimi+" io blocks.");
                      }
                      else
                      {
                          player.sendMessage(ChatColor.RED + "Logic "+args[3]+" not found!");
                      }
                  }
*/
                  else if(args[0].equalsIgnoreCase("io") && args[1].equalsIgnoreCase("add") && args[2].equalsIgnoreCase("output"))
                  {

                      if(args.length<5)
                      {
                          player.sendMessage(ChatColor.YELLOW + "You need give more information.");
                      }
                        else
                      {
                          int logiikan_indeksi = etsi_logiikka(args[3]);
                          if(logiikan_indeksi==-1)
                          {
                              player.sendMessage(ChatColor.YELLOW + "Logic "+args[3]+" not found.");
                          }
                            else
                          {
                              webSocketClient.send("io add output " + args[3] +" "+ args[4]);
                          }
                      }
                  }
                  else if(args[0].equalsIgnoreCase("io") && args[1].equalsIgnoreCase("add") && args[2].equalsIgnoreCase("input"))
                  {

                      if(args.length<5)
                      {
                          player.sendMessage(ChatColor.YELLOW + "You need give more information.");
                      }
                      else
                      {
                          int logiikan_indeksi = etsi_logiikka(args[3]);
                          if(logiikan_indeksi==-1)
                          {
                              player.sendMessage(ChatColor.YELLOW + "Logic "+args[3]+" not found.");
                          }
                          else
                          {
                              webSocketClient.send("io add input " + args[3] +" "+ args[4]);
                          }
                      }
                  }
                  else if(args[0].equalsIgnoreCase("settestblock"))
                  {
                      blockset = 1;
                      player.sendMessage(ChatColor.YELLOW + "Please broke io block.");
                  }
                  else if(args[0].equalsIgnoreCase("testblock"))
                  {
                      if(blockset == 2)
                      {
                        if(args[1].equalsIgnoreCase("1"))
                        {
                            //8 palikkaa sopiva liukuvirtaan
                            testblock.setType(Material.REDSTONE_BLOCK);
                        }
                        else
                        {
                            testblock.setType(Material.OBSIDIAN);
                        }
                      }
                        else
                      {
                          player.sendMessage(ChatColor.YELLOW + "Please set block first. with /tia settestblock");
                      }
                  }
                  else
                  {
                      player.sendMessage(ChatColor.YELLOW + "ERROR");
                  }
            }
        }

        return true;
    }
}