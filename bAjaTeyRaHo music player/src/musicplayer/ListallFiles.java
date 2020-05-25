package musicplayer;

import java.io.*;
import java.sql.*;

public class ListallFiles {
    
    //ArrayList<File> Songs_Array;
    private static final String username = "root";
    private static final String password = "Passwordhai@2000";
    private static final String connector = "jdbc:mysql://localhost:3306/bAjaTeyRaHo_music_player";
    static Connection conn = null;
    Statement stmt = null;
    
    public ListallFiles()
    {
        try
        {
            conn = DriverManager.getConnection(connector,username,password);
            stmt = conn.createStatement();
        }
        catch(SQLException e)
        {
            System.err.println(e);
        }
    }
    
    public boolean IsDirectory(File f)
    {
        if(f.isDirectory())  //Checking whether Path is directory or not
            return true;
        return false;
    }
    
    public boolean CheckExtension(File f)
    {
        String extension = "";
        int i = (f.getName()).lastIndexOf('.'); // Finding extension of files
        if (i > 0) 
        {
            extension = (f.getName()).substring(i+1);
        }
        if(extension.equals("mp3")) //Checking for audio files mp3.
            return true;
       return false;
    }
    
    public void ListallFiles(String path) throws IOException, SQLException
    {
        try
        {
            String sql = "CREATE TABLE MainTable(Song_Name varchar(200), SongUrl varchar(400), Extension varchar(5), PRIMARY KEY (Song_Name))"; // creating table
            stmt.executeUpdate(sql);
            System.out.println("Table created");
        }
        catch(SQLException e)
        {
            String sql = "TRUNCATE TABLE MainTable";  // if Table exists delete all the row for searching folder again
            stmt.executeUpdate(sql);
        }
        FindFiles(path);
        if(stmt != null)
        {
            stmt.close();
        }
    }
    
    public void FindFiles(String Path)
    {
        File dir = new File(Path);
        File[] files = dir.listFiles();
        for(File x : files)
        {
            if(IsDirectory(x))
                FindFiles(x.getPath());  // Recursively calling directory for files
            else if(CheckExtension(x))
            {
                try
                {
                    String sql = "INSERT INTO MainTable VALUES ('" + x.getName() + "','" + x.getAbsolutePath() + "','mp3')";
                    stmt.executeUpdate(sql);
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
            }
        }
    }
    
   
}


