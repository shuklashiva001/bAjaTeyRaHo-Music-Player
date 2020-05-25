/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author shiva
 */
public class SearchinPC
{
    
    private static final String username = "root";
    private static final String password = "Passwordhai@2000";
    private static final String connector = "jdbc:mysql://localhost:3306/bAjaTeyRaHo_music_players";
    static Connection conn = null;
    
    public SearchinPC()
    {
        Listing_Songs ls = new Listing_Songs();
        ls.stopsong();
        try 
        {
            conn = DriverManager.getConnection(connector,username,password);
            Statement stmt = (Statement) conn.createStatement();
            String sql = "CREATE DATABASE bAjaTeyRaHo_music_player";
            stmt.executeUpdate(sql);
            System.out.println("Done");
        }
        catch(SQLException e) 
        {
            //
        }
    }
    
    public void search() throws IOException, SQLException
    {
        ListallFiles List_Files = new ListallFiles();
        List_Files.ListallFiles("Music Player");
        System.out.println("Searched");
    }
    
}
