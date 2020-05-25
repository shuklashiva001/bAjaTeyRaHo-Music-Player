package musicplayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JLabel;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;


public final class Listing_Songs{
    
    static AdvancedPlayer player = null;
    private static final String username = "root";
    private static final String password = "Passwordhai@2000";
    private static final String connector = "jdbc:mysql://localhost:3306/bAjaTeyRaHo_music_player";
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rst =  null;
    static ArrayList <String> songname = null;
    static ArrayList <String> songurl = null;
    static int n;
    static String path;
    static String song_name;
    static int playing = 0;
    static int currentindex = 0;
    private JLabel lblSongName;
    static Thread t  = null;
    
    public Listing_Songs()
    {
        songname = new ArrayList();
        songurl = new ArrayList();
        try
        {
            conn = DriverManager.getConnection(connector, username, password);
            stmt = (Statement) conn.createStatement();
            String sql = "SELECT Song_Name,SongUrl FROM MainTable WHERE 1";
            try 
            {
                rst = stmt.executeQuery(sql);
                while(rst.next())
                {
                    String var1 = (rst.getString("SongUrl"));
                    String var2 = (rst.getString("Song_Name"));
                    songname.add(var2);
                    songurl.add(var1);
                    
                }
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }
        }
        catch(SQLException e)
        {
            System.err.println(e);
        }
        n = songname.size();
        settingplayer();
    }
    
    public void settingplayer()
    {
        song_name = songname.get(currentindex%n);
        path = songurl.get(currentindex%n);
    }
    
    public void Get_Label(JLabel label)
    {
        lblSongName = label;
    }
    
    public void Change_Label()
    {
        
        lblSongName.setText(getsongname());
    }
    
    public String getsongname()
    {
        return song_name;
    }
    
    public void playsong()
    {
        Listing_Songs ls = new Listing_Songs();
        t = new Thread() 
        {
            @Override 
            public void run() 
            {
                try
                {
                    FileInputStream fis = new FileInputStream(path);
                    player = new AdvancedPlayer(fis);
                    playing = 1;
                    player.play();
                }
                catch (JavaLayerException | FileNotFoundException e) 
                {
                    System.err.println(e);
                }
                playing = 2;         // Important and difficult 
                nextsong();   
            }
        };
        t.start();
    }
    
    public void pausesong()
    {
        try
        {
            playing = 0;
            t.suspend();
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
    
    public void stopsong()
    {   
        try
        {
            if(t != null)
                t.stop();
            playing = 0;
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
    
    public void nextsong()
    {
        currentindex = (currentindex + 1 + n)%n;
        settingplayer();
        if(playing == 1)
        {
            stopsong();
            playsong();
        }
        else if(playing == 2)
        {
            playsong();
            Change_Label();
        }
        else
        {
            stopsong();
        }
    }
    
    public void previoussong()
    {
        currentindex = (currentindex - 1 + n)%n;
        settingplayer();
        if(playing == 1)
        {
            stopsong();
            playsong();
        }
        else
        {
            stopsong();
        }    
    }
    
    public void resumesong()
    {
        try
        {
            playing = 1;
            t.resume();
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}
