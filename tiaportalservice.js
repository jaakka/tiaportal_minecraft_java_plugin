const puppeteer = require('puppeteer');
const WebSocket = require('ws');
let uusi_sivu;
let logic_list = [];
let active_commands = false;
let monitoringInterval;
// [Logiikan_nimi , Logiikan_selain, Logiikan_io_lista, io_values, io_v,io_type]
console.log("Tiaportal communicate service.");
console.log("Waiting minecraft server..");
const wss = new WebSocket.Server({ port: 3000 });

function hanki_logic_id(nimi)
{
  let i = 0;
  while(i < logic_list.length)
  {
    if(logic_list[i][0] === nimi)
    {
      return i;
    }
    i++;
  }
  return -1;
}

function hanki_io_id(index,nimi)
{
  let i = 0;
  while(i < logic_list[index][2].length)
  {
    if(logic_list[index][2][i] === nimi)
    {
      return i;
    }
    i++;
  }
  return -1;
}


(async () => 
{
  const browser = await puppeteer.launch({ headless: false }); //{ headless: false tai piilossa "new" }

  wss.on('connection', (ws) => 
  {
    console.log('Minecraft server connected.');

    const interval = setInterval(async () => 
              {
                  //dynamic_contentt1  
                  let total_logics = logic_list.length;
                  let i = 0;
                  if(total_logics > 0)
                  {
                    while(i < total_logics)
                    {
                        let io_types = logic_list[i][5];
                        let a = 0;
                        while(a < io_types.length)
                        {
                          if(io_types[a] == false)
                          {
                            //Tämä etsii ainoastaan outputit koska emme tee input tiedoilla mitään
                            let logiikan_selain = logic_list[i][1];   //selain
                            let io_nimi = logic_list[i][2][a];        //ion nimi
                            let io_vanha_arvo = logic_list[i][3][a];  //ion arvo joka oli noden muistissa
                            let io_etsittava_v = logic_list[i][4][a]; //v arvo joka on sivulla riveillä omat
                            let elementti = '#dynamic_contentt'+String(io_etsittava_v);
                            await logiikan_selain.waitForSelector(elementti);
                            let arvo = await logiikan_selain.$eval(elementti, arvo => arvo.innerText.trim());
                            //console.log("arvo:"+String(arvo));

                            if((io_vanha_arvo == true && arvo.includes("TRUE")) || (io_vanha_arvo == false && arvo.includes("FALSE")))
                            {}
                            else
                            {
                              if(arvo.includes("TRUE"))
                              {
                                ws.send("ioset "+logic_list[i][0]+" "+io_nimi+" true"); // logiikka, io, value
                                logic_list[i][3][a] = true;
                                console.log("Logic "+String(logic_list[i][0])+" changed io "+String(io_nimi)+" value to true");
                              }
                                else
                              {
                                ws.send("ioset "+logic_list[i][0]+" "+io_nimi+" false"); // logiikka, io, value
                                logic_list[i][3][a] = false;
                                console.log("Logic "+String(logic_list[i][0])+" changed io "+String(io_nimi)+" value to false");
                              }
                            }
                          }
                          a++;
                        }
                        i++;
                    }
                  }   
              }
              , 1000);

    ws.on('message', async (message) => 
    {
      let msg_parts = String(message).split(" ");
      console.log(msg_parts);
      if (msg_parts[0] == "logic" && msg_parts[1] == "add") //nimi osoite user password
      {
        active_commands=true;
        let logic_name = msg_parts[2], logic_ip = msg_parts[3], user = msg_parts[4], pass = msg_parts[5];
              uusi_sivu = await browser.newPage();
        
              await uusi_sivu.goto('http://'+String(logic_ip));
              await uusi_sivu.waitForSelector('.intro_enter');
              await uusi_sivu.click('.intro_enter');

              await uusi_sivu.waitForSelector('#loginBox');

              await uusi_sivu.type('#Login_Area_Name_InputTag', user);
              await uusi_sivu.type('#Login_Area_PW_InputTag', pass);
              await uusi_sivu.click('#Login_Area_SubmitButton');
  
              //sertikaatti virhe
              await uusi_sivu.waitForSelector('#details-button');
              await uusi_sivu.click('#details-button');

              await uusi_sivu.click('#proceed-link');

              await uusi_sivu.waitForSelector('.intro_enter');
              await uusi_sivu.click('.intro_enter');

              let hrefValue = '/Portal/Portal.mwsl?PriNav=Varstate';
              await uusi_sivu.click(`a[href="${hrefValue}"]`);
      
              logic_list.push([logic_name,uusi_sivu,[],[],[],[]]);

              console.log("logiikka "+String(logic_name)+" added from "+String(logic_ip));
              ws.send("logic added "+String(logic_name)+" "+String(logic_ip));

              active_commands=false;
      }

      if(msg_parts[0] == "ioupdate_beta")
      {
        console.log("Nodejs muutti minecraftin arvoja.");
        ws.send("ioset "+args[1]+" "+args[2]+" "+args[3]); // logiikka, io, value
      }

      if(msg_parts[0] == "ioset")
      {
        active_commands=true;
        let Logiikan_nimi = msg_parts[1];
        let logiikan_index = hanki_logic_id(Logiikan_nimi);
        let selain = logic_list[logiikan_index][1];
        let io_nimi = msg_parts[2];
        let uusi_arvo = msg_parts[3];
        let io_v_lista = logic_list[logiikan_index][4];
        let io_index = hanki_io_id(logiikan_index,io_nimi);
        let kohta = io_v_lista[io_index];
        
        await selain.waitForSelector('input[name="'+String(io_nimi)+'"]');
        await selain.type('input[name="'+String(io_nimi)+'"]', uusi_arvo);

        await selain.evaluate((kohta) => {
          send_write_rq(kohta); //suoritetaan javascript sivulla
        }, kohta);

        console.log("IO "+String(io_nimi)+" set to "+String(uusi_arvo));
        active_commands=false;
      }

      if (msg_parts[0] == "io" && msg_parts[1] == "add" ) //logic io
      {
        active_commands=true;
        let logic_name = msg_parts[3];
        let io_name = msg_parts[4];
        let index = hanki_logic_id(logic_name);
        let vanha_sivu = logic_list[index][1];
        let io_list = logic_list[index][2];
        let io_values = logic_list[index][3];
        let io_v_kohdat = logic_list[index][4];
        let io_tyypit = logic_list[index][5];
        io_list.push(io_name);
        let txt = "";
        if(msg_parts[2] == "input")
        {
          io_values.push(false);
          txt="input"
          io_tyypit.push(true);
        }
          else
        {
          io_values.push(false);
          io_tyypit.push(false);
          txt="output"
        }
        
        let kohta = io_list.length;
        io_v_kohdat.push(kohta);
        await vanha_sivu.waitForSelector('input[name="v'+String(kohta)+'"]');
        await vanha_sivu.type('input[name="v'+String(kohta)+'"]', io_name);
        await vanha_sivu.click('input[name="DoVars"]');
        ws.send("io added "+String(txt) + " "+String(logic_name) +" " + String(io_name));
        logic_list[index][2] = io_list;
        logic_list[index][3] = io_values;
        logic_list[index][4] = io_v_kohdat;
        logic_list[index][5] = io_tyypit;
        active_commands=false;
      }

    });
  });
})();