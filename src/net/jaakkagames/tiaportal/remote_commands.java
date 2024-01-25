package net.jaakkagames.tiaportal;

//Riippuvuudet
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

import static net.jaakkagames.tiaportal.main.*;
import static org.bukkit.Bukkit.getServer;

public class remote_commands {
    public static void kasittely(String message)
    {
        getServer().broadcastMessage(ChatColor.YELLOW + "Remote command: " + message);


        //Etäkomentojen käsittely
        if(test_in_run)
        {
            if(message.equals("testdone"))
            {
                test_in_run = false;
                getServer().broadcastMessage(ChatColor.GREEN + "Connection working!");
            }
        }
            //Pilkotaan lause että saadaan tarkat tiedot
            String pilkottava = message;
            String[] etakomennot = pilkottava.split(" ");
            //getServer().broadcastMessage(ChatColor.GREEN + "Command length "+etakomennot.length);
            if(etakomennot.length>3) //Logiikan Lisäyskomennossa täytyy olla 4 arvoa
            {                        //logic added nimi ja ip
                if(etakomennot[0].equals("logic"))
                {
                    if(etakomennot[1].equals("added"))
                    {
                        logic_list.add(etakomennot[2]);
                        logic_ip.add(etakomennot[3]);
                        logic_connected.add(false);
                        ArrayList<String> tyhja_input_lista = new ArrayList<>();
                        logic_inputs.add(tyhja_input_lista);
                        ArrayList<String> tyhja_output_lista = new ArrayList<>();
                        logic_outputs.add(tyhja_output_lista);
                        getServer().broadcastMessage(ChatColor.GREEN + "Logic "+etakomennot[2]+" added success!");
                    }
                }

                if(etakomennot[0].equals("ioset"))
                {
                    String logic_name = etakomennot[1];
                    String io_name = etakomennot[2];
                    String io_value = etakomennot[3];
                    boolean value = false;
                    if(io_value.equals("true")){value=true;}
                    update_io_block(logic_name, io_name, value);
                }
            }

            // io added output logicnimi ionimi
            if(etakomennot.length>4) //IO Lisäyskomennossa täytyy olla 5 arvoa
            {                        //logic added nimi ja ip
                if(etakomennot[0].equals("io"))
                {
                    if(etakomennot[1].equals("added"))
                    {
                        int logiikan_indeksi = etsi_logiikka(etakomennot[3]);
                        if(etakomennot[2].equals("input"))
                        {
                            List<String> vanha_lista = logic_inputs.get(logiikan_indeksi);
                            vanha_lista.add(etakomennot[4]);
                            logic_inputs.set(logiikan_indeksi,vanha_lista);
                            getServer().broadcastMessage(ChatColor.GREEN + "Input io "+etakomennot[4]+" added to logic "+etakomennot[3]+".");
                        }
                        if(etakomennot[2].equals("output"))
                        {
                            List<String> vanha_lista = logic_outputs.get(logiikan_indeksi);
                            vanha_lista.add(etakomennot[4]);
                            logic_outputs.set(logiikan_indeksi,vanha_lista);
                            getServer().broadcastMessage(ChatColor.GREEN + "Output io "+etakomennot[4]+" added to logic "+etakomennot[3]+".");
                        }
                    }
                }
            }
    }
}