package minesweeper;

import static java.lang.Math.ceil;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Comparator;


public class Score
{
    private Connection connection;
    private int playerId;

    ArrayList<Time> bestTimes;
    
    int gamesPlayed;
    int gamesWon;
       
    int longestWinningStreak;
    int longestLosingStreak;
    
    int currentStreak;

    int currentWinningStreak;
    int currentLosingStreak;
    
    public Score(int playerId)
    {
        this.connection = DatabaseConnection.getConnection();
        this.playerId = playerId;
        gamesPlayed = gamesWon = currentStreak = longestLosingStreak = longestWinningStreak = currentWinningStreak = currentLosingStreak = 0;
        bestTimes = new ArrayList();
    }
    
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getGamesPlayed()
    {
        return gamesPlayed;        
    }
    
    public int getGamesWon()
    {        
        return gamesWon;
    }
    
    public int getWinPercentage()
    {
        double gP = gamesPlayed;
        double gW = gamesWon;
        
        double percentage = ceil((gW/gP) * 100);
        
        return (int)percentage;
    }
    
    public int getLongestWinningStreak()
    {
        return longestWinningStreak;
    }
    
    public int getLongestLosingStreak()
    {
        return longestLosingStreak;
    }
    
    public int getCurrentStreak()
    {
        return currentStreak;
    }
    
    public int getCurrentLosingStreak()
    {
        return currentLosingStreak;
    }

    public int getCurrentWinningStreak(){
        return currentWinningStreak;
    }
    
    public void incGamesWon()
    {
        gamesWon++;
    }
    
    public void incGamesPlayed()
    {
        gamesPlayed++;
    }
    
    public void incCurrentStreak()
    {
        currentStreak++;
    }
    
    public void incCurrentLosingStreak()
    {
        currentLosingStreak++;
        
        if (longestLosingStreak < currentLosingStreak)
        {
            longestLosingStreak = currentLosingStreak;
        }                
    }

    public void incCurrentWinningStreak()
    {
        currentWinningStreak++;
        
        if (longestWinningStreak < currentWinningStreak)
        {
            longestWinningStreak = currentWinningStreak;
        }                
    }
    
    
    public void decCurrentStreak()
    {        
        currentStreak--;
    }    
    
    
    public void resetScore()
    {
        gamesPlayed = gamesWon = currentStreak = longestLosingStreak = longestWinningStreak = currentWinningStreak = currentLosingStreak = 0;
    }
    
    
    
    public ArrayList<Time> getBestTimes()
    {
        return bestTimes;
    }
        
    
    public void addTime(int time, Date date)
    {
        bestTimes.add(new Time(time,date));
        Collections.sort(bestTimes,new TimeComparator()); 
        
        if(bestTimes.size() > 5)
            bestTimes.remove(bestTimes.size()-1);
    }

    public boolean populate()
    {
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Score");

            while(resultSet.next()) 
            {
                gamesPlayed = resultSet.getInt("Games_Played");
                gamesWon = resultSet.getInt("Games_won");

                longestWinningStreak = resultSet.getInt("LWStreak");
                longestLosingStreak = resultSet.getInt("LLStreak");

                currentStreak = resultSet.getInt("CStreak");

                currentWinningStreak = resultSet.getInt("CWStreak");
                currentLosingStreak = resultSet.getInt("CLStreak");                                
            }
            resultSet.close();
            statement.close();

            return true;
        }
        catch(SQLException sqlex)
        {
            sqlex.printStackTrace();
            return false;
        }
    }

    
    public void save(int playerId)
    {
        PreparedStatement statement = null;
        
        try {          
            String scoreTemplate = "INSERT INTO score (Id, Games_Played, Games_won, LWStreak, LLStreak, CStreak, CWStreak, CLStreak) VALUES (?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(scoreTemplate);
        
            statement.setInt(1, playerId);
            statement.setInt(2, gamesPlayed);
            statement.setInt(3, gamesWon);
            statement.setInt(4, longestWinningStreak);
            statement.setInt(5, longestLosingStreak);
            statement.setInt(6, currentStreak);
            statement.setInt(7, currentWinningStreak);
            statement.setInt(8, currentLosingStreak);
        
            statement.executeUpdate();
            statement.close();      
        }
        catch(SQLException sqlex)
        {
            sqlex.printStackTrace();
        }
        
    }

}
