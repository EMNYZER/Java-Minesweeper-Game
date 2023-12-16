package minesweeper;

import static java.lang.Math.ceil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class Score extends GameObject
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
    
    
    public void resetScore(int playerId) {
        PreparedStatement statement = null;
        try {
            String resetQuery = "UPDATE score SET Games_Played = 0, Games_won = 0, LWStreak = 0, LLStreak = 0, CStreak = 0, CWStreak = 0, CLStreak = 0 WHERE Id = ?";
            statement = connection.prepareStatement(resetQuery);
            statement.setInt(1, playerId);
            statement.executeUpdate();
    
            longestLosingStreak = 0;
            currentStreak = 0;
            currentLosingStreak= 0;
            gamesPlayed = 0;
            
            statement.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
    
    public void updateGamesPlayed(int playerId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String countQuery = "SELECT COUNT(*) AS gameCount FROM score WHERE Id = ?";
            statement = connection.prepareStatement(countQuery);
            statement.setInt(1, playerId);
            resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                int gameCount = resultSet.getInt("gameCount");
    
                // Jika Games_Played masih 0, atur nilai menjadi 1
                if (gameCount > 0) {
                    String updateQuery = "UPDATE score SET Games_Played = ? WHERE Id = ?";
                    statement = connection.prepareStatement(updateQuery);
                    statement.setInt(1, gameCount);
                    statement.setInt(2, playerId);
                    statement.executeUpdate();
                } else {
                    String resetGamesPlayedQuery = "UPDATE score SET Games_Played = 1 WHERE Id = ?";
                    statement = connection.prepareStatement(resetGamesPlayedQuery);
                    statement.setInt(1, playerId);
                    statement.executeUpdate();
                }
            }
    
            resultSet.close();
            statement.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
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

    private int score;
    public void resetScore() {
        reset();
        score = 0;
        System.out.println("Score has been reset.");
    }

    public void performAction() {
        System.out.println("Performing scoring action");
    }

    
    public void save(int playerId){
        PreparedStatement statement = null;
        try {          
        String checkIfExists = "SELECT COUNT(*) AS count FROM score WHERE Id = ?";
        statement = connection.prepareStatement(checkIfExists);
        statement.setInt(1, playerId);
        ResultSet rs = statement.executeQuery();
        rs.next();
        int count = rs.getInt("count");
        statement.close();
        
        if (count > 0) {
            String updateScore = "UPDATE score SET Games_Played=?, Games_won=?, LWStreak=?, LLStreak=?, CStreak=?, CWStreak=?, CLStreak=? WHERE Id=?";
            statement = connection.prepareStatement(updateScore);
            statement.setInt(1, gamesPlayed);
            statement.setInt(2, gamesWon);
            statement.setInt(3, longestWinningStreak);
            statement.setInt(4, longestLosingStreak);
            statement.setInt(5, currentStreak);
            statement.setInt(6, currentWinningStreak);
            statement.setInt(7, currentLosingStreak);
            statement.setInt(8, playerId);
            statement.executeUpdate();
            statement.close();
        } else {
            String insertScore = "INSERT INTO score (Id, Games_Played, Games_won, LWStreak, LLStreak, CStreak, CWStreak, CLStreak) VALUES (?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(insertScore);
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
        } catch(SQLException sqlex) {
        sqlex.printStackTrace();
        }
    }

}
